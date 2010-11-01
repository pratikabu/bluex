package net.sf.bluex.components.monitors;

import net.sf.bluex.controller.FileModule;
import net.sf.bluex.threads.InterruptableThread;
import java.io.File;

/**
 *
 * @author Blue
 */
public class InstanceMonitor implements Runnable {
    private InterruptableThread it;

    public static void startMonitoring(){
        InstanceMonitor tvm=new InstanceMonitor();
        tvm.it=InterruptableThread.getMyThread(tvm, "Instance Monitor");
        tvm.it.start();
    }

    public void run() {
        //create the new instance file reference
        File newInst=new File(FileModule.CONFIG_FOLDER, FileModule.NEW_INSTANCE_FILE);

        while(true){

            if(newInst.exists()){//a new request has been arrived, open a new BlueX window in the same instance
                //delete the request file
                newInst.delete();

                //create new instance
                new net.sf.bluex.boundary.BaseWindow(null);
            }
            
            //stop for atleast 2 seconds
            try{
                Thread.sleep(2000);
            }catch(InterruptedException ie){
                //pass
            }
        }
    }
}
