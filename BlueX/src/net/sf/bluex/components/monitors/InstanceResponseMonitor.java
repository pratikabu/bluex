package net.sf.bluex.components.monitors;

import net.sf.bluex.InitializeSoftware;
import net.sf.bluex.controller.FileModule;
import net.sf.bluex.threads.InterruptableThread;
import java.io.File;

/**
 *
 * @author Blue
 */
public class InstanceResponseMonitor implements Runnable {

    public static void startMonitoring(){
        InstanceResponseMonitor tvm=new InstanceResponseMonitor();

        tvm.run();
    }

    public void run() {
        //create the new instance file reference
        File newInst=new File(FileModule.CONFIG_FOLDER, FileModule.NEW_INSTANCE_FILE);

        //wait for atleast 5 seconds
        try{
            Thread.sleep(5000);
        }catch(InterruptedException ie){
            //pass
        }

        //now check if the file is deleted than it is ok else create a new instance of bluex
        if(newInst.exists()){
            //create a new instance by deleting the instance File
            File file=new File(FileModule.CONFIG_FOLDER+File.separatorChar+FileModule.INSTANCE_FILE);
            file.delete();
            //delete the newInstance request file
            newInst.delete();

            //create the instance
            new InitializeSoftware();
        }
    }
}
