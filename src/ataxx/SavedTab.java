
package ataxx;
import java.io.*;
import java.util.*;

/**
 * La clase <code>SavedTab</code> almacena todos los datos de una partida.
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
public class SavedTab implements Serializable,Comparable{

    /**
     * Tablero de juego.
     */
    private GestorDeMovimientos tab;
    /**
     * Primer jugador.
     */
    private Player jugador1;
    /**
     * Segundo jugador.
     */
    private Player jugador2;
    /**
     * Nombre de la partida.
     */
    private String nombre;
    /**
     * Fecha en que se guarda.
     */
    private GregorianCalendar fecha;
    /**
     * Jugador que empieza a mover.
     */
    private int turno;
    
    /**
     * Crea una partida guardada en la que sólo se especifica el nombre.
     * @param nombre Nombre de la partida
     */
    public SavedTab(String nombre){
        this.nombre=nombre;
        this.tab=null;
        this.jugador1=null;
        this.jugador2=null;
        this.fecha=null;
    }
    
    /**
     * Crea un partida guardada con toda la información. La información incluye el estado del tablero de juego en el
     * momento de guardar, los nombres de los jugadores, el nombre de la partida, la fecha en que se guarda y el
     * jugador que comenzará moviendo cuando se reanude.
     * @param tab Tablero de juego
     * @param jugador1 Primer jugador
     * @param jugador2 Segundo jugador
     * @param nombre Nombre de la partida
     * @param fecha Fecha en que se guarda
     * @param turno Jugador que comenzará a mover
     */
    public SavedTab(GestorDeMovimientos tab,Player jugador1,Player jugador2,
            String nombre,GregorianCalendar fecha, int turno){
        this.tab=tab;
        this.jugador1=jugador1;
        this.jugador2=jugador2;
        this.nombre=nombre;
        this.fecha=fecha;
        this.turno=turno;
    }
    
    /**
     * Crea una partida guardada por defecto. Inicia todos los datos miembro como nulos.
     */
    public SavedTab(){
        this.nombre=null;
        this.tab=null;
        this.jugador1=null;
        this.jugador2=null;
        this.fecha=null;
    }
    
    /**
     * Devuelve el tablero de juego asociado a una partida guardada.
     * @return Tablero de juego
     */
    public GestorDeMovimientos getGestor(){
        return tab;
    }
    /**
     * Devuelve el primer jugador asociado a una partida guardada.
     * @return Primer jugador
     */
    public Player getJugador1(){
        return jugador1;
    }
    /**
     * Devuelve el segundo jugador asociado a una partida guardada.
     * @return Segundo jugador
     */
    public Player getJugador2(){
        return jugador2;
    }
    /**
     * Devuelve el nombre asociado a una partida guardada.
     * @return Nombre de la partida
     */
    public String getNombre(){
        return nombre;
    }
    /**
     * Devuelve la fecha asociada a una partida guardada.
     * @return Fecha en que se guarda
     */
    public GregorianCalendar getFecha(){
        return fecha;
    }
    /**
     * Devuelve el jugador que empieza a mover asociado a una partida guardada.
     * @return Jugador que empieza a mover
     */
    public int getTurno(){
        return turno;
    }
    /**
     * Compara las partidas guardadas por nombre.
     * @see String#compareTo(String)
     */
    public int compareTo(Object s2){
        SavedTab s=(SavedTab)s2;
        return nombre.compareTo(s.nombre);
    }
    /**
     * Sobreescritura del método <code>toString()</code>. Al imprimir por pantalla una partida guardada aparecerá su 
     * nombre.
     */
    public String toString(){
        return nombre;
    }

}
