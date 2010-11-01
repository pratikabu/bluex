/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.exceptionManager;

import java.util.Vector;

/**
 *
 * @author Blue
 */
public class ExceptionMetaData {
    private String date;
    private String message;
    private Vector<String> stackTrace=new Vector<String>();;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addStackTrace(String s){
        stackTrace.add(s);
    }

    public String getStackTrace(){
        StringBuffer sb=new StringBuffer("");
        for(String s : stackTrace)
            sb.append("\n"+s);
        return sb.toString();
    }
}
