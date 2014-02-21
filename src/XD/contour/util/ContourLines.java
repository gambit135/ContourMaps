package XD.contour.util;

import java.util.LinkedList;

/**
 * This class abstracts a list of lists of pixels (i.e., a list of curves).
 *
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 */
public class ContourLines {

    /**
     * A list of lists of pixels.
     */
    public LinkedList<ContourLine> contourLines;

    public ContourLines() {
        this.contourLines = new LinkedList<ContourLine>();
    }
    @Override
    public String toString(){
        String s = "";
        for(ContourLine lp: this.contourLines){
            s.concat(lp.toString());
        }
        return s;
    }
    
}
