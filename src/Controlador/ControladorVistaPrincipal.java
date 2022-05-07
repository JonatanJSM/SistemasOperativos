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
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import javax.swing.JTable;


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
        
        try {
            limiteIndices();
        } catch (IOException ex) {
            //Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("AREA DISPONIBLE: " + mvt.getMemoriaPrincipal().getAreaDisponible());
    }
    
    

    //tabla1 es areas libres
    public void actualizaALibres(){
        //tablaModelo.setDefaultRenderer(Object.class, new MyTableCellRender());
        vista.getTablamodelo1().setRowCount(0);
        vista.getjTable1().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        int[] anchos = {30, 30, 30, 30};
        
        //int indiceALibre = 0;
        for(AreasLibres areaActual : mvt.getAreasLibres()){
            if(areaActual.getEstado() == true){
                vista.getTablamodelo1().addRow(new Object[] {areaActual.getNumero(), 
                                                areaActual.getLocalidad() ,
                                                areaActual.getTamanio(), "D"});
            }else{
                vista.getTablamodelo1().addRow(new Object[] {areaActual.getNumero(), 
                                                areaActual.getLocalidad() ,
                                                areaActual.getTamanio(), "A"});
            }
        }
        //tablaModelo.addRow(new Object[] {this.mvt.getTiempo(), 10 ,54, "D"});
        //AGREGAR EL DEFAULTRENDER PARA PODER CAMBIAR EL COLOR DE LAS FILAS DE ALGUNA TABLA
        //this.vista.getjTable1().setDefaultRenderer(Object.class, new MyTableCellRender());
        
        for (int i = 0; i < this.vista.getjTable1().getColumnCount(); i++) {
            this.vista.getjTable1().getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    //tabla2 es particiones
    public void actualizaParticiones(){
    vista.getTablaModelo2().setRowCount(0);
        int[] anchosP = {50, 200, 200, 200,100};
        for (int i = 0; i < this.vista.getjTable2().getColumnCount(); i++) {
            this.vista.getjTable2().getColumnModel().getColumn(i).setPreferredWidth(anchosP[i]);
        }    

        for(Particiones particionActual : mvt.getParticiones()){
            if(particionActual.getEstado() == true){
                vista.getTablaModelo2().addRow(new Object[] {particionActual.getNumero(), 
                                                particionActual.getLocalidad() ,
                                                particionActual.getTamanio(), "D" ,
                                                particionActual.getProceso()});
            }else{
                vista.getTablaModelo2().addRow(new Object[] {particionActual.getNumero(), 
                                                particionActual.getLocalidad() ,
                                                particionActual.getTamanio(), "A" ,
                                                particionActual.getProceso()});
            }
        }
        //tparticiones.addRow(new Object[] {"","","","",""}); //fila de prueba
    }

    //tabla3 es memoria

    public void actualizaMemoria(){

        //tablaModelo.setDefaultRenderer(Object.class, new MyTableCellRender());
            vista.getTablaModelo3().setRowCount(0);
            int[] anchos = {100};
                this.vista.getjTable1().getColumnModel().getColumn(0).setMinWidth(anchos[0]);

            vista.getTablaModelo3().addRow(new Object[] {"SO"});
            this.vista.getjTable3().setRowHeight(0,90);
//            for(Particiones particionActual : mvt.getParticiones()){
//                vista.getTablaModelo3().addRow(new Object[] {particionActual.getProceso()});
//            }

            for(int i = 0 ;i<mvt.getMemoriaPrincipal().getElementos().size()-1;i++){
                vista.getTablaModelo3().addRow(new Object[] {mvt.getMemoriaPrincipal().getElementos().get(i).getNomAux()});
                System.out.print("hola "+ mvt.getMemoriaPrincipal().getElementos().get(i).getNomAux());
            }
            //vista.getTablaModelo3().addRow(new Object[] {mvt.getMemoriaPrincipal().getElementos().get(0).getNomAux()});
            actuliazarAlturaCeldas();
           // System.out.println("Columnas"+vista.getjTable3().getRowCount());
            //AGREGAR EL DEFAULTRENDER PARA PODER CAMBIAR EL COLOR DE LAS FILAS DE ALGUNA TABLA
            this.vista.getjTable3().setDefaultRenderer(Object.class, new MyTableCellRender());
    }

    //Iniciar tablas
    public void iniciarTablas(){
       crearTablaAreasLibres();
       crearTablaParticiones();
       crearTablaMemoria();
    }
    
    public void  crearTablaAreasLibres(){
        //SE CREA LA TABLA DE AREAS LIBRES 

        vista.getjTable1().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int[] anchos = {30, 30, 30, 30};
        for (int i = 0; i < this.vista.getjTable1().getColumnCount(); i++) {
            this.vista.getjTable1().getColumnModel().getColumn(i).setMinWidth(anchos[i]);
        }

        if(mvt.getAreasLibres().get(0).getEstado() == true){
            vista.getTablamodelo1().addRow(new Object[] {mvt.getAreasLibres().get(0).getNumero(), 
                                            mvt.getAreasLibres().get(0).getLocalidad() ,
                                            mvt.getAreasLibres().get(0).getTamanio(), "D"});
        }else{
            vista.getTablamodelo1().addRow(new Object[] {mvt.getAreasLibres().get(0).getNumero(), 
                mvt.getAreasLibres().get(0).getLocalidad() ,
                mvt.getAreasLibres().get(0).getTamanio(), "A"});
        }
    }
    
    public void crearTablaParticiones(){
         //SE CREA LA TABLA DE PARTICIONES

        int[] anchosP = {50, 200, 200, 200,100};
        for (int i = 0; i < this.vista.getjTable1().getColumnCount(); i++) {
            this.vista.getjTable1().getColumnModel().getColumn(i).setMinWidth(anchosP[i]);
        }    
       
       
    }
    
    public void crearTablaMemoria(){
         //SE CREA LA TABLA DE LA MEMORIA
        
        //tablaModelo.setDefaultRenderer(Object.class, new MyTableCellRender());
   
        //Se establece el ancho de la columna de la tabla
            this.vista.getjTable1().getColumnModel().getColumn(0).setMinWidth(100);

        //tablaModelo.addRow(new Object[] {"SO"}); fila de prueba
        
        //AGREGAR EL DEFAULTRENDER PARA PODER CAMBIAR EL COLOR DE LAS FILAS DE ALGUNA TABLA
        this.vista.getjTable3().setDefaultRenderer(Object.class, new MyTableCellRender());

    }
    
    public void actuliazarAlturaCeldas(){
        for(int i = 0; i<vista.getjTable3().getRowCount(); i++){
           this.vista.getjTable3().setRowHeight(i+1,mvt.getMemoriaPrincipal().getElementos().get(i).getTamanio()*9);
           System.out.println("Tamaño :" +mvt.getMemoriaPrincipal().getElementos().get(i).getTamanio());
        }
    }
    
    public void limiteIndices() throws IOException{
        BufferedImage image = ImageIO.read(new File("fondo.png"));
        Graphics g = image.getGraphics();
        g.setFont(new Font("Arial",Font.BOLD,12));
        g.setColor(Color.black);
        g.drawString("0", 15, 15);
        g.drawString("10", 15, 95);
        g.drawString("64", 15, 570);
        g.dispose();
        ImageIO.write(image, "png", new File("test.png"));
        agregarImagen();
    }
    
    public void agregarImagen(){
        this.vista.getjLabel4().setIcon(new ImageIcon("test.png"));
    }
}
