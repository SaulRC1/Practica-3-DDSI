/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author usuario
 */
public class Actividad {
    
    String idActividad;
    String nombre;
    String descripcion;
    int preciobasemes;
    String monitorResponsable;

    public Actividad(String idActividad, String nombre, String descripcion, int preciobasemes, String monitorResponsable) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.preciobasemes = preciobasemes;
        this.monitorResponsable = monitorResponsable;
    }

    public String getIdActividad() {
        return idActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPreciobasemes() {
        return preciobasemes;
    }

    public String getMonitorResponsable() {
        return monitorResponsable;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPreciobasemes(int preciobasemes) {
        this.preciobasemes = preciobasemes;
    }

    public void setMonitorResponsable(String monitorResponsable) {
        this.monitorResponsable = monitorResponsable;
    }
    
    
    
}
