package my_simple_media_player;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class My_Simple_Media_Player extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            
            stage.show();
            
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
