package net.sf.bluex;


import net.sf.bluex.components.monitors.InstanceMonitor;
import net.sf.bluex.components.monitors.InstanceResponseMonitor;
import net.sf.bluex.components.monitors.ThreadMonitor;
import net.sf.bluex.controller.FileModule;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import net.sf.bluex.components.BlueXStatics;
import net.sf.bluex.components.Updater;
import net.sf.bluex.exceptionManager.ErrorIcon;
import net.sf.bluex.exceptionManager.ExceptionInitializer;
/**
 *
 * @author Blue
 */
public class InitializeSoftware{
    
    private net.sf.bluex.components.SystemIcon si;

    static{
        ExceptionInitializer.registerExceptionManager(new ExceptionInitializer() {

            @Override
            public void exceptionOccurred(String message) {
                ErrorIcon.warn(message);
            }
        }, FileModule.ERROR_FILE);
    }
    
    public InitializeSoftware(){
        initLookNFeel();

        if(ensureFirstTimeCalled()){//ensure first time called
            initSystemTray();
            initMonitors();
            initBlueX();
        }
    }

    private void initBlueX(){
        //init the blueX
//        if(hasFonts()){
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new net.sf.bluex.boundary.BaseWindow(null);
                    Updater.assignAutomaticUpdater();
                }
            });
//        }
//        else{
//            JOptionPane.showMessageDialog(null,"<html><body>You have misplaced the font file provided in the folder.<br/><br/>" +
//                    "BURECS__.TTF is missing.<br/>" +
//                    "Don't panic.<br/>" +
//                    "Get it and paste them in the ECData/config folder.<br/>" +
//                    "or, Reinstall the BlueX, download it from:<br/><b>http://bluex.sourceforge.net</b><br/><br/>" +
//                    "Program is terminating.</body></html>","Terminating",JOptionPane.ERROR_MESSAGE);
//            System.exit(1);
//        }
    }
    
    private boolean ensureFirstTimeCalled(){

        File file=new File(FileModule.CONFIG_FOLDER+File.separatorChar+FileModule.INSTANCE_FILE);

        if(!file.exists()){//ensures first time called
            try{
                file.createNewFile();
                file.deleteOnExit();//delete the file when the JVM exits
            }catch(IOException ioe){
                return false;
            }
            return true;
        }
        else{//request the same instance
            //create a temporary file to get the new instance of the working BlueX
            try{
                File newInst=new File(FileModule.CONFIG_FOLDER, FileModule.NEW_INSTANCE_FILE);
                newInst.createNewFile();
                newInst.deleteOnExit();
                InstanceResponseMonitor.startMonitoring();
            }catch(IOException ioe){
                JOptionPane.showMessageDialog(null, "Request rejected. Try again.");
            }
            return false;
        }
    }
    
    private static boolean hasFonts(){
        File file=new File(FileModule.CONFIG_FOLDER+"\\BURECS__.TTF");
        if(!file.exists())
            return false;

        //if everything found then return true
        return true;
    }
    
    public static void main(String ar[]) {
//        if(ar.length==0)
            new InitializeSoftware();
//        else{
//            PluginManager.installPluginFromFile(new File(ar[0]));
//        }
    }

    private void initLookNFeel() {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(BlueXStatics.prop.getProperty("laf"));
        } catch (Exception e) {
            try {
               UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch(Exception e1){}
        }
    }

    private void initMonitors() {
        //thread monitor is started
        ThreadMonitor.startMonitoring();

        //instance monitor started
        InstanceMonitor.startMonitoring();
    }

    private void initSystemTray() {
        //show the systemTray icon
        si=new net.sf.bluex.components.SystemIcon();
        try{
            java.awt.SystemTray.getSystemTray().add(si);
        }catch(java.awt.AWTException e){
            //pass the exception
        }
    }
}