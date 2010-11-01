/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.components;

import java.io.BufferedReader;
import net.sf.bluex.boundary.HelpWindow;
import net.sf.bluex.controller.FileModule;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Blue
 */
public class HelpMapper {
    public static void showHelpFor(String helpFolderName){
        String helpFile=null;
        if(helpFolderName!=null){
            helpFile=FileModule.HELP_FOLDER+File.separatorChar+helpFolderName+File.separatorChar+"index.html";
            openHelpWithAbsolutePath(helpFile);
        }
    }

    public static void showHelpHome(){
        //generate help home
        createHomePage();
        openHelpWithAbsolutePath("");
    }

    public static void openHelpWithAbsolutePath(String path){
        HelpWindow.showHelpWindow(path);
    }

    public static void openURL(String urlSTR){
        try{
            URL url=new URL(urlSTR);
            HelpWindow.showHelpWindow(url);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Cannot open this url. The url is malformed.", "Cannot Open", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void createHomePage(){
        File[] files=new File(FileModule.HELP_FOLDER).listFiles();
        Vector<String> vectHelpNames=new Vector<String>();
        for(File file : files)
            if(file.isDirectory())
                if(!file.getName().equals("index_files"))
                    vectHelpNames.add(file.getName());

        PrintWriter pw=null;
        FileReader fr1=null;
        BufferedReader br1=null;
        FileReader fr2=null;
        BufferedReader br2=null;
        try{
            pw=new PrintWriter(FileModule.HELP_FOLDER+File.separatorChar+"index.html");
            fr1=new FileReader(FileModule.HELP_FOLDER+File.separatorChar+"index1.html");
            fr2=new FileReader(FileModule.HELP_FOLDER+File.separatorChar+"index2.html");
            br1=new BufferedReader(fr1);
            br2=new BufferedReader(fr2);

            String line;
            while((line=br1.readLine())!=null)
                pw.println(line);
            for(String help : vectHelpNames)
                pw.println("-<a href=\""+help+"/index.html\">"+help+" Help</a><br/>");
            while((line=br2.readLine())!=null)
                pw.println(line);
        }catch(Exception e){
        }finally{
            pw.flush();
            pw.close();
            try{
                br1.close();
                fr1.close();
            }catch(Exception e){}
            try{
                br2.close();
                fr2.close();
            }catch(Exception e){}
        }
    }
}
