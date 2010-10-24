/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.plugin;

import javax.swing.JPanel;

/**
 *
 * @author Blue
 */
public abstract class MyPanel extends JPanel{

    /**
     * This method should be implemented by every plugin if they wanted to become my panel.
     * As this method ensures the saftey.
     * @return
     */
    public abstract boolean safeToClose();

    /**
     * if the base window have the tabbed view then this method will give the tab the name
     * @return
     */
    public abstract String getTabName();
}
