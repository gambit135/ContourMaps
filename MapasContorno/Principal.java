/*
Una imagen se representa como una matriz de tamaño n x m 
donde cada elemento de la matriz es un pixel en la imagen.
 
 En el caso de imágenes en escala de grises cada pixel es representado
 por valores de enteros entre 0-255 
 
 Suponga una imagen en escala de grises de tamaño 5 X 5. Un ejemplo de matriz 
 que representa dicha imagen es la siguiente:
    
	200 75 87  200 75  
	129 75 87  240 240  
	58  75 75  75  240  
	58  58 210 210 210  
	200 75 26  240 26  
 
 */

//import java.util.Random;
import java.util.*;
import java.io.*;

public class Principal
{
	public static void main(String [] args)
	{
		
		// Leer Imagen de mapas de contorno
		Imagen img_ent = new Imagen("curvas1.bmp");
		int matrizPxls[][] = new int[img_ent.getAncho()][img_ent.getAlto()];
		int contador = 0, cont2=0, elemento;
		boolean bandera;
		
		ArrayList curvas = new ArrayList();
		Iterator <Integer> it;
		
		matrizPxls = img_ent.getPixeles();
		
		System.out.println("Ancho: " + img_ent.getAncho());
		System.out.println("Alto: " + img_ent.getAlto());
		//Escribir en un archivo la información de la matriz de pixeles
		
		try {
			Writer salida= new FileWriter("PixelesImagen.txt");
			Writer aux= new FileWriter("ColoresCurvas.txt");

			for (int j = 0; j < img_ent.getAncho()-1; j++) {
				for (int i = 0; i < img_ent.getAlto()-1; i++) {
					// El valor 255 es el fondo blanco de la imagen
					if (matrizPxls[i][j] != 255) {
						bandera = false;
						contador++;
						it = curvas.iterator();
						while (it.hasNext()){
							elemento = (int)it.next();
							if(elemento == matrizPxls[i][j]){
								bandera = true;
								break;
							}
						}
						//Identifica los diferentes colores en la imagen
						if(bandera == false){
							curvas.add(matrizPxls[i][j]);
							aux.write(String.valueOf(matrizPxls[i][j]) + " ");
							cont2++;
						}
							
					}
					salida.write(String.valueOf(matrizPxls[i][j]) + " ");
				}
			}
			salida.close();
			aux.close();
		}
		catch (IOException e) {
			System.out.println("Error al escribir " + e);
		}
		
		
		System.out.println("Número total de pixeles con valor diferente a 255 -> " + contador);
		System.out.println("Colores en la imagen -> " + cont2);
		

	}
	
	
}