package net.sf.bluex.explorer.components;

import java.awt.Color;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import net.sf.bluex.components.BlueXFile;
import net.sf.bluex.components.BlueXStatics;

/**
 *
 * @author Blue
 */
public class MyTableCellRenderer extends DefaultTableCellRenderer {
    private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

    public MyTableCellRenderer() {
        super();
    }

    private static Border getNoFocusBorder() {
        if (System.getSecurityManager() != null) {
            return SAFE_NO_FOCUS_BORDER;
        } else {
            return noFocusBorder;
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	setFont(table.getFont());
        setValue(value);

        if (column == 0 && isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }

        if (column == 0) {
            BlueXFile f = (BlueXFile) value;
            setIcon(BlueXStatics.getIcon(f.getFile()));
            if (f.getFile().isHidden())
                setForeground(BlueXStatics.hiddenColor);
        } else {
            setIcon(null);
            setForeground(table.getForeground());
        }

        if (hasFocus) {
            Border border = null;
            if (isSelected) {
                border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
            }
            if (border == null) {
                border = UIManager.getBorder("Table.focusCellHighlightBorder");
            }
            setBorder(border);

	    if (!isSelected && table.isCellEditable(row, column)) {
                Color col;
                col = UIManager.getColor("Table.focusCellForeground");
                if (col != null) {
                    super.setForeground(col);
                }
                col = UIManager.getColor("Table.focusCellBackground");
                if (col != null) {
                    super.setBackground(col);
                }
	    }
	} else {
            setBorder(getNoFocusBorder());
	}
        return this;
    }
}
