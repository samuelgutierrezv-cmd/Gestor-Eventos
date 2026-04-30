package org.samuel.gestor_eventos.interfaces.creacion;

public interface Pasarela {
    public boolean actualizar(Pasarela pasarela);
    public boolean eliminar(int id);
    public Pasarela buscar(int id);
    public boolean guardar(Pasarela pasarela);
}
