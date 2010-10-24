/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.explorer.tabPanes;

import net.sf.bluex.controller.UsefulMethods;
import net.sf.bluex.explorer.components.MyTableModel;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Blue
 */
public class ConfirmDelete extends net.sf.bluex.explorer.abstractClasses.AbstractTabPanel{
    
    //private objects
    private net.sf.bluex.explorer.tabPanes.Issues issue;
    
    //not made private so as to delete these files permanently while the close button is pressed in progress window.
    public java.util.ArrayList<java.io.File> arrFiles;
    
    
    /**
     * constructor of the issue class
     */
    public ConfirmDelete(){
        //init the arraylist object
        arrFiles=new java.util.ArrayList<java.io.File>();
        
        //initialization methods
        selectComponents();
        renameComponents();
        setTableHeaders();
    }
    
    public void setIssue(net.sf.bluex.explorer.tabPanes.Issues issue){
        this.issue=issue;
    }
    
    private void selectComponents(){
        this.setCmdSWest2Visible(false);
    }
    
    private void renameComponents(){
        //set panel head
        this.setPanelHead("Confirm Delete");
        //set command buttons text
        this.setCmdSEast2Text("Restore");
        this.setCmdSWest1Text("Select All");
        //set not visible
        this.setCmdSEast1Visible(false);
        this.setCmdSWest2Visible(false);
    }
    
    private void setTableHeaders(){
        String[] colHead={"Icon","File Name","Folder Path","Length","Type"};
        jTable.setModel(new MyTableModel(null, colHead));
        jTable.getParent().setBackground(CENTER_COLOR);
    }
    
    /**
     * public methods
     */
    
    public void addDataRow(java.io.File file){
        //add file to arraylist
        arrFiles.add(file);
        
        //save size of the instance array
        FileSystemView fsv=FileSystemView.getFileSystemView();
        //do further tasks
        //////////
        //this assignement will throw FileNotFoundException. This exception occurrs as the method was not able to find out the icon file
        javax.swing.Icon icon=null;
        try{
            icon=fsv.getSystemIcon(file);
        }catch(Throwable e){
            icon=net.sf.bluex.controller.UsefulMethods.getIcon("unknownFile.gif");
        }

        //getting the fileName of the file first calculated its system name if it is empyt then the original filename will be stored
        String fileName;
        try{
            fileName=fsv.getSystemDisplayName(file);
        }catch(Throwable e){
            fileName=file.getName();
        }
        if(fileName.length()==0)
            fileName=file.getName();
        
        String description;
        try{
            description=fsv.getSystemTypeDescription(file);
        }catch(Throwable e){
            description="Unknown";
        }
        ////////////
        if(fileName.length()==0)
            fileName=file.getName();
        //set data
        Object[] data=new Object[5];
        data[0]=icon;
        data[1]=fileName;
        data[2]=file.getParent();
        data[3]=UsefulMethods.getFileSize(file.length());
        data[4]=description;
        //create a tablemodel object
        MyTableModel mtm=(MyTableModel)jTable.getModel();
        mtm.addRow(data);
    }

    /**
     * Private methods
     */
    private void deleteSelectedFile(int i){
        javax.swing.table.DefaultTableModel dtm=(javax.swing.table.DefaultTableModel)jTable.getModel();
        dtm.removeRow(i);
    }
    
    //not visible
    protected void doCmdSEast1() {
        //implemented later
    }

    //Restore button
    protected void doCmdSEast2() {
        if(jTable.getSelectedRow()!=-1){
            int[] rows=jTable.getSelectedRows();
            for(int i : rows){
                //get row
                int row=jTable.getSelectedRow();
                //add data row
                issue.addDataRow(arrFiles.get(row));
                //delete entry from this object table
                deleteSelectedFile(row);
                //delete from the arraylist
                arrFiles.remove(row);
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
