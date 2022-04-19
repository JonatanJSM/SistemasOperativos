/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Modelo;

/**
 *
 * @author Natali, Angélica, Jesús, Jonatan
 */
public class Particiones extends AreasProceso{
    private String nombreProceso;
    private int tiempodeLlegada;
    private int duracion;
    
    //------------------------- 
    
    private int numero;
    private String proceso;

    public Particiones(String nombreProceso, int tiempodeLlegada, int duracion, int numero, String proceso, int localidad, int tamanio, boolean estado) {
        super(localidad, tamanio, estado);
        this.nombreProceso = nombreProceso;
        this.tiempodeLlegada = tiempodeLlegada;
        this.duracion = duracion;
        this.numero = numero;
        this.proceso = proceso;
    }
    
}
