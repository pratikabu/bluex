/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.explorer.components;

import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;

public class MyTableColorRenderer extends javax.swing.JLabel
                           implements TableCellRenderer {
    Border border = null;

    public MyTableColorRenderer() {
        setOpaque(true); //MUST do this for background to show up.
    }

    public Component getTableCellRendererComponent(
                            JTable table, Object obj,
                            boolean isSelected, boolean hasFocus,
                            int row, int column) {
        if(obj!=null){
            setBackground((Color)obj);
        }else
            setBackground(Color.WHITE);
//        border = BorderFactory.createMatteBorder(2,5,2,5,Color.WHITE);
//        setBorder(border);
        return this;
    }
}