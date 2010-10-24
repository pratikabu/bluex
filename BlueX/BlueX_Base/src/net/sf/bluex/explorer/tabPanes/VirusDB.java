/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.explorer.tabPanes;

import net.sf.bluex.explorer.components.MyTableModel;


/**
 *
 * @author Blue
 */
public class VirusDB extends net.sf.bluex.explorer.abstractClasses.AbstractTabPanel{
    
    //private objects
    
    /**
     * constructor of the issue class
     */
    public VirusDB(){
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
        this.setPanelHead("Virus Database");
        //set command buttons text
        this.setCmdSEast1Text("Add");
        this.setCmdSEast2Text("Delete");
        this.setCmdSWest1Text("Add From File");
        //set not visible cmdSWest2
        this.setCmdSWest2Visible(false);
    }
    
    private void setTableHeaders(){
        String[] colHead={"File Name","Type"};
        jTable.setModel(new MyTableModel(null, colHead));
        jTable.getParent().setBackground(CENTER_COLOR);
    }
    
    /**
     * public methods
     */
    
    public void addDataRow(String fileName){
        Object[] data=new Object[2];
        //set data
        data[0]=fileName;
        data[1]="Simple File";
        
        //create a tablemodel object
        javax.swing.table.DefaultTableModel dtm=(javax.swing.table.DefaultTableModel)jTable.getModel();
        dtm.addRow(data);
    }

    /**
     * Private methods
     */
    private void deleteSelectedFile(int i){
//        javax.swing.JOptionPane.showMessageDialog(null, "Delete the virus manually by opening the database.txt file\n" +
//                "provided in the ECData folder.\n\n"+com.gmail.pratikabu.blueX.abstractInterfaces.AbstractExplorer.ACTIVE_PARTICIPATION);
    }
    
    //Add button
    protected void doCmdSEast1() {
        //implemented later
//        javax.swing.JOptionPane.showMessageDialog(null, "Not implemented yet. Will be implemented in next version.\n\n" +
//                com.gmail.pratikabu.blueX.abstractInterfaces.AbstractExplorer.ACTIVE_PARTICIPATION);
    }

    //Delete button
    protected void doCmdSEast2() {
            deleteSelectedFile(0);
    }

    //Add From File Button
    protected void doCmdSWest1() {
//        javax.swing.JOptionPane.showMessageDialog(null, "Not implemented yet. Will be implemented in next version.\n\n" +
//                com.gmail.pratikabu.blueX.abstractInterfaces.AbstractExplorer.ACTIVE_PARTICIPATION);
    }

    //not visible
    protected void doCmdSWest2() {}

}
