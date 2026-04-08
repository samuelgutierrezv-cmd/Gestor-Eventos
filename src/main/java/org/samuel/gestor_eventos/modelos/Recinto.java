package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.enums.EstadoAsiento;

import java.util.ArrayList;

public class Recinto {
    private int id;
    private String direccion;
    private String nombre;
    private String ciudad;
    private ArrayList<String> conjuntoZonas;

    public Recinto(int id, String direccion, ArrayList<String> conjuntoZonas, String ciudad, String nombre) {
        this.id = id;
        this.direccion = direccion;
        this.conjuntoZonas = conjuntoZonas;
        this.ciudad = ciudad;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public ArrayList<String> getConjuntoZonas() {
        return conjuntoZonas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setConjuntoZonas(ArrayList<String> conjuntoZonas) {
        this.conjuntoZonas = conjuntoZonas;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setId(int id) {
        this.id = id;
    }
}
