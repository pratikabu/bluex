/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 *
 * @author Blue
 */
public class SavingList extends LinkedHashSet<String> {

    public SavingList(Collection<? extends String> c) {
        super(c);
    }

    public SavingList() {
    }

    public SavingList(int initialCapacity) {
        super(initialCapacity);
    }

    public SavingList(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    public void load(InputStream is){
        BufferedReader br=null;
        try{
            br=new BufferedReader(new InputStreamReader(is));
            String line=null;
            while((line=br.readLine())!=null)
                add(line);
        }catch(Exception e){
        }finally{
            try{
                br.close();
            }catch(Exception e){}
        }
    }

    public void load(File file){
        try{
            load(new FileInputStream(file));
        }catch(Exception e){}
    }

    public void store(OutputStream os){
        PrintWriter pw=null;
        try{
            pw=new PrintWriter(os);

            for(String p : this)
                pw.println(p.toString());
        }catch(Exception e){
        }finally{
            pw.close();
        }
    }

    public void store(File file){
        try{
            store(new FileOutputStream(file));
        }catch(Exception e){}
    }
}
