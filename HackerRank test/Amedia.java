package amedia;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import javafx.util.Duration;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javax.swing.DefaultListModel;

/**
 *
 * @author AnaGeorgia
 */
public class FXMLDocumentController implements Initializable {
    
    
    private int index;
    private MediaPlayer mediaPlayer;
    
    @FXML
    private MediaView mediaView;
    
   
    private String filePath;
    
    @FXML
    
    private Slider slider;
    
    @FXML
    private Slider seekSlider;
    @FXML
    private ListView<String> listview;
    @FXML
    
    private void handleButtonAction(ActionEvent event) {
       FileChooser fileChooser = new FileChooser();
       FileChooser.ExtensionFilter filter= new FileChooser.ExtensionFilter("Select a File (*.mp4,*mp3)","*.mp4","*.mp3");
        fileChooser.getExtensionFilters().add(filter);
        File file=fileChooser.showOpenDialog(null);
        filePath=file.toURI().toString();
        if(filePath !=null)
            playFile(filePath);
    }
    @FXML
    private void pauseVideo(ActionEvent event){
    
        mediaPlayer.pause();
    }
    @FXML
    private void playVideo(ActionEvent event){
    
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }
    @FXML
    private void stopVideo(ActionEvent event){
        mediaPlayer.stop();
    
    }
    @FXML
    private void fastVideo(ActionEvent event){
        mediaPlayer.setRate(1.5);
    
    }
    @FXML
    private void fasterVideo(ActionEvent event){
        mediaPlayer.setRate(2);
    
    }
    @FXML
    private void slowVideo(ActionEvent event){
        mediaPlayer.setRate(.75);
    
    }
    @FXML
    private void slowerVideo(ActionEvent event){
        mediaPlayer.setRate(.5);
    
    }
    @FXML
    private void exitVideo(ActionEvent event){
        System.exit(0);
    
    }

    @FXML
    
    
    ArrayList<String> mp3Paths = new ArrayList();
    
    public void getMp3s(File f) {
        File[] files;
        if (f.isDirectory() && (files = f.listFiles()) != null) {
            for (File file : files)  {
                getMp3s(file);
            }
        }
        else {
            String path = f.getPath();
            if (path.substring(path.length()-4, path.length()).equals(".mp3")) {
                mp3Paths.add(f.getPath());

            }
        }
        for (int i=0;i<mp3Paths.size(); i++)
        {
            System.out.println(mp3Paths.get(i));
        
        }
        
}

    public void showFiles(ActionEvent event) {
    
        getMp3s(new File("C:\\Users\\AnaGeorgia\\Desktop\\songs"));
    
        ObservableList<String> data= FXCollections.observableArrayList(
    
                mp3Paths
    
    
        );
        listview.setItems(data);
        


 }
    @FXML
    public void handleMouseClick(MouseEvent arg0) {
       playFile( listview.getSelectionModel().getSelectedItem());
}
    private void playFile(String filePath){
        Media media=new Media(filePath);
        mediaPlayer=new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        DoubleProperty width = mediaView.fitWidthProperty();
        DoubleProperty height = mediaView.fitHeightProperty();

        width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));

        height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));



        slider.setValue(mediaPlayer.getVolume() * 100);
        slider.valueProperty().addListener(new InvalidationListener(){
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(slider.getValue()/100);
            }




        });

        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                seekSlider.setValue(newValue.toSeconds());
            }
        });

       seekSlider.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {

                mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
            }




       });

        mediaPlayer.play();
    }
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       
    }    
    
}