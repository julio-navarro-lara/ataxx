

package ataxx;
import java.io.*;
/**
 * La clase <code>GestorDeMovimiento</code> es una clase derivada de la clase <code>Tablero</code>, que además
 * incorpora los métodos necesarios para poder realizar y controlar los movimentos de fichas sobre el tablero.
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
public class GestorDeMovimientos extends Tablero implements Serializable{

   /**
    * Permite crear un tablero a partir de la introducción del número de filas y de columnas. Este tablero ofrece la
    * posibilidad de poder realizar movimientos de fichas, ya que los métodos asociados permiten este tipo de 
    * acciones. Para crear el tablero se llama al constructor de la clase <code>Tablero</code>.
    * @param a Número de filas
    * @param b Número de columnas
    * @see Tablero#Tablero(int, int)
    */
    public GestorDeMovimientos(int a, int b){
        //Se llama al constructor de la clase "madre"
        super(a,b);
    }
    
    /**
     * Determina si es posible realizar algún movimiento con una determinada ficha. La posición de la ficha que
     * se quiere comprobar si es posible mover, se pasa como argumento, y el método se encarga de buscar alguna
     * posición a la que está permitido mover y que está libre. Se devuelve una variable de tipo<code>boolean</code>
     * indicando si es posible o no el movimiento de la ficha.
     * @param x Fila de la ficha 
     * @param y Columna de la ficha  
     * @return <code>true</code> si es posible realizar algún movimiento con la ficha o <code>false</code> en caso 
     * contrario.
     * @see #noSeSale(int, int)
     */
    public boolean puedeMover(int x, int y){
        boolean puede=false;
        //Se recorren aquellas posiciones a las que la ficha podría mover
        for(int i=-2; i<=2; i++){
            for(int j=-2; j<=2; j++){
                if(noSeSale(x,y,i,j)){
                    //Si alguna de esas posiciones es 0 o 3, entonces puede mover
                    if(t[i+x][j+y]==0||t[i+x][j+y]==3){
                        puede=true;
                        break;
                    }
                }
            }
        }
        return puede;
    }
    
    /**
     * Determina el número de casillas a las que puede mover una ficha. A partir de la posición de la ficha, se 
     * cuenta el número de casillas del tablero a las que le está permitido mover y que además están libres. El
     * resultado del recuento se almacema en una variable de tipo entero que devuelve el método.
     * @param x Fila de la ficha
     * @param y Columna de la ficha
     * @return Número de casillas a las que puede mover la ficha.
     * @see #noSeSale(int, int)
     */
    public int lugaresParaMover(int x, int y){
        int contador=0;
        //Se recorren las casillas a las que se podría mover y, si están accesibles, se incrementa el contador
        for(int i=-2; i<=2; i++){
            for(int j=-2; j<=2; j++){
                if(noSeSale(x,y,i,j)){
                    if(t[i+x][j+y]==0||t[i+x][j+y]==3){
                        contador++;
                    }
                }
            }
        }
        return contador;
    }
     
   /**
    * Coloca el valor de <code>b</code> en las casillas a las que se puede mover una ficha y que contengan 
    * el valor de <code>a</code>. La posición de la ficha se pasa como argumento.
    * @param x Fila de la ficha
    * @param y Columna de la ficha
    * @param a Valor a sustituir 
    * @param b Valor a asignar
    */
    public void colocarPosicionesAMover(int x, int y, int a, int b){
        for(int i=-2; i<=2; i++){
            for(int j=-2; j<=2; j++){
                if(noSeSale(x,y,i,j)){
                    if(t[i+x][j+y]==a){
                        t[i+x][j+y]=b;
                    }
                }
            }
        }
    }
    
    /**
     * Convierte en casillas libres las casillas ocupadas por los indicadores de movimiento. En el <code>array</code> 
     * que representa al tablero, las zonas a las que puede mover una ficha se simbolizan con el número 3. Como de 
     * estas casillas la ficha sólo puede ocupar una, es necesario liberar las demás después de hacer el movimiento o 
     * liberarlas todas si cambiamos de ficha. Por tanto, aplicado a un tablero, el método elimina estas zonas de 
     * indicación.
     */
    public void vaciarDe3(){
        for(int i=0; i<t.length; i++){
            for(int j=0; j<t[i].length; j++){
                   if(t[i][j]==3){
                        t[i][j]=0;
                    }
            }
        }
    }
    
    /**
     * Comprueba que la casilla a la que se quiere mover una ficha está dentro del tablero. El método recibe como 
     * argumento la posición inicial de la ficha y la posición a la que se quiere mover y determina si esta posición 
     * se encuentra dentro de los límites del tablero. Se devuelve una variable de tipo<code>boolean</code> indicando
     * la posibilidad o no del movimiento.
     * @param x Fila donde se encuentra la ficha
     * @param y Columna donde se encuentra la ficha
     * @param i Fila a la cual se quiere mover la ficha
     * @param j Columna a la cual se quiere mover la ficha
     * @return <code>true</code>si la casilla a la que se quiere mover pertenece al tablero o <code>false</code> si no
     * pertenece.
     */
    public boolean noSeSale(int x, int y, int i, int j){
        boolean puede;
        //si no se sale de los límites del tablero, puede mover
        if(y+j<t[0].length&&x+i<t.length&&y+j>=0&&x+i>=0)
            puede=true;
        else
            puede=false;
        return puede;
    }
    
    /**
     * Comprueba si una casilla está dentro del tablero. A partir de la posición de la casilla se devuelve una
     * variable de tipo <code>boolean</code> que indica si ésta pertenece o no al tablero.
     * @param x Fila de la casilla
     * @param y Columna de la casilla
     * @return <code>true</code> si la casilla pertenece al tablero o <code>false</code> si no pertenece.
     */
    public boolean noSeSale(int x, int y){
        boolean puede=false;
        if(y<t[0].length&&x<t.length&&y>=0&&x>=0)
            puede=true;
        else
            puede=false;
        return puede;
    }
    
    /**
     * Permite mover una ficha. Para ello se introduce el jugador al que pertenece la ficha, la posición inicial y la
     * posición a la que se pretente mover. El método determina si el movimiento que se quiere realizar está
     * permitido, en cuyo caso hay dos posibilidades, que la ficha se copie o que salte. 
     * Si la casilla a la que se quiere mover no es contigua a la original la ficha salta, y el método retira la ficha
     * de su posición original, la coloca en la posición elegida y llama al método que comprueba si es posible comer 
     * alguna ficha del jugador contrario desde la nueva ubicación. 
     * Si la casilla a la que se quiere mover es contigua a la original la ficha se copia, y el método coloca una 
     * nueva ficha en la posición elegida y llama al método que comprueba si es posible comer alguna ficha del jugador
     * contrario desde la nueva ubicación.
     * En caso de que el movimiento no esté permitido, el método no realiza ninguna acción sobre las fichas del 
     * tablero. 
     * Finalmente, se devuelve una variable de tipo <code>boolean</code> indicando si se ha realizado o no el 
     * movimiento.
     * @param jugador Jugador al que pertenece la ficha.
     * @param xi Fila original de la ficha
     * @param yi Columna original de la ficha
     * @param xf Fila destino de la ficha 
     * @param yf Columna destino de la ficha
     * @return <code>true</code> si el movimiento se ha realizado correctamente o <code>false</code> si no se ha
     * podido realizar el movimiento.
     * @see #noSeSale(int, int)
     * @see #colocarFicha(int, int, int)
     * @see #comerFichas(int, int, int)
     */
    public boolean hacerMovimiento(int jugador,int xi,int yi,int xf,int yf){
        boolean puede=false;
        //Si se cumplen las condiciones de salto, la ficha salta a la posición indicada
        if(((Math.abs(xf-xi)==2&&Math.abs(yf-yi)==2)||(Math.abs(xf-xi)==2&&Math.abs(yf-yi)==1)
                ||(Math.abs(xf-xi)==1&&Math.abs(yf-yi)==2)||(Math.abs(xf-xi)==2&&Math.abs(yf-yi)==0)
                ||(Math.abs(xf-xi)==0&&Math.abs(yf-yi)==2))
                &&noSeSale(xf,yf)&&noSeSale(xi,yi)&&(t[xf][yf]==0||t[xf][yf]==3)){
            this.colocarFicha(jugador, xf, yf);
            this.colocarFicha(0,xi,yi);
            puede=true;
            comerFichas(jugador,xf,yf);
            
        }
        //Si se cumplen las de copia, la ficha se copia a una posición contigua
        else{
            if(((Math.abs(xf-xi)==1&&Math.abs(yf-yi)==1)||(Math.abs(xf-xi)==0&&Math.abs(yf-yi)==1)
                ||(Math.abs(xf-xi)==1&&Math.abs(yf-yi)==0))
                &&noSeSale(xf,yf)&&noSeSale(xi,yi)&&(t[xf][yf]==0||t[xf][yf]==3)){
                this.colocarFicha(jugador, xf, yf);
                puede=true;
                comerFichas(jugador,xf,yf);
            
            }
        }
        return puede;
    }
    
    /**
     * Sustituye fichas del jugador contrario por las del jugador pasado como argumento. Se comprueba si la ficha
     * del jugador pasado como argumuento tiene fichas contiguas del jugador contrario, en cuyo caso se convierten en 
     * fichas del jugador indicado en el argumento.
     * @param jugador Jugador al que pertenece la ficha
     * @param x Fila de la ficha
     * @param y Columna de la ficha
     * @see #noSeSale(int, int, int, int)
     * @see #colocarFicha(int, int, int)
     */
    public void comerFichas(int jugador, int x, int y){
        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                if(noSeSale(x,y,i,j)&&t[x+i][y+j]!=0&&t[x+i][y+j]!=jugador
                        &&t[x+i][y+j]!=3){
                    this.colocarFicha(jugador, x+i, y+j);
                }
            }
        }
    }
    
    /**
     * Comprueba si un jugador puede mover alguna de sus fichas. Se recorren todas las fichas del jugador pasado como
     * argumento, comprobando en cada una de ellas si puede realizar algún movimiento. Se devuelve una variable de
     * tipo <code> boolean</code> que indica si alguna de la las fichas del jugador puede mover.
     * @param jugador Jugador del que se determinará si puede mover alguna ficha
     * @return <code>true</code> si puede mover alguna ficha o <code>false</code> si no puede mover ninguna ficha.
     * @see #puedeMover(int, int)
     */
    public boolean puedeMoverJugador(int jugador){
        boolean puede=false;
        for(int i=0; i<t.length; i++){
            for(int j=0; j<t[i].length; j++){
                if(t[i][j]==jugador){
                    boolean podra=puedeMover(i,j);
                    //Si una de las fichas puede mover, se devuelve true y se sale
                    if(podra){
                        puede=true;
                        break;
                    }
                }
            }
        }
        return puede;
    }
    
    /**
     * Imprime la representación del tablero por la salida estándar.
     *
     */
   public void imprimirTablero(){
		System.out.print(" ");
                //Imprime los bordes superiores del tablero
		for(int i=0; i<=t.length;i++){
			System.out.print("_ ");
		}
		System.out.println();
		for(int i=0; i<t[0].length; i++){
			System.out.print("| ");
			for(int j=0; j<t.length; j++){
					System.out.print(t[j][i]+" ");
			}
			System.out.println("|");
		}
		System.out.print("  ");
                //Imprime los bordes inferiores
		for(int i=0; i<t.length;i++){
			System.out.print("--");
		}
		System.out.println();
    }    
    
}
