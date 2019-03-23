package my_simple_media_player;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;


public class FXMLDocumentController implements Initializable {
    
    @FXML
    private BorderPane program;
    
    @FXML
    private MediaView mv;
    
    private MediaPlayer mp;
    
    private Media me;
    
    @FXML
    private Button playVideo;
    
    @FXML
    private Button pauseVideo;
    
    @FXML
    private Button stopVideo;
    
    @FXML
    private Button openFlile;
    
    @FXML
    public void openFile(ActionEvent event){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP4 Files", "*.mp4"));
        fc.setTitle("Open File");
        File selectedFile = fc.showOpenDialog(null);
        String path = selectedFile.getAbsolutePath();
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mv.setMediaPlayer(mp);
    }
    
    @FXML
    public void playVideo(ActionEvent event){
        mp.play();
        playVideo.setDisable(true);
        pauseVideo.setDisable(false);
        stopVideo.setDisable(false);
        
    }
    
    @FXML
    public void pauseVideo(ActionEvent event){
        mp.pause();
        playVideo.setDisable(false);
        stopVideo.setDisable(false);
        pauseVideo.setDisable(true);
    }
    
    @FXML
    public void stopVideo(ActionEvent event){
        mp.stop();
        playVideo.setDisable(false);
        pauseVideo.setDisable(true);
        stopVideo.setDisable(true);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DoubleProperty width = mv.fitWidthProperty();
        DoubleProperty height = mv.fitHeightProperty();
        width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
        
    }    
    
}
