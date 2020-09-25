
package ataxx;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

/**
 * La clase <code>HiloCpu</code> crea el hilo que controla la partida.
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
public class HiloCpu implements Runnable,ActionListener{
    
    /**
     * Hilo que controla la partida.
     */
    private Thread t;
    /**
     * Ventana de la aplicación.
     */
    private Gui gui;
    /**
     * Ventana que informa del resultado de la partida.
     */
    private JFrame jf;
    /**
     * Contador que controla la ejecución de los primero movimientos en nivel fácil
     */
    private int contador;
    
    /**
     * Crea e inicia el hilo que controla la partida.
     * @param gui Ventana de juego
     */
    public HiloCpu(Gui gui){
        this.gui=gui;
        contador=0;
        t=new Thread(this,"HiloCpu");
        t.start();
    }
    
    /**
     * Crea e inicia el hilo que controla la partida. Se introduce el contador como argumento
     * por si quieren eliminarse los movimientos preestablecidos en modo fácil.
     * @param gui Ventana de juego
     * @param contador Si es mayor que 1, los movimientos preestablecidos no se ejecutan
     */
    public HiloCpu(Gui gui, int contador){
        this.gui=gui;
        this.contador=contador;
        t=new Thread(this,"HiloCpu");
        t.start();
    }
    
    /**
     * Ejecuta el hilo que controla la partida. Realiza los movimientos del ordenador en la partida y actualiza el
     * tablero de juego en cada movimiento. 
     * Una vez que se ha llegado al final de la partida, se crea una ventana donde se informa sobre si ha ganado el 
     * usuario o el ordenador, o si ha habido empate y se reproduce el sonido de fin de la partida. La ventana contiene
     * además las puntuaciones finales que han obtenido los jugadores. 
     * @see Cpu#aplicarMinimax(GestorDeMovimientos, int)
     * @see Gui#cambiarActivo()
     * @see Player#aumentarGanadas()
     * @see Player#aumentarPerdidas()
     * @see Player#aumentarEmpates()
     */
    public void run(){
        //El bucle se ejecuta hasta que recibe la orden de interrupción desde la ventana principal
        d:while(!gui.getInterrumpirHilo()){
            //El cuerpo no empieza hasta que la máquina no reciba la orden de mover
            while(!Gui.getMueveCpu()||Gui.devolverJugadorActivo()==1){
                //Si se interrumpe, nos salimos
                if(gui.getInterrumpirHilo()){
                    break d;
                }
            }
            //Los dos primeros movimientos en nivel fácil están reprogramados para una mejor experiencia de juego
            if(contador<=1&&gui.getDificultad()==1){
                switch(contador){
                    case 0: gui.getGuiTab().getGDM().hacerMovimiento(2, 0, 6, 1, 6);
                    break;
                    case 1: gui.getGuiTab().getGDM().hacerMovimiento(2, 0, 6, 0, 5);
                    break;
                }
                
                Gui.cambiarActivo();
                gui.getGuiTab().repaint();
                contador++;
            }else{
            if(gui.getGuiTab().getGDM().puedeMoverJugador(2)){
                //Se aplica e método minimax, que decide el movimiento que realizará la máquina
                gui.getGuiTab().getCpu().aplicarMinimax(gui.getGuiTab().getGDM(),gui.getDificultad());
                gui.getGuiTab().setGDM(gui.getGuiTab().getCpu().getGDM());
                //Se cambia al jugador activo y se repinta el tablero
                Gui.cambiarActivo();
                gui.getGuiTab().repaint();
            }
            }
            
            
            //Se establece el control de fin de la partida que ya se incluyó en GuiMouse.
            //Se muestra la ventanita de victoria o derrota pertinente
            if(!gui.getGuiTab().getGDM().puedeMoverJugador(1)
                    &&gui.getGuiTab().getGestorRaton().getNoHaSalido()){
                gui.getGuiTab().getGestorRaton().setNoHaSalido(false);
            
                jf=new JFrame();
                jf.setLocation(375, 300);
                jf.setSize(210,105);
            
                jf.setLayout(new FlowLayout());
                jf.setResizable(false);
            
                JLabel etiqueta;
                jf.add(etiqueta=new JLabel());
            
                JLabel puntuacionj1;
                JLabel puntuacionj2;
                jf.add(puntuacionj1=new JLabel());
                jf.add(new JLabel("       "));
                jf.add(puntuacionj2=new JLabel());
            
                String alias1=gui.getGuiTab().getPlayer1().getAlias();
                String alias2=gui.getGuiTab().getPlayer2().getAlias();
                int puntuacion1=gui.getGuiTab().getGDM().contarFichas(1);
                int puntuacion2=gui.getGuiTab().getGDM().contarFichas(2);
                
                if(alias1.equals("Default")){
                    alias1="Jugador 1";
                }
                if(puntuacion1>puntuacion2){
                    if(alias1.equals("Jugador 1")){
                        etiqueta.setText("     El jugador 1 ha ganado     ");
                    }else{
                        String texto=alias1+" ha ganado";
                        while(texto.length()<40){
                            texto=" "+texto+" ";
                        }
                        etiqueta.setText(texto);
                        gui.getGuiTab().getPlayer1().aumentarGanadas(gui.getDificultad());
                    }
                }else if(puntuacion2>puntuacion1){
                        etiqueta.setText("     El ordenador ha ganado     ");
                    if(!alias1.equals("Jugador 1"))
                        gui.getGuiTab().getPlayer1().aumentarPerdidas(gui.getDificultad());
                
                    }else{
                        etiqueta.setText("     ¡Ha habido un empate!     ");
                        if(!gui.getGuiTab().getPlayer1().getAlias().equals("Default")){
                            gui.getGuiTab().getPlayer1().aumentarEmpates(gui.getDificultad());
                    }
                }
                puntuacionj1.setText(alias1+":  "+puntuacion1);
                puntuacionj2.setText(alias2+":  "+puntuacion2);
            
                JButton aceptar;
                jf.add(aceptar=new JButton("Aceptar"));
                aceptar.addActionListener(this);
                aceptar.setMnemonic(KeyEvent.VK_A);
            
                jf.setVisible(true);
                if(gui.getGuiTab().haySonido()){
                    new HiloSonidos(3);
                }
                
                gui.setInterrumpirHilo(true);
            
              }
              Gui.setMueveCpu(false);
               
        }
    }
    
    /**
     * Cierra la ventada de resultado cuando se pulsa el botón "Aceptar". Además, establece la partida como guardada.
     * @see GuiTab#estaGuardada()
     */
    public void actionPerformed(ActionEvent ae){
      String str=ae.getActionCommand();
      if(str.equals("Aceptar")){
          jf.dispose();
          gui.getGuiTab().setEstaGuardada(true);
      }
    }
}
