/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Controlador;

import Modelo.AreasLibres;
import Modelo.MVT;
import Modelo.MyTableCellRender;
import Modelo.Particiones;
import Vista.VistaPrincipal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jonatan, Natali, Angélica, Jesús
 */
public class ControladorVistaPrincipal implements ActionListener {
    private VistaPrincipal vista;
    private MVT mvt;

    public ControladorVistaPrincipal(MVT modelo, VistaPrincipal vista){
        this.vista = vista;
        this.mvt = modelo;
        this.vista.getjButton1().addActionListener(this);
        iniciarTablas();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        mvt.aumentaTiempo();
        mvt.gestorTiempo();
        vista.getjButton1().setText("Paso " + mvt.getTiempo());
        actualizaALibres();
        actualizaParticiones();
        actualizaMemoria();
        System.out.println("AREA DISPONIBLE: " + mvt.getMemoriaPrincipal().getAreaDisponible());
    }

    //tabla1 es areas libres
    public void actualizaALibres(){
        DefaultTableModel tablaModelo = new DefaultTableModel();
        //tablaModelo.setDefaultRenderer(Object.class, new MyTableCellRender());
        tablaModelo.addColumn("No");
        tablaModelo.addColumn("Localidad");
        tablaModelo.addColumn("Tamaño");
        tablaModelo.addColumn("Estado");
        
        vista.getjTable1().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        int[] anchos = {30, 30, 30, 30};
        
        //int indiceALibre = 0;
        for(AreasLibres areaActual : mvt.getAreasLibres()){
            if(areaActual.getEstado() == true){
                tablaModelo.addRow(new Object[] {areaActual.getNumero(), 
                                                areaActual.getLocalidad() ,
                                                areaActual.getTamanio(), "D"});
            }else{
                tablaModelo.addRow(new Object[] {areaActual.getNumero(), 
                                                areaActual.getLocalidad() ,
                                                areaActual.getTamanio(), "A"});
            }
        }
        //tablaModelo.addRow(new Object[] {this.mvt.getTiempo(), 10 ,54, "D"});
        
        //AGREGAR EL DEFAULTRENDER PARA PODER CAMBIAR EL COLOR DE LAS FILAS DE ALGUNA TABLA
        //this.vista.getjTable1().setDefaultRenderer(Object.class, new MyTableCellRender());
        this.vista.getjTable1().setModel(tablaModelo);
        for (int i = 0; i < this.vista.getjTable1().getColumnCount(); i++) {
            this.vista.getjTable1().getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    //tabla2 es particiones
    public void actualizaParticiones(){
        DefaultTableModel tablaModelo = new DefaultTableModel();
        tablaModelo.addColumn("No");
        tablaModelo.addColumn("Localidad");
        tablaModelo.addColumn("Tamaño");
        tablaModelo.addColumn("Estado");
        tablaModelo.addColumn("Proceso");
        
        int[] anchosP = {50, 200, 200, 200,100};
        for (int i = 0; i < this.vista.getjTable1().getColumnCount(); i++) {
            this.vista.getjTable1().getColumnModel().getColumn(i).setMinWidth(anchosP[i]);
        }    

        for(Particiones particionActual : mvt.getParticiones()){
            if(particionActual.getEstado() == true){
                tablaModelo.addRow(new Object[] {particionActual.getNumero(), 
                                                particionActual.getLocalidad() ,
                                                particionActual.getTamanio(), "D" ,
                                                particionActual.getProceso()});
            }else{
                tablaModelo.addRow(new Object[] {particionActual.getNumero(), 
                                                particionActual.getLocalidad() ,
                                                particionActual.getTamanio(), "A" ,
                                                particionActual.getProceso()});
            }
        }
        //tparticiones.addRow(new Object[] {"","","","",""}); //fila de prueba
        this.vista.getjTable2().setModel(tablaModelo);
    }

    //tabla3 es memoria

    public void actualizaMemoria(){
        DefaultTableModel tablaModelo = new DefaultTableModel();
        //tablaModelo.setDefaultRenderer(Object.class, new MyTableCellRender());
            tablaModelo.addColumn("SO");
            
            int[] anchos = {100};
                this.vista.getjTable1().getColumnModel().getColumn(0).setMinWidth(anchos[0]);

            tablaModelo.addRow(new Object[] {"SO"});
            
            for(Particiones particionActual : mvt.getParticiones()){
                tablaModelo.addRow(new Object[] {particionActual.getProceso()});
                
            }
            
            //AGREGAR EL DEFAULTRENDER PARA PODER CAMBIAR EL COLOR DE LAS FILAS DE ALGUNA TABLA
            this.vista.getjTable3().setDefaultRenderer(Object.class, new MyTableCellRender());
            this.vista.getjTable3().setModel(tablaModelo);
            for(int i = 0; i<this.vista.getjTable3().getRowCount()-1;i++){
                this.vista.getjTable3().setRowHeight(i, mvt.getParticiones().get(i).getTamanio()*7);
            }
    }

    

    //Iniciar tablas
    public void iniciarTablas(){
       crearTablaAreasLibres();
       crearTablaParticiones();
       crearTablaMemoria();
    }
    
    public void  crearTablaAreasLibres(){
                //SE CREA LA TABLA DE AREAS LIBRES 
        DefaultTableModel aLibres = new DefaultTableModel();
        aLibres.addColumn("No");
        aLibres.addColumn("Localidad");
        aLibres.addColumn("Tamaño");
        aLibres.addColumn("Estado");

        vista.getjTable1().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int[] anchos = {30, 30, 30, 30};
        for (int i = 0; i < this.vista.getjTable1().getColumnCount(); i++) {
            this.vista.getjTable1().getColumnModel().getColumn(i).setMinWidth(anchos[i]);
        }

        if(mvt.getAreasLibres().get(0).getEstado() == true){
            aLibres.addRow(new Object[] {mvt.getAreasLibres().get(0).getNumero(), 
                                            mvt.getAreasLibres().get(0).getLocalidad() ,
                                            mvt.getAreasLibres().get(0).getTamanio(), "D"});
        }else{
            aLibres.addRow(new Object[] {mvt.getAreasLibres().get(0).getNumero(), 
                mvt.getAreasLibres().get(0).getLocalidad() ,
                mvt.getAreasLibres().get(0).getTamanio(), "A"});
        }
        this.vista.getjTable1().setModel(aLibres);
    }
    
    public void crearTablaParticiones(){
         //SE CREA LA TABLA DE PARTICIONES
        DefaultTableModel tparticiones = new DefaultTableModel();
        tparticiones.addColumn("No");
        tparticiones.addColumn("Localidad");
        tparticiones.addColumn("Tamaño");
        tparticiones.addColumn("Estado");
        tparticiones.addColumn("Proceso");
        
        int[] anchosP = {50, 200, 200, 200,100};
        for (int i = 0; i < this.vista.getjTable1().getColumnCount(); i++) {
            this.vista.getjTable1().getColumnModel().getColumn(i).setMinWidth(anchosP[i]);
        }    
        tparticiones.addRow(new Object[] {"","","","",""}); //fila de prueba
        this.vista.getjTable2().setModel(tparticiones);
    }
    
    public void crearTablaMemoria(){
         //SE CREA LA TABLA DE LA MEMORIA
            DefaultTableModel tablaModelo = new DefaultTableModel();
        //tablaModelo.setDefaultRenderer(Object.class, new MyTableCellRender());
        tablaModelo.addColumn("SO");
        //Se establece el ancho de la columna de la tabla
            this.vista.getjTable1().getColumnModel().getColumn(0).setMinWidth(100);

        //tablaModelo.addRow(new Object[] {"SO"}); fila de prueba
        
        //AGREGAR EL DEFAULTRENDER PARA PODER CAMBIAR EL COLOR DE LAS FILAS DE ALGUNA TABLA
        this.vista.getjTable3().setDefaultRenderer(Object.class, new MyTableCellRender());
        this.vista.getjTable3().setModel(tablaModelo);
    }
}
