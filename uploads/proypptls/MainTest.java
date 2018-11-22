/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pptls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author victor
 */
public class MainTest {

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    public void setIn(CharSequence input) {
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        System.setIn(new ByteArrayInputStream(input.toString().getBytes()));
    }

    @Before
    public void setUpStreams() {
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test(timeout = 500)
    public void testConvertirNombreANumero() {
        String msg = "ConvertirNombreANumero no se comporta como se espera.";
        Main app = new Main();
        assertEquals(msg, 0, app.convertirNombreANumero("piedra"));
        assertEquals(msg, 1, app.convertirNombreANumero("Spock"));
        assertEquals(msg, 2, app.convertirNombreANumero("papel"));
        assertEquals(msg, 3, app.convertirNombreANumero("lagarto"));
        assertEquals(msg, 4, app.convertirNombreANumero("tijeras"));
        assertEquals(msg, -1, app.convertirNombreANumero("zas"));
        assertEquals(msg, -1, app.convertirNombreANumero(""));
    }

    public int convertirNombreANumero(String nombre) {
        int resultado;
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

    public String convertirNumeroANombre(int numero) {
        String[] nombre = new String[]{"piedra", "Spock", "papel", "lagarto", "tijeras"};
        if (numero >= 0 && numero <= 4) {
            return nombre[numero];
        } else {
            return "incorrecto";
        }
    }

    @Test(timeout = 500)
    public void testConvertirNumeroANombre() {
        String msg = "ConvertirNumeroANombre no se compora como se espera.";
        Main app = new Main();
        assertEquals(msg, "piedra", app.convertirNumeroANombre(0));
        assertEquals(msg, "Spock", app.convertirNumeroANombre(1));
        assertEquals(msg, "papel", app.convertirNumeroANombre(2));
        assertEquals(msg, "lagarto", app.convertirNumeroANombre(3));
        assertEquals(msg, "tijeras", app.convertirNumeroANombre(4));
        assertEquals(msg, "", app.convertirNumeroANombre(-1));

    }

    @Test(timeout = 3000)
    public void testRun3() {
        testRun();
        testRun();
        testRun();
    }

    public void testRun() {
        int puntosJugador = 0;
        int puntosOrdenador = 0;
        String msg = "La salida por consola no es la esperada. ";
        final String msgIncorrecto = msg + "Espero que me pidan la jugada con \"Tu jugada: \". Si la introduzco mal, espero que me digas \"incorrecto\"";
        setIn("pieda\n\nwoo\npapel\nfoo\ntijeras\npiedra\npapel\n\tijeras\n");
        new Main().run();
        restoreStreams();
        String[] lineas = outContent.toString().split(System.lineSeparator());
//        for (int i = 0; i < lineas.length; i++) {
//            System.err.println(i + lineas[i]);
//        }
        assertEquals(msgIncorrecto, "Tu jugada: incorrecto", lineas[0]);
        assertEquals(msgIncorrecto, "Tu jugada: incorrecto", lineas[1]);
        assertEquals(msgIncorrecto, "Tu jugada: incorrecto", lineas[2]);
        assertTrue(lineas[3].startsWith("Tu jugada: Ordenador saca "));
        int ordenador = convertirNombreANumero(lineas[3].split(" ")[4]);
        assertTrue(msg + "No entiendo cuál es la jugada del ordenador.", ordenador >= 0);
        int decision = (ordenador - convertirNombreANumero("papel") + 5) % 5;
        if (decision == 0) {
            assertEquals(msg + "'empate' no indicado correctamente.", "empate", lineas[4]);
        } else if (decision > 2) {
            assertEquals(msg + "'gana jugador' no indicado correctamente.", "gana jugador", lineas[4]);
            puntosJugador++;
        } else {
            assertEquals(msg + "'gana ordenador' no indicado correctamente.", "gana ordenador", lineas[4]);
            puntosOrdenador++;
        }
        assertEquals(msgIncorrecto, "Tu jugada: incorrecto", lineas[5]);

        assertTrue(msg + "No entiendo cuál es la jugada del ordenador.", lineas[6].startsWith("Tu jugada: Ordenador saca "));
        ordenador = convertirNombreANumero(lineas[6].split(" ")[4]);
        assertTrue(msg + "No entiendo cuál es la jugada del ordenador.", ordenador >= 0);
        decision = (ordenador - convertirNombreANumero("tijeras") + 5) % 5;
        if (decision == 0) {
            assertEquals(msg + "'empate' no indicado correctamente.", "empate", lineas[7]);
        } else if (decision > 2) {
            assertEquals(msg + "'gana jugador' no indicado correctamente.", "gana jugador", lineas[7]);
            puntosJugador++;
        } else {
            assertEquals(msg + "'gana ordenador' no indicado correctamente.", "gana ordenador", lineas[7]);
            puntosOrdenador++;
        }

        assertTrue(msg + "No sé cuál es la jugada del ordenador", lineas[8].startsWith("Tu jugada: Ordenador saca "));
        ordenador = convertirNombreANumero(lineas[8].split(" ")[4]);
        assertTrue(msg + "No sé cuál es la jugada del ordenador", ordenador >= 0);
        decision = (ordenador - convertirNombreANumero("piedra") + 5) % 5;
        if (decision == 0) {
            assertEquals(msg + "'empate' no indicado correctamente.", "empate", lineas[9]);
        } else if (decision > 2) {
            assertEquals(msg + "'gana jugador' no indicado correctamente.", "gana jugador", lineas[9]);
            puntosJugador++;
        } else {
            assertEquals(msg + "'gana ordenador' no indicado correctamente.", "gana ordenador", lineas[9]);
            puntosOrdenador++;
        }
        assertEquals(msg + "El marcador final no tiene el formato correcto.", String.format("Jugador: %d; Ordenador: %d;", puntosJugador, puntosOrdenador),
                lineas[10]);
    }

    @Test(timeout = 1000)
    public void testRonda3() {
        testRonda();
        testRonda();
        testRonda();
    }

    public void testRonda() {
        String msg = "La salida que emite el método ronda() no es la esperada. ";
        final String msgIncorrecto = msg + "Espero que me pidan la jugada con \"Tu jugada: \". Si la introduzco mal, espero que me digas \"incorrecto\"";
        setIn("faas\ntijeras\n");
        Main app = new Main();
        app.in = new Scanner(System.in);
        app.ronda();
        restoreStreams();
        String[] lineas = outContent.toString().split(System.lineSeparator());
//        for (String linea : lineas) {
//            System.err.println(linea);
//        }
        assertEquals(msgIncorrecto, "Tu jugada: incorrecto", lineas[0]);
        assertTrue(lineas[1].startsWith("Tu jugada: Ordenador saca "));
        int ordenador = convertirNombreANumero(lineas[1].split(" ")[4]);
        assertTrue(msg + "No entiendo cuál es la jugada del ordenador.", ordenador >= 0);
        int decision = (ordenador - convertirNombreANumero("tijeras") + 5) % 5;
        if (decision == 0) {
            assertEquals(msg + "'empate' no indicado correctamente.", "empate", lineas[2]);
        } else if (decision > 2) {
            assertEquals(msg + "'gana jugador' no indicado correctamente.", "gana jugador", lineas[2]);
        } else {
            assertEquals(msg + "'gana ordenador' no indicado correctamente.", "gana ordenador", lineas[2]);
        }
    }

    @Test(timeout = 100)
    public void testReflection()  {
        int fieldCount =  Main.class.getDeclaredFields().length;
        assertEquals("Debería haber exactamente tres variables de instancia", 3, fieldCount);
        try {
            assertTrue("Debería haber una variable de instancia 'in' para la entrada por teclado", Main.class.getDeclaredField("in").getType().equals(Scanner.class));
        } catch (NoSuchFieldException ex) {
            fail("Debería haber una variable de instancia 'in' para la entrada por teclado");
        } 
        try {
            assertTrue("Debería haber una variable de instancia 'puntosJugador' de tipo int.", Main.class.getDeclaredField("puntosJugador").getType().toString().equals("int"));
        } catch (NoSuchFieldException ex) {
            fail("Debería haber una variable de instancia 'puntosJugador' de tipo int.");
        } 
        try {
            assertTrue("Debería haber una variable de instancia 'puntosOrdenador' de tipo int.", Main.class.getDeclaredField("puntosOrdenador").getType().toString().equals("int"));
        } catch (NoSuchFieldException ex) {
            fail("Debería haber una variable de instancia 'puntosOrdenador' de tipo int.");
        } 
        Main app=new Main();
        assertEquals("El scanner se debe inicializar en run. No debe darse valor directo a las variablas de instancia",
                app.in, null);
    }
}
