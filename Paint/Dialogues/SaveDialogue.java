package Dialogues;

import java.io.File;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import paint.Paint;
import paint.ResizableCanvas;

/**
 * used to save the image or create an image file if it has not been saved before
 * @author Logan
 */
public class SaveDialogue extends Dialogue{
    
    //data for save dialogue
    private boolean saved;
    private boolean hasBeenOpened;
    private boolean setCanvasSaved;
    private boolean enableAutosave;
    private SnapshotParameters backgroundSnap = new SnapshotParameters();

    /**
     * constructor for save dialogue
     */
    public SaveDialogue() {
    }

    //following 4 methods to set data
    /**
     * sets whether or not the canvas has been saved
     * @param saved boolean that determines if canvas has been saved or not
     */
    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    /**
     * sets whether or not a file has been opened into the canvas
     * @param hasBeenOpened boolean that determines if a file has been opened into the canvas
     */
    public void setHasBeenOpened(boolean hasBeenOpened) {
        this.hasBeenOpened = hasBeenOpened;
    }

    /**
     * sets whether or not the canvas has been saved
     * @param setCanvasSaved boolean that determines if the canvas has been saved or not
     */
    public void setSetCanvasSaved(boolean setCanvasSaved) {
        this.setCanvasSaved = setCanvasSaved;
    }
    
    /**
     * used to turn auto save on and off
     * @param enableAutosave boolean used to turn auto save on and off
     */
    public void setEnableAutosave(boolean enableAutosave) {
        this.enableAutosave = enableAutosave;
    }

    //following 4 methods to retrieve data
    /**
     * tells whether or not the canvas has been saved
     * @return boolean that determines if the canvas has been saved or not
     */
    public boolean getSaved() {
        return saved;
    }

    /**
     * tells whether or not a file has been opened into the canvas
     * @return boolean that determines if a file has been opened into the canvas
     */
    public boolean getHasBeenOpened() {
        return hasBeenOpened;
    }

    /**
     * tells whether or not the canvas has been saved
     * @return boolean that determines if the canvas has been saved or not
     */
    public boolean getSetCanvasSaved() {
        return setCanvasSaved;
    }
    
    /**
     * tells if auto save is on or off
     * @return boolean that tells if auto save is on or off
     */
    public boolean getEnableAutosave() {
        return enableAutosave;
    }

    /**
     * used if the file has never been saved before (same as save as)
     * @param file file that the image will be saved as
     * @param canvas canvas where the image for the file comes from
     * @return 
     */
    public File hasNotBeenSaved(File file, ResizableCanvas canvas) {
        //open save as dialogue to save the file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg")
        );
        file = fileChooser.showSaveDialog(Paint.getPrimaryStage());

        //get current screen and make a file
        backgroundSnap.setFill(Color.TRANSPARENT);
        Image image = canvas.snapshot(backgroundSnap, null);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", file);
            setSaved(true);
            setSetCanvasSaved(true);
            setHasBeenOpened(true);
            setEnableAutosave(true);
        } catch (Exception e) {
            System.out.println("Failed to save image.");
        }
        return file;
    }

    /**
     * used if the file has been saved before, overwrites file
     * @param file file that is being overwritten
     * @param canvas canvas where the image that overwrites the file comes from
     * @return 
     */
    public File hasBeenSaved(File file, ResizableCanvas canvas) {
        //screenshot the canvas and overwrite the file
            try {
                backgroundSnap.setFill(Color.TRANSPARENT); // to fix discoloration of background
                Image image = canvas.snapshot(backgroundSnap, null);
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", file);
                setSaved(true);
                setSetCanvasSaved(true);
                setHasBeenOpened(true);
            } catch (Exception e) {
                System.out.println("Failed to save image.");
            }
        return file;
    } 
}