/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Modelo;

import java.util.ArrayList;

/**
 *
 * @author Angélica, Natali, Jesus, Jonatan
 */
public class MVT {
    //Está clase es el que se encargaría de administar todos los procesos 
    private ArrayList <AreasLibres> areaslibres;
    private ArrayList <Particiones> particiones;
    private MemoriaPrincipal memoriaPrincipal;

    public MVT() {
        areaslibres = new ArrayList <>();
        particiones = new ArrayList <>();
        memoriaPrincipal = new MemoriaPrincipal();
        areaslibres.add(new AreasLibres(1, 10, 54, true));
    }
    
}
