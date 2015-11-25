/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Shared;

import java.io.Serializable;

/**
 *
 * @author Stan
 */
public interface IMessage extends Serializable {
    public String getSender();
    public String getMessage();
    @Override
    public String toString();
}
