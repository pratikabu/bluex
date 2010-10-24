
package net.sf.bluex.plugin;

import java.io.File;

/**
 * The Plugin interface contains three method for each type of the plugin.
 * Any plugin must implement any of these methods if it provides functionality according to it else
 * the user may provide dummy implementations so that only the workable method is used according to the file type.
 * @author Blue
 */
public interface Plugin {

    /**
     * This method is called when the type of the plugin is found FILE_ASSOCIATION_PLUGIN.
     * @param file
     * @throws com.gmail.pratikabu.blueX.components.plugin.PluginException
     */
    public void openFileAssociationPlugin(File[] file) throws PluginException;

    /**
     * This method is called when the type of the plugin is found NEW_WINDOW_PLUGIN.
     * @param file
     * @throws com.gmail.pratikabu.blueX.components.plugin.PluginException
     */
    public void openNewWindowPlugin(File[] file) throws PluginException;

    /**
     * This method is called when the type of the plugin is found PANEL_BASED_PLUGIN.
     * @param file
     * @throws com.gmail.pratikabu.blueX.components.plugin.PluginException
     */
    public MyPanel openPanelBasedPlugin(File[] file) throws PluginException;

    /** the constant which gives the extension of the plugin file */
    public static final String PLUGIN_EXTENSION="plugin";

}
