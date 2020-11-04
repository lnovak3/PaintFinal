package shapes;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 * used to draw polygons on the canvas
 *
 * @author Logan
 */
public class MyPolygon extends MyShapes {

    //data for polygon
    private GraphicsContext graphicsContext;
    private Color colorStroke;
    private Color colorFill;
    private double lineWidth;
    private ArrayList<Double> xPoints = new ArrayList<Double>();
    private ArrayList<Double> yPoints = new ArrayList<Double>();

    /**
     * constructor for MyPolygon object
     */
    public MyPolygon() {

    }

    //following 4 methods to set data for MyPolygon
    /**
     * sets graphics context to be drawn on
     *
     * @param graphicsContext graphics context of canvas from main program to be
     * drawn on
     */
    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    /**
     * sets color of polygon
     *
     * @param colorPicker color picker from main program, color extracted to set
     * color of polygon
     */
    public void setColorPicker(ColorPicker colorPicker) {
        colorStroke = colorPicker.getValue();
    }

    /**
     * sets fill of polygon
     *
     * @param colorPickerFill color picker from main program, color extracted to
     * set fill of polygon
     */
    public void setColorPickerFill(ColorPicker colorPickerFill) {
        colorFill = colorPickerFill.getValue();
    }

    /**
     * sets width of line of polygon
     *
     * @param lineWidth double from line width field in main program
     */
    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    //to add points to the polygon as it is being drawn
    /**
     * used to add points to a polygon as the user drags or stops dragging
     *
     * @param x x value where user is dragging or has stopped dragging
     * @param y y value where user is dragging or has stopped dragging
     */
    public void addPoint(double x, double y) {
        xPoints.add(x);
        yPoints.add(y);
    }

    //puts polygon on canvas
    /**
     * used to draw a polygon onto a canvas
     */
    public void draw() {
        //sets stroke, fill, and line width of polygon
        graphicsContext.setStroke(colorStroke);
        graphicsContext.setFill(colorFill);
        graphicsContext.setLineWidth(lineWidth);

        //draw polygon onto canvas using it's arrays of points
        double[] polyX = new double[xPoints.size()];
        double[] polyY = new double[yPoints.size()];
        for (int i = 0; i < xPoints.size(); i++) {
            polyX[i] = xPoints.get(i);
            polyY[i] = yPoints.get(i);
        }
        graphicsContext.strokePolygon(polyX, polyY, polyX.length);
        graphicsContext.fillPolygon(polyX, polyY, polyX.length);
    }

}
