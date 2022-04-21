/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Modelo;

/**
 *
 * @author Natali, Jesús, Angélica, Jonatan
 */
public class AreasLibres extends AreasProceso{
    private int numero;

    public AreasLibres(int numero, int localidad, int tamanio, boolean estado) {
        super(localidad, tamanio, estado);
        this.numero = numero;
    }
    
    public int getNumero() {
        return numero;
    }
}
