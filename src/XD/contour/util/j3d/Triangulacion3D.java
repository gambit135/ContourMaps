package XD.contour.util.j3d;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.StringTokenizer;
import javax.media.j3d.Geometry;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

/**
 * Reads the triangulation as calculated by the Java Delaunay Triangulation API
 * (jdt), and returns an array of triangles as a Shape3D.
 *
 * @author Alejandro Tëllez G. <java.util.fck@hotmail.com>
 */
public class Triangulacion3D extends Shape3D {

    public Triangulacion3D(int[][] pixelMatrix) {
        this.setGeometry(this.createGeometry(pixelMatrix));
    }

    private Geometry createGeometry(int[][] pixelMatrix) {
        return readSmfFile("triangulacion.smf", pixelMatrix);
    }

    Geometry readSmfFile(String fileName, int[][] pixelMatrix) {
        //Los puntos leídos del archivo, como vértices con la bandera 'v'
        LinkedList<MyPoint3d> puntos = new LinkedList<MyPoint3d>();
        LinkedList<Triangle_d> triangulos = new LinkedList<Triangle_d>();
        //IndexedTriangleArray triangles = null;
        TriangleArray triangles = null;

        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader is = new BufferedReader(fr);
            String s = is.readLine();

            while (s.charAt(0) != 'v') {
                s = is.readLine();
            }
            //Fill in the list of points
            while (s != null && s.charAt(0) == 'v') {
                StringTokenizer st = new StringTokenizer(s);
                //Jump the first 'f' character identifier
                st.nextToken();
                //Create and add the found point to the list
                MyPoint3d p = new MyPoint3d();

                double d1 = new Double(st.nextToken());
                double d2 = new Double(st.nextToken());
                double d3 = new Double(st.nextToken());

                //Paso de la muerte.
                //Bórrame
                int color = Integer.parseInt(st.nextToken());


                int i1 = (int) d1;
                int i2 = (int) d2;
                int i3 = (int) d3;

                d1 = d1 / 250;
                d2 = d2 / 250;
                d3 = d3 / 500;

                //Asigna coordenadas dobles
                p.setX(d1);
                p.setY(d2);
                p.setZ(d3);
                //Paso de la muerte
                p.setColor(color);

                //Asigna al Point3d el color original almacenado en la imagen
                //Se convierte implícitamente en Color3f
                //p.setColor(pixelMatrix[i1][i2]);

                puntos.add(p);
                //DEBUG
                System.out.println(p.toString()
                        + " " + p.getColor() + " " + p.getColor3f().toString());
                s = is.readLine();
            }
            //Recorrer todos los triángulos, para saber cuántos son
            //y cuáles son los puntos que conforman a cada uno.
            while (s != null && s.charAt(0) == 'f') {
                StringTokenizer st = new StringTokenizer(s);
                //Jump the first 'f' character identifier
                st.nextToken();
                //Add to the triangle array, from 3 to 3 coordinates
                Triangle_d t = new Triangle_d();
                int i1 = new Integer(Integer.parseInt(st.nextToken()));
                int i2 = new Integer(Integer.parseInt(st.nextToken()));
                int i3 = new Integer(Integer.parseInt(st.nextToken()));

                t.setP1(puntos.get(i1 - 1));
                t.setP2(puntos.get(i2 - 1));
                t.setP3(puntos.get(i3 - 1));

                triangulos.add(t);
                s = is.readLine();
            }
        }
        catch (Exception e) {
            System.out.println(e.toString() + "\n" + e.getLocalizedMessage());
        }
        //DEBUG
        System.out.println("No. of Points: " + puntos.size());
        System.out.println("No. of Triangles: " + triangulos.size());

        //Draw triangles to ensure visibility from anverse and reverse
        triangles = new TriangleArray(
                triangulos.size() * 3 * 2,
                TriangleArray.COORDINATES | TriangleArray.COLOR_3);
        int contador = 0;
        for (Triangle_d t : triangulos) {
            MyPoint3d p;

            // Obverse
            p = (MyPoint3d) t.p1;
            triangles.setCoordinate(contador, t.p1);
            triangles.setColor(contador, p.getColor3f());
            contador++;

            p = (MyPoint3d) t.p2;
            triangles.setCoordinate(contador, t.p2);
            triangles.setColor(contador, p.getColor3f());
            contador++;

            p = (MyPoint3d) t.p3;
            triangles.setCoordinate(contador, t.p3);
            triangles.setColor(contador, p.getColor3f());
            contador++;

            //Reverse
            p = (MyPoint3d) t.p3;
            triangles.setCoordinate(contador, t.p3);
            triangles.setColor(contador, p.getColor3f());
            contador++;

            p = (MyPoint3d) t.p2;
            triangles.setCoordinate(contador, t.p2);
            triangles.setColor(contador, p.getColor3f());
            contador++;

            p = (MyPoint3d) t.p1;
            triangles.setCoordinate(contador, t.p1);
            triangles.setColor(contador, p.getColor3f());
            contador++;
        }
        return triangles;
    }

    public TriangleArray readSmfFile(String fileName, int i) {
        //Los puntos leídos del archivo, como vértices con la bandera 'v'
        LinkedList<Point3d> puntos = new LinkedList<Point3d>();
        LinkedList<Triangle_d> triangulos = new LinkedList<Triangle_d>();
        //IndexedTriangleArray triangles = null;
        TriangleArray triangles = null;

        int c = 0;
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader is = new BufferedReader(fr);
            String s = is.readLine();

            while (s.charAt(0) != 'v') {
                s = is.readLine();
            }
            //Fill in the list of points
            while (s != null && s.charAt(0) == 'v') {
                StringTokenizer st = new StringTokenizer(s);
                //Jump the first 'f' character identifier
                st.nextToken();
                //Create and add the found point to the list
                Point3d p = new Point3d();
                p.setX(new Double(st.nextToken()).doubleValue());
                p.setY(new Double(st.nextToken()).doubleValue());
                p.setZ(new Double(st.nextToken()).doubleValue());
                puntos.add(p);
                s = is.readLine();
            }
            //Recorrer todos los triángulos, para saber cuántos son
            //y cuáles son los puntos que conforman a cada uno.
            while (s != null && s.charAt(0) == 'f') {
                StringTokenizer st = new StringTokenizer(s);
                //Jump the first 'f' character identifier
                st.nextToken();
                //Add to the triangle array, from 3 to 3 coordinates
                Triangle_d t = new Triangle_d();
                int i1 = new Integer(Integer.parseInt(st.nextToken()));
                int i2 = new Integer(Integer.parseInt(st.nextToken()));
                int i3 = new Integer(Integer.parseInt(st.nextToken()));
                t.setP1(puntos.get(i1 - 1));
                t.setP2(puntos.get(i2 - 1));
                t.setP3(puntos.get(i3 - 1));
                triangulos.add(t);
                s = is.readLine();
                c++;
            }
        }
        catch (Exception e) {
            System.out.println(e.toString() + "\n" + e.getLocalizedMessage());
        }
        int contador = 0;
        //DEBUG
        System.out.println("No. of Points: " + puntos.size());
        System.out.println("No. of Triangles: " + triangulos.size());
        triangles = new TriangleArray(triangulos.size() * 3, TriangleArray.COORDINATES);
        for (Triangle_d t : triangulos) {
            triangles.setCoordinate(contador, t.p1);
            contador++;
            triangles.setCoordinate(contador, t.p2);
            contador++;
            triangles.setCoordinate(contador, t.p3);
            contador++;
        }
        return triangles;
    }
}
