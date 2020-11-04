package shapes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * used to place text on a canvas
 * @author Logan
 */
public class MyTextBox extends MyShapes {
    
    //data of MyTextBox
    private GraphicsContext graphicsContext;
    private double positionX;
    private double positionY;
    private boolean onCanvas; //used to place back on canvas
    private String text;

    /**
     * constructor to create a MyTextBox object
     */
    public void MyTextBox() {
        onCanvas = false;
    }

    //following 5 methods to set data of MyTextBox
    /**
     * sets the graphics context of the text box so it can be stroked
     * @param graphicsContext from the main program where text box will be placed
     */
    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    /**
     * sets the y position where the text box will be on the canvas
     * @param x double that is the x position where the user clicked
     */
    public void setPositionX(double x) {
        positionX = x;
    }

    /**
     * sets the y position where the text box will be on the canvas
     * @param y double that is the y position where the user clicked
     */
    public void setPositionY(double y) {
        positionY = y;
    }

    /**
     * updates whether or not the text has been placed
     * @param bool true if on canvas, false if not
     */
    public void setOnCanvas(boolean bool) {
        onCanvas = bool;
    }

    /**
     * sets the text that will be displayed on the canvas
     * @param text a string that is the user inputted text from the pop up dialogue
     */
    public void setText(String text) {
        this.text = text;
    }

    //following 2 methods to get necessary data from MyTextBox

    /**
     * used to get whether or not the text is on the canvas
     * @return boolean that says true if on canvas, false if not on canvas
     */
    public boolean getOnCanvas() {
        return onCanvas;
    }

    /**
     * used to get the text the user put in the pop up dialogue
     * @return a string of what the text is
     */
    public String getText() {
        return text;
    }

    //to put the text box on the canvas
    /**
     * pop up dialogue for text, puts the text on the canvas
     */
    public void draw() {
        //if this is the first time drawing it, do the following
        if (!onCanvas) {
            //layout for popup and what is being put inside it
            GridPane gridPane = new GridPane();
            Label insertText = new Label("Insert Text: ");
            TextField insertTextField = new TextField("Type Here");
            Button insert = new Button("Insert");
            gridPane.add(insertText, 0, 0); //numbers correspond to where to put it on the gridpane
            gridPane.add(insertTextField, 1, 0);
            gridPane.add(insert, 1, 1);

            //pop up for placing text in canvas
            Alert alert = new Alert(Alert.AlertType.INFORMATION); //setting type of alert it is
            alert.setTitle("Insert Text");
            alert.getDialogPane().setContent(gridPane); //putting gridPane into alert

            insert.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    setText(insertTextField.getText()); //set the text of the object
                    setOnCanvas(true); //has been placed on canvas
                    graphicsContext.setLineWidth(1.0); //how thick drawing is of text
                    graphicsContext.strokeText(getText(), positionX, positionY); //putting text on canvas
                    alert.close(); //close popup
                }
            });
            alert.showAndWait();
        } else { //if this is an old object already placed on canvas (for undo button)
            graphicsContext.setLineWidth(1.0);
            graphicsContext.strokeText(text, positionX, positionY);
        }

    }

}
