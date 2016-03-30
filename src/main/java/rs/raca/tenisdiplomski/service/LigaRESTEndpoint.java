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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.raca.tenisdiplomski.domain.Liga;
import rs.raca.tenisdiplomski.domain.Takmicenje;
import rs.raca.tenisdiplomski.exception.DataNotFoundException;
import rs.raca.tenisdiplomski.exception.MyRollbackException;
import rs.raca.tenisdiplomski.exception.NotAuthorizedException;
import rs.raca.tenisdiplomski.help.RestHelpClass;

/**
 *
 * @author marko
 */
@Path("/liga")
public class LigaRESTEndpoint {

    RestHelpClass help;

    public LigaRESTEndpoint() {
        help = new RestHelpClass();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLiga(@HeaderParam("authorization") String authorization, @QueryParam("takmicenje") int takmicenje) {
        EntityManager em = help.getEntityManager();
        if (help.isLogged(authorization, em)) {
            String query = "SELECT l FROM Liga l";
            if (takmicenje != 0) {
                query += " WHERE l.takmicenje.takmicenjeID = " + takmicenje;
            }
            System.out.println(query);
            List<Liga> lige = em.createQuery(query).getResultList();
            if (lige.isEmpty()) {
                throw new DataNotFoundException("Nema takmicenja!");
            } else {
                return Response.ok().entity(lige).build();
            }
        } else {
            throw new NotAuthorizedException("Niste ulogovani!");
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertLiga(@HeaderParam("authorization") String authorization, Liga liga) {
        EntityManager em = help.getEntityManager();
        if (help.isLogged(authorization, em)) {
            try {
                liga.setTakmicenje(em.find(Takmicenje.class, liga.getTakmicenje().getTakmicenjeID()));
                help.persistObject(em, liga);
                em.close();
                return Response.status(Response.Status.CREATED).build();
            } catch (RollbackException e) {
                throw new MyRollbackException("Ova liga vec postoji u bazi!");
            }

        } else {
            throw new NotAuthorizedException("Niste ulogovani!");
        }
    }
}
