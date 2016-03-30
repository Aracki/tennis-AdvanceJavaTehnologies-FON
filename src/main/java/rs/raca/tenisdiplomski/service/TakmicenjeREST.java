    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.raca.tenisdiplomski.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.raca.tenisdiplomski.domain.Takmicenje;
import rs.raca.tenisdiplomski.exception.DataNotFoundException;
import rs.raca.tenisdiplomski.exception.MyRollbackException;
import rs.raca.tenisdiplomski.exception.NotAuthorizedException;
import rs.raca.tenisdiplomski.help.RestHelpClass;

/**
 *
 * @author marko
 */
@Path("/takmicenje")
public class TakmicenjeREST {

    RestHelpClass help;

    public TakmicenjeREST() {
        help = new RestHelpClass();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTipTakmicenja(@HeaderParam("authorization") String authorization, @QueryParam("tip") int tip, @QueryParam("naziv") String naziv) {
        EntityManager em = help.getEntityManager();
        if (help.isLogged(authorization, em)) {
            String query = "SELECT t FROM Takmicenje t ";
            if (tip != 0) {
                query += "WHERE t.tiptakmicenja.tiptakmicenjaID = " + tip;
            }
            if (naziv != null) {
                String search = "'%" + naziv + "%'";
                if (tip != 0) {
                    query += " AND t.naziv LIKE " + search;
                } else {
                    query += "WHERE t.naziv LIKE " + search;
                }
            }
            System.out.println(query);
            List<Takmicenje> takmicenja = em.createQuery(query).getResultList();
            if (takmicenja.isEmpty()) {
                throw new DataNotFoundException("Nema takmicenja!");
            } else {
                return Response.ok().entity(takmicenja).build();
            }
        } else {
            throw new NotAuthorizedException("Niste ulogovani!");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ubaciTakmicenje(@HeaderParam("authorization") String authorization, Takmicenje takmicenje) {
        EntityManager em = help.getEntityManager();
        if (help.isLogged(authorization, em)) {
            try {
                help.persistObject(em, takmicenje);
                return Response.status(Response.Status.CREATED).build();
            } catch (Exception e) {
                throw new MyRollbackException("Ovo takmicenje vec postoji u bazi!");
            }
        } else {
            throw new NotAuthorizedException("Niste ulogovani!");
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteTakmicenje(@HeaderParam("authorization") String authorization, @PathParam("id") String id){
        EntityManager em = help.getEntityManager();
        if (help.isLogged(authorization, em)){
            Takmicenje takmicenje = em.find(Takmicenje.class, Integer.parseInt(id));
            if (takmicenje != null){
                help.removeObject(em, takmicenje);
                return Response.ok().entity("Uspesno obrisano takmicenje!").build();
            } else {
                throw new DataNotFoundException("Ovo takmicenje ne postoji u bazi!");
            }
        } else {
            throw new NotAuthorizedException("Nemate pristup ovom pozivu!");
        }
    }
}
