package rbsys;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (value != null) {
            if ("inactive".equalsIgnoreCase(value.toString())) {
                cellComponent.setBackground(Color.RED);
                cellComponent.setForeground(Color.WHITE);
            } else if ("active".equalsIgnoreCase(value.toString())) {
                cellComponent.setBackground(Color.GREEN);
                cellComponent.setForeground(Color.BLACK);
            } else {
                cellComponent.setBackground(Color.WHITE);
                cellComponent.setForeground(Color.BLACK);
            }
        }
        
        return cellComponent;
    }
}
