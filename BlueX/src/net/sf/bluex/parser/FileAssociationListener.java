/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.parser;

import java.util.Vector;
import net.sf.bluex.components.FileAssociation;
import newComponents.events.ParsingListener;

/**
 *
 * @author Blue
 */
public class FileAssociationListener implements ParsingListener{
    private Vector<FileAssociation> vectFA=new Vector<FileAssociation>();

    private FileAssociation fa;

    public void startElement(String element) {
        if(element.equals("association"))
            fa=new FileAssociation();
    }

    public void character(String data, String element) {
        if(element.equals("plugin-name"))
            fa.setPluginName(data);
        else if(element.equals("extension"))
            fa.setExtension(data);
    }

    public void endElement(String element) {
        if(element.equals("association"))
            vectFA.add(fa);
    }

    public Vector<FileAssociation> getVectFA() {
        return vectFA;
    }
}
