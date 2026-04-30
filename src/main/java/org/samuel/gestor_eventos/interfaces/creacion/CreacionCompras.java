package org.samuel.gestor_eventos.interfaces.creacion;

import org.samuel.gestor_eventos.enums.Entidades;
import org.samuel.gestor_eventos.enums.EstadoCompras;
import org.samuel.gestor_eventos.enums.EstadoEntrada;
import org.samuel.gestor_eventos.enums.TipoIncidencia;
import org.samuel.gestor_eventos.modelos.*;

import java.sql.Date;
import java.util.ArrayList;

public interface CreacionCompras {
    public Pasarela creacionCompra(int id, Usuario usuarioAsociado, float valor, Evento eventoAsociado, Date fechaCompra, EstadoCompras estado, ArrayList<String> conjuntoItems, ArrayList<String> serviciosAdicionales, ArrayList<Entrada> entradas);
    public Pasarela creandoEntrada(int id, Zona zona, Asiento asiento, double precioFinal, EstadoEntrada estado);
    public Pasarela creandoIncidencia(String descripcion, int id, TipoIncidencia tipo, Date fechaCreacion, Entidades entidadAfectada);
    public Pasarela creandoFactura(Compra compra, Pago pago);
}
