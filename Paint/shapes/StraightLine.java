package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * used for drawing a straight line on a canvas
 * @author Logan
 */
public class StraightLine extends MyShapes {

    //data of straightline
    private GraphicsContext graphicsContext;
    private Color color;
    private Line line = new Line();
    private double lineWidth;

    /**
     * constructor for StraightLine, builds a StraightLine object
     */
    public StraightLine() {
    }

    /**
     * gets the graphics context from the main program for the straight line
     *
     * @param graphicsContext graphics context from main program
     */
    //following 2 methods get necessary info from canvas
    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    /**
     * sets the color of the line to be drawn
     *
     * @param colorPicker gets the value from the color picker from main program
     */
    public void setColor(ColorPicker colorPicker) {
        color = colorPicker.getValue();
    }

    /**
     * sets the starting point of the line
     *
     * @param x the x value where the user first clicks
     * @param y the y value where the user first clicks
     */
    //following 2 method set start and end point of line
    public void setStartPoint(double x, double y) {
        line.setStartX(x);
        line.setStartY(y);
    }

    /**
     * sets the end point of the line
     *
     * @param x the x point of the line where the user drags or stops dragging
     * @param y the y point of the line where the user drags or stops dragging
     */
    public void setEndPoint(double x, double y) {
        line.setEndX(x);
        line.setEndY(y);
    }

    /**
     * sets the line width of the line
     *
     * @param lineWidth taken from the line width box in the main program
     */
    //sets line width of line
    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    //following 4 methods to get any data for line
    /**
     * used to get the starting x point of the line
     *
     * @return a double where the x point of the line starts
     */
    public double getStartX() {
        return line.getStartX();
    }

    /**
     * used to get the starting y point of the line
     *
     * @return a double where the y point of the line starts
     */
    public double getStartY() {
        return line.getStartY();
    }

    /**
     * used to get the ending x point of the line
     *
     * @return a double where the x point of the line ends
     */
    public double getEndX() {
        return line.getEndX();
    }

    /**
     * used to get the ending y point of the line
     *
     * @return a double where the y point of the line ends
     */
    public double getEndY() {
        return line.getEndY();
    }

    //put the line on the canvas
    /**
     * draws the line on the canvas
     */
    public void draw() {
        graphicsContext.setStroke(color);
        graphicsContext.setLineWidth(lineWidth);
        graphicsContext.strokeLine(getStartX(), getStartY(), getEndX(), getEndY());
    }
}
