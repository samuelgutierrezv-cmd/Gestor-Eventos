package org.samuel.gestor_eventos.interfaces.creacion;

import org.samuel.gestor_eventos.enums.CategoriaEvento;
import org.samuel.gestor_eventos.enums.EstadoAsiento;
import org.samuel.gestor_eventos.enums.EstadoEvento;
import org.samuel.gestor_eventos.enums.Sector;
import org.samuel.gestor_eventos.modelos.Recinto;
import org.samuel.gestor_eventos.modelos.Zona;

import java.time.LocalDate;
import java.util.ArrayList;

public interface CreacionEventos {
    public EventoComponente creandoEvento(CategoriaEvento categoria, int id, String nombre, String actividadProgramada, String descripcion, String ciudad, LocalDate fecha, String hora, String politica, Recinto recinto, EstadoEvento estado);
    public EventoComponente creandoAsientos(int numero, EstadoAsiento estado, int fila, int id);
    public EventoComponente creandoRecinto(int id, String direccion, ArrayList<Zona> conjuntoZonas, String ciudad, String nombre);
    public EventoComponente creandoZona(double precioBase, int id, Sector sector, String nombre, int capacidad);
}
