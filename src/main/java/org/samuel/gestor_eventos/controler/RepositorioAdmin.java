package org.samuel.gestor_eventos.controler;

import org.samuel.gestor_eventos.modelos.*;
import org.samuel.gestor_eventos.enums.*;

import java.util.ArrayList;
import java.time.LocalDate;

/**
 * RepositorioAdmin  —  Fuente única de datos en memoria.
 *
 * Usa listas estáticas para que tanto AdminControler como
 * FormularioControler compartan siempre los mismos datos.
 * No hay base de datos ni archivos: todo vive en RAM.
 */
public class RepositorioAdmin {
    private static ArrayList<Usuario>  usuarios;
    private static ArrayList<Evento>   eventos;
    private static ArrayList<Zona>     zonas;
    private static ArrayList<Asiento>  asientos;
    private static ArrayList<Recinto>  recintos;
    private ArrayList<Compra>   compras;
    private ArrayList<Pago> pagos;
    private static RepositorioAdmin repositorioAdmin;

    private RepositorioAdmin(){
        this.repositorioAdmin =  new RepositorioAdmin();
        this.usuarios = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.zonas = new ArrayList<>();
        this.asientos = new ArrayList<>();
        this.recintos = new ArrayList<>();
        this.compras = new ArrayList<>();
        this.pagos = new ArrayList<>();
    }

    public static RepositorioAdmin getInstance(){
        if(repositorioAdmin == null){
            repositorioAdmin= new RepositorioAdmin();
        }
        return repositorioAdmin;
    }


    // ── Datos de prueba cargados una sola vez ───────────────────────
    static {
        // Usuarios
        usuarios.add(new Usuario("Samuel Torres", 1, "samuel@gmail.com",
                3101, new ArrayList<>(), "pass123"));
        usuarios.add(new Usuario("Laura Gómez", 2, "laura@gmail.com",
                32098, new ArrayList<>(), "pass456"));
        usuarios.add(new Usuario("Andrés Ruiz", 3, "andres@gmail.com",
                3155, new ArrayList<>(), "pass789"));

        // Recintos
        recintos.add(new Recinto(1, "Cra 30 # 57-60", new ArrayList<>(), "Bogotá", "Estadio El Campín"));
        recintos.add(new Recinto(2, "Calle 41 # 57-30", new ArrayList<>(), "Medellín", "Teatro Metropolitano"));
        recintos.add(new Recinto(3, "Calle 63 # 1-60", new ArrayList<>(), "Bogotá", "Anfiteatro del Lago"));

        // Zonas
        zonas.add(new Zona.ZonaBuilder(80000.0, 1, Sector.GENERAL, "General", 5000).builder());
        zonas.add(new Zona.ZonaBuilder(350000.0, 2, Sector.VIP, "VIP", 500).builder());
        zonas.add(new Zona.ZonaBuilder(120000.0, 3, Sector.VIP, "Platea", 1200).builder());

        // Asientos
        asientos.add(new Asiento(1, EstadoAsiento.DISPONIBLE, 1, 1));
        asientos.add(new Asiento(2, EstadoAsiento.OCUPADO, 1, 2));
        asientos.add(new Asiento(3, EstadoAsiento.RESERVADO, 2, 3));
        asientos.add(new Asiento(1, EstadoAsiento.DISPONIBLE, 2, 4));

        // Eventos
        eventos.add(new Evento(
                CategoriaEvento.CONCIERTO, 1, "Festival Estéreo Picnic",
                "4 escenarios simultáneos", "El festival más grande de Colombia",
                "Bogotá", LocalDate.now(), "16:00", "Sin reembolsos 48h antes",
                recintos.get(2), EstadoEvento.ACTIVO));

        eventos.add(new Evento(
                CategoriaEvento.DEPORTES, 2, "Clásico Millonarios vs Santa Fe",
                "Ida Copa Colombia", "El clásico capitalino",
                "Bogotá", LocalDate.now(), "19:30", "Reembolso hasta 24h",
                recintos.get(0), EstadoEvento.ACTIVO));

        eventos.add(new Evento(
                CategoriaEvento.TEATRO, 3, "Hamlet Contemporáneo",
                "Elenco nacional", "La obra más aclamada del año",
                "Medellín", LocalDate.now(), "20:00", "Cambio con 72h",
                recintos.get(1), EstadoEvento.BORRADOR));
    }
}
