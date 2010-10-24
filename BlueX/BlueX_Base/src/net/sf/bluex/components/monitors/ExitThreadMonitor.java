package net.sf.bluex.components.monitors;

import net.sf.bluex.components.SystemIconPopupMenu;
import net.sf.bluex.threads.InterruptableThread;
import net.sf.bluex.threads.MyThread;
import net.sf.bluex.threads.UninterruptableThread;
import javax.swing.JList;

/**
 *
 * @author Blue
 */
public class ExitThreadMonitor implements Runnable {
    private InterruptableThread it;
    private JList lst;
    private boolean active;

    public ExitThreadMonitor(JList lst) {
        this.lst=lst;
        it=InterruptableThread.getMyThread(this, "Exiting BlueX Monitor");
        active=true;
        it.start();
    }

    public void stop(){
        active=false;
    }

    //set the data in the list
    private void setListData(){
        java.util.Vector<String> runningThreads=new java.util.Vector<String>();

        for(Thread t : MyThread.getRunningThreads(UninterruptableThread.class)){
                runningThreads.add(t.getName());
        }

        lst.setListData(runningThreads);

        if(runningThreads.size()==0)
            SystemIconPopupMenu.exit();
    }

    public void run() {
        while(active){

            setListData();
            
            //stop for atleast 5 seconds
            try{
                Thread.sleep(2000);
            }catch(InterruptedException ie){
                //pass
            }
        }
    }
}
