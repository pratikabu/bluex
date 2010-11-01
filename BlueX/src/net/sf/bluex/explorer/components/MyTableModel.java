/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.explorer.components;

import java.awt.Color;
import java.util.Vector;

/**
 *
 * @author Blue
 */
public class MyTableModel extends javax.swing.table.DefaultTableModel{
    
    /**
     * Empty Constructor
     */
    public MyTableModel(){
        super();
    }
    
    public MyTableModel(Object[][] data,String[] columnNames){
        super(data, columnNames);
    }
    
    public MyTableModel(Vector data, Vector columnNames) {
        super(data, columnNames);
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.
     */
    public Class getColumnClass(int c) {
        Object obj=getValueAt(0, c);
        if(obj!=null)
            return obj.getClass();
        else
            return Color.class;
    }

    public boolean isCellEditable(int row, int col) {
        //make the cell not editable
        return false;
    }
    
    /**
     * create a my
     */
}
