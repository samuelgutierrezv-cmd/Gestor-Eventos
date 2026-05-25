package org.samuel.gestor_eventos.interfaces.comportamiento;

public interface Subject {
    void agregarObserver(Observer observer);
    void eliminarObserver(Observer observer);
    void notificarObservers(String mensaje);
}