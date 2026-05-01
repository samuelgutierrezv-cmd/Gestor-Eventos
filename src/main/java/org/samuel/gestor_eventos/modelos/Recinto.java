package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.enums.EstadoAsiento;
import org.samuel.gestor_eventos.interfaces.creacion.EventoComponente;

import java.util.ArrayList;

public class Recinto implements EventoComponente {
    private int id;
    private String direccion;
    private String nombre;
    private String ciudad;
    private ArrayList<Zona> conjuntoZonas;

    public Recinto(int id, String direccion, ArrayList<Zona> conjuntoZonas, String ciudad, String nombre) {
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

    public ArrayList<Zona> getConjuntoZonas() {
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

    public void setConjuntoZonas(ArrayList<Zona> conjuntoZonas) {
        this.conjuntoZonas = conjuntoZonas;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean actualizar(EventoComponente componente) {
        return false;
    }

    @Override
    public boolean elminarEvento(int id) {
        return conjuntoZonas.removeIf(z -> z.getId() == id);
    }

    @Override
    public EventoComponente buscar(int id) {
        if (this.id == id) return this;

        for (Zona z : conjuntoZonas) {
            EventoComponente encontrado = z.buscar(id);
            if (encontrado != null) return encontrado;
        }
        return null;
    }

    @Override
    public boolean guadarComponente(EventoComponente componente) {
        if (componente instanceof Zona) {
            if (conjuntoZonas == null) {
                conjuntoZonas = new ArrayList<>();
            }
            return conjuntoZonas.add((Zona) componente);
        }
        return false;
    }

    // Funciones basicas

    public void agregarZona(Zona zona) {
        if (zona == null) throw new IllegalArgumentException("Zona no valida");

        if (conjuntoZonas == null) {
            conjuntoZonas = new ArrayList<>();
        }

        conjuntoZonas.add(zona);
    }


    public void eliminarZona(int idZona) {
        Zona eliminar = null;

        for (Zona z : conjuntoZonas) {
            if (z.getId() == idZona) {
                eliminar = z;
                break;
            }
        }

        if (eliminar == null) {
            throw new IllegalArgumentException("La zona no existe");
        }

        conjuntoZonas.remove(eliminar);
    }


    public void agregarComponente(Zona zona) {
        agregarZona(zona);
    }
}
