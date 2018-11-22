/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pptls;

import java.util.*;

/**
 * Clase de la cosa.
 * @author victor
 */
public class Main {

    /** El scanner para leer del teclado. */
    private Scanner in;
    /** Los puntos del jugador. */
    int puntosJugador;
    /** Los puntos del ordenador. */
    int puntosOrdenador;
    /**
     * Un método de convertir.
     * @param nombre la cosa de la cosa
     * @return pepe
     */
    public int convertirNombreANumero(String nombre) {
        int resutado;
        switch (nombre) {
            case "piedra":
                resultado = 0;
                break;
            case "Spock":
                resultado = 1;
                break;
            case "papel":
                resultado = 2;
                break;
            case "lagarto":
                resultado = 3;
                break;
            case "tijeras":
                resultado = 4;
                break;
            default:
                resultado = -1;
        }
        return resultado;
    }

    /**
     * Un método de convertir.
     * @param numero la cosa de la cosa
     * @return pepe
     */
    public String convertirNumeroANombre(int numero) {
        String[] nombre = new String[]{"piedra", "Spock", "papel", "lagarto", "tijeras"};

        if (numero >= 0 && numero <= 4) {
            return nombre[numero];
        } else {
            return "incorrecto";
        }
    }

    /**
     * Dale.
     */
    public void jugada() {
        int numeroJugador;
        do {
            System.out.print("Tu jugada: ");
            numeroJugador = convertirNombreANumero(in.nextLine());
            if (numeroJugador == -1) {
                System.out.println("incorrecto");
            }
        } while (numeroJugador == -1);
        int numeroOrdenador = (int) (Math.random() * 5);
        System.out.format("Ordenador saca %s%n", convertirNumeroANombre(numeroOrdenador));
        int decision = ((numeroOrdenador - numeroJugador + 5) % 5);
        System.err.println(numeroJugador + " " + numeroOrdenador + " " + decision);
        if (decision == 0) {
            System.out.println("empate");
        } else if (decision > 2) {
            System.out.println("gana jugador");
            puntosJugador++;
        } else {
            System.out.println("gana ordenador");
            puntosOrdenador++;
        }
    }

    /**
     * run.
     */
    public void run() {
        in = new Scanner(System.in);
        puntosJugador = 0;
        puntosOrdenador = 0;
        for (int i = 1; i <= 3; i++) {
            jugada();
        }
        System.out.format("Jugador: %d; Ordenador: %d;%n", puntosJugador, puntosOrdenador);
    }

    /**
     * main.
     * @param args asas
     */
    public static void main(String[] args) {
        new Main().run();
    }

}
