package net.sf.bluex.parser;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Vector;
import net.sf.bluex.components.FileAssociation;
import net.sf.bluex.controller.FileModule;

/**
 *
 * @author Blue
 */
public class FileAssociationSavingAlgo {
    private Vector<FileAssociation> vectFA=new Vector<FileAssociation>();
    private PrintWriter pw;

    public FileAssociationSavingAlgo() {
    }

    public FileAssociationSavingAlgo(Vector<FileAssociation> vectFA) {
        this.vectFA = vectFA;
    }

    public Vector<FileAssociation> getVectFA() {
        return vectFA;
    }

    public void addAssoication(FileAssociation fa){
        if(vectFA!=null)
            vectFA.add(fa);
    }

    public boolean save(){
        boolean saved=false;
        try{
            File file=new File(FileModule.FILE_ASSOCIATION_DB);
            pw=new PrintWriter(file);
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<!--");
            pw.println("Document    : "+file.getName());
            pw.println("Last Updated: "+new Date());
            pw.println("Author      : BlueX- Plugin API");
            pw.println("-->");
            pw.println("<file-association>");
            for(FileAssociation fa : vectFA){
                pw.println("<association>");
                pw.println("<plugin-name>"+fa.getPluginName()+"/plugin-name");
                pw.println("<extension>"+fa.getExtension()+"/extension");
                pw.println("</association>");
            }
            pw.println("</file-association>");
            saved=true;
        }catch(Exception e){
        }finally{
            pw.close();
        }

        return saved;
    }
}
