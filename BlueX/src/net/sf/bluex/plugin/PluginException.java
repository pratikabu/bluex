/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.plugin;

/**
 * This exception is thrown when there is some problem while loading a plugin
 * from the installed location.
 * @author Blue
 */
public class PluginException extends Exception{

    /**
     * Only pass the message to be shown in the exception
     * @param msg
     */
    public PluginException(String msg){
        super(msg);
    }

    /**
     * Pass the message to be shown and the cause or the stack trace of the exception
     * @param msg
     * @param cause
     */
    public PluginException(String msg, Throwable cause){
        super(msg,cause);
    }
}
