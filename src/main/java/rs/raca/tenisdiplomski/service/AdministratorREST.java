/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.raca.tenisdiplomski.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.raca.tenisdiplomski.domain.Administrator;
import rs.raca.tenisdiplomski.exception.BasicAuthenticationException;
import rs.raca.tenisdiplomski.help.JsonToken;
import rs.raca.tenisdiplomski.help.RestHelpClass;

/**
 *
 * @author marko
 */
@Path("/administrator")
public class AdministratorREST {

    RestHelpClass help;

    public AdministratorREST() {
        help = new RestHelpClass();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRadnici(@HeaderParam("authorization") String authorization) {
        EntityManager em = help.getEntityManager();
        Administrator ar = em.find(Administrator.class, Integer.parseInt(help.getAbstractToken().decode(authorization).split("##")[1]));
        return Response.ok().entity(ar).build();

    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@HeaderParam("authorization") String authorization) {
        try {
            String[] userPass = help.getAbstractToken().decodeBasicAuth(authorization);
            EntityManager em = help.getEntityManager();
            Administrator a = (Administrator) em
                    .createQuery("SELECT a FROM Administrator a WHERE a.user = :user AND a.pass = :pass")
                    .setParameter("user", userPass[0])
                    .setParameter("pass", userPass[1])
                    .getSingleResult();

            if (a.getToken() == null || a.getToken().equals("")) {
                a.setToken(help.getAbstractToken().createToken(a.getId()));
                help.mergeObject(em, a);
            }
            JsonToken jsonToken = new JsonToken(help.getAbstractToken().encode(a.getToken()));
            return Response.ok().entity(jsonToken).build();
        } catch (RuntimeException e) {
            Logger.getLogger(AdministratorREST.class.getName()).log(Level.SEVERE, null, e);
            throw new BasicAuthenticationException(e.getMessage());
        }
    }

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@HeaderParam("authorization") String authorization) {
        EntityManager em = help.getEntityManager();
        Administrator ar = em.find(Administrator.class, Integer.parseInt(help.getAbstractToken().decode(authorization).split("##")[1]));
        if (ar != null) {
            ar.setToken(null);
            help.mergeObject(em, ar);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
