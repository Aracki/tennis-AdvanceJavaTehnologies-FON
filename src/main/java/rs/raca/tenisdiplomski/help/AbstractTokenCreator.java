/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.raca.tenisdiplomski.help;

import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Marko
 */
public abstract class AbstractTokenCreator {

    public abstract String createToken(Object... o);

    public abstract String encode(String token);

    public abstract String decode(String token);

    public String[] decodeBasicAuth(String authorization) {
        if (authorization == null) {
            throw new RuntimeException("Invalid Authorization String.");
        }
        if (authorization.length() < 9) {
            throw new RuntimeException("Invalid Authorization String.");
        }
        if (authorization.length() > 64) {
            throw new RuntimeException("Invalid Authorization String.");
        }
        String s[] = authorization.split("\\s", 3);
        if (s.length < 2) {
            throw new RuntimeException("Invalid Authorization String.");
        }
        for (int i = 0; i < s.length; i++) {
            String part = s[i];
            if (part.compareTo("Basic") == 0) {
                String userPassBase64 = s[i + 1];
                if (!userPassBase64.isEmpty()) {
                    String userPass = null;
                    try {
                        userPass = new String(DatatypeConverter.parseBase64Binary(userPassBase64));
                    } catch (RuntimeException e) {
                        throw new RuntimeException("Authorization cannot be decoded.", e);
                    }
                    String userPassArray[] = userPass.split(":");
                    if (userPassArray.length == 2) {
                        return userPassArray;
                    } else {
                        throw new RuntimeException("Invalid Authorization String.");
                    }
                } else {
                    throw new RuntimeException("Invalid Authorization String.");
                }
            }
        }
        throw new RuntimeException("Authorization cannot be decoded.");
    }

}
