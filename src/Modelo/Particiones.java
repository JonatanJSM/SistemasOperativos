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

    private int numero;
    private String proceso;

    public Particiones(int numero, String proceso, int localidad, int tamanio, boolean estado) {
        super(localidad, tamanio, estado);
        this.numero = numero;
        this.proceso = proceso;
    }
    
    public int getNumero() {
        return numero;
    }
    public String getProceso() {
        return proceso;
    }
}
