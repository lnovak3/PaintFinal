package shapes;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import paint.ResizableCanvas;

/**
 * for selecting a piece of the canvas and pasting it elsewhere
 * @author Logan
 */
public class MySelection extends MyShapes{
    
    //data for rectangle
    private GraphicsContext graphicsContext;
    public double startX, startY, endX, endY, width, height, pasteX, pasteY;
    private WritableImage temp, croppedSnap;
    private ResizableCanvas canvas;
    private Image image, startImage, endImage;
    private Rectangle rectangle = new Rectangle();
    private SnapshotParameters backgroundSnap = new SnapshotParameters();

    /**
     * constructor for MySelection object
     */
    public MySelection() {
        backgroundSnap.setFill(Color.TRANSPARENT);
    }

    //following 2 methods to set info from canvas
    /**
     * sets the graphics context for select and paste
     * @param graphicsContext graphics context from main program that will be selected and pasted on
     */
    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }
    
    /**
     * determines where the selected area will be pasted
     * @param pasteX double x value where user clicks to paste selected area
     * @param pasteY double y value where user clicks to paste selected area
     */
    public void setPastePoints(double pasteX, double pasteY) {
        this.pasteX = pasteX;
        this.pasteY = pasteY;
    }

    //following 2 methods setting points of rectangle
    /**
     * sets the starting points of the selection rectangle
     * @param startX double x value where user first clicks
     * @param startY double y value where user first clicks
     */
    public void setStartPoint(double startX, double startY) {
        this.startX = startX;
        this.startY = startY;
        rectangle.setX(startX);
        rectangle.setY(startY);
    }

    /**
     * sets end point of selection rectangle when user stops dragging
     * @param endX double x value where user stops dragging
     * @param endY double y value where user stops dragging
     */
    public void setEndPoint(double endX, double endY) {
        this.endX = endX;
        this.endY = endY;
    }

    //setting width and height of rectangle
    /**
     * sets width of selection area
     */
    public void setWidth() {
        this.width = Math.abs((endX - startX));

        rectangle.setWidth(Math.abs((endX - startX)));
    }
/**
 * sets height of selection area
 */
    public void setHeight() {
        this.height = Math.abs((endY - startY));

        rectangle.setHeight(Math.abs((endY - startY)));
    }
    
    //setting canvas to be worked on
    /**
     * sets the canvas that will be selected and pasted on
     * @param canvas resizable canvas from main program to be copied and pasted on
     */
    public void setCanvas(ResizableCanvas canvas) {
        this.canvas = canvas;
    }
    
    //setting start and end images of select and paste tool
    /**
     * sets start image before selecting for undo button
     * @param startImage image snapshot right before user starts dragging to select an area
     */
    public void setStartImage(Image startImage) {
        this.startImage = startImage;
    }
    
    /**
     * sets end image after user is done dragging select area
     * @param endImage image snapshot right after user stops dragging to select an area
     */
    public void setEndImage(Image endImage) {
        this.endImage = endImage;
    }

    /**
     * checks to make sure select area is formatting properly
     */
    public void check() {
        if (getX() > endX) {
            rectangle.setX(endX);
        }
        if (getY() > endY) {
            rectangle.setY(endY);
        }
    }

    //following 6 methods to get any necessary attributes of select area
    /**
     * gets x value where select area starts
     * @return double that is the x value where select area starts
     */
    public double getX() {
        return rectangle.getX();
    }

    /**
     * gets y value where select area starts
     * @return double that is the y value where select area starts
     */
    public double getY() {
        return rectangle.getY();
    }

    /**
     * gets width of select area
     * @return double that is width of select area
     */
    public double getWidth() {
        return rectangle.getWidth();
    }

    /**
     * gets height of select area
     * @return double that is height of select area
     */
    public double getHeight() {
        return rectangle.getHeight();
    }
    
    /**
     * gets start image before select area starts
     * @return image that is a snapshot of area before it is selected
     */
    public Image getStartImage() {
        return startImage;
    }

    /**
     * gets end image after select area ends
     * @return image that is a snapshot of select area once it is finished
     */
    public Image getEndImage() {
        return endImage;
    }

    /**
     * clears area where select area is and creates a snapshot of it
     */
    public void clearSection() {
        temp = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(backgroundSnap, temp);
        graphicsContext.clearRect(getX(), getY(), getWidth(), getHeight());
        croppedSnap = new WritableImage(temp.getPixelReader(), (int) startX, (int) startY, (int)getWidth(), (int) getHeight());
        image = croppedSnap;
    }
    //put select area on canvas
    /**
     * pastes select area where user clicks
     */
    public void paste() {
        graphicsContext.drawImage(image, pasteX, pasteY);
    }

    /**
     * image to use when pressing undo button
     */
    public void onUndo() {
         graphicsContext.drawImage(startImage, 0, 0);
    }
    
    /**
     * image to use when pressing redo button
     */
    public void onRedo() {
        graphicsContext.drawImage(endImage, 0, 0);
    }
    
    /**
     * clears an area as select area is being dragged
     * @param x double x value where user is dragging
     * @param y double y value where user is dragging
     */
    public void onDragClear(double x, double y) {
        double temp_width = Math.abs((x - startX));
        double temp_height = Math.abs((y - startY));
        graphicsContext.clearRect(getX(), getY(), temp_width, temp_height);
    }
}
