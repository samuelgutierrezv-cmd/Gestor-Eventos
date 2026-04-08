package org.samuel.gestor_eventos.modelos;

public abstract class Persona {
    protected String nombre;
    protected int di;
    protected String corroElectronico;
    protected int numeroTelefono;

    public Persona(String nombre, int di, String corroElectronico, int numeroTelefono) {
        this.nombre = nombre;
        this.di = di;
        this.corroElectronico = corroElectronico;
        this.numeroTelefono = numeroTelefono;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDi() {
        return di;
    }

    public String getCorroElectronico() {
        return corroElectronico;
    }

    public int getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDi(int di) {
        this.di = di;
    }

    public void setCorroElectronico(String corroElectronico) {
        this.corroElectronico = corroElectronico;
    }

    public void setNumeroTelefono(int numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }
}
