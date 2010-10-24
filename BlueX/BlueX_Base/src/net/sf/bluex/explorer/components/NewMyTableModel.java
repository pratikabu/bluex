package net.sf.bluex.explorer.components;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import net.sf.bluex.components.BlueXFile;

/**
 * 
 * @author Blue
 */
public class NewMyTableModel extends AbstractTableModel {

    private Vector<Vector> files = new Vector<Vector>();
    private String[] cellHeaders={"Name", "Size", "Type"};
    
    public NewMyTableModel(){
    }

    public NewMyTableModel(String[] cellHeaders){
        this.cellHeaders=cellHeaders;
    }

    public String[] getCellHeaders() {
        return cellHeaders;
    }

    public int getRowCount() {
        return files.size();
    }

    public int getColumnCount() {
        return cellHeaders.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (files.size() <= rowIndex) {
            return null;
        }
        return files.elementAt(rowIndex).elementAt(columnIndex);
    }

    /**
     * It will return the BlueXFile object if available else return false if out of bounds :)
     * @param rowIndex
     * @return
     */
    public BlueXFile getFileAt(int rowIndex){
        Object obj=getValueAt(rowIndex, 0);
        if(obj==null)
            return null;
        else
            return (BlueXFile)obj;
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public String getColumnName(int column) {
        return cellHeaders[column];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void addRow(Vector newRow) {
        files.add(newRow);
        fireTableRowsInserted(files.size(), files.size());
    }

    public void clear(){
        int oldSize = files.size();
        files.clear();
        this.fireTableRowsDeleted(0, oldSize);
    }
}