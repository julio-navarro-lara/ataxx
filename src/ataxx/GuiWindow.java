
package ataxx;

import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * La clase <code>GuiWindow</code> se encarga de cargar la lista de jugadores y de partidas al iniciar la aplicación y 
 * de guardarla al cerrar la aplicación.
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
public class GuiWindow implements WindowListener{
    /**
     * Ventana de la aplicación.
     */
    private Gui gui;
    
    /**
     * Inicializa un objeto con la ventana especificada.
     * @param gui Ventana
     */
    public GuiWindow(Gui gui){
        this.gui=gui;
    }
    
    /**
     * Detecta cuándo la ventana está cerrada. En nuestro caso no hace nada.
     * @param we Evento de ventana
     */
    public void windowClosed(WindowEvent we){
    }
    
    /**
     * Carga la lista de jugadores y de partidas al iniciar la ventana. Se crean los flujos de datos necesarios para
     * que al iniciar la aplicación se reestablezcan todos los usuarios registrados y todas las partidas guardadas.
     * @param we Evento de ventana
     * @see GuiTab#getListaJugadores()
     * @see Gui#getPartidasGuardadas()
     */
    public void windowOpened(WindowEvent we){

        //Se toma el archivo de jugadores y se carga su contenido en la lista de jugadores del programa
      File f=new File("players");

      FileInputStream fis=null;
      ObjectInputStream ois=null;
      try{
          fis=new FileInputStream(f);
          ois=new ObjectInputStream(fis);
          Player jug;
          while((jug=(Player)ois.readObject())!=null){
              gui.getGuiTab().getListaJugadores().add(jug);
          }
      }catch(IOException e){
          System.out.println("Error en la lectura de datos");
      }catch(ClassNotFoundException e1){
          System.out.println("No se encuentra la clase en el archivo");
      }
      try{
          fis.close();
          ois.close();
      }catch(IOException e){
          System.out.println("Problema al cerrar el flujo");
      }
      
      //Se toma el archivo de partidas guardadas y se carga su contenido en la lista de partidas del programa
      File f2=new File("partidas");
      
      FileInputStream fis2=null;
      ObjectInputStream ois2=null;
      SavedTab partida;
      try{
          fis2=new FileInputStream(f2);
          ois2=new ObjectInputStream(fis2);
      }catch(IOException e){
          System.out.println("Problema en la creación de flujo");
      }
      
      try{
          while((partida=(SavedTab)ois2.readObject())!=null){
              gui.getPartidasGuardadas().add(partida);
          }
      }catch(EOFException e){
          System.out.println("Fin de lectura de partidas");
      }catch(InvalidClassException e){
          System.out.println("Flujo erroneo");
      }catch(ClassNotFoundException e){
          System.out.println("Clase incompatible");
      }catch(IOException e){
          System.out.println("IOException");
      }
      
      try{
          ois2.close();
      }catch(IOException e){
          System.out.println("Problema al cerrar el flujo");
      }
      
    }
    
    /**
     * Detecta cuándo la ventana se activa. En nuestro caso no hace nada.
     * @param we Evento de ventana
     */
    public void windowActivated(WindowEvent we){
    }
    
    /**
     * Almacena los jugadores registrados y las partidas guardadas al cerrar la ventana. Si la partida no está guardada
     * cuando se cierra la ventana principal, se abre una ventana para decidir si se quiere guardar. Una vez hecho esto, 
     * se crean los flujos de datos necesarios para almacenar todos los jugadores registrados y todas las partidas 
     * guardadas.
     * @param we Evento de ventana
     */
    public void windowClosing(WindowEvent we){
        //Si la partida no está guardad se da la opción de guardar
      if(!gui.getGuiTab().estaGuardada())  
        new SaveFrame(gui,true);
        
      //Se guardan todos los jugadores de la lista del programa
      //Se guardan todas las partidas de la lista del programa
      File f=new File("players");
      try{
      if(!f.exists()){
          f.createNewFile();
      }
      }catch(IOException exception){
          System.out.println("Error al crear el archivo");
      }
      File f2=new File("partidas");
      try{
      if(!f2.exists()){
          f2.createNewFile();
      }
      }catch(IOException exception){
          System.out.println("Error al crear el archivo de partidas guardadas");
      }
      
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
      
      FileOutputStream fos2=null;
      ObjectOutputStream flujo=null;
      SavedTab partida;
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
      
    }
    
    /**
     * Detecta cuándo la ventana se desactiva. En nuestro caso no hace nada.
     * @param we Evento de ventana
     */
    public void windowDeactivated(WindowEvent we){
    }
    
    /**
     * Detecta cuándo la ventana se "desicona". En nuestro caso no hace nada.
     * @param we Evento de ventana
     */
    public void windowDeiconified(WindowEvent we){
    }
    
    /**
     * Detecta cuándo la ventana se "icona". En nuestro caso no hace nada.
     * @param we Evento de ventana
     */
    public void windowIconified(WindowEvent we){
    }
    
}
