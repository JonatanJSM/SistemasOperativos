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
        //leerProcesos();
    }

    public ArrayList<AreasLibres> getAreasLibres(){
        return this.areaslibres;
    }

    public ArrayList<Particiones> getParticiones(){
        return this.particiones;
    }

    public void aumentaTiempo(){
        this.contadorTiempos++;
    }

    public int getTiempo(){
        return this.contadorTiempos;
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

    public void gestorTiempo(){
        switch(this.contadorTiempos){
            case 1:
                procesos.add(new Proceso("A", 8, 1, 7));
                particiones.add(new Particiones(1, "A", 10, 8, true));
                //areaslibres.add(new AreasLibres(1, 20, 20, true));
            break;
            case 2:
                procesos.add(new Proceso("B", 14, 2, 7));
                particiones.add(new Particiones(2, "B", 18, 14, true));
                //procesos.add(new Proceso("C", 18, 3, 4));
                //procesos.add(new Proceso("D", 6, 4, 6));
                //procesos.add(new Proceso("E", 14, 5, 5));
            break;
            case 3:
                procesos.add(new Proceso("C", 18, 3, 4));
                particiones.add(new Particiones(3, "C", 32, 18, true));
            break;
            case 4:
                procesos.add(new Proceso("D", 6, 4, 6));
                particiones.add(new Particiones(4, "D", 50, 6, true));
            break;
            case 5:

            break;
            case 6:
            
            break;
            case 7: //TERMINA EL PROCESO C
                particiones.removeIf( particiones -> particiones.getProceso().equals("C"));
            break;
            case 8: //TERMINA EL PROCESO A
                particiones.removeIf( particiones -> particiones.getProceso().equals("A"));
            break;
            case 9: //TERMINA EL PROCESO B
                particiones.removeIf( particiones -> particiones.getProceso().equals("B"));
            break;
        }
    }
}
