package org.samuel.gestor_eventos.interfaces.creacion;

import org.samuel.gestor_eventos.enums.CategoriaEvento;
import org.samuel.gestor_eventos.enums.EstadoAsiento;
import org.samuel.gestor_eventos.enums.EstadoEvento;
import org.samuel.gestor_eventos.enums.Sector;
import org.samuel.gestor_eventos.modelos.Asiento;
import org.samuel.gestor_eventos.modelos.Evento;
import org.samuel.gestor_eventos.modelos.Recinto;
import org.samuel.gestor_eventos.modelos.Zona;

import java.time.LocalDate;
import java.util.ArrayList;

public class FactoryEventos implements CreacionEventos{

    @Override
    public EventoComponente creandoEvento(CategoriaEvento categoria, int id, String nombre, String actividadProgramada, String descripcion, String ciudad, LocalDate fecha, String hora, String politica, Recinto recinto, EstadoEvento estado) {
        return  new Evento( categoria,  id,nombre, actividadProgramada, descripcion, ciudad, fecha, hora, politica, recinto,estado);
    }

    @Override
    public EventoComponente creandoAsientos(int numero, EstadoAsiento estado, int fila, int id) {

        return new Asiento(numero, estado, fila, id);
    }

    @Override
    public EventoComponente creandoRecinto(int id, String direccion, ArrayList<Zona> conjuntoZonas, String ciudad, String nombre) {
        return new Recinto(id, direccion, conjuntoZonas, ciudad, nombre);
    }

    @Override
    public EventoComponente creandoZona(double precioBase, int id, Sector sector, String nombre, int capacidad) {
        return new Zona.ZonaBuilder(precioBase,id,sector,nombre,capacidad).builder();
    }
}
