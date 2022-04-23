
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author Jonatan, Jesús, Angélica, Natali
 */
public class MemoriaPrincipal {
    private int tamanio;
    private ArrayList<Celdas> celdasMemoria;
    private int areaDisponible;

    public MemoriaPrincipal(){
        this.celdasMemoria = new ArrayList <>();
        for(int i = 0; i <= 64; i++){
            celdasMemoria.add(new Celdas());
        }
        this.areaDisponible = 54;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

    public ArrayList<Celdas> getCeldas() {
        return celdasMemoria;
    }

    public void setAreaDisponible(int areaDisponible) {
        this.areaDisponible = areaDisponible;
    }

    public int getAreaDisponible() {
        int area = 0;
        for (Celdas celdas : celdasMemoria) {
            if (celdas.isAsignada() ==false ){
                area++;
            }
        } 

        setAreaDisponible(area);
        return area;
    }
}
