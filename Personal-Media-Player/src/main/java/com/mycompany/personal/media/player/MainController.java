package com.mycompany.personal.media.player;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class MainController implements Initializable {
    
    private MediaPlayer mediaPlayer;
    
    @FXML
    private MediaView mediaView;
    
    @FXML
    private Slider audioSlider, videoSlider;
    
    @FXML
    private Button openfileButton, playButton, pauseButton, stopButton, exitButton, slowButton, slowerButton, fastButton, fasterButton;
       
    private String pathVideo;
    
    @FXML
    public void openFile()
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.mp4)", "*.mp4");
            fileChooser.getExtensionFilters().add(filter);
            File file = fileChooser.showOpenDialog(null);
            pathVideo = file.toURI().toString();
            
            if(pathVideo != null)
            {
                Media media = new Media(pathVideo);
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);              
                DoubleProperty width = mediaView.fitWidthProperty();
                DoubleProperty height = mediaView.fitHeightProperty();                    
                width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height")); 
                audioSlider.setValue(mediaPlayer.getVolume() * 100);
                audioSlider.valueProperty().addListener(new InvalidationListener(){
                    @Override
                    public void invalidated(Observable observable)
                    {
                        mediaPlayer.setVolume(audioSlider.getValue()/100);
                    }
                });
                mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>(){
                    @Override
                    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue)
                    {
                        videoSlider.setValue(newValue.toSeconds());
                    }
                });
                videoSlider.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event)
                    {
                        mediaPlayer.seek(Duration.seconds(videoSlider.getValue()));
                    }
                });
                mediaPlayer.play();
            }
    }
    
    public void playVideo()
    {
        mediaPlayer.play();
        mediaPlayer.setRate(0);
    }
    
    public void pauseVideo()
    {
        mediaPlayer.pause();
    }
    
    public void stopVideo()
    {
        mediaPlayer.stop();
    }
    
    public void slowVideo()
    {
        mediaPlayer.setRate(.75);
    }
    
    public void slowerVideo()
    {
        mediaPlayer.setRate(.5);
    }
    
    public void fastVideo()
    {
        mediaPlayer.setRate(1.5);
    }
    
    public void fasterVideo()
    {
        mediaPlayer.setRate(2);
    }
    
    public void exit()
    {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }       
}
