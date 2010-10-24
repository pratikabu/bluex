/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.controller;

import java.io.File;
import java.util.Vector;
import net.sf.bluex.boundary.BaseWindow;
import net.sf.bluex.components.BlueXStatics;
import net.sf.bluex.threads.InterruptableThread;

/**
 *
 * @author Blue
 */
public class BaseWindowManagement implements Runnable {
    private InterruptableThread t;

    private static Vector<BaseWindow> vectBW = new Vector<BaseWindow>();

    private BaseWindow bw;
    private File[] updatedFiles;
    private int updationType;

    private static int threadCount;

    public static final int NEW_ADDITION=1, DELETE=2, RENAMED=3;

    private BaseWindowManagement(BaseWindow bw, File[] updatedFiles, int updationType) {
        this.bw = bw;
        this.updatedFiles = updatedFiles;
        this.updationType=updationType;

        //create the thread
        synchronized(this){//for the thread count variable
            t=InterruptableThread.getMyThread(this, "BaseWindowManagement Thread #"+(++threadCount));
            t.start();
        }
    }

    public synchronized static void registerBaseWindow(BaseWindow bw){
        vectBW.add(bw);
    }

    public synchronized static void deregisterBaseWindow(BaseWindow bw) {
        vectBW.removeElement(bw);
    }

    public static Vector<BaseWindow> getAllInstances(){
        return vectBW;
    }

    public static int getBlueXInstanceCount(){
        return vectBW.size();
    }

    public static void dataUpdated(File[] files, BaseWindow bw, int updationType){
        new BaseWindowManagement(bw, files, updationType);
    }

    public void run() {
        synchronized(this){
            //do the job of updating the updated data on the windows :)
            if(updationType==RENAMED){
                //renamed job
                //favorites
                BlueXStatics.fav.remove(updatedFiles[1].getAbsolutePath());//first remove it then add it
                Vector<File> temp=new Vector<File>();
                temp.add(updatedFiles[0]);
                bw.getExplorer().addToFavorites(temp);
                
                for(BaseWindow b : vectBW){
                    b.getExplorer().initFavorites();
                    if(b.getExplorer().getAV().getCurrentURI()==null && bw.getExplorer().getAV().getCurrentURI()==null)
                        b.getExplorer().getAV().refresh();
                    else if(bw.getExplorer().getAV().getCurrentURI()!=null && b.getExplorer().getAV().getCurrentURI()==null){
                        //just pass
                    }else if(bw.getExplorer().getAV().getCurrentURI()==null && b.getExplorer().getAV().getCurrentURI()!=null){

                    }else if(b.getExplorer().getAV().getCurrentURI().equals(bw.getExplorer().getAV().getCurrentURI()))
                        b.getExplorer().getAV().refresh();
                }
            }else if(updationType==NEW_ADDITION){
                //new addition job
                for(BaseWindow b : vectBW){
                    if(b.getExplorer().getAV().getCurrentURI()==null && bw.getExplorer().getAV().getCurrentURI()==null)
                        b.getExplorer().getAV().refresh();
                    else if(bw.getExplorer().getAV().getCurrentURI()!=null && b.getExplorer().getAV().getCurrentURI()==null){
                        //just pass
                    }else if(bw.getExplorer().getAV().getCurrentURI()==null && b.getExplorer().getAV().getCurrentURI()!=null){

                    }else if(b.getExplorer().getAV().getCurrentURI().equals(bw.getExplorer().getAV().getCurrentURI()))
                        b.getExplorer().getAV().refresh();
                }
            }else{
                //deleted job
                //init the favorites
                for(File file : updatedFiles)
                    BlueXStatics.fav.remove(file.getAbsolutePath());
                BlueXStatics.fav.store(new File(FileModule.FAVORITES_FILE));

                for(BaseWindow b : vectBW){
                    b.getExplorer().initFavorites();
                    if(b.getExplorer().getAV().getCurrentURI()==null && bw.getExplorer().getAV().getCurrentURI()==null)
                        b.getExplorer().getAV().refresh();
                    else if(bw.getExplorer().getAV().getCurrentURI()!=null && b.getExplorer().getAV().getCurrentURI()==null){
                        //just pass
                    }else if(bw.getExplorer().getAV().getCurrentURI()==null && b.getExplorer().getAV().getCurrentURI()!=null){
                        
                    }else if(b.getExplorer().getAV().getCurrentURI().equals(bw.getExplorer().getAV().getCurrentURI()))
                        b.getExplorer().getAV().refresh();
                }
            }

            //decrement the thread count
            threadCount--;
        }
    }
}
