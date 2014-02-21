package HuellaDigital;

//import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Una imagen se representa como una matriz de tamaño n x m, donde cada elemento
 * de la matriz es un pixel en la imagen.
 *
 * En el caso de imágenes en escala de grises, cada pixel es representado por
 * valores de enteros entre 0-255.
 *
 * Suponga una imagen en escala de grises de tamaño 5 X 5. Un ejemplo de matriz
 * que representa dicha imagen es la siguiente:
 *
 * 200 75 87 200 75
 *
 * 129 75 87 240 240
 *
 * 58 75 75 75 240
 *
 * 58 58 210 210 210
 *
 * 200 75 26 240 26
 *
 * @author Mirita <miriam.pescador@gmail.com>
 *
 */
public class Principal {

    public static void main(String[] args) {

        // Leer Imagen de mapas de contorno
        Imagen img_ent = new Imagen("curvas1.bmp");
        int matrizPxls[][] = new int[img_ent.getAncho()][img_ent.getAlto()];
        int contador = 0;
        //Número de curvas distintas (colores)
        int cont2 = 0;
        //Usado para iterar el ArrayList de curvas
        int elemento;
        //Para saber si se ha encontrado un nuevo color
        boolean bandera;

        ArrayList curvas = new ArrayList();
        Iterator<Integer> it;

        //Obtiene el valor de color de todos los pixeles de la imágen 
        //en su matríz de pixeles.
        matrizPxls = img_ent.getPixeles();

        System.out.println("Ancho: " + img_ent.getAncho());
        System.out.println("Alto: " + img_ent.getAlto());

        //Escribir en un archivo la información de la matriz de pixeles
        try {
            Writer salida = new FileWriter("PixelesImagen.txt");
            Writer aux = new FileWriter("ColoresCurvas.txt");
            for (int j = 0; j < img_ent.getAlto() - 1; j++) {
                for (int i = 0; i < img_ent.getAncho() - 1; i++) {
                    // El valor 255 es el fondo blanco de la imagen
                    if (matrizPxls[i][j] != 255) {  //Si no es blanco
                        bandera = false;
                        contador++;
                        it = curvas.iterator(); //Recorre el ArrayList de colores
                        while (it.hasNext()) {
                            //Para cada color en el ArrayList
                            elemento = (int) it.next();
                            if (elemento == matrizPxls[i][j]) {
                                //El color ya se había agregado anteriormente
                                bandera = true;
                                break;
                            }
                        }
                        //Identifica los diferentes colores en la imagen
                        //Si el color no se había agregado
                        if (bandera == false) {
                            //Añadir una nueva curva
                            curvas.add(matrizPxls[i][j]);
                            //Añade al archivo de colores el nuevo color de la curva hallado.
                            aux.write(String.valueOf(matrizPxls[i][j]) + " ");
                            cont2++;
                        }
                    }
                    //Añade al archivo de la Imágen el valor del color de la curva en ese pixel
                    salida.write(String.valueOf(matrizPxls[i][j]) + " ");
                }
            }
            salida.close();
            aux.close();
        } catch (IOException e) {
            System.out.println("Error al escribir " + e);
        }
        System.out.println("Número total de pixeles con valor diferente a 255 -> " + contador);
        System.out.println("Colores en la imagen -> " + cont2);
    }
}