/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CopyFrame.java
 *
 * Created on May 30, 2009, 12:48:30 AM
 */

package net.sf.bluex.explorer.boundary;

import net.sf.bluex.components.Stack;
import net.sf.bluex.components.SeletionDetail;
import net.sf.bluex.controller.UsefulMethods;
import net.sf.bluex.components.StackSizeComputer;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import net.sf.bluex.boundary.BaseWindow;
import net.sf.bluex.components.BlueXStatics;
import net.sf.bluex.controller.BaseWindowManagement;
import net.sf.bluex.controller.FolderIntruder;
import net.sf.bluex.explorer.components.MyTableModel;
import net.sf.bluex.threads.UninterruptableThread;

/**
 * This class creates a frame when a stack of source files and destination file is passed to it.
 * it have three methods, one is for iterating throughout the source files this method calls the <b>doCopyingJob</b> method if
 * the current file is a directory. This method iterates to all the paths in the passed source folder.
 * And if the current file is a file then it calls a fileCopy method, which do the actual task of copying the file.
 * @author Blue
 */
public class CopyFrame extends javax.swing.JFrame implements Runnable{

    //the disk allocation unit variable, init it with the value from the settings file
    private int allocationUnit;

    //thread related objects
    private boolean active=false, paused=false;
    public UninterruptableThread t;

    /** stack to store the source files */
    private Stack<File> stSourceFiles;

    /** the file object of the destination folder */
    private java.io.File destinationFolder;

    //long variable to hold the total size
    private static int PROGRESS_BAR_MAX=Integer.MAX_VALUE/8;
    private double clusterSize;
    private long totalProcessedFile;

    //make a variable to count the copyied files
    private int copyFileCount=0;

    //for the details of the all source files
    private SeletionDetail sd;

    //the StackSizeComputer thread
    private StackSizeComputer ssc=new StackSizeComputer();

    //base window object
    private BaseWindow bw;
    
    /**
     * This constructor creates a copy frame object, it takes a stack of files and the destination folder location as input
     * @param stSourceFiles
     * @param destFile
     */
    private CopyFrame(Stack<File> stSourceFiles, File destFile, BaseWindow bw) {
        super("Copying...");
        this.bw=bw;
        initComponents();

        //save a copy of stack in the local object
        this.stSourceFiles=stSourceFiles.getReverseCopy();
        this.destinationFolder=destFile;
        if(destFile!=null)
            txtDestination.setText(destFile.getAbsolutePath());

        //allocation unit init
        allocationUnit=Integer.parseInt(BlueXStatics.prop.getProperty("allocationUnit"));

        //change the background
        //set the color of the panel
        taCurrentFolder.setBackground(this.jPanel2.getBackground());
        
        //jtable inits
        jTable.setShowVerticalLines(false);
        jTable.setShowHorizontalLines(false);
        //call the function to fill the table with the contents from the global copy stack
        fillTable();

        //frame init
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent we){
                doCancelJob();
            }
        });

        cmdStart.requestFocus();

        this.setIconImage(net.sf.bluex.controller.UsefulMethods.getImage(net.sf.bluex.controller.FileModule.APP_ICON));

        if(destFile.canWrite()){
            doStartJob();
        }else
            JOptionPane.showMessageDialog(this, "BlueX cannot write to this folder location.\n" +
                    "The folder is locked.", "Error in Copying", JOptionPane.ERROR_MESSAGE);

        this.setLocationByPlatform(true);
        this.setVisible(true);
    }

    public UninterruptableThread getT() {
        return t;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCurrentFile = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jsp1 = new javax.swing.JScrollPane();
        taCurrentFolder = new javax.swing.JTextArea();
        jsprogCopy = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtDestination = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lbCopiedFileCount = new javax.swing.JLabel();
        cmdStart = new javax.swing.JButton();
        cmdCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Current File Details:"));

        jLabel4.setText("File Name:");

        txtCurrentFile.setEditable(false);
        txtCurrentFile.setBorder(null);

        jLabel5.setText("Current Folder:");

        jsp1.setBorder(null);

        taCurrentFolder.setBackground(new java.awt.Color(236, 233, 216));
        taCurrentFolder.setColumns(20);
        taCurrentFolder.setEditable(false);
        taCurrentFolder.setFont(new java.awt.Font("Tahoma", 0, 11));
        taCurrentFolder.setLineWrap(true);
        taCurrentFolder.setRows(4);
        jsp1.setViewportView(taCurrentFolder);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsp1, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                    .addComponent(txtCurrentFile, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCurrentFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jsp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jsprogCopy.setString("Click Start to Copy");
        jsprogCopy.setStringPainted(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Added Files/Folders:"));

        jTable.setModel(new net.sf.bluex.explorer.components.MyTableModel());
        jScrollPane1.setViewportView(jTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
        );

        jLabel3.setText("Pasting At:");

        txtDestination.setEditable(false);
        txtDestination.setBorder(null);

        jLabel6.setText("Files Copied:");

        lbCopiedFileCount.setText("0/0");

        cmdStart.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cmdStart.setText("Start");
        cmdStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdStartActionPerformed(evt);
            }
        });

        cmdCancel.setText("Cancel");
        cmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jsprogCopy, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDestination, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbCopiedFileCount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 291, Short.MAX_VALUE)
                        .addComponent(cmdStart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsprogCopy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lbCopiedFileCount)
                    .addComponent(cmdCancel)
                    .addComponent(cmdStart))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelActionPerformed
        // TODO add your handling code here:
        doCancelJob();
    }//GEN-LAST:event_cmdCancelActionPerformed

    private void cmdStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdStartActionPerformed
        // TODO add your handling code here:
        doStartJob();
    }//GEN-LAST:event_cmdStartActionPerformed

    private boolean checkSizeConstraint() {
        boolean eligible=true;

        long freeSpace=destinationFolder.getFreeSpace();
        if((freeSpace-sd.getTotalSize())<0)
            eligible=false;

        return eligible;
    }

    public void run(){
        //start the copy yaar
        active=true;
        paused=false;

        //disable start button
        cmdStart.setEnabled(false);

        //get the folder size
        getFolderSize();

        //enable start button
        cmdStart.setEnabled(true);

        //set the label of files count
        lbCopiedFileCount.setText("0/"+sd.getFilesCount());

        File currentFile;
        if(checkSizeConstraint()){
            //make a copy of all stack items
            Stack stCopy=stSourceFiles.getAnotherCopy();//so that updations can be made after the copying process

            /**
             * this loop iterates for all the stack elements which have been previously added
             */
            /** this variable is used to identify the row in the table */
            int i=0;
            while(active && (currentFile=stSourceFiles.pop())!=null){//
                //check whether the same name exists or not
                //if exists change the name with the prefix of [CopyOf <File/Folder Name>]
                //get the unique name of the file/folder in the copylocation
                String uniqueFileName=UsefulMethods.getUniqueName(currentFile.getName(),destinationFolder);

                //create a new file with the new folder created
                java.io.File tempDestFile=new java.io.File(destinationFolder.getAbsolutePath(),uniqueFileName);

                //set the status to progress of the current file
                jTable.setValueAt("Progress", i, 5);

                //check whether the parentFile is a directory or a file
                //if folder than doCopyingJob() else doFileCopyJob(parentFile,destFile)
                if(currentFile.isDirectory()){//the parentFile is a folder confirms
                    doCopyingJob(currentFile,tempDestFile);
                }
                else{//the parentFile is a file confirm
                    //set path of the destination textArea
                    taCurrentFolder.setText(destinationFolder.getAbsolutePath());
                    
                    doFileCopyJob(currentFile,tempDestFile);
                }

                //set the status to done of the current file
                jTable.setValueAt("Done", i++, 5);

                while(paused);//pausing the thread
            }

            if(bw!=null){
                //make updations to all bws
                File temp;
                i=0;
                File[] files=new File[stCopy.size()];
                while((temp=(File)stCopy.pop())!=null){
                    if(temp.exists())
                        files[i++]=temp;
                }

                bw.dataUpdated(files, BaseWindowManagement.NEW_ADDITION);
            }
        }else
            JOptionPane.showMessageDialog(this, "Your disk does not have enough space to process.\n" +
                    "Please remove some files.","Copy Error", JOptionPane.ERROR_MESSAGE);

        //stop copy
        doCancelJob();
    }
    
    /**
     * private methods..
     */

    /**
     * method to get the size of each and every folder/file and total size.
     */
    private void getFolderSize(){
        //set indeterminate mode on
        jsprogCopy.setIndeterminate(true);

        //set the text in the progress bar as calculating the total space
        this.jsprogCopy.setString("Calculating total space...");

        //set folsize variable
        sd=new SeletionDetail();

        //get the individual sizes and add them to the sd object
        //reverse stack and then calculate
        Stack revStack=stSourceFiles.getReverseCopy();
        for(int i=0;i<revStack.size();i++){
            if(active){
                Stack tempStack=new Stack();
                tempStack.push(revStack.elementAt(i));

                SeletionDetail tempSD=ssc.getAllFolderLengthOfStack(tempStack);

                //set the sizes in the table
                jTable.setValueAt(UsefulMethods.getFileSize(tempSD.getTotalSize()), i, 4);

                //add to global sd object
                sd.addSelectionDetail(tempSD);
            }
        }

        //set the clusterSize variable
        clusterSize=(double)PROGRESS_BAR_MAX/(double)sd.getTotalSize();

        //set indeterminate mode off
        jsprogCopy.setIndeterminate(false);

        //set the max value of the progress bar
        jsprogCopy.setMaximum(PROGRESS_BAR_MAX);
    }

    /**
     * the algo of copying one file to another
     * @param parentFile
     * @param destFile
     */
    private void doCopyingJob(File parentFile, File destFile){
        Stack<File> stcopyFolders=new Stack<File>();

        /////////////put the parentFile in the stack
        stcopyFolders.push(parentFile);

        //////////////
        java.io.File destFileForLoop;
        //do copy operation

        /////////////////////////////////////////////////////////////////////////copy all files
        File parentFolder=null;
        while((active && (parentFolder=stcopyFolders.pop())!=null)){

            //set the destination folder
            destFileForLoop=new File(destFile, getDestFlderSuffix(parentFile, parentFolder));

            //create the destination directory
            if(!destFileForLoop.mkdir()){//error creating the folder
                //display error that cannot create folder. Exiting
                JOptionPane.showMessageDialog(null, "The Folders cannot be created on the requested folder location.\n\n" +
                        "Try again later.");
                doCancelJob();
                return;
            }

            //set path of the destination textArea
            taCurrentFolder.setText(destFileForLoop.getAbsolutePath());

            //create a instance of files object which contains all files and folders for corresoponding parent folder
            File[] file=FolderIntruder.getFilesFolders(parentFolder);

            //if the file object is not null then go inside else outside return.
            if(file!=null){

                //do the job of iteration
                for(java.io.File tempFile : file){
                    if(active){//to stop the deleting operation inbetween
                        try{

                            if(tempFile.isFile()){//ensures file
                                //create the tempDestFile object which will contain the new destFile
                                File tempDestFile=new File(destFileForLoop, tempFile.getName());
                                doFileCopyJob(tempFile, tempDestFile);
                            }
                            else if(tempFile.isDirectory()){
                                stcopyFolders.push(tempFile);
                            }
                        }catch(NullPointerException npe){
                            //pass the exception
                        }
                    }
                }
            }
            while(paused);//pausing the thread
        }
    }

    /**
     * This method will find the suffix for the destination folder
     */
    private String getDestFlderSuffix(File parentFolder, File currentFolderDepth){
        //create two string objects to get the absolute paths of both type of objects
        String parentAbsolutePath=parentFolder.getAbsolutePath();
        String currentDepthAbsolutePath=currentFolderDepth.getAbsolutePath();

        //create a string object which will contain the depth of the current folder
        String depth="";

        //assign the depth in the object
        depth=currentDepthAbsolutePath.substring(parentAbsolutePath.length());

        return depth;
    }

    /**
     * algo to copy one file to another physically
     * @param parentFile
     * @param destFile
     * @return
     */
    private boolean doFileCopyJob(File parentFile, File destFile){
        //do the job of copying the parentFile to destination File

        //set the filename text field of src
        txtCurrentFile.setText(parentFile.getName());

        //make the FileInputStream Object of parentFile
        FileInputStream fis=null;
        //make the FileOutputStream Object of destFile
        FileOutputStream fos=null;
        int n=0;//no of bytes copied
        try{
            fis=new FileInputStream(parentFile);
            fos=new FileOutputStream(destFile);

            //////////////////////////////////copying algorithm
            byte[] data=new byte[allocationUnit];
            n=fis.read(data);
            while(n!=-1 && active){
                fos.write(data,0,n);
                n=fis.read(data);
                //increment the progressbar
                incrementProgressBar(n);

                while(paused);//pausing the thread
            }

            //increment the filecount
            lbCopiedFileCount.setText((++copyFileCount)+"/"+sd.getFilesCount());

            //on successfull return true
            return true;
        }catch(Exception e){
            //catch any exception and return false
            return false;
        }finally{
            try{
                fis.close();
            }catch(Exception e){}
            try{
                fos.flush();
                fos.close();
            }catch(Exception e){}

            //delete the file when cancel is pressed and the file is not copied fully
            if(!active && n!=-1){
                destFile.delete();
            }
        }
    }

    private void incrementProgressBar(long fileSize){
        //set the progress bar value
        totalProcessedFile+=fileSize;
        int fill=(int)(totalProcessedFile*clusterSize);
        jsprogCopy.setValue(fill);
        jsprogCopy.setString((int)((float)fill/(float)PROGRESS_BAR_MAX*100)+"%");
    }

    /**
     * it will do task of filling the table with the data
     * @return
     */
    private void fillTable(){
        //make vector of vector object to get the data to be displayed
        Vector<Vector> data=new Vector<Vector>();

        //make the data vector filled
        for(int i=stSourceFiles.size()-1;i>=0;i--)
            data.add(getTableRow(stSourceFiles.elementAt(i)));

        jTable.setModel(new MyTableModel(data, getColumnHead()));

        //now resize the table
        resizeTable();
    }

    /**
     * resize the table
     */
    private void resizeTable(){
        for(int i=0;i<getColumnHead().size();i++){

            //rezise
            int size=100;
            if(i==0)
                size=25;
            else if(i==1)
                size=130;
            else if(i==2)
                size=100;
            else if(i==3)
                size=70;
            else if(i==4)
                size=70;
            else if(i==5)
                size=50;

            jTable.getColumnModel().getColumn(i).setPreferredWidth(size);
        }
        jTable.getParent().setBackground(java.awt.Color.WHITE);
        jTable.setRowHeight(19);

        jTable.setAutoResizeMode(jTable.AUTO_RESIZE_OFF);
        jTable.requestFocus();
    }

    /**
     * get column heads for the table
     * @return
     */
    public static java.util.Vector<Object> getColumnHead(){
        Vector<Object> colHead=new Vector<Object>();
        colHead.add("");colHead.add("Name");colHead.add("Location");colHead.add("Type");colHead.add("Size");colHead.add("Status");
        return colHead;
    }

    /**
     * this method takes a file object as an argument and returns the<b/>
     * vector object which contains the data related to the file
     * @param file
     * @return
     */
    private java.util.Vector<Object> getTableRow(File file){
        //object array data which is returned through this method
        java.util.Vector<Object> data=new java.util.Vector<Object>();

        //set data

        //1. set icon
        data.add(BlueXStatics.getIcon(file));

        //2. set file name
        data.add(BlueXStatics.getFileName(file));

        //3. set location of file
        data.add(file.getParent());

        //4. type of the file. type description
        data.add(BlueXStatics.getFileDescription(file));

        //5. size of the files/dirs
        //set dirs to nothing and files to the actual size they have
        if(file.isDirectory())
            data.add("");//left empty
        else //for files
            data.add(UsefulMethods.getFileSize(file.length()));

        //6. status
        //set the all to empty and when anyone is completed then set it to "Done"
        data.add("");//empty

        //return the object
        return data;
    }

    private void doStartJob(){
        if(cmdStart.getText().equals("Start")){
            t=UninterruptableThread.getMyThread(this, "Copying Thread");
            t.start();
            cmdStart.setText("Pause");
        }else if(cmdStart.getText().equals("Pause")){
            this.paused=true;
            cmdStart.setText("Resume");
        }
        else{
            this.paused=false;
            cmdStart.setText("Pause");
        }
    }

    private void doCancelJob(){
        paused=false;
        active=false;
        ssc.stop();
        this.dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdCancel;
    private javax.swing.JButton cmdStart;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    private javax.swing.JScrollPane jsp1;
    private javax.swing.JProgressBar jsprogCopy;
    private javax.swing.JLabel lbCopiedFileCount;
    private javax.swing.JTextArea taCurrentFolder;
    private javax.swing.JTextField txtCurrentFile;
    private javax.swing.JTextField txtDestination;
    // End of variables declaration//GEN-END:variables

    public static CopyFrame showCopyProgressDialog(File destFile, BaseWindow bw){
        Stack<File> stFiles=new Stack<File>();

        //get the files from the system clipboard
        Clipboard clip=Toolkit.getDefaultToolkit().getSystemClipboard();
        if(clip.isDataFlavorAvailable(java.awt.datatransfer.DataFlavor.javaFileListFlavor))
            try{
                List<File> list=(List<File>)clip.getData(DataFlavor.javaFileListFlavor);
                for(java.io.File file : list)
                    stFiles.push(file);

                //copy data if there is any file to copy
                if(stFiles.size()>0)
                    return new CopyFrame(stFiles, destFile, bw);
            }catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "There is some problem while getting the list from the clipboard.\n\n" +
                        "Please try again.", "Error getting list", JOptionPane.ERROR_MESSAGE);
            }
        return null;
    }
}
