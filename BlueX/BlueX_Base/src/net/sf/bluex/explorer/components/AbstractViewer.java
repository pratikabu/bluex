/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.explorer.components;

import net.sf.bluex.explorer.boundary.DeleteFrame;
import net.sf.bluex.explorer.boundary.NewFolderDialog;
import net.sf.bluex.explorer.boundary.PropertiesDialog;
import net.sf.bluex.explorer.boundary.RenameDialog;
import net.sf.bluex.plugin.MyPanel;
import net.sf.bluex.components.Stack;
import net.sf.bluex.controller.UsefulMethods;
import net.sf.bluex.explorer.boundary.CopyFrame;
import net.sf.bluex.fileViewer.FileViewer;
import java.awt.Toolkit;
import java.io.File;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import net.sf.bluex.boundary.BaseWindow;
import net.sf.bluex.components.BlueXStatics;
import net.sf.bluex.controller.BaseWindowManagement;

/**
 *
 * @author Blue
 */
public abstract class AbstractViewer extends MyPanel implements FileViewer{
    /**
     * uri objec of the current file
     */
    protected File currentFile;

    //Stack object
    private Stack<StackEntry> stTraverse=new Stack<StackEntry>();

    //copy datastructure
    private Vector<File> copyDS;
    
    protected BaseWindow bw;

    protected int count=0, hidden=0;

    protected boolean showHidden;

    public AbstractViewer(BaseWindow bw){
        this.bw=bw;
    }

    public void back() {
        StackEntry file=stTraverse.getBackItem();
        updateContents(file.getFile());
//        setSelectedRow(file.getPos());
    }

    public void forward() {
        StackEntry file=stTraverse.getForwardItem();
        updateContents(file.getFile());
//        setSelectedRow(file.getPos());
    }

    public void up() {
        //make a new file object using this string path
        File parentFile=currentFile.getParentFile();

        goToURI(parentFile);
    }

    public void cut() {
    }

    public void copy() {
        //place the selected urls in the copy datastructure
        copyDS=getSelectedFiles();
        UsefulMethods.copy(copyDS);
    }

    public void paste() {
        Vector<File> selectedFiles=getSelectedFiles();
        if(selectedFiles!=null){
            File file=getSelectedFile();
            
            if(file!=null)
                CopyFrame.showCopyProgressDialog(file, this.getBw());
        }
        else
            CopyFrame.showCopyProgressDialog(currentFile, this.getBw());
        System.out.println("pasted");
    }

    public boolean hasPasteItems(){
        return Toolkit.getDefaultToolkit().getSystemClipboard().isDataFlavorAvailable(java.awt.datatransfer.DataFlavor.javaFileListFlavor);
    }

    public void paste(File file){
        if(file!=null)
            CopyFrame.showCopyProgressDialog(file, this.getBw());
    }

    public void delete() {
        Stack<File> stDelete=new Stack<File>();

        for(File file : getSelectedFiles())
            stDelete.push(file);

        new DeleteFrame(stDelete, this.getBw());
    }

    public void refresh() {
        updateContents(currentFile);
        setFocus();
    }

    public void requestOSToRun() {
        File file=getSelectedFile();
        if(file!=null)
            UsefulMethods.requestOSToRun(file);

        setFocus();
    }

    public void newFolder() {
        if(currentFile!=null)
            new NewFolderDialog(this.getBw(), currentFile);
    }

    public void rename() {
        File file=getSelectedFile();
        if(file!=null){
            //do something yaar
            File renamed=RenameDialog.showRenamer(null, file);
            if(renamed!=null)
                this.getBw().dataUpdated(new File[]{renamed, file}, BaseWindowManagement.RENAMED);
        }
    }

    public void showProperties() {
        Object verified=getSelectedFiles();
        if(verified!=null){
            FileSystemView fsv=BlueXStatics.fsv;

            try{
                if(fsv.isDrive(getSelectedFile())){
                    verified=null;
                    JOptionPane.showMessageDialog(null, "You have selected a drive. You cannot view its property.");
                    return;
                }
            }catch(Exception e) {
                return;
            }

            new PropertiesDialog(null, getSelectedFiles());
        }
        else
            JOptionPane.showMessageDialog(null, "Please select any file/s.");
    }
    
    public File getSelectedFile(){
        Vector<File> selectedURIs=getSelectedFiles();
        File file=null;

        if(selectedURIs!=null)
            file=selectedURIs.elementAt(getSelectedRow());

        return file;
    }

    /**
     * get all the files currently vieweing
     * @return
     */
    public abstract Vector<File> getAllFiles();

    /**
     * This method is used when a searching thread adds a row
     * @param file
     */
    public abstract void addFile(File file);

    public abstract boolean isDataUpdated();

    public abstract void setFocus();

    public abstract int getTotalCounts();

    public abstract int getSelectedRow();

    protected abstract void setSelectedRow(int pos);

    /**
     * This method will do the job of opening the file.
     * But this method is activated only when the Mode of viewer is not selection
     */
    public void open(){
        File file=getSelectedFile();

        if(file!=null){
            if(file.isDirectory()){
                if(BlueXStatics.prop.getProperty("browseFolders").equals("0"))
                    goToURI(file);
                else
                    new BaseWindow(file);
            }
            else
                UsefulMethods.openAssociatedFile(file);
        }
    }

    public File getCurrentURI(){
        return currentFile;
    }

    public void pushToStack(File file) {
        int pos=getSelectedRow();
        File lastFile = null;
        try {
            lastFile = stTraverse.elementAt(stTraverse.getBackPosition()).getFile();
        } catch (ArrayIndexOutOfBoundsException e) {
            lastFile = new File("file:///foo/bar");
        }

        StackEntry se=new StackEntry(file, pos);
        if (lastFile == null && file != null) {
            stTraverse.push(se);
        } else if (lastFile == null && file == null) {
            //do nothing
        } else if (lastFile != null && file == null) {
            stTraverse.push(se);
        } else if (!lastFile.equals(file)) {
            stTraverse.push(se);
        }
    }

    public void goToURI(File file){
        showHidden=Boolean.parseBoolean(BlueXStatics.prop.getProperty("showHidden"));
        pushToStack(file);
        updateContents(file);
        setFocus();
    }

    //back, forward, up enable disable verification methods
    public boolean canGoBack(){
        return stTraverse.ensureBack();
    }

    public boolean canGoForward(){
        return stTraverse.ensureForward();
    }

    public boolean canGoUp(){
        return currentFile!=null;
    }

    /**
     * This method is called when the Viewer object enters in the run method.
     * If any processing is required then one can override them.
     * @param current
     * @param next
     */
    public void processStarted(File current, File next) {}

    /**
     * This method is called when the Viewer object leaves the run method.
     * If any processing is required then one can override them.
     * @param current
     */
    public void processFinished(File current) {}

    /**
     * this method clears all the data
     */
    public abstract void clearData();

    public abstract void selectAll();

    public abstract void invertSelection();

    protected void setTitle(File file){
        String path="Roots";
        if(file!=null){
            if(Boolean.parseBoolean(BlueXStatics.prop.getProperty("displayFullPath"))){
                path=file.getAbsolutePath();
            }else{
                path=file.getName();
                try{
                    path=BlueXStatics.fsv.getSystemDisplayName(file);
                }catch(Throwable t){}
            }
        }
        bw.setTitle(path);
    }

    public Stack getStTraverse() {
        return stTraverse;
    }

    public void setStTraverse(Stack stTraverse) {
        this.stTraverse = stTraverse;
    }

    public BaseWindow getBw() {
        return bw;
    }
}
