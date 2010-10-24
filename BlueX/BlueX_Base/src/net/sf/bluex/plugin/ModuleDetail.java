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
public class ModuleDetail {
    private int moduleType;
    private Vector<String> mappingExtension=new Vector<String>();

    /** these constants are used to identify the type of the plugin */

    /** plugins which associates any file extension */
    public static final int FILE_ASSOCIATION_PLUGIN=0;

    /** plugins which opens in a new window */
    public static final int NEW_WINDOW_PLUGIN=1;

    /** plugins which opens in as tab or something in a baseWindow or other */
    public static final int PANEL_BASED_PLUGIN=2;

    public ModuleDetail() {
    }

    public ModuleDetail(int moduleType) {
        this.moduleType = moduleType;
    }

    public Vector<String> getMappingExtension() {
        return mappingExtension;
    }

    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }

    public int getModuleType() {
        return moduleType;
    }

    public boolean addExtension(String extension){
        if(extension!=null && !extension.equals("")){
            mappingExtension.add(extension);
            return true;
        }
        return false;
    }

    public static String getShowableType(int type){
        String strType="";
        if(type==FILE_ASSOCIATION_PLUGIN)
            strType="File Associated Plugin";
        else if(type==NEW_WINDOW_PLUGIN)
            strType="New Window Plugin";
        else
            strType="Panel Based Plugin";

        return strType;
    }

    public static int[] getAllModuleTypes(){
        int[] moduleTypes=new int[3];
        for(int i=0; i<3; i++)
            moduleTypes[i]=i;
        
        return moduleTypes;
    }
}
