package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * to erase canvas
 *
 * @author Logan
 */
public class MyEraser extends MyShapes {

    //data for MyEraser
    private GraphicsContext graphicsContext;
    private Image startImage, endImage;

    /**
     * construct for MyEraser object
     */
    public MyEraser() {
    }

    //following method to set data for eraser
    /**
     * sets the graphics context for the eraser
     *
     * @param graphicsContext graphics context from main program to be erased on
     */
    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    //following 2 methods to retrieve necessary images for redo and undo
    /**
     * to get the start image before erasing
     *
     * @return image that is the canvas before it was erased
     */
    public Image getStartImage() {
        return startImage;
    }

    /**
     * to get the end image once erasing is complete
     *
     * @return image that is the canvas after erasing has been completed
     */
    public Image getEndImage() {
        return endImage;
    }

    //following 2 methods set the start and end images of the eraser
    /**
     * sets the image before the eraser starts
     *
     * @param image image that is a snapshot of canvas before it was erased
     */
    public void whenEraserStart(Image image) {
        startImage = image;
    }

    /**
     * sets the image as the canvas is being erased or is done being erased
     *
     * @param image image that is a snapshot of canvas as it is being erased or
     * is done being erased
     */
    public void whenEraserEnd(Image image) {
        endImage = image;
    }

    /**
     * image to draw when using the undo button
     */
    public void drawUndo() {
        graphicsContext.drawImage(startImage, 0, 0);
    }

    /**
     * image to draw when the redo button is being used
     */
    public void drawRedo() {
        graphicsContext.drawImage(endImage, 0, 0);
    }
}
