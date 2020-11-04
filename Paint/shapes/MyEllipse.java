package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * used to draw an ellipse on a canvas
 * @author Logan
 */
public class MyEllipse extends MyShapes{
    
    //data for ellipse
    private GraphicsContext graphicsContext;
    private double centerX, centerY, endX, endY, lineWidth;
    private Color colorStroke;
    private Color colorFill;
    private Ellipse ellipse = new Ellipse();

    /**
     * constructor for MyEllipse object
     */
    public MyEllipse() {
    }

    //following 3 methods set necessary info from main canvas
    /**
     * sets graphics context to access canvas to be drawn on
     * @param graphicsContext graphics context from main program to draw on
     */
    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    /**
     * sets color of ellipse
     * @param colorPicker color picker from main program where color value is extracted to determine color of ellipse
     */
    public void setColor(ColorPicker colorPicker) {
        colorStroke = colorPicker.getValue();
    }

    /**
     * sets fill of ellipse
     * @param colorPicker color picker from main program where color value is extracted to determine fill of ellipse
     */
    public void setFill(ColorPicker colorPicker) {
        colorFill = colorPicker.getValue();
    }

    //following 4 methods set necessary requirements for ellipse
    /**
     * sets the center point of the ellipse
     * @param centerX double x value where user first clicks
     * @param centerY double x value where user first clicks
     */
    public void setCenterPoint(double centerX, double centerY) {
        this.centerX = centerX;
        this.centerY = centerY;

        ellipse.setCenterX(centerX);
        ellipse.setCenterY(centerY);
    }

    /**
     * sets end point of ellipse
     * @param endX double x value where user is dragging or stops dragging
     * @param endY double y value where user is dragging or stops dragging
     */
    public void setEndPoint(double endX, double endY) {
        this.endX = endX;
        this.endY = endY;
    }

    /**
     * sets radius of the ellipse
     */
    public void setRadius() {
        ellipse.setRadiusX(Math.abs((endX - centerX)));
        ellipse.setRadiusY(Math.abs((endY - centerY)));
    }
    
    /**
     * sets line width for the ellipse
     * @param lineWidth double from line width box in main program to set width of ellipse
     */
    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    /**
     * checks to make sure when user is drawing that the ellipse formats properly
     */
    public void check() {
        if (centerX > endX) {
            ellipse.setCenterX(endX);
        }
        if (centerY > endY) {
            ellipse.setCenterY(endY);
        }
    }

    //following 4 methods to get any data points on the ellipse
    /**
     * to get center x value
     * @return double that is the center x value of ellipse
     */
    public double getCenterX() {
        return ellipse.getCenterX();
    }
    
    /**
     * to get center y value
     * @return double that is the center y value of ellipse
     */

    public double getCenterY() {
        return ellipse.getCenterY();
    }

    /**
     * gets x value of the radius
     * @return double that is the x value of the radius
     */
    public double getRadiusX() {
        return ellipse.getRadiusX();
    }

    /**
     * get y value of the radius
     * @return double that is the y value of the radius
     */
    public double getRadiusY() {
        return ellipse.getRadiusY();
    }

    //put the ellipse on the canvas
    /**
     * draws the ellipse onto the canvas
     */
    public void draw() {
        //sets stroke, fill, and line width of ellipse
        graphicsContext.setStroke(colorStroke);
        graphicsContext.setLineWidth(lineWidth);
        graphicsContext.setFill(colorFill);

        //puts ellipse onto the canvas
        graphicsContext.strokeOval(getCenterX(), getCenterY(), getRadiusX(), getRadiusY());
        graphicsContext.fillOval(getCenterX(), getCenterY(), getRadiusX(), getRadiusY());
    }
}