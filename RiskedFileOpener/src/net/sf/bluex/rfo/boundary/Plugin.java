/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.rfo.boundary;

import java.io.File;
import net.sf.bluex.plugin.MyPanel;
import net.sf.bluex.plugin.PluginException;

/**
 *
 * @author Blue
 */
public class Plugin implements net.sf.bluex.plugin.Plugin {

    public void openFileAssociationPlugin(File[] file) throws PluginException {
        for(File filez : file)
            RiskDialog.showRiskDialog(filez);
    }

    public void openNewWindowPlugin(File[] file) throws PluginException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public MyPanel openPanelBasedPlugin(File[] file) throws PluginException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
