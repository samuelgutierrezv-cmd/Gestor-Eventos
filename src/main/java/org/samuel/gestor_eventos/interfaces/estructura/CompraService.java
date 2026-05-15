package org.samuel.gestor_eventos.interfaces.estructura;

import org.samuel.gestor_eventos.interfaces.comportamiento.Iguales;
import org.samuel.gestor_eventos.interfaces.comportamiento.ProcesoDeCompra;
import org.samuel.gestor_eventos.modelos.Compra;
import org.samuel.gestor_eventos.modelos.Evento;

import javax.swing.*;
import java.util.ArrayList;

public class CompraService extends ProcesoDeCompra {
    @Override
    public Iguales procesar(Compra compra, Evento evento) {
        String nombre = JOptionPane.showInputDialog("Ingresa si quieres servicios adiccionales como (VIP , Parqueadero, Ninguno, VIP y parqueadero)").trim().toLowerCase();
        if(nombre.equals("vip")){
            CompraInterface vip = new DecoratorVIP(compra);
            compra.setValor(vip.definirValorTotal());
            compra.setServiciosAdicionales(vip.definirServiciosAdiccionales());
            return compra;
        }else if(nombre.equals("parqueadero")) {
            CompraInterface parqueadero = new DecoratorParqueadero(compra);
            compra.setValor(parqueadero.definirValorTotal());
            compra.setServiciosAdicionales(parqueadero.definirServiciosAdiccionales());
            return compra;
        } else if(nombre.equals("vip y parqueadero")){
            CompraInterface ambos = new DecoratorVIP(new DecoratorParqueadero(compra));
            compra.setValor(ambos.definirValorTotal());
            compra.setServiciosAdicionales(ambos.definirServiciosAdiccionales());
            return compra;
        }else if(nombre.equals("ninguno")) {
            JOptionPane.showInputDialog(null, "Excelente sigamos.");
            return compra;
        }
        return compra;
    }
}