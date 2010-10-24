/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.components;

import net.sf.bluex.controller.FileModule;
import java.awt.Color;
import java.io.File;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.swing.Icon;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;
import net.sf.bluex.controller.UsefulMethods;

/**
 * This class is responsible for the OS detection and the necessary operations for particular OS
 * @author Blue
 */
public class BlueXStatics {
    private static int os;
    public static Properties prop;

    public static FileSystemView fsv;

    public static SavingList fav;

    public static Color hiddenColor;
    public static Color tableBackground=java.awt.Color.WHITE;

    /** The File Association property object */
    public static Properties fa;

    public static Proxy proxy;

    static {
        //detect OS
        String osName = System.getProperty("os.name");
        if (File.separatorChar == '\\')
            os = 1;
        else if (File.separatorChar == '/')
            os = 2;
        else
            os = 3;

        //fetch configuration file
        prop=UsefulMethods.getProperties(FileModule.CONFIGURATION_FILE);

        //get the fsv
        fsv=FileSystemView.getFileSystemView();

        //favorites
        fav=new SavingList();
        fav.load(new File(FileModule.FAVORITES_FILE));

        //set hidden color
        hiddenColor=new Color(Integer.parseInt(prop.getProperty("hiddenColor")));

        loadFA();

        //proxy settings
        initProxy();
    }

    public static boolean isWindowsOS(){
        return os==1;
    }

    public static boolean isUnix(){
        return os==2;
    }

    public static final boolean isGenericOS() {
	return os == 3;
    }

    public static String getRunningCommand(){
        String runningCommand=null;
        if(isWindowsOS())
            runningCommand="rundll32 SHELL32.DLL,ShellExec_RunDLL";

        return runningCommand;
    }

    public static void saveProperties(){
        UsefulMethods.saveProperties(prop, FileModule.CONFIGURATION_FILE);
    }

    public static Icon getIcon(File file){
        if(file!=null){
            Icon icon=null;
            try{
                icon=fsv.getSystemIcon(file);
            }catch(Throwable e){
            }
            if (icon == null) {
                if (file.isDirectory()) {
                    icon = (Icon) UIManager.get("FileView.directoryIcon");
                } else {
                    icon = (Icon) UIManager.get("FileView.fileIcon");
                }
            }
            return icon;
        }else//roots identified :)
            return UsefulMethods.getIcon("exit.gif");
    }

    public static String getFileName(File file){
        String fn=null;
        if(file!=null){
            fn=fsv.getSystemDisplayName(file);
            if(fn.isEmpty())
                fn=file.getAbsolutePath();

            return fn;
        }else//roots identified :)
            return "Roots";
    }

    public static String getFileDescription(File file){
        String description="Unknown";
        try{
            description=BlueXStatics.fsv.getSystemTypeDescription(file);
        }catch(Throwable t){}
        return description;
    }

    public static void loadFA() {
        //file asscociation
        fa=UsefulMethods.getProperties(FileModule.FILE_ASSOCIATION_DB);
    }

    public static void initProxy() {
        proxy=null;
        boolean proxyEnabled=Boolean.parseBoolean(prop.getProperty("proxyEnabled"));

        try{
            if(proxyEnabled){
                proxy=new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
                        Inet4Address.getByAddress(convertStringIPToBytes(prop.getProperty("host"))), Integer.parseInt(prop.getProperty("port"))));
            }
        }catch(Exception e){}
    }

    private static byte[] convertStringIPToBytes(String ip){
        byte[] bip=new byte[4];
        StringTokenizer st=new StringTokenizer(ip, ".");
        int i=0;
        while(st.hasMoreTokens())
            bip[i++]=(byte)Integer.parseInt(st.nextToken());
        return bip;
    }
}
