/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.explorer.tabPanes;

import net.sf.bluex.explorer.components.MyTableModel;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Blue
 */
public class ScanBasket extends net.sf.bluex.explorer.abstractClasses.AbstractTabPanel{
    
    //private objects
    private static FileSystemView fsv=FileSystemView.getFileSystemView();
    
    private net.sf.bluex.components.Stack stScanBasket;
    
    /**
     * constructor of the issue class
     */
    public ScanBasket(net.sf.bluex.components.Stack stScanBasket){
        
        this.stScanBasket=stScanBasket;
        
        //initialization methods
        selectComponents();
        renameComponents();
        setTableHeaders();
    }
    
    private void selectComponents(){
        this.setCmdSWest2Visible(false);
    }
    
    private void renameComponents(){
        //set panel head
        this.setPanelHead("Scan Basket");
        //set command buttons text
        this.setCmdSEast1Text("Add");
        this.cmdSEast1.setEnabled(false);
        this.setCmdSEast2Text("Remove");
        //set not visible cmdSWest2
        this.setCmdSWest1Visible(false);
        this.setCmdSWest2Visible(false);
    }
    
    private void setTableHeaders(){
        String[] colHead={"Icon","Name","Path","Type"};
        jTable.setModel(new MyTableModel(null, colHead));
        jTable.getParent().setBackground(CENTER_COLOR);
    }
    
    /**
     * public methods
     */
    
    public void addDataRow(java.io.File file){
        //set data
        Object[] data=new Object[4];
        data[0]=fsv.getSystemIcon(file);
        data[1]=fsv.getSystemDisplayName(file);
        data[2]=fsv.getParentDirectory(file);
        data[3]=fsv.getSystemTypeDescription(file);
        //create a tablemodel object
        javax.swing.table.DefaultTableModel dtm=(javax.swing.table.DefaultTableModel)jTable.getModel();
        dtm.addRow(data);
    }

    /**
     * Private methods
     */
    private void deleteSelectedFile(int i){
        //delete from stack
        stScanBasket.removeItem(i);
        //delete from table
        javax.swing.table.DefaultTableModel dtm=(javax.swing.table.DefaultTableModel)jTable.getModel();
        dtm.removeRow(i);
    }
    
    //Fix button
    protected void doCmdSEast1() {
        //implemented later
    }

    //Delete button
    protected void doCmdSEast2() {
        if(jTable.getSelectedRow()!=-1){
            int[] rows=jTable.getSelectedRows();
            for(int i : rows){
                //delete entry from this object table
                deleteSelectedFile(jTable.getSelectedRow());
            }
        }
    }

    //Select All Button
    protected void doCmdSWest1() {
        jTable.selectAll();
    }

    //not visible
    protected void doCmdSWest2() {}

}
