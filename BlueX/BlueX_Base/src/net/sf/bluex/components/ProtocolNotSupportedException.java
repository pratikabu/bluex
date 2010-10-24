/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.components;

/**
 *
 * @author Blue
 */
public class ProtocolNotSupportedException extends RuntimeException {

    public ProtocolNotSupportedException() {
        super();
    }

    public ProtocolNotSupportedException(String message) {
        super(message);
    }
}
