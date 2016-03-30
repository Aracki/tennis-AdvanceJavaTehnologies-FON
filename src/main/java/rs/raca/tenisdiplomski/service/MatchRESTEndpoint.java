/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.raca.tenisdiplomski.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.raca.tenisdiplomski.domain.Mec;
import rs.raca.tenisdiplomski.domain.Takmicar;
import rs.raca.tenisdiplomski.exception.DataNotFoundException;
import rs.raca.tenisdiplomski.exception.MyRollbackException;
import rs.raca.tenisdiplomski.exception.NotAuthorizedException;
import rs.raca.tenisdiplomski.exception.ValidationException;
import rs.raca.tenisdiplomski.help.RestHelpClass;

/**
 *
 * @author marko
 */
@Path("/match")
public class MatchRESTEndpoint {

    RestHelpClass help;

    public MatchRESTEndpoint() {
        help = new RestHelpClass();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{liga}")
    public Response getMatch(@HeaderParam("authorization") String authorization, @PathParam("liga") int ligaID) {
        EntityManager em = help.getEntityManager();
        if (help.isLogged(authorization, em)) {
            String query = "SELECT m FROM Mec m JOIN m.takmicarDID t JOIN t.liga l WHERE l.ligaID = " + ligaID;
            List<Mec> mecevi = em.createQuery(query).getResultList();
            if (mecevi != null) {
                return Response.ok().entity(mecevi).build();
            } else {
                throw new DataNotFoundException("Nema meceva!");
            }
        } else {
            throw new NotAuthorizedException("Niste ulogovani!");
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertMatch(@HeaderParam("authorization") String authorization, Mec mec) {
        EntityManager em = help.getEntityManager();
        if (help.isLogged(authorization, em)) {
            Takmicar oldTakmicarDID = em.find(Takmicar.class, mec.getTakmicarDID().getTakmicarID());
            Takmicar oldTakmicarGID = em.find(Takmicar.class, mec.getTakmicarGID().getTakmicarID());
            Takmicar newTakmicarDID = mec.getTakmicarDID();
            Takmicar newTakmicarGID = mec.getTakmicarGID();
            if (oldTakmicarDID != null && oldTakmicarGID != null) {
                Takmicar takmicarDID = (Takmicar) help.mergeValues(oldTakmicarDID, newTakmicarDID);
                Takmicar takmicarGID = (Takmicar) help.mergeValues(oldTakmicarGID, newTakmicarGID);
                new TakmicarRESTEndpoint().updateTakmicar(authorization, takmicarDID);
                new TakmicarRESTEndpoint().updateTakmicar(authorization, takmicarGID);
                mec.setTakmicarDID(takmicarDID);
                mec.setTakmicarGID(takmicarGID);
                try {
                    help.persistObject(em, mec);
                } catch (RollbackException e) {
                    throw new MyRollbackException("Greska!");
                }
            } else {
                throw new ValidationException("Greska!!!");
            }
            return Response.status(Response.Status.CREATED).build();
        } else {
            throw new NotAuthorizedException("Niste ulogovani!");
        }
    }
}
