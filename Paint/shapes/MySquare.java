package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * used to place a square on a canvas
 *
 * @author Logan
 */
public class MySquare extends MyShapes {

    //data of square
    private GraphicsContext graphicsContext;
    private Color colorStroke;
    private Color colorFill;
    private double startX, startY, endX, endY, lineWidth;
    private Rectangle square = new Rectangle();

    /**
     * used to construct a square object
     */
    public MySquare() {
    }

    //following 3 methods used to set necessary info for canvas
    /**
     * sets the graphics context where the square will be drawn
     *
     * @param graphicsContext graphics context from the main program where
     * square will be drawn
     */
    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    /**
     * sets the color of the square when it is drawn
     *
     * @param colorPicker color picker from main program, current color is the
     * color of square
     */
    public void setColor(ColorPicker colorPicker) {
        colorStroke = colorPicker.getValue();
    }

    /**
     * sets the fill of the square
     *
     * @param colorPicker used to get what the fill will be from the main
     * program
     */
    public void setFill(ColorPicker colorPicker) {
        colorFill = colorPicker.getValue();
    }

    //following 2 methods set start and end point for square
    /**
     * sets where the square will start on the canvas
     *
     * @param startX starting x point when the user first clicks
     * @param startY starting y point when the user first clicks
     */
    public void setStartPoint(double startX, double startY) {
        this.startX = startX;
        this.startY = startY;

        square.setX(startX);
        square.setY(startY);
    }

    /**
     * sets where the square will end on the canvas
     *
     * @param endX ending x point when the user drags or stops dragging
     * @param endY ending y point when the user drags or stops dragging
     */
    public void setEndPoint(double endX, double endY) {
        this.endX = endX;
        this.endY = endY;
    }

    //following 3 methods set up the squares width and height
    /**
     * sets the width of the square
     */
    public void setWidth() {
        square.setWidth(Math.abs((endX - startX)));
    }

    /**
     * sets the height of the square
     */
    public void setHeight() {
        square.setHeight(Math.abs((endY - startY)));
    }

    /**
     * sets the width of the square sides
     *
     * @param lineWidth taken from line width box of main program
     */
    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    /**
     * used to monitor drags of user to align square as necessary
     */
    public void check() {
        if (getX() > endX) {
            square.setX(endX);
        }
        if (getY() > endY) {
            square.setY(endY);
        }
    }

    //following 4 methods are getters for data of square
    /**
     * returns starting x value of the square
     *
     * @return a double that is the starting x value of the square
     */
    public double getX() {
        return square.getX();
    }

    /**
     * returns starting y value of the square
     *
     * @return a double that is the starting y value of the square
     */
    public double getY() {
        return square.getY();
    }

    /**
     * returns width of square
     *
     * @return a double that is the width of the square
     */
    public double getWidth() {
        return square.getWidth();
    }

    /**
     * returns height of square
     *
     * @return a double that is the height of the square
     */
    public double getHeight() {
        return square.getHeight();
    }

    //put square on canvas
    /**
     * draws the square on the canvas using the graphics context
     */
    public void draw() {
        //sets stroke, fill, line width of square
        graphicsContext.setStroke(colorStroke);
        graphicsContext.setFill(colorFill);
        graphicsContext.setLineWidth(lineWidth);

        //draws square on canvas
        graphicsContext.fillRect(getX(), getY(), getWidth(), getWidth());
        graphicsContext.strokeRect(getX(), getY(), getWidth(), getWidth());
    }
}
