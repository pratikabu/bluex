/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.components;

import net.sf.bluex.controller.UsefulMethods;
import net.sf.bluex.plugin.*;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JOptionPane;
import net.sf.bluex.exceptionManager.ExceptionManager;
import net.sf.bluex.threads.MyThread;

/**
 *
 * @author Blue
 */
public class SystemIconPopupMenu extends java.awt.PopupMenu implements ActionListener{
    private java.awt.MenuItem mniShowExplorer, mniExceptionManager, mniAboutBlueX, mniHelpContents, mniExit;
    public static net.sf.bluex.components.SystemIcon si;
    
    public SystemIconPopupMenu(net.sf.bluex.components.SystemIcon si){
        SystemIconPopupMenu.si=si;
        initComponents();
        placeComponents();
    }
    
    /**
     * @private methods
     */
    private void initComponents(){
        java.awt.Font defaultFont=new java.awt.Font(null, java.awt.Font.PLAIN, 11);
        
        mniShowExplorer=new java.awt.MenuItem("Open BlueX");
        mniShowExplorer.setFont(new java.awt.Font(null, java.awt.Font.BOLD, 11));
        mniShowExplorer.addActionListener(this);

        mniExceptionManager=new java.awt.MenuItem("Exception Manager");
        mniExceptionManager.setFont(defaultFont);
        mniExceptionManager.addActionListener(this);
        
        mniAboutBlueX=new java.awt.MenuItem("About BlueX");
        mniAboutBlueX.setFont(defaultFont);
        mniAboutBlueX.addActionListener(this);
        
        mniHelpContents=new java.awt.MenuItem("Help Contents");
        mniHelpContents.setFont(defaultFont);
        mniHelpContents.addActionListener(this);
        
        mniExit=new java.awt.MenuItem("Exit");
        mniExit.setFont(defaultFont);
        mniExit.addActionListener(this);
    }
    
    private void placeComponents(){
        this.add(mniShowExplorer);
        this.addSeparator();
        initPluginsMenu();
        this.addSeparator();
        this.add(mniExceptionManager);
        this.add(mniAboutBlueX);
        this.add(mniHelpContents);
        this.addSeparator();
        this.add(mniExit);
    }

    private void initPluginsMenu() {
        Vector<PluginMetaData> vectPMD=UsefulMethods.getAllInstalledPlugins(ModuleDetail.NEW_WINDOW_PLUGIN);
        if(vectPMD!=null)
            for(final PluginMetaData pmd : vectPMD){
                MenuItem jmi=new MenuItem(pmd.getName());
                jmi.setFont(new java.awt.Font(null, java.awt.Font.PLAIN, 11));
                jmi.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try{
                            Plugin pg=UsefulMethods.getSelectedPlugin(pmd);
                            pg.openNewWindowPlugin(null);
                        }catch(Exception ex){
                            JOptionPane.showMessageDialog(null, "Cannot open the selected Plugin.");
                        }
                    }
                });

                this.add(jmi);
            }
    }
    
    /**
     * public methods
     */
    
    public void actionPerformed(ActionEvent ae){
        Object obj=ae.getSource();
        if (obj == mniShowExplorer) {
            new net.sf.bluex.boundary.BaseWindow(null);
        } else if (obj == mniExceptionManager) {
            ExceptionManager.showExceptionManager();
        } else if (obj == mniAboutBlueX) {
            net.sf.bluex.boundary.AboutBlueX.showAboutDialog(null);
        } else if (obj == mniHelpContents) {
            HelpMapper.showHelpHome();
        } else if (obj == mniExit) {
            exit();
        }
    }
    
    /**
     * its own static method for other classes
     */
    public static void exit(){
        MyThread.exit(si);
    }
}
