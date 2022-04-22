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
    private boolean procesoNoIngresado,aux;
    // ProcesoNoIngresado es para saber si en algun tiempo un proceso no entró a memoria
    // aux será para no aumentar el tiempo y se conserve en el paso
        // Por si un espacio se desocupa o más de un proceso termina o inicia en el mismo tiempo

    public MVT() {
        procesos = new ArrayList <>();
        areaslibres = new ArrayList <>();
        particiones = new ArrayList <>();
        memoriaPrincipal = new MemoriaPrincipal();
        areaslibres.add(new AreasLibres(1, 10, 54, true)); //Se agrega la primera área libre que se indica en la descrición del proyecto
        contadorTiempos = 0;
        procesoNoIngresado = true;
        aux = true;
        leerProcesos();
    }

    public boolean isAux() {
        return aux;
    }
    
    public boolean isProcesoNoIngresado() {
        return procesoNoIngresado;
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
        while(x == false && j<procesos.size()){
            if(procesos.get(j).getTiempoLLegada() == i){
                x = true;
            }
            j++;
        }
        return x;
    }
    
    public int indiceProceso(int i){
        boolean x = false; // false = no hubo cambios
        int j = 0;
        while(x == false && j<procesos.size()){
            if(procesos.get(j).getTiempoLLegada() == i){
                x = true;
            }
            j++;
        }
        return j;
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
            j++;
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
    
    public void gestorProcesos(){ //Esto todavía está en construccion
        // Es como la función que está arriba
        // se revisa si llega proceso para esta función
        // se revisa si termina uno proceso
        // Se controla el aux ya que si un proceso puede entrar en un paso no se cambia a otro tiempo
        // Podría haber un if donde se revisa si hay proceso en espera que pueda entrar, si no hay se continua
        // otro if que revise si ya hay fragmentación
            // Si no hay se puede utilizar llegadaProceso(int tiempo, int numero)
            // si ya lo hay entonces llegadaPRocesoAreasLibresFragmentadas()
        llegadaProceso(contadorTiempos,0);
    }
    
    // ESto solo es cuando no se ha terminado algún proceso, y todos llegan seguido
    // Cuando hay fragmentacion hay que ver si el proceso da en algun fragmento
    public void llegadaProceso(int tiempo, int numero){
        int j = indiceProceso(tiempo)-1;
        if(areaslibres.get(0).getTamanio()>procesos.get(j).getTamanio()){
            particiones.add(new Particiones(numero,procesos.get(j).getNombreProceso(),areaslibres.get(0).getLocalidad(),procesos.get(j).getTamanio(),true));
            int x = areaslibres.get(0).getTamanio();
            areaslibres.get(0).setTamanio(x - procesos.get(j).getTamanio());
            x = areaslibres.get(0).getLocalidad();
            areaslibres.get(0).setLocalidad(x + procesos.get(j).getTamanio());
            procesos.remove(j);
        }else{
            procesoNoIngresado = false;
        }
    }
    
    public void terminaProceso(int tiempo){
        // Cuando un proceso acaba se hacen más filas en la tabla de áreas libres
        // Se debería revisar si hay fragmentos nuevos que estén pegados a otros para unirlos
        // Si etamos en un área libre y sumamos su localidas más tamaño esto nos dará la localidad de la siguinte área libre que exista
        // con la localidad podemos ver si un área libre está detrás o adelante de otra área
    }
    
    public void llegadaProcesoAreasLibresFragmentadas(){
        //Cuando un proceso acaba y no han temrinado de llegar más procesos o están a la espera de un área libre
        // Se necesitan revisar las áreas libres diponibles, ya que no solo habrá una
    }
    
    public boolean procesosEnEspera(){
        // Determinar si ya hay espacio para los que no han entrado
        // se podría utilizar con llegadaProcesoAreasLibresFragmentadas()
        return false;
    }
    
}
