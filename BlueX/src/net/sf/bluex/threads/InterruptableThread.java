
package net.sf.bluex.threads;

/**
 * These threads does not affects the system while the system is closing and can be
 * interrupted inbetween.
 * @author Blue
 */
public class InterruptableThread extends MyThread{
    
    private InterruptableThread(Runnable target, String name){
        super(target, name);
    }
    
    public static InterruptableThread getMyThread(Runnable target, String name){
        InterruptableThread t=new InterruptableThread(target, name);
        
        //add this thread to the vector
        addThread(t);
        
        return t;
    }
}
