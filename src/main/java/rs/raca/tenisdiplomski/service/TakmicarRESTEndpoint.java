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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.raca.tenisdiplomski.domain.Liga;
import rs.raca.tenisdiplomski.domain.Mesto;
import rs.raca.tenisdiplomski.domain.Takmicar;
import rs.raca.tenisdiplomski.exception.DataNotFoundException;
import rs.raca.tenisdiplomski.exception.MyRollbackException;
import rs.raca.tenisdiplomski.exception.NotAuthorizedException;
import rs.raca.tenisdiplomski.help.RestHelpClass;

/**
 *
 * @author marko
 */
@Path("/takmicar")
public class TakmicarRESTEndpoint {

    RestHelpClass help;

    public TakmicarRESTEndpoint() {
        help = new RestHelpClass();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTakmicari(@HeaderParam("authorization") String authorization, @QueryParam("liga") int ligaID, @QueryParam("idTakmicara") int id) {
        EntityManager em = help.getEntityManager();
        if (help.isLogged(authorization, em)) {
            String query = "SELECT t FROM Takmicar t ";
            if (ligaID != 0) {
                query += "WHERE t.liga.ligaID = " + ligaID + " ORDER BY t.pozicija ASC";
                List<Takmicar> takmicari = em.createQuery(query).getResultList();
                if (takmicari != null) {
                    return Response.ok().entity(takmicari).build();
                } else {
                    throw new DataNotFoundException("Nema takmicara!");
                }
            }
            if (id != 0) {
                Takmicar takmicar = em.find(Takmicar.class, id);
                if (takmicar != null) {
                    return Response.ok().entity(takmicar).build();
                } else {
                    throw new DataNotFoundException("Nema takmicara!");
                }
            }
            List<Takmicar> takmicari = em.createQuery(query).getResultList();
            return Response.ok().entity(takmicari).build();
        } else {
            throw new NotAuthorizedException("Niste ulogovani!");
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{liga}")
    public Response insertTakmicar(@HeaderParam("authorization") String authorization, @PathParam("liga") int idLige, Takmicar takmicar) {
        EntityManager em = help.getEntityManager();
        if (help.isLogged(authorization, em)) {
            Liga liga = em.find(Liga.class, idLige);
            Mesto mesto = em.find(Mesto.class, takmicar.getMesto().getPtt());
            takmicar.setLiga(liga);
            takmicar.setMesto(mesto);
            try {
                help.persistObject(em, takmicar);
                return Response.status(Response.Status.CREATED).build();
            } catch (RollbackException e) {
                throw new MyRollbackException("Greska pri ubacivanju!");
            }
        } else {
            throw new NotAuthorizedException("Niste ulogovani!");
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTakmicar(@HeaderParam("authorization") String authorization, Takmicar takmicar) {
        EntityManager em = help.getEntityManager();
        if (help.isLogged(authorization, em)) {
            Takmicar oldTakmicar = em.find(Takmicar.class, takmicar.getTakmicarID());
            if (oldTakmicar != null) {
                Liga liga = em.find(Liga.class, takmicar.getLiga().getLigaID());
                Mesto mesto = em.find(Mesto.class, takmicar.getMesto().getPtt());
                takmicar.setLiga(liga);
                takmicar.setMesto(mesto);
                Takmicar t = (Takmicar) help.mergeValues(oldTakmicar, takmicar);
                help.mergeObject(em, t);
                return Response.ok().build();
            } else {
                throw new DataNotFoundException("Ne postoji u bazi!");
            }
        } else {
            throw new NotAuthorizedException("Niste ulogovani!");
        }
    }
}
