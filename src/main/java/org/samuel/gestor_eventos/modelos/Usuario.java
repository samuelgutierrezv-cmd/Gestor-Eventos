package org.samuel.gestor_eventos.modelos;

import java.util.ArrayList;

public class Usuario extends Persona{

    private ArrayList<String> metodosDepagos;
    public Usuario(String nombre, int di, String corroElectronico, int numeroTelefono, ArrayList<String> metodosDepagos) {
        super(nombre, di, corroElectronico, numeroTelefono);
        this.metodosDepagos = metodosDepagos;
    }

    public ArrayList<String> getMetodosDepagos() {
        return metodosDepagos;
    }

    public void setMetodosDepagos(ArrayList<String> metodosDepagos) {
        this.metodosDepagos = metodosDepagos;
    }
}
