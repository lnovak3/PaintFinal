package paint;

import shapes.StraightLine;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import shapes.*;
import Dialogues.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

import javafx.util.Duration;

/**
 * used to control program and what happens to canvas, set up, etc.
 * @author Logan
 */
public class Controller implements Initializable {

    //variables from FXML
    @FXML
    private ScrollPane sp1;
    @FXML
    private TextField brushSize;
    @FXML
    public ColorPicker colorPicker;
    @FXML
    private Slider slider;
    @FXML
    private MenuItem save, saveAs, quit, undo, resize, fDraw,
            lDraw, sDraw, rDraw, cDraw, eDraw, filled, transparent,
            about, tDraw, noTool, triDraw, redo, polygon, open, toolEraser, selection, clear;
    @FXML
    private Menu fileFxml, help;
    @FXML
    private Label currentTool;
    @FXML
    private MenuButton menuButton = new MenuButton();
    @FXML 
    VBox vbox;
    
    private ResizableCanvas selectedCanvas; //canvas to be drawn on 
    public ColorPicker colorPickerFill; //to determine color of fill, not actually on program
    private String mode = "No Tool"; //to determine what tool is currently being used
    private File currentFile; //current file being edited
    private GraphicsContext currentGraphicsContext;
    public boolean saved = false; //to check if a file has been saved
    private boolean hasBeenOpened = false; //to check if this is a file that has been opened / saved
    private boolean checkAutosave = true; //check if autosave is on or off
    private boolean enableAutosave = false; //autosave is turned on once this is enabled

    //declaring all the modes
    private StraightLine straightLine;
    private MyRectangle myRectangle;
    private MySquare mySquare;
    private MyEllipse myEllipse;
    private MyCircle myCircle;
    private FreeDraw freeDraw;
    private MyTextBox textBox;
    private MyTriangle triangle;
    private MyPolygon myPolygon;
    private MyEraser myEraser;
    private MySelection mySelection;

    //stacks for undo and redo
    private Stack<MyShapes> undoStack = new Stack<>();
    private Stack<MyShapes> redoStack = new Stack<>();

    private SnapshotParameters backgroundSnap = new SnapshotParameters(); //to set background of snapshot to default
    private Image onDragScreenshot; //screenshot of canvas for seeing shapes as they are drawn

    private final static Logger logger = Logger.getLogger(Controller.class.getName()); //logger to determine saved, current tool, time stamp

    /**
     * constructor for controller object
     */
    public Controller() {
    }

    /**
     * gets current mode
     * @return String that determines current mode
     */
    public String getMode() {
        return mode;

    }

    /**
     * Saves the image to the computer, popping up Save As Dialogue
     */
    //method for Save As button
    public void onSaveAs() {
        SaveAsDialogue saveAs = new SaveAsDialogue();
        if (hasBeenOpened) {
            currentFile = saveAs.hasBeenOpened(currentFile, selectedCanvas);

            saved = saveAs.getSaved();
        } else {
            currentFile = saveAs.hasNotBeenOpened(currentFile, selectedCanvas);

            saved = saveAs.getSaved();
            hasBeenOpened = saveAs.getHasBeenOpened();
            enableAutosave = saveAs.getEnableAutosave();
        }
    }

    /**
     * Saves the image if it has been saved before or has been opened from file
     * browser
     */
    //method for Save button
    public void onSave() {
        SaveDialogue saveDialogue = new SaveDialogue();
        //if has not been saved before or opened from file, act like save as button
        if (!saved) {
            currentFile = saveDialogue.hasNotBeenSaved(currentFile, selectedCanvas);

            saved = saveDialogue.getSaved();
            hasBeenOpened = saveDialogue.getHasBeenOpened();
            enableAutosave = saveDialogue.getEnableAutosave();
            selectedCanvas.setCanvasSaved(saveDialogue.getSetCanvasSaved());
        } else {
            currentFile = saveDialogue.hasBeenSaved(currentFile, selectedCanvas);
            saved = saveDialogue.getSaved();
            hasBeenOpened = saveDialogue.getHasBeenOpened();
            selectedCanvas.setCanvasSaved(saveDialogue.getSetCanvasSaved());
        }
    }

    /**
     * Used to exit the program. Checks to see if the canvas has been
     * edited/saved. If it has, closes program, otherwise pops up save dialog
     */
    //method for Exit button
    public void onExit() {
        //if this a fresh project and the canvas has been edited
        ExitDialogue exitDialogue = new ExitDialogue();
        if (!selectedCanvas.getCanvasSaved() && !saved) {
            currentFile = exitDialogue.isFreshProject(currentFile, selectedCanvas);
            selectedCanvas.setCanvasSaved(exitDialogue.getSetCanvasSaved());

            //if canvas has been edited but the work was already saved at lease
            //once or has been opened from a file, do the following
        } else if (!selectedCanvas.getCanvasSaved()) {
            currentFile = exitDialogue.isEditedOrOpened(currentFile, selectedCanvas);
            selectedCanvas.setCanvasSaved(exitDialogue.getSetCanvasSaved());
            saved = exitDialogue.getSaved();
            //if the canvas not been edited, or the work was already saved and the
            //canvas has not been edited since last save, exit the program
        } else {
            Platform.exit();
            System.exit(0);
        }
    }

    /**
     * Used to open a file into the canvas using an Open Dialogue
     */
    //method for Open button
    public void onOpen() {

        OpenDialogue openDialogue = new OpenDialogue();
        currentFile = openDialogue.openFile(currentFile, selectedCanvas, currentGraphicsContext);

        saved = openDialogue.getSaved();
        hasBeenOpened = openDialogue.getHasBeenOpened();
        selectedCanvas.setCanvasSaved(openDialogue.getCanvasSaved());
        enableAutosave = openDialogue.getEnableAutosave();
    }

    /**
     * Upon clicking about button, opens up an alert about the program
     */
    //method for About button
    public void onAbout() {
        AboutDialogue aboutDialogue = new AboutDialogue();
        aboutDialogue.openAbout();
    }

    public void logInformation() {
        if (currentFile != null) {
            logger.log(Level.INFO, "\n" + "Current tool: " + mode + "\n"
                    + "File: " + currentFile.getName() + "\n"
                    + "Saved: " + selectedCanvas.getCanvasSaved());
        } else {
            logger.log(Level.INFO, "\n" + "Current tool: " + mode + "\n"
                    + "Saved: " + selectedCanvas.getCanvasSaved());
        }
    }

    public void doAutosave() {
        if (checkAutosave) {
            onSave();
            logger.log(Level.INFO, "\n" + "Saved");
        } else {
            logger.log(Level.INFO, "\n" + "Autosave is off");
        }
    }

    /**
     * Initializes the canvas and graphics context, also has mouse events
     *
     * @param url The location used to resolve relative paths for the root
     * object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the
     * root object was not localized.
     */
    //to initialize canvas upon program start up and what to do when mouse is used in canvas
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         vbox.setStyle( "-fx-border-style: solid;" + 
                      "-fx-border-width: 2;" +
                      "-fx-border-insets: 5;" + 
                      "-fx-border-radius: 5;" + 
                      "-fx-border-color: black;");
        //initialize canvas
        selectedCanvas = new ResizableCanvas();
        selectedCanvas.setWidth(1750);
        selectedCanvas.setHeight(1000);
        
        //initializing graphicsContext so it can be written on and setting canvas in scrollpane
        currentGraphicsContext = selectedCanvas.getGraphicsContext2D();
        sp1.setContent(selectedCanvas);
        
        //adjusting look of colorpicker and default color 
        colorPicker.getStyleClass().add("button");
        colorPicker.setValue(javafx.scene.paint.Color.BLACK);

        //setting default of mode and colorPickerFill so they do not throw Null
        mode = "No Tool";
        currentTool.setText(mode);
        colorPickerFill = colorPicker;
        backgroundSnap.setFill(Color.TRANSPARENT);
        
        //setting key shortcuts
        setKeyCombinations();

        //setting start for slider and listener to adjust zoom
        slider.setValue(1);
        slider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
            selectedCanvas.setScaleX(newvalue.doubleValue());
            selectedCanvas.setScaleY(newvalue.doubleValue());
        }); 

        //setting level of log
        logger.setLevel(Level.INFO);

        //thread for getting current tool, file, and whether the file has been saved, every 60 seconds
        Timeline logUpdate = new Timeline(new KeyFrame(
                Duration.millis(10000),
                ae -> logInformation()));

        //setting the cycle as indefinite and playing it
        logUpdate.setCycleCount(Animation.INDEFINITE);
        logUpdate.play();

        //thread for autosave every 10 seconds
        Timeline autosave = new Timeline(new KeyFrame(
                Duration.millis(30000),
                ae -> doAutosave()));

        //what to do when mouse is clicked on canvas
        selectedCanvas.setOnMousePressed(e -> {
            //setting cycle as indefinite and playing it
            if (enableAutosave) {
                autosave.setCycleCount(Animation.INDEFINITE);
                autosave.play();
            }
            //when the mode is line
            if (mode.equals("Line")) {
                currentGraphicsContext.setStroke(colorPicker.getValue()); //set color of line
                currentGraphicsContext.setLineWidth(Double.parseDouble(brushSize.getText()));
                straightLine = new StraightLine(); //instantiating object

                //giving the line object data about color, graphicsContext, and start point
                straightLine.setGraphicsContext(currentGraphicsContext);
                straightLine.setColor(colorPicker);
                straightLine.setLineWidth(Double.parseDouble(brushSize.getText()));
                onDragScreenshot = selectedCanvas.snapshot(backgroundSnap, null);
                straightLine.setStartPoint(e.getX(), e.getY());
                selectedCanvas.setCanvasSaved(false);
                
            //when the mode is free draw
            } else if (mode.equals("Free Draw")) {
                currentGraphicsContext.setStroke(colorPicker.getValue()); //set color of line
                currentGraphicsContext.setLineWidth(Double.parseDouble(brushSize.getText()));
                currentGraphicsContext.beginPath();
                currentGraphicsContext.lineTo(e.getX(), e.getY());

                freeDraw = new FreeDraw(); //instantiating object
                
                //giving free draw object data about color, graphicsContext, linewidth, start point
                freeDraw.setGraphicsContext(currentGraphicsContext);
                freeDraw.setColor(colorPicker);
                freeDraw.setLineWidth(Double.parseDouble(brushSize.getText()));
                freeDraw.setStartPoint(e.getX(), e.getY());
                selectedCanvas.setCanvasSaved(false);

                //when mode is rectangle
            } else if (mode.equals("Rectangle")) {
                currentGraphicsContext.setStroke(colorPicker.getValue()); //set color of rectangle
                currentGraphicsContext.setFill(colorPicker.getValue()); //set fill of rectangle
                currentGraphicsContext.setLineWidth(Double.parseDouble(brushSize.getText()));

                myRectangle = new MyRectangle(); //instantiating object

                //give the rectangle object data about color, graphicsContext, and start point
                myRectangle.setGraphicsContext(currentGraphicsContext);
                myRectangle.setColor(colorPicker);
                myRectangle.setFill(colorPickerFill);
                myRectangle.setLineWidth(Double.parseDouble(brushSize.getText()));
                onDragScreenshot = selectedCanvas.snapshot(backgroundSnap, null);
                myRectangle.setStartPoint(e.getX(), e.getY());
                selectedCanvas.setCanvasSaved(false);

                //when mode is square
            } else if (mode.equals("Square")) {
                currentGraphicsContext.setStroke(colorPicker.getValue()); //set color of square
                currentGraphicsContext.setFill(colorPickerFill.getValue()); //set fill of square
                currentGraphicsContext.setLineWidth(Double.parseDouble(brushSize.getText()));

                mySquare = new MySquare(); //instantiating object

                //give the square object data about color, graphicsContext, and start point
                mySquare.setGraphicsContext(currentGraphicsContext);
                mySquare.setColor(colorPicker);
                mySquare.setFill(colorPickerFill);
                mySquare.setLineWidth(Double.parseDouble(brushSize.getText()));
                onDragScreenshot = selectedCanvas.snapshot(backgroundSnap, null);
                mySquare.setStartPoint(e.getX(), e.getY());
                selectedCanvas.setCanvasSaved(false);

                //when mode is ellipse
            } else if (mode.equals("Ellipse")) {
                currentGraphicsContext.setStroke(colorPicker.getValue()); //set color of ellipse
                currentGraphicsContext.setFill(colorPickerFill.getValue()); //set fill of ellipse
                currentGraphicsContext.setLineWidth(Double.parseDouble(brushSize.getText()));

                myEllipse = new MyEllipse(); //instantiating object

                //give the ellipse object data about color, graphicsContext, and start point
                myEllipse.setGraphicsContext(currentGraphicsContext);
                myEllipse.setColor(colorPicker);
                myEllipse.setFill(colorPickerFill);
                myEllipse.setLineWidth(Double.parseDouble(brushSize.getText()));
                onDragScreenshot = selectedCanvas.snapshot(backgroundSnap, null);
                myEllipse.setCenterPoint(e.getX(), e.getY());
                selectedCanvas.setCanvasSaved(false);

                //when mode is circle
            } else if (mode.equals("Circle")) {
                currentGraphicsContext.setStroke(colorPicker.getValue()); //set color of circle
                currentGraphicsContext.setFill(colorPickerFill.getValue()); //set fill of circle
                currentGraphicsContext.setLineWidth(Double.parseDouble(brushSize.getText()));

                myCircle = new MyCircle(); //instantiating object

                //give the circle object data about color, graphicsContext, and start point
                myCircle.setGraphicsContext(currentGraphicsContext);
                myCircle.setColor(colorPicker);
                myCircle.setFill(colorPickerFill);
                myCircle.setLineWidth(Double.parseDouble(brushSize.getText()));
                onDragScreenshot = selectedCanvas.snapshot(backgroundSnap, null);
                myCircle.setCenterPoint(e.getX(), e.getY());
                selectedCanvas.setCanvasSaved(false);

                //when mode is text box
            } else if (mode.equals("Text Box")) {
                textBox = new MyTextBox(); //instantiating object

                //give the text box object data about graphicsContext, positions of click
                textBox.setGraphicsContext(currentGraphicsContext);
                textBox.setPositionX(e.getX());
                textBox.setPositionY(e.getY());

                //draw text box, set save of canvas, and put it in stack
                textBox.draw();
                selectedCanvas.setCanvasSaved(false);
                undoStack.push(textBox);
                
                //when mode is eraser
            } else if (mode.equals("Eraser")) {
                currentGraphicsContext.setLineWidth(Double.parseDouble(brushSize.getText())); //setting line width for eraser

                myEraser = new MyEraser(); //instantiating object
                
                //give the eraser object data about graphicsContext, what it looked like before eraser, line width
                myEraser.setGraphicsContext(currentGraphicsContext);
                Image image = selectedCanvas.snapshot(backgroundSnap, null);
                myEraser.whenEraserStart(image);
                
                //start erasing
                currentGraphicsContext.clearRect(e.getX(), e.getY(), Double.parseDouble(brushSize.getText()), Double.parseDouble(brushSize.getText()));
                
                //when mode is triangle
            } else if (mode.equals("Triangle")) {
                currentGraphicsContext.setStroke(colorPicker.getValue()); //setting color for triangle
                currentGraphicsContext.setFill(colorPickerFill.getValue()); //setting fill of triangle
                currentGraphicsContext.setLineWidth(Double.parseDouble(brushSize.getText())); //setting width of triangle

                triangle = new MyTriangle(); //instantiating object
                
                //give the triangle object data about graphics context, color, line width, starting points
                triangle.setGraphicsContext(currentGraphicsContext);
                triangle.setColorPicker(colorPicker);
                triangle.setColorPickerFill(colorPickerFill);
                triangle.setLineWidth(Double.parseDouble(brushSize.getText()));
                onDragScreenshot = selectedCanvas.snapshot(backgroundSnap, null);
                triangle.setStartX(e.getX());
                triangle.setStartY(e.getY());

                selectedCanvas.setCanvasSaved(false);
                
                //when mode is selection
            } else if (mode.equals("Selection")) {
                mySelection = new MySelection(); //instantiate object
                
                //give selection object data about graphics context, canvas, start image, start point
                mySelection.setGraphicsContext(currentGraphicsContext); 
                mySelection.setCanvas(selectedCanvas);
                onDragScreenshot = selectedCanvas.snapshot(backgroundSnap, null);
                mySelection.setStartImage(onDragScreenshot);
                mySelection.setStartPoint(e.getX(), e.getY());
                selectedCanvas.setCanvasSaved(false);

                //when mode is paste
            } else if (mode.equals("Paste")) {
                mySelection.setPastePoints(e.getX(), e.getY()); //where to paste the selected object
                mySelection.paste();
                
                //image for undo stack
                Image image = selectedCanvas.snapshot(backgroundSnap, null);
                mySelection.setEndImage(image);
                undoStack.push(mySelection);
                
                //set the mode to no tool in order to fix bugs related to copy/paste
                mode = "No Tool";
                currentTool.setText(mode);
                
                //when mode is polygon
            } else if (mode.equals("Polygon")) {
                currentGraphicsContext.setStroke(colorPicker.getValue()); //set color of line
                currentGraphicsContext.setFill(colorPicker.getValue()); //set the fill
                currentGraphicsContext.setLineWidth(Double.parseDouble(brushSize.getText())); //set width of polygon

                myPolygon = new MyPolygon(); //instantiate object
                
                //give the polygon object the graphics context, color, fill, lin width, and start point
                myPolygon.setGraphicsContext(currentGraphicsContext);
                myPolygon.setColorPicker(colorPicker);
                myPolygon.setColorPickerFill(colorPickerFill);
                myPolygon.setLineWidth(Double.parseDouble(brushSize.getText()));
                myPolygon.addPoint(e.getX(), e.getY());

                selectedCanvas.setCanvasSaved(false);
            } 
        });

        //what to do when mouse is pressed and dragged
        selectedCanvas.setOnMouseDragged(e -> {

            //when mode is free draw
            if (mode.equals("Free Draw")) {
                currentGraphicsContext.lineTo(e.getX(), e.getY());
                currentGraphicsContext.stroke();

                freeDraw.addPoint(e.getX(), e.getY());
                selectedCanvas.setCanvasSaved(false);

                //clear a rectangle at the given coordinate, size = brush size
            } else if (mode.equals("Eraser")) {
                currentGraphicsContext.clearRect(e.getX(), e.getY(), Double.parseDouble(brushSize.getText()), Double.parseDouble(brushSize.getText()));

                //rest of onMouseDragged is same as onMouseReleased, so see comments on that below
            } else if (mode.equals("Triangle")) {
                triangle.setEndX(e.getX());
                triangle.setEndY(e.getY());

                currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight()); //clear canvas, then redraw it each time
                currentGraphicsContext.drawImage(onDragScreenshot, 0, 0);
                triangle.draw();

            } else if (mode.equals("Line")) {
                straightLine.setEndPoint(e.getX(), e.getY());

                currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
                currentGraphicsContext.drawImage(onDragScreenshot, 0, 0);
                straightLine.draw();

            } else if (mode.equals("Rectangle")) {
                myRectangle.setEndPoint(e.getX(), e.getY());

                currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
                currentGraphicsContext.drawImage(onDragScreenshot, 0, 0);
                myRectangle.setWidth();
                myRectangle.setHeight();
                myRectangle.check();

                myRectangle.draw();

            } else if (mode.equals("Square")) {
                mySquare.setEndPoint(e.getX(), e.getY());

                currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
                currentGraphicsContext.drawImage(onDragScreenshot, 0, 0);
                mySquare.setWidth();
                mySquare.setHeight();
                mySquare.check();

                mySquare.draw();

            } else if (mode.equals("Ellipse")) {
                myEllipse.setEndPoint(e.getX(), e.getY());

                currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
                currentGraphicsContext.drawImage(onDragScreenshot, 0, 0);
                myEllipse.setRadius();
                myEllipse.check();

                myEllipse.draw();
            } else if (mode.equals("Circle")) {
                myCircle.setEndPoint(e.getX(), e.getY());

                currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
                currentGraphicsContext.drawImage(onDragScreenshot, 0, 0);
                myCircle.setRadius();
                myCircle.check();

                myCircle.draw();
            } else if (mode.equals("Polygon")) {
                myPolygon.addPoint(e.getX(), e.getY());
            } else if (mode.equals("Selection")) {
                mySelection.onDragClear(e.getX(), e.getY());
            }

        });

        //what to do when mouse is pressed and then released
        selectedCanvas.setOnMouseReleased(e -> {
            //when mode is free draw
            if (mode.equals("Free Draw")) {
                currentGraphicsContext.lineTo(e.getX(), e.getY());
                currentGraphicsContext.stroke();

                freeDraw.setEndPoint(e.getX(), e.getY());
                undoStack.push(freeDraw);
                selectedCanvas.setCanvasSaved(false);

                //when mode is line
            } else if (mode.equals("Line")) {
                straightLine.setEndPoint(e.getX(), e.getY()); //set where line ends
                currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
                redrawCanvas();
                straightLine.draw(); //put line on canvas
                undoStack.push(straightLine);
                selectedCanvas.setCanvasSaved(false);

                //when mode is rectangle
            } else if (mode.equals("Rectangle")) {
                myRectangle.setEndPoint(e.getX(), e.getY()); //set where mouse ends
                currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
                redrawCanvas();
                //set width and height
                myRectangle.setWidth();
                myRectangle.setHeight();
                myRectangle.check();

                myRectangle.draw(); //put rectangle on canvas
                undoStack.push(myRectangle);
                selectedCanvas.setCanvasSaved(false);

                //when mode is square
            } else if (mode.equals("Square")) {
                mySquare.setEndPoint(e.getX(), e.getY()); //set where mouse ends
                currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
                redrawCanvas();
                //set width and height
                mySquare.setWidth();
                mySquare.setHeight();
                mySquare.check();

                mySquare.draw(); //put square on canvas
                undoStack.push(mySquare);
                selectedCanvas.setCanvasSaved(false);

                //when mode is ellipse
            } else if (mode.equals("Ellipse")) {
                myEllipse.setEndPoint(e.getX(), e.getY()); //set where mouse ends
                currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
                redrawCanvas();
                //set radius for ellipse
                myEllipse.setRadius();
                myEllipse.check();

                myEllipse.draw(); //put ellipse on canvas
                undoStack.push(myEllipse);
                selectedCanvas.setCanvasSaved(false);

                //when mode is circle
            } else if (mode.equals("Circle")) {
                myCircle.setEndPoint(e.getX(), e.getY()); //set where mouse ends
                currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
                redrawCanvas();
                //set radius for circle
                myCircle.setRadius();
                myCircle.check();

                myCircle.draw(); //put on canvas
                undoStack.push(myCircle);
                selectedCanvas.setCanvasSaved(false);
                
                //when mode is triangle
            } else if (mode.equals("Triangle")) {
                //set end points of triangle
                triangle.setEndX(e.getX());
                triangle.setEndY(e.getY());
                
                //clear the canvas and redraw it with the triangle
                currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
                redrawCanvas();
                triangle.draw();
                undoStack.push(triangle);
                selectedCanvas.setCanvasSaved(false);
                
                //when the mode is polygon
            } else if (mode.equals("Polygon")) {
                myPolygon.addPoint(e.getX(), e.getY()); //add start points to the polygon
                myPolygon.draw(); 

                undoStack.push(myPolygon);
                selectedCanvas.setCanvasSaved(false);
                
                //when the mode is eraser
            } else if (mode.equals("Eraser")) {
                Image image = selectedCanvas.snapshot(backgroundSnap, null); //snapshot after eraser is done
                myEraser.whenEraserEnd(image); //send final snapshot to eraser
                undoStack.push(myEraser);
                selectedCanvas.setCanvasSaved(false);
                
                //when mode is selection
            } else if (mode.equals("Selection")) {
                mySelection.setEndPoint(e.getX(), e.getY()); //set end of selection
                
                // set necessary attributes for selection (similar to rectangle)
                mySelection.setWidth();
                mySelection.setHeight();
                mySelection.check();
                redrawCanvas();
                mySelection.clearSection();
                
                //set mode to paste for user to paste in the selected area
                mode = "Paste"; 
                currentTool.setText(mode);

                
            }

        }); 

    }
    
    
    //following 9 methods are for changing the mode when selected tools
    /**
     * Sets the current mode to Rectangle
     */
    
    public void onRectangle() {
        mode = "Rectangle";
        currentTool.setText(mode);
    }

    /**
     * Sets the current mode to Free Draw
     */
    
    public void onFreeDraw() {
        mode = "Free Draw";
        currentTool.setText(mode);
    }

    /**
     * Sets the current mode to Square
     */
    
    public void onSquare() {
        mode = "Square";
        currentTool.setText(mode);
    }

    /**
     * Sets the mode to Ellipse
     */
    
    public void onEllipse() {
        mode = "Ellipse";
        currentTool.setText(mode);
    }

    /**
     * Sets the mode to Circle
     */
    
    public void onCircle() {
        mode = "Circle";
        currentTool.setText(mode);
    }

    /**
     * Sets the mode to Line
     */
    
    public void onLine() {
        mode = "Line";
        currentTool.setText(mode);
    }

    /**
     * Sets the mode to No Tool
     */
    
    public void onNoTool() {
        mode = "No Tool";
        currentTool.setText(mode);
    }

    /**
     * Sets the mode to Text Box
     */
    
    public void onTextBox() {
        mode = "Text Box";
        currentTool.setText(mode);
    }

    /**
     * Sets the mode to Eraser
     */
    
    public void onEraser() {
        mode = "Eraser";
        currentTool.setText(mode);
    }

    /**
     * Sets the mode to Triangle
     */
    
    public void onTriangle() {
        mode = "Triangle";
        currentTool.setText(mode);
    }

    /**
     * Sets the mode to Select Tool
     */
    
    public void onSelection() {
        mode = "Selection";
        currentTool.setText(mode);
    }

    /**
     * Sets the mode to Polygon
     */
    
    public void onPolygon() {
        mode = "Polygon";
        currentTool.setText(mode);
    }

    /**
     * Sets the fill to the currently chosen color
     */
    
    //when filled button is selected under fill
    public void onFill() {
        colorPickerFill = colorPicker; //set fill to whatever current color is
    }

    /**
     * clears the canvas
     */
    public void onClear() {
        currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
        onDragScreenshot = null;
        undoStack.clear();
        redoStack.clear();
    } 


    /**
     * Sets the fill to transparent, making the shape unfilled
     */
    //when transparent button is selected under fill
    
    public void onTransparent() {
        colorPickerFill = new ColorPicker(Color.TRANSPARENT); //set fill to transparent
    }

    public void onAutosaveOff() {
        checkAutosave = false;
    }

    public void onAutosaveOn() {
        checkAutosave = true;
    }

    /**
     * Iterates through undo stack, putting in temporary stack minus the first
     * element Reverses temporary stack, goes through it and puts on canvas
     */
    
    //what to do when clicking undo
    public void onUndo() {

        if (!undoStack.empty()) {
            //remove first element
            MyShapes pulledShape = undoStack.pop();
            redoStack.push(pulledShape);
            currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
            if (pulledShape.getClass() == MyEraser.class) {
                MyEraser tempMyEraser = (MyEraser) pulledShape;
                tempMyEraser.drawUndo();
            } else if (pulledShape.getClass() == MySelection.class) {
                MySelection tempMySelection = (MySelection) pulledShape;
                tempMySelection.onUndo();
            } else {
                //to hold what is in undoStack so that we do not remove anything else from it
                //this is explained a bit more below
                Stack<MyShapes> tempStack = new Stack<>();

                //used to loop through undoStack
                Iterator iterator = undoStack.iterator();

                //putting what is in undoStack into tempStack starting at 1st element
                while (iterator.hasNext()) {
                    tempStack.push((MyShapes) iterator.next());
                }

                //reverses stack to put in order of when shapes were put on canvas
                Collections.reverse(tempStack);
                while (!tempStack.isEmpty()) {
                    //grabs the first element from the temporary stack and removes it for next loop
                    //used so that we do not remove anything from undoStack and have to put it back in
                    MyShapes tempShape = tempStack.pop();

                    if (tempShape.getClass() == MyEraser.class) {
                        MyEraser tempMyEraser = (MyEraser) tempShape;
                        tempMyEraser.drawRedo();
                        redrawCanvas();
                        tempStack.clear();
                    } else if (tempShape.getClass() == MySelection.class) {
                        MySelection tempMySelection = (MySelection) tempShape;
                        tempMySelection.onUndo();
                        redrawCanvas();
                        tempStack.clear();
                    } else {
                        //use the current shape and draw it back on the canvas
                        if (tempShape.getClass() == FreeDraw.class) {
                            FreeDraw tempFreeDraw = (FreeDraw) tempShape;
                            tempFreeDraw.draw();
                        } else if (tempShape.getClass() == StraightLine.class) {
                            StraightLine tempStraightLine = (StraightLine) tempShape;
                            tempStraightLine.draw();
                        } else if (tempShape.getClass() == MyRectangle.class) {
                            MyRectangle tempRectangle = (MyRectangle) tempShape;
                            tempRectangle.draw();
                        } else if (tempShape.getClass() == MyEllipse.class) {
                            MyEllipse tempEllipse = (MyEllipse) tempShape;
                            tempEllipse.draw();
                        } else if (tempShape.getClass() == MySquare.class) {
                            MySquare tempSquare = (MySquare) tempShape;
                            tempSquare.draw();
                        } else if (tempShape.getClass() == MyCircle.class) {
                            MyCircle tempCircle = (MyCircle) tempShape;
                            tempCircle.draw();
                        } else if (tempShape.getClass() == MyTextBox.class) {
                            MyTextBox tempTextBox = (MyTextBox) tempShape;
                            tempTextBox.draw();
                        } else if (tempShape.getClass() == MyTriangle.class) {
                            MyTriangle tempMyTriangle = (MyTriangle) tempShape;
                            tempMyTriangle.draw();
                        } else if (tempShape.getClass() == MyPolygon.class) {
                            MyPolygon tempMyPolygon = (MyPolygon) tempShape;
                            tempMyPolygon.draw();
                        }
                    }
                }
            }
        }
    }

    /**
     * Iterates through redo stack, putting it into a temporary stack. Goes
     * through temporary stack and places it on canvas
     */
    
    public void onRedo() {

        if (!redoStack.empty()) {
            MyShapes removedShape = redoStack.pop();
            undoStack.push(removedShape);
            if (removedShape.getClass() == MyEraser.class) {
                currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
                MyEraser tempMyEraser = (MyEraser) removedShape;
                tempMyEraser.drawRedo();
            } else if (removedShape.getClass() == MySelection.class) {
                currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
                MySelection tempMySelection = (MySelection) removedShape;
                tempMySelection.onRedo();
            } else {
                Iterator iterator = undoStack.iterator();
                Stack<MyShapes> tempStack = new Stack<>();

                while (iterator.hasNext()) {
                    tempStack.push((MyShapes) iterator.next());
                }

                Collections.reverse(tempStack);
                int x = 1;
                while (!tempStack.isEmpty() && x == 1) {
                    //grabs the first element from the temporary stack and removes it for next loop
                    //used so that we do not remove anything from undoStack and have to put it back in
                    MyShapes tempShape = tempStack.pop();
                    if (tempShape.getClass() == MyEraser.class) {
                        currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
                        MyEraser tempMyEraser = (MyEraser) tempShape;
                        tempMyEraser.drawRedo();

                    } else if (tempShape.getClass() == MySelection.class) {
                        currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
                        MySelection tempMySelection = (MySelection) tempShape;
                        tempMySelection.onRedo();

                    } else {
                        //use the current shape and draw it back on the canvas
                        if (tempShape.getClass() == FreeDraw.class) {
                            FreeDraw tempFreeDraw = (FreeDraw) tempShape;
                            tempFreeDraw.draw();
                        } else if (tempShape.getClass() == StraightLine.class) {
                            StraightLine tempStraightLine = (StraightLine) tempShape;
                            tempStraightLine.draw();
                        } else if (tempShape.getClass() == MyRectangle.class) {
                            MyRectangle tempRectangle = (MyRectangle) tempShape;
                            tempRectangle.draw();
                        } else if (tempShape.getClass() == MyEllipse.class) {
                            MyEllipse tempEllipse = (MyEllipse) tempShape;
                            tempEllipse.draw();
                        } else if (tempShape.getClass() == MySquare.class) {
                            MySquare tempSquare = (MySquare) tempShape;
                            tempSquare.draw();
                        } else if (tempShape.getClass() == MyCircle.class) {
                            MyCircle tempCircle = (MyCircle) tempShape;
                            tempCircle.draw();
                        } else if (tempShape.getClass() == MyTextBox.class) {
                            MyTextBox tempTextBox = (MyTextBox) tempShape;
                            tempTextBox.draw();
                        } else if (tempShape.getClass() == MyTriangle.class) {
                            MyTriangle tempMyTriangle = (MyTriangle) tempShape;
                            tempMyTriangle.draw();
                        }
                    }
                }
            }
        }
    }

    /**
     * Redraws the canvas as a line is being dragged
     */
    //same as undo, except it does not remove the first element from undoStack
    
    public void redrawCanvas() {
        Iterator iterator = undoStack.iterator();
        Stack<MyShapes> tempStack = new Stack<>();

        while (iterator.hasNext()) {
            tempStack.push((MyShapes) iterator.next());
        }

        Collections.reverse(tempStack);

        while (!tempStack.isEmpty()) {
            //grabs the first element from the temporary stack and removes it for next loop
            //used so that we do not remove anything from undoStack and have to put it back in
            MyShapes tempShape = tempStack.pop();
            if (tempShape.getClass() == MyEraser.class) {
                currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
                MyEraser tempMyEraser = (MyEraser) tempShape;
                tempMyEraser.drawRedo();

            } else if (tempShape.getClass() == MySelection.class) {
                currentGraphicsContext.clearRect(0, 0, selectedCanvas.getWidth(), selectedCanvas.getHeight());
                MySelection tempMySelection = (MySelection) tempShape;
                tempMySelection.onRedo();

            } else {
                //use the current shape and draw it back on the canvas
                if (tempShape.getClass() == FreeDraw.class) {
                    FreeDraw tempFreeDraw = (FreeDraw) tempShape;
                    tempFreeDraw.draw();
                } else if (tempShape.getClass() == StraightLine.class) {
                    StraightLine tempStraightLine = (StraightLine) tempShape;
                    tempStraightLine.draw();
                } else if (tempShape.getClass() == MyRectangle.class) {
                    MyRectangle tempRectangle = (MyRectangle) tempShape;
                    tempRectangle.draw();
                } else if (tempShape.getClass() == MyEllipse.class) {
                    MyEllipse tempEllipse = (MyEllipse) tempShape;
                    tempEllipse.draw();
                } else if (tempShape.getClass() == MySquare.class) {
                    MySquare tempSquare = (MySquare) tempShape;
                    tempSquare.draw();
                } else if (tempShape.getClass() == MyCircle.class) {
                    MyCircle tempCircle = (MyCircle) tempShape;
                    tempCircle.draw();
                } else if (tempShape.getClass() == MyTextBox.class) {
                    MyTextBox tempTextBox = (MyTextBox) tempShape;
                    tempTextBox.draw();
                } else if (tempShape.getClass() == MyTriangle.class) {
                    MyTriangle tempMyTriangle = (MyTriangle) tempShape;
                    tempMyTriangle.draw();
                }
            }
        }

    }

    /**
     * Provides a pop up for resizing the canvas
     */
    //pop up to resize the canvas
    public void onResize() {
        ResizeDialogue resizeDialogue = new ResizeDialogue();
        selectedCanvas = resizeDialogue.doResize(selectedCanvas);
    }

    /**
     * set of key combinations for all buttons in Controller
     */
    //setting key combinations for all the buttons
    public void setKeyCombinations() {
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        saveAs.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        fileFxml.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN));
        undo.setAccelerator(new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN));
        quit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        resize.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        fDraw.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN));
        lDraw.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.ALT_DOWN));
        sDraw.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN));
        rDraw.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.ALT_DOWN));
        cDraw.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN));
        eDraw.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.ALT_DOWN));
        filled.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));
        transparent.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN));
        about.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.ALT_DOWN));
        help.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN));
        tDraw.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.ALT_DOWN));
        noTool.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.ALT_DOWN));
        toolEraser.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.ALT_DOWN));
        triDraw.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCombination.ALT_DOWN));
        redo.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
        polygon.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.ALT_DOWN));
        selection.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.ALT_DOWN));
        clear.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        colorPicker.setTooltip(new Tooltip("Used to select the color of the drawing. Select this button to open up more options."));
        menuButton.setTooltip(new Tooltip("This is a list of all the tools you can use."));
    }
}