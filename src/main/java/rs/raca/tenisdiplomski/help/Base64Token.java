/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.raca.tenisdiplomski.help;

import java.util.Base64;

/**
 *
 * @author Marko
 */
public class Base64Token extends AbstractTokenCreator {
    
    @Override
    public String createToken(Object ... o) {
        String s = "TOKEN";
        for (Object o1 : o) {
            s += "##" + o1;
        }
        return s;
    }

    @Override
    public String encode(String token) {
        return Base64.getEncoder().encodeToString(token.getBytes());
    }

    @Override
    public String decode(String token) {
        return new String(Base64.getDecoder().decode(token));
    }

}
