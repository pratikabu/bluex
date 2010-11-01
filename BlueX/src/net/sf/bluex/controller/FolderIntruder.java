/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.bluex.controller;

import net.sf.bluex.components.Stack;
import java.io.File;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Blue
 */
public class FolderIntruder {

    /**
     * static member of int type for getFolderContents method
     */
    public static final int FOLDER = 1, FILES = 2;

    /**
     * This class will give all the folders and files for a particular File object passed to its method
     * do make the entry in the stack if you are implementing it for folder scanner task
     */
    public static File[] getFilesFolders(Stack<File> st) {
        //create tempobject of stack top item
        File parentFolder = st.pop();
        //create an array of file objects for parent Folder
        File[] file = null;
        try {
            file = parentFolder.listFiles();
        } catch (SecurityException se) {
            JOptionPane.showMessageDialog(null, "Security Option is occrred. Path: " + parentFolder.getAbsolutePath() + " is Password protected.");
        }

        //return all files
        return file;
    }

    /**
     * it checks for security exception too and returns null if exists.. else returns the array of files
     * @param parentFolder
     * @return
     */
    public static File[] getFilesFolders(File parentFolder) {
        //create an array of file objects for parent Folder
        File[] file = null;
        try {
            file = parentFolder.listFiles();
            return file;
        } catch (SecurityException se) {
            JOptionPane.showMessageDialog(null, "Security Option is occrred. Path: " + parentFolder.getAbsolutePath() + " is Password protected.");
        }

        //return all files
        return null;
    }

    /**
     * This class will give all the folders for a particular File object passed to its method
     */
    public static Vector<File> getFolderContents(File parentFolder, int mode) {
        //create an array list object
        Vector<File> vecData = new Vector<File>();
        //create an array of file objects for parent Folder
        File[] file = null;
        try {
            file = parentFolder.listFiles();

            //if no security exception occurred then go downwards
            for (File tempFile : file) {
                //check whether file or folder

                if (tempFile.isFile()) {
                    if (mode == FILES) {
                        //add to arraylist if mode=2 else just pass through it
                        vecData.add(tempFile);
                    }
                } else if (tempFile.isDirectory()) {
                    if (mode == FOLDER) {
                        //add to arraylist if mode=1 else just pass through it
                        vecData.add(tempFile);
                    }
                }
            }

        } catch (SecurityException se) {
            JOptionPane.showMessageDialog(null, "Security Option is occrred. Path: " + parentFolder.getAbsolutePath() + " is Password protected.");
        }

        //return contents
        return vecData;
    }
}
