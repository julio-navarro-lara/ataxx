
package ataxx;

import javax.sound.sampled.*;
import javax.sound.midi.*;
import java.io.*;
import java.awt.event.*;

/**
 * La clase <code>HiloMusical<code> crea el hilo que controla la música que se reproduce durante la ejecución de la
 * aplicación. 
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
public class HiloMusical implements Runnable{
   /**
    * Controla la ejecución del hilo.
    */
    private boolean p=false;
    /**
     * Hilo que reproduce la música.
     */
    private Thread t;
    /**
     * Ventana de la aplicación.
     */
    private Gui entorno;
   /**
    * Contador de canciones.
    */
    private int contador=0;
    
    /**
     * Crea el hilo musical asociado a una ventana. El método recibe como argumento la ventana a la que añadirá música
     * y crea e inicia el hilo que la reproduce.
     * @param entorno Ventana de aplicación
     */
    public HiloMusical(Gui entorno){
        this.entorno=entorno;
        t=new Thread(this,"HiloMusical");
        t.start();
    }
    
    /**
     * Controla la ejecución del hilo musical. Reproduce la música siempre que esté activa. Además, comprueba si
     * una canción ha terminado, para comenzar a reproducir la siguiente. Cuando se reproducen todas las canciones, se
     * vuelve a la primera y comienza un nuevo ciclo.
     * @see Gui#getMusica()
     */
    public void run(){
        try{
            //Bucle infinito que controla la reproducción de la música
            while(p==false){
                //No comienza la reproducción mientras no se seleccione el icono de música
                while(!entorno.getMusica().isSelected());
                //Se aumenta en uno el contador de la música y se llama a la clase Musica con la cancion correspondiente
                contador++;
                Musica m=new Musica(contador,entorno);
                entorno.getMusica().addActionListener(m);
                //Mientras que la música sigue sonando no se continua.
                while(m.sigueSonando());
                entorno.getMusica().removeActionListener(m);
                //Se duerme el hilo un rato entre canción y canción.
                Thread.sleep(1000);
                //Si el contador llega al final, se formatea.
                if(contador==3){
                    contador=0;
                }
            }
        }catch(InterruptedException e){
            System.out.println("Se interrumpió el hilo musical");
        }catch(LineUnavailableException e2){
            System.out.println("Mala lectura de linea");
        }catch(IOException e3){
            System.out.println("Error en la entrada");
        }catch(UnsupportedAudioFileException e4){
            System.out.println("Tipo de archivo no soportado");
        }
        
    }
     
}

/**
 * La clase <code>Musica</code> controla la música que se reproduce en la aplicación. 
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
class Musica extends Object implements ActionListener,LineListener{
       /**
        * Archivo musical.
        */
	private File f;
       /**
        * Permite reproducir la música.
        */
	private Clip clip;
       /**
        * Ventana de la aplicación.
        */
        private Gui gui;

       /**
        * Reproduce la música de la aplicación. Una vez que comprueba si está permitida la música para la ventana, 
        * se reproduce la canción seleccionada.
        * @param k Número de la canción
        * @param gui Ventana
        * @throws LineUnavailableException 
        * @throws IOException Si se ha producido un error en la entrada de audio
        * @throws UnsupportedAudioFileException Si el archivo de música tiene una extensión no válida
        * @see Gui#getMusica()
        */
	public Musica(int k,Gui gui) throws LineUnavailableException,IOException,
                UnsupportedAudioFileException{
            //Se selecciona la canción según el valor de la variable k.
            if(k==1){
                f=new File("bicicletapokemon.wav");
            }else if(k==2){
                f=new File("tetris.wav");
            }else if(k==3){
                f=new File("bolero of fire.wav");
            }
            if(!f.exists()){
                System.out.println("Archivo no encontrado.");
            }
            
            this.gui=gui;
            //Si el icono de musica está activo, se comienza a reproducir la música
            if(gui.getMusica().getState()){
                Line.Info linfo=new Line.Info(Clip.class);
		Line line=AudioSystem.getLine(linfo);
		clip=(Clip)line;
                clip.addLineListener(this);
		AudioInputStream ais=AudioSystem.getAudioInputStream(f);
		clip.open(ais);
                clip.start();
            }
	}
        
        /**
         * Activa o desactiva la música. Controla los eventos que se producen sobre el icono Música.
         * @see Gui#getSonido()
         * @see Gui#getMusica()
         */
        public void actionPerformed(ActionEvent ae) {
            String str=ae.getActionCommand();
            if(str.equals("Música")){
                //Si se selecciona la música, se deselecciona el sonido
               if(gui.getMusica().isSelected()){
                   if(gui.getSonido().isSelected())
                      gui.getSonido().doClick();
                   clip.start();
                   
               }else{
                   clip.stop();
               }
            }
        }
        
        /**
         * Comprueba si se ha terminado de reproducir una canción.
         * @return <code>false</code> si ha terminado o <code>true</code> si aún sigue reproduciendose
         */
        public boolean sigueSonando(){
            boolean p=true;
            //Si se llega al final de la canción, se indica.
            if(clip.getMicrosecondPosition()==clip.getMicrosecondLength()){
                System.out.println("Esto se ha acabado.");
                p=false;
            }
            return p;
        }

        /**
         * Actualiza la información del LineEvent. Indica el estado de reproducción de la canción en la salida estándar.
         */
        public void update(LineEvent le){
                LineEvent.Type type;type=le.getType();
		if(type==LineEvent.Type.OPEN){
			System.out.println("OPEN");
		}else if(type==LineEvent.Type.CLOSE){
			System.out.println("CLOSE");
			System.exit(0);
		}else if(type==LineEvent.Type.START){
			System.out.println("START");
		}else if(type==LineEvent.Type.STOP){
			System.out.println("STOP");
		}
	}
}


