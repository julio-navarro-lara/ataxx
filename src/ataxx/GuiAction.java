
package ataxx;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.text.*;

/**
 * La clase <code>GuiAction</code> gestiona todos los eventos que se puedan producir en los menús y submenús de la
 * aplicación.
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
public class GuiAction implements ActionListener,ListSelectionListener{
   /**
    * Ventana de la aplicación.
    */
    private Gui gui;
    /**
     * Ventana de cambio de usuario.
     */
    private JDialog usu;
    
    /**
     * Ventana de nuevo usuario.
     */
    private JDialog nusu;
    /**
     * Campo de texto para la introducción del alias.
     */
    private JTextField alias;
    /**
     * Campo de texto para la introducción de la contraseña.
     */
    private JPasswordField contrasenha;
    /**
     * Campo de texto para la introducción del nombre.
     */
    private JTextField nombre;
    /**
     * Campo de texto para la introducción de los apellidos.
     */
    private JTextField apellidos;
    /**
     * Campo de texto para la introducción de la fecha de nacimiento.
     */
    private JTextField nacimiento;
    /**
     * Etiqueta para informar al usuario durante la creación de usuarios.
     */
    private JLabel informacion;
    
    /**
     * Ventana que indica la elección incorrecta de usuarios.
     */
    private JDialog ui;
    
    /**
     * Ventana en la que se introduce la contraseña del usuario 1.
     */
    private JDialog up1=new JDialog();
    /**
     * Ventana en la que se introduce la contraseña del usuario 2.
     */
    private JDialog up2=new JDialog();
    /**
     * Información de la ventana de contraseña 1.
     */
    private JLabel infoup1;
    /**
     * Información de la ventana de contraseña 2.
     */
    private JLabel infoup2;
    /**
     * Campo de texto para la introducción de la contraseña del usuario 1.
     */
    private JPasswordField pass1;
    /**
     * Campo de texto para la introducción de la contraseña del usuario 2.
     */
    private JPasswordField pass2;
    /**
     * Indica si se cierra la ventana de contraseña del usuario 2.
     */
    private boolean cierraup2=false;
    
    /**
     * Ventana de estadísticas de usuarios.
     */
    private JDialog est;
    /**
     * Lista de jugadores en la ventana de estadísticas.
     */
    private JList lista;
    /**
     * Campo de los datos de cada usuario.
     */
    private JTextArea datos;
    /**
     * Botón de borrar usuario.
     */
    private JButton borrar;
    /**
     * Contenedor con barras de desplazamiento para los datos del usuario.
     */
    private JScrollPane textScroller;
    
    /**
     * Ventana para elegir dificultad.
     */
    private JDialog dif;
    /**
     * Botón para aceptar dificultad.
     */
    private JButton aceptarDif;
    
    /**
     * Ventana para indicar ruta de lector de pdf.
     */
    private JDialog pdf;
    /**
     * Botón de aceptar en la ventana de elección de ruta de pdf.
     */
    private JButton aceptarPdf;
    /**
     * Botón de cancelar en la ventana de elección de ruta de pdf.
     */
    private JButton cancelarPdf;
    /**
     * Campo de texto para introducir la ruta del lector de pdf.
     */
    private JTextField rutaPdf;
    /**
     * Etiqueta de información de itroducción de ruta del lector de pdf.
     */
    private JLabel infoPdf;
    
    /**
     * Constructor de la clase. Asigna una ventana al objeto.
     * @param gui Ventana de la aplicación
     */
    public GuiAction(Gui gui){
        this.gui=gui;
    }
    
    /**
     * Gestiona todos los eventos que se producen en los menús.
     * 
     * -Si entramos en <i>Nueva Partida</i> en primer lugar se comprueba que la partida actual está guardada y si no lo 
     * está aparece una ventana indicando si se desea guardar. Una vez hecho esto, se inicializa el tablero y la 
     * posición activa, se establece como jugador activo al primer jugador y se actualiza la pantalla. 
     * 
     * -Si entramos en <i>Salir</i> y no hemos guardado la partida se abre una ventana para decidir si se quiere guardar
     * o no. Una vez hecho esto, se crean los flujos de datos necesarios para almacenar la lista de jugadores registrados
     * y la de partidas guardadas.
     * 
     * -Si entramos en <i>Cambiar Usuario</i> primero se comprueba si la partida está guardada y si no lo está aparece 
     * una ventana indicando si se desea guardar. Una vez hecho esto se inicializa el tablero, se establece como jugador
     * activo al primer jugador y se actualiza por pantalla. A continuación, se abre una ventana de diálogo para cambiar
     * los usuarios en la que aparece una lista con todos ellos. 
     * 
     * Cuando elegimos a un usuario se pide la introducción de su contraseña, excepto en el caso del jugador por 
     * defecto. Si escojemos como usuario a Cpu aparece una ventana en la que se indica el nivel de dificultad de la
     * partida, una vez elegido se cierra la ventana.
     * 
     * -Si entramos en el menú <i>Nuevo Usuario</i> aparecerá una nueva ventana en la que se pide la introducción del alias,
     * del nombre y los apellidos y de la fecha de nacimiento. La ventana tiene dos botones, el botón "Crear" comprueba que
     * los datos introducidos sean correctos y que no exista un jugador con ese alias para después añadirlo a la lista de 
     * jugadores, y el botón "Cancelar" cierra la ventana de creación de usuario.
     * 
     * -Si entramos en <i>Estadísticas</i> aparece una nueva ventana que muestra una lista con el alias de todos los jugadores
     * registrados. Si seleccionamos un alias aparecen sus estadísticas, y si pulsamos sobre
     * el botón borrar se elimina al jugador de la lista de estadísticas.
     * 
     * -Desde el icono "Marcas" podemos activar o desacticar las marcas de posibilidad de movimiento.
     * -Desde el icono "Sonidos" podemos activar o desactivar los sonidos del juego.
     * -Desde el icono "Manual" se abre el archivo que contiene el manual del juego.
     * -El icono "Acerca de " abre una ventana en la que aparece una imagen de con los datos de los creadores.
     * @see GestorDeMovimientos#inicializarTablero()
     * @see GuiTab#ordenarListaJugadores()
     * @see Player#aumentarVecesInicio()
     * 
     * 
     */
  public void actionPerformed(ActionEvent ae){
      String str=ae.getActionCommand();
      if(str.equals("Nueva partida")){
          //Si la partida no está guardada, se muestra una ventana para poder guardarla.
          if(!gui.getGuiTab().estaGuardada())
            new SaveFrame(gui,true);
          gui.setInterrumpirHilo(true);
          gui.setHiloCpu(null);
          gui.getGuiTab().getGDM().inicializarTablero();
          Gui.cambiarActivo(1);
          gui.getGuiTab().repaint();
          gui.getGuiTab().setEstaGuardada(true);
          gui.getGuiTab().modificarPosActiva(-1, -1);
          if(gui.getGuiTab().getPlayer2().getAlias().equals("Cpu")){
              gui.setInterrumpirHilo(false);
              gui.setHiloCpu(new HiloCpu(gui));
          }
      }
      
      if(str.equals("Salir")){
        if(!gui.getGuiTab().estaGuardada())
            new SaveFrame(gui,true);
        //Se crea un archivo para guardar los jugadores
        File f=new File("players");
        try{
        if(!f.exists()){
             f.createNewFile();
        }
        }catch(IOException exception){
              System.out.println("Error al crear el archivo");
        }
        //Se crea un archivo para guardar las partidas
        File f2=new File("partidas");
        try{
        if(!f2.exists()){
              f2.createNewFile();
        }
        }catch(IOException exception){
              System.out.println("Error al crear el archivo de partidas guardadas");
        }
          
        //Se escriben los jugadores almacenados en el programa.
        FileOutputStream fos;
        ObjectOutputStream oos=null;
        try{
              fos=new FileOutputStream(f);
              oos=new ObjectOutputStream(fos);
              
            for(Player p:gui.getGuiTab().getListaJugadores()){
                  oos.writeObject(p);
            }
        }catch(IOException io){
              System.out.println("Problemas en la escritura de datos.");
        } 
        try{
              oos.close();
        }catch(IOException e){
              System.out.println("Problema al cerrar el flujo");
        }
        //Se escriben las partidas almacenadas en el programa
        FileOutputStream fos2=null;
        ObjectOutputStream flujo=null;
        try{
          fos2=new FileOutputStream(f2);
          flujo=new ObjectOutputStream(fos2);
        }catch(IOException e){
          System.out.println("Problema en la creación de flujo");
        }
        for(SavedTab i:gui.getPartidasGuardadas()){
            try{
                flujo.writeObject(i);
            }catch(IOException e){
                System.out.println("Problema al escribir: "+i);
            }
        }
        try{
          flujo.close();
        }catch(IOException e){
          System.out.println("Problema al cerrar el flujo 2");
        }
        System.exit(0);
     }
      
      
        if(str.equals("Cambiar usuario")){
            //Se inicializan los elementos y se dispone todo para una nueva partida.
            if(!gui.getGuiTab().estaGuardada())
                new SaveFrame(gui,true);
            gui.getGuiTab().getGDM().inicializarTablero();
            Gui.cambiarActivo(1);
            gui.getGuiTab().repaint();
            gui.getGuiTab().setEstaGuardada(true);
            gui.getGuiTab().modificarPosActiva(-1, -1);
            
            //Se crea la ventana de elección de usuario
            usu=new JDialog(gui,"Cambiar usuario",true);
            usu.setLocationRelativeTo(gui);
            usu.setSize(250,113);
            usu.setLayout(new FlowLayout());
         
         JLabel etiqueta1=new JLabel("      "+"Jugador 1");
         usu.add(etiqueta1);
         
         usu.add(new JLabel("            "));
         
         JLabel etiqueta2=new JLabel("Jugador 2"+"     ");
         usu.add(etiqueta2);
         
         usu.add(new JLabel("  "));
         
         gui.getGuiTab().ordenarListaJugadores();
         
         //Se crean las listas con el nombre de los usuarios
         JComboBox jc1=new JComboBox();
         for(Player p: gui.getGuiTab().getListaJugadores()){
             if(!p.getAlias().equals("Cpu"))
                jc1.addItem(p.getAlias());
         }
         usu.add(jc1);
         jc1.setSelectedItem(gui.getGuiTab().getPlayer1().getAlias());
         gui.modificarNombreJugador1((String)jc1.getSelectedItem());
         PlayerAction pa1=new PlayerAction(gui,1);
         jc1.addItemListener(pa1);
         
         usu.add(new JLabel("       "));
         
         JComboBox jc2=new JComboBox();
         for(Player p: gui.getGuiTab().getListaJugadores()){
             jc2.addItem(p.getAlias());
         }
         usu.add(jc2);
         jc2.setSelectedItem(gui.getGuiTab().getPlayer2().getAlias());
         gui.modificarNombreJugador2((String)jc2.getSelectedItem());
         PlayerAction pa2=new PlayerAction(gui,2);
         jc2.addItemListener(pa2);
         
         JButton jb=new JButton("Aceptar");
         jb.addActionListener(this);
         usu.add(jb);
         jb.setMnemonic(KeyEvent.VK_A);
         
         usu.setResizable(false);
         usu.setVisible(true);
      }
      
      if(str.equals("Aceptar")){
          //Si ambos usuarios son iguales, se muestra una ventana de error.
         if(gui.getNombreJugador1().equals(gui.getNombreJugador2())&&!gui.getNombreJugador1().
                 equals("Default")&&!gui.getNombreJugador2().equals("Default")&&
                 !gui.getNombreJugador2().equals("Cpu")){
             ui=new JDialog(usu,true);
             ui.setLocationRelativeTo(usu);
             ui.setSize(160,110);
             ui.setLayout(new FlowLayout(FlowLayout.CENTER));
             ui.add(new JLabel("Un usuario no puede"));
             ui.add(new JLabel("jugar contra sí mismo."));
             JButton ok=new JButton("Aceptar");
             ok.setActionCommand("Aceptarui");
             ui.add(ok);
             ok.addActionListener(this);
             ui.setResizable(false);
             ui.setVisible(true);
         }else{
             
             if(gui.getNombreJugador1().equals("Default")||gui.getNombreJugador1()
                     .equals(gui.getGuiTab().getPlayer1().getAlias())){
                 gui.getGuiTab().modificarPlayer1(gui.getNombreJugador1());
                 gui.actualizarJugadores();
             //Si el jugador elegido no es el jugador por defecto, se pide la introducción de la contraseña
             }else{
                 up1=new JDialog(usu,"Jugador 1- "+gui.getNombreJugador1(),true);
                 up1.setLocationRelativeTo(gui);
                 up1.setSize(240,130);
                 up1.setResizable(false);
                 up1.setLayout(new FlowLayout(FlowLayout.CENTER));
                 up1.add(new JLabel("Introduzca la contraseña de "+gui.getNombreJugador1()));
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
                if(gui.getNombreJugador2().equals("Default")||gui.getNombreJugador2().
                        equals(gui.getGuiTab().getPlayer2().getAlias())||gui.
                        getNombreJugador2().equals("Cpu")){
                    //Si el segundo jugador es la Cpu, se dispone todo para jugar contra la máquina
                    //Debe elegirse la dificultad de la máquina
                    if(gui.getNombreJugador2().equals("Cpu")){
                        gui.setHiloCpu(null);
                        gui.setInterrumpirHilo(true);
                        dif=new JDialog(gui,"Dificultad",true);
                        dif.setResizable(false);
                        dif.setSize(200,100);
                        dif.setLocationRelativeTo(gui);
                        dif.setLayout(new FlowLayout(FlowLayout.CENTER));
                        
                        JRadioButton facil=new JRadioButton("Fácil");
                        facil.setMnemonic(KeyEvent.VK_F);
                        
                        JRadioButton medio=new JRadioButton("Medio");
                        medio.setMnemonic(KeyEvent.VK_M);
                        
                        JRadioButton dificil=new JRadioButton("Difícil");
                        dificil.setMnemonic(KeyEvent.VK_D);
                        
                        //Se hace un grupo con los botones de dificultad
                        ButtonGroup grupo=new ButtonGroup();
                        grupo.add(facil);
                        grupo.add(medio);
                        grupo.add(dificil);
                        
                        dif.add(facil);
                        dif.add(medio);
                        dif.add(dificil);
                        
                        facil.addActionListener(this);
                        medio.addActionListener(this);
                        dificil.addActionListener(this);
                        
                        aceptarDif=new JButton("Aceptar");
                        aceptarDif.setActionCommand("Aceptar dificultad");
                        aceptarDif.setEnabled(false);
                        dif.add(aceptarDif);
                        aceptarDif.addActionListener(this);
                        
                        dif.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                        dif.setVisible(true);
                        //Se inicia un hilo de la Cpu para que pueda jugar contra el usuario
                        gui.setInterrumpirHilo(false);
                        gui.setHiloCpu(new HiloCpu(gui));
                    }
                    else{
                        //Si se elige un usuario que no sea la máquina, se interrumpe el posible hilo de Cpu que pudiese haber.
                        gui.setHiloCpu(null);
                        gui.setInterrumpirHilo(true);
                    }
                     gui.getGuiTab().modificarPlayer2(gui.getNombreJugador2());
                    gui.actualizarJugadores();
                    usu.dispose();
                    
                }else{
                  up2=new JDialog(usu,"Jugador 2- "+gui.getNombreJugador2(),true);
                  up2.setLocationRelativeTo(gui);
                  up2.setSize(240,130);
                  up2.setResizable(false);
                  up2.setLayout(new FlowLayout(FlowLayout.CENTER));
                  up2.add(new JLabel("Introduzca la contraseña de "+gui.getNombreJugador2()));
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
             }else{
                 cierraup2=false;
             }
             
         }
      }
      
      if(str.equals("Aceptarui")){
          ui.dispose();
      }
      
      if(str.equals("Cancelarup1")){
          up1.dispose();
          cierraup2=true;
      }
      
      if(str.equals("Cancelarup2")){
          up2.dispose();
      }
      
      if(str.equals("Aceptarup1")||ae.getSource()==pass1){
          Player jugadorBuscado=gui.getGuiTab().buscarAlias(gui.getNombreJugador1());
          String contr=new String(pass1.getPassword());
          if(contr.equals(jugadorBuscado.getContrasenha())){
              gui.getGuiTab().modificarPlayer1(jugadorBuscado.getAlias());
              //Se aumentan las veces que el jugador ha iniciado
              gui.getGuiTab().getPlayer1().aumentarVecesInicio();
              up1.dispose();
              if(gui.getNombreJugador2().equals("Default")){
                  usu.dispose();
              }
              gui.actualizarJugadores();
          }else
              infoup1.setText("Contraseña incorrecta");
          
      }
      
      if(str.equals("Aceptarup2")||ae.getSource()==pass2){
          Player jugadorBuscado=gui.getGuiTab().buscarAlias(gui.getNombreJugador2());
          String contr=new String(pass2.getPassword());
          if(contr.equals(jugadorBuscado.getContrasenha())){
              gui.getGuiTab().modificarPlayer2(jugadorBuscado.getAlias());
              gui.getGuiTab().getPlayer2().aumentarVecesInicio();
              up2.dispose();
              usu.dispose();
              //Si se elige un usuario que no sea la máquina, se interrumpe el posible hilo de Cpu que pudiese haber.
              gui.setHiloCpu(null);
              gui.setInterrumpirHilo(true);
              gui.actualizarJugadores();
          }else
              infoup2.setText("Contraseña incorrecta");
      }
      
      /*
       * Los niveles de dificultad son los siguientes:
       * -Fácil: Profundidad 1
       * -Medio: Profundidad 2
       * -Difícil: Profundidad 3
       */
      if(str.equals("Fácil")){
          aceptarDif.setEnabled(true);
          gui.setDificultad(1);
      }
      
      if(str.equals("Medio")){
          aceptarDif.setEnabled(true);
          gui.setDificultad(2);
      }
      
      if(str.equals("Difícil")){
          aceptarDif.setEnabled(true);
          gui.setDificultad(3);
      }
      
      if(str.equals("Aceptar dificultad")){
          dif.dispose();
      }
      
      if(str.equals("Nuevo Usuario")){
          //Se crea la ventana correspondiente para crear un nuevo usuario.
         nusu=new JDialog(gui,"Nuevo Usuario",true);
         nusu.setLocationRelativeTo(gui);
         nusu.setSize(270,230);
         
         nusu.setLayout(new FlowLayout(FlowLayout.CENTER));
         
         JLabel aliasL=new JLabel("          Alias:    ");
         nusu.add(aliasL);
         
         alias=new JTextField(12);
         nusu.add(alias);
         alias.addActionListener(this);
         
         nusu.add(new JLabel("     "));
         
         JLabel contrasenhaL=new JLabel("Contraseña:  ");
         nusu.add(contrasenhaL);
         
         contrasenha=new JPasswordField(12);
         nusu.add(contrasenha);
         contrasenha.addActionListener(this);
         
         nusu.add(new JLabel("     "));
         
         JLabel nombreL=new JLabel("         Nombre: ");
         nusu.add(nombreL);
         
         nombre=new JTextField(12);
         nusu.add(nombre);
         nombre.addActionListener(this);
         
         nusu.add(new JLabel("      "));
         
         JLabel apellidosL=new JLabel("Apellidos: ");
         nusu.add(apellidosL);
         
         apellidos=new JTextField(12);
         nusu.add(apellidos);
         apellidos.addActionListener(this);
         
         JLabel nacimientoL=new JLabel("Fecha de nacimiento (dd/mm/aaaa):");
         nusu.add(nacimientoL);
         
         nusu.add(new JLabel(""));
         
         nacimiento=new JTextField(17);
         nusu.add(nacimiento);
         nacimiento.addActionListener(this);
         nacimiento.setHorizontalAlignment(JTextField.CENTER);
         
                 
         JButton ok=new JButton("Crear");
         nusu.add(ok);
         ok.addActionListener(this);
         ok.setMnemonic(KeyEvent.VK_C);
         
         JButton no=new JButton("Cancelar");
         no.setActionCommand("CancelarVentanaCreación");
         nusu.add(no);
         no.addActionListener(this);
         no.setMnemonic(KeyEvent.VK_L);
         
         informacion=new JLabel("");
         nusu.add(informacion);
         
         nusu.setResizable(false);
         nusu.setVisible(true);
         
      }
      
      e:if(str.equals("Crear")||ae.getSource()==alias||ae.getSource()==contrasenha||
              ae.getSource()==nombre||ae.getSource()==apellidos||ae.getSource()==nacimiento){
          //Se hacen todas las comprobaciones necesarias para ver que los campos introducidos son correctos
          int contador1=0;
          int contador2=0;
          for(int i=0;i<alias.getText().length();i++){
              char ch=alias.getText().charAt(i);
              if(Character.isLetterOrDigit(ch))
                  contador1++;
          }
          for(int i=0;i<contrasenha.getPassword().length;i++){
              if(Character.isLetterOrDigit(contrasenha.getPassword()[i]))
                  contador2++;
          }
          
          Player jugDuplicado=gui.getGuiTab().buscarAlias(alias.getText());
          
          if(alias.getText().length()<=10&&contador1!=0&&contador2!=0&&
                  jugDuplicado==null){
            StringTokenizer st=new StringTokenizer(nacimiento.getText(),"/");
            GregorianCalendar gc=new GregorianCalendar();
            int dia;
            int mes;
            int anho;
            try{
                dia=Integer.parseInt(st.nextToken());
                mes=Integer.parseInt(st.nextToken());
                anho=Integer.parseInt(st.nextToken());
                gc=new GregorianCalendar(anho, mes-1, dia);
            }catch(Exception e){
                informacion.setText("Debe introducir un formato de fecha correcto");
                break e;
            }
            if(dia>0&&dia<32&&mes>0&&mes<13){
            Player jugador=new Player(alias.getText(),new String(contrasenha.getPassword()),
                    nombre.getText(),apellidos.getText(),gc,new GregorianCalendar());
            System.out.println(jugador);
            gui.getGuiTab().getListaJugadores().add(jugador);
            nusu.dispose();
            }else{
                informacion.setText("Debe introducir un formato de fecha correcto");
                break e; 
            }
          }else if(contador1==0){
              informacion.setText("       Debe introducir un alias válido       ");
          }
          else if(alias.getText().length()>10)
          {
              informacion.setText("El alias debe tener menos de 10 caracteres");
          }else if(jugDuplicado.getAlias().equals(alias.getText())){
              informacion.setText("     Ya existe un jugador con este alias     ");
          }else if(contador2==0){
              informacion.setText("    Debe introducir una contraseña válida    ");
          }
      }
      
      if(str.equals("CancelarVentanaCreación")){
          nusu.dispose();
      }
      
      if(str.equals("Manual")){
          try{
              //Se ejecuta el manual en pdf correspondiente
          Runtime.getRuntime().exec("C:\\Program Files\\Adobe\\Acrobat 7.0\\Reader" +
                  "\\AcroRd32.exe "+new File("manualusuario.pdf").getAbsolutePath());
          }catch(IOException e){
              //Si la ruta del lector de pdf por defecto no es correcta, se pide la introducción de la apropiada
              pdf=new JDialog(gui,"Manual",true);
              pdf.setResizable(false);
              pdf.setSize(380,160);
              pdf.setLayout(new FlowLayout(FlowLayout.CENTER));
              pdf.setLocationRelativeTo(gui);
              pdf.add(new JLabel("Introduzca la ruta de su lector de PDF"));
              pdf.add(new JLabel("Ej: C:\\Program Files\\Adobe\\Acrobat 7.0\\Reader" +
                  "\\AcroRd32.exe "));
              rutaPdf=new JTextField(30);
              pdf.add(rutaPdf);
              rutaPdf.addActionListener(this);
              pdf.add(aceptarPdf=new JButton("Aceptar"));
              aceptarPdf.setActionCommand("AceptarPdf");
              aceptarPdf.addActionListener(this);
              aceptarPdf.setMnemonic(KeyEvent.VK_A);
              pdf.add(cancelarPdf=new JButton("Cancelar"));
              cancelarPdf.setActionCommand("CancelarPdf");
              cancelarPdf.addActionListener(this);
              cancelarPdf.setMnemonic(KeyEvent.VK_C);
              pdf.add(infoPdf=new JLabel(""));
              pdf.setVisible(true);
          }
      }
      
      if(str.equals("AceptarPdf")||ae.getSource()==rutaPdf){
          try{
              Runtime.getRuntime().exec(rutaPdf.getText().trim()+" "+
                      new File("manualusuario.pdf").getAbsolutePath());
              pdf.dispose();
          }catch(IOException e){
              infoPdf.setText("Ruta incorrecta. Introduzca la ruta adecuada");
          }
      }
      if(str.equals("CancelarPdf")){
          pdf.dispose();
      }
      
      if(str.equals("Marcas")){
          gui.getGuiTab().repaint();
      }
      if(str.equals("Sonido")){
          //Si se activa el sonido, se desactiva la musica
          if(gui.getSonido().getState()){
              gui.getGuiTab().setSonido(true);
              if(gui.getMusica().getState())
                  gui.getMusica().doClick();
          }else
              gui.getGuiTab().setSonido(false);
      }
      if(str.equals("Estadísticas")){
          est=new JDialog(gui,true);
          est.setTitle("Estadísticas de jugadores");
          est.setResizable(false);
          est.setSize(330,228);
          est.setLocation(340,290);
          
          est.setLayout(new FlowLayout(FlowLayout.CENTER));
          
          //Se crea una lista con los nombres de todos los usuarios
          gui.getGuiTab().ordenarListaJugadores();
          String[] nombres=new String[gui.getGuiTab().getListaJugadores().size()];
          for(int i=0; i<nombres.length; i++){
              nombres[i]=gui.getGuiTab().getListaJugadores().get(i).getAlias();
          }
          lista=new JList(nombres);
          lista.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
          JScrollPane listScroller = new JScrollPane(lista);
          listScroller.setPreferredSize(new Dimension(90, 150));
          est.add(listScroller);
          lista.addListSelectionListener(this);
          
          //Se crea el campo en el que aparecerán las estadísticas
          datos=new JTextArea();
          datos.setEditable(false);
          datos.setBackground(new Color(255,255,200));
          datos.setFont(new Font("Sans Serif",Font.BOLD,12));
          
          textScroller=new JScrollPane(datos);
          textScroller.setPreferredSize(new Dimension(220,150));
          est.add(textScroller);
          
          JSplitPane jsp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,listScroller,textScroller);
          jsp.setOneTouchExpandable(true);
          jsp.setDividerLocation(90);
          est.add(jsp);
          
          borrar=new JButton("Borrar");
          est.add(borrar);
          borrar.setMnemonic(KeyEvent.VK_B);
          borrar.addActionListener(this);
          borrar.setEnabled(false);
          
          JButton aceptar=new JButton("Aceptar");
          aceptar.setActionCommand("Aceptar estadisticas");
          est.add(aceptar);
          aceptar.setMnemonic(KeyEvent.VK_A);
          aceptar.addActionListener(this);
          
          est.setVisible(true);
      }
      
      if(str.equals("Borrar")){
          String nombre=(String)lista.getSelectedValue();
          gui.getGuiTab().borrarJugador(nombre);
          lista.removeAll();
          String[] nombres=new String[gui.getGuiTab().getListaJugadores().size()];
          for(int i=0; i<nombres.length; i++){
              nombres[i]=gui.getGuiTab().getListaJugadores().get(i).getAlias();
          }
          lista.setListData(nombres);
      }
      if(str.equals("Aceptar estadisticas")){
          est.dispose();
      }
      
      if(str.equals("Acerca de")){
          JDialog ad=new JDialog(gui,"Acerca de",true);
          ad.setSize(350, 350);
          ad.setResizable(false);
          ad.setLocationRelativeTo(gui);
          
          AcercaDe img=new AcercaDe(300,300);
          ad.add(img);
          
          ad.setVisible(true);
      }

  }
  
  /**
   * Muestra las estadísticas de un jugador. Cuando seleccionamos un jugador de la lista del menú <i>Estadísticas</i>
   * se muestran todos los datos almacenados por la ventana.
   */
  public void valueChanged(ListSelectionEvent e){
      if(!e.getValueIsAdjusting()){
          if(!lista.isSelectionEmpty()){
              if(lista.getSelectedValue().equals("Default")){
                  borrar.setEnabled(false);
              }else{
                  borrar.setEnabled(true);
              }
              //Simplemente se muestran las estadísticas correspondientes al jugador seleccionado
              String alias=(String)lista.getSelectedValue();
              Player j=gui.getGuiTab().buscarAlias(alias);
              datos.setText("");
              String str="";
              if(alias.equals("Default")){
                  str+="Jugador por Defecto\n";
                  str+="============================\n";
                  str+="Se trata del jugador por defecto\n"+
                          "con el que juegan los invitados.\n";
                  str+="============================\n";
              }else if(alias.equals("Cpu")){
                  str+="Cpu\n";
                  str+="============================\n";
                  str+="Se trata del jugador que lleva\n"+
                          "la máquina.\n";
                  str+="============================\n";
              }else{
                  str+=alias+"\n";
                  str+="============================\n";
                  str+="Nombre => "+j.getNombre();
                  str+="\nApellidos => "+j.getApellidos();
                  str+="\nFecha de Nacimiento => ";
                  str+=j.getNacimiento().get(Calendar.DAY_OF_MONTH)+"/"
                          +(j.getNacimiento().get(Calendar.MONTH)+1)+"/"+
                          j.getNacimiento().get(Calendar.YEAR)+"\n";
                  str+="Fecha de registro => ";
                  str+=j.getRegistro().get(Calendar.DAY_OF_MONTH)+"/"
                          +(j.getRegistro().get(Calendar.MONTH)+1)+"/"+
                          j.getRegistro().get(Calendar.YEAR)+"\n";
                  str+="============================\n";
                  str+="Estadísticas\n";
                  str+="============================\n";
                  str+="Nº de movimientos => "+j.getMovimientos();
                  str+="\nVeces de inicio del juego=> "+j.getVecesInicio()+"\n";
                  str+="\n''''''''''''''''''''''''''''''''''''''''''''''''''''''\n";
                  str+="Contra otros Usuarios\n";
                  str+="----------------------------\n";
                  double total=j.getGanadas()[0]+j.getEmpates()[0]+j.getPerdidas()[0];
                  str+="Partidas jugadas => "+(int)total+"\n";
                  double porcentaje=total>0?((j.getGanadas()[0]/total)*100):0;
                  porcentaje=Math.round(porcentaje*Math.pow(10,2))/Math.pow(10,2);
                  str+="Partidas ganadas => "+j.getGanadas()[0]+" / "+porcentaje+"%";
                  porcentaje=total>0?j.getPerdidas()[0]/total*100:0;
                  porcentaje=Math.round(porcentaje*Math.pow(10,2))/Math.pow(10,2);
                  str+="\nPartidas perdidas => "+j.getPerdidas()[0]+" / "+porcentaje+"%";
                  porcentaje=total>0?j.getEmpates()[0]/total*100:0;
                  porcentaje=Math.round(porcentaje*Math.pow(10,2))/Math.pow(10,2);
                  str+="\nPartidas empatadas => "+j.getEmpates()[0]+" / "+porcentaje+"%\n";
                  str+="\n''''''''''''''''''''''''''''''''''''''''''''''''''''''\n";
                  str+="Contra la Máquina (Fácil)\n";                  
                  str+="----------------------------\n";
                  total=j.getGanadas()[1]+j.getEmpates()[1]+j.getPerdidas()[1];
                  str+="Partidas jugadas => "+(int)total+"\n";
                  porcentaje=total>0?((j.getGanadas()[1]/total)*100):0;
                  porcentaje=Math.round(porcentaje*Math.pow(10,2))/Math.pow(10,2);
                  str+="Partidas ganadas => "+j.getGanadas()[1]+" / "+porcentaje+"%";
                  porcentaje=total>0?j.getPerdidas()[1]/total*100:0;
                  porcentaje=Math.round(porcentaje*Math.pow(10,2))/Math.pow(10,2);
                  str+="\nPartidas perdidas => "+j.getPerdidas()[1]+" / "+porcentaje+"%";
                  porcentaje=total>0?j.getEmpates()[1]/total*100:0;
                  porcentaje=Math.round(porcentaje*Math.pow(10,2))/Math.pow(10,2);
                  str+="\nPartidas empatadas => "+j.getEmpates()[1]+" / "+porcentaje+"%\n";
                  str+="\n''''''''''''''''''''''''''''''''''''''''''''''''''''''\n";
                  str+="Contra la Máquina (Medio)\n";                  
                  str+="----------------------------\n";
                  total=j.getGanadas()[2]+j.getEmpates()[2]+j.getPerdidas()[2];
                  str+="Partidas jugadas => "+(int)total+"\n";
                  porcentaje=total>0?((j.getGanadas()[2]/total)*100):0;
                  porcentaje=Math.round(porcentaje*Math.pow(10,2))/Math.pow(10,2);
                  str+="Partidas ganadas => "+j.getGanadas()[2]+" / "+porcentaje+"%";
                  porcentaje=total>0?j.getPerdidas()[2]/total*100:0;
                  porcentaje=Math.round(porcentaje*Math.pow(10,2))/Math.pow(10,2);
                  str+="\nPartidas perdidas => "+j.getPerdidas()[2]+" / "+porcentaje+"%";
                  porcentaje=total>0?j.getEmpates()[2]/total*100:0;
                  porcentaje=Math.round(porcentaje*Math.pow(10,2))/Math.pow(10,2);
                  str+="\nPartidas empatadas => "+j.getEmpates()[2]+" / "+porcentaje+"%\n";
                  str+="\n''''''''''''''''''''''''''''''''''''''''''''''''''''''\n";
                  str+="Contra la Máquina (Difícil)\n";                  
                  str+="----------------------------\n";
                  total=j.getGanadas()[3]+j.getEmpates()[3]+j.getPerdidas()[3];
                  str+="Partidas jugadas => "+(int)total+"\n";
                  porcentaje=total>0?((j.getGanadas()[3]/total)*100):0;
                  porcentaje=Math.round(porcentaje*Math.pow(10,2))/Math.pow(10,2);
                  str+="Partidas ganadas => "+j.getGanadas()[3]+" / "+porcentaje+"%";
                  porcentaje=total>0?j.getPerdidas()[3]/total*100:0;
                  porcentaje=Math.round(porcentaje*Math.pow(10,2))/Math.pow(10,2);
                  str+="\nPartidas perdidas => "+j.getPerdidas()[3]+" / "+porcentaje+"%";
                  porcentaje=total>0?j.getEmpates()[3]/total*100:0;
                  porcentaje=Math.round(porcentaje*Math.pow(10,2))/Math.pow(10,2);
                  str+="\nPartidas empatadas => "+j.getEmpates()[3]+" / "+porcentaje+"%\n";
              }
              datos.setText(str);
              datos.setCaretPosition(0);
              
              
          }else{
              borrar.setEnabled(false);
          }
      }
      
  }
}

/**
 * La clase <code>PlayerAction</code> permite cambiar el nombre del jugador cuando se selecciona de la lista.
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
class PlayerAction implements ItemListener{
    /**
     * Jugador.
     */
    private int jugador;
    /**
     * Ventana de la aplicación.
     */
    private Gui gui;
        
    /**
     * Inicia los datos miembro.
     * @param gui Ventana
     * @param i jugador
     */  
    public PlayerAction(Gui gui,int i){
        this.gui=gui;
        this.jugador=i;
    }
    
    /**
     * Actualiza el nombre de los jugadores cuando se seleccionan de la lista.
     * @see Gui#modificarNombreJugador1(String)
     * @see Gui#modificarNombreJugador2(String)
     */
    public void itemStateChanged(ItemEvent ie){
        String s=(String)ie.getItem();
        if(jugador==1){
            gui.modificarNombreJugador1(s);
        }else
          if(jugador==2){
             gui.modificarNombreJugador2(s);
          }
    }
}

/**
 * La clase <code>AcercaDe</code> muestra una ventana con los datos de los creadores.
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 *
 */
class AcercaDe extends JPanel{
    /**
     * Crea un panel con las dimensiones indicadas.
     * @param x Ancho
     * @param y Alto
     */
    public AcercaDe(int x, int y){
        setSize(x,y);
    }
    
    /**
     * Pinta sobre el panel una imagen, "Ataxx" y "Versión 1.0", así como los nombres de los creadores
     */
    public void paint(Graphics g){
        Image img=Toolkit.getDefaultToolkit().getImage("bladerunner.jpg");
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
        g.setColor(new Color(255,146,0));
        g.setFont(new Font("SansSerif",Font.BOLD,30));
        g.drawString("taxx",175,45);
        g.setColor(new Color(255,200,70));
        g.setFont(new Font("SansSerif",Font.BOLD,15));
        g.drawString("Version 1.0.", 150, 61);
        g.drawString("By",150,87);
        g.drawString("Julio Navarro", 154, 105);
        g.drawString("Juan Antonio Montesinos", 154, 125);
        //Se dibuja la A de Ataxx como la trifuerza de Zelda :-)
        g.setColor(new Color(255,146,0));
        int mX=150;
        int mY=45;
        int[] X={mX,mX+14,mX+21,mX+7,mX+14,mX+21,mX+28,mX+14};
	int[] Y={mY,mY-28,mY-14,mY-14,mY,mY-14,mY,mY};
	g.fillPolygon(X,Y,8);
	g.setColor(new Color(158,67,18));
	g.drawPolygon(X,Y,8);
    }
}


