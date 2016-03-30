/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.raca.tenisdiplomski.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.raca.tenisdiplomski.domain.Mesto;
import rs.raca.tenisdiplomski.exception.DataNotFoundException;
import rs.raca.tenisdiplomski.exception.NotAuthorizedException;
import rs.raca.tenisdiplomski.help.RestHelpClass;

/**
 *
 * @author marko
 */
@Path("/mesto")
public class MestoREST {

    RestHelpClass help;

    public MestoREST() {
        help = new RestHelpClass();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMesto(@HeaderParam("authorization") String authorization) {
        EntityManager em = help.getEntityManager();
        if (help.isLogged(authorization, em)) {
            List<Mesto> mesta = em.createNamedQuery("Mesto.findAll").getResultList();
            if (mesta.isEmpty()){
                throw new DataNotFoundException("Nema mesta!");
            } else {
                return Response.ok().entity(mesta).build();
            }
        } else {
            throw new NotAuthorizedException("Niste ulogovani!");
        }
    }
}
