

package XD.contour;

import XD.contour.util.ContourLine;
import XD.contour.util.Image;

/**
 * Class created to test functionality of objects and such.
 *
 * @author Alejandro Tëllez G. <java.util.fck@hotmail.com>
 */
public class Test2 {

    public static void main(String args[]) {
        
        int alto = 5, ancho = 5;
        int[][]matrix = new int [ancho][alto];
        
        //Inicializa la matriz de prueba en blanco
        for (int j = 0; j < alto; j++){
            for (int i = 0; i < ancho; i++){
                matrix[i][j] = 255;
            }
        }
        
        //Asigna puntos de una curva de prueba definida
        matrix[0][3] = 79;
        matrix[1][1] = 79;
        matrix[1][2] = 79;
        matrix[1][4] = 79;
        matrix[2][0] = 79;
        matrix[2][4] = 79;
        matrix[3][0] = 79;
        matrix[3][3] = 79;
        matrix[4][1] = 79;
        matrix[4][2] = 79;
        
        //Construye un nuevo objeto de imágen con la matriz actual
        Image i = new Image(matrix, 8, ancho, alto);
        i.getContourLines();
        System.out.println("Colores distintos o número de curvas: "
                + /*
                 * i.contourLines.contourLines.toString()
                 */ +i.contourLines.contourLines.size());
        
        for (ContourLine c : i.contourLines.contourLines) {
            c.printCurva();
        }
               
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        
        i.setSamplingDistance(1.5);
        
        i.sampleContourLines();
        
        for (ContourLine c : i.contourLines.contourLines) {
            c.sampledCurva.printCurva();
        }
        i.writeImage();
        Image sampledImage = new Image(i.getSampledMatrix(), 8, i.img.getAncho(), i.img.getAlto());
        
        sampledImage.writeImage("sampledCurvas2");

    }
}
