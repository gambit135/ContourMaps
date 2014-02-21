package HuellaDigital;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Esta clase implementa la estructura necesaría para almacenar una imagen en
 * escala de grises o blanco y negro, dando acceso a sus datos básicos: matriz
 * de pixeles, dimensiones, profundidad, y componentes de color(siempre uno),
 * proveé métodos para acceder y modificar la información de sus pixeles.
 *
 * @author Angel Pérez
 */
public class Imagen {

    private int[][] pixeles;
    private int alto;
    private int ancho;
    private int bits;
    private int componentes;

    /**
     * Construye una imagen en escala de grises a partir de una imagen en grises
     * almacena en disco duro.
     *
     * @param ruta_img ruta hacia la imagen en disco duro.
     */
    public Imagen(String ruta_img) {
        try {
            BufferedImage image = ImageIO.read(new File(ruta_img));
            this.alto = image.getHeight();
            this.ancho = image.getWidth();
            this.bits = image.getColorModel().getPixelSize();
            this.componentes = image.getColorModel().getNumComponents();
            this.pixeles = new int[this.ancho][this.alto];

            //Lee cada pixel de la imagen y la almacena en un arreglo
            int pixel;
            for (int j = 0; j < this.alto; j++) {
                for (int i = 0; i < this.ancho; i++) {
                    pixel = image.getRaster().getSample(i, j, 0);
                    this.pixeles[i][j] = pixel;
                }
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Constructor de copia, construye una imagen en escala de grises, a partir
     * de un objeto Imagen ya existente.
     *
     * @param imagen_o Objeto Imagen del cual se quiere generar una copia.
     */
    public Imagen(Imagen imagen_o) {
        this.pixeles = new int[imagen_o.getAlto()][imagen_o.getAncho()];
        for (int i = 0; i < imagen_o.getAlto(); i++) {
            for (int j = 0; j < imagen_o.getAncho(); j++) {
                this.pixeles[i][j] = imagen_o.getPixel(i, j);
            }
        }

        this.alto = imagen_o.getAlto();
        this.ancho = imagen_o.getAncho();
        this.bits = imagen_o.getBits();
        this.componentes = imagen_o.getComponentes();
    }

    /**
     * Construye una imagen en escala de grises a partir de una matriz de mxn.
     *
     * @param pixeles matriz de mxn que representa los pixeles de la imagen.
     * @param bits    numero de bits por pixel o profundidad de color.
     */
    public Imagen(int[][] pixeles, int bits, int ancho, int alto) {
        if (pixeles == null) {
            return;
        }
        if (bits > 8) {
            bits = 8;
        }
        if (bits < 8) {
            bits = 1;
        }

        this.pixeles = new int[ancho][alto];
        this.alto = alto;
        this.ancho = ancho;
        this.bits = bits;
        this.componentes = 1;

        for (int j = 0; j < this.alto; j++) {
            for (int i = 0; i < this.ancho; i++) {
                this.pixeles[i][j] = pixeles[i][j];
            }
        }

    }

    /**
     * Construye una imagen en escala de grises a partir de sus dimensiones
     * (alto y ancho).
     *
     * @param alto  pixeles de altura de la imagen.
     * @param ancho pixeles de anchura de la imagen.
     * @param bits  numero de bits por pixel o profundidad de color.
     */
    public Imagen(int alto, int ancho, int bits) {
        if (alto <= 0 || ancho <= 0) {
            return;
        }
        if (bits > 8) {
            bits = 8;
        }
        if (bits < 8) {
            bits = 1;
        }

        this.pixeles = new int[alto][ancho];
        this.alto = alto;
        this.ancho = ancho;
        this.bits = bits;
        this.componentes = 1;
    }

    /**
     * Obtiene el ancho en pixeles de la imagen.
     *
     * @return el ancho de la imagen.
     */
    public int getAncho() {
        return this.ancho;
    }

    /**
     * Obtiene el alto en pixeles de la imagen.
     *
     * @return el alto de la imagen
     */
    public int getAlto() {
        return this.alto;
    }

    /**
     * Obtine la cantindad de bits de profundidad de color que forman un pixel
     * en la imagen.
     *
     * @return bits por pixel
     */
    public int getBits() {
        return this.bits;
    }

    /**
     * Obtiene la cantidad de componente del color de la imagen.
     *
     * @return cantidad de componentes del color.
     */
    public int getComponentes() {
        return this.componentes;
    }

    /**
     * Obtine el valor del pixel en la coordenada (i,j) de la imagen, siendo
     * siendo 0 <= i <= alto y 0 <= j <= ancho. Si las coordenadas salen de los
     * rangos espeificados, entonces la funcion retoran el valor 255(blanco).
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *

     *
     * @param i indice de alto.
     * @param j indice de ancho.
     * @return valor del pixel en la coordenada(i,j).
     */
    public int getPixel(int i, int j) {
        if (i < 0 || i >= this.ancho || j < 0 || j >= this.alto) {
            return 255;
        }
        return this.pixeles[i][j];
    }

    /**
     * Establece el pixel en la coordenada (i,j) de la imagen con el valor de
     * value, siendo siendo 0 <= i <= alto y 0 <= j <= ancho. Retorna true si se
     * pudo realizar la operaciÃ³n, en caso contrario retorna false.
     *

     *
     * @param i     indice de alto.
     * @param j     indice de ancho.
     * @param value valor a establecer el pixel (i,j).
     * @return true si se pudo hacer la opereciÃ³n, false en caso contrario.
     */
    public boolean setPixel(int i, int j, int value) {
        if (i < 0 || i >= this.alto || j < 0 || j >= this.ancho) {
            return false;
        }
        if (value > 255) {
            value = 255;
        }
        else if (value < 0) {
            value = 0;
        }
        this.pixeles[i][j] = value;
        return true;
    }

    /**
     * Escribe en la ruta especificada el archivo de imagen correspondiente al
     * objeto actual.
     *
     * @param ruta_img_salida ruta en la que se guardara la imagen.
     * @return true si la operaciÃ³n se logrÃ³ con exito, false en caso
     *         contrario.
     */
    public boolean escribeImagen(String ruta_img_salida) {
        try {
            BufferedImage imagen_salida;
            if (this.bits == 8) {
                imagen_salida = new BufferedImage(this.ancho, this.alto, BufferedImage.TYPE_BYTE_GRAY);
            }
            else {
                imagen_salida = new BufferedImage(this.ancho, this.alto, BufferedImage.TYPE_BYTE_BINARY);
            }

            WritableRaster salida = imagen_salida.getRaster();

            for (int j = 0; j < this.alto; j++) {
                for (int i = 0; i < this.ancho; i++) {
                    if (this.pixeles[i][j] != 255) {
                        salida.setSample(i, j, 0, 0);
                    }
                    else {
                        salida.setSample(i, j, 0, 255);
                    }
                    //salida.setSample(i, j, 0, this.pixeles[i][j]);
                }
            }

            imagen_salida.setData(salida);
            ImageIO.write(imagen_salida, "BMP", new File(ruta_img_salida));
            return true;
        }
        catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Obtiene una matriz que representa los pixeles del objeto Imagen actual
     *
     * @return matriz de pixeles.
     */
    public int[][] getPixeles() {
        return this.pixeles;
    }

    /**
     * Establece la profundidad de color o numero de bits que utilizara cada
     * pixel.
     *
     * @param bits cantidad de bits por pixel
     * @return true si la operaciÃ³n fue correcta, false en caso contrario.
     */
    public boolean setNumBits(int bits) {
        if (bits != 1 && bits != 8) {
            return false;
        }
        this.bits = bits;
        return true;
    }
}