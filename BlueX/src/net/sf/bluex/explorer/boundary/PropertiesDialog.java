/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PropertiesDialog.java
 *
 * Created on Jun 13, 2009, 9:35:50 PM
 */

package net.sf.bluex.explorer.boundary;

import net.sf.bluex.components.SeletionDetail;
import net.sf.bluex.components.Stack;
import net.sf.bluex.controller.UsefulMethods;
import net.sf.bluex.components.StackSizeComputer;
import java.io.File;
import java.util.Vector;
import javax.swing.JOptionPane;
import net.sf.bluex.boundary.BaseWindow;

/**
 *
 * @author Blue
 */
public class PropertiesDialog extends javax.swing.JDialog implements Runnable {

    //the StackSizeComputer thread
    private StackSizeComputer ssc=new StackSizeComputer();

    //the stack of files
    private Stack stSourceFiles;

    /** file system view object for displaying purposes of files */
    private javax.swing.filechooser.FileSystemView fsv;

    /** thread object */
    private boolean active=false;
    public net.sf.bluex.threads.UninterruptableThread t;

    //for the details of the all source files
    private SeletionDetail sd;

    //
    private Vector<File> files;

    /** Creates new form PropertiesDialog */
    public PropertiesDialog(java.awt.Frame parent, Vector<File> files) {
        super(parent, false);
        initComponents();

        this.files=files;

        //set it to stack
        stSourceFiles=new Stack();
        for(File file : files)
            stSourceFiles.push(file);

        //jtable inits
        //folders
        jTable.setShowVerticalLines(false);
        jTable.setShowHorizontalLines(false);
        
        //dispose job
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent we){
                doCancelJob();
            }
        });

        //call the function to fill the table with the contents from the global copy stack
        fillTable();

        //thread inits
        t=net.sf.bluex.threads.UninterruptableThread.getMyThread(this, "Properties Thread");
        t.start();

        this.setLocationRelativeTo(parent);
        this.setIconImage(net.sf.bluex.controller.UsefulMethods.getImage(net.sf.bluex.controller.FileModule.APP_ICON));
        setVisible(true);
    }

    private void doCancelJob(){
        active=false;
        ssc.stop();
        this.dispose();
    }

    /**
     * it will do task of filling the table with the data
     * @return
     */
    private void fillTable(){
        //init the file system view
        fsv=javax.swing.filechooser.FileSystemView.getFileSystemView();

        //make vector of vector object to get the data to be displayed
        java.util.Vector<java.util.Vector> data=new java.util.Vector<java.util.Vector>();

        //make the data vector filled
        for(int i=stSourceFiles.size()-1;i>=0;i--)
            data.add(getTableRow((java.io.File)stSourceFiles.elementAt(i),stSourceFiles.size()-1-i));

        jTable.setModel(new net.sf.bluex.explorer.components.MyTableModel(data, getColumnHead()));

        //now resize the table
        resizeTable();
    }

    /**
     * get column heads for the table
     * @return
     */
    public static java.util.Vector<Object> getColumnHead(){
        java.util.Vector<Object> colHead=new java.util.Vector<Object>();
        colHead.add("");colHead.add("Name");colHead.add("Location");colHead.add("Type");colHead.add("Size");colHead.add("Folders Count");colHead.add("Files Count");
        return colHead;
    }

    /**
     * method to get the size of each and every folder/file and total size.
     */
    private void getFolderSize(){
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

                //set the entry in the table
                jTable.setValueAt(tempSD.getFoldersCount(), i, 5);
                jTable.setValueAt(tempSD.getFilesCount(), i, 6);

                //set the total variables...
                lbSize.setText(UsefulMethods.getFileSize(sd.getTotalSize()));
                lbFolders.setText(sd.getFoldersCount()+"");
                lbFiles.setText(sd.getFilesCount()+"");
            }
        }
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
                size=62;
            else if(i==6)
                size=62;

            jTable.getColumnModel().getColumn(i).setPreferredWidth(size);
        }
        jTable.getParent().setBackground(java.awt.Color.WHITE);
////        jTable.setAutoCreateRowSorter(true);
        jTable.setRowHeight(19);

        jTable.setAutoResizeMode(jTable.AUTO_RESIZE_OFF);
        jTable.setEnabled(true);
        jTable.requestFocus();
    }

    /**
     * this method takes a file object as an argument and returns the<b/>
     * vector object which contains the data related to the file
     * @param file
     * @return
     */
    private java.util.Vector<Object> getTableRow(java.io.File file, int i){
        //object array data which is returned through this method
        java.util.Vector<Object> data=new java.util.Vector<Object>();

        //set data

        //1. set icon
        //this assignement will throw FileNotFoundException. This exception occurrs as the method was not able to find out the icon file
        javax.swing.Icon icon=null;
        try{
            icon=fsv.getSystemIcon(file);
        }catch(Throwable e){
            icon=net.sf.bluex.controller.UsefulMethods.getIcon("unknownFile.gif");
        }
        data.add(icon);

        //2. set file name
        //original filename will be stored
        String fileName=file.getName();
        data.add(fileName);

        //3. set location of file
        data.add(file.getParent());

        //4. type of the file. type description
        //try if fsv do not give any exception then add it else set unkonwn
        try{
            data.add(fsv.getSystemTypeDescription(file));
        }catch(Throwable e){
            data.add("Unknown");
        }

        //5. size of the files/dirs
        //set dirs to nothing and files to the actual size they have
        if(file.isDirectory())
            data.add("");//left empty
        else //for files
            data.add(net.sf.bluex.controller.UsefulMethods.getFileSize(file.length()));

        //6. folder count
        //set to nil
        data.add("---");//empty

        //7. file count
        //set to nil
        data.add("---");//empty

        //return the object
        return data;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        cmdCancel = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbFolders = new javax.swing.JLabel();
        lbFiles = new javax.swing.JLabel();
        jsprogCopy = new javax.swing.JProgressBar();
        cmdRename = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lbSize = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cmdOpen = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Properties Winodw");

        jLabel1.setText("Selection Details:");

        jTable.setModel(new net.sf.bluex.explorer.components.MyTableModel());
        jScrollPane1.setViewportView(jTable);

        cmdCancel.setText("Close");
        cmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancelActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("Total:");

        jLabel4.setText("Folders:");

        jLabel5.setText("Files:");

        lbFolders.setFont(new java.awt.Font("Tahoma", 1, 11));
        lbFolders.setText("Total Folders");

        lbFiles.setFont(new java.awt.Font("Tahoma", 1, 11));
        lbFiles.setText("Total Files");

        jsprogCopy.setIndeterminate(true);
        jsprogCopy.setString("Calculating...");
        jsprogCopy.setStringPainted(true);

        cmdRename.setText("Rename");
        cmdRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRenameActionPerformed(evt);
            }
        });

        jLabel2.setText("Size:");

        lbSize.setFont(new java.awt.Font("Tahoma", 1, 11));
        lbSize.setText("Total Size");

        jLabel6.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel6.setText("Folders");

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 11));
        jLabel7.setText("Files");

        cmdOpen.setText("Open");
        cmdOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdOpenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jsprogCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 335, Short.MAX_VALUE)
                        .addComponent(cmdCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbSize)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbFolders)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbFiles)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)))
                        .addGap(393, 393, 393))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cmdOpen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdRename))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                    .addComponent(jLabel3))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdRename)
                    .addComponent(cmdOpen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbSize))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lbFolders)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lbFiles)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdCancel)
                    .addComponent(jsprogCopy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelActionPerformed
        // TODO add your handling code here:
        doCancelJob();
    }//GEN-LAST:event_cmdCancelActionPerformed

    private void cmdRenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRenameActionPerformed
        // TODO add your handling code here:
        int row=getSelection();

        if(row!=-1){
            row=files.size()-row-1;
            RenameDialog.showRenamer(null, files.elementAt(row));
        }
    }//GEN-LAST:event_cmdRenameActionPerformed

    private void cmdOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdOpenActionPerformed
        // TODO add your handling code here:
        int row=getSelection();
        if(row!=-1){
            row=files.size()-row-1;
            File file=files.elementAt(row);

            if(file.isDirectory()){
                new BaseWindow(file);
            }else
                UsefulMethods.openAssociatedFile(files.elementAt(row));
        }
    }//GEN-LAST:event_cmdOpenActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdCancel;
    private javax.swing.JButton cmdOpen;
    private javax.swing.JButton cmdRename;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable;
    private javax.swing.JProgressBar jsprogCopy;
    private javax.swing.JLabel lbFiles;
    private javax.swing.JLabel lbFolders;
    private javax.swing.JLabel lbSize;
    // End of variables declaration//GEN-END:variables

    public void run() {
        active=true;
        
        //get the folder size
        getFolderSize();

        //finally hide the bar
        jsprogCopy.setVisible(false);
    }

    private int getSelection(){
        int row=jTable.getSelectedRow();

        if(row==-1)
            JOptionPane.showMessageDialog(this, "Please select any row to continue.");

        return row;
    }

}
