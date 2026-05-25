package org.samuel.gestor_eventos.interfaces.creacion;

public interface EventoComponente {
    public boolean actualizar(EventoComponente componente);
    public boolean eliminarEvento(int id);
    public EventoComponente buscar(int id);
    public boolean guardarComponente(EventoComponente componente);
}