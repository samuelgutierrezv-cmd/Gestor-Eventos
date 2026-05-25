package org.samuel.gestor_eventos.interfaces.creacion;

import org.samuel.gestor_eventos.modelos.Administrador;
import org.samuel.gestor_eventos.modelos.Persona;
import org.samuel.gestor_eventos.modelos.Usuario;

import java.util.ArrayList;

public class FactoryUsuarios implements CreacionUsuarios{
    @Override
    public Persona creandoUsuario(String numeroTelefono,int id,String correoElectronico,String nombre,ArrayList<String> metodosDePago, String password) {
        return new Usuario(nombre, id, correoElectronico,numeroTelefono, metodosDePago,password);
    }

    @Override
    public Persona creandoAdministrador(String numeroTelefono,int id ,String correoElectronico,String nombre, FactoryCompras factoryCompras, FactoryEventos factoryEventos, FactoryUsuarios factoryUsuarios, String password) {
        return new Administrador(nombre, id, correoElectronico, numeroTelefono, password, factoryCompras, factoryEventos, factoryUsuarios);
    }
}
