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
import javafx.scene.media.MediaPlayer.Status;
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
    private Label timeLabel;
    
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
                
                //Resize window
                resizeWindow();
                
                //moving audio with audio slider
                moveAudioWithSlider();
                
                //video slider moving together with time from video
                moveSliderWithTime();
                
                //video slider take all time from video
                setTotalTimeToSlider();
                            
                //moving with double click in video slider
                moveSliderWithDClick();
                              
                //Method for Time Label
                setTime();
                
                mediaPlayer.play();                              
            }
    }
    
    private void resizeWindow()
    {
        DoubleProperty width = mediaView.fitWidthProperty();
        DoubleProperty height = mediaView.fitHeightProperty();                    
        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
    }
    
    private void moveAudioWithSlider()
    {
        audioSlider.setValue(mediaPlayer.getVolume() * 100);
        audioSlider.valueProperty().addListener((Observable observable) -> {
            mediaPlayer.setVolume(audioSlider.getValue()/100);
        });
    }
    
    private void moveSliderWithTime()
    {
        mediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
            videoSlider.setValue(newValue.toSeconds());                    
        });
    }
    
    private void setTotalTimeToSlider()
    {
//        Status status = mediaPlayer.getStatus();
//        if(status == Status.READY)
//        {
            videoSlider.maxProperty().bind(Bindings.createDoubleBinding( 
                () -> mediaPlayer.getTotalDuration().toSeconds(), 
                mediaPlayer.totalDurationProperty()));
//        }       
    }
    
    private void moveSliderWithDClick()
    {
        videoSlider.setOnMouseClicked((MouseEvent event) -> {
            mediaPlayer.seek(Duration.seconds(videoSlider.getValue()));
         });
    }
    
    //Time Label
    private void setTime()
    {
        mediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
            timeLabel.setText(getTime(newValue,mediaPlayer.getTotalDuration()));                    
        });
    }

    private String getTime(Duration elapsed, Duration duration)
    {
       int intElapsed = (int)Math.floor(elapsed.toSeconds());
       int elapsedHours = intElapsed / (60 * 60);
       if (elapsedHours > 0) {
           intElapsed -= elapsedHours * 60 * 60;
       }
       int elapsedMinutes = intElapsed / 60;
       int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 
                               - elapsedMinutes * 60;

       if (duration.greaterThan(Duration.ZERO)) {
          int intDuration = (int)Math.floor(duration.toSeconds());
          int durationHours = intDuration / (60 * 60);
          if (durationHours > 0) {
             intDuration -= durationHours * 60 * 60;
          }
          int durationMinutes = intDuration / 60;
          int durationSeconds = intDuration - durationHours * 60 * 60 - 
              durationMinutes * 60;
          if (durationHours > 0) {
             return String.format("%d:%02d:%02d/%d:%02d:%02d", 
                elapsedHours, elapsedMinutes, elapsedSeconds,
                durationHours, durationMinutes, durationSeconds);
          } else {
              return String.format("%02d:%02d/%02d:%02d",
                elapsedMinutes, elapsedSeconds,durationMinutes, 
                    durationSeconds);
          }
          } else {
              if (elapsedHours > 0) {
                 return String.format("%d:%02d:%02d", elapsedHours, 
                        elapsedMinutes, elapsedSeconds);
                } else {
                    return String.format("%02d:%02d",elapsedMinutes, 
                        elapsedSeconds);
                }
        }
    }
    //End Time Label
    
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
