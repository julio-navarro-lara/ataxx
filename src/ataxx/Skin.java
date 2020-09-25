
package ataxx;
import java.awt.*;

/**
 * La enumeración <code>Skin</code> incluye todos los skins o temas de que dispone la aplicación.
 * @version 1.0
 * @author Julio Navarro Lara
 * @author Juan Antonio Montesinos Delgado
 */
public enum Skin {
    /**
     * La Skin clásica del ataxx, la de toda la vida.
     */
    Clasica(Color.WHITE,Color.BLACK,new Color(90,124,152),Color.WHITE,new Color(110,144,172)),
    /**
     * La Skin feucha que aparece por internet. Con un estilo de cubismo pop art.
     */
    Cubista(Color.RED,Color.BLUE,Color.WHITE,Color.BLACK,Color.GRAY),
    /**
     * Colores que simulan los fuegos del averno.
     */
    Infernum(Color.RED,Color.YELLOW,Color.BLACK,Color.WHITE,new Color(66,66,66)),
    /**
     * Referencia a la conocida serie de los Simpsons
     */
    Homer(new Color(38,142,204),new Color(48,9,234),Color.YELLOW,Color.BLACK,Color.YELLOW),
    /**
     * Colores basados en las pantallas postapocalípticas del universo Matrix
     */
    Matrix(new Color(0,100,0),new Color(65,255,55),Color.BLACK,new Color(67,239,48),new Color(11,57,2)),
    /**
     * Sentido homenaje a la película con la que Kubrick revolucionó el cine ucrónico a partir de la novela de Anthony Burguess.
     */
    NaranjaMecanica(new Color(255,133,19),new Color(140,85,5),Color.ORANGE,Color.ORANGE,Color.LIGHT_GRAY),
    /**
     * Intenta jugar con esta skin si te atreves.
     */
    Paranoico(Color.BLACK,Color.BLACK,Color.WHITE,Color.BLACK,Color.GRAY);
    
    /**
     * Color de las fichas del primer jugador.
     */
    private Color colorj1;
    /**
     * Color de las fichas del segundo jugador.
     */
    private Color colorj2;
    /**
     * Color del fondo del tablero.
     */
    private Color fondo;
    /**
     * Color de las lineas separadoras de casillas.
     */
    private Color colorLineas;
    /**
     * Color de las zonas a las que puede mover una ficha.
     */
    private Color colorMarcas;
    
    /**
     * Crea un nuevo skin. El método recibe como argumentos el color de las fichas de cada jugador, el de fondo
     * del tablero, el de las líneas separadores de casillas y el de las zonas a las que puede mover una ficha.
     */
    Skin(Color colorj1,Color colorj2,Color fondo,Color colorLineas,Color colorMarcas){
        this.colorj1=colorj1;
        this.colorj2=colorj2;
        this.colorLineas=colorLineas;
        this.colorMarcas=colorMarcas;
        this.fondo=fondo;
    }
    
    /**
     * Devuelve el color de las fichas del primer jugador.
     * @return colorj1 Color de las fichas del primer jugador
     */
    public Color getColorJ1(){
        return colorj1;
    }
    /**
     * Devuelve el color de las fichas del segundo jugador.
     * @return colorj2 Color de las fichas del segundo jugador
     */
    public Color getColorJ2(){
        return colorj2;
    }
    /**
     * Devuelve el color del fondo del tablero.
     * @return fondo Color del fondo del tablero
     */
    public Color getColorFondo(){
        return fondo;
    }
    /**
     * Devuelve el color de las lineas separadoras de casillas.
     * @return colorLineas Color de las lineas separadoras de casillas
     */
    public Color getColorLineas(){
        return colorLineas;
    }
    /**
     * Devuelve el color de las zonas a las que puede mover una ficha.
     * @return colorMarcas Color de las zonas a las que puede mover una ficha
     */
    public Color getColorMarcas(){
        return colorMarcas;
    }
}
