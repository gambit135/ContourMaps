package XD.contour.util.j3d;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;
//import java.applet.Applet;
import javax.swing.JApplet;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

/**
 *
 * @author Alejandro Tëllez G. <java.util.fck@hotmail.com>
 */
public class SemiReconstruction extends JApplet {

    int pixelMatrix[][];

    public SemiReconstruction(int[][] pixelMatrix) {
        this.pixelMatrix = pixelMatrix;
        setLayout(new BorderLayout());
        GraphicsConfiguration config =
                SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas3D = new Canvas3D(config);
        add("Center", canvas3D);

        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

        vpTrans = simpleU.getViewingPlatform().getViewPlatformTransform();

        BranchGroup scene = createSceneGraph();

        // SimpleUniverse is a Convenience Utility class

        // This will move the ViewPlatform back a bit so the
        // objects in the scene can be viewed.

        //simpleU.getViewingPlatform().setNominalViewingTransform();


        simpleU.addBranchGraph(scene);
        //Frame frame = new MainFrame(this, 600, 400);
    }

    public BranchGroup createSceneGraph() {
        // Create the root of the branch graph
        BranchGroup objRoot = new BranchGroup();

        TransformGroup objRotate = new TransformGroup();
        objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

        objRoot.addChild(objRotate);
        objRotate.addChild(new Triangulacion3DRainbow(this.pixelMatrix));
        //objRotate.addChild(new ColorCube(.1));
        //objRoot.addChild(new Axis());

        BoundingSphere mouseBounds = new BoundingSphere(new Point3d(), 1000.0);
        //For allowing rotation with the mouse
        
        MouseRotate myMouseRotate = new MouseRotate();
        myMouseRotate.setTransformGroup(objRotate);
        myMouseRotate.setSchedulingBounds(new BoundingSphere());
        objRoot.addChild(myMouseRotate);
        
        MouseTranslate myMouseTranslate = new MouseTranslate(MouseBehavior.INVERT_INPUT);
        myMouseTranslate.setTransformGroup(vpTrans);
        myMouseTranslate.setSchedulingBounds(mouseBounds);
        objRoot.addChild(myMouseTranslate);

        //*************************
        //ZOOMING IN AND OUT WITH THE MOUSE!!!!

        //Mouse bounds

        MouseWheelZoom myMouseZoom = new MouseWheelZoom(MouseBehavior.INVERT_INPUT);
        myMouseZoom.setTransformGroup(vpTrans);
        myMouseZoom.setSchedulingBounds(mouseBounds);
        objRoot.addChild(myMouseZoom);
        //****************************/

        // Let Java 3D perform optimizations on this scene graph.
        objRoot.compile();

        return objRoot;
    } // end of CreateSceneGraph method of MouseRotateApp
    // Create a simple scene and attach it to the virtual universe
    //Vptrans
    TransformGroup vpTrans = null;

    public SemiReconstruction() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config =
                SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas3D = new Canvas3D(config);
        add("Center", canvas3D);

        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

        vpTrans = simpleU.getViewingPlatform().getViewPlatformTransform();

        BranchGroup scene = createSceneGraph();

        // SimpleUniverse is a Convenience Utility class

        // This will move the ViewPlatform back a bit so the
        // objects in the scene can be viewed.

        //simpleU.getViewingPlatform().setNominalViewingTransform();


        simpleU.addBranchGraph(scene);
    } // end of MouseRotateApp (constructor)
    //  The following allows this to be run as an application
    //  as well as an applet

    public static void main(String[] args) {
        System.out.print("MouseRotateApp.java \n- a demonstration of using the MouseRotate ");
        System.out.println("utility behavior class to provide interaction in a Java 3D scene.");
        System.out.println("Hold the mouse button while moving the mouse to make the cube rotate.");
        System.out.println("This is a simple example progam from The Java 3D API Tutorial.");
        System.out.println("The Java 3D Tutorial is available on the web at:");
        System.out.println("http://java.sun.com/products/java-media/3D/collateral");
        Frame frame = new MainFrame(new SemiReconstruction(), 600, 400);
    } // end of main (method of MouseRotateApp)

    public static void main2(int[][] pixelMatrix) {
        Frame f = new MainFrame(new SemiReconstruction(pixelMatrix), 600, 400);
    }
}
