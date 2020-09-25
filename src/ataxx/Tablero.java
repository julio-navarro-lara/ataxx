

package ataxx;

import java.io.*;
/**
 * La clase <code>Tablero</code> construye el tablero de juego y gestiona las casillas.
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
public class Tablero implements Serializable {
    /**
    * Casillas del tablero.
    */
    protected int[][] t;
    /**
     * Se asigna al primer jugador el número 1.
     */
    final static int JUGADOR1=1;
    /**
     * Se asigna al segundo jugador el número 2.
     */
    final static int JUGADOR2=2;
    /*
     * Casillas libres-> 0
     *    ""    jugador1-> 1
     *    ""    jugador2-> 2 
     */
    /**
     * Permite crear un tablero a partir de la introducción del número de filas y de columnas. El tablero es un
     * <code>array</code> de enteros, y en cada posición se almacena un número que depende del estado de la casilla
     * asociada a esa posición. Los posibles valores para este número son 0, si la casilla está libre, 1 si en la
     * casilla hay una ficha del primer jugador o 2 si en la casilla hay una ficha del segundo jugador. Puntualmente 
     * también puede tomar el valor 3, si esa casilla puede ser ocupada por alguna ficha al moverse.
     * Por defecto se añaden cuatro fichas, dos de cada jugador, cuyas posiciones vienen indicadas por el 
     * método <code>inicializarTablero()</code>
     * @param a Número de filas.
     * @param b Número de columnas.
     * @see #inicializarTablero()
     */
    public Tablero(int a,int b){
        t=new int[a][b];
        inicializarTablero();
    }
    
    /**
     * Añade una ficha al tablero. Se modifica en el tablero el valor de la casilla que pasamos como argumento, el
     * nuevo valor corresponde al entero que representa al jugador que coloca la ficha.
     * @param jugador Jugador al que pertenecerá la ficha.
     * @param x Número de fila.
     * @param y Número de columna.
     */
    public void colocarFicha(int jugador, int x, int y){
        t[x][y]=jugador;
    }
    
    /**
     * Comprueba si en un tablero quedan casillas libres.
     * @return <code>false</code> si quedan casillas libres o <code>true</code> si todas las casillas están ocupadas.
     */
    public boolean estaLleno(){
        boolean estaLleno=true;
        //Recorre todas las casillas del tablero
        for(int[] i:t){
            for(int j: i){
                //Si alguna vale 0 o 3 no está lleno
                if(j==0||j==3){
                    estaLleno=false;
                    break;
                }
            }
        }
        return estaLleno;
    }
    
    /**
     * Delvuelve el <code>array</code>de enteros que representa el tablero.
     * @return <code>Array</code>de enteros que muestra el estado del tablero.
     */
    public int[][] getTablero(){
        return t;
    }
    
    /**
     * Inicializa un tablero con todas las casillas libres. Se añade un cero a cada una de las posiciones del 
     *<code>array</code>que representa el tablero.
     *@see #colocarFicha(int, int, int)
     */
    public void llenarDeCeros(){
        for(int i=0; i<t.length;i++){
            for(int j=0; j<t[i].length;j++){
                colocarFicha(0,i,j);
            }
        }
    }
    
    /**
     * Copia el tablero pasado como argumento al tablero del objeto que llama al método.
     * @param tab Tablero del que se creará una copia.
     */
    public void copiarTablero(Tablero tab){
        try{
        for(int i=0; i<tab.t.length; i++){
            for(int j=0; j<tab.t[i].length; j++){
                this.t[i][j]=tab.t[i][j];
            }
        }
        //Se devuelve una excepción si ambos tableros no tienen el mismo tamaño
        }catch(Exception e){
            System.out.println("No se pudo copiar el tablero");
        }
    }
    
    /**
     * Inicializa un tablero con dos fichas de cada jugador y el resto de las casillas libres. Las fichas del primer
     * jugador se colocan una en la fila 0, columna 0 y otra en la fila 6, columna 6. Las fichas segundo jugador se 
     * colocan una en la fila 6, columna 0 y otra en la fila 0, columna 6. 
     * @see #llenarDeCeros()
     * @see #colocarFicha(int, int, int)
     */
    public void inicializarTablero(){
      llenarDeCeros();
      colocarFicha(1, 0, 0);
      colocarFicha(1,6,6);
      colocarFicha(2, 6, 0);
      colocarFicha(2, 0, 6); 
    }
    
    /**
     * Cuenta las fichas del tablero que pertenecen a un jugador.
     * @param jugador Jugador del que se contarán las fichas.
     * @return Número de fichas del jugador.
     */
    public int contarFichas(int jugador){
        int fichas=0;
        //Se recorre todo el tablero
        for(int[] i:t){
            for(int j: i){
                //Si la ficha pertenece al jugador requerido, se aumenta el contador
                if(j==jugador){
                    fichas++;
                }
            }
        }
        return fichas;
    }
}
