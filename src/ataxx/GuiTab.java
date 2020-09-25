package ataxx;
import java.util.*;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.io.*;

/**
 * La clase <code>GuiTab</code> deriva de la clase <code>Jpanel</code> y es la que crea el panel sobre el que se 
 * pintará el tablero de juego.
 *@version 1.0
 *@author Julio Navarro Lara
 *@author Juan Antonio Montesinos Delgado
 */
class GuiTab extends JPanel{
  /**
   * Constante para indicar el tamaño de la casilla (en puntos)
   */
  public final static int dimensionCasilla=50;
  /**
   * Número de filas del tablero.
   */
  private int filas;
  /**
   * Número de columnas del tablero.
   */
  private int columnas;

  /**
   * Array con las lineas de delimitación de las cuadrículas
   */
  private ArrayList<Line2D.Float> lineasDelimitadoras;

  /**
   * Escuchador de eventos para el panel del tablero
   */
  private GuiMouse gestorEventosRaton;
  
  /**
   * Tablero de juego.
   */
  private GestorDeMovimientos gdm;
  
  //Posición activa
  /**
   * Ficha de la posición activa.
   */
  private Ellipse2D.Float posicionActiva;
  /**
   * Coordenada x de la posición activa
   */
  private int pacx=-1;
  /**
   * Coordenada y de la posición activa
   */
  private int pacy=-1;
  
  /**
   * Puntuación del jugador 1
   */
  private int puntuacion1;
  /**
   * Puntuación del jugador 2
   */
  private int puntuacion2;
  
  /**
   * Control de la partida del ordenador.
   */
  private Cpu maquina;
  
  /**
   * Primer jugador.
   */
  private Player jugador1;
  /**
   * Segundo jugador.
   */
  private Player jugador2;
  /**
   * Jugador por defecto.
   */
  private static Player jugPorDef=new Player("Default");
  /**
   * Lista con todos los jugadores.
   */
  private static ArrayList<Player> listaJugadores=new ArrayList<Player>();
  
  /**
   * Tema del tablero. Se inicia a Clasica por defecto.
   */
  private Skin tema=Skin.valueOf("Clasica");
  
  /**
   * Indica si está guardada la partida.
   */
  private boolean estaGuardada=true;
  
  /**
   * Indica si se reproducen los sonidos.
   */
  private boolean sonido=false;

  /**
   * Dificultad de juego contra la máquina.
   * Esta variable está coordinada con la equivalente de la clase <code>Gui</code>.
   */
  private int dificultad;
  
  /**
   * Crea el tablero de juego en la interfaz gráfica. A partir del número de filas y columnas, se crean el panel sobre 
   * el que se pintará el tablero de juego y las líneas que delimitan las casillas. También se controlan los posibles 
   * eventos de ratón y se le indica al ordenador el tablero sobre el que debe jugar.
   * @param filas Número de filas del tablero
   * @param columnas Número de columnas del tablero
   * @see #crearLineasDelimitadoras()
   * @see GestorDeMovimientos#GestorDeMovimientos(int, int)
   * @see Cpu#Cpu(int, int)
   */
  public GuiTab(int filas, int columnas){
    this.filas=filas;
    this.columnas=columnas;

    //Ambos jugadores comienzan como jugadores por defecto
    jugador1=jugador2=jugPorDef;
    
    // Se crea el array de lineas
    lineasDelimitadoras=new ArrayList<Line2D.Float>();
    // Se fija la dimension
    Dimension dimension=new Dimension(columnas*dimensionCasilla,filas*dimensionCasilla);
    setPreferredSize(dimension);
    // Se crean las lineas delimitadoras
    crearLineasDelimitadoras();

    // Se crea el gestor de eventos
    gestorEventosRaton=new GuiMouse(this);
    // Se agrega el gestor al panel
    addMouseListener(gestorEventosRaton);
    
    //Se crea el tablero sobre el que se jugará
    gdm=new GestorDeMovimientos(7,7);
    //Se crea la máquina que jugará contra el usuario
    maquina=new Cpu(gdm);
  }
  
  /**
   * Modifica la posición activa. Situa la posición activa a la que pasamos como argumento.
   * @param x Fila de la nueva posición
   * @param y Columna de la nueva posición
   */
  public void modificarPosActiva(int x, int y){
      //Crea la ficha activa, que será más grande que las demás
      posicionActiva=new Ellipse2D.Float(x*dimensionCasilla+2,y*dimensionCasilla+2,
            dimensionCasilla-4,dimensionCasilla-4);
      pacx=x;
      pacy=y;
  }
  
  /**
   * Devuelve la posición activa.
   * @return <code>Array</code>con la coordenada x e y de la posición activa 
   */
  public int[] getPosActiva(){
      int[] m=new int[2];
      m[0]=pacx;
      m[1]=pacy;
      return m;
  }
  
  /**
   * Establece la dificultad del juego.
   * @param dificultad Dificultad del juego
   */
  public void setDificultad(int dificultad){
      this.dificultad=dificultad;
  }
  /**
   * Devuelve la dificultad del juego
   * @return Dificultad del juego
   */
  public int getDificultad(){
      return dificultad;
  }
  
  /**
   * Devuelve el objeto de la clase <code>Cpu</code> que controla el juego del ordenador.
   * @return Objeto de la clase <code>Cpu</code> que controla el juego del ordenador.
   */
  public Cpu getCpu(){
      return maquina;
  }
  
  /**
   * Devuelve el tablero de juego.
   * @return Tablero de juego.
   */
  public GestorDeMovimientos getGDM(){
      return gdm;
  }
  /**
   * Establece como tablero de juego el que pasamos como argumento.
   * @param mov Tablero de juego
   * @see GestorDeMovimientos#copiarTablero(Tablero)
   */
  public void setGDM(GestorDeMovimientos mov){
      gdm.copiarTablero(mov);
  }
  
  /**
   * Delvuelve el escuchador de eventos de ratón para el panel del tablero.
   * @return Escuchador de eventos de ratón del panel del tablero.
   */
  public GuiMouse getGestorRaton(){
      return gestorEventosRaton;
  }
  
  /**
   * Devuelve las dimensiones del tablero.
   * @return <code>Array</code> donde el primer elemento es el ancho del tablero y el segundo el alto.
   */
  public int[] getDimensions(){
      int[] m={columnas*dimensionCasilla,filas*dimensionCasilla};
      return m;
  }
  
  /**
   * Devuelve las puntuaciones de los jugadores.
   * @return <code>Array</code> donde el primer elemento es la puntuación del primer jugador y el segundo elemento, la 
   * del segundo jugador.
   */
  public int[] getPuntuaciones(){
      int[] m={puntuacion1,puntuacion2};
      return m;
  }
  
  /**
   * Devuelve la lista de jugadores.
   * @return <code>ArrayList</code> que contiene todos los jugadores.
   */
  public ArrayList<Player> getListaJugadores(){
      return listaJugadores;
  }
  
  /**
   * Busca un jugador por su alias. Recorre la lista de jugadores buscando al jugador que le corresponde el alias 
   * pasado como argumento al método. Si lo encuentra lo devuelve, si no, devuelve <code>null</code>.
   * @param alias Alias del jugador que se buscará
   * @return Jugador al que pertenece el alias introducido o <code>null</code> si no hay ningún jugador con ese alias
   * @see Player#getAlias()
   */
  public Player buscarAlias(String alias){
      Player jugador=null;
      for(Player p: listaJugadores){
          if(p.getAlias().equalsIgnoreCase(alias)){
              jugador=p;
              break;
          }
      }
      return jugador;
  }
  
  /**
   * Borra un jugador de la lista. El método recibe como argumento el alias del jugador que se borrará. Se devuelve una
   * variable tipo <code>boolean</code> indicando el resultado.
   * @param alias Alias del jugador que se borrará
   * @return <code>true</code> si se borró el jugador o <code>false</code> en caso contrario. 
   */
  public boolean borrarJugador(String alias){
      boolean p=false;
      //Se recorre la lista con un iterador
      Iterator<Player> itr=listaJugadores.iterator();
      while(itr.hasNext()){
          Player s=itr.next();
          if(s.getAlias().equals(alias)){
              listaJugadores.remove(s);
              p=true;
              break;
          }
      }
      return p;
  }
  
  /**
   * Devuelve la variable que indica si la partida está guardada.
   * @return <code>true</code> si está guardada o <code>false</code>si no lo está.
   */
  public boolean estaGuardada(){
      return estaGuardada;
  }
  /**
   * Establece la partida como guardada o no.
   * @param f Nuevo estado de la partida
   */
  public void setEstaGuardada(boolean f){
      estaGuardada=f;
  }
  
  /**
   * Comprueba si la aplicación tiene sonido.
   * @return <code>true</code> si tiene sonido o <code>false</code> si no tiene.
   */
  public boolean haySonido(){
      return sonido;
  }
  /**
   * Establece si la aplicación tiene sonido o no. 
   * @param b Nuevo estado del sonido
   */
  public void setSonido(boolean b){
      sonido=b;
  }
  
  /**
   * Modifica el tablero del objeto por el que se pasa como argumento.
   * @param gdm Nuevo tablero
   */
  public void modificarGDM(GestorDeMovimientos gdm){
      this.gdm=gdm;
  }
  
  /**
   * Devuelve el primer jugador
   * @return Primer jugador
   */
  public Player getPlayer1(){
      return jugador1;
  }
  /**
   * Devuelve el segundo jugador.
   * @return Segundo jugador
   */
  public Player getPlayer2(){
      return jugador2;
  }
  /**
   * Devuelve el jugador por defecto.
   * @return Jugador por defecto
   */
  public Player getDefaultPlayer(){
      return jugPorDef;
  }
  
  /**
   * Asigna como primer jugador al jugador cuyo alias pasamos como argumento.
   * @param alias Alias del jugador 
   * @see Player#getAlias()
   */
  public void modificarPlayer1(String alias){
      ListIterator itr=listaJugadores.listIterator();
      while(itr.hasNext()){
          Player p=(Player)itr.next();
          if(p.getAlias().equals(alias))
              jugador1=p;
      }
  }
  /**
   * Asigna como segundo jugador al jugador cuyo alias pasamos como argumento.
   * @param alias Alias del jugador
   * @see Player#getAlias()
   */
  public void modificarPlayer2(String alias){
      ListIterator itr=listaJugadores.listIterator();
      while(itr.hasNext()){
          Player p=(Player)itr.next();
          if(p.getAlias().equals(alias))
              jugador2=p;
      }
  }
  
  /**
   * Modifica el tema del tablero.
   * @param tema Nuevo tema
   * @see #paint(Graphics)
   */
  public void modificarTema(String tema){
      this.tema=Skin.valueOf(tema);
      repaint();
  }
  /**
   * Ordena la lista de jugadores según el alias.
   */
  public void ordenarListaJugadores(){
      Collections.sort(listaJugadores, new CompararJugadores());
  }
  
  /**
   * Pinta el tablero de juego. Según el tema que está activo, se pinta el tablero, las lineas delimitadoras y las 
   * fichas de cada jugador.
   * @param g Gráfico
   */
  public void paint(Graphics g){
    Graphics2D g2=(Graphics2D)g;
    
    //Se establece el color del jugador activo en ese momento
    Color coloractivo=Gui.devolverJugadorActivo()==1?tema.getColorJ1():tema.getColorJ2();
    
    //Se establece el color del fondo y se dibuja un rectángulo como fondo
    g2.setColor(tema.getColorFondo());
    g2.fillRect(0,0,columnas*dimensionCasilla,filas*dimensionCasilla);
    
    //En determinados casos especiales, en lugar de un fondo liso, se coloca una imagen
    if(tema.getColorFondo().equals(Color.ORANGE)&&tema.getColorLineas().equals(Color.ORANGE)){
        Image img=Toolkit.getDefaultToolkit().getImage("naranja.jpg");
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    }
    if(tema.getColorFondo().equals(Color.YELLOW)&&tema.getColorLineas().equals(Color.BLACK)){
        Image img=Toolkit.getDefaultToolkit().getImage("homer.jpg");
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    }
    
    //Se pintan las líneas con el color correspondiente
    g2.setColor(tema.getColorLineas());
    g2.drawRect(0,0,columnas*dimensionCasilla-1,filas*dimensionCasilla-1);
    for(Line2D.Float linea: lineasDelimitadoras){
      g2.draw(linea);
    }
    
    //Se recorre todo el tablero para pintar las fichas y las marcas que hay en él.
    for(int i=0;i<gdm.getTablero().length;i++){
        for(int j=0;j<gdm.getTablero()[i].length;j++){
            if(gdm.getTablero()[i][j]==1||gdm.getTablero()[i][j]==2){
            if(gdm.getTablero()[i][j]==1){
                g2.setColor(tema.getColorJ1());
            }
            else if(gdm.getTablero()[i][j]==2){
                g2.setColor(tema.getColorJ2());
            }
            g2.fill(new Ellipse2D.Float(i*dimensionCasilla+5,j*dimensionCasilla+5
                        ,dimensionCasilla-10,dimensionCasilla-10));
            }
            else if(gdm.getTablero()[i][j]==3&&Gui.getMarcas().getState()){
                g2.setColor(tema.getColorMarcas());
                g2.fill(new Ellipse2D.Float(i*dimensionCasilla+5,j*dimensionCasilla+5
                        ,dimensionCasilla-10,dimensionCasilla-10));
            }
        }
    }
    
    //Se dibuja la ficha activa de un tamaño menor
    if(posicionActiva!=null){
        g2.setColor(coloractivo);
        g2.fill(posicionActiva);
        
    }
    
    //Se actualizan las puntuaciones
    puntuacion1=gdm.contarFichas(1);
    puntuacion2=gdm.contarFichas(2);
    
  }

  
  /**
   * Crea las lineas que delimitan las casillas del tablero.
   */
  private void crearLineasDelimitadoras(){
    Line2D.Float linea;
    int xp,yp,xl,yl;

    // Se crean las lineas verticales: tantas como columnas +1
    for(int i=0; i < columnas+1; i++){
      // Posicion de partida: xp valdra tanto como i*dimensionCasilla
      //                      yp valdra siempre 0
      // Posicion de llegada: xl vale igual que xp
      //                      yl valdra siempre numeroFilas*dimensionCasilla 
      xp=i*dimensionCasilla;
      yp=0;
      xl=xp;
      yl=filas*dimensionCasilla;
      linea=new Line2D.Float(xp,yp,xl,yl);

      // Se agrega la linea al array de lineas
      lineasDelimitadoras.add(linea);
    }

    // Se crean las lineas horizontales: tantas como filas + 1
    for(int i=0; i < filas+1; i++){
      // Posicion de partida: xp valdra siempre 0
      //                      yp valdra i*dimensionCasilla
      // Posicion de llegada: xl valdra numeroFilas*dimensionCasilla
      //                      yl valdra igual que yp
      xp=0;
      yp=i*dimensionCasilla;
      xl=filas*dimensionCasilla;
      yl=yp;
      linea=new Line2D.Float(xp,yp,xl,yl);

      // Se agrega la linea al array de lineas
      lineasDelimitadoras.add(linea);
    }
  }

}


/**
 * La clase <code>CompararJugadores</code> simplemente permite comparar jugadores por su alias. Se implementa la 
 * interfaz <code>Comparator</code>.
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
class CompararJugadores implements Comparator<Player>{   
 /**
  * Compara dos jugadores según su alias.
  * @see String#compareTo(String)
  */
   public int compare(Player a, Player b){
       return a.getAlias().compareTo(b.getAlias());
   } 
}

