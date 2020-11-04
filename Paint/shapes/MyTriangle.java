package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 * used for drawing a triangle on the canvas
 * @author Logan
 */
public class MyTriangle extends MyShapes {

    //data for triangle
    private GraphicsContext graphicsContext;
    private Color colorStroke;
    private Color colorFill;
    private Double startX, startY, endX, endY, midX, midY, lineWidth;
    private double[] xPoints;
    private double[] yPoints;

    /**
     * used to construct a triangle object
     */
    public MyTriangle() {
        xPoints = new double[3]; //triangle has 3 points
        yPoints = new double[3];
    }

    //following 6 methods to get data

    /**
     * used to get the starting x point of the triangle
     * @return a double that is the starting x value
     */
    public Double getStartX() {
        return startX;
    }

    /**
     * used to get the starting y point of the triangle
     * @return a double that is the starting y value
     */
    public Double getStartY() {
        return startY;
    }

    /**
     * used to get the ending x point of the triangle
     * @return a double that is the ending x value
     */
    public Double getEndX() {
        return endX;
    }

    /**
     * used to get the ending y point of the triangle
     * @return a double that is the ending y value
     */
    public Double getEndY() {
        return endY;
    }

    /**
     * used to get the mid point of the triangle that is x
     * @return a double that is the midpoint x value
     */
    public Double getMidx() {
        return midX;
    }

    /**
     * used to get the mid point of the triangle that is y
     * @return a double that is the midpoint y value
     */
    public Double getMidY() {
        return midY;
    }

    //following 8 methods to set data
    /**
     * sets the graphics context of the triangle from the main program
     * @param graphicsContext graphics context of the main program
     */
    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    /**
     * used to set the color of the triangle
     * @param colorPicker color picker from main program whose value is extracted to get the color
     */
    public void setColorPicker(ColorPicker colorPicker) {
        colorStroke = colorPicker.getValue();
    }

    /** 
     * used to set the fill of the triangle
     * @param colorPickerFill color picker from main program whose value is extracted to get the color
     */
    public void setColorPickerFill(ColorPicker colorPickerFill) {
        colorFill = colorPickerFill.getValue();
    }

    /**
     * used to set the starting x point of the triangle
     * @param startX x value where user first clicks
     */
    public void setStartX(Double startX) {
        this.startX = startX;
    }

    /**
     * used to set the starting y point of the triangle
     * @param startY y value where user first clicks
     */
    public void setStartY(Double startY) {
        this.startY = startY;
    }

    /**
     * used to set the end x point of the triangle
     * @param endX the x value when the user is dragging or stops dragging
     */
    public void setEndX(Double endX) {
        this.endX = endX;
    }

    /**
     * used to set the end y point of the triangle
     * @param endY the y value when the user is dragging or stops dragging
     */
    public void setEndY(Double endY) {
        this.endY = endY;
    }
  
    /**
     * used to set the line width of the triangle
     * @param lineWidth value extracted from line width box in main program
     */
    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    //put triangle on canvas
    /**
     * used to draw the triangle on the canvas
     */
    public void draw() {
        
        //getting midpoints to connect the lines
        if (startX > endX) {
            midX = endX + (Math.abs(startX - endX));
            midY = endY;
        } else {
            midX = endX - (Math.abs(startX - endX));
            midY = endY;
        }
        
        //put x and y points into an array for putting triangle on canvas
        xPoints[0] = startX;
        xPoints[1] = endX;
        xPoints[2] = midX;

        yPoints[0] = startY;
        yPoints[1] = endY;
        yPoints[2] = midY;
        
        //setting color and fill of triangle
        graphicsContext.setStroke(colorStroke);
        graphicsContext.setFill(colorFill);
        graphicsContext.setLineWidth(lineWidth);
        
        //putting triangle on canvas
        graphicsContext.strokePolygon(xPoints, yPoints, 3); //where 3 is the number of sides
        graphicsContext.fillPolygon(xPoints, yPoints, 3);
   }
}
