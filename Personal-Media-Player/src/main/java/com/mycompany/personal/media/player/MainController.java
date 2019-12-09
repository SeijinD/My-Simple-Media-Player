package com.mycompany.personal.media.player;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class MainController implements Initializable {
    
    private String pathVideo;
    private MediaPlayer mediaPlayer;
    private Media media;
    boolean flag = false;
    
    @FXML
    private MediaView mediaView;
    
    @FXML
    private Slider audioSlider, videoSlider;
    
    @FXML
    private Label startTimeLabel, endTimeLabel;
    
    @FXML
    private Button openfileButton, playButton, pauseButton, stopButton, exitButton, slowButton, slowerButton, fastButton, fasterButton, autoButton;  
   
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
                media = new Media(pathVideo);
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);              
                DoubleProperty width = mediaView.fitWidthProperty();
                DoubleProperty height = mediaView.fitHeightProperty();                    
                width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
                
                //moving audio with audio slider
                audioSlider.setValue(mediaPlayer.getVolume() * 100);
                audioSlider.valueProperty().addListener((Observable observable) -> {
                    mediaPlayer.setVolume(audioSlider.getValue()/100);
                });
                
                //video slider moving together with time from video
                mediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
                    videoSlider.setValue(newValue.toSeconds());                    
                });
                
                //video slider take all time from video
                videoSlider.maxProperty().bind(Bindings.createDoubleBinding( 
                            () -> mediaPlayer.getTotalDuration().toSeconds(), 
                            mediaPlayer.totalDurationProperty()));
                            
                //moving with double click in video slider
                videoSlider.setOnMouseClicked((MouseEvent event) -> {
                    mediaPlayer.seek(Duration.seconds(videoSlider.getValue()));
                });
                
                setMusicTimeEnd();
                
                mediaPlayer.play();                              
            }
    }
    
    private void setMusicTimeEnd()
    {
        mediaPlayer.setOnReady(new Runnable() {
        @Override
        public void run() 
        {
            double time = mediaPlayer.getTotalDuration().toSeconds();
            endTimeLabel.setText(getTime(time));
        }
        });
    }

    private String getTime(double time)
    {
        double init = Math.round(time);
        double hours = init / 3600;
        double minutes = ((init / 60) % 60);
        double seconds = init % 60;
        String Time = "";

        if (hours > 0.5)
        {
            Time = Math.round(hours) + " : " + Math.round(minutes) + " : "  +   Math.round(seconds);
        }
        else
        {
            Time = Math.round(minutes) + " : " + Math.round(seconds);
        }
        return Time;
    }
    
    @FXML
    public void playVideo()
    {
        mediaPlayer.play();
        mediaPlayer.setRate(1);        
    }
    
    @FXML
    public void pauseVideo()
    {
        mediaPlayer.pause();
    }
    
    @FXML
    public void stopVideo()
    {
        mediaPlayer.stop();
    }
    
    @FXML
    public void slowVideo()
    {
        mediaPlayer.setRate(.75);
    }
    
    @FXML
    public void slowerVideo()
    {
        mediaPlayer.setRate(.5);
    }
    
    @FXML
    public void fastVideo()
    {
        mediaPlayer.setRate(1.5);
    }
    
    @FXML
    public void fasterVideo()
    {
        mediaPlayer.setRate(2);
    }
    
    @FXML
    public void autoVideo()
    {   
        if(!flag)
        {
             mediaPlayer.setOnEndOfMedia(() -> {
                 mediaPlayer.seek(Duration.ZERO);
             });
            flag = true;
        }
        else
        {
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.pause();
            });         
            flag = false;
        }
    }
    
    @FXML
    public void exit()
    {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }       
}
