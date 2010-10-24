
package net.sf.bluex.threads;

/**
 * Thread class so that the progress can be checked when the system is closing itself.
 * @author Blue
 */
public class UninterruptableThread extends MyThread{
    
    private UninterruptableThread(Runnable target, String name){
        super(target, name);
    }
    
    public static UninterruptableThread getMyThread(Runnable target, String name){
        UninterruptableThread t=new UninterruptableThread(target, name);
        
        //add this thread to the vector
        addThread(t);
        
        return t;
    }
}
