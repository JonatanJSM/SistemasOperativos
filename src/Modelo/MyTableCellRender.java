package Modelo;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;

public class MyTableCellRender extends DefaultTableCellRenderer {
    private static int contador=-1;
    
    public MyTableCellRender() 
    {
        super();
        setOpaque(true);
        contador+=1;
    } 
    
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) 
    { 
        if(contador>4&&value=="Area Libre"&&contador<12)
        {
            setForeground(Color.black);        
            setBackground(Color.cyan);            
        }    
        else
        {   //Color  
            setBackground(Color.red);    
            setForeground(Color.black);    
        } 
        setText(value !=null ? value.toString() : "");
        return this;
    }
}