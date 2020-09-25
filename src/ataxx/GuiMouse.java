
package ataxx;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * La clase <code>GuiMouse</code> gestiona todos los clicks de ratón que se producen en el panel sobre el que se pinta
 * el tablero de juego.
 * @version 1.0
 * @author Julio Navarro Lara 
 * @author Juan Antonio Montesinos Delgado
 */
public class GuiMouse implements MouseListener,ActionListener{
  /**
   * Panel sobre el que se pinta el tablero.
   */
   private GuiTab panelControlado;
   /**
    * Ventana que indica el resultado de la partida.
    */
   private JFrame jf;
   /**
     * Determina si ha salido la ventana de resultado.
     */
   private boolean noHaSalido=true;
  
   /**
    * Etiqueta que indica el resultado de la partida.
    */
   public JLabel etiqueta;
   
  /**
   * Asigna al panel del objeto que se crea el panel que se pasa como argumento.
   */
  public GuiMouse(GuiTab panel){
    this.panelControlado=panel;
  }

  /**
   * Detecta los cliks del ratón sobre el tablero y realiza las acciones necesarias. Cuando se pincha con el ratón 
   * dentro del panel sobre el que se pinta el tablero, se detecta la casilla sobre la que se ha pinchado y esto 
   * permite que los jugadores pueden realizar movimientos con el ratón. 
   * 
   * Para que un jugador pueda realizar un movimiento primero se debe seleccionar una ficha suya del tablero, y esta 
   * pasa a ser la posición activa. En ese momento se indican las zonas a las que puede mover esa ficha. Con otro click
   * sobre una de estas casillas marcadas se realiza el movimiento. No se realiza ninguna acción en caso de que se 
   * pinche fuera del tablero, se seleccione una ficha del jugador contrario o no se seleccione ninguna ficha. En caso
   * de que se seleccione una ficha del jugador pero no se escoja una posición permitida para mover no se realizará el
   * movimiento.
   * 
   * Si el movimiento se ha realizado correctamente, se aumenta el número de movimientos del jugador, se eliminan las
   * zonas a las que podía mover y se cambia el jugador activo.
   * 
   * Por último, si ningún jugador puede realizar algún movimiento la partida ha terminado y se muestra una ventana con
   * el resultado de la misma. En la ventana se nos informa sobre qué jugador ha ganado o sobre si ha habido empate, 
   * además de mostrarse las puntuaciones de los jugadores.
   * 
   * En caso de que el jugador activo sea el ordenador no tendrán efecto los clicks sobre el tablero.
   * 
   * Cuando seleccionamos una ficha, hacemos un movimiento o finaliza la partida se reproducen los sonidos correspondientes
   * en caso de tener activado el icono sonidos.
   * @see GestorDeMovimientos#vaciarDe3()
   * @see GuiTab#modificarPosActiva(int, int)
   * @see GestorDeMovimientos#colocarPosicionesAMover(int, int, int, int)
   * @see Player#aumentarMovimientos()
   * @see GestorDeMovimientos#contarFichas(int)
   * 
   */
  public void mouseClicked(MouseEvent evento){
    //Se registran la posición activa y la posición en la que se hace click
    int pacx=panelControlado.getPosActiva()[0];
    int pacy=panelControlado.getPosActiva()[1];
    int posx=evento.getX();
    int posy=evento.getY();
    
    if(!(Gui.devolverJugadorActivo()==2&&panelControlado.getPlayer2().getAlias().equals("Cpu"))){
    //Si no juega la Cpu y estamos dentro de las dimensiones, se realizan las acciones correspondientes
    if(posx>=0&&posy>=0&&posx<=panelControlado.getDimensions()[0]&&posy<=
            panelControlado.getDimensions()[1]){
        int fila=posy/GuiTab.dimensionCasilla;
        int columna=posx/GuiTab.dimensionCasilla;
        //En primer lugar se activa la ficha correspondiente para mover.
        if(panelControlado.getGDM().getTablero()[columna][fila]==Gui.devolverJugadorActivo()){
            //Si los sonidos están activados, se reproduce el sonido pertinente.
            if(panelControlado.haySonido()){
                new HiloSonidos(1);
            }
            noHaSalido=true;
            //Se eliminan las marcas antiguas y se colocan las nuevas
            panelControlado.getGDM().vaciarDe3();
            panelControlado.modificarPosActiva(columna, fila);
            panelControlado.getGDM().colocarPosicionesAMover(columna, fila,0,3);
        }else
            //Se intenta hacer el movimiento correspondiente
        if(pacx>=0&&pacy>=0&&
             (panelControlado.getGDM().getTablero()[columna][fila]==0||panelControlado.getGDM().getTablero()[columna][fila]==3)
             &&panelControlado.getGDM().hacerMovimiento(Gui.devolverJugadorActivo(),pacx,pacy,columna,fila)){
            panelControlado.setEstaGuardada(false);
            //Se reproduce el sonido de mover ficha
            if(panelControlado.haySonido()){
                new HiloSonidos(2);
            }
            //Se aumenta el número de movimientos del usuario
            if(!panelControlado.getPlayer1().getAlias().equals("Default")){
                if(Gui.devolverJugadorActivo()==1){
                    panelControlado.getPlayer1().aumentarMovimientos();
                }else if(Gui.devolverJugadorActivo()==2){
                    panelControlado.getPlayer2().aumentarMovimientos();
                }
            }
            //Se resetea la posición activa y vaciamos de marcas el tablero
            panelControlado.modificarPosActiva(-1, -1);
            panelControlado.getGDM().vaciarDe3();
            //Cambiamos de jugador activo
            Gui.cambiarActivo();
            //Si jugamos contra la Cpu, la instamos a que mueva.
            if(panelControlado.getPlayer2().getAlias().equals("Cpu")){
                Gui.setMueveCpu(true);
            }
        }
        //Si el jugador ya no puede mover, lanzamos la ventana de fin de juego
        if(!panelControlado.getGDM().puedeMoverJugador(Gui.devolverJugadorActivo())&&noHaSalido){
            noHaSalido=false;
            
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
            
            String alias1=panelControlado.getPlayer1().getAlias();
            String alias2=panelControlado.getPlayer2().getAlias();
            int puntuacion1=panelControlado.getGDM().contarFichas(1);
            int puntuacion2=panelControlado.getGDM().contarFichas(2);
            
            //Mostramos las etiquetas con los mensajes pertinentes según el resultado de la partida
            //Además, modificamos el historial de cada jugador de la manera oportuna
            if(alias1.equals("Default")){
                alias1="Jugador 1";
            }
            if(alias2.equals("Default")){
                alias2="Jugador 2";
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
                    if(!alias2.equals("Cpu")){
                        panelControlado.getPlayer1().aumentarGanadas(0);
                    }else
                        panelControlado.getPlayer1().aumentarGanadas(
                                panelControlado.getDificultad());
                }
                if(!alias2.equals("Jugador 2")&&!alias2.equals("Cpu"))
                        panelControlado.getPlayer2().aumentarPerdidas(0);
            }else if(puntuacion2>puntuacion1){
                if(alias2.equals("Jugador 2")){
                    etiqueta.setText("     El jugador 2 ha ganado     ");
                }else if(alias2.equals("Cpu")){
                    etiqueta.setText("     El ordenador ha ganado     ");
                }else{
                    String texto=alias2+" ha ganado";
                    while(texto.length()<40){
                        texto=" "+texto+" ";
                    }
                    etiqueta.setText(texto);
                    panelControlado.getPlayer2().aumentarGanadas(0);
                }
                if(!alias1.equals("Jugador 1")&&alias2.equals("Cpu"))
                    panelControlado.getPlayer1().aumentarPerdidas(panelControlado.getDificultad());
                else
                    panelControlado.getPlayer1().aumentarPerdidas(0);
                
            }else{
                etiqueta.setText("     ¡Ha habido un empate!     ");
                if(!panelControlado.getPlayer1().getAlias().equals("Default")){
                    if(panelControlado.getPlayer2().getAlias().equals("Cpu")){
                        panelControlado.getPlayer1().aumentarEmpates(panelControlado.getDificultad());
                    }else
                        panelControlado.getPlayer1().aumentarEmpates(0);
                }
                if(!panelControlado.getPlayer2().getAlias().equals("Default")&&
                        !panelControlado.getPlayer2().getAlias().equals("Cpu")){
                    panelControlado.getPlayer2().aumentarEmpates(0);
                }
            }
            puntuacionj1.setText(alias1+":  "+puntuacion1);
            puntuacionj2.setText(alias2+":  "+puntuacion2);
            
            JButton aceptar;
            jf.add(aceptar=new JButton("Aceptar"));
            aceptar.addActionListener(this);
            aceptar.setMnemonic(KeyEvent.VK_A);
            
            jf.setVisible(true);
            
            //Se reproduce musiquilla de victoria
            if(panelControlado.haySonido()){
                new HiloSonidos(3);
            }
        }
        //Se repinta el panel para mostrar los cambios
        panelControlado.repaint();
    }
     }
    
  }

  /**
   * Detecta la entrada del ratón en el área del tablero, sin hacer nada más.
   * @param evento Evento del ratón
   */
  public void mouseEntered(MouseEvent evento){
  }

  /**
   * Detecta la salida del ratón en el área del tablero, sin hacer nada más.
   * @param evento Evento del ratón
   */
  public void mouseExited(MouseEvent evento){
  }

  /**
   * Detecta cuando se "baja" la tecla del ratón, sin hacer nada más.
   * @param evento Evento del ratón
   */
  public void mousePressed(MouseEvent evento){
  }

  /**
   * Detecta cuando se "sube" la tecla del ratón, sin hacer nada más.
   * @param evento Evento del ratón
   */
  public void mouseReleased(MouseEvent evento){
  }
  
  /**
   * Al pulsar el botón "Aceptar", cierra la ventana de resultado y establece la partida como guardada.
   */
  public void actionPerformed(ActionEvent ae){
      String str=ae.getActionCommand();
      if(str.equals("Aceptar")){
          jf.dispose();
          panelControlado.setEstaGuardada(true);
      }
  }
  
  /**
   * Devuelve si ha salido o no la ventana de resultado.
   * @return <code>true</code> si no ha salido o <code>false</code> si ha salido
   */
  public boolean getNoHaSalido(){
      return noHaSalido;
  }
  /**
   * Establece si ha salido o no la ventana de resultado.
   * @param b <code>true</code> si no ha salido o <code>false</code> si ha salido
   */
  public void setNoHaSalido(boolean b){
      noHaSalido=b;
  }
  
  
}
