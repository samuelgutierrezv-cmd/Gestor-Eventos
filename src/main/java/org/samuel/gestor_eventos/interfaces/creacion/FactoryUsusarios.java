package org.samuel.gestor_eventos.interfaces.creacion;

import org.samuel.gestor_eventos.modelos.Administrador;
import org.samuel.gestor_eventos.modelos.Persona;
import org.samuel.gestor_eventos.modelos.Usuario;

import java.util.ArrayList;

public class FactoryUsusarios implements CreacionUsuarios{
    @Override
    public Persona creandoUsuario(int numeroTelefono,String correoElectronico,String nombre,ArrayList<String> metodosDePago) {
        return new Usuario(nombre, 0, correoElectronico,numeroTelefono, metodosDePago);
    }

    @Override
    public Persona creandoAdministrador(int numeroTelefono,String correoElectronico,String nombre) {
        return new Administrador(nombre, 0 , correoElectronico, numeroTelefono);
    }
}
