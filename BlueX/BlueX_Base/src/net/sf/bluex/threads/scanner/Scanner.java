/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.threads.scanner;

import java.io.File;

/**
 * this interface must be implemented by any scanning algorithm
 * @author Blue
 */
public interface Scanner {

    /**
     * this method starts the thread for scanning
     */
    public void start();

    /**
     * this method stops it, and frees any resources opened
     */
    public void stop();

    /**
     * thim method pauses the thread
     */
    public void pause();

    /**
     * this method again resumes the thread
     */
    public void resume();

    /**
     * This method is called for every file and directory.
     * @param file
     * @param isFile
     */
    public void processFile(java.io.File file, boolean isFile);

    /**
     * This method is called for the directory on which the scanning is running.
     * @param parentFolder
     */
    public void processFolder(File parentFolder);

    /**
     * this method is called when the processing is finished
     */
    public void processCompleted();
}
