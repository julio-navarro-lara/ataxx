
package ataxx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;

/**
 * La clase <code>SaveFrame</code> gestiona la interfaz gráfica para poder guardar y cargar 
 * partidas.
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
public class SaveFrame implements ActionListener,ListSelectionListener {

    /**
     * Ventana de la aplicación.
     */
    private Gui gui;
    /**
     * Determina si aparece la ventana de guardar partida.
     */
    private boolean aparicionVentana;
    /**
     * Determina si estamos en el modo cargar partida.
     */
    private boolean estaCargando;
    /**
     * Ventana para decidir si se guarda o no la partida.
     */
    private JDialog ventana;
    /**
     * Ventana para guardar la partida.
     */
    private JDialog guardado;
    /**
     * Etiqueta de información para el usuario.
     */
    private JLabel datosPartida;
    /**
     * Campo de texto donde se escribe el nombre de la partida.
     */
    private JTextField nombreGuardado;
    /**
     * Lista con las partidas guardadas.
     */
    private JList lista;
    /**
     * Ventana para cargar la partida.
     */
    private JDialog cargar;
    /**
     * Botón para cargar partida.
     */
    private JButton cargarb;
    /**
     * Botón para borrar partida.
     */
    private JButton borrar;
    /**
     * Ventana de petición de contraseÃ±a para el primer jugador.
     */
    private JDialog up1;
    /**
     * Ventana de petición de contraseÃ±a para el segundo jugador.
     */
    private JDialog up2;
    /**
     * Campo de contraseña para el primer jugador.
     */
    private JPasswordField pass1;
    /**
     * Campo de contraseña para el segundo jugador.
     */
    private JPasswordField pass2;
    /**
     * Etiqueta de información para el primer jugador.
     */
    private JLabel infoup1;
    /**
     * Etiqueta de información para el segundo jugador.
     */
    private JLabel infoup2;
    /**
     * Determina si debe cerrarse la ventana de contraseña del segundo jugador.
     */
    private boolean cierraup2=false;
    /**
     * Determina si el resgistro es correcto.
     */
    private boolean registroCorrecto=false;
    /**
     * Alias del primer jugador.
     */
    private String alias1;
    /**
     * Alias del segundo jugador.
     */
    private String alias2;
    
    /**
     * Crea una ventana que permite al usuario decidir si quiere guardar la partida.
     * @param gui Ventana del juego
     * @param k Aparece o no la nueva ventana
     */
    public SaveFrame(Gui gui,boolean k){
        this.gui=gui;
        this.aparicionVentana=k;
        //Si se indica se crea la ventana correspondiente que pregunta si se quiere guardar la partida.
        if(k){
        ventana=new JDialog(gui,true);
        ventana.setLocationRelativeTo(gui);
        ventana.setResizable(false);
        ventana.setSize(200,90);
        
        ventana.setLayout(new FlowLayout(FlowLayout.CENTER));
        ventana.add(new JLabel("¿Quiere guardar la partida?"));
        JButton si;
        JButton no;
        ventana.add(si=new JButton("Sí"));
        ventana.add(no=new JButton("No"));
        si.setMnemonic(KeyEvent.VK_S);
        no.setMnemonic(KeyEvent.VK_N);
        si.addActionListener(this);
        no.addActionListener(this);
        
        ventana.setVisible(true);
        }
        
    }
    
    /**
     * Permite guardar, cargar y borrar partidas. 
     * 
     * Determina el evento que se ha producido en la ventana en que se decide sobre
     * si se guarda la partida y realizan las siguientes acciones:
     * 
     * Si se ha decidido guardar la partida, se crea la ventana en la que se introducen los datos necesarios, se crea
     * la partida guardada y se almacena en el contenerdor de partidas guardadas. Antes de almacenar la partida se
     * comprueba que la partida tenga algún nombre, que el nombre no sobrepase los 10 caracteres y que no exista ninguna
     * partida con ese nombre.
     * Si se ha decido no guardar la partida se cierra la ventana.
     * 
     * Por otro lado, tambiÃ©n permite cargar partidas que seleccionamos de una lista. Cuando elegimos una partida para
     * cargarla es necesario introducir la contraseña de ambos jugadores, si se introduce una contraseña incorrecta se
     * informa de ello en la ventana de contraseña. Por último, si el registro ha sido correcto se recupera la partida
     * del contenedor donde se almacena y se actualiza la ventana de juego para continuar con la partida cargada. 
     * 
     * También es posible borrar las partidas guardadas que se muestran en la lista.
     * @see Gui#getPartidasGuardadas()
     * @see Gui#borrarPartida(String)
     * @see GestorDeMovimientos#copiarTablero(Tablero)
     */
    public void actionPerformed(ActionEvent ae){
        String str=ae.getActionCommand();
        //Si se elige que se quiere guardar la partida, se abre la ventana de guardado
        if(str.equals("Sí")){
            estaCargando=false;
            guardado=new JDialog(gui,true);
            guardado.setLocationRelativeTo(gui);
            if(aparicionVentana)
                ventana.dispose();
            guardado.setResizable(false);
            guardado.setSize(300,193);
            guardado.setTitle("Guardar Partida");
            guardado.setLayout(new FlowLayout(FlowLayout.CENTER));
            
            //Se muestran las partidas ya guardadas en una lista
            int longitud=gui.getPartidasGuardadas().size();
            String[] nombres=new String[longitud];
            Iterator<SavedTab> itr=gui.getPartidasGuardadas().iterator();
            for(int i=0; i<gui.getPartidasGuardadas().size(); i++){
                nombres[i]=itr.next().getNombre();
            }
            lista=new JList(nombres);
            lista.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            JScrollPane listScroller = new JScrollPane(lista);
            listScroller.setPreferredSize(new Dimension(250, 80));
            guardado.add(listScroller);
            lista.addListSelectionListener(this);
            
            guardado.add(datosPartida=new JLabel("                                   "));
            
            guardado.add(nombreGuardado=new JTextField(20));
            nombreGuardado.addActionListener(this);
            
            
            JButton guardar;
            JButton cancelar;
            guardado.add(guardar=new JButton("Guardar"));
            guardado.add(cancelar=new JButton("Cancelar"));
            guardar.addActionListener(this);
            cancelar.addActionListener(this);
            guardar.setMnemonic(KeyEvent.VK_G);
            cancelar.setMnemonic(KeyEvent.VK_C);
            
            guardado.setVisible(true);
        }
        if(str.equals("No")){
            ventana.dispose();
        }
        if((str.equals("Guardar"))||(ae.getSource()==nombreGuardado)){
            //Se hacen todas las comprobaciones necesarias para cerciorarse de que el nombre introducido es correcto
            int contador=0;
            for(int i=0;i<nombreGuardado.getText().length();i++){
              char ch=nombreGuardado.getText().charAt(i);
              if(Character.isLetterOrDigit(ch))
                  contador++;
            }
            
            if(nombreGuardado.getText().length()<=10&&contador!=0&&
                  !gui.existeLaPartida(nombreGuardado.getText())){
                //Si es correcto se vacía el tablero de marcas y se guarda en la lista de partidas guardadas
                //Se debe guardar también el turno activo en ese momento
                GestorDeMovimientos gdm=new GestorDeMovimientos(7,7);
                gdm.copiarTablero(gui.getGuiTab().getGDM());
                gdm.vaciarDe3();
                SavedTab tab=new SavedTab(gdm,gui.getGuiTab().getPlayer1(),gui.getGuiTab().getPlayer2()
                        ,nombreGuardado.getText(),new GregorianCalendar(),Gui.devolverJugadorActivo());
                gui.getPartidasGuardadas().add(tab);
                gui.getGuiTab().setEstaGuardada(true);
                guardado.dispose();
                
            }else if(contador==0){
                datosPartida.setText("Debe darle un nombre a la partida guardada");
            }else if(nombreGuardado.getText().length()>10){
                datosPartida.setText("El nombre debe tener menos de 10 caracteres");
            }else if(gui.existeLaPartida(nombreGuardado.getText())){
                datosPartida.setText("Ya existe una partida con ese mismo nombre");
            }
            
        }
        if(str.equals("Cancelar")){
            guardado.dispose();
        }
        
        if(str.equals("Cargar partida")){
            //Para cargar se abre una ventana similar a la de guardar
            estaCargando=true;
            if(!gui.getGuiTab().estaGuardada())
                 new SaveFrame(gui,true);
            cargar=new JDialog(gui,true);
            cargar.setLocationRelativeTo(gui);
            cargar.setResizable(false);
            cargar.setSize(270,193);
            cargar.setTitle("Cargar Partida");
            cargar.setLayout(new FlowLayout(FlowLayout.CENTER));
            
            int longitud=gui.getPartidasGuardadas().size();
            String[] nombres=new String[longitud];
            Iterator<SavedTab> itr=gui.getPartidasGuardadas().iterator();
            for(int i=0; i<longitud; i++){
                nombres[i]=itr.next().getNombre();
            }
            lista=new JList(nombres);
            lista.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            JScrollPane listScroller = new JScrollPane(lista);
            listScroller.setPreferredSize(new Dimension(250, 80));
            cargar.add(listScroller);
            lista.addListSelectionListener(this);
            
            cargar.add(datosPartida=new JLabel("                                          "+
                    "                              "));
            
            
            JButton cancelar;
            cargar.add(cargarb=new JButton("Cargar"));
            cargar.add(borrar=new JButton("Borrar"));
            cargar.add(cancelar=new JButton("Cancelar"));
            cancelar.setActionCommand("Cancelar cargar");
            cargarb.addActionListener(this);
            cancelar.addActionListener(this);
            borrar.addActionListener(this);
            cargarb.setEnabled(false);
            borrar.setEnabled(false);
            
            cargarb.setMnemonic(KeyEvent.VK_G);
            borrar.setMnemonic(KeyEvent.VK_B);
            cancelar.setMnemonic(KeyEvent.VK_C);
            
            cargar.setVisible(true);
        }
        if(str.equals("Cargar")){
            //Para cargar la partida se piden todas las contraseñas necesarias
            SavedTab partida=gui.buscarPartida((String)lista.getSelectedValue());
            alias1=partida.getJugador1().getAlias();
            alias2=partida.getJugador2().getAlias();
            if(!alias1.equals("Default")&&!alias1.equals(gui.getGuiTab().getPlayer1().getAlias())){
                 up1=new JDialog(cargar,"Jugador 1- "+alias1,true);
                 up1.setLocationRelativeTo(gui);
                 up1.setSize(240,130);
                 up1.setResizable(false);
                 up1.setLayout(new FlowLayout(FlowLayout.CENTER));
                 up1.add(new JLabel("Introduzca la contraseña de "+alias1));
                 pass1=new JPasswordField(15);
                 up1.add(pass1);
                 pass1.addActionListener(this);
                 
                 
                 JButton ok=new JButton("Aceptar");
                 ok.setActionCommand("Aceptarup1");
                 up1.add(ok);
                 ok.addActionListener(this);
                 
                 JButton no=new JButton("Cancelar");
                 no.setActionCommand("Cancelarup1");
                 up1.add(no);
                 no.addActionListener(this);
                 
                 infoup1=new JLabel("");
                 up1.add(infoup1);
                 
                 up1.setVisible(true);
             }
             if(!cierraup2){
                if(!alias2.equals("Cpu")&&!alias2.equals("Default")&&!alias2.equals(gui.getGuiTab().getPlayer2())){
                  up2=new JDialog(cargar,"Jugador 2- "+alias2,true);
                  up2.setLocationRelativeTo(gui);
                  up2.setSize(240,130);
                  up2.setResizable(false);
                  up2.setLayout(new FlowLayout(FlowLayout.CENTER));
                  up2.add(new JLabel("Introduzca la contraseña de "+alias2));
                  pass2=new JPasswordField(15);
                  up2.add(pass2);
                  pass2.addActionListener(this);
                 
                 
                  JButton ok=new JButton("Aceptar");
                  ok.setActionCommand("Aceptarup2");
                  up2.add(ok);
                  ok.addActionListener(this);
                 
                  JButton no=new JButton("Cancelar");
                  no.setActionCommand("Cancelarup2");
                  up2.add(no);
                  no.addActionListener(this);
                 
                  infoup2=new JLabel("");
                  up2.add(infoup2);
                 
                  up2.setVisible(true);
                }
                else{
                    registroCorrecto=true;
                }
             }else{
                 cierraup2=false;
             }
            if(registroCorrecto){
                //Si se registra correctamente se carga la partida
                //Para ello se modifica el tablero, los jugadores y el turno actual
                 gui.getGuiTab().modificarGDM(partida.getGestor());
                 gui.getGuiTab().modificarPlayer1(alias1);
                 gui.getGuiTab().modificarPlayer2(alias2);
                 gui.setInterrumpirHilo(true);
                 gui.setHiloCpu(null);
                 gui.cambiarActivo(partida.getTurno());
                 gui.getGuiTab().modificarPosActiva(-1, -1);
                 gui.getGuiTab().repaint();
                 gui.actualizarJugadores();
                 registroCorrecto=false;
                 cargar.dispose();
                 if(gui.getGuiTab().getPlayer2().getAlias().equals("Cpu")){
                     gui.setInterrumpirHilo(false);
                     gui.setHiloCpu(new HiloCpu(gui,3));
                 }
            }
        }
        if(str.equals("Cancelarup1")){
          up1.dispose();
          cierraup2=true;
        }
      
        if(str.equals("Cancelarup2")){
          up2.dispose();
          registroCorrecto=false;
        }
      
        if(str.equals("Aceptarup1")||ae.getSource()==pass1){
          Player jugadorBuscado=gui.getGuiTab().buscarAlias(alias1);
          String contr=new String(pass1.getPassword());
          if(contr.equals(jugadorBuscado.getContrasenha())){
              up1.dispose();
          }else
              infoup1.setText("La contraseña introducida no es correcta");
          
      }
      
      if(str.equals("Aceptarup2")||ae.getSource()==pass2){
          Player jugadorBuscado=gui.getGuiTab().buscarAlias(alias2);
          String contr=new String(pass2.getPassword());
          if(contr.equals(jugadorBuscado.getContrasenha())){
              up2.dispose();
              registroCorrecto=true;
          }else
              infoup2.setText("La contraseña introducida no es correcta");
       }
        
        
        if(str.equals("Cancelar cargar")){
            cargar.dispose();
        }
        if(str.equals("Borrar")){
            //Si se selecciona, se borra la partida
            //La lista de partidas mostrada se vacía y luego se vuelve a rellenar con las que quedan
            String nombre=(String)lista.getSelectedValue();
            gui.borrarPartida(nombre);
            lista.removeAll();
            int longitud=gui.getPartidasGuardadas().size();
            String[] nombres=new String[longitud];
            Iterator<SavedTab> itr=gui.getPartidasGuardadas().iterator();
            for(int i=0; i<gui.getPartidasGuardadas().size(); i++){
                nombres[i]=itr.next().getNombre();
            }
            lista.setListData(nombres);
        }
    }
    
    /**
     * Activa la partida que se selecciona de la lista de partidas guardadas. Si la lista contiene partidas guardadas, 
     * cuando se selecciona una, se muestra la información sobre ella en la ventana; y además, si estamos en el modo 
     * cargar partida, se habilitan los botones de cargar y borrar. Si la lista no contiene partidas se deshabilitan los
     * botones.
     * @param e Evento de selección de lista
     */
    public void valueChanged(ListSelectionEvent e) {
       if (!e.getValueIsAdjusting()) {
           if(!lista.isSelectionEmpty()){
            String seleccion=(String)lista.getSelectedValue();
            //Si estamos en la pantalla de cargar, se habilitan los botones de borrar y cargar
            if(estaCargando){
                cargarb.setEnabled(true);
                borrar.setEnabled(true);
            }
            SavedTab partida=gui.buscarPartida(seleccion);
            String text=partida.getNombre()+" ("+partida.getJugador1()+", "
                +partida.getJugador2()+") "+partida.getFecha().get(Calendar.DAY_OF_MONTH)+
                "/"+(partida.getFecha().get(Calendar.MONTH)+1)+"/"+partida.getFecha().get(Calendar.YEAR);
            while(text.length()<=46||text.length()<=47){
                text=" "+text+" ";
            }
            datosPartida.setText(text);
           }else{
               //Si no hay nada seleccionado, se deshabilitan los botones de cargar y borrar
               if(estaCargando){
                   cargarb.setEnabled(false);
                   borrar.setEnabled(false);
               }
           }
       }
    }
    
}
