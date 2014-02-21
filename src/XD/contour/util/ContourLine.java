package XD.contour.util;

import java.util.LinkedList;

/**
 * Represents a list comprised by pixels, representing a curve of a certain
 * color as a whole entity.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class ContourLine extends LinkedList<Pixel> {

    /**
     * The color identifier of this curve.
     */
    private int idColor;
    /**
     * The height for this curve's pixels
     */
    double height;
    /**
     * The sampled version of this curve.
     */
    public ContourLine sampledCurva;
    /**
     * The box square area bounding this curve.
     */
    private int boundingBox;

    /**
     *
     * @return This curve's bounding box area.
     */
    public int getBoundingBox() {
        return boundingBox;
    }

    /**
     *
     * @return The calculated box area of this curve.
     */
    void calculateBoxArea() {
        int x0 = this.getMinXPixel().getX();
        int x1 = this.getMaxXPixel().getX();
        int y0 = this.getMinYPixel().getY();
        int y1 = this.getMaxYPixel().getY();
        this.boundingBox = ((x1 - x0) * (y1 - y0));

    }

    /**
     *
     *
     * @return the pixel with the biggest X coordinate within this list.
     */
    public Pixel getMaxXPixel() {
        Pixel control = this.peek();
        for (Pixel p : this) {
            if (p.getX() > control.getX()) {
                control = p;
            }
        }
        return control;
    }

    /**
     *
     *
     * @return the pixel with the biggest Y coordinate within this list.
     */
    public Pixel getMaxYPixel() {
        Pixel control = this.peek();
        for (Pixel p : this) {
            if (p.getY() > control.getY()) {
                control = p;
            }
        }
        return control;
    }

    /**
     *
     *
     * @return the pixel with the smallest X coordinate within this list.
     */
    public Pixel getMinXPixel() {
        Pixel control = this.peek();
        for (Pixel p : this) {
            if (p.getX() < control.getX()) {
                control = p;
            }
        }
        return control;
    }

    /**
     *
     *
     * @return the pixel with the smallest Y coordinate within this list.
     */
    public Pixel getMinYPixel() {
        Pixel control = this.peek();
        for (Pixel p : this) {
            if (p.getY() < control.getY()) {
                control = p;
            }
        }
        return control;
    }

    public void applyMask() {
        Pixel p = this.getMaxXPixel();
    }

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    public ContourLine(int idColor) {
        this.idColor = idColor;
    }

    /**
     * Constructor, taking a pixel to be the first to be added to the recently
     * constructed list.
     *
     * @param idColor The color of the list to be created.
     * @param p       The first pixel to be added to this list.
     */
    public ContourLine(int idColor, Pixel p) {
        this.idColor = idColor;
        this.add(p);
    }

    /**
     * Returns a pixel of this curve, given it's X and Y values; if no such
     * pixel exists, null is returned.
     *
     * @param x The pixel's X value.
     * @param y The pixel's Y value.
     * @return The pixel with such X and Y values, or null if none exists.
     */
    Pixel getPixel(int x, int y) {
        for (Pixel p : this) {
            if (p.getX() == x && p.getY() == y) {
                return p;
            }
        }
        return null;
    }

    public void printCurva() {
        int contador = 1;
        for (Pixel p : this) {
            System.out.println(p.getColor()
                    + "@ (" + p.getX() + ", " + p.getY() + ") "
                    + contador + " of " + this.size());
            contador++;
        }
    }

    /**
     * Assigns a value of zero to all the pixels' z coordinate of this curve.
     */
    void flatPixels() {
        for (Pixel p : this) {
            p.setZ(0);
        }
    }

    void risePixels() {
        for (Pixel p : this) {
            p.setZ(this.height);
        }
    }

    /**
     * Moves along the contour line, starting at the pixel wit the biggest
     * x-value, and moving according to a mask applied to it's current position
     * (4 and 8 neighborgs).
     *
     * @param width            The total width of the image, for bounds
     *                         detection
     * @param height           The total height of the image, for bounds
     *                         detection
     * @param samplingDistance The sampling distance, as calculated and defined
     *                         as half the minimum distance.
     */
    void sampleContourLine(int width, int height, double samplingDistance) {
        Mask m = new Mask(width, height);
        Pixel next, current, pivot, past;
        LinkedList<Pixel> iterated;
        boolean flag1 = false;
        this.sampledCurva = new ContourLine(this.getIdColor());
        pivot = this.getMaxXPixel();
        this.sampledCurva.add(pivot);
        //DEGUG
        System.out.println("MaxXPixel: " + pivot.toString());
        iterated = new LinkedList<Pixel>();
        past = null;
        do {
            current = pivot;
            do {
                next = m.mask4(current, this, past, iterated);
                if (next == null) {
                    next = m.mask8(current, this, past, iterated);
                }
                if (next != null) {
                    if (iterated.contains(next)) {
                        flag1 = true;
                        break;
                    }
                    else {
                        iterated.add(current);
                        double dist = Image.getEuclideanDistance(pivot, next);
                        if (dist >= samplingDistance) {
                            this.sampledCurva.add(next);

                            past = current;
                            current = next;
                            pivot = current;
                        }
                        else {
                            past = current;
                            current = next;
                        }
                    }
                }
                else {
                    flag1 = true;
                    break;
                }
            }
            while (true);
            if (flag1) {
                break;
            }
        }
        while (true);
    }
}
