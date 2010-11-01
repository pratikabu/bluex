/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.components;

import net.sf.bluex.controller.FileModule;
import net.sf.bluex.controller.UsefulMethods;
import net.sf.bluex.plugin.*;

/**
 *
 * @author Blue
 */
public class SystemIcon extends java.awt.TrayIcon{
    public SystemIcon(){
        super(UsefulMethods.getImage(FileModule.APP_ICON));
        
        SystemIconPopupMenu sipm=new SystemIconPopupMenu(this);
        
        this.setPopupMenu(sipm);
        this.setToolTip("BlueX Environment");
        initFurtherComponents();
    }
    
    public SystemIcon(java.awt.Image img){
        super(img);
        initFurtherComponents();
        
    }
    
    public SystemIcon(java.awt.Image img,String toolTipText){
        super(img, toolTipText);
        initFurtherComponents();
    }
    public SystemIcon(java.awt.Image img,String toolTipText,java.awt.PopupMenu pm){
        super(img, toolTipText,pm);
        initFurtherComponents();
    }
    
    /**
     * private methods
     */
    private void initFurtherComponents(){
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent me){
                if(me.getClickCount()==2){
                    //do the coding of showing the explorer window
                    new net.sf.bluex.boundary.BaseWindow(null);
                }
            }
        });
    }
}
