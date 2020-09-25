package ataxx;

import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

/**
 * La clase <code>Gui</code> permitirá pintar la retícula del tablero y posicionar sobre ella fichas de colores en las 
 * distintas casillas. Deriva de la clase JFrame para que se comporte como un marco de aplicación.
 * 
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
public class Gui extends JFrame {

  /**
   * Panel sobre el que se pintará
   */
  private GuiTab panelTablero;
  /**
   * Indica si mueve o no el ordenador
   */
  private static boolean mueveCpu=false;
  /**
   * Se asigna el jugador activo al primer jugador
   */
  private static int jugadorActivo=1;
  /**
   * Etiqueta de la ventana: puntuación del jugador 1.
   */
  private JLabel puntuacion1;
  /**
   * Etiqueta de la ventana: puntuación del jugador 2.
   */
  private JLabel puntuacion2;
  /**
   * Etiqueta de la ventana: nombre del jugador 1.
   */
  private JLabel nombrej1;
  /**
   * Etiqueta de la ventana: nombre del jugador 1.
   */
  private JLabel nombrej2;
  
  /**
   * Variable para cambiar el usuario 1
   */
  private String nombreUsuario1;
  /**
   * Variable para cambiar el usuario 2
   */
  private String nombreUsuario2;
  
  /**
   * Icono que indica si la música está activada.
   */
  private JCheckBoxMenuItem musica;
  /**
   * Icono que indica si las marcas están activadas.
   */
  private static JCheckBoxMenuItem marcas;
  /**
   * Icono que indica si el sonido está activado.
   */
  private JCheckBoxMenuItem sonido;
  
  /**
   * Contenedor que almacena partidas. Los objetos que almacena pertenecen a la clase <code>SavedTab</code>, y en
   * ellos se guarda toda la información referente a una partida.
   */
  private TreeSet<SavedTab> partidasGuardadas=new TreeSet<SavedTab>();
  
  /**
   * Hilo que ejecuta los movimientos del ordenador.
   */
  private HiloCpu hilocpu;
  /**
   * Dificultad de la partida.
   */
  private int dificultad;
  /**
   * Indica si se debe interrumpir el hilo de la cpu o no.
   */
  private boolean interrumpirHilo=false;
  
  /**
   * Crea la ventana principal de la aplicación. Establece el color del fondo, crea la barra de menú y todos los 
   * submenús que esta contiene y añade el panel sobre el que se pintará el tablero de juego y las etiquetas para 
   * indicar la puntuación de cada jugador. Finalmente, se añaden los escuchadores, se organiza el estilo de la
   * ventana y se hace visible.
   * 
   * Para crear el panel que contiene al tablero, se llama al constructor de la clase <code>GuiTab<code> que deriva de
   * la clase <code>Jpanel</code>, y este es el que especifica el tamaño de la ventana, que depende del número de filas
   * y columnas que tendrá el tablero. El tamaño de la ventana no se podrá modificar una vez creada.
   * 
   * @param filas Número de filas del tablero
   * @param columnas Número de columnas del tablero
   * @see GuiTab#GuiTab(int, int)
   */
  public Gui(int filas, int columnas){
    // Se crea el panel donde se hara el pintado
    panelTablero=new GuiTab(filas,columnas);
    //Se establece un nivel de dificultad por defecto
    dificultad=2;

    //Se establece la localización de la ventana
    this.setLocation(300, 150);
    
    //Se crea la barra de menús superior con los submenús correspondientes
    JMenuBar mbar=new JMenuBar();
    setJMenuBar(mbar);
    
    JMenu archivo=new JMenu("Archivo");
    JMenuItem i1,i2,i3,i4,i5;
    archivo.add(i1=new JMenuItem("Nueva partida"));
    archivo.add(i2=new JMenuItem("Guardar partida"));
    archivo.add(i3=new JMenuItem("Cargar partida"));
    archivo.add(i4=new JMenuItem("Cambiar usuario"));
    archivo.addSeparator();
    archivo.add(i5=new JMenuItem("Salir"));
    mbar.add(archivo);
    
    JMenu pref=new JMenu("Preferencias");
    JMenuItem i6,i7;
    pref.add(i6=new JMenuItem("Estadísticas"));
    pref.add(i7=new JMenuItem("Nuevo Usuario"));
    mbar.add(pref);
    
    JMenu op=new JMenu("Opciones");
    op.add(musica=new JCheckBoxMenuItem("Música",false));
    op.add(sonido=new JCheckBoxMenuItem("Sonido",false));
    op.add(marcas=new JCheckBoxMenuItem("Marcas",true));
    pref.add(op);
    
    JMenu skins=new JMenu("Skins");
    mbar.add(skins);
    
    //Se crea la lista de Skins a partir de la enum Skin.
    SkinAction sa=new SkinAction(this);
    ButtonGroup groupSkins=new ButtonGroup();
    Skin[] matrizSkin=Skin.values();
    for(Skin s: matrizSkin){
        JRadioButtonMenuItem icono;
        if(s.toString().equals("Clasica")){
           icono=new JRadioButtonMenuItem(s.toString(),true);
        }else{
           icono=new JRadioButtonMenuItem(s.toString(),false);
        }
        groupSkins.add(icono);
        skins.add(icono);
        icono.addActionListener(sa);
        
    }

    
    JMenu ayuda=new JMenu("Ayuda");
    JMenuItem i8,i9;
    ayuda.add(i8=new JMenuItem("Manual"));
    ayuda.add(i9=new JMenuItem("Acerca de"));
    mbar.add(ayuda);
    
    //Se incluye un layout para organizar los elementos de la ventana
    this.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER,3,2));
    // Se agrega el tablero al marco
    getContentPane().add(panelTablero);
    
    //Se añaden las etiquetas de nombre y puntuaciones
    if(panelTablero.getPlayer1()==panelTablero.getDefaultPlayer())
        nombrej1=new JLabel("",SwingConstants.CENTER);
    getContentPane().add(nombrej1);
    
    puntuacion1=new JLabel(" ",SwingConstants.CENTER);
    getContentPane().add(puntuacion1);
    
    
    getContentPane().add(new JLabel("                ",SwingConstants.CENTER));
    
    if(panelTablero.getPlayer2()==panelTablero.getDefaultPlayer())
        nombrej2=new JLabel("",SwingConstants.CENTER);
    getContentPane().add(nombrej2);
    actualizarJugadores();
    
    puntuacion2=new JLabel(" ",SwingConstants.CENTER);
    getContentPane().add(puntuacion2);
    
    //Se crea el ActionListener y se conectan con él todos los botones del menú
    GuiAction A=new GuiAction(this);
    i1.addActionListener(A);
    i5.addActionListener(A);
    i4.addActionListener(A);
    i6.addActionListener(A);
    i7.addActionListener(A);
    i8.addActionListener(A);
    i9.addActionListener(A);
    marcas.addActionListener(A);
    sonido.addActionListener(A);
    
    //Se crea otro ActionListener que gestionará el guardado y cargado de partidas
    SaveFrame SF=new SaveFrame(this,false);
    i2.setActionCommand("Sí");
    i2.addActionListener(SF);
    i3.addActionListener(SF);
    
    //Se asignan las teclas de acceso rápido pertinentes
    archivo.setMnemonic(KeyEvent.VK_A);
    pref.setMnemonic(KeyEvent.VK_P);
    skins.setMnemonic(KeyEvent.VK_S);
    ayuda.setMnemonic(KeyEvent.VK_Y);
    i1.setMnemonic(KeyEvent.VK_N);
    i1.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_N, ActionEvent.CTRL_MASK));
    i2.setMnemonic(KeyEvent.VK_G);
    i2.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_G, ActionEvent.CTRL_MASK));
    i3.setMnemonic(KeyEvent.VK_C);
    i3.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_C, ActionEvent.CTRL_MASK));
    i4.setMnemonic(KeyEvent.VK_U);
    i4.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_U, ActionEvent.CTRL_MASK));
    i5.setMnemonic(KeyEvent.VK_S);
    i5.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    i6.setMnemonic(KeyEvent.VK_E);
    i6.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_E,ActionEvent.CTRL_MASK));
    i7.setMnemonic(KeyEvent.VK_N);
    i8.setMnemonic(KeyEvent.VK_M);
    i8.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_F1,ActionEvent.CTRL_MASK));
    i9.setMnemonic(KeyEvent.VK_A);
    
    //Se añade el WindowListener para guardar y cargar al abrir y cerrar el programa
    GuiWindow w=new GuiWindow(this);
    this.addWindowListener(w);
    
    // Se empaqueta todo
    pack();
    
    // Se hace visible
    setVisible(true);

    // Se evita el redimensionamiento
    setResizable(false);

    // Se hace que la aplicacion finalice al cerrar
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * Escribe el nombre de los jugadores que se enfrentan en la ventana principal. Cuando se seleccionan los jugadores 
   * que van a participar en la partida, el método escribe sus nombres en la parte inferior de la ventana junto a la
   * puntuación que tiene cada uno. Si no elegimos ningún jugador los nombres son por defecto Jugador 1 y Jugador 2. 
   * @see GuiTab#getPlayer1()
   * @see GuiTab#getPlayer2()
   * @see Player#getAlias() 
   */
  public void actualizarJugadores(){
      if(panelTablero.getPlayer1().getAlias().equals("Default")){
          nombrej1.setText("Jugador 1: ");
      }else
          nombrej1.setText(panelTablero.getPlayer1().getAlias()+": ");
      if(panelTablero.getPlayer2().getAlias().equals("Default")){
          nombrej2.setText("Jugador 2: ");
      }else
          nombrej2.setText(panelTablero.getPlayer2().getAlias()+": ");
  }
  
  
  /**
   * Devuelve el elemento de menú "musica".
   * @return Elemento de menú <code>musica</code>
   */
  public JCheckBoxMenuItem getMusica(){
      return musica;
  }
  /**
   * Devuelve el elemento de menú "marcas".
   * @return Elemento de menú <code>marcas</code>
   */
  public static JCheckBoxMenuItem getMarcas(){
      return marcas;
  }
  /**
   * Devuelve el elemento de menú "sonido".
   * @return Elemento de menú <code>sonido</code>
   */
  public JCheckBoxMenuItem getSonido(){
      return sonido;
  }

  /**
   * Devuelve el hilo que ejecuta los movimientos del ordenador.
   * @return Hilo que ejecuta los movimientos del ordenador
   */
  public HiloCpu getHiloCpu(){
      return hilocpu;
  }
  /**
   * Establece como hilo que controlará al ordenador el hilo pasado como argumento.
   * @param hilo Hilo que controlará al ordenador
   */
  public void setHiloCpu(HiloCpu hilo){
      hilocpu=hilo;
  }
  /**
   * Devuelve la dificultad de la partida.
   * @return Dificultad de la partida.
   */
  public int getDificultad(){
      return dificultad;
  }
  /**
   * Establece la dificultad de la partida.
   * @param i Dificultad de la partida
   */
  public void setDificultad(int i){
      dificultad=i;
      //Se establece la misma dificultad en el panel del tablero
      panelTablero.setDificultad(i);
  }
  /**
   * Devuelve la variable que indica la interrupción del hilo de la máquina
   * @return Varriable que controla la interrupción del hilo de la máquina
   */
  public boolean getInterrumpirHilo(){
      return interrumpirHilo;
  }
  /**
   * Establece la variable que indica la interrupción del hilo de la máquina
   * @param i Nuevo valor de la variable <code>interrumpirHilo</code>
   */
  public void setInterrumpirHilo(boolean i){
      interrumpirHilo=i;
  }
  
  /**
   * Devuelve una lista con todas las partidas guardadas.
   * @return Lista con las partidas guardadas
   */
  public TreeSet<SavedTab> getPartidasGuardadas(){
      return partidasGuardadas;
  }
  
  /**
   * Comprueba si ya existe una partida guardada con ese nombre. Cuando guardamos una partida debemos especificar un 
   * nombre; el método determina si en la lista de partidas guardadas ya existe una con ese nombre.
   * @param nombre Nombre de la partida
   * @return <code>true</code> si ya existe una partida con ese nombre o <code>false</code> si no existe.
   * @see SavedTab#getNombre()
   * @see String#equals(Object)
   */
  public boolean existeLaPartida(String nombre){
      boolean p=false;
      for(SavedTab s:partidasGuardadas){
          if(s.getNombre().equals(nombre)){
              p=true;
              break;
          }
      }
      return p;
  }
  
  /**
   * Busca una partida guardada. El método recorre la lista de partidas guardadas buscando la que tiene como nombre
   * el que se pasa como argumento. Si la encuentra, la devuelve, sino, devuelve una partida nula.
   * @param nombre Nombre de la partida a buscar
   * @return La partida que se buscaba o una partida nula si no se encuentra.
   * @see SavedTab#getNombre()
   * @see String#equals(Object)
   */
  public SavedTab buscarPartida(String nombre){
      //Partida que se devolverá en caso de no encontrarse la buscada
      SavedTab partida=new SavedTab("NoNo");
      for(SavedTab s:partidasGuardadas){
          if(s.getNombre().equals(nombre)){
              partida=s;
              break;
          }
      }
      return partida;
  }
  
  /**
   * Borra una partida guardada. A partir del nombre de una partida, se busca en la lista de partidas guardadas y si se
   * encuentra se borra. Se devuelve una variable tipo <code>boolean</code> indicando el resultado de la operación.
   * @param nombre Nombre de la partida que se borrará
   * @return <code>true</code> si se borra la partida o <code>false</code> si no se pudo encontrar.
   * @see SavedTab#getNombre()
   * @see String#equals(Object)
   */
  public boolean borrarPartida(String nombre){
      boolean p=false;
      //Se recorre la lista con un iterador
      Iterator<SavedTab> itr=partidasGuardadas.iterator();
      while(itr.hasNext()){
          SavedTab s=itr.next();
          if(s.getNombre().equals(nombre)){
              partidasGuardadas.remove(s);
              p=true;
              break;
          }
      }
      return p;
  }
  
 /**
  * Devuelve el jugador activo.
  * @return Jugador activo
  */
  public static int devolverJugadorActivo(){
      return jugadorActivo;
  }
  /**
   * Cambia el jugador activo.
   * @see Cpu#cambiarJugador(int)
   */
  public static void cambiarActivo(){
      jugadorActivo=Cpu.cambiarJugador(jugadorActivo);
  }
  /**
   * Asigna al jugador activo el jugador que pasamos como argumento.
   * @param j Jugador que pasará a ser el jugador activo.
   */
  public static void cambiarActivo(int j){
      jugadorActivo=j;
  }
  
  
  /**
   * Devuelve el nombre del primer usuario.
   * @return Nombre del primer usuario
   */
  public String getNombreJugador1(){
      return nombreUsuario1;
  }
  /**
   * Devuelve el nombre del segundo usuario.
   * @return Nombre del segundo usuario
   */
  public String getNombreJugador2(){
      return nombreUsuario2;
  }
  
  /**
   * Devuelve la variable que indica si mueve o no el ordenador.
   * @return <code>true</code>si mueve el ordenador o <code>false</code>si no mueve
   */
  public static boolean getMueveCpu(){
      return mueveCpu;
  }
  /**
   * Establece si mueve o no el ordenador.
   * @param f Indica si mueve o no el ordenador
   */
  public static void setMueveCpu(boolean f){
      mueveCpu=f;
  }
  
  /**
   * Modifica el nombre del primer jugador por el que se pasa como argumento.
   * @param s Nuevo nombre
   */
  public void modificarNombreJugador1(String s){
      nombreUsuario1=s;
  }
  /**
   * Modifica el nombre del segundo jugador por el que se pasa como argumento.
   * @param s Nuevo nombre
   */
  public void modificarNombreJugador2(String s){
      nombreUsuario2=s;
  }
  
  /**
   * Devuelve el objeto de la clase <code>GuiTab</code> que contiene al tablero de juego.
   * @return Objeto que contiene al tablero de juego
   */
  public GuiTab getGuiTab(){
      return panelTablero;
  }
  
  /**
   * Modifica las puntuaciones de los jugadores.
   * @param p1 Puntuación del primer jugador
   * @param p2 Puntuación del segundo jugador
   */
  public void modificarPuntuaciones(String p1,String p2){
      puntuacion1.setText(p1);
      puntuacion2.setText(p2);
      
      //Dependiendo de cual sea el jugador activo, se destaca su nombre en negrita.
      //Esto se realiza cambiando la fuente de letra de las etiquetas
      if(jugadorActivo==1){
          puntuacion1.setFont(new Font("SansSerif",Font.BOLD,13));
          nombrej1.setFont(new Font("SansSerif",Font.BOLD,13));
          nombrej2.setFont(new Font("SansSerif",Font.PLAIN,13));
          puntuacion2.setFont(new Font("SansSerif",Font.PLAIN,13));
          
      }else{
          puntuacion2.setFont(new Font("SansSerif",Font.BOLD,13));
          nombrej2.setFont(new Font("SansSerif",Font.BOLD,13));
          nombrej1.setFont(new Font("SansSerif",Font.PLAIN,13));
          puntuacion1.setFont(new Font("SansSerif",Font.PLAIN,13));
      }
  }
  
  /**
   * Método main. Se inicia toda la aplicación.
   */
  public static void main(String args[]){
      
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
           //Se crea la ventana
           Gui tablero=new Gui(7,7);
           //Se le asigna un tamaño y un título
           tablero.setSize(364,tablero.panelTablero.getDimensions()[1]+72);
           tablero.setTitle("Ataxx");
           //Se inician el hilo de enventos y el de música
           new HiloEventos(tablero);
           new HiloMusical(tablero);
        }
    });
  }
}

/**
 * La clase <code>HiloEventos</code> maneja en un hilo aparte la actualización de las puntuaciones de los jugadores.
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
class HiloEventos implements Runnable{
   /**
    * Hilo que se utilizará.
    */
    private Thread t;
    /**
     * Controla la ejecución infinita de las comprobaciones.
     */
    private boolean p=false;
    /**
     * Ventana del juego.
     */
    private Gui entorno;
    
    /**
     * Constructor de la clase. La ventana del juego se pasa como argumento y se inicia el hilo.
     * @param entorno Ventana del juego.
     */
    HiloEventos(Gui entorno){
        this.entorno=entorno;
        t=new Thread(this,"HiloEventos");
        t.start();
    }
    
    /**
     * Actualiza las puntuaciones de los jugadores. Mantiene el hilo en estado ejecutable y actualiza las puntuaciones
     * de los dos jugadores cuando se realiza algún movimiento de fichas.
     * @see Gui#modificarPuntuaciones(String, String)
     * @see Gui#getGuiTab()
     * @see GestorDeMovimientos#contarFichas(int) 
     */
    public void run(){
        try{
            //Las comprobaciones se realizan hasta que el programa se cierra
            while(!p){
                entorno.modificarPuntuaciones(String.valueOf(entorno.getGuiTab().
                        getGDM().contarFichas(1)),String.valueOf(entorno.getGuiTab().
                        getGDM().contarFichas(2)));
                //Se hace dormir el hilo un tiempo prudencial para que no se sature la memoria
                Thread.sleep(250);
            }
        }catch(InterruptedException e){
            //si se interrumpe el hilo se avisa por la salida estándar
            System.out.println("Hilo de enventos interrumpido");
        }
    }
}

/**
 * La clase <code>SkinAction</code> actualiza la apariencia del tablero cuando se modifica la skin desde el menú. La
 * clase implementa la interfaz <code>ActionListener</code>.
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
class SkinAction implements ActionListener{
    /**
     * Ventana de juego.
     */
    private Gui gui;
    
   /**
    * Constructor de la clase. Se pasa como único argumento la ventana de juego.
    * @param gui Ventana de juego
    */
    public SkinAction(Gui gui){
        this.gui=gui;
    }
    
    /**
     * Actualiza la apariencia del tablero. Modifica el tema del tablero cuando se cambia la skin.
     * @see Gui#getGuiTab()
     * @see GuiTab#modificarTema(String)
     */
    public void actionPerformed(ActionEvent ae){
        gui.getGuiTab().modificarTema(ae.getActionCommand());
    }
}


        
        
        
        
        