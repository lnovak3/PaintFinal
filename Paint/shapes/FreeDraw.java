package shapes;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 * used to draw on canvas
 *
 * @author Logan
 */
public class FreeDraw extends MyShapes {

    //date of FreeDraw
    private GraphicsContext graphicsContext;
    private Color color;

    private double startX, startY, endX, endY, lineWidth;

    //to hold all x and y values to draw
    private ArrayList<Double> xValues = new ArrayList<>();
    private ArrayList<Double> yValues = new ArrayList<>();

    /**
     * constructor to create a FreeDraw object
     */
    public FreeDraw() {
    }

    /**
     * sets the graphics context of free draw object
     *
     * @param graphicsContext graphics context of current canvas being worked on
     */
    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    //used to set a specific color
    /**
     * sets color of free draw
     *
     * @param colorPicker color picker from canvas to determine color of free
     * draw
     */
    public void setColor(ColorPicker colorPicker) {
        color = colorPicker.getValue();
    }

    //get the starting point of the line drawn
    /**
     * sets starting point of free draw
     *
     * @param startX x value where user first clicks
     * @param startY y value where user first clicks
     */
    public void setStartPoint(double startX, double startY) {
        this.startX = startX;
        this.startY = startY;
    }

    //get the ending point of the line drawn
    /**
     * sets ending point of line drawn
     *
     * @param endX x value where user stops dragging
     * @param endY y value where user stops dragging
     */
    public void setEndPoint(double endX, double endY) {
        this.endX = endX;
        this.endY = endY;
    }

    /**
     * sets width of line
     *
     * @param lineWidth double, from line width box in main program
     */
    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    //add a point to the line 
    /**
     * adds a point to the line
     *
     * @param x x value where user is dragging
     * @param y y value where user is dragging
     */
    public void addPoint(double x, double y) {
        xValues.add(x);
        yValues.add(y);
    }

    //next 4 methods to get X and Y points
    /**
     * gets starting x point of line
     *
     * @return double that is starting point x of line
     */
    public double getStartX() {
        return startX;
    }

    /**
     * gets starting y point of line
     *
     * @return double that is the starting y point of line
     */
    public double getStartY() {
        return startY;
    }

    /**
     * gets ending x point of line
     *
     * @return double that is the ending x point of line
     */
    public double getEndX() {
        return endX;
    }

    /**
     * gets ending y point of line
     *
     * @return double that is the ending y point of line
     */
    public double getEndY() {
        return endY;
    }

    //returns a list of all the X points
    /**
     * returns a list of all the x values in the line
     *
     * @return array of doubles of x values
     */
    public double[] getAllXValues() {
        double[] xVals = new double[xValues.size()];

        for (int i = 0; i < xValues.size(); i++) {
            xVals[i] = xValues.get(i);
        }

        return xVals;
    }

    //returns a list of all the Y points
    /**
     * returns a list of all the y values in the line
     *
     * @return array of double of y values
     */
    public double[] getAllYValues() {
        double[] yVals = new double[yValues.size()];

        for (int i = 0; i < yValues.size(); i++) {
            yVals[i] = yValues.get(i);
        }

        return yVals;
    }

    //draws line on canvas
    /**
     * puts the free draw onto the canvas
     */
    public void draw() {
        //sets stroke and width of line
        graphicsContext.setStroke(color);
        graphicsContext.setLineWidth(lineWidth);

        //putting the line on the canvas
        graphicsContext.beginPath();
        graphicsContext.lineTo(getStartX(), getStartY());
        for (int i = 0; i < xValues.size(); i++) {
            graphicsContext.lineTo(xValues.get(i), yValues.get(i));
            graphicsContext.stroke();
        }
        graphicsContext.lineTo(getEndX(), getEndY());
        graphicsContext.stroke();
        graphicsContext.closePath();
    }
}
