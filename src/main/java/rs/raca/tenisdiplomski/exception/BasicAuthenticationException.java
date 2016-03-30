/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.raca.tenisdiplomski.exception;

import javax.ws.rs.core.Response;

/**
 *
 * @author Marko
 */
public class BasicAuthenticationException extends AbstractException{
    public BasicAuthenticationException (String message){
        super(message, 401, Response.Status.UNAUTHORIZED);
    }
}
