/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author jonatan, Jesus, Natali, Angélica
 */
public class AreasProceso {
    private int localidad;
    private int tamanio;
    private boolean estado;
    private String nomAux;
    // Disponible = true     Asignado = false

    public AreasProceso(int localidad, int tamanio, boolean estado) {
        this.localidad = localidad;
        this.tamanio = tamanio;
        this.estado = estado;
    }

    public AreasProceso(int localidad, int tamanio, boolean estado, String nomAux) {
        this.localidad = localidad;
        this.tamanio = tamanio;
        this.estado = estado;
        this.nomAux = nomAux;
    }

    public AreasProceso() {
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

    public String getNomAux() {
        return nomAux;
    }

    public boolean compareTo(Object o) {
         return localidad>(((AreasProceso)o).getLocalidad());
    }

    @Override
    public String toString() {
        return "AreasProceso{" + "localidad=" + localidad + ", tamanio=" + tamanio + ", estado=" + estado + ", nomAux=" + nomAux + '}';
    }
    
    
}
