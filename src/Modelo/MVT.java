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
    private ArrayList <Proceso> procesos;
    private ArrayList <AreasLibres> areaslibres;
    private ArrayList <Particiones> particiones;
    private MemoriaPrincipal memoriaPrincipal;
    private int contadorTiempos;

    public MVT() {
        procesos = new ArrayList <>();
        areaslibres = new ArrayList <>();
        particiones = new ArrayList <>();
        memoriaPrincipal = new MemoriaPrincipal();
        areaslibres.add(new AreasLibres(1, 10, 54, true)); //Se agrega la primera área libre que se indica en la descrición del proyecto
        contadorTiempos = 0;
        leerProcesos();
    }
    
    public final void leerProcesos(){
        procesos.add(new Proceso("A", 8, 1, 7));
        procesos.add(new Proceso("B", 14, 2, 7));
        procesos.add(new Proceso("C", 18, 3, 4));
        procesos.add(new Proceso("D", 6, 4, 6));
        procesos.add(new Proceso("E", 14, 5, 5));
    }
    
    // Si se encuentra que un proceso llegó en el tiempo i, se detiene el ciclo y se retorna el valor
    public boolean detectarCambioTiempoLlegada(int i){
        boolean x = false; // false = no hubo cambios
        int j = 0;
        while(x == false){
            if(procesos.get(j).getTiempoLLegada() == i){
                x = true;
            }
        }
        return x;
    }
    
    // Cuando se crea un proceso se hace la suma del tiempo de llegada más lo que dura. Así nos dará el click en el que terminará
    // Esto evitará hacer lo que pensamos de ir restando a su tiempo y comprobar que llege a 0 para que termine
    public boolean detectarCambioTiempoSalida(int i){ // Cuando termina un proceso
        boolean x = false; // false = no hubo cambios
        int j = 0;
        while(x == false){
            if(procesos.get(j).getDuracionTotal() == i){
                x = true;
            }
        }
        return x;
    }
}
