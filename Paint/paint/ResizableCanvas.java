package paint;

import javafx.scene.canvas.Canvas;

/**
 * extends canvas and makes it resizable
 * @author Logan
 */
public class ResizableCanvas extends Canvas {

    boolean saved; //to check if the canvas has been edited recently

    //instaniates object
    /**
     * constructor for ResizableCanvas object
     */
    public ResizableCanvas() {
        saved = true;
    }

    //allows canvas to be resizable
    /**
     * allows canvas to be overwritten
     * @return boolean that says the canvas is resizable
     */
    @Override
    public boolean isResizable() {
        return true;
    }

    //what to do when update button is clicked
    /**
     * resizes the canvas
     * @param width double that is width of new canvas
     * @param height double that is height of new canvas
     */
    public void resizeCanvas(double width, double height) {
        super.setWidth(width);
        super.setHeight(height);
        
    }

    //to check if the canvas has been saved since the last edit
    /**
     * determines whether or not canvas has been saved since last edit
     * @return boolean that determines whether or not canvas has been saved since last edit
     */
    public boolean getCanvasSaved() {
        return saved;
    }

    //to set the canvas when it has been saved or edited
    /**
     * sets the canvas to saved or not saved
     * @param saved boolean that is used to set whether or not the canvas has been saved
     */
    public void setCanvasSaved(boolean saved) {
        this.saved = saved;
    }
}
