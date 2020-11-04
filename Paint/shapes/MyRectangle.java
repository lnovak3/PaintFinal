package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * used to draw a rectangle on the canvas
 *
 * @author Logan
 */
public class MyRectangle extends MyShapes {

    //data for rectangle
    private GraphicsContext graphicsContext;
    private Color colorStroke;
    private Color colorFill;
    public double startX, startY, endX, endY, width, height, lineWidth;
    private Rectangle rectangle = new Rectangle();

    /**
     * constructor to create a MyRectangle object
     */
    public MyRectangle() {
    }

    //following 3 methods to get info from canvas
    /**
     * sets graphics context where rectangle will be drawn
     *
     * @param graphicsContext graphics context from the main program where the
     * rectangle will be drawn
     */
    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    /**
     * sets the color of the rectangle
     *
     * @param colorPicker used from main program to extract it's color and make
     * that the color of the rectangle
     */
    public void setColor(ColorPicker colorPicker) {
        colorStroke = colorPicker.getValue();
    }

    /**
     * sets the fill of the rectangle
     *
     * @param colorPicker from main program to set whether or not there is a
     * fill of the rectangle
     */
    public void setFill(ColorPicker colorPicker) {
        colorFill = colorPicker.getValue();
    }

    //following 2 methods setting points of rectangle
    /**
     * sets the starting points of the rectangle
     *
     * @param startX x value where user first clicks
     * @param startY y value where user first clicks
     */
    public void setStartPoint(double startX, double startY) {
        this.startX = startX;
        this.startY = startY;

        rectangle.setX(startX);
        rectangle.setY(startY);
    }

    /**
     * sets end points of the rectangle
     *
     * @param endX x value when user is dragging or has stopped dragging
     * @param endY y value when user is dragging or has stopped dragging
     */
    public void setEndPoint(double endX, double endY) {
        this.endX = endX;
        this.endY = endY;
    }

    //setting width and height of rectangle
    /**
     * sets the width of the rectangle
     */
    public void setWidth() {
        this.width = Math.abs((endX - startX));

        rectangle.setWidth(Math.abs((endX - startX)));
    }

    /**
     * sets the height of the rectangle
     */
    public void setHeight() {
        this.height = Math.abs((endY - startY));

        rectangle.setHeight(Math.abs((endY - startY)));
    }

    /**
     * sets the line width of the square
     *
     * @param lineWidth from input box from main program that determines line
     * width
     */
    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    /**
     * corrects rectangle as user drags to draw it correctly
     */
    public void check() {
        if (getX() > endX) {
            rectangle.setX(endX);
        }
        if (getY() > endY) {
            rectangle.setY(endY);
        }
    }

    //following 6 methods to get any necessary attributes of rectangle
    /**
     * used to get starting x value of rectangle
     *
     * @return double that is starting x value of rectangle
     */
    public double getX() {
        return rectangle.getX();
    }

    /**
     * used to get starting y value of rectangle
     *
     * @return double that is the starting y value of rectangle
     */
    public double getY() {
        return rectangle.getY();
    }

    /**
     * used to get width of rectangle
     *
     * @return double that is width of rectangle
     */
    public double getWidth() {
        return rectangle.getWidth();
    }

    /**
     * used to get height of rectangle
     *
     * @return double that is the height of rectangle
     */
    public double getHeight() {
        return rectangle.getHeight();
    }

//put rectangle on canvas
    /**
     * draws rectangle onto the canvas
     */
    public void draw() {
        //sets stroke and fill of rectangle
        graphicsContext.setStroke(colorStroke);
        graphicsContext.setFill(colorFill);

        //puts rectangle on canvas
        graphicsContext.fillRect(getX(), getY(), getWidth(), getHeight());
        graphicsContext.strokeRect(getX(), getY(), getWidth(), getHeight());
    }
}
