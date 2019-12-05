package com.mycompany.personal.media.player;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

public class MainController implements Initializable {
    
    @FXML
    private MediaPlayer mediaPlayer;
    
    @FXML
    private Button openfileButton, playButton, pauseButton, stopButton, exitButton;
       
    private String pathVideo;
    
    @FXML
    public void openFile()
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.mp4)", "*.mp4");
            fileChooser.getExtensionFilters().add(filter);
            File file = fileChooser.showOpenDialog(null);
            pathVideo = file.toURI().toString();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }       
}
