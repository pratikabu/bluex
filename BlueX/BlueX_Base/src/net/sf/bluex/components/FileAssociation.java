/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.components;

/**
 *
 * @author Blue
 */
public class FileAssociation {
    private String pluginName, extension;

    public FileAssociation() {
    }

    public FileAssociation(String pluginName, String extension) {
        this.pluginName = pluginName;
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }
}
