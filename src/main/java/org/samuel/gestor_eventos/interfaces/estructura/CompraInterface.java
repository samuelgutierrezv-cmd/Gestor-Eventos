package org.samuel.gestor_eventos.interfaces.estructura;

import java.util.ArrayList;

public interface CompraInterface {
    public double  definirValorTotal();
    public String definirServicios();
    public ArrayList<String> getServiciosAdicionales();
    public boolean annadirServiciosAdicionales(ArrayList<String> servicios);
}
