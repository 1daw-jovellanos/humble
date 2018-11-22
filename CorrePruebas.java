/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;


/**
 *
 * @author victor
 */
public class CorrePruebas {

    public static String traduce(String s) {
        s = s.replace("expected:", "Se esperaba ");
        s = s.replace("but was:", "pero se ha encontrado ");
        return (s);
    }

    public static void main(String[] args) {
        String msg="";
        int numFallos = 1;
        try {
            Result r = JUnitCore.runClasses(Class.forName("pptls.MainTest"));
            numFallos = r.getFailureCount();
            int numPruebas = r.getRunCount();
            msg += String.format("No se ha superado %d pruebas de las %d realizadas.%n", numFallos, numPruebas);
            if (numFallos == 0) {
                msg += "Ok. Pruebas pasadas.";
            } else {
                msg += "Se han pasado algunas pruebas al programa y se han detectado fallos:\n";
                msg += "-" + traduce(r.getFailures().get(0).getMessage()) + "\n";
            }
            
        } catch (ClassNotFoundException ex) {
            msg += "Error interno: No puedo cargar las pruebas";
        }
        System.out.println(msg);
        System.exit(numFallos);
    }
    
}
