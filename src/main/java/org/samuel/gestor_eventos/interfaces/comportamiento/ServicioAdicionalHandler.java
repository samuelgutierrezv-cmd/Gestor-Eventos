package org.samuel.gestor_eventos.interfaces.comportamiento;

import org.samuel.gestor_eventos.interfaces.estructura.*;
import org.samuel.gestor_eventos.modelos.Compra;
import org.samuel.gestor_eventos.modelos.Evento;

import javax.swing.*;
import java.util.ArrayList;

public class ServicioAdicionalHandler extends ProcesoDeCompra {

    @Override
    public Compra procesar(Compra compra, Evento evento) {

        String nombre = JOptionPane.showInputDialog(
                "Servicios: VIP, Parqueadero, VIP y Parqueadero o Ninguno"
        ).trim().toLowerCase();

        ArrayList<String> servicios = new ArrayList<>();

        if(nombre.equals("vip")){

            CompraInterface vip = new DecoratorVIP(compra);

            compra.setValor(vip.definirValorTotal());

            servicios.add("VIP");

            compra.setServiciosAdicionales(servicios);

        } else if(nombre.equals("parqueadero")) {

            CompraInterface parqueadero = new DecoratorParqueadero(compra);

            compra.setValor(parqueadero.definirValorTotal());

            servicios.add("Parqueadero");

            compra.setServiciosAdicionales(servicios);

        } else if(nombre.equals("vip y parqueadero")) {

            CompraInterface ambos = new DecoratorVIP(new DecoratorParqueadero(compra));

            compra.setValor(ambos.definirValorTotal());

            servicios.add("VIP");
            servicios.add("Parqueadero");

            compra.setServiciosAdicionales(servicios);
        }

        if(siguiente != null){
            return siguiente.procesar(compra, evento);
        }
        return compra;
    }
}