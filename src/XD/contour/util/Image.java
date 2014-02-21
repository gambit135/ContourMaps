package XD.contour.util;

import HuellaDigital.Imagen;
import jdt.delaunay_triangulation.Point_dt;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Wraps and employs methods for reading a color image, provided by the Imagen
 * class. The data about the image is stored in a object of this class. For
 * reading several images at a time, use several instances of this class.
 *
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
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class Image {

    /**
     * The image that this class wraps, as represented by the Imagen class.
     */
    public Imagen img;
    /**
     * The image that this class wraps, as represented by a matrix comprised by
     * pixels.
     */
    public int pixelMatrix[][];
    /**
     * A sampled version of the image that this class wraps, as represented by a
     * matrix comprides by pixels. It was sampled using the sample
     */
    int sampledMatrix[][];
    /**
     * The max height value the topmost curve can achieve, as is proportional to
     * the whole image
     */
    int maxHeight;
    /**
     * A list, containing the curves comprising the current image, as a list of
     * pixels; therefore, this object represents a list of lists of pixels.
     */
    public ContourLines contourLines;
    /**
     * A list, containing all different color values on the current image.
     */
    LinkedList<Integer> listaColores;
    /**
     * The minimum distance that exists between two points of different curves.
     */
    double minDistance;
    /**
     * The distance that should be taken to account when sampling a curve, as
     * defined by half the calculateMinDistance.
     */
    double samplingDistance;

    public void setSamplingDistance(double samplingDistance) {
        this.samplingDistance = samplingDistance;
    }

    /**
     * Instantiates the Imagen class object associated with this class, and
     * initializes and loads it's corresponding pixelMatrix.
     *
     * @param fileName The filename of the image to load, as "curvas1.bmp" The
     *                 file must be within the path of the project, or provide an
     *                 absolut path (verification required).
     *
     */
    public Image(String fileName) {
        this.img = new Imagen(fileName);
        this.pixelMatrix = new int[img.getAncho()][img.getAlto()];
        this.pixelMatrix = this.img.getPixeles();
        //DEBUG
        System.out.println(img.getAncho() + " x " + img.getAlto());
    }

    public Image(int[][] pixelMatrix, int bits, int ancho, int alto) {
        this.img = new Imagen(pixelMatrix, bits, ancho, alto);
        this.pixelMatrix = pixelMatrix;
        //DEBUG
        System.out.println(img.getAncho() + " x " + img.getAlto());
    }

    /**
     * Loads the image data into pixel lists from it's pixelMatrix, to be stored
     * according to it's color as Contour Lines.
     */
    public void getContourLines() {
        this.contourLines = new ContourLines();
        this.listaColores = new LinkedList<Integer>();

        int currentColor;
        for (int j = 0; j < this.img.getAlto(); j++) {
            for (int i = 0; i < this.img.getAncho(); i++) {
                boolean existe = false;
                ListIterator<Integer> it = this.listaColores.listIterator();
                //If not a white value for current pixel
                if (this.pixelMatrix[i][j] != 255) {
                    if (!it.hasNext()) {
                        /*
                         * Todavía no existe ninguna curva. Si no existe la
                         * lista con el color, agregar una nueva lista para ese
                         * color, además del pixel en curso.
                         */
                        existe = false;
                    }
                    else {
                        while (it.hasNext()) {
                            currentColor = it.next();
                            if (this.pixelMatrix[i][j] == currentColor) {
                                //El color ya se había agregado anteriormente
                                existe = true;
                                break;
                            }
                        }
                    }
                    if (!existe) {
                        /*
                         * Si no existe la lista con el color, agregar una nueva
                         * lista para ese color, además del pixel en curso.
                         */
                        this.listaColores.add(this.pixelMatrix[i][j]);
                        this.contourLines.contourLines.add(
                                new ContourLine(
                                this.pixelMatrix[i][j],
                                new Pixel(i, j, this.pixelMatrix[i][j])));
                    }
                    else {
                        /*
                         * Si ya existe la lista con el color, agregar el pixel
                         * en curso a su lista correspondiente.
                         */
                        this.getCurvaByColor(this.pixelMatrix[i][j]).
                                add(
                                new Pixel(i, j, this.pixelMatrix[i][j]));
                    }
                }
            }
        }
    }

    /**
     * Returns a curve as a list of pixels, given it's color attribute; if no
     * such curve exists, it returns null.
     *
     * @param color The color of the desired curve
     * @return The curve of such color, or null if non exists.
     */
    private ContourLine getCurvaByColor(int color) {
        for (ContourLine c : this.contourLines.contourLines) {
            if (c.getIdColor() == color) {
                return c;
            }
        }
        return null;
    }

    /**
     * Calculates the minimun distance that exists between two points of
     * different curves, and therefore, the samplingDistance required to sample
     * curves, as defined by half the minimum distance.
     *
     */
    public void calculateMinDistance() {
        double minDist = 100000, d;
        for (ContourLine c1 : this.contourLines.contourLines) {
            for (ContourLine c2 : this.contourLines.contourLines) {
                if (c1.getIdColor() != c2.getIdColor()) {
                    for (Pixel p1 : c1) {
                        for (Pixel p2 : c2) {
                            d = Image.getEuclideanDistance(p1, p2);
                            if (d < minDist) {
                                minDist = d;
                            }
                        }
                    }
                }
            }
        }
        this.minDistance = minDist;
        this.samplingDistance = minDist / 2;
        //DEBUG
        System.out.println("MINDISTANCE: " + minDist);
        System.out.println("SAMPLINGDISTANCE: " + this.samplingDistance);
    }

    public void sampleContourLines() {
        Mask m = new Mask(img.getAncho(), img.getAlto());
        Pixel next, current, p, anterior;
        LinkedList<Pixel> past;
        boolean flag1 = false;
        for (ContourLine c : this.contourLines.contourLines) {
            c.sampleContourLine(img.getAncho(), img.getAlto(), this.samplingDistance);
        }
    }

    public void calculateSampledMatrix() {
        this.sampledMatrix = new int[img.getAncho()][img.getAlto()];
        //Fill with white
        for (int j = 0; j < this.img.getAlto(); j++) {
            for (int i = 0; i < this.img.getAncho(); i++) {
                sampledMatrix[i][j] = 255;
            }
        }
        //Fill in on the matrix the pixels for each curve where they should go
        for (ContourLine c : this.contourLines.contourLines) {
            for (Pixel p : c.sampledCurva) {
                sampledMatrix[p.getX()][p.getY()] = p.getColor();
            }
        }
    }

    public int[][] getSampledMatrix() {
        return sampledMatrix;
    }

    /**
     * Calculates and returns the euclidean distance between two given pixels.
     *
     * @param p1 A pixel
     * @param p2 Another pixel
     * @return The euclidean distance between such pixels.
     */
    static double getEuclideanDistance(Pixel p1, Pixel p2) {
        return Math.sqrt(
                Math.pow((p1.getX() - p2.getX()), 2)
                + Math.pow((p1.getY() - p2.getY()), 2));
    }

    public void writeImage() {
        this.writeImage("salida");
    }

    public void writeImage(String fileName) {
        Imagen im = new Imagen(this.pixelMatrix, 8, this.img.getAncho(), this.img.getAlto());
        im.escribeImagen(fileName + ".bmp");
    }

    public void write_tsin() {
        try {
            FileWriter fw = new FileWriter("salida.tsin");
            PrintWriter os = new PrintWriter(fw);
            int len = 0;
            //Counts all pixels of each curve
            for (ContourLine c : this.contourLines.contourLines) {
                len += c.sampledCurva.size();
            }
            // prints the tsin file header:
            os.println(len);
            for (ContourLine c : this.contourLines.contourLines) {
                //Flattens all pixels
                //c.flatPixels();
                for (Pixel p : c.sampledCurva) {
                    //String representation for file storage
                    os.println(p.toFile());
                }
            }
            os.close();
            fw.close();
        }
        catch (Exception e) {
        }
    }

    /**
     * Calculates the heights for each contour line, setting a max height as the
     * minimum value between the height and width of the 2-D original image;
     * assigning to each contour line a height proportional to the area of its
     * bounding box. The height for each contour line is proportional to the
     * area it occupies.
     *
     */
    public void calculateHeights() {
        this.maxHeight = Math.min(this.img.getAncho(), this.img.getAlto());
        int maxArea = 0;
        //Finds biggest area (lowest contour line)
        for (ContourLine c : this.contourLines.contourLines) {
            c.calculateBoxArea();
            if (c.getBoundingBox() > maxArea) {
                maxArea = c.getBoundingBox();
            }
        }
        System.out.println("Max Area is " + maxArea);
        //The height for each contour line is proportional to the area
        //it occupies
        for (ContourLine c : this.contourLines.contourLines) {
            c.height = (c.getBoundingBox() * this.maxHeight) / maxArea;
            c.risePixels();
        }
    }

    public void imprimeAlturas() {
        for (ContourLine c : this.contourLines.contourLines) {
            System.out.println("Curva " + c.getIdColor()
                    + " @(" + c.height + ")");
        }
    }
}
