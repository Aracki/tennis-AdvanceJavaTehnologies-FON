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
import rs.raca.tenisdiplomski.domain.Tiptakmicenja;
import rs.raca.tenisdiplomski.exception.DataNotFoundException;
import rs.raca.tenisdiplomski.exception.NotAuthorizedException;
import rs.raca.tenisdiplomski.help.RestHelpClass;

/**
 *
 * @author marko
 */
@Path("/tipTakmicenja")
public class TipTakmicenjaREST {
    RestHelpClass help;

    public TipTakmicenjaREST() {
        help = new RestHelpClass();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTipTakmicenja(@HeaderParam("authorization") String authorization) {
        EntityManager em = help.getEntityManager();
        if (help.isLogged(authorization, em)) {
            List<Tiptakmicenja> tipovi = em.createNamedQuery("Tiptakmicenja.findAll").getResultList();
            if (tipovi.isEmpty()){
                throw new DataNotFoundException("Nema tipova!");
            } else {
                return Response.ok().entity(tipovi).build();
            }
        } else {
            throw new NotAuthorizedException("Niste ulogovani!");
        }
    }
}
