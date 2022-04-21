/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author jonat
 */
public class AreasProceso {
    private int localidad;
    private int tamanio;
    private boolean estado;
    // Disponible = true     Asignado = false

    public AreasProceso(int localidad, int tamanio, boolean estado) {
        this.localidad = localidad;
        this.tamanio = tamanio;
        this.estado = estado;
    }

    public int getLocalidad() {
        return localidad;
    }

    public int getTamanio() {
        return tamanio;
    }

    public boolean getEstado() {
        return estado;
    }
    
}
