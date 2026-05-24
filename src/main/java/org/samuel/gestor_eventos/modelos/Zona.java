package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.enums.EstadoAsiento;
import org.samuel.gestor_eventos.enums.Sector;
import org.samuel.gestor_eventos.interfaces.creacion.EventoComponente;

import java.util.ArrayList;

public class Zona implements EventoComponente {
    private Sector sector;
    private int id;
    private String nombre;
    private int capacidad;
    private double precioBase;
    private ArrayList<Asiento> configuracionAsientos;

    public Zona(ZonaBuilder builder){
        this.nombre = builder.nombre;
        this.id = builder.id;
        this.capacidad = builder.capacidad;
        this.precioBase = builder.precioBase;
        this.sector = builder.sector;
        this.configuracionAsientos = builder.configuracionAsientos;
    }

    @Override
    public boolean actualizar(EventoComponente componente) {

        // actualizar zona
        if (componente instanceof Zona) {
            Zona z = (Zona) componente;

            if (this.id == z.id) {
                this.nombre = z.nombre;
                this.capacidad = z.capacidad;
                this.precioBase = z.precioBase;
                this.sector = z.sector;
                return true;
            }
        }

        // delegar a asientos
        if (configuracionAsientos != null) {
            for (Asiento a : configuracionAsientos) {
                if (a.actualizar(componente)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean eliminarEvento(int id) {
        if (configuracionAsientos != null) {
            return configuracionAsientos.removeIf(
                    asiento -> asiento.getId() == id
            );
        }
        return false;
    }

    @Override
    public EventoComponente buscar(int id) {
        if (this.id == id) return this;

        if (configuracionAsientos != null) {
            for (Asiento a : configuracionAsientos) {
                EventoComponente encontrado = a.buscar(id);
                if (encontrado != null) {
                    return encontrado;
                }
            }
        }
        return null;
    }

    @Override
    public boolean guardarComponente(EventoComponente componente) {
        if (componente instanceof Asiento) {
            if (configuracionAsientos == null) {
                configuracionAsientos = new ArrayList<>();
            }
            return configuracionAsientos.add((Asiento) componente);
        }
        return false;
    }

    public static class ZonaBuilder{
        private Sector sector;
        private int id;
        private String nombre;
        private int capacidad;
        private double precioBase;
        private ArrayList<Asiento> configuracionAsientos;

        public ZonaBuilder(double precioBase,int id, Sector sector, String nombre, int capacidad){
            this.capacidad = capacidad;
            this.id = id;
            this.nombre = nombre;
            this.sector = sector;
            this.precioBase = precioBase;
        }

        public ZonaBuilder setConfiguracionAsientos(ArrayList<Asiento> configuracionAsientos){
            this.configuracionAsientos = configuracionAsientos;
            return this;
        }

        public Zona builder(){
            return new Zona(this);
        }
    }

    public void generarAsientosAutomaticos() {

        if (configuracionAsientos == null) {
            configuracionAsientos = new ArrayList<>();
        }

        configuracionAsientos.clear();

        int cantidadFilas = 10;
        int asientosPorFila = (int) Math.ceil((double) capacidad / cantidadFilas);
        int contador = 1;
        for (int fila = 1; fila <= cantidadFilas; fila++) {
            for (int numero = 1; numero <= asientosPorFila; numero++) {
                if (contador > capacidad) {
                    return;
                }

                Asiento asiento = new Asiento(
                    numero,
                    EstadoAsiento.DISPONIBLE,
                    fila,
                    contador
                );

                configuracionAsientos.add(asiento);
                contador++;
            }
        }
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public void setConfiguracionAsientos(ArrayList<Asiento> configuracionAsientos) {
        this.configuracionAsientos = configuracionAsientos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public Sector getSector() {
        return sector;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public ArrayList<Asiento> getConfiguracionAsientos() {
        return configuracionAsientos;
    }

    // Funciones basicas

    public void agregarComponente(Asiento asiento) {
        if (asiento == null) {
            throw new IllegalArgumentException("Asiento no valido");
        }

        if (configuracionAsientos == null) {
            configuracionAsientos = new ArrayList<>();
        }

        if (configuracionAsientos.size() >= capacidad) {
            throw new IllegalStateException("Zona llena");
        }

        configuracionAsientos.add(asiento);
    }


    public double calcularOcupacion() {
        if (capacidad == 0) return 0;

        int ocupados = 0;

        if (configuracionAsientos != null) {
            for (Asiento a : configuracionAsientos) {
                if (a.getEstado() == EstadoAsiento.RESERVADO) {
                    ocupados++;
                }
            }
        }

        return ((double) ocupados / capacidad) * 100;
    }

    @Override
    public String toString() {

        return nombre
                + " | "
                + sector
                + " | Capacidad: "
                + capacidad
                + " | Precio: "
                + precioBase;
    }
}