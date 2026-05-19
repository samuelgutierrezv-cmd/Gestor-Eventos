package org.samuel.gestor_eventos.interfaces.creacion;

import org.samuel.gestor_eventos.modelos.Persona;

import java.util.ArrayList;

public interface CreacionUsuarios {
    public Persona creandoUsuario(String numeroTelefono, int id,String correoElectronico, String nombre, ArrayList<String> metodosDePago, String password);
    public Persona creandoAdministrador(String numeroTelefono,int id,String correoElectronico,String nombre,FactoryCompras factoryCompras, FactoryEventos factoryEventos, FactoryUsuarios factoryUsuarios);
}
