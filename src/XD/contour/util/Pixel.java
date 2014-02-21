package XD.contour.util;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class Pixel {

    /**
     * Pixel's X coordinate
     */
    private int x;
    /**
     * Pixel's Y coordinate
     */
    private int y;
    /**
     * Pixel's Z coordinate. Used for triangulation and reconstruction.
     */
    private double z;
    /**
     * Pixel's int color value
     */
    private int color;

    public Pixel(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Pixel() {
    }
    

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return this.getColor()
                + "@("
                + this.getX()
                + ", "
                + this.getY() + ")";
    }

    /**
     * Regresa una cadena para ser escrita en un archivo.
     * 
     * Paso de la muerte, quitar color si todo se va al diablo.
     * Restáurame.
     * 
     * @return 
     */
    public String toFile() {
        return (x + " " + y + " " + z + " " + color);
    }
}
