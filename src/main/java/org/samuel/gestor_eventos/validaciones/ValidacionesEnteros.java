package org.samuel.gestor_eventos.validaciones;

public class ValidacionesEnteros {

    public boolean ingresarEnteros(String numero) {
        try {
            Integer.parseInt(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public boolean validarEnterosPositivos(String numero) {
        boolean val = ingresarEnteros(numero);
        int num = Integer.parseInt(numero);
        if(val && num > 0){
            return true;
        }else{
            return false;
        }
    }
}
