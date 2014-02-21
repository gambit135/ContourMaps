package XD.contour;

import XD.contour.util.Image;
import XD.contour.util.j3d.Reconstruction;
import XD.contour.util.j3d.SemiReconstruction;
import jdt.gui.DelaunayPreview;

/**
 * Class created to test functionality of objects and such.
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class Test1 {

    public static void main(String args[]) {
        Image i = new Image("curvas1.bmp");
        i.getContourLines();
        i.calculateMinDistance();
        i.sampleContourLines();
        i.writeImage();
        i.calculateHeights();
        i.write_tsin();
        i.calculateSampledMatrix();
        Image sampledImage = new Image(i.getSampledMatrix(), 8,
                                       i.img.getAncho(), i.img.getAlto());
        sampledImage.writeImage("sampledCurvas");
        DelaunayPreview f = new DelaunayPreview("salida.tsin");
        f.setSize(i.img.getAncho(), i.img.getAlto());
        f.setVisible(true);
        f.myWriteSmfFile("triangulacion.smf");
        Reconstruction.main2(i.pixelMatrix);
        //Reconstruction using random colors A.K.A. Rainbow :)
        SemiReconstruction.main2(i.pixelMatrix);
        i.imprimeAlturas();
    }
}
