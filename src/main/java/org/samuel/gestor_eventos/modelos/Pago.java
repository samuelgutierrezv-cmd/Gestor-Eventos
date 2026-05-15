package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.interfaces.comportamiento.Strategy;
import org.samuel.gestor_eventos.enums.EstadoPago;
import org.samuel.gestor_eventos.interfaces.creacion.EventoComponente;
import org.samuel.gestor_eventos.interfaces.creacion.Pasarela;

import java.sql.Date;
import java.time.LocalDate;

public class Pago implements Pasarela {

    private int idPago;
    private double monto;
    private Date fecha;
    private EstadoPago estado;
    private String metodoPago;

    private Strategy strategy;

    public Pago(int idPago, double monto, Date fecha, EstadoPago estado, String metodoPago) {
        this.idPago = idPago;
        this.monto = monto;
        this.fecha = fecha;
        this.estado = estado;
        this.metodoPago = metodoPago;
    }


    public int getIdPago() {
        return idPago;
    }

    public double getMonto() {
        return monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setEstado (EstadoPago estado) {
        this.estado = estado;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean actualizar(Pasarela pasarela) {
        if (pasarela instanceof Pago) {
            Pago p = (Pago) pasarela;

            if (this.idPago == p.idPago) {
                this.monto = p.monto;
                this.fecha = p.fecha;
                this.estado = p.estado;
                this.metodoPago = p.metodoPago;
                this.strategy = p.strategy;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        return this.idPago == id;
    }

    @Override
    public Pasarela buscar(int id) {
        if (this.idPago == id) {
            return this;
        }
        return null;
    }

    @Override
    public boolean guardar(Pasarela pasarela) {
        return false;
    }

    public boolean procesarPago() {

        if (strategy == null) {
            throw new IllegalStateException("No hay metodo de pago seleccionado");
        }

        boolean resultado = strategy.pago(monto);

        if (resultado) {
            estado = EstadoPago.APROBADO;
        } else {
            estado = EstadoPago.RECHAZADO;
        }
        return resultado;
    }
}