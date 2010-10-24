/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.bluex.explorer.components;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import net.sf.bluex.components.BlueXStatics;

/**
 *
 * @author Blue
 */
public class MyComboBoxRenderer extends JLabel implements ListCellRenderer{

    public static final Color HIDDEN_COLOR=new Color(255,225,255);

    public MyComboBoxRenderer(){
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if(value!=null){
            if(value instanceof File){
                File file = (File)value;
                //set data
                setText(BlueXStatics.getFileName(file));
                setIcon(BlueXStatics.getIcon(file));
            }
        }else{//to show roots
            setText(BlueXStatics.getFileName(null));
            setIcon(BlueXStatics.getIcon(null));
        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }

}
