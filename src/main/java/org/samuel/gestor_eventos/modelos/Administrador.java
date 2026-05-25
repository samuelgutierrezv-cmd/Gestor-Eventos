package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.interfaces.creacion.FactoryCompras;
import org.samuel.gestor_eventos.interfaces.creacion.FactoryEventos;
import org.samuel.gestor_eventos.interfaces.creacion.FactoryUsuarios;

public class Administrador extends Persona {

    private String password;

    private FactoryUsuarios factoryUsuarios;
    private FactoryEventos factoryEventos;
    private FactoryCompras factoryCompras;

    public Administrador(
            String nombre,
            int di,
            String correoElectronico,
            String numeroTelefono,
            String password,
            FactoryCompras factoryCompras,
            FactoryEventos factoryEventos,
            FactoryUsuarios factoryUsuarios
    ) {


        super(nombre, di, correoElectronico, numeroTelefono);

        this.password = password;

        this.factoryCompras = factoryCompras;
        this.factoryEventos = factoryEventos;
        this.factoryUsuarios = factoryUsuarios;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}