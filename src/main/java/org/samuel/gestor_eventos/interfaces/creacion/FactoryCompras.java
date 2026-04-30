package org.samuel.gestor_eventos.interfaces.creacion;

import org.samuel.gestor_eventos.enums.Entidades;
import org.samuel.gestor_eventos.enums.EstadoCompras;
import org.samuel.gestor_eventos.enums.EstadoEntrada;
import org.samuel.gestor_eventos.enums.TipoIncidencia;
import org.samuel.gestor_eventos.modelos.*;

import java.sql.Date;
import java.util.ArrayList;

public class FactoryCompras implements CreacionCompras{
    @Override
    public Pasarela creacionCompra(int id, Usuario usuarioAsociado, float valor, Evento eventoAsociado, Date fechaCompra, EstadoCompras estado, ArrayList<String> conjuntoItems, ArrayList<String> serviciosAdicionales, ArrayList<Entrada> entradas) {
        return new Compra(id,usuarioAsociado, valor, eventoAsociado, fechaCompra, estado,conjuntoItems, serviciosAdicionales,entradas );
    }

    @Override
    public Pasarela creandoEntrada(int id, Zona zona, Asiento asiento, double precioFinal, EstadoEntrada estado) {
        return new Entrada(id,zona, asiento,precioFinal,estado);
    }

    @Override
    public Pasarela creandoIncidencia(String descripcion, int id, TipoIncidencia tipo, Date fechaCreacion, Entidades entidadAfectada) {
        return new Incidencia(descripcion,id,tipo,fechaCreacion,entidadAfectada);
    }

    @Override
    public Pasarela creandoFactura(Compra compra, Pago pago) {
        return Facturas.getInstance(compra,pago);
    }
}
