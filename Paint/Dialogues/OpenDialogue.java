package Dialogues;

import java.io.File;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import paint.Paint;
import paint.ResizableCanvas;

/**
 * used to open image files into a canvas
 *
 * @author Logan
 */
public class OpenDialogue extends Dialogue {

    //data used below
    boolean saved;
    boolean hasBeenOpened;
    boolean canvasSaved;
    boolean enableAutosave;

    //construct for open dialogue
    /**
     * constructor the open dialogue object
     */
    public OpenDialogue() {

    }

    //following 4 methods set data
    /**
     * sets whether or not the canvas has been saved
     *
     * @param saved boolean that tracks if the canvas was saved
     */
    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    /**
     * sets whether or not the canvas has been saved
     *
     * @param canvasSaved boolean that tracks if the canvas was saved
     */
    public void setCanvasSaved(boolean canvasSaved) {
        this.canvasSaved = canvasSaved;
    }

    /**
     * sets whether or not a file has been opened into the canvas
     *
     * @param hasBeenOpened boolean that tracks if the canvas has had a file
     * opened into it
     */
    public void setHasBeenOpened(boolean hasBeenOpened) {
        this.hasBeenOpened = hasBeenOpened;
    }

    /**
     * sets whether or not auto save should be enabled
     *
     * @param enableAutosave boolean that tracks if auto save should be enabled
     */
    public void setEnableAutosave(boolean enableAutosave) {
        this.enableAutosave = enableAutosave;
    }

    //following 4 methods get data
    /**
     * tells whether or not canvas has been saved
     *
     * @return boolean that tells whether or not canvas has been saved
     */
    public boolean getSaved() {
        return saved;
    }

    /**
     * tells whether or not a file has been opened into the canvas
     *
     * @return boolean that tells whether or not a file has been opened into the
     * canvas
     */
    public boolean getHasBeenOpened() {
        return hasBeenOpened;
    }

    /**
     * tells whether or not canvas has been saved
     *
     * @return boolean that tells whether or not canvas has been saved
     */
    public boolean getCanvasSaved() {
        return canvasSaved;
    }

    /**
     * tells whether or not auto save has been enabled
     *
     * @return boolean that tells whether or not auto save has been enabled
     */
    public boolean getEnableAutosave() {
        return enableAutosave;
    }

    /**
     * opens dialogue to open a file into the canvas
     *
     * @param file the file to be returned later with the file being opened
     * @param canvas canvas that the file is being opened in
     * @param graphicsContext graphics context of canvas to draw image
     * @return a file used to track saving in the main program
     */
    //to open a file into the canvas
    public File openFile(File file, ResizableCanvas canvas, GraphicsContext graphicsContext) {
        //opening file chooser
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        file = fileChooser.showOpenDialog(Paint.getPrimaryStage());

        //put image on the canvas
        if (file != null) {
            Image image = new Image(file.toURI().toString());

            //setting the canvas to the size of the image
            canvas.setHeight(image.getHeight());
            canvas.setWidth(image.getWidth());
            graphicsContext.drawImage(image, 0, 0); //0 corresponds to x and y position, putting in center
            setSaved(true);
            setCanvasSaved(true);
            setHasBeenOpened(true);
            setEnableAutosave(true);

        }

        return file;
    }

    /**
     * used to set attributes of file chooser
     *
     * @param fileChooser used to open File Chooser Dialogue to open files into
     * canvas
     */
    //configuring the file chooser
    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures"); //title of file chooser
        fileChooser.setInitialDirectory( //which directory the dialogue will start in
                new File(System.getProperty("user.home"))
        );

        //the extentions the user will be open from the file chooser
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.png", "*.jpeg"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg")
        );
    }

}
