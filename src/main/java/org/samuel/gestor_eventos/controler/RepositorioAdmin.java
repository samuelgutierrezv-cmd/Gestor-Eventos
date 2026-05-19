package org.samuel.gestor_eventos.controler;

import org.samuel.gestor_eventos.enums.*;
import org.samuel.gestor_eventos.modelos.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class RepositorioAdmin {

    private static RepositorioAdmin instancia;

    private ArrayList<Usuario> usuarios;
    private ArrayList<Evento> eventos;
    private ArrayList<Zona> zonas;
    private ArrayList<Asiento> asientos;
    private ArrayList<Recinto> recintos;
    private ArrayList<Compra> compras;
    private ArrayList<Pago> pagos;
    private ArrayList<Facturas> facturas;

    private RepositorioAdmin() {

        usuarios = new ArrayList<>();
        eventos = new ArrayList<>();
        zonas = new ArrayList<>();
        asientos = new ArrayList<>();
        recintos = new ArrayList<>();
        compras = new ArrayList<>();
        pagos = new ArrayList<>();
        facturas = new ArrayList<>();

        cargarDatosPrueba();
    }

    public static RepositorioAdmin getInstance() {

        if (instancia == null) {
            instancia = new RepositorioAdmin();
        }

        return instancia;
    }

    private void cargarDatosPrueba() {

        usuarios.add(
                new Usuario(
                        "Samuel Torres",
                        1,
                        "samuel@gmail.com",
                        "3101",
                        new ArrayList<>(),
                        "123"
                )
        );

        usuarios.add(
                new Usuario(
                        "Laura Gómez",
                        2,
                        "laura@gmail.com",
                        "32098",
                        new ArrayList<>(),
                        "123"
                )
        );

        ArrayList<Asiento> asientosGeneral = new ArrayList<>();

        for (int fila = 1; fila <= 5; fila++) {

            for (int numero = 1; numero <= 10; numero++) {

                Asiento asiento = new Asiento(
                        numero,
                        EstadoAsiento.DISPONIBLE,
                        fila,
                        asientosGeneral.size() + 1
                );

                asientosGeneral.add(asiento);

                asientos.add(asiento);
            }
        }

        Zona zonaGeneral = new Zona.ZonaBuilder(
                80000.0,
                1,
                Sector.GENERAL,
                "General",
                5000
        )
                .setConfiguracionAsientos(asientosGeneral)
                .builder();

        zonas.add(zonaGeneral);

        recintos.add(
                new Recinto(
                        1,
                        "Estadio El Campín",
                        zonas,
                        "Bogotá",
                        "Cra 30 # 57-60"
                )
        );

        eventos.add(
                new Evento(
                        CategoriaEvento.CONCIERTO,
                        1,
                        "Festival Estéreo Picnic",
                        "4 escenarios",
                        "Festival principal",
                        "Bogotá",
                        LocalDate.now(),
                        "16:00",
                        "Sin reembolso",
                        recintos.get(0),
                        EstadoEvento.ACTIVO
                )
        );
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public ArrayList<Evento> getEventos() {
        return eventos;
    }

    public ArrayList<Compra> getCompras() {
        return compras;
    }

    public ArrayList<Pago> getPagos() {
        return pagos;
    }

    public ArrayList<Facturas> getFacturas() {
        return facturas;
    }

    public ArrayList<Zona> getZonas() {
        return zonas;
    }

    public ArrayList<Asiento> getAsientos() {
        return asientos;
    }
}