/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.raca.tenisdiplomski.help;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import rs.raca.tenisdiplomski.domain.Administrator;

/**
 *
 * @author marko
 */
public class RestHelpClass {

    AbstractTokenCreator tokenHelper;

    public AbstractTokenCreator getAbstractToken() {
        return tokenHelper;
    }

    public RestHelpClass() {
        tokenHelper = new Base64Token();
    }

    public Object mergeValues(Object oldObject, Object newObject) {
        Field[] nizOld = oldObject.getClass().getDeclaredFields();
        Field[] nizNew = newObject.getClass().getDeclaredFields();
        for (int i = 0; i < nizOld.length; i++) {
            if (!nizOld[i].getName().equals("serialVersionUID")) {
                nizOld[i].setAccessible(true);
                nizNew[i].setAccessible(true);
                if (nizOld[i].getGenericType().getClass().toString().equals("class java.lang.Class")) {
                    try {
                        try {
                            if (nizNew[i].get(newObject) == null || Integer.parseInt(nizNew[i].get(newObject) + "") == 0) {
                                nizNew[i].set(newObject, nizOld[i].get(oldObject));
                            }
                        } catch (NumberFormatException ignore) {
                            if (nizNew[i].get(newObject) == null) {
                                nizNew[i].set(newObject, nizOld[i].get(oldObject));
                            }
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(RestHelpClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return newObject;
    }

    public boolean isLogged(String token, EntityManager em) {
        try {
            String[] userPass = getAbstractToken().decode(token).split("##");
            Administrator ar = em.find(Administrator.class, Integer.parseInt(userPass[1]));
            return ar.getToken() != null;
        } catch (Exception e) {
            return false;
        }
    }

    public EntityManager getEntityManager() {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("rs.raca_tenisDiplomski_war_1.0PU");
//        EntityManager ecm = emf.createEntityManager();
        return EMF.createEntityManager();
    }

    public void persistObject(EntityManager em, Object o) {
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
    }

    public void removeObject(EntityManager em, Object o) {
        em.getTransaction().begin();
        em.remove(o);
        em.getTransaction().commit();
    }

    public void mergeObject(EntityManager em, Object o) {
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
    }
}
