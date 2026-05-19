package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.enums.*;
import org.samuel.gestor_eventos.interfaces.creacion.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class Administrador extends Persona {

    private FactoryUsuarios factoryUsuarios;
    private FactoryEventos factoryEventos;
    private FactoryCompras factoryCompras;

    private static Administrador instancia;

    private Administrador(
            String nombre,
            int di,
            String correoElectronico,
            String numeroTelefono,
            FactoryCompras factoryCompras,
            FactoryEventos factoryEventos,
            FactoryUsuarios factoryUsuarios
    ) {

        super(nombre, di, correoElectronico, numeroTelefono);

        this.factoryCompras = factoryCompras;
        this.factoryEventos = factoryEventos;
        this.factoryUsuarios = factoryUsuarios;
    }

    public static Administrador getInstance(
            String numeroTelefono,
            int di,
            String correoElectronico,
            String nombre,
            FactoryCompras factoryCompras,
            FactoryEventos factoryEventos,
            FactoryUsuarios factoryUsuarios
    ) {

        if(instancia == null){
            instancia = new Administrador(
                    nombre,
                    di,
                    correoElectronico,
                    numeroTelefono,
                    factoryCompras,
                    factoryEventos,
                    factoryUsuarios
            );
        }
        return instancia;
    }

    public Usuario crearUsuario(
            String numeroTelefono,
            int id,
            String correoElectronico,
            String nombre,
            ArrayList<String> metodosDePago,
            String password
    ) {

        return (Usuario) factoryUsuarios.creandoUsuario(
                numeroTelefono,
                id,
                correoElectronico,
                nombre,
                metodosDePago,
                password
        );
    }

    public Evento crearEvento(
            CategoriaEvento categoria,
            int id,
            String nombre,
            String actividadProgramada,
            String descripcion,
            String ciudad,
            LocalDate fecha,
            String hora,
            String politica,
            Recinto recinto,
            EstadoEvento estado
    ) {

        return (Evento) factoryEventos.creandoEvento(
                categoria,
                id,
                nombre,
                actividadProgramada,
                descripcion,
                ciudad,
                fecha,
                hora,
                politica,
                recinto,
                estado
        );
    }

    public Recinto crearRecinto(
            int id,
            String direccion,
            ArrayList<Zona> conjuntoZonas,
            String ciudad,
            String nombre
    ) {

        return (Recinto) factoryEventos.creandoRecinto(
                id,
                direccion,
                conjuntoZonas,
                ciudad,
                nombre
        );
    }

    public Zona crearZona(
            double precioBase,
            int id,
            Sector sector,
            String nombre,
            int capacidad
    ) {

        return (Zona) factoryEventos.creandoZona(
                precioBase,
                id,
                sector,
                nombre,
                capacidad
        );
    }

    public Asiento crearAsiento(
            int numero,
            EstadoAsiento estado,
            int fila,
            int id
    ) {

        return (Asiento) factoryEventos.creandoAsientos(
                numero,
                estado,
                fila,
                id
        );
    }
}