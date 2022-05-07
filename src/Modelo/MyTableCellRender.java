package Modelo;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;

public class MyTableCellRender extends DefaultTableCellRenderer 
{
    public MyTableCellRender() 
    {
        super();
        setOpaque(true);
    } 
    
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) 
    { 
        if( (String)table.getValueAt(row, 0) == "Z")
        {
            setForeground(Color.black);        
            setBackground(Color.red);            
        }    
        else
        {    
            setBackground(Color.red);    
            setForeground(Color.black);    
        } 
        setText(value !=null ? value.toString() : "");
        return this;
    }
}