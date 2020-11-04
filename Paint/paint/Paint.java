package paint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * launches paint application
 * @author Logan
 */
public class Paint extends Application {

    private static Stage primaryStage;

    /**
     * sets GUI for paint application, connects to FXML
     * @param stage stage that GUI takes place in
     * @throws Exception for any errors that occur when launching program
     */
    @Override
    public void start(Stage stage) throws Exception {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

 
        setPrimaryStage(stage);

        //setting the scene based off of the FXML file paint.fmxl and showing
        //it when the file is opened
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("paint.fxml")));
        stage.setScene(scene);
        stage.setTitle("Paint");
        
        //set Stage boundaries to visible bounds of the main screen
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
        stage.show();
    }

    //following two methods used in order to grant access to the current stage in the Controller
    /**
     * sets primary stage so that it can be passed to other files
     * @param stage stage that GUI takes place in
     */
    private void setPrimaryStage(Stage stage) {
        Paint.primaryStage = stage;
        primaryStage.setTitle("Paint");
    }

    /**
     * used to access primary stage in other files
     * @return stage to be used in other files
     */
    public static Stage getPrimaryStage() {
        return Paint.primaryStage;
    }

    /**
     * launches program
     * @param args array of strings that are any needed arguments to launch program
     */
    public static void main(String[] args) {
        launch(args);
    }

}
