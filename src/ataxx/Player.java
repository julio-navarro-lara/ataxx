
package ataxx;

import java.util.*;
import java.io.*;

/**
 * La clase <code>Player</code> incluye todo lo necesario para identificar a un jugador y para gestionar sus 
 * estadísticas.
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
public class Player implements Serializable{
   /**
    * Alias.
    */
    private String alias;
    /**
     * Contraseña.
     */
    private String contrasenha;
    /**
     * Nombre.
     */
    private String nombre;
    /**
     * Apellidos.
     */
    private String apellidos;
    /**
     * Fecha de nacimiento.
     */
    private GregorianCalendar nacimiento;
    /**
     * Fecha de registro.
     */
    private GregorianCalendar registro;
    
    /**
     * Nº de partidas ganadas. Cada elemento de la matriz corresponde a una situación:
     * 0 - Partidas ganadas contra otros usuarios
     * 1 - Partidas ganadas contra la máquina en nivel fácil.
     * 2 - Partidas ganadas contra la máquina en nivel medio.
     * 3 - Partidas ganadas contra la máquina en nivel difícil.
     */
    private int[] partidasGanadas={0,0,0,0};
    /**
     * Nº de partidas perdidas. Cada elemento de la matriz corresponde a una situación:
     * 0 - Partidas perdidas contra otros usuarios
     * 1 - Partidas perdidas contra la máquina en nivel fácil.
     * 2 - Partidas perdidas contra la máquina en nivel medio.
     * 3 - Partidas perdidas contra la máquina en nivel difícil.
     */
    private int[] partidasPerdidas={0,0,0,0};
    /**
     * Nº de partidas empatadas. Cada elemento de la matriz corresponde a una situación:
     * 0 - Partidas empatadas contra otros usuarios
     * 1 - Partidas empatadas contra la máquina en nivel fácil.
     * 2 - Partidas empatadas contra la máquina en nivel medio.
     * 3 - Partidas empatadas contra la máquina en nivel difícil.
     */
    private int[] partidasEmpatadas={0,0,0,0};
    
    /**
     * Nº de movimientos realizados.
     */
    private int movimientos=0;
    /**
     * Nº de veces en las que se ha iniciado una sesión.
     */
    private int vecesInicio=0;
    
    /**
     * Crea un jugador con todos sus datos.
     * @param alias Alias
     * @param contraseña Contraseña
     * @param nombre Nombre
     * @param apellidos Apellidos
     * @param nacimiento Fecha de nacimiento
     * @param registro Fecha de registro
     */
    public Player(String alias,String contrasenha,String nombre,String apellidos,
            GregorianCalendar nacimiento,GregorianCalendar registro){
        this.alias=alias;
        this.contrasenha=contrasenha;
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.nacimiento=nacimiento;
        this.registro=registro;
    }
    
    /**
     * Crea un jugador sólo con su alias.
     * @param alias Alias del jugador
     */
    public Player(String alias){
        this.alias=alias;
    }
    
    /**
     * Devuelve el alias del jugador.
     * @return Alias del jugador
     */
    public String getAlias(){
        return alias;
    }
    /**
     * Devuelve la contraseña del jugador.
     * @return Contraseña del jugador
     */
    public String getContrasenha(){
        return contrasenha;
    }
    /**
     * Devuelve el nombre del jugador.
     * @return Nombre del jugador
     */
    public String getNombre(){
        return nombre;
    }
    /**
     * Devuelve los apellidos del jugador.
     * @return Apellidos del jugador
     */
    public String getApellidos(){
        return apellidos;
    }
    /**
     * Devuelve la fecha de nacimiento del jugador.
     * @return Fecha de nacimiento del jugador
     */
    public GregorianCalendar getNacimiento(){
        return nacimiento;
    }
    /**
     * Devuelve la fecha de registro del jugador.
     * @return Fecha de registro del jugador
     */
    public GregorianCalendar getRegistro(){
        return registro;
    }
    
    /**
     * Aumenta en una unidad el número de partidas ganadas dada una dificultad determinada.
     * @param dificultad Posición de la matriz de victorias que quiere aumentarse.
     */
    public void aumentarGanadas(int dificultad){
        partidasGanadas[dificultad]++;
    }
    /**
     * Devuelve la matriz de partidas ganadas.
     * @return Nº de partidas ganadas para cada situación
     */
    public int[] getGanadas(){
        return partidasGanadas;
    }
    /**
     * Aumenta en una unidad el número de partidas perdidas dada una dificultad determinada.
     * @param dificultad Posición de la matriz de derrotas que quiere aumentarse.
     */
    public void aumentarPerdidas(int dificultad){
        partidasPerdidas[dificultad]++;
    }
    /**
     * Devuelve la matriz de partidas perdidas.
     * @return Nº de partidas perdidas para cada situación
     */
    public int[] getPerdidas(){
        return partidasPerdidas;
    }
    /**
     * Aumenta en una unidad el número de partidas empatadas dada una dificultad determinada.
     * @param dificultad Posición de la matriz de empates que quiere aumentarse.
     */
    public void aumentarEmpates(int dificultad){
        partidasEmpatadas[dificultad]++;
    }
    /**
     * Devuelve la matriz de partidas empatadas.
     * @return Nº de partidas empatadas para cada situación
     */
    public int[] getEmpates(){
        return partidasEmpatadas;
    }
    
    /**
     * Incrementa en una unidad el número de movimientos.
     */
    public void aumentarMovimientos(){
        movimientos++;
    }
    /**
     * Delvuelve el número de movimientos.
     * @return Número de movimientos
     */
    public int getMovimientos(){
        return movimientos;
    }
    
    /**
     * Incrementa en una unidad el número de veces que se ha iniciado una sesión.
     */
    public void aumentarVecesInicio(){
        vecesInicio++;
    }
    /**
     * Devuelve el número de veces que se ha iniciado una sesión.
     * @return Número de veces que se ha iniciado una sesión
     */
    public int getVecesInicio(){
        return vecesInicio;
    }
    
    /**
     * Sobreescritura del método <code>toString()</code>. Al imprimir por pantalla un jugador aparecerá su alias.
     */
    public String toString(){
        return alias;
    }
}
