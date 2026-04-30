package org.samuel.gestor_eventos.interfaces.creacion;

import org.samuel.gestor_eventos.modelos.Persona;

import java.util.ArrayList;

public interface CreacionUsuarios {
    public Persona creandoUsuario(int numeroTelefono, String correoElectronico, String nombre, ArrayList<String> metodosDePago);
    public Persona creandoAdministrador(int numeroTelefono,String correoElectronico,String nombre);
}
