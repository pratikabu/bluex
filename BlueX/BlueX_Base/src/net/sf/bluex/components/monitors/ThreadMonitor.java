package net.sf.bluex.components.monitors;

import net.sf.bluex.threads.InterruptableThread;
import net.sf.bluex.threads.MyThread;
import java.util.Vector;

/**
 *
 * @author Blue
 */
public class ThreadMonitor implements Runnable {
    private InterruptableThread it;

    public static void startMonitoring(){
        ThreadMonitor tvm=new ThreadMonitor();
        tvm.it=InterruptableThread.getMyThread(tvm, "Thread Monitor");
        tvm.it.start();
    }

    public void run() {
        while(true){
            //get all running threads
            Vector<Thread> vect=MyThread.getRunningThreads();

            //remove the dead thread
            for(Thread t : vect)
                if(!t.isAlive())
                    MyThread.removeThread((MyThread)t);
            
            //stop for atleast 5 seconds
            try{
                Thread.sleep(5000);
            }catch(InterruptedException ie){
                //pass
            }
        }
    }
}
