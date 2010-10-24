package net.sf.bluex.components.monitors;

import net.sf.bluex.threads.InterruptableThread;
import net.sf.bluex.threads.MyThread;
import javax.swing.JList;

/**
 *
 * @author Blue
 */
public class ThreadProgressMonitor implements Runnable {
    private InterruptableThread it;
    private JList lst;
    private boolean active;

    public ThreadProgressMonitor(JList lst) {
        this.lst=lst;
        this.it=InterruptableThread.getMyThread(this, "Thread Progress Monitor");
        active=true;
        this.it.start();
    }

    public void stop(){
        active=false;
    }

    //set the data in the list
    private void setListData(){
        java.util.Vector<String> runningThreads=new java.util.Vector<String>();

        for(Thread t : MyThread.getRunningThreads()){
                runningThreads.add(t.getName());
        }

        //set the row where it was previously or do not set
        int row=lst.getSelectedIndex();

        lst.setListData(runningThreads);

        if(row!=-1){
            try{
                lst.setSelectedIndex(row);
            }catch(Exception eh){
                //pass
            }
        }
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
