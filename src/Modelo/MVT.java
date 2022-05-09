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
    private boolean procesoNoIngresado,aux;
    // ProcesoNoIngresado es para saber si en algun tiempo un proceso no entró a memoria
    // aux será para no aumentar el tiempo y se conserve en el paso
        // Por si un espacio se desocupa o más de un proceso termina o inicia en el mismo tiempo

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
        //memoriaPrincipal.imprimir();
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
                        System.out.println("CElda borrada " + i + ": " + memoriaPrincipal.getCeldas().get(i).isAsignada());
                    }
                    for (Particiones areas : particiones) {
                        System.out.println("Particion " + areas.getNumero() + areas.getProceso());
                    }
                    particiones.removeIf( particiones -> particiones.getProceso().equals(procesoActual.getNombreProceso()));
                }
            }
            j++;
        }
    }
    
    public boolean comprobarParticiones(){
        boolean x = true;
        for (Particiones t : particiones) {
            if (t != null) {
                x = false;
            break;
            }
            j++;
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
                            System.out.println("CElda " + i + ": " + memoriaPrincipal.getCeldas().get(i).isAsignada());
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
                    System.out.println("        -----------------------------INFERIOR: " + limiteInferior );
                }
                if( i != 63){
                    if( memoriaPrincipal.getCeldas().get(i).isAsignada() == false && memoriaPrincipal.getCeldas().get(i+1).isAsignada() == true){
                        limiteSuperior = i+1;
                        superior = true;
                        System.out.println("        -----------------------------SuPERIOR: " + limiteSuperior );
                    }
                }else{
                    if(superior ==false){
                        if(memoriaPrincipal.getCeldas().get(63).isAsignada() == false && memoriaPrincipal.getCeldas().get(62).isAsignada() == false){
                            limiteSuperior = 64;
                            superior  = true;
                            System.out.println("        -----------------------------SuPERIOR: " + limiteSuperior );
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
