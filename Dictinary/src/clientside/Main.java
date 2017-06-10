package clientside;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("123.fxml"));
        primaryStage.setTitle("Dictionary window");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
    
            /**
             * Invoked when a specific event of the type for which this handler is
             * registered happens.
             *
             * @param event the event which occurred
             */
            @Override
            public void handle(WindowEvent event){
                System.out.println("Stage is closing");
                Platform.exit();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
