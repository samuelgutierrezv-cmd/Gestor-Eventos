package org.samuel.gestor_eventos.controler;

import org.samuel.gestor_eventos.enums.*;
import org.samuel.gestor_eventos.interfaces.creacion.FactoryCompras;
import org.samuel.gestor_eventos.interfaces.creacion.FactoryEventos;
import org.samuel.gestor_eventos.interfaces.creacion.FactoryUsuarios;
import org.samuel.gestor_eventos.modelos.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class RepositorioAdmin {

    private static RepositorioAdmin instancia;

    private ArrayList<Usuario> usuarios;
    private ArrayList<Administrador> administradores;
    private ArrayList<Evento> eventos;
    private ArrayList<Zona> zonas;
    private ArrayList<Recinto> recintos;
    private ArrayList<Compra> compras;
    private ArrayList<Pago> pagos;
    private ArrayList<Facturas> facturas;
    private ArrayList<Incidencia> incidencias;


    private RepositorioAdmin() {
        usuarios = new ArrayList<>();
        administradores = new ArrayList<>();
        eventos = new ArrayList<>();
        zonas = new ArrayList<>();
        recintos = new ArrayList<>();
        compras = new ArrayList<>();
        pagos = new ArrayList<>();
        facturas = new ArrayList<>();
        incidencias = new ArrayList<>();


        cargarDatosPrueba();
    }

    public static RepositorioAdmin getInstance() {
        if (instancia == null) {
            instancia = new RepositorioAdmin();
        }
        return instancia;
    }

    private void cargarDatosPrueba() {
        // ---- USUARIOS ----
        Usuario usuario1 = new Usuario("Samuel Torres", 1, "samuel@gmail.com", "3101234567", new ArrayList<>(), "123");
        Usuario usuario2 = new Usuario("Laura Gómez", 2, "laura@gmail.com", "3209876543", new ArrayList<>(), "123");
        Usuario usuario3 = new Usuario("Carlos Pérez", 3, "carlos@gmail.com", "3155678901", new ArrayList<>(), "123");
        Usuario usuario4 = new Usuario("Ana Martínez", 4, "ana@gmail.com", "3109876543", new ArrayList<>(), "123");
        usuarios.add(usuario1);
        usuarios.add(usuario2);
        usuarios.add(usuario3);
        usuarios.add(usuario4);

        // ---- ADMINISTRADOR ----
        administradores.add(new Administrador("Administrador", 999, "admin@gmail.com", "3001234567", "123",
                new FactoryCompras(), new FactoryEventos(), new FactoryUsuarios()));

        // ---- RECINTOS ----
        // Recinto 1: Estadio El Campín
        Zona zonaGeneral = new Zona.ZonaBuilder(80000.0, 1, Sector.GENERAL, "General", 50).builder();
        zonaGeneral.generarAsientosAutomaticos();
        Zona zonaVIP = new Zona.ZonaBuilder(150000.0, 2, Sector.VIP, "VIP", 20).builder();
        zonaVIP.generarAsientosAutomaticos();
        Zona zonaPreferencial = new Zona.ZonaBuilder(120000.0, 3, Sector.PREFERENCIAL, "Preferencial", 30).builder();
        zonaPreferencial.generarAsientosAutomaticos();
        zonas.add(zonaGeneral);
        zonas.add(zonaVIP);
        zonas.add(zonaPreferencial);

        ArrayList<Zona> zonasRecinto1 = new ArrayList<>();
        zonasRecinto1.add(zonaGeneral);
        zonasRecinto1.add(zonaVIP);
        zonasRecinto1.add(zonaPreferencial);

        Recinto recinto1 = new Recinto(1, "Cra 30 # 57-60", zonasRecinto1, "Bogotá", "Estadio El Campín");
        recintos.add(recinto1);

        // Recinto 2: Teatro Nacional
        Zona zonaTeatroGeneral = new Zona.ZonaBuilder(50000.0, 4, Sector.GENERAL, "Platea", 40).builder();
        zonaTeatroGeneral.generarAsientosAutomaticos();
        Zona zonaTeatroVIP = new Zona.ZonaBuilder(100000.0, 5, Sector.VIP, "Palcos VIP", 10).builder();
        zonaTeatroVIP.generarAsientosAutomaticos();
        zonas.add(zonaTeatroGeneral);
        zonas.add(zonaTeatroVIP);

        ArrayList<Zona> zonasRecinto2 = new ArrayList<>();
        zonasRecinto2.add(zonaTeatroGeneral);
        zonasRecinto2.add(zonaTeatroVIP);

        Recinto recinto2 = new Recinto(2, "Carrera 7 # 45-20", zonasRecinto2, "Bogotá", "Teatro Nacional");
        recintos.add(recinto2);

        // Recinto 3: Centro de Convenciones
        Zona zonaConferencia = new Zona.ZonaBuilder(30000.0, 6, Sector.GENERAL, "Auditorio", 60).builder();
        zonaConferencia.generarAsientosAutomaticos();
        zonas.add(zonaConferencia);

        ArrayList<Zona> zonasRecinto3 = new ArrayList<>();
        zonasRecinto3.add(zonaConferencia);

        Recinto recinto3 = new Recinto(3, "Calle 100 # 15-30", zonasRecinto3, "Medellín", "Centro de Convenciones Plaza Mayor");
        recintos.add(recinto3);

        // ---- EVENTOS ----
        // Evento 1: Concierto - Estadio El Campín
        Evento evento1 = new Evento(CategoriaEvento.CONCIERTO, 1, "Festival Estéreo Picnic", "4 escenarios",
                "Festival principal de música", "Bogotá", LocalDate.now().plusDays(30), "16:00", "Sin reembolso", recinto1, EstadoEvento.ACTIVO);
        eventos.add(evento1);

        // Evento 2: Teatro - Teatro Nacional
        Evento evento2 = new Evento(CategoriaEvento.TEATRO, 2, "La Casa de Bernarda Alba", "Obra de teatro",
                "Presentación de la obra clásica", "Bogotá", LocalDate.now().plusDays(15), "20:00", "Reembolso 48h", recinto2, EstadoEvento.PUBLICADO);
        eventos.add(evento2);

        // Evento 3: Conferencia - Centro de Convenciones
        Evento evento3 = new Evento(CategoriaEvento.CONFERENCIA, 3, "Conferencia de Innovación", "Charlas TED",
                "Conferencia sobre tecnología", "Medellín", LocalDate.now().plusDays(45), "10:00", "No reembolso", recinto3, EstadoEvento.PUBLICADO);
        eventos.add(evento3);

        // Evento 4: Deportes - Estadio El Campín
        Evento evento4 = new Evento(CategoriaEvento.DEPORTES, 4, "Final Liga Colombiana", "Partido de fútbol",
                "Final del torneo", "Bogotá", LocalDate.now().plusDays(60), "18:00", "Sin reembolso", recinto1, EstadoEvento.ACTIVO);
        eventos.add(evento4);

        // ---- COMPRAS DE PRUEBA ----
        // Compra 1: Samuel - Festival Estéreo Picnic (PAGADA)
        ArrayList<Entrada> entradas1 = new ArrayList<>();
        if (!zonaGeneral.getConfiguracionAsientos().isEmpty()) {
            Asiento a1 = zonaGeneral.getConfiguracionAsientos().get(0);
            a1.setEstado(EstadoAsiento.VENDIDO);
            Entrada entrada1 = new Entrada(1, zonaGeneral, a1, 80000.0, EstadoEntrada.ACTIVA);
            entradas1.add(entrada1);
        }
        ArrayList<String> servicios1 = new ArrayList<>();
        servicios1.add("VIP");
        Compra compra1 = new Compra(1, usuario1, 1800000, evento1,
                new java.sql.Date(System.currentTimeMillis()), EstadoCompras.PAGADA,
                new ArrayList<>(), servicios1, entradas1);
        compras.add(compra1);

        // Compra 2: Laura - La Casa de Bernarda Alba (CREADA)
        ArrayList<Entrada> entradas2 = new ArrayList<>();
        if (!zonaTeatroGeneral.getConfiguracionAsientos().isEmpty()) {
            Asiento a2 = zonaTeatroGeneral.getConfiguracionAsientos().get(0);
            a2.setEstado(EstadoAsiento.RESERVADO);
            Entrada entrada2 = new Entrada(2, zonaTeatroGeneral, a2, 50000.0, EstadoEntrada.ACTIVA);
            entradas2.add(entrada2);
        }
        ArrayList<String> servicios2 = new ArrayList<>();
        servicios2.add("Parqueadero");
        Compra compra2 = new Compra(2, usuario2, 1000000, evento2,
                new java.sql.Date(System.currentTimeMillis()), EstadoCompras.CREADA,
                new ArrayList<>(), servicios2, entradas2);
        compras.add(compra2);

        // Compra 3: Carlos - Conferencia de Innovación (CONFIRMADA)
        ArrayList<Entrada> entradas3 = new ArrayList<>();
        if (!zonaConferencia.getConfiguracionAsientos().isEmpty()) {
            Asiento a3 = zonaConferencia.getConfiguracionAsientos().get(0);
            a3.setEstado(EstadoAsiento.VENDIDO);
            Entrada entrada3 = new Entrada(3, zonaConferencia, a3, 30000.0, EstadoEntrada.ACTIVA);
            entradas3.add(entrada3);
        }
        ArrayList<String> servicios3 = new ArrayList<>();
        servicios3.add("VIP");
        servicios3.add("Parqueadero");
        Compra compra3 = new Compra(3, usuario3, 1800000, evento3,
                new java.sql.Date(System.currentTimeMillis()), EstadoCompras.CONFIRMADA,
                new ArrayList<>(), servicios3, entradas3);
        compras.add(compra3);

        // Compra 4: Ana - Final Liga Colombiana (CANCELADA)
        ArrayList<Entrada> entradas4 = new ArrayList<>();
        if (!zonaGeneral.getConfiguracionAsientos().isEmpty()) {
            Asiento a4 = zonaGeneral.getConfiguracionAsientos().get(0);
            a4.setEstado(EstadoAsiento.DISPONIBLE);
            Entrada entrada4 = new Entrada(4, zonaGeneral, a4, 80000.0, EstadoEntrada.ANULADA);
            entradas4.add(entrada4);
        }
        ArrayList<String> servicios4 = new ArrayList<>();
        servicios4.add("Seguro");
        Compra compra4 = new Compra(4, usuario4, 1000000, evento4,
                new java.sql.Date(System.currentTimeMillis()), EstadoCompras.CANCELADA,
                new ArrayList<>(), servicios4, entradas4);
        compras.add(compra4);

        // Compra 5: Samuel - Final Liga Colombiana (PAGADA)
        ArrayList<Entrada> entradas5 = new ArrayList<>();
        if (!zonaVIP.getConfiguracionAsientos().isEmpty()) {
            Asiento a5 = zonaVIP.getConfiguracionAsientos().get(0);
            a5.setEstado(EstadoAsiento.VENDIDO);
            Entrada entrada5 = new Entrada(5, zonaVIP, a5, 150000.0, EstadoEntrada.ACTIVA);
            entradas5.add(entrada5);
        }
        ArrayList<String> servicios5 = new ArrayList<>();
        servicios5.add("VIP");
        servicios5.add("Parqueadero");
        servicios5.add("Seguro");
        Compra compra5 = new Compra(5, usuario1, 3200000, evento4,
                new java.sql.Date(System.currentTimeMillis()), EstadoCompras.PAGADA,
                new ArrayList<>(), servicios5, entradas5);
        compras.add(compra5);

        // ---- INCIDENCIAS DE PRUEBA ----
        incidencias.add(new Incidencia(
                "Intento de doble compra de asiento detectado",
                1,
                TipoIncidencia.CREADA,
                new java.sql.Date(System.currentTimeMillis()),
                Entidades.ZONA
        ));

        incidencias.add(new Incidencia(
                "Error al procesar pago con tarjeta",
                2,
                TipoIncidencia.PAGADA,
                new java.sql.Date(System.currentTimeMillis()),
                Entidades.EVENTO
        ));

        incidencias.add(new Incidencia(
                "Usuario reporta problema con la entrada",
                3,
                TipoIncidencia.CREADA,
                new java.sql.Date(System.currentTimeMillis()),
                Entidades.ZONA
        ));
    }

    // ---- GETTERS (sin cambios) ----
    public ArrayList<Usuario> getUsuarios() { return usuarios; }
    public ArrayList<Administrador> getAdministradores() { return administradores; }
    public ArrayList<Evento> getEventos() { return eventos; }
    public ArrayList<Compra> getCompras() { return compras; }
    public ArrayList<Pago> getPagos() { return pagos; }
    public ArrayList<Facturas> getFacturas() { return facturas; }
    public ArrayList<Zona> getZonas() { return zonas; }
    public ArrayList<Recinto> getRecintos() { return recintos; }
    public ArrayList<Incidencia> getIncidencias() {
        return incidencias;
    }
}