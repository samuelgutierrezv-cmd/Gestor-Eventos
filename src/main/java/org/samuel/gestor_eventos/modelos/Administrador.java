package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.enums.EstadoAsiento;
import org.samuel.gestor_eventos.enums.EstadoEvento;
import org.samuel.gestor_eventos.modelos.*;

import java.util.ArrayList;
import java.util.List;

public class Administrador extends Persona {

    private ArrayList<Usuario> usuariosRegistrados;
    private ArrayList<Evento> eventosGestionados;
    private ArrayList<Recinto> recintosGestionados;
    private ArrayList<Incidencia> incidencias;

    public Administrador(String nombre, int di, String correoElectronico, int numeroTelefono) {
        super(nombre, di, correoElectronico, numeroTelefono);
        this.usuariosRegistrados = new ArrayList<>();
        this.eventosGestionados  = new ArrayList<>();
        this.recintosGestionados = new ArrayList<>();
        this.incidencias         = new ArrayList<>();
    }

    // ─ RF-012: Gestión de usuarios ─ A

    public boolean agregarUsuario(Usuario usuario) {
        if (usuario == null) return false;
        usuariosRegistrados.add(usuario);
        return true;
    }

    public boolean eliminarUsuario(int id) {
        return usuariosRegistrados.removeIf(u -> u.getDi() == id);
    }

    public Usuario buscarUsuario(int id) {
        for (Usuario u : usuariosRegistrados) {
            if (u.getDi() == id) return u;
        }
        return null;
    }

    public ArrayList<Usuario> listarUsuarios() {
        return usuariosRegistrados;
    }

    // ─ RF-013: Gestión de eventos ─

    public boolean agregarEvento(Evento evento) {
        if (evento == null) return false;
        eventosGestionados.add(evento);
        return true;
    }

    public boolean eliminarEvento(int id) {
        return eventosGestionados.removeIf(e -> e.getId() == id);
    }

    public Evento buscarEvento(int id) {
        for (Evento e : eventosGestionados) {
            if (e.getId() == id) return e;
        }
        return null;
    }

    public ArrayList<Evento> listarEventos() {
        return eventosGestionados;
    }

    public boolean publicarEvento(int id) {
        Evento e = buscarEvento(id);
        if (e == null) return false;
        try {
            e.publicar();
            return true;
        } catch (IllegalStateException ex) {
            System.out.println("Error al publicar: " + ex.getMessage());
            return false;
        }
    }

    public boolean cancelarEvento(int id) {
        Evento e = buscarEvento(id);
        if (e == null) return false;
        try {
            e.cancelar();
            return true;
        } catch (IllegalStateException ex) {
            System.out.println("Error al cancelar: " + ex.getMessage());
            return false;
        }
    }

    public boolean pausarEvento(int id) {
        Evento e = buscarEvento(id);
        if (e == null) return false;
        e.setEstado(EstadoEvento.PAUSADO);
        return true;
    }

    // ─ RF-014: Gestión de recintos y zonas ─ A

    public boolean agregarRecinto(Recinto recinto) {
        if (recinto == null) return false;
        recintosGestionados.add(recinto);
        return true;
    }

    public boolean eliminarRecinto(int id) {
        return recintosGestionados.removeIf(r -> r.getId() == id);
    }

    public Recinto buscarRecinto(int id) {
        for (Recinto r : recintosGestionados) {
            if (r.getId() == id) return r;
        }
        return null;
    }

    public ArrayList<Recinto> listarRecintos() {
        return recintosGestionados;
    }

    public boolean agregarZonaARecinto(int idRecinto, Zona zona) {
        Recinto r = buscarRecinto(idRecinto);
        if (r == null || zona == null) return false;
        r.agregarZona(zona);
        return true;
    }

    public boolean eliminarZonaDeRecinto(int idRecinto, int idZona) {
        Recinto r = buscarRecinto(idRecinto);
        if (r == null) return false;
        try {
            r.eliminarZona(idZona);
            return true;
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
            return false;
        }
    }

    // ─ RF-015: Gestión de asientos ─ A

    public boolean cambiarEstadoAsiento(Asiento asiento, EstadoAsiento nuevoEstado) {
        if (asiento == null) return false;
        try {
            asiento.cambiarEstado(nuevoEstado);
            return true;
        } catch (IllegalStateException | IllegalArgumentException ex) {
            System.out.println("Error asiento: " + ex.getMessage());
            return false;
        }
    }

    // ── RF-017: Registro de incidencias ─ A

    public boolean registrarIncidencia(Incidencia incidencia) {
        if (incidencia == null) return false;
        incidencias.add(incidencia);
        return true;
    }

    public ArrayList<Incidencia> listarIncidencias() {
        return incidencias;
    }

    // ─ RF-018: Métricas básicas ─ A

    public int totalEventosActivos() {
        int count = 0;
        for (Evento e : eventosGestionados) {
            if (e.getEstado() == EstadoEvento.ACTIVO ||
                    e.getEstado() == EstadoEvento.PUBLICADO) count++;
        }
        return count;
    }

    public double ocupacionPromedioRecintos() {
        if (recintosGestionados.isEmpty()) return 0;
        double total = 0;
        int zonasTotales = 0;
        for (Recinto r : recintosGestionados) {
            for (Zona z : r.getConjuntoZonas()) {
                total += z.calcularOcupacion();
                zonasTotales++;
            }
        }
        return zonasTotales == 0 ? 0 : total / zonasTotales;
    }
}