package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.enums.CategoriaEvento;
import org.samuel.gestor_eventos.enums.EstadoAsiento;
import org.samuel.gestor_eventos.enums.EstadoEvento;
import org.samuel.gestor_eventos.enums.Sector;
import org.samuel.gestor_eventos.interfaces.creacion.FactoryUsuarios;
import org.samuel.gestor_eventos.interfaces.creacion.FactoryEventos;
import org.samuel.gestor_eventos.interfaces.creacion.FactoryCompras;

import java.time.LocalDate;
import java.util.ArrayList;

public class Administrador extends Persona{

    private FactoryUsuarios factoryUsuarios;
    private FactoryEventos factoryEventos;
    private FactoryCompras factoryCompras;
    private static Administrador instancia;

    public Administrador(String nombre, int di, String corroElectronico, int numeroTelefono, FactoryCompras factoryCompras, FactoryEventos factoryEventos, FactoryUsuarios factoryUsuarios) {
        super(nombre, di, corroElectronico, numeroTelefono);
        this.factoryCompras = factoryCompras;
        this.factoryUsuarios = factoryUsuarios;
        this.factoryEventos = factoryEventos;
    }

    public static Administrador getInstance(int numeroTelefono,int id ,String correoElectronico,String nombre,FactoryCompras factoryCompras, FactoryEventos factoryEventos, FactoryUsuarios factoryUsuarios){
        if(instancia == null){
            instancia = (Administrador) factoryUsuarios.creandoAdministrador(numeroTelefono, id, correoElectronico, nombre,factoryCompras, factoryEventos, factoryUsuarios);
        }
        return instancia;
    }

    public Usuario crearUsuario(int numeroTelefono,int id,String correoElectronico,String nombre,ArrayList<String> metodosDePago, String password){
        return (Usuario) factoryUsuarios.creandoUsuario(numeroTelefono,id,correoElectronico,nombre, metodosDePago,password);
    }

    public  Evento crearEvento(CategoriaEvento categoria, int id, String nombre, String actividadProgramada, String descripcion, String ciudad, LocalDate fecha, String hora, String politica, Recinto recinto, EstadoEvento estado){
        return (Evento) factoryEventos.creandoEvento(categoria,  id,nombre, actividadProgramada, descripcion, ciudad, fecha, hora, politica, recinto,estado);
    }

    public Recinto crearRecinto(int id, String direccion, ArrayList<Zona> conjuntoZonas, String ciudad, String nombre){
        return (Recinto) factoryEventos.creandoRecinto(id, direccion, conjuntoZonas, ciudad, nombre);
    }
    public Zona crearZona(double precioBase, int id, Sector sector, String nombre, int capacidad){
        return (Zona) factoryEventos.creandoZona(precioBase,id,sector,nombre,capacidad);
    }

    public Asiento crearAsinto(int numero, EstadoAsiento estado, int fila, int id){
        return (Asiento) factoryEventos.creandoAsientos(numero, estado, fila, id);
    }
}
