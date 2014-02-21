/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XD.contour.util;

import java.util.LinkedList;

/**
 * Used for mask applying for neighbor search. Use one instance of this class
 * for every curve.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class Mask {

    int sizeX;
    int sizeY;

    public Mask(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    /**
     * Returns the first pixel found on a 4-mask pattern applied to the given
     * pixel depending on it's position on the matrix; if no point exists on
     * such pattern, null is returned.
     *
     * @param current The given or current pixel.
     * @param c       The curve on which this pixel exists.
     * @return The first pixel found on the 4-mask pattern, or null if none
     *         exists.
     */
    Pixel mask4(Pixel current, ContourLine c, Pixel anterior, LinkedList<Pixel> past) {
        //DEBUG
        System.out.println("Aplicando máscara4 a curva " + c.getIdColor());
        System.out.println("current: " + current.toString());
        Pixel p;
        //Check for 1 existence
        //If there's at least one pixel above
        if (current.getY() + 1 < this.sizeY) {
            //See if the pixel above belongs to the current curve.            
            p = c.getPixel(current.getX(), current.getY() + 1);
            if (p != null) {
                if (p != anterior) {
                    if (!past.contains(p)) {
                        //DEBUG
                        System.out.println("Going up");
                        return p;
                    }
                }
            }
        }
        //Check for 3 existence
        //If there's at least one pixel to the left
        if (current.getX() > 0) {
            //See if the pixel to the left belongs to the current curve.
            p = c.getPixel(current.getX() - 1, current.getY());
            if (p != null) {
                if (p != anterior) {
                    if (!past.contains(p)) {
                        //DEBUG
                        System.out.println("Going left");
                        return p;
                    }
                }
            }
        }
        //Check for 5 existence
        //If there's at least one pixel to the right
        if (current.getX() + 1 < this.sizeX) {
            //See if the pixel to the right belongs to the current curve.
            p = c.getPixel(current.getX() + 1, current.getY());
            if (p != null) {
                if (p != anterior) {
                    if (!past.contains(p)) {
                        //DEBUG
                        System.out.println("Going right");
                        return p;
                    }
                }
            }
        }
        //Check for 7 existence
        //If there's at least one pixel below
        if (current.getY() > 0) {
            //See if the pixel below belongs to the current curve.
            p = c.getPixel(current.getX(), current.getY() - 1);
            if (p != null) {
                if (p != anterior) {
                    if (!past.contains(p)) {
                        //DEBUG
                        System.out.println("Going down");
                        return p;
                    }
                }
            }
        }
        //If no pixel with the current pattern has been found return null.
        return null;
    }

    /**
     * Returns the first pixel found on a 8-mask pattern applied to the given
     * pixel depending on it's position on the matrix; if no point exists on
     * such pattern, null is returned.
     *
     * @param current The given or current pixel.
     * @param c       The curve on which this pixel exists.
     * @return The first pixel found on the 8-mask pattern, or null if none
     *         exists.
     */
    Pixel mask8(Pixel current, ContourLine c, Pixel anterior, LinkedList<Pixel> past) {
        //DEBUG
        System.out.println("Aplicando máscara8 a curva " + c.getIdColor());
        System.out.println("current: " + current.toString());
        Pixel p;
        //Check for 6 existence
        //If there's at least one pixel below to the left
        if (current.getY() > 0 && current.getX() > 0) {
            //See if the pixel below to the left belongs to the current curve.
            p = c.getPixel(current.getX() - 1, current.getY() - 1);
            if (p != null) {
                if (p != anterior) {
                    if (!past.contains(p)) {
                        //DEBUG
                        System.out.println("Going 6");
                        return p;
                    }
                }
            }
        }
        //Check for 0 existence
        //If there's at least one pixel above to the left
        if (current.getY() + 1 < this.sizeY && current.getX() > 0) {
            //See if the pixel above to the left belongs to the current curve.            
            p = c.getPixel(current.getX() - 1, current.getY() + 1);
            if (p != null) {
                if (p != anterior) {
                    if (!past.contains(p)) {
                        //DEBUG
                        System.out.println("Going 0");
                        return p;
                    }
                }
            }
        }
        //Check for 2 existence
        //If there's at least one pixel above to the right
        if (current.getY() + 1 < this.sizeY && current.getX() + 1 < this.sizeX) {
            //See if the pixel above to the right belongs to the current curve.
            p = c.getPixel(current.getX() + 1, current.getY() + 1);
            if (p != null) {
                if (p != anterior) {
                    if (!past.contains(p)) {
                        //DEBUG
                        System.out.println("Going 2");
                        return p;
                    }
                }
            }
        }

        //Check for 8 existence
        //If there's at least one pixel below to the right
        if (current.getY() > 0 && current.getX() + 1 < this.sizeX) {
            //See if the pixel below to the right belongs to the current curve.
            p = c.getPixel(current.getX() + 1, current.getY() - 1);
            if (p != null) {
                if (p != anterior) {
                    if (!past.contains(p)) {
                        //DEBUG
                        System.out.println("Going 8");
                        return p;
                    }
                }
            }
        }
        //If no pixel with the current pattern has been found return null.
        return null;
    }

    /**
     * Deletes all neighbourgs from current pixel, except the significant ones,
     * for assuring a 1-pixel thickness for the current curve.
     *
     * @param c        The curve
     * @param anterior The previous pixel
     * @param current  The current pixel
     * @param next     The next pixel
     * @return The updated curve
     */
    ContourLine yourFriendlyNeighborhoodSpiderMan(ContourLine c, Pixel anterior, Pixel current, Pixel next) {
        //Remove all 8 neighborgs from current, except previous and next

        //Delete 0
        //If there's at least one pixel above to the left
        if (current.getY() + 1 < this.sizeY && current.getX() > 0) {
            //See if the pixel above to the left belongs to the current curve.            
            Pixel p = c.getPixel(current.getX() - 1, current.getY() + 1);
            c = this.deleteNeighbor(p, anterior, next, c);
        }

        //Delete 1
        //If there's at least one pixel above
        if (current.getY() + 1 < this.sizeY) {
            //See if the pixel above belongs to the current curve.            
            Pixel p = c.getPixel(current.getX(), current.getY() + 1);
            c = this.deleteNeighbor(p, anterior, next, c);
        }
        //Delete 2
        //If there's at least one pixel above to the right
        if (current.getY() + 1 < this.sizeY && current.getX() + 1 < this.sizeX) {
            //See if the pixel above to the right belongs to the current curve.
            Pixel p = c.getPixel(current.getX() + 1, current.getY() + 1);
            c = this.deleteNeighbor(p, anterior, next, c);
        }
        //Delete 3
        //If there's at least one pixel to the left
        if (current.getX() > 0) {
            //See if the pixel to the left belongs to the current curve.
            Pixel p = c.getPixel(current.getX() - 1, current.getY());
            c = this.deleteNeighbor(p, anterior, next, c);
        }
        //Delete 5
        //If there's at least one pixel to the right
        if (current.getX() + 1 < this.sizeX) {
            //See if the pixel to the right belongs to the current curve.
            Pixel p = c.getPixel(current.getX() + 1, current.getY());
            c = this.deleteNeighbor(p, anterior, next, c);
        }
        //Delete 6
        //If there's at least one pixel below to the left
        if (current.getY() > 0 && current.getX() > 0) {
            //See if the pixel below to the left belongs to the current curve.
            Pixel p = c.getPixel(current.getX() - 1, current.getY() - 1);
            c = this.deleteNeighbor(p, anterior, next, c);
        }
        //Delete 7
        //If there's at least one pixel below
        if (current.getY() > 0) {
            //See if the pixel below belongs to the current curve.
            Pixel p = c.getPixel(current.getX(), current.getY() - 1);
            c = this.deleteNeighbor(p, anterior, next, c);
        }
        //Delete 8
        //If there's at least one pixel below to the right
        if (current.getY() > 0 && current.getX() + 1 < this.sizeX) {
            //See if the pixel below to the right belongs to the current curve.
            Pixel p = c.getPixel(current.getX() + 1, current.getY() - 1);
            c = this.deleteNeighbor(p, anterior, next, c);
        }
        return c;
    }

    /**
     * Sends the pixel to delete, and the previous and next one for comparing,
     * and making sure p is not a significant pixel and can be deleted.
     *
     * @param p        The pixel to be deleted
     * @param anterior The previous pixel
     * @param next     The next pixel
     * @param c        The current curve
     * @return The updated curve
     */
    ContourLine deleteNeighbor(Pixel p, Pixel anterior, Pixel next, ContourLine c) {
        if (p != null) {
            if (p != anterior && p != next) {
                //DEBUG
                System.out.println("Trying to delete a 8 neighbor");
                boolean remove = c.remove(p);
                if (remove) {
                    System.out.println("deleting a 8 neighbor");
                }
            }
        }
        return c;
    }
}
