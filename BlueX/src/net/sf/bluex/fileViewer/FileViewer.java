
package net.sf.bluex.fileViewer;

import java.io.File;
import java.util.Vector;

/**
 * This interface must be implemented by the class who wants to display folder contents
 * @author Blue
 */
public interface FileViewer{

    public void back();
    
    public void forward();
    
    public void up();
    
    public void cut();

    public void copy();
    
    public void paste();
    
    public void delete();
    
    public void refresh();
    
    public void requestOSToRun();
    
    public void newFolder();
    
    public void rename();

    public void showProperties();

    /**
     * this method is used to update the contents of the file viewer object
     * it has an URI object as input and then it updates the viewer.
     * It should return true when the uri is successfully opened else false.
     * @param uri
     * @return
     */
    public boolean updateContents(File file);

    /**
     * get the selected files from the viewer
     * @return
     */
    public Vector<File> getSelectedFiles();
}
