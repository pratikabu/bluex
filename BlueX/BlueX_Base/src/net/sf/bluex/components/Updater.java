/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.components;

import java.util.Properties;
import java.util.Vector;
import javax.swing.JOptionPane;
import net.sf.bluex.boundary.PluginManager;
import net.sf.bluex.controller.FileModule;
import net.sf.bluex.controller.UsefulMethods;
import net.sf.bluex.plugin.PluginMetaData;
import net.sf.bluex.plugin.Version;
import net.sf.bluex.threads.InterruptableThread;

/**
 *
 * @author Blue
 */
public class Updater implements Runnable {
    private Vector<PluginMetaData> vectDownloadedFilePlugins;

    private Vector<PluginMetaData> vectNewPlugins, vectPluginUpates;

    private InterruptableThread t;

    public void run(){
        updationStatus(true);

        vectNewPlugins=new Vector<PluginMetaData>();
        vectPluginUpates=new Vector<PluginMetaData>();
        boolean proxyError=false;
        
        try{
            String s=FileModule.SERVER_URL;
            if(UsefulMethods.downloadFile(s+"catalog.xml", FileModule.AVAILABLE_PLUGINS_FILE, UsefulMethods.HTTP_PROTOCOL)){
                vectDownloadedFilePlugins=UsefulMethods.getPluginsFromFile(FileModule.AVAILABLE_PLUGINS_FILE);
                initAvailablePlugins();
                initUpdatablePlugins();
            }else{
                proxyError=true;
                Info info=new Info("Cannot Connect to Server", "Either network connection is not available or\n" +
                        "the settings for Proxy Server is not configured.\n" +
                        "To configure please go to Tools->Options->Network");
                if(PluginManager.isManagerVisible())
                    JOptionPane.showMessageDialog(PluginManager.pm, info.getDescription(), info.getMessage(), JOptionPane.ERROR_MESSAGE);
                else
                    InfoIcon.info(info);
            }

            //bluex environment update
            if(!proxyError){
                if(UsefulMethods.downloadFile(s+"bluexversion.properties", FileModule.BLUEX_VERSION_DOWNLOADED_FILE, UsefulMethods.HTTP_PROTOCOL)){
                    //check if update is available for the bluex
                    Properties prop=UsefulMethods.getProperties(FileModule.BLUEX_VERSION_DOWNLOADED_FILE);
                    Version currentVersion=new Version(BlueXStatics.prop.getProperty("bluexversion"));
                    Version newVersion=new Version(prop.getProperty("bluexversion"));
                    if(Version.compareVersions(currentVersion, newVersion)==Version.LESSER){
                        //update found
                        if(UsefulMethods.downloadFile(s+"bluexpage.html", FileModule.BLUEX_UPDATE_PAGE, UsefulMethods.HTTP_PROTOCOL)){
                            //show the updates on the help center browser
                            HelpMapper.openHelpWithAbsolutePath(FileModule.BLUEX_UPDATE_PAGE);
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        int newPlugins=vectNewPlugins.size(), updates=vectPluginUpates.size();
        boolean showInfo=newPlugins>0 || updates>0;

        String message=null;
        if(newPlugins>0 && updates>0)
            message="New plugins and updates are available.\n" +
                    "New plugins: "+newPlugins+"\n" +
                    "Updates: "+updates;
        else if(newPlugins>0)
            message="New plugins available.\n" +
                    "New plugins: "+newPlugins;
        else if(updates>0)
            message="Updates available.\n" +
                    "Updates: "+updates;

        if(showInfo){
            Info info=new Info("Updates Available", message+"\n\nGoto Tools->Plugin Manager\nto download them.");
            if(PluginManager.isManagerVisible())
                JOptionPane.showMessageDialog(PluginManager.pm, info.getDescription(), info.getMessage(), JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, info.getDescription(), info.getMessage(), JOptionPane.INFORMATION_MESSAGE);
            InfoIcon.info(info);
        }

        updationStatus(false);
    }

    public boolean isAlive(){
        return t.isAlive();
    }

    private void initAvailablePlugins() {
        dwnload: for(PluginMetaData dwnldPMD : vectDownloadedFilePlugins){
            for(PluginMetaData instldPMD : UsefulMethods.getAllInstalledPlugins())
                if(dwnldPMD.getName().equals(instldPMD.getName()))
                    continue dwnload;

            vectNewPlugins.add(dwnldPMD);
        }
    }

    private void initUpdatablePlugins() {
        for(PluginMetaData dwnldPMD : vectDownloadedFilePlugins)
            for(PluginMetaData instldPMD : UsefulMethods.getAllInstalledPlugins())
                if(dwnldPMD.getName().equals(instldPMD.getName())){
                    if(Version.compareVersions(dwnldPMD.getVersion(), instldPMD.getVersion())==Version.GREATER)
                        vectPluginUpates.add(dwnldPMD);
                }
    }

    public void refreshDownloadedData() {
        if(t==null || !t.isAlive()){
            t=InterruptableThread.getMyThread(this, "Fetching From Server");
            t.start();
        }
    }

    public Vector<PluginMetaData> getVectDownloadedFilePlugins() {
        return vectDownloadedFilePlugins;
    }

    public Vector<PluginMetaData> getVectNewPlugins() {
        return vectNewPlugins;
    }

    public Vector<PluginMetaData> getVectPluginUpates() {
        return vectPluginUpates;
    }

    public static void assignAutomaticUpdater(){
        boolean checkPlugins=Boolean.parseBoolean(BlueXStatics.prop.getProperty("checkPlugins"));
        if(checkPlugins)
            new Updater().refreshDownloadedData();
    }

    private void updationStatus(boolean b) {
        if(PluginManager.isManagerVisible())
            PluginManager.updateStatus(b);
    }
}
