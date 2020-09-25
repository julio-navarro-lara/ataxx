
package ataxx;

import java.io.*;
import java.util.*;

/**
 * La clase <code>Cpu</code> contiene los métodos necesarios para que el ordenador pueda interactuar con el tablero
 * de juego y además controla todas las jugadas que se realizan. Esto permite que el ordenador sea capaz de decidir 
 * cual será la mejor jugada que puede realizar un jugador y actuar en consecuencia.
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
public class Cpu {
    /**
     * Tablero de juego.
     */
    private GestorDeMovimientos mov;
    /**
     * Asigana a max el primer jugador
     */
    private int max=1;
    /**
     * Asigna a min el segundo jugador
     */
    private int min=2;
    
    /**
     * Crea un tablero a partir de la introducción del número de filas y de columnas. El tablero creado pertenece
     * a la clase <code>GestorDeMovimientos</code> por lo que sobre él podemos realizar movimientos de fichas. 
     * Además, sobre este tablero actuan los métodos que permiten controlar la partida realizada por el ordenador. 
     * @param a Número de filas.
     * @param b Número de columnas.
     * @see GestorDeMovimientos#GestorDeMovimientos(int, int)
     */
    public Cpu(int a, int b){
        mov=new GestorDeMovimientos(a,b);
    }
    
    /**
     * Crea un tablero a partir de otro pasado como argumento. Se copia el contenido de un tablero perteneciente a la
     * clase<code>GestorDeMovimientos</code> a otro de la clase <code>Cpu</code>.
     * @param mov Objeto cuyo tablero se copiará al del nuevo objeto que se crea.
     * @see GestorDeMovimientos#GestorDeMovimientos(int, int)
     * @see Tablero#copiarTablero(Tablero)
     */
    public Cpu(GestorDeMovimientos mov){
        this.mov=new GestorDeMovimientos(mov.getTablero().length,mov.getTablero()[0].length);
        this.mov.copiarTablero(mov);
    }

    /**
     * Controla el juego del ordenador. El método recibe como argumentos un objeto de la clase 
     * <code>GestorDeMovimentos</code>, un jugador y los niveles de profundidad de búsqueda de comienzo y de fin.
     * 
     * A partir de estos datos, asigna a cada movimiento de cada una de las fichas de un jugador una puntuación. Esta
     * puntuación se basa en el número de fichas del jugador contrario que se convertirán en fichas del jugador que 
     * llama al método, y en la posterior jugada que podría hacer el oponente teniendo en cuenta el número de fichas
     * que perderá el jugador. Debemos señalar que para la max la mejor puntuación es el número más grande que
     * se obtenga al evaluar los tableros, y para min es el número más pequeño, ya que las fichas que convierte min en 
     * fichas propias se consideran negativas.
     * 
     * Cada uno de estos posibles movimientos se almacenan en un <code>ArrayList</code> y el método determinará para 
     * cada uno de ellos la puntuación. Finalmente, se escoge el movimiento que mejor puntuación haya tenido y se 
     * realiza sobre el tablero de juego. 
     * @param gdm Objeto sobre cuyo tablero se realizan los movimientos.
     * @param nivel Nivel de inicio
     * @param nivelMax Nivel máximo de profundidad
     * @param jugador Jugador al que le pertenece el turno
     * @return Entero que estima la efectividad de una jugada.
     * @see #evaluarTablero(int, GestorDeMovimientos)
     * @see GestorDeMovimientos#getTablero()
     * @see #verCamino(int)
     * @see GestorDeMovimientos#copiarTablero(Tablero)
     * @see GestorDeMovimientos#hacerMovimiento(int, int, int, int, int)
     */
    public int minimax(GestorDeMovimientos gdm,int nivel, int nivelMax,int jugador){
        ArrayList<GestorDeMovimientos> listTab=new ArrayList<GestorDeMovimientos>();
        //La mejor jugada se asigna en principio a un número muy alto o muy bajo, dependiendo si juega max o min.
        int mejorJugada=jugador==max?-99:99;
        
        //Si se llega al nivel máximo, se evalúa el tablero
        if(nivel==nivelMax){
            return evaluarTablero(jugador, gdm);
        }
        
        GestorDeMovimientos mejorTab=new GestorDeMovimientos(gdm.getTablero().length
                                ,gdm.getTablero()[0].length);
        int[] movs=new int[2];
        //Se generan todos los tableros posibles que pudiesen derivar de un movimiento por parte del jugador que llama al método
        for(int i=0;i<gdm.getTablero().length;i++){
            for(int j=0;j<gdm.getTablero()[i].length;j++){
                if(gdm.getTablero()[i][j]==jugador){
                    for(int k=24;k>=1;k--){
                        movs=verCamino(k);
                        GestorDeMovimientos tfalse=new GestorDeMovimientos(gdm.getTablero().length
                                ,gdm.getTablero()[0].length);
                        tfalse.copiarTablero(gdm);
                        //Si el movimiento se puede hacer, se añade al ArrayList de tableros
                        if(tfalse.hacerMovimiento(jugador,i,j,i+movs[0],j+movs[1])){
                            listTab.add(tfalse);
                        }
                    }
                }
            }
        }
        
        //Se recorren los tableros almacenados y se llama de nuevo al minimax en cada uno de ellos,
        //esta vez con el nivel incrementado en una unidad y con el otro jugador.
        for(GestorDeMovimientos g:listTab){
           int n=minimax(g,nivel+1,nivelMax,cambiarJugador(jugador));
           //Para max la mejor jugada será el mayor número devuelto, y para min, el menor.
           if(jugador==max&&n>mejorJugada){
               mejorJugada=n;
               mejorTab.copiarTablero(g);
           }else if(jugador==min&&n<mejorJugada){
               mejorJugada=n;
               mejorTab.copiarTablero(g);
           }
       }
        //Si se regresa al nivel base, se utiliza el tablero escogido.
       if(nivel==0){
          mov.copiarTablero(mejorTab);
       }
        //Se devuelve el resultado de la mejor evaluación
        return mejorJugada;
        
    }
    
    /**
     * Cuenta las fichas que tiene max o min en un tablero. A partir de un jugador y un tablero pasados como 
     * argumentos, el método determina si el jugador es max o min, y si es así cuenta las fichas que este jugador 
     * tiene en el tablero. 
     * En el caso de min el número de fichas es negativo. Esto es así porque el resultado que devuelve el método se 
     * emplea para evaluar la ventaja que tiene un jugador sobre otro en una determinada jugada.
     * Si el jugador no es ni max ni min se devuelve 0.
     * @param jugador Jugador que se evaluará
     * @param gdm Tablero sobre el que se contarán las fichas
     * @return Número entero que representa la ventaja que tiene un jugador frente a otro. En realidad, es el número de
     * fichas que tiene en el tablero.
     */
    public int evaluarTablero(int jugador,GestorDeMovimientos gdm){
        int resultado=0;
        if(jugador==max){
            resultado=gdm.contarFichas(jugador);
        }
        if(jugador==min){
            resultado=-gdm.contarFichas(jugador);
        }
        return resultado;
    }
    
    /**
     * Asigna a max el jugador activo y llama al método <code>minimax</code>. A partir de un tablero y el nivel máximo
     * pasados como argumentos, el método asigna el jugador activo a max y llama al método <code>minimax</code> aplicado
     * a max con ese tablero y con la dificultad establecida por el nivel máximo.
     * @param gdm Tablero de juego
     * @param nivelMax Dificultad
     * @see Gui#devolverJugadorActivo()
     * @see Gui#cambiarActivo(int)
     * @see #minimax(GestorDeMovimientos, int, int, int)
     */
    public void aplicarMinimax(GestorDeMovimientos gdm,int nivelMax){
        max=Gui.devolverJugadorActivo();
        min=cambiarJugador(Gui.devolverJugadorActivo());
        minimax(gdm,0,nivelMax,max);
    }
    
    /**
     * Devuelve el tablero sobre el que actúa el ordenador.
     * @return Tablero asociado al objeto de la clase <code>Cpu</code>
     */
    public GestorDeMovimientos getGDM(){
        return mov;
    }
    
    /**
     * Indica cuántas casillas tenemos que desplazar una ficha para realizar un movimiento. A partir de la posición a la
     * que se quiere mover una ficha, el método determina el número de casillas en horizontal y vertical que tenemos que
     * sumar para realizar el movimiento. La posiciones a mover van numeradas del 1 al 24.
     * @param cposicion Posición a la que se quire mover la ficha
     * @return <code>Array</code> donde el primer elemento es el número que hay que sumar a la coordenada
     * <code>x</code> para realizar el movimiento y el segundo es el número que hay que sumar a la coordenada
     * <code>y</code>.
     */
    public int[] verCamino(int cposicion){
        int sumx=0;
        int sumy=0;
        int[] a=new int[2];
        //Dependiendo del valor introducido se dan las diferentes adiciones a cada coordenada
        switch(cposicion){
            case 13: case 14: case 15: case 16: case 17:
                sumx=2;
                break;
            case 12: case 3: case 4: case 5: case 18:
                sumx=1;
                break;
            case 11: case 2: case 6: case 19:
                sumx=0;
                break;
            case 10: case 1: case 8: case 7: case 20:
                sumx=-1;
                break;
            case 9: case 24: case 23: case 22: case 21:
                sumx=-2;
                break;
            default:
                System.out.println("Fallo en la elección");
        }
        switch(cposicion){
            case 21: case 20: case 19: case 18: case 17:
                sumy=2;
                break;
            case 22: case 7: case 6: case 5: case 16:
                sumy=1;
                break;
            case 23: case 8: case 4: case 15:
                sumy=0;
                break;
            case 24: case 1: case 2: case 3: case 14:
                sumy=-1;
                break;
            case 9: case 10: case 11: case 12: case 13:
                sumy=-2;
                break;
            default:
                System.out.println("Fallo en la elección 2");
        }
        a[0]=sumx;
        a[1]=sumy;
        return a;
    }
    
    /**
     * Devuelve el jugador contrario al que se pasa como argumento. Si introducimos el primer jugador nos devolverá el
     * segundo y viceversa.
     * @param jugador Jugador que se quiere cambiar
     * @return Jugador contrario al pasado como argumento
     */
    public static int cambiarJugador(int jugador){
        if(jugador==GestorDeMovimientos.JUGADOR1){
            return GestorDeMovimientos.JUGADOR2;
        }
        else
            return GestorDeMovimientos.JUGADOR1;
    }
    
    /**
     * Cambia el jugador activo. Asigna a max el jugador que está activo y lo cambia por min.
     * @see Gui#devolverJugadorActivo()
     * @see #cambiarJugador(int)
     */
    public void actualizarDatos(){
        max=Gui.devolverJugadorActivo();
        min=cambiarJugador(max);
    }
    
    /**
     * Cambia el jugador max por el jugador min.
     */
    public void cambiarMaxMin(){
        int j=max;
        max=min;
        min=j;
    }
        
}
