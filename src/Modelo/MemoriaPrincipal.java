
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Jonatan, Jesús, Angélica, Natali
 */
public class MemoriaPrincipal {
    private int tamanio;
    private ArrayList<Celdas> celdasMemoria;
    private int areaDisponible;
    private ArrayList<AreasProceso> elementos;

    public MemoriaPrincipal(){
        this.celdasMemoria = new ArrayList <>();
        for(int i = 0; i < 64; i++){
            celdasMemoria.add(new Celdas());
        }
        this.areaDisponible = 54;
        elementos = new ArrayList<>();
    }

    public int getTamanio() {
        return tamanio;
    }

    public ArrayList<AreasProceso> getElementos() {
        return elementos;
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
    
    public void agregarElementos(ArrayList <AreasLibres> areaslibres, ArrayList <Particiones> particiones){
        elementos.clear();
        for(Particiones particionActual : particiones){
                                   // localidad, tamaño, estado, nombre
            elementos.add(new AreasProceso(particionActual.getLocalidad(),particionActual.getTamanio(),true,particionActual.getProceso()));
        }
        
        for(AreasLibres areaLibreActual : areaslibres){
                                   // localidad, tamaño, estado, nombre
            elementos.add(new AreasProceso(areaLibreActual.getLocalidad(),areaLibreActual.getTamanio(),true,"Area Libre"));
        }
        elementos.add(new AreasProceso(areaslibres.get(areaslibres.size()-1).getLocalidad(),areaslibres.get(areaslibres.size()-1).getTamanio(),true,"Area Libre"));
        insercion();
        
        System.out.println(elementos);
    }
    
    public void insercion(){
        int i,k;
        AreasProceso aux = new AreasProceso();
        for (i=1;i<elementos.size();i++){
          aux = elementos.get(i);
          k = i-1;
          while(k>=0 && aux.compareTo(elementos.get(k))){
            elementos.set(k+1,elementos.get(k));
            k--;
          }
          elementos.set(k+1, aux);
        } 
        Collections.reverse(elementos);
    }
    
    public void imprimir(){
        System.out.println(elementos);
    }
}
