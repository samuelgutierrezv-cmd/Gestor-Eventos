package org.samuel.gestor_eventos.validaciones;

import java.util.Scanner;

public class ValidacionesTexto {

    public boolean ingresarTexto(String texto) {
        if(texto == null || texto.trim().isEmpty()) {
            return false;
        }else{
            return true;
        }
    }

    public boolean validarDescripciones(String texto) {
        boolean val = ingresarTexto(texto);
        boolean val2 = texto.length() >= 200;
        if(val2 && val ){
            return true;
        }else{
            return false;
        }
    }
}
