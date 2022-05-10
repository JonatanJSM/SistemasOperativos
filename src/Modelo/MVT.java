/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Modelo;

import java.util.ArrayList;
import java.util.Collection;
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
        memoriaPrincipal.setTamanio(64);
        areaslibres.add(new AreasLibres(1, 10, 54, true)); //Se agrega la primera área libre que se indica en la descrición del proyecto
        for(int i  = 0; i<10; i++){
            memoriaPrincipal.getCeldas().get(i).setAsignada(true);
        }
        contadorTiempos = 0;
        leerProcesos();
    }

    public MemoriaPrincipal getMemoriaPrincipal() {
        return memoriaPrincipal;
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
        procesos.add(new Proceso("A", 8, 1, 7,false));
        procesos.add(new Proceso("B", 14, 2, 7,false));
        procesos.add(new Proceso("C", 18, 3, 4,false));
        procesos.add(new Proceso("D", 6, 4, 6,false));
        procesos.add(new Proceso("E", 14, 5, 5,false));
    }

    public void actualizarEspera(){
        for(Proceso procesoActual : this.procesos){
            if(procesoActual.isEnEspera()){
                procesoActual.setTiempoLLegada(contadorTiempos);
                procesoActual.setDuracionTotal();
            }
        }
    }

    public void gestorTiempo(){
        //AGREGAr LAS AREAS LIBRES
        agregarAreasLibres();
           
        boolean empty = true;

        empty = comprobarParticiones();
        
        limpiarMemoriaProcesosTerminados();
        
        actualizarEspera();
        
        agregarProcesosEntrantes(empty);
        
        memoriaPrincipal.agregarElementos(areaslibres, particiones);
    }

    public static Particiones findParticionByProceso(Collection<Particiones> listCarnet, String codeIsIn) {
        return listCarnet.stream().filter(carnet -> codeIsIn.equals(carnet.getProceso())).findFirst().orElse(null);
    }
    
    public void limpiarMemoriaProcesosTerminados(){
        for (Proceso procesoActual : this.procesos) {
            if(procesoActual.isActivo() == true){
                procesoActual.setEnEspera(false);
                if(procesoActual.getDuracionTotal() <= contadorTiempos){
                    procesoActual.setActivo(false);
                    Particiones particionPorBorrar = findParticionByProceso(particiones, procesoActual.getNombreProceso());
                    for(int i  = (particionPorBorrar.getLocalidad()) ; i < particionPorBorrar.getLocalidad() + particionPorBorrar.getTamanio(); i++){
                        memoriaPrincipal.getCeldas().get(i).setAsignada(false);
                    }
                    particiones.removeIf( particiones -> particiones.getProceso().equals(procesoActual.getNombreProceso()));
                }
            }
        }
    }
    
    public boolean comprobarParticiones(){
        boolean x = true;
        for (Particiones t : particiones) {
            if (t != null) {
                x = false;
            break;
            }
        }
        return x;
    }
    
    public void agregarProcesosEntrantes(boolean empty){
        //GUARDAR LOS VALORES DEL NUMERO, LOCALIDAD y TAMANIO DE LA ULTIMA PARTICION
        int ultNumParticion = 0;
        int ultLocParticion = areaslibres.get(areaslibres.size()-1).getLocalidad(); // es la localidad 10 donde termina el SO
        int ultTamParticion = 0;
        
        for(Proceso procesoActual : this.procesos){
            if(procesoActual.getTiempoLLegada() == contadorTiempos){
                if(! empty){
                    for (AreasLibres aLibreActual : areaslibres) {
                        if (aLibreActual.getTamanio() >= procesoActual.getTamanio()){
                                ultNumParticion = particiones.get(particiones.size() - 1).getNumero();
                                ultLocParticion = aLibreActual.getLocalidad();
                                ultTamParticion = particiones.get(particiones.size() - 1).getTamanio();
                        }
                        break;
                    }
                }
                    //SE COMPRUEBA QUE HAYA ESPACIO SUFICIENTE EN MEMORIA
                    if( procesoActual.getTamanio() <= memoriaPrincipal.getAreaDisponible()){
                        procesoActual.setActivo(true); 
                        particiones.add(new Particiones(ultNumParticion + 1, procesoActual.getNombreProceso(), ultLocParticion, 
                        procesoActual.getTamanio(), true));
                        //SE MARCAN LAS CELDAS OCUPADAS:
                        for(int i  = ultLocParticion; i < ultLocParticion + procesoActual.getTamanio(); i++){
                            memoriaPrincipal.getCeldas().get(i).setAsignada(true);
                        }
                    }else{
                        //SE PONE EL PROCESO EN ESPERA
                        procesoActual.setEnEspera(true);
                    }
            }
            agregarAreasLibres();
        }
    }

    public void agregarAreasLibres(){
        boolean inferior = false;
        boolean superior = false;
        int limiteInferior = 0;
        int limiteSuperior = 0;
        int numPart = 1;
        areaslibres.clear();
        for (int i = 0; i < memoriaPrincipal.getCeldas().size(); i++) {
            //arreglar los limites
            if(i!= memoriaPrincipal.getCeldas().size()){
                if( memoriaPrincipal.getCeldas().get(i).isAsignada() == true && memoriaPrincipal.getCeldas().get(i+1).isAsignada() == false){
                    limiteInferior = i+1;
                    inferior = true;
                }
                if( i != 63){
                    if( memoriaPrincipal.getCeldas().get(i).isAsignada() == false && memoriaPrincipal.getCeldas().get(i+1).isAsignada() == true){
                        limiteSuperior = i+1;
                        superior = true;
                    }
                }else{
                    if(superior ==false){
                        if(memoriaPrincipal.getCeldas().get(63).isAsignada() == false && memoriaPrincipal.getCeldas().get(62).isAsignada() == false){
                            limiteSuperior = 64;
                            superior  = true;
                        }
                    }
                }
            }
            if( inferior ==true && superior == true ){
                if(limiteSuperior > limiteInferior){
                    areaslibres.add(new AreasLibres(numPart, limiteInferior, limiteSuperior - limiteInferior, true));
                }else{
                    areaslibres.add(new AreasLibres(numPart, limiteSuperior, limiteInferior - limiteSuperior, true));
                }
                inferior =false;
                superior = false;
                numPart++;
            }

        }
    }
}
