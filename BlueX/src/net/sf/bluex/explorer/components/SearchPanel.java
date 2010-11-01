/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SearchPanel.java
 *
 * Created on Oct 2, 2009, 8:05:17 PM
 */

package net.sf.bluex.explorer.components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;
import javax.swing.JOptionPane;
import net.sf.bluex.boundary.BaseWindow;
import net.sf.bluex.components.BlueXFile;
import net.sf.bluex.components.BlueXStatics;
import net.sf.bluex.components.Stack;
import net.sf.bluex.controller.UsefulMethods;
import net.sf.bluex.explorer.boundary.PropertiesDialog;
import net.sf.bluex.threads.scanner.BFS;

/**
 *
 * @author Blue
 */
public class SearchPanel extends javax.swing.JPanel {
    private File currentFile;
    private BFS bfs;

    public static int threadCount=1;

    private MyTable mt;
    private NewMyTableModel mtm;

    private int searchNumber;

    private String textSearch;

    /** Creates new form SearchPanel */
    public SearchPanel(File currentFile) {
        initComponents();
        mt=new MyTable(new String[]{"Name", "Location", "Size", "Type"});
        mt.setComponentPopupMenu(jPopupMenu1);
        mtm=mt.getModel();
        mt.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me){
                if(me.getClickCount()==2 && me.getButton()==me.BUTTON1){
                    jMenuItem1ActionPerformed(null);
                }
            }
        });
        jScrollPane1.setViewportView(mt);

        this.currentFile=currentFile;
        initJCB();

        synchronized(this){
            searchNumber=threadCount;
            threadCount++;
        }
        
        bfs=new BFS("Searching Thread # "+searchNumber) {

            public void processFile(File file, boolean isFile) {
                boolean add=true;
                String fileName;
                if(jCheckBox2.isSelected())
                    fileName=file.getName();
                else
                    fileName=file.getName().toLowerCase();

                if(jCheckBox3.isSelected()&& fileName.equalsIgnoreCase(textSearch) || !jCheckBox3.isSelected() && fileName.contains(textSearch)){
                    if(!jCheckBox1.isSelected()){
                        if(file.isHidden())
                            add=false;
                    }
                    if(add){
                        Vector<Object> data=new Vector<Object>();
                        data.add(new BlueXFile(file));
                        data.add(file.getParent());
                        if(isFile)
                            data.add(UsefulMethods.getFileSize(file.length()));
                        else
                            data.add("");
                        data.add(BlueXStatics.getFileDescription(file));
                        mtm.addRow(data);
                        lbCount.setText(mtm.getRowCount()+"");
                    }
                }
            }

            public void processFolder(File parentFolder) {
                lbFolder.setText(parentFolder.getAbsolutePath());
            }

            public void processCompleted() {
                doStopSearch();
            }
        };
    }

    private void initJCB(){
        jComboBox1.setRenderer(new MyComboBoxRenderer());
        Set<File> set=new LinkedHashSet<File>();

        set.add(null);

        for(BlueXFile file : BlueXFile.getRoots()){
            set.add(file.getFile());
        }

        for(String location : BlueXStatics.fav){
            File file=null;
            if(!location.equals("null")){
                file=new File(location);
                set.add(file);
            }
        }
        set.add(currentFile);

        //now add them to the jCombo
        for(File file : set){
            jComboBox1.addItem(file);
        }
        jComboBox1.setSelectedItem(currentFile);
    }

    public void doStartSearch() {
        if(!jTextField1.getText().equals("")){
            jComboBox1.setEnabled(false);
            jTextField1.setEditable(false);
            jCheckBox1.setEnabled(false);
            jCheckBox2.setEnabled(false);
            jCheckBox3.setEnabled(false);
            jButton1.setText("Stop");
            mtm.clear();
            lbCount.setText("0");

            //
            if(jCheckBox2.isSelected())
                textSearch=jTextField1.getText();
            else
                textSearch=jTextField1.getText().toLowerCase();

            Stack<File> st=new Stack<File>();
            if(currentFile!=null)
                st.push(currentFile);
            else{
                for(BlueXFile f : BlueXFile.getRoots())
                    st.push(f.getFile());
            }
            bfs.setStackContents(st);

            bfs.start();
        }
        else
            JOptionPane.showMessageDialog(null, "The \"Search For\" text field " +
                    "cannot be left empty.", "Cannot perform the search", JOptionPane.ERROR_MESSAGE);
    }

    public void doStopSearch() {
        bfs.stop();
        jComboBox1.setEnabled(true);
        jTextField1.setEditable(true);
        jCheckBox1.setEnabled(true);
        jCheckBox2.setEnabled(true);
        jCheckBox3.setEnabled(true);
        jButton1.setText("Search");
        lbFolder.setText("Finished");
    }

    public int getSearchNumber(){
        return searchNumber;
    }

    public void setFocus(){
        jTextField1.requestFocus();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        lbFolder = new javax.swing.JLabel();
        lbCount = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();

        jMenuItem1.setText("Open");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText("Copy");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);
        jPopupMenu1.add(jSeparator2);

        jMenuItem3.setText("Properties");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem3);

        jLabel1.setText("Search In:");

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Search Hidden Files");

        jLabel2.setText("Search For:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        lbFolder.setText("<Folder Location>");

        lbCount.setFont(new java.awt.Font("Tahoma", 1, 11));
        lbCount.setText("Count");

        jCheckBox2.setText("Case Sensetive");

        jCheckBox3.setText("Match Whole Word");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, 0, 570, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(lbCount)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jButton1)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbFolder)
                    .addComponent(lbCount))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(jButton1.getText().equals("Search"))
            doStartSearch();
        else
            doStopSearch();
}//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        currentFile=(File)jComboBox1.getSelectedItem();
}//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        if(jButton1.getText().equals("Search"))
            doStartSearch();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int row=mt.getSelectedRow();
        if(row!=-1){
            BlueXFile bxf=mtm.getFileAt(row);
            if(bxf.getFile().isDirectory())
                new BaseWindow(bxf.getFile());
            else
                UsefulMethods.openAssociatedFile(bxf.getFile());
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if(mt.getSelectedRow()!=-1){
            int[] rows=mt.getSelectedRows();
            Vector<File> copyDS=new Vector<File>(rows.length);
            for(int i=0; i<rows.length; i++)
                copyDS.add(mtm.getFileAt(rows[i]).getFile());
            UsefulMethods.copy(copyDS);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        if(mt.getSelectedRow()!=-1){
            int[] rows=mt.getSelectedRows();
            Vector<File> vectFile=new Vector<File>(rows.length);
            for(int i=0; i<rows.length; i++)
                vectFile.add(mtm.getFileAt(rows[i]).getFile());
            new PropertiesDialog(null, vectFile);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lbCount;
    private javax.swing.JLabel lbFolder;
    // End of variables declaration//GEN-END:variables

}
