
package net.sf.bluex.threads.scanner;

import net.sf.bluex.components.Stack;
import net.sf.bluex.controller.FolderIntruder;
import net.sf.bluex.threads.UninterruptableThread;
import java.io.File;

/**
 * The Breadth First Search Algorithm for files only.
 * @author Blue
 */
public abstract class BFS implements Scanner, Runnable{
    /**
     * private members for functionality
     */
    //private stack of scan basket to hold the scan basket
    private Stack<File> stScanBasket;

    //private boolean for starting/stoping and pausing/resuming the thread
    private boolean active;
    private boolean paused;

    private String threadName;

    //thread object
    private UninterruptableThread t;
    
    /**
     * 
     * @param threadName
     */
    public BFS(String threadName) {
        this.threadName=threadName;
    }

    /**
     * my methods @public
     */

    public void setStackContents(Stack<File> stScanBasket) {
        //init scan basket object
        this.stScanBasket=stScanBasket;
    }

    @Override
    public void run(){
        doScanning();
    }

    @Override
    public void start(){
        active=true;

        t=UninterruptableThread.getMyThread(this, threadName);
        t.start();
    }

    @Override
    public void stop(){
        active=false;

        paused=false;
    }

    @Override
    public void pause(){
        paused=true;
    }

    @Override
    public void resume(){
        paused=false;
    }

    private void doScanning(){
        //do scan operation
        /** parentFolder */
        File parentFolder=null;

        while(active && (parentFolder=stScanBasket.pop())!=null){
            //create a instance of files object which contains all files and folders for corresoponding parent folder
            File[] file=FolderIntruder.getFilesFolders(parentFolder);

            //this method contains the current folder to be scanned
            processFolder(parentFolder);

            //if the file object is not null then go inside else outside return.
            if(file!=null){
                //do the job of iteration
                for(File tempFile : file){
                    boolean isDir=tempFile.isDirectory();

                    //now call process files if startScanning is true
                    if(active){
                        //call to process file method
                        processFile(tempFile, !isDir);

                        //add to stack the tempFile if it is a directory for intrusion
                        if(isDir)
                            stScanBasket.push(tempFile);
                    }else
                        break;

                    //pause the thread
                    while(paused);
                }
            }
            if(!active)
                t=null;
        }
        stop();
        processCompleted();
    }
}
