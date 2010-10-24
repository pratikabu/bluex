/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.bluex.notepad.boundary;

import java.io.File;
import net.sf.bluex.plugin.MyPanel;
import net.sf.bluex.plugin.PluginException;

/**
 *
 * @author Blue
 */
public class Plugin implements net.sf.bluex.plugin.Plugin {

    /**
     * Since this is a file assocication plugin so this method is to be implemented
     * @param uri
     * @throws com.gmail.pratikabu.blueX.components.plugin.PluginException
     */
    @Override
    public void openFileAssociationPlugin(File[] uri) throws PluginException {
        for(File file : uri)
            new Notepad().openFile(file);//currently open all the files in different window
    }

    /**
     * And also this is a new window plugin too.. so this method is also be implemented
     * @param uri
     * @throws com.gmail.pratikabu.blueX.components.plugin.PluginException
     */
    @Override
    public void openNewWindowPlugin(File[] uri) throws PluginException {
        new Notepad();
    }

    @Override
    public MyPanel openPanelBasedPlugin(File[] uri) throws PluginException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
