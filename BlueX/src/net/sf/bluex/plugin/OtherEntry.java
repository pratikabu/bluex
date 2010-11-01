/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.plugin;

import java.util.Vector;

/**
 *
 * @author Blue
 */
public class OtherEntry {
    private String pluginName;
    private Vector<MenuEntry> menu=new Vector<MenuEntry>();

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public void addMenu(MenuEntry me){
        menu.add(me);
    }

    public Vector<MenuEntry> getMenu() {
        return menu;
    }

    @Override
    public String toString() {
        return getPluginName();
    }
}
