/*
 * ProcessDialog.java
 *
 * Created on August 29, 2008, 1:11 AM
 */

package net.sf.bluex.explorer.boundary;

import net.sf.bluex.components.Stack;
import java.io.File;

/**
 *
 * @author  Blue
 */
public class AgentDialog extends javax.swing.JFrame implements Runnable {
    /**
     * private members for functionality
     */
    private net.sf.bluex.explorer.tabPanes.Issues tbIssues;
    private net.sf.bluex.explorer.tabPanes.ConfirmDelete tbConfirmDelete;
    private Stack stScanBasket;
    
    //private boolean for stoping and pausing scaning
    private boolean startScanning;
    
    //private boolean for checking the visible
    private boolean isVisible;
    
    //thread object
    private net.sf.bluex.threads.UninterruptableThread t;

    /** Creates new form ProcessDialog */
    public AgentDialog() {
        initComponents();
        initFurtherComponents();
        startScanning();
    }
    
    /**
     * my methods @Private
     */
    private void initFurtherComponents(){//initialize the tabs
        tbConfirmDelete=new net.sf.bluex.explorer.tabPanes.ConfirmDelete();
        tbIssues=new net.sf.bluex.explorer.tabPanes.Issues(tbConfirmDelete);
        //set the issue tab pane into the confirm delete tab
        tbConfirmDelete.setIssue(tbIssues);
        ////////
        
        ///set the heads of the panels
        jtbMain.add(tbIssues,"Issues");
        jtbMain.add(tbConfirmDelete,"Confirm Delete");
        
        //frame init
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter(){
            public void windowClosing(java.awt.event.WindowEvent we){
                doCloseJob();
            }
        });
    }
    
    private void initScanBasket(){//fill the drives in the scan basket
        stScanBasket=new net.sf.bluex.components.Stack();
        
        //drive char for windows
        char drive='A';

        //if windows
        //iterate through each root
        for(int i=0;i<26;i++){
            File tempFile=new File(drive+":\\");
            drive++;

            if(tempFile.exists()){//windows file system confirmed
                stScanBasket.push(tempFile);
            }
        }
    }
    
    public void run(){
        doScanning();
    }

    private void doCloseJob(){
        doDeleteFiles();
        dispose();
    }
    
    /////////////other private methods
    
    private void doDeleteFiles(){
    }
    
    private void waitThread(){
        try{
            Thread.sleep(6000);
        }catch(InterruptedException ie){
            //pass the exception
        }
    }
    
    private void addDataRow(java.io.File tempFile){
        java.util.ArrayList<java.io.File> addedFiles=tbIssues.getAddedFiles();
        boolean repeated=false;
        
        for(java.io.File file : addedFiles){
            if(tempFile.getAbsolutePath().equals(file.getAbsolutePath())){
                repeated=true;
                break;
            }
        }
        if(!repeated)
            tbIssues.addDataRow(tempFile);
    }
    
    //////////////////////////////SCANNING STARTS
    private void doScanning(){
        //do scan operation
        //get the virus list in the array 
        java.util.ArrayList<String> virusList=net.sf.bluex.controller.FileModule.getList();
        
        //now make the arrFiles object empty
        tbIssues.arrFiles=new java.util.ArrayList<File>();
        
        while(startScanning){
            //init scan basket object
            initScanBasket();
            
            File parentFolder=null;
            while((startScanning && (parentFolder=(File)stScanBasket.pop())!=null)){

                //create a instance of files object which contains all files and folders for corresoponding parent folder
                File[] file=net.sf.bluex.controller.FolderIntruder.getFilesFolders(parentFolder);

                //if the file object is not null then go inside else outside return.
                if(file!=null){
                    
                    //create two objects of string for foldername.exe and foldername .exe
                    String folderNameDotExe=parentFolder.getName()+".exe";
                    String folderNameSpaceDotExe=parentFolder.getName()+" .exe";
                    
                    

                    //do the job of iteration
                    for(File tempFile : file){
                        if(startScanning){//to stop the searching operation inbetween
                            
                            //check whether the foldername.exe and foldername .exe exists or not
                            if(tempFile.getName().equalsIgnoreCase(folderNameDotExe) || tempFile.getName().equalsIgnoreCase(folderNameSpaceDotExe)){
                                //ensures issues
                                if(!isVisible)
                                    showDialog();
                                this.addDataRow(tempFile);
                            }
                            
                            //check for the viurses in the virus list
                            for(String virus : virusList){
                                try{
                                    if(tempFile.getName().equalsIgnoreCase(virus)){
                                        //ensures issues
                                        if(!isVisible)
                                            showDialog();
                                        this.addDataRow(tempFile);
                                    }

                                }catch(NullPointerException npe){
                                    //pass the exception
                                }
                            }
                        }
                    }
                }
                if(!startScanning)
                    t=null;
            }
            waitThread();
        }
    }
    
    /**
     * @public methods
     */
    public void startScanning(){
        startScanning=true;
        t=net.sf.bluex.threads.UninterruptableThread.getMyThread(this, "Agent Scanning Thread");
        t.start();
    }
    
    public void stopScanning(){
        startScanning=false;
    }
    
    public void showDialog(){
        isVisible=true;
        this.setVisible(true);
    }
    
    public void hideDialog(){
        isVisible=false;
        this.setVisible(false);
    }
    
    //////////////////////////////
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtbMain = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agent Window");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtbMain, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtbMain, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane jtbMain;
    // End of variables declaration//GEN-END:variables

}
