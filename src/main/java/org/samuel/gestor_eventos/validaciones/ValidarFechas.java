package org.samuel.gestor_eventos.validaciones;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.util.Locale;

public class ValidarFechas {


    //Validando si el tiempo de inicio
    //no es despues que el final
    public boolean validarTiempo(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio.isAfter(fechaFin)) {
            return false;
        } else {
            return true;
        }
    }

    /*Calcualando los dias de un prestamo o lo que sea*/
    /*Retorna en dias*/
    public int calculandoDias(LocalDate inicio, LocalDate fin) {
        Period periodo = Period.between(inicio, fin);
        return periodo.getDays();
    }

    /*Calculando el dia de inicio al dia de hoy*/
    /*Retorna en dias*/
    public int calcualndoDiasHoy(LocalDate inicio){
        LocalDate hoy = LocalDate.now();
        Period periodo = Period.between(inicio, hoy);
        return periodo.getDays();
    }


    //validando que si cumpla con el fromato de fecha
    public boolean validandTexto(String texto){
        String texto1 = texto.trim();
        if(texto.charAt(2) == '/' && texto.charAt(5) == '/' && texto.length() == 10){
            return true;
        }else{
            return false;
        }
    }

    //Formateando los texto que ingresa la gente
    //para que quede en localDate
    //formato fecha
    public LocalDate validarEntrdaFechas(String fecha ){
        // 2. Texto en formato personalizado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date2 = LocalDate.parse(fecha, formatter);
        System.out.println("Fecha 2: " + date2);
        return date2;
    }

}
