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
public class MyListRenderer extends JLabel implements ListCellRenderer{

    public static final Color HIDDEN_COLOR=new Color(255,225,255);

    public MyListRenderer(){
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        File file = (File)value;

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        //set data
        setText(BlueXStatics.getFileName(file));
        setIcon(BlueXStatics.getIcon(file));
        if (file!=null && file.isHidden())
            setForeground(BlueXStatics.hiddenColor);

        return this;
    }

}
