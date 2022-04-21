/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package proyectosistemasoperativos;

import Controlador.ControladorVistaPrincipal;
import Modelo.MVT;
import Vista.VistaPrincipal;

/**
 *
 * @author Jonatan, Angélica, Natali, Jesus
 */
public class ProyectoSistemasOperativos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MVT mvt = new MVT();
        VistaPrincipal vista = new VistaPrincipal();
        ControladorVistaPrincipal control = new ControladorVistaPrincipal(mvt, vista);
        vista.setVisible(true);
    }
    
}
