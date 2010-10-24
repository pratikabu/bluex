/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.exceptionManager;

import java.util.Vector;
import newComponents.events.ParsingListener;

/**
 *
 * @author Blue
 */
class ExceptionParsingListener implements ParsingListener{
    private Vector<ExceptionMetaData> vectEMD=new Vector<ExceptionMetaData>();
    private ExceptionMetaData emd;

    public void startElement(String element) {
        if(element.equals("exception"))
            emd=new ExceptionMetaData();
    }

    public void character(String data, String element) {
        if(element.equals("time"))
            emd.setDate(data);
        if(element.equals("message"))
            emd.setMessage(data);
        else if(element.equals("stack-trace"))
            emd.addStackTrace(data);
    }

    public void endElement(String element) {
        if(element.equals("exception"))
            vectEMD.add(emd);
    }

    public Vector<ExceptionMetaData> getEMDs(){
        return vectEMD;
    }
}
