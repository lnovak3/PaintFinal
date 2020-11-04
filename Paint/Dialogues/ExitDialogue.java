package Dialogues;

import java.io.File;
import java.util.Optional;
import javafx.application.Platform;
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
 * pops up exit dialogue to ensure user has saved and acknowledges they are closing program
 * @author Logan
 */
public class ExitDialogue extends Dialogue {
    
    //data for ExitDialogue
    boolean setCanvasSaved;
    boolean saved;
    private SnapshotParameters backgroundSnap = new SnapshotParameters();

    /**
     * constructor for ExitDialogue object
     */
    public ExitDialogue() {

    }

    //following 2 methods get data 
    /**
     * gets whether or not the canvas has been saved
     * @return boolean that determines if canvas has been saved or not
     */
    public boolean getSaved() {
        return saved;
    }

    /**
     * gets whether or not canvas has been saved
     * @return boolean that determines if canvas has been saved or not
     */
    public boolean getSetCanvasSaved() {
        return setCanvasSaved;
    }

    //following 2 methods set data
    /**
     * sets whether or not canvas has been saved
     * @param setCanvasSaved boolean that determines if the canvas has been saved or not
     */
    public void setSetCanvasSaved(boolean setCanvasSaved) {
        this.setCanvasSaved = setCanvasSaved;
    }

    /**
     * sets whether or not canvas has been saved
     * @param setCanvasSaved boolean that determines if the canvas has been saved or not
     */
    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    /**
     * if the project has not been saved or opened, this pops up a dialogue to warn the user to save
     * @param file file to be saved
     * @param canvas canvas to take a snapshot of and put in a file
     * @return file that was created from saving
     */
    public File isFreshProject(File file, ResizableCanvas canvas) {
        //popup reminding the user work is not saved
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Work?");
        alert.setContentText("Exiting will lose all progress. Save work?");
        Optional<ButtonType> rslt = alert.showAndWait();
        //if user selects ok to save, act like saveAs button
        if ((rslt.isPresent()) && rslt.get() == ButtonType.OK) {
            //opening file chooser
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
            } catch (Exception e) {
                System.out.println("Failed to save image.");
            }
            setSetCanvasSaved(true);
        } //if user chooses cancel button, the program will close and not save
        else if ((rslt.isPresent()) && rslt.get() == ButtonType.CANCEL) {
            Platform.exit();
            System.exit(0);
        }
        return file;
    }

    /**
     * if file has been edited or opened, pop up this dialogue to warn user to save
     * @param file file to be overwritten
     * @param canvas canvas to take a snapshot of and overwrite the file
     * @return file that has been overwritten
     */
    public File isEditedOrOpened(File file, ResizableCanvas canvas) {
        //pop up reminding them work has not been saved
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Work?");
        alert.setContentText("Exiting will lose all progress. Save work?");
        Optional<ButtonType> rslt = alert.showAndWait();
        //if they choose to save work, acts like save button when ok is pressed
        if ((rslt.isPresent()) && rslt.get() == ButtonType.OK) {
            //get current screen and save it to the selected file
            try {
                backgroundSnap.setFill(Color.TRANSPARENT);
                Image image = canvas.snapshot(backgroundSnap, null);
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                setSaved(true);
                setSetCanvasSaved(true);
            } catch (Exception e) {
                System.out.println("Failed to save image.");
            }
        }
        //after saving, exit the program
        Platform.exit();
        System.exit(0);

        return file;
    }

}