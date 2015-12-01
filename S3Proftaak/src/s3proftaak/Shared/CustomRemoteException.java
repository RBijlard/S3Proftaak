/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Shared;

import java.rmi.RemoteException;

/**
 *
 * @author Stan
 */
public class CustomRemoteException extends RemoteException {

    private final String message;

    public CustomRemoteException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
