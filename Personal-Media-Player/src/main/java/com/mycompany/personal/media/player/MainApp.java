package com.mycompany.personal.media.player;

import java.awt.event.MouseEvent;
import java.beans.EventHandler;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        scene.setMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent doubleClicked)
            {
               if(doubleClicked.getClickCount() == 2)
               {
                   stage.setFullScreen(true);
               }
            }
        })
        stage.setTitle("Personal Media Player");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
