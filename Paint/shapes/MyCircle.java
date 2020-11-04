package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * used to draw a circle on the canvas
 *
 * @author Logan
 */
public class MyCircle extends MyShapes {

    //data of MyCircle
    private GraphicsContext graphicsContext;
    private Color colorStroke;
    private Color colorFill;
    private double centerX, centerY, endX, endY, lineWidth;
    private Circle circle = new Circle();

    /**
     * constructor to create a MyCircle object
     */
    public MyCircle() {
    }

    //setting context, color, fill based off canvas
    /**
     * sets graphics context to connect to canvas to be drawn on
     *
     * @param graphicsContext graphics context from main context to use to draw
     * on canvas
     */
    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    /**
     * sets color of circle
     *
     * @param colorPicker color picker from main program, color value of it used
     * to determine color of circle
     */
    public void setColor(ColorPicker colorPicker) {
        colorStroke = colorPicker.getValue();
    }

    /**
     * sets the fill of circle
     *
     * @param colorPickerFill color picker from main program, determine whether
     * or not circle is filled
     */
    public void setFill(ColorPicker colorPickerFill) {
        colorFill = colorPickerFill.getValue();
    }

    //following 5 methods to set various attributes of circle
    /**
     * sets center of circle to base the circle on
     *
     * @param centerX x value on initial click
     * @param centerY y value on initial click
     */
    public void setCenterPoint(double centerX, double centerY) {
        this.centerX = centerX;
        this.centerY = centerY;

        circle.setCenterX(centerX);
        circle.setCenterY(centerY);
    }

    /**
     * sets where circle ends
     *
     * @param endX x value when user drags or stops dragging
     * @param endY y value when user drags or stops dragging
     */
    public void setEndPoint(double endX, double endY) {
        this.endX = endX;
        this.endY = endY;
    }

    /**
     * sets the radius of the circle
     */
    public void setRadius() {
        circle.setRadius((Math.abs(endX - centerX) + Math.abs(endY - centerY)) / 2);
    }

    /**
     * sets line width of circle
     *
     * @param lineWidth value retrieved from line width box in main program
     */
    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    /**
     * checks to make sure user drag is not breaking circle, adjusts circle as
     * needed
     */
    public void check() {
        if (centerX > endX) {
            circle.setCenterX(endX);
        }
        if (centerY > endY) {
            circle.setCenterY(endY);
        }
    }

    //following 3 methods to retrieve any data needed
    /**
     * used to get radius of circle
     *
     * @return double that is the radius of the circle
     */
    public double getRadius() {
        return circle.getRadius();
    }

    /**
     * used to get center x value of circle
     *
     * @return a double that is the x value of the center of the circle
     */
    public double getCenterX() {
        return circle.getCenterX();
    }

    /**
     * used to get center y value of circle
     *
     * @return a double that is the y value of the center of the circle
     */
    public double getCenterY() {
        return circle.getCenterY();
    }

    //put circle on the canvas
    /**
     * draws circle onto the canvas
     */
    public void draw() {
        //set stroke, line width, and fill of circle
        graphicsContext.setStroke(colorStroke);
        graphicsContext.setLineWidth(lineWidth);
        graphicsContext.setFill(colorFill);

        //draw the circle onto the canvas
        graphicsContext.fillOval(circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getRadius());
        graphicsContext.strokeOval(circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getRadius());
    }
}
