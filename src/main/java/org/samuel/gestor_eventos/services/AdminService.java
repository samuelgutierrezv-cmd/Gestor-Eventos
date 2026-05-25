package org.samuel.gestor_eventos.services;

import org.samuel.gestor_eventos.controler.RepositorioAdmin;
import org.samuel.gestor_eventos.enums.EstadoEvento;
import org.samuel.gestor_eventos.modelos.Evento;
import org.samuel.gestor_eventos.modelos.Usuario;

import java.util.ArrayList;

public class AdminService {

    private final RepositorioAdmin repo =
            RepositorioAdmin.getInstance();

    // ==================== EVENTOS ====================

    public void agregarEvento(Evento evento) {

        repo.getEventos().add(evento);
    }

    public boolean eliminarEvento(int id) {

        return repo.getEventos()
                .removeIf(e -> e.getId() == id);
    }

    public Evento buscarEvento(int id) {

        for (Evento e : repo.getEventos()) {

            if (e.getId() == id) {
                return e;
            }
        }

        return null;
    }

    public ArrayList<Evento> listarEventos() {

        return repo.getEventos();
    }

    public void cancelarEvento(int id) {
        Evento evento = buscarEvento(id);
        if (evento != null) {
            evento.setEstado(EstadoEvento.CANCELADO);
            // Notificar a todos los observers
            evento.notificarObservers("El evento " + evento.getNombre() + " ha sido cancelado.");
        }
    }

    public void publicarEvento(int id) {

        Evento evento = buscarEvento(id);

        if (evento != null) {

            evento.setEstado(
                    EstadoEvento.PUBLICADO
            );
        }
    }

    // ==================== USUARIOS ====================

    public void agregarUsuario(Usuario usuario) {

        repo.getUsuarios().add(usuario);
    }

    public boolean eliminarUsuario(int id) {

        return repo.getUsuarios()
                .removeIf(u -> u.getDi() == id);
    }

    public Usuario buscarUsuario(int id) {

        for (Usuario u : repo.getUsuarios()) {

            if (u.getDi() == id) {
                return u;
            }
        }

        return null;
    }

    public ArrayList<Usuario> listarUsuarios() {

        return repo.getUsuarios();
    }
}