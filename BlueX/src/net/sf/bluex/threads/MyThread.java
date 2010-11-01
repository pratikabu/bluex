package net.sf.bluex.threads;

import javax.swing.JOptionPane;
import net.sf.bluex.controller.BaseWindowManagement;
import net.sf.bluex.boundary.ExitUniterruptableThreadsMonitor;
import net.sf.bluex.controller.FileModule;
import net.sf.bluex.components.SystemIcon;

/**
 *
 * @author Blue
 */
public abstract class MyThread extends Thread{
    
    //private static vector which holds the total number of threads which are given
    //vital resource, should be cared of multiple threads
    private static java.util.Vector<MyThread> vectThreads=new java.util.Vector<MyThread>();

    //
    public static String AGENT_SCANNING_THREAD="Agent Scanning Thread";


    protected MyThread(Runnable target, String name){
        super(target, name);
    }

    /**
     * Synchronized methods so that no redundancies may arise.
     * @param t
     */
    public synchronized static void addThread(MyThread t){
        vectThreads.add(t);
    }

    /**
     * Synchronized methods so that no redundancies may arise.
     * @param t
     */
    public synchronized static void removeThread(MyThread t){
        vectThreads.removeElement(t);
    }

    /**
     * Synchronized methods so that no redundancies may arise.
     * This method will check that whether all threads have been successfully completed or not.
     * @return
     */
    public synchronized static boolean ensureThreadsCompleted(){
        for(Thread t : vectThreads){
            if(t instanceof UninterruptableThread && t.isAlive())
                return false;
        }

        return true;
    }

    /**
     * Synchronized methods so that no redundancies may arise.
     * gives all running threads
     * @return
     */
    public synchronized static java.util.Vector<Thread> getRunningThreads() {
        java.util.Vector<Thread> runningVect=new java.util.Vector<Thread>();

        for(Thread t : vectThreads){
            if(t.isAlive())
                runningVect.add(t);
        }

        return runningVect;
    }

    /**
     * Synchronized methods so that no redundancies may arise.
     * gives all running threads of type c
     * @param c
     * @return
     */
    public synchronized static java.util.Vector<Thread> getRunningThreads(Class c) {
        java.util.Vector<Thread> runningVect=new java.util.Vector<Thread>();

        for(Thread t : vectThreads){
            if(c.isInstance(t) && t.isAlive())
                runningVect.add(t);
        }

        return runningVect;
    }

    /**
     * Public static method to exit from the system
     */
    public static void exit(SystemIcon si){
        boolean exit=true;

        if(!ensureThreadsCompleted()){
            exit=false;

            //show running threads
            exit=ExitUniterruptableThreadsMonitor.showRunningThreads(null);
        }

        if(exit){
            if(BaseWindowManagement.getBlueXInstanceCount()>1){
                int choice=JOptionPane.showConfirmDialog(null, "Multiple windows of BlueX are running.\n" +
                        "Are you sure you want to exit?", "Sure Exit?", JOptionPane.YES_NO_OPTION);
                exit=choice==JOptionPane.YES_NO_OPTION;
            }
        }

        if(exit){
            java.awt.SystemTray.getSystemTray().remove(si);
            java.io.File file=new java.io.File(FileModule.INSTANCE_FILE);
            file.delete();

            System.exit(0);
        }
    }
}
