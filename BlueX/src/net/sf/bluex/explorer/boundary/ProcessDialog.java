/*
 * ProcessDialog.java
 *
 * Created on August 29, 2008, 1:11 AM
 */

package net.sf.bluex.explorer.boundary;

import net.sf.bluex.controller.DeletionThread;
import net.sf.bluex.threads.scanner.BFS;
import net.sf.bluex.components.StackSizeComputer;
import java.io.File;
import javax.swing.JOptionPane;
import net.sf.bluex.components.HelpMapper;

/**
 *
 * @author  Blue
 */
public class ProcessDialog extends javax.swing.JDialog implements Runnable {
    /**
     * private members for functionality
     */
    private net.sf.bluex.explorer.tabPanes.Issues tbIssues;
    private net.sf.bluex.explorer.tabPanes.ScanBasket tbScanBasket;
    private net.sf.bluex.explorer.tabPanes.VirusDB tbVirusDB;
    private net.sf.bluex.explorer.tabPanes.ConfirmDelete tbConfirmDelete;
    
    //private stack of scan basket to hold the scan basket
    private net.sf.bluex.components.Stack stScanBasket;

    //stack size calculator
    private StackSizeComputer ssc=new StackSizeComputer();
    
    //thread object
    private net.sf.bluex.threads.UninterruptableThread t;
    
    //long variable to hold the total size
    private long folSize;
    private static int PROGRESS_BAR_MAX=Integer.MAX_VALUE;
    private double clusterSize;
    private long currentSize;

    //scanner object
    private BFS bfs;

    //private objects for scanning
    private String folderNameDotExe, folderNameSpaceDotExe;
    private int fileCount, detectedCount;

    /** Creates new form ProcessDialog */
    public ProcessDialog(java.awt.Frame parent,net.sf.bluex.components.Stack stScanBasket) {
        super(parent, false);
        
        //init scan basket object
        this.stScanBasket=stScanBasket;
        
        initComponents();
        initFurtherComponents();
        initScannerObject();
        this.setLocationRelativeTo(parent);
        this.setIconImage(net.sf.bluex.controller.UsefulMethods.getImage(net.sf.bluex.controller.FileModule.APP_ICON));
        setVisible(true);
    }
    
    /**
     * my methods @Private
     */
    private void initFurtherComponents(){
        
        //set the status label as idle
        lbStatus.setText("Idle State");
        
        //set the background of the text area according to the background of this dialog box
        taFolderPath.setBackground(this.getBackground());
        
        //initialize the tabs
        tbConfirmDelete=new net.sf.bluex.explorer.tabPanes.ConfirmDelete();
        tbIssues=new net.sf.bluex.explorer.tabPanes.Issues(tbConfirmDelete);
        //set the issue tab pane into the confirm delete tab
        tbConfirmDelete.setIssue(tbIssues);
        tbScanBasket=new net.sf.bluex.explorer.tabPanes.ScanBasket(stScanBasket);
        tbVirusDB=new net.sf.bluex.explorer.tabPanes.VirusDB();
        ////////
        
        ///set the heads of the panels
        jtbMain.add(tbIssues,"Issues");
        jtbMain.add(tbScanBasket,"Scan Basket");
        jtbMain.add(tbVirusDB,"Virus DB");
        jtbMain.add(tbConfirmDelete,"Confirm Delete");
        
        //do fill the scan basket with the data given by the stScanBasket Object
        doFillScanBasket();
        
        //do fill the virus db panel with the data stored in the file
        doFillVirusDB();
        
        //frame init
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter(){
            public void windowClosing(java.awt.event.WindowEvent we){
                doDisposeJob();
            }
        });
    }
    
    private void doFillScanBasket(){
        for(int i=0;i<stScanBasket.size();i++){
            java.io.File tempFile=(java.io.File)stScanBasket.elementAt(i);
            tbScanBasket.addDataRow(tempFile);
        }
    }
    
    private void doFillVirusDB(){
        java.util.ArrayList<String> listVir=net.sf.bluex.controller.FileModule.getList();
        for(String tempSTR : listVir)
            tbVirusDB.addDataRow(tempSTR);
    }
    
    public void run(){
        //init the total size as given by all folders in the stack
        initFolderSize();

        //now make the arrFiles object empty
        tbIssues.arrFiles=new java.util.ArrayList<File>();

        //start the scanning thread
        bfs.start();
    }

    private void doCloseJob(){
        stScanBasket.removeAll();
        if(tbConfirmDelete.arrFiles.size()>0){//there are some files in confirm delete tab, ask user whether to delete them for ever and ever or not
            int choice=JOptionPane.showConfirmDialog(this, "There are some files in the Confirm Delete Tab.\n" +
                    "They will be deleted forever.\n\n" +
                    "The algorithm just checks the File name and does not goes in the file content.\n" +
                    "It matches the file name with the entries in the Virus DB and also for the\n" +
                    "<folderName>.exe viruses. Please be sure before deleting the files.\n" +
                    "Immediately press the Cancel button if you have not gone through the files\n" +
                    "in the Confirm Delete Tab. If you are sure then you can press Yes button.\n\n" +
                    "Do you want to delete them all?\n" +
                    "They can't be recovered any how.", "Read it before clicking Yes", JOptionPane.YES_NO_CANCEL_OPTION);
            if(choice==JOptionPane.YES_OPTION){//do the job of deleting them
                doDeleteFiles();
                doDisposeJob();
            }
            else if(choice==javax.swing.JOptionPane.NO_OPTION){//dispose the dialog box
                doDisposeJob();
            }
            else;//do nothing if canceled is pressed
        }
        else
            doDisposeJob();
    }
    
    private void doDisposeJob(){
        if(!cmdClose.getText().equals("Close")){//check whether the label on the button close is close or not
            JOptionPane.showMessageDialog(this, "The scanning is on progress. Please stop it first.");
        }
        else
            dispose();
    }
    
    /////////////other private methods
    
    private void initFolderSize(){
        //set the heading of the interface as calculating the total space
        this.lbStatus.setText("Calculating total space...");
        
        //set the maximum size of the progressbar
        jsprogScan.setMaximum(PROGRESS_BAR_MAX);
        
        //get the total space
        folSize=ssc.getAllFolderLengthOfStack(stScanBasket).getTotalSize();

        //set the clusterSize variable
        clusterSize=(double)PROGRESS_BAR_MAX/(double)folSize;
        
        //again set the heading of the interface as Scanning...
        this.lbStatus.setText("Scanning...");
    }
    
    private void incrementProgressBar(long fileSize){
        //set the progress bar value
        currentSize+=fileSize;
        int fill=(int)((currentSize)*clusterSize);
        if(fill<=PROGRESS_BAR_MAX){
            jsprogScan.setValue(fill);
        }
        else{
            jsprogScan.setValue(jsprogScan.getMaximum());
        }
        jsprogScan.setString((int)((float)fill/(float)PROGRESS_BAR_MAX*100)+"%");
    }
    
    private void doDeleteFiles(){
        new DeletionThread(tbConfirmDelete.arrFiles);

        //another approach
//        Stack delFiles=new Stack();
//        for(File file : tbConfirmDelete.arrFiles)
//            delFiles.push(file);
//
//        new DeleteFrame(delFiles).doStartJob();
    }
    
    private void doScanStartJob(){
        cmdScan.setText("Pause");
        
        cmdClose.setText("Stop");
        
        t=net.sf.bluex.threads.UninterruptableThread.getMyThread(this, "Process Scanning Thread");
        t.start();
    }
    
    private void doScanStopJob(){
        ssc.stop();

        bfs.stop();
        
        cmdScan.setText("Scan");
        cmdClose.setText("Close");
        
        //set text for fileName and folder path as completed
        txtFileName.setText("");
        taFolderPath.setText("");
        
        //set the status label as finished
        lbStatus.setText("Scanning Finished");
        
        //set the value of progress bar as maximum
        jsprogScan.setValue(jsprogScan.getMaximum());
        jsprogScan.setString("100%");
    }
    
    private void doScanPauseJob(){
        bfs.pause();
        lbStatus.setText("Paused...");
        cmdScan.setText("Resume");
    }
    
    private void doScanResumeJob(){
        bfs.resume();
        lbStatus.setText("Scanning...");
        cmdScan.setText("Pause");
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

        cmdClose = new javax.swing.JButton();
        cmdScan = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtFileName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        taFolderPath = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        lbFileCount = new javax.swing.JLabel();
        lbDetectCount = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jtbMain = new javax.swing.JTabbedPane();
        jsprogScan = new javax.swing.JProgressBar();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Process Window");

        cmdClose.setText("Close");
        cmdClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCloseActionPerformed(evt);
            }
        });

        cmdScan.setText("Scan");
        cmdScan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdScanActionPerformed(evt);
            }
        });

        jLabel2.setText("File Name:");

        jLabel3.setText("Folder Path :");

        txtFileName.setEditable(false);
        txtFileName.setBorder(null);

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        taFolderPath.setBackground(new java.awt.Color(236, 233, 216));
        taFolderPath.setColumns(20);
        taFolderPath.setEditable(false);
        taFolderPath.setFont(new java.awt.Font("Arial", 0, 12));
        taFolderPath.setLineWrap(true);
        taFolderPath.setRows(5);
        taFolderPath.setBorder(null);
        jScrollPane1.setViewportView(taFolderPath);

        jLabel5.setText("Files Scanned:");

        lbFileCount.setText("0");

        lbDetectCount.setForeground(java.awt.Color.red);
        lbDetectCount.setText("0");

        jLabel8.setForeground(java.awt.Color.red);
        jLabel8.setText("Detected Files:");

        jsprogScan.setString("");
        jsprogScan.setStringPainted(true);

        jLabel4.setText("Progress:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("Status:");

        lbStatus.setText("Idle State");

        jLabel7.setText("Note:");

        jButton1.setText("Help");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane2.setBorder(null);

        jTextArea1.setBackground(null);
        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(2);
        jTextArea1.setText("Every entry shown in the Issues Tab are not viruses. The algorithm just checks the File name and does not goes in the file content. It matches the file name with the entries in the Virus DB and also for the <folderName>.exe viruses. Please be sure before deleting the files.");
        jTextArea1.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtbMain, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 399, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdScan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdClose, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
                            .addComponent(txtFileName, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbFileCount)
                        .addGap(83, 83, 83)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbDetectCount))
                    .addComponent(jsprogScan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsprogScan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lbFileCount)
                    .addComponent(jLabel8)
                    .addComponent(lbDetectCount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtbMain, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdClose)
                    .addComponent(cmdScan)
                    .addComponent(jLabel6)
                    .addComponent(lbStatus)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void cmdCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCloseActionPerformed
// TODO add your handling code here:
    if(cmdClose.getText().equals("Close"))
        doCloseJob();
    else if(cmdClose.getText().equals("Stop"))
        doScanStopJob();
}//GEN-LAST:event_cmdCloseActionPerformed

private void cmdScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdScanActionPerformed
// TODO add your handling code here:
    if(cmdScan.getText().equals("Scan"))
        doScanStartJob();
    else if(cmdScan.getText().equals("Pause"))
        doScanPauseJob();
    else if(cmdScan.getText().equals("Resume"))
        doScanResumeJob();
}//GEN-LAST:event_cmdScanActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    HelpMapper.showHelpFor("Process Window");
}//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdClose;
    private javax.swing.JButton cmdScan;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JProgressBar jsprogScan;
    private javax.swing.JTabbedPane jtbMain;
    private javax.swing.JLabel lbDetectCount;
    private javax.swing.JLabel lbFileCount;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JTextArea taFolderPath;
    private javax.swing.JTextField txtFileName;
    // End of variables declaration//GEN-END:variables

    private void initScannerObject() {
        //get the virus list in the array
        //made final as this is to be accessed by the anonymous class
        final java.util.ArrayList<String> virusList=net.sf.bluex.controller.FileModule.getList();

        bfs=new BFS("Processing Thread") {

            @Override
            public void processFile(File file, boolean isFile) {
                //set filecount and file name label and text box data
                lbFileCount.setText((++fileCount)+"");
                txtFileName.setText(file.getName());

                //check whether the foldername.exe and foldername .exe exists or not
                if(file.getName().equalsIgnoreCase(folderNameDotExe) || file.getName().equalsIgnoreCase(folderNameSpaceDotExe)){
                    //ensures issues
                    tbIssues.addDataRow(file);
                    lbDetectCount.setText((++detectedCount)+"");
                }

                //check for the viurses in the virus list
                for(String virus : virusList){
                    try{
                        if(file.getName().equalsIgnoreCase(virus)){
                            //ensures issues
                            tbIssues.addDataRow(file);
                            lbDetectCount.setText((++detectedCount)+"");
                        }

                    }catch(NullPointerException npe){
                        //pass the exception
                    }
                }

                //add to stack the tempFile if it is a directory for intrusion
                if(isFile){//ensures file
                    //increment the progressbar
                    incrementProgressBar(file.length());
                }
            }

            @Override
            public void processFolder(File parentFolder) {
                //set folder path text
                taFolderPath.setText(parentFolder.getAbsolutePath());

                //create two objects of string for foldername.exe and foldername .exe
                folderNameDotExe=parentFolder.getName()+".exe";
                folderNameSpaceDotExe=parentFolder.getName()+" .exe";
            }

            @Override
            public void processCompleted() {
                doScanStopJob();
            }
        };

        //add the stack items to sc object
        bfs.setStackContents(stScanBasket);
    }

}
