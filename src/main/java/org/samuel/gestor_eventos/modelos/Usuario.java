package org.samuel.gestor_eventos.modelos;

import java.util.ArrayList;

public class Usuario extends Persona{
    private String password;
    private ArrayList<String> metodosDepagos;

    public Usuario(String nombre, int di, String corroElectronico, int numeroTelefono, ArrayList<String> metodosDepagos, String password) {
        super(nombre, di, corroElectronico, numeroTelefono);
        this.metodosDepagos = metodosDepagos;
        this.password = password;
    }

    public ArrayList<String> getMetodosDepagos() {
        return metodosDepagos;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }



    public void setMetodosDepagos(ArrayList<String> metodosDepagos) {
        this.metodosDepagos = metodosDepagos;
    }
}
