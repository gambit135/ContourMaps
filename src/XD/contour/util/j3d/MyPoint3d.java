package XD.contour.util.j3d;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

/**
 *
 * @author Alejandro Tëllez G. <java.util.fck@hotmail.com>
 */
public class MyPoint3d extends Point3d {

    /**
     * The color, as represented on gray-scale
     */
    int color;
    Color3f color3f;

    /**
     * Parses the current color from gray-scale to Color3f, initializing it.
     */
    private void parseColor() {
        Float d = Float.parseFloat(Integer.toString(color));
        
        d /= 256;
        
        float r = d;
        float g = d;
        float b = d;
        
        r = Math.abs(1-d);
        g = Math.abs(1-d);
        b = Math.abs(1-d);
        
        if (r < .6){
            r *= 2.5;
        }
        if(g < .3){
            g *= 3;
        }
        if (b < .25){
            b *= 4;
        }
        
        //DEBUG
        System.out.println("Casted color: " + d);
        this.color3f = new Color3f(r, g, b);
    }

    public MyPoint3d(double d, double d1, double d2, int color) {
        super(d, d1, d2);
        this.color = color;
        this.parseColor();
    }

    public MyPoint3d() {
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        this.parseColor();
    }

    public Color3f getColor3f() {
        return color3f;
    }

    public void setColor3f(Color3f color3f) {
        this.color3f = color3f;
    }
}
