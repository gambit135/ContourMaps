package XD.contour.util.j3d;

import javax.vecmath.Point3d;

/**
 * A triangle, defined by 3 Point3d points.
 *
 * @author Alejandro Tëllez G. <java.util.fck@hotmail.com>
 */
public class Triangle_d {

    Point3d p1, p2, p3;

    public Point3d getP1() {
        return p1;
    }

    public void setP1(Point3d p1) {
        this.p1 = p1;
    }

    public Point3d getP2() {
        return p2;
    }

    public void setP2(Point3d p2) {
        this.p2 = p2;
    }

    public Point3d getP3() {
        return p3;
    }

    public void setP3(Point3d p3) {
        this.p3 = p3;
    }

    public Triangle_d() {
    }

    public Triangle_d(Point3d p1, Point3d p2, Point3d p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
}
