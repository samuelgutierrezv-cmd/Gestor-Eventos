package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.enums.Sector;

import java.util.ArrayList;

public class Zona {
    private Sector sector;
    private int id;
    private String nombre;
    private int capacidad;
    private float precioBase;
    private ArrayList<String> configuracionAsientos;

    public Zona(ZonaBuilder builder){
        this.nombre = builder.nombre;
        this.id = builder.id;
        this.capacidad = builder.capacidad;
        this.precioBase = builder.capacidad;
        this.sector = builder.sector;
    }

    public static class ZonaBuilder{
        private Sector sector;
        private int id;
        private String nombre;
        private int capacidad;
        private float precioBase;
        private ArrayList<String> configuracionAsientos;

        public ZonaBuilder(float precioBase,int id, Sector sector, String nombre, int capacidad){
            this.capacidad = capacidad;
            this.id = id;
            this.nombre = nombre;
            this.sector = sector;
            this.precioBase = precioBase;
        }

        public ZonaBuilder setConfiguracionAsientos(ArrayList<String> configuracionAsientos){
            this.configuracionAsientos = configuracionAsientos;
            return this;
        }

        public Zona builder(){
            return new Zona(this);
        }
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setPrecioBase(float precioBase) {
        this.precioBase = precioBase;
    }

    public void setConfiguracionAsientos(ArrayList<String> configuracionAsientos) {
        this.configuracionAsientos = configuracionAsientos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public Sector getSector() {
        return sector;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public float getPrecioBase() {
        return precioBase;
    }

    public ArrayList<String> getConfiguracionAsientos() {
        return configuracionAsientos;
    }
}
