/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author jonat
 */
public class Proceso{
    private boolean activo;
    private String nombreProceso;
    private int tamanio;
    private int tiempoLLegada;
    private int duracion;
    private int duracionTotal; // Esto considerando en qué click debe acabar

    public Proceso(String nombreProceso, int tamanio, int tiempoLLegada, int duracion, boolean activo) {
        this.nombreProceso = nombreProceso;
        this.tamanio = tamanio;
        this.tiempoLLegada = tiempoLLegada;
        this.duracion = duracion;
        duracionTotal = tiempoLLegada + duracion;

        this.activo = activo;
    }

    public String getNombreProceso() {
        return nombreProceso;
    }

    public int getTamanio() {
        return tamanio;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getTiempoLLegada() {
        return tiempoLLegada;
    }

    public int getDuracionTotal() {
        return duracionTotal;
    }

    public void setActivo(boolean b){
        this.activo = b;
    }

    public boolean isActivo() {
        return activo;
    }

}
