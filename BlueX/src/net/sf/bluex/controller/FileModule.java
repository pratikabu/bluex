package net.sf.bluex.controller;

import net.sf.bluex.components.BlueXStatics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author Blue
 */
public class FileModule {

    /////////////////////////folder paths
    //public folder path
    public static final String FOLDER_PATH="ECData";

    //config folder path. all configuration files will be saved here
    public static String CONFIG_FOLDER=FOLDER_PATH+File.separatorChar+"config";

    //settings file location
    public static final String CONFIGURATION_FILE=CONFIG_FOLDER+File.separatorChar+"configuration.properties";

    //Favorites file
    public static final String FAVORITES_FILE=CONFIG_FOLDER+File.separatorChar+"favorites.fav";

    //file type manager file
    public static final String FILE_ASSOCIATION_DB=CONFIG_FOLDER+File.separatorChar+"fileAssociation.db";

    //update folder where all the updates resides temporarily
    public static final String DOWNLOAD_FOLDER=CONFIG_FOLDER+File.separatorChar+"downloads";

    //update folder where all the extracted files of the plugins resides. the content may be deleted by the temp files removing thread
    public static final String EXTRACTED_TEMP_FOLDER=DOWNLOAD_FOLDER+File.separatorChar+"temp";

    //plugin folder. where plugins will be saved
    public static final String PLUGIN_FOLDER=FOLDER_PATH+File.separatorChar+"plugins";

    //shared library
    public static final String SHARED_FOLDER=FOLDER_PATH+File.separatorChar+"shared";

    //dependencies folder
    public static final String DEPENDENCIES_FOLDER=PLUGIN_FOLDER+File.separatorChar+"dependencies";

    //help main folder
    public static final String HELP_FOLDER=FOLDER_PATH+File.separatorChar+"help";

    ////////////////////files path

    //icons folder location
    public static final String ICON_FOLDER=FOLDER_PATH+File.separatorChar+"icons"+File.separatorChar+"iconSet";

    //the icon of the BlueX
    public static final String APP_ICON=ICON_FOLDER+1+File.separatorChar+"exit.gif";
    
    //the virus database
    private static final String DATABASE_FILE=CONFIG_FOLDER+File.separatorChar+"database.txt";
    
    //temp instance file name
    public static final String INSTANCE_FILE="instance";

    //temp new instance file name
    public static final String NEW_INSTANCE_FILE="newInstance";

    //error file location
    public static final String ERROR_FILE=CONFIG_FOLDER+File.separatorChar+"error.xml";
    
    //plugin manager file
    public static final String PLUGINS_DB=CONFIG_FOLDER+File.separatorChar+"plugins.xml";

    //available plugins file from the server
    public static final String AVAILABLE_PLUGINS_FILE=FileModule.DOWNLOAD_FOLDER + File.separatorChar + "AvailablePlugins.xml";

    //bluex update
    public static final String BLUEX_VERSION_DOWNLOADED_FILE=FileModule.DOWNLOAD_FOLDER + File.separatorChar + "bluexversion.properties";

    //bluex page
    public static final String BLUEX_UPDATE_PAGE=FileModule.DOWNLOAD_FOLDER + File.separatorChar + "bluexpage.html";
    
    private static int addCount;
    
    public static final String SERVER_URL="http://bluex.sourceforge.net/plugins/";
    
    public static ArrayList<String> getList(){
        ArrayList<String> al=new ArrayList<String>();
        try{
            FileInputStream fis=new FileInputStream(DATABASE_FILE);
            int c;
            StringBuffer sb=new StringBuffer("");
            while((c=fis.read())!=-1){
                if(c!='|')
                    sb.append((char)c);
                else{
                    al.add(sb.toString());
                    sb=new StringBuffer("");
                }
            }
        }catch(Exception e){
            System.out.println("IOException Occurred.\n"+e);
        }
        return al;
    }
    
    public static void writeFile(JFrame f,String fileName){
        try{
            ArrayList<String> savedFiles=getList();
            boolean write=true;
            for(String tempFile : savedFiles){
                if((tempFile+"|").equalsIgnoreCase(fileName)){
                    write=false;
                    JOptionPane.showMessageDialog(f,"File Name: "+fileName.substring(0,fileName.length()-1)+
                            "\n\nThis file is already added in database.","Summary",JOptionPane.OK_OPTION);
                    break;
                }
            }
            if(write){
                FileOutputStream fos=new FileOutputStream(DATABASE_FILE,true);
                byte b[]=fileName.getBytes();
                fos.write(b);
                fos.close();
                addCount++;
            }
        }catch(IOException ioe){
            javax.swing.JOptionPane.showMessageDialog(f,"Input Output Exception occurred. The file name does not copied.","Exception",javax.swing.JOptionPane.OK_OPTION);
        }
    }
    
    public static void writeLumpFiles(JFrame f,String fileName[]){
        int i;
        for(i=0;i<fileName.length;i++)
            writeFile(f,fileName[i]);
        javax.swing.JOptionPane.showMessageDialog(f, addCount+" files are Successfully added to database.");
    }
    
    public static boolean writeObject(Object obj,String filePath){
        try{
            FileOutputStream fos=new FileOutputStream(filePath);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
            fos.close();
            return true;
//            MsgBox.showMessageDialog(null,"Drives Information successfully stored.","Successful",MsgBox.INFORMATION_MESSAGE);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error in saving file.\n","Error",JOptionPane.OK_OPTION);
            return false;
        }
    }
    
    public static Object getObject(String filePath){
        Object obj=null;
        try{
            FileInputStream fis=new FileInputStream(filePath);
            ObjectInputStream ois=new ObjectInputStream(fis);
            obj=ois.readObject();
            ois.close();
            fis.close();
        }catch(Exception e){
        }
        return obj;
    }

    public static String getIconFolderWithCurrentSet(){
        return BlueXStatics.prop.getProperty("iconSet");
    }
}
