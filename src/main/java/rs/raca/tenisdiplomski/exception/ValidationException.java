/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.raca.tenisdiplomski.exception;

import javax.ws.rs.core.Response;

/**
 *
 * @author marko
 */
public class ValidationException extends AbstractException{

    public ValidationException (String message){
        super(message, 400, Response.Status.BAD_REQUEST);
    }
}
