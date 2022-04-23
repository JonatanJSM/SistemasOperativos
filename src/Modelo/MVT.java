/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.List;
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
        for(int i  = 0; i <=10; i++){
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
        //GUARDAR LOS VALORES DEL NUMERO, LOCALIDAD y TAMANIO DE LA ULTIMA PARTICION
        int ultNumParticion = 0;
        int ultLocParticion = areaslibres.get(areaslibres.size() -1).getLocalidad(); // es la localidad 10 donde termina el SO
        int ultTamParticion = 0;
        boolean empty = true;
        for (Particiones t : particiones) {
            if (t != null) {
                empty = false;
            break;
            }
        }
        if ( ! empty){
            ultNumParticion = particiones.get(particiones.size() - 1).getNumero();
            ultLocParticion = particiones.get(particiones.size() - 1).getLocalidad();
            ultTamParticion = particiones.get(particiones.size() - 1).getTamanio();
        }

        

        //AGREGAR LAS PARTICIONES 
        for(Proceso procesoActual : this.procesos){
            if(procesoActual.getTiempoLLegada() == contadorTiempos){
                if(procesoActual.getNombreProceso() != "E"){
                    procesoActual.setActivo(true); 
                }
                //if(! empty){
                    //SE COMPRUEBA QUE HAYA ESPACIO SUFICIENTE EN MEMORIA
                    if(( procesoActual.getTamanio()) <= memoriaPrincipal.getAreaDisponible()){
                        particiones.add(new Particiones(ultNumParticion + 1, procesoActual.getNombreProceso(), ultLocParticion + ultTamParticion, 
                        procesoActual.getTamanio(), true));
                        //SE MARCAN LAS CELDAS OCUPADAS:
                        for(int i  = ultLocParticion + ultTamParticion; i <= ultLocParticion + ultTamParticion + procesoActual.getTamanio(); i++){
                            memoriaPrincipal.getCeldas().get(i).setAsignada(true);
                            System.out.println("CElda " + i + ": " + memoriaPrincipal.getCeldas().get(i).isAsignada());
                        }
                        //contadorTiempos = 0;

                    }
                //}else{
                //
                //}
                //else{//
                //    particiones.add(new Particiones(ultNumParticion + 1, procesoActual.getNombreProceso(), 
                //    areaslibres.get(areaslibres.size() -1).getLocalidad(),
                //    procesoActual.getTamanio(), true));
                //}
                
            }
            if(procesoActual.isActivo() == true){
                if(procesoActual.getDuracionTotal() <= contadorTiempos){
                
                    procesoActual.setActivo(false);
                    //particiones.indexOf(o)
                    Particiones particionPorBorrar = findByCodeIsIn(particiones, procesoActual.getNombreProceso());
                  
                    for(int i  = particionPorBorrar.getLocalidad() ; i <= particionPorBorrar.getLocalidad() + particionPorBorrar.getTamanio(); i++){
                        memoriaPrincipal.getCeldas().get(i).setAsignada(false);
                        System.out.println("CElda " + i + ": " + memoriaPrincipal.getCeldas().get(i).isAsignada());
                    }
    
                    for (Particiones areas : particiones) {
                        System.out.println("Particion " + areas.getNumero() + areas.getProceso());
                    }
                    particiones.removeIf( particiones -> particiones.getProceso().equals(procesoActual.getNombreProceso()));
                    
                }
            }
        }

        //AGREGAr LAS AREAS LIBRES
        ArrayList<Integer> limitesAreas = new ArrayList<>();
        boolean inferior = false;
        boolean superior = false;
        int limiteInferior = 0;
        int limiteSuperior = 0;
        int numPart = 1;
        areaslibres.clear();
        for (int i = 0; i <= memoriaPrincipal.getCeldas().size()-1; i++) {
            
            if( i != 0 && i!= memoriaPrincipal.getCeldas().size()-1){
                if( memoriaPrincipal.getCeldas().get(i-1).isAsignada() == true && memoriaPrincipal.getCeldas().get(i).isAsignada() == false){
                    limiteInferior = i;
                    inferior = true;
                    System.out.println("        -----------------------------INFERIOR: " + limiteInferior );
                }
/*
                if(i == 63){
                    if(memoriaPrincipal.getCeldas().get(64).isAsignada() == false && memoriaPrincipal.getCeldas().get(63).isAsignada() == false){
                        limiteSuperior = 64;
                        superior  = true;
                        System.out.println("        -----------------------------SuPERIOR: " + limiteSuperior );
                    }
                }
                */

                if( i != 63){
                    if( memoriaPrincipal.getCeldas().get(i-1).isAsignada() == false && memoriaPrincipal.getCeldas().get(i).isAsignada() == true){
                        limiteSuperior = i;
                        superior = true;
                        System.out.println("        -----------------------------SuPERIOR: " + limiteSuperior );
                        
                    }
                }else{
                    if(superior ==false){
                        if(memoriaPrincipal.getCeldas().get(64).isAsignada() == false && memoriaPrincipal.getCeldas().get(63).isAsignada() == false){
                            limiteSuperior = 65;
                            superior  = true;
                            System.out.println("        -----------------------------SuPERIOR: " + limiteSuperior );
                        }
                    }
                }
/*
                if( i != memoriaPrincipal.getCeldas().size() -1){
                    if( memoriaPrincipal.getCeldas().get(i-1).isAsignada() == false && memoriaPrincipal.getCeldas().get(i).isAsignada() == true){
                        limiteSuperior = i;
                        superior = true;
                        
                    }
                }else{
                    if(superior ==false){
                        if(memoriaPrincipal.getCeldas().get(62).isAsignada() == false && memoriaPrincipal.getCeldas().get(63).isAsignada() == false){
                            limiteSuperior = i;
                            superior  = true;
                        }
                    }
                }
*/
                //if(superior == false){
                //    if(memoriaPrincipal.getCeldas().get(63).isAsignada() == false && memoriaPrincipal.getCeldas().get(64).isAsignada() == false){
                //        limiteSuperior = i;
                //        superior  = true;
                //    }
                //}/*
                
                
            }

            

            if( inferior ==true && superior == true ){
                if(limiteSuperior > limiteInferior){
                    limitesAreas.add(limiteInferior);
                    limitesAreas.add(limiteSuperior);
                    areaslibres.add(new AreasLibres(numPart, limiteInferior, limiteSuperior - limiteInferior -1, true));
                }else{
                    limitesAreas.add(limiteSuperior);
                    limitesAreas.add(limiteInferior);
                    areaslibres.add(new AreasLibres(numPart, limiteSuperior, limiteInferior - limiteSuperior -1, true));
                }
                inferior =false;
                superior = false;
                numPart++;
            }
        }

        int indice = 0; //BORRAR
        for (Celdas integer : memoriaPrincipal.getCeldas()) {
            System.out.println("CELDAs : " + ++indice + integer.isAsignada());
        }
        indice = 0;
        for (Integer integer : limitesAreas) {
            System.out.println("limite : " + integer);
        }
        indice = 0;
    }

    public static Particiones findByCodeIsIn(Collection<Particiones> listCarnet, String codeIsIn) {
        return listCarnet.stream().filter(carnet -> codeIsIn.equals(carnet.getProceso())).findFirst().orElse(null);
    }
}
