/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.bluex.explorer.components;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Blue
 */
public class MyTable extends JTable {

    private MyTableCellRenderer renderer = new MyTableCellRenderer();
    private NewMyTableModel mtm;

    public MyTable() {
        mtm = new NewMyTableModel();
        init();
    }
    
    public MyTable(String[] cellHeaders){
        mtm=new NewMyTableModel(cellHeaders);
        init();
    }

    private void init(){
        this.setModel(mtm);
        resizeTable();

        //table inits
        this.setShowVerticalLines(false);
        this.setShowHorizontalLines(false);
        this.setRowHeight(19);
        this.setAutoResizeMode(MyTable.AUTO_RESIZE_OFF);
//        this.setDragEnabled(true);
        this.getTableHeader().setReorderingAllowed(false);
    }

    @Override
    public NewMyTableModel getModel() {
        return mtm;
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        return renderer;
    }

    /**
     * resize the table
     */
    private void resizeTable() {
        //rezise
        int size = 100;

        if (mtm.getCellHeaders().length == 3) {
            size = 400;
            this.getColumnModel().getColumn(0).setPreferredWidth(size);
            size = 100;
            this.getColumnModel().getColumn(1).setPreferredWidth(size);
            size = 150;
            this.getColumnModel().getColumn(2).setPreferredWidth(size);
        } else {
            size=200;
            this.getColumnModel().getColumn(0).setPreferredWidth(size);
            size=300;
            this.getColumnModel().getColumn(1).setPreferredWidth(size);
            size=100;
            this.getColumnModel().getColumn(2).setPreferredWidth(size);
            this.getColumnModel().getColumn(3).setPreferredWidth(size);
        }
    }
}
