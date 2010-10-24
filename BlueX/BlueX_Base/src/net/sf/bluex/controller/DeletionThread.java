/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.controller;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Blue
 */
public class DeletionThread implements Runnable{
    private net.sf.bluex.threads.UninterruptableThread t;
    private ArrayList<File> arrFiles;
    
    public DeletionThread(ArrayList<File> arrFiles){
        this.arrFiles=arrFiles;
        t=net.sf.bluex.threads.UninterruptableThread.getMyThread(this, "Virus Deletion Thread");
        t.start();
    }
    
    public void run(){
        int countSuccessFulDelete=0;
        
        for(java.io.File tempFile : arrFiles){
            if(tempFile.delete())
                countSuccessFulDelete++;
        }
        
        if(arrFiles.size()!=0)
            javax.swing.JOptionPane.showMessageDialog(null, "Files selected for deletion: "+arrFiles.size()+"\n\n" +
                    "Files Deleted Successfully: "+countSuccessFulDelete);
    }
}
