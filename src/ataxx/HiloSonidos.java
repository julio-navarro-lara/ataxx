
package ataxx;
import javax.sound.sampled.*;
import javax.sound.midi.*;
import java.io.*;
import java.awt.event.*;
/**
 * La clase <code>HiloSonidos</code> crea el hilo que controla los sonidos que realiza la aplicación durante su ejecución.
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
public class HiloSonidos implements Runnable,LineListener {
    /**
     * Indica el tipo de sonido a reproducir.
     */
    private int i;
    /**
     * Hilo que reproduce los sonidos.
     */
    private Thread t;
    /**
     * Archivo de sonido.
     */
    private File f;
    /**
     * Permite reproducir los sonidos.
     */
    private Clip clip;
    
    /**
     * Crea e inicia el hilo de sonidos. Se indica el tipo de sonido que se va a reproducir.
     * @param i Tipo de sonido
     */
    public HiloSonidos(int i){
        this.i=i;
        t=new Thread(this,"HiloGuiMouse");
        t.start();
    }
    
    /**
     * Ejecuta el hilo de sonidos. Reproduce el sonido asociado al objeto que llama al método.
     * @see #reproducir()
     */
    public void run(){
        try{
           this.reproducir();
        }catch(LineUnavailableException e2){
            System.out.println("Mala lectura de linea");
        }catch(IOException e3){
            System.out.println("Error en la entrada");
        }catch(UnsupportedAudioFileException e4){
            System.out.println("Tipo de archivo no soportado");
        }
    }
    
    /**
     * Reproduce los sonidos de la aplicación. Una vez seleccionado el tipo de sonido asociado al objeto que llama al 
     * método, comienza su reproducción.
     * @throws LineUnavailableException
     * @throws IOException Si se ha producido un error en la entrada de audio
     * @throws UnsupportedAudioFileException Si la extensión del archivo no es válida
     */
    public void reproducir() throws LineUnavailableException,IOException,
                UnsupportedAudioFileException{
        //Dependiendo del valor de la variable, se reproduce un sonido u otro
        if(i==1){
            f=new File("activarficha.wav");
        }else if(i==2){
            f=new File("button119.wav");
        }else if(i==3){
            f=new File("finpartida.wav");
        }
        //Se inician las gestiones necesarias y se reproduce el sonido.
        Line.Info linfo=new Line.Info(Clip.class);
        Line line=AudioSystem.getLine(linfo);
	clip=(Clip)line;
        clip.addLineListener(this);
	AudioInputStream ais=AudioSystem.getAudioInputStream(f);
	clip.open(ais);
        clip.start();
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
