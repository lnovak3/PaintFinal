package Dialogues;

import java.io.File;
import java.util.Optional;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import paint.Paint;
import paint.ResizableCanvas;

/**
 *pop up dialogue for save as
 * @author Logan
 */
public class SaveAsDialogue extends Dialogue{

    //data for SaveAsDialogue
    private boolean saved;
    private boolean hasBeenOpened;
    private boolean enableAutosave;
    private SnapshotParameters backgroundSnap = new SnapshotParameters();

    /**
     * constructor for SaveAsDialogue object
     */
    public SaveAsDialogue() {
    }

    //following 3 methods set data of this object
    /**
     * set whether or not a file has been opened into the canvas
     * @param hasBeenOpened boolean to determine whether or not a file has been opened into the canvas
     */
    public void setHasBeenOpened(boolean hasBeenOpened) {
        this.hasBeenOpened = hasBeenOpened;
    }

    /**
     * sets whether or not canvas has been saved
     * @param saved boolean that determines whether or not canvas has been saved
     */
    public void setSaved(boolean saved) {
        this.saved = saved;
    }
    
    /**
     * used to turn auto save on or off
     * @param enableAutosave boolean that turns auto save on or off
     */
    public void setEnableAutosave(boolean enableAutosave) {
        this.enableAutosave = enableAutosave;
    }

    //following 3 methods retrieve data from this object
    
    /**
     * tells whether or not the canvas has been saved
     * @return a boolean that tells whether or not the canvas has been saved
     */
    public boolean getSaved() {
        return saved;
    }

    /**
     * tells whether or not a file has been opened into the canvas
     * @return a boolean that tells whether or not a file has been opened into the canvas
     */
    public boolean getHasBeenOpened() {
        return hasBeenOpened;
    }
    
    /**
     * tells whether or not auto save is enabled
     * @return boolean that tells whether or not auto save is enabled
     */
    public boolean getEnableAutosave() {
        return enableAutosave;
    }

    /**
     * if the file has been opened before, pop up a dialogue with a warning about file conversions
     * @param file file that will hold the new file being saved
     * @param canvas canvas that will be snapshotted and out into file
     * @return file that has been created from saving
     */
    public File hasBeenOpened(File file, ResizableCanvas canvas) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText("Saving in an alternate file format may cause data loss. Continue?");
        Optional<ButtonType> rslt = alert.showAndWait();
        if ((rslt.isPresent()) && rslt.get() == ButtonType.OK) {
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
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                setSaved(true);
            } catch (Exception e) {
                System.out.println("Failed to save image.");
            }
        }
        return file;
    }

    /**
     * if this is a new file, pop up a save as dialogue
     * @param file file to hold new file being created
     * @param canvas canvas to be snapshotted and put into file
     * @return file that is created from save as dialogue
     */
    public File hasNotBeenOpened(File file, ResizableCanvas canvas) {
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
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            setSaved(true);
            setHasBeenOpened(true);
            setEnableAutosave(true);
            
        } catch (Exception e) {
            System.out.println("Failed to save image.");
        }
        
        return file;
    }

}






