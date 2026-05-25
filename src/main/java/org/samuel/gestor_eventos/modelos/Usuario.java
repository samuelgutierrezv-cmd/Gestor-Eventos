package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.interfaces.comportamiento.Observer;

import java.util.ArrayList;

public class Usuario extends Persona implements Observer {
    private String password;
    private ArrayList<String> metodosDePago;
    private ArrayList<String> notificaciones = new ArrayList<>();

    public Usuario(String nombre, int di, String corroElectronico, String numeroTelefono, ArrayList<String> metodosDePago, String password) {
        super(nombre, di, corroElectronico, numeroTelefono);
        this.metodosDePago = metodosDePago;
        this.password = password;
    }

    // ==================== GETTERS Y SETTERS ====================

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getMetodosDePago() {
        return metodosDePago;
    }

    public void setMetodosDePago(ArrayList<String> metodosDePago) {
        this.metodosDePago = metodosDePago;
    }

    // ==================== GESTIÓN DE MÉTODOS DE PAGO ====================

    public void agregarMetodoPago(String metodo) {
        if (metodosDePago == null) {
            metodosDePago = new ArrayList<>();
        }
        if (!metodosDePago.contains(metodo)) {
            metodosDePago.add(metodo);
        }
    }

    public void eliminarMetodoPago(String metodo) {
        if (metodosDePago != null) {
            metodosDePago.remove(metodo);
        }
    }

    // ==================== NOTIFICACIONES (Observer) ====================

    public ArrayList<String> getNotificaciones() {
        return notificaciones;
    }

    public void agregarNotificacion(String mensaje) {
        notificaciones.add(mensaje);
    }

    public void limpiarNotificaciones() {
        notificaciones.clear();
    }

    @Override
    public void actualizar(String mensaje) {
        agregarNotificacion(mensaje);
        System.out.println("Notificación para " + getNombre() + ": " + mensaje);
    }

    // ==================== MÉTODOS DE PERSONA (heredados) ====================

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + getNombre() + '\'' +
                ", di=" + getDi() +
                ", correo='" + getCorroElectronico() + '\'' +
                ", telefono='" + getNumeroTelefono() + '\'' +
                ", metodosDePago=" + metodosDePago +
                '}';
    }
}