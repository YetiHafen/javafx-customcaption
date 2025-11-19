package net.yetihafen.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.yetihafen.javafx.customcaption.CaptionConfiguration;
import net.yetihafen.javafx.customcaption.CustomCaption;


public class DemoApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        // initialize FXMLLoader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("complex-application.fxml"));
        // load the contents of the fxml file
        Parent root = loader.load();

        // create scene with loaded contents
        Scene scene = new Scene(root);

        // start stage with specified scene
        stage.setScene(scene);
        stage.setTitle("customcaption-demo");
        stage.show();


        // get MenuBar to supply as DragRegion
        MenuBar bar = (MenuBar) loader.getNamespace().get("menuBar");

        // apply customizations
        CustomCaption.useForStage(stage, new CaptionConfiguration()
                        .setIconColor(Color.BLACK)  // set the icon/foreground color to black
                        .setCaptionDragRegion(bar)  // set the MenuBar as DragRegion to exclude the
                                                    // buttons automatically
        );
    }

    public static void main(String[] args) {
        launch();
    }
}