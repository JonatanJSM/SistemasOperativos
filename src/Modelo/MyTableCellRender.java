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
        if(value.equals("SO")){
            setForeground(Color.white);
            setBackground(new Color(0, 0, 205));
        }else{
           if(contador>4&&value=="Area Libre"&&contador<12){
               setForeground(Color.white);
               setBackground(new Color(255, 0, 0));
           }else{   //Color
               setBackground(new Color(30, 144, 255));
               setForeground(Color.black);
           } 
        }

        setText(value !=null ? value.toString() : "");
        return this;
       }
}