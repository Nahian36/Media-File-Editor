/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_project;

import com.jfoenix.controls.JFXDrawer;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * AudioEnhanceController controls AudioEnhance window
 * @author Nahian Ibn Asad 180041136
 */
public class AudioEnhanceController {

    public String vpath="",vpath_1="";
    public float val;
    private MediaPlayer mediaPlayer;
    private int counter=0;
    
    @FXML
    private Label error_msg;
    @FXML
    private Slider progressBar1;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private BorderPane border;

    /**
     * To open files from local drive
     * @param event ActionEvent
     */
    
    @FXML
    void chooseFiles(ActionEvent event) {
        
        if(counter > 0){
            error_msg.setText("Sorry you can enhance only once.");
            return ;
        }
        
        File f5 = new File("F:\\project_x\\audio_volume1.mp3");
        if(f5.length() != 0){
            if(f5.renameTo(new File("G:\\music.mp3"))==false)f5.delete();
        }
        
        File f6 = new File("F:\\project_x\\volume1_output1.mp3");
        if(f6.length() != 0){
            if(f6.renameTo(new File("G:\\music_enhance.mp3"))==false)f6.delete();
        }
        
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(null);
        
        if(file == null) {
            error_msg.setText("Please choose file correctly.");
            return ;
        }
        
        vpath_1 = file.getAbsolutePath();
        File f1 = new File(vpath_1);
        File f2;
        f1.renameTo(f2 = new File("F:\\project_x\\audio_volume"+Integer.toString(1)+".mp3"));
        vpath = f2.toURI().toString();
        System.out.println(vpath);
        
        if("".equals(vpath)==false){
            
            Media media = new Media(vpath);
            mediaPlayer = new MediaPlayer(media);
             //mediaView.setMediaPlayer(mediaPlayer);
            
            /*DoubleProperty widthProp = mediaView.fitWidthProperty();
            DoubleProperty heightProp = mediaView.fitHeightProperty();
            
            widthProp.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
            heightProp.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));*/
            
            mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                Duration total = media.getDuration();
                System.out.println("atiq1"+total);
                progressBar1.setMax(total.toSeconds());
            }
        });
            
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>(){
                @Override
                public void changed(ObservableValue<? extends Duration> observable,Duration oldValue, Duration newValue){
                    progressBar1.setValue(newValue.toSeconds());
                    
                    //int uptime = (int) newValue.toSeconds();
                    //System.out.println(hours+":"+minutes+":"+seconds);
                }
            });
            
          
            progressBar1.setOnMousePressed(new EventHandler<MouseEvent>() {
                
                @Override
                public void handle(MouseEvent event) {
                    
                    mediaPlayer.seek(Duration.seconds(progressBar1.getValue()));
                }
            });
            
            progressBar1.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(progressBar1.getValue()));
                }
            });
        } else {
            error_msg.setText("Please choose files correctly.");
        }
    }
    
    /**
     * To hear the enhanced audio
     * @param event ActionEvent
     */
    
    @FXML
    public void preview(ActionEvent event) {
        
        if(counter == 0) {
            error_msg.setText("Please enhance first.");
            return ;
        }
        
        File f5 = new File("F:\\project_x\\volume1_output1.mp3");
        
        if(f5.length() == 0)
        {
            error_msg.setText("Please enhance first.");
            return ;
        }
        
        vpath = f5.toURI().toString();
        
        Media media = new Media(vpath);
            mediaPlayer = new MediaPlayer(media);
             //mediaView.setMediaPlayer(mediaPlayer);
            
            /*DoubleProperty widthProp = mediaView.fitWidthProperty();
            DoubleProperty heightProp = mediaView.fitHeightProperty();
            
            widthProp.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
            heightProp.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));*/
            
            mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                Duration total = media.getDuration();
                System.out.println("atiq1"+total);
                progressBar1.setMax(total.toSeconds());
            }
        });
            
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>(){
                @Override
                public void changed(ObservableValue<? extends Duration> observable,Duration oldValue, Duration newValue){
                    progressBar1.setValue(newValue.toSeconds());
                    
                    //int uptime = (int) newValue.toSeconds();
                    //System.out.println(hours+":"+minutes+":"+seconds);
                }
            });
            
          
            progressBar1.setOnMousePressed(new EventHandler<MouseEvent>() {
                
                @Override
                public void handle(MouseEvent event) {
                    
                    mediaPlayer.seek(Duration.seconds(progressBar1.getValue()));
                }
            });
            
            progressBar1.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(progressBar1.getValue()));
                }
            });
        
    }
    
    /**
     * To play enhanced audio
     * @param event ActionEvent
     */
    
    @FXML
    public void play(ActionEvent event) {
        
        if("".equals(vpath_1)) {
            error_msg.setText("Please enhance first.");
            return ;
        }
        mediaPlayer.play();
    }
    
    /**
     * To pause enhanced audio
     * @param event ActionEvent
     */
    
    @FXML
    public void pause(ActionEvent event) {
        
        if("".equals(vpath_1)) {
            error_msg.setText("Please enhance first.");
            return ;
        }
        mediaPlayer.pause();
    }
    
    /**
     * To stop enhanced audio
     * @param event ActionEvent
     */
    
    @FXML
    public void stop(ActionEvent event) {
        
        if("".equals(vpath_1)) {
            error_msg.setText("Please enhance first.");
            return ;
        }
        mediaPlayer.stop();
    }
    
    /**
     * To set the value to half
     * @param event ActionEvent
     */
    
    @FXML
    public void xhalf(ActionEvent event){
        val = (float) 0.5;
    }
    
    /**
     * To set the value to twice
     * @param event ActionEvent
     */
    
    @FXML
    public void x2(ActionEvent event) {
        val = (float) 2;
    }
    
    /**
     * To set the value to four times
     * @param event ActionEvent
     */
    
    @FXML
    public void x4(ActionEvent event) {
        val = (float) 4;
    }
    
    /**
     * To set the value to eight times
     * @param event ActionEvent
     */
    
    @FXML
    public void x8(ActionEvent event) {
        val = (float) 8;
    }
    
    /**
     * enhance our audio
     * @param event ActionEvent
     * @throws IOException when resource is not found
     */
    
    @FXML
    public void enhance(ActionEvent event) throws IOException {
        
        if("".equals(vpath_1)){
            error_msg.setText("Please choose file.");
            return ;
        }
        
        if(counter > 0) {
            error_msg.setText("Sorry you can enhance only once.");
            return ;
        } else {
            counter++;
        }
        
        File f5 = new File("F:\\project_x\\volume1_output1.mp3");
        if(f5.length() != 0) {
            error_msg.setText("You just lost a file.");
            f5.delete();
        }
        
        String ext="";
        char c = '.';
        int j = vpath_1.length()-1;
        
        while(j>=0) {
            if(vpath_1.charAt(j)=='c')break;
            ext += vpath_1.charAt(j);
            j--;
        }
        ext = new StringBuilder(ext).reverse().toString();
        
        String directory="F:\\project_x";
        String cmd2;
        cmd2 = String.format("%s && cd/ && cd %s && ffmpeg -i audio_volume1.mp3 -filter:a \"volume=\""+val+" volume1_output1.mp3",directory.substring(0,2),directory.substring(3,directory.length()));
        System.out.println(cmd2);
        
        ProcessBuilder builder2 = new ProcessBuilder("cmd.exe","/c",cmd2);
        builder2.redirectErrorStream(true);

        try{
            Process p=builder2.start();
        
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line="";
        while (true) {
            try {
            line = r.readLine();
            }
            catch(IOException e){
               e.printStackTrace(); 
            }
            if (line == null) { break; }
            System.out.println(line);
        }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        error_msg.setText("Enhancement completed.");
    }
    
    /**
     * To go to "merge" window from "enhance" window
     * @param event ActionEvent
     * @throws IOException when resource is not found
     */
    
    @FXML
    public void merge(ActionEvent event) throws IOException {
        Parent p2 = FXMLLoader.load(getClass().getResource("AudioMerge.fxml"));
        Scene scene = new Scene(p2);
        //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        //stage.initStyle(StageStyle.TRANSPARENT);
        //stage.setTitle("VIDEO MENU");
        stage.show();
    }
    
    /**
     * To go to "trim" window from "enhance" window
     * @param event ActionEvent
     * @throws IOException when resource is not found
     */
    
    @FXML
    public void trim(ActionEvent event) throws IOException {
        Parent p2 = FXMLLoader.load(getClass().getResource("AudioTrim.fxml"));
        Scene scene = new Scene(p2);
        //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        //stage.initStyle(StageStyle.TRANSPARENT);
        //stage.setTitle("VIDEO MENU");
        stage.show();
    }
    
    /**
     * To close our window
     * @param event ActionEvent
     */
    
    @FXML
    public void close(ActionEvent event)
    {
        System.exit(1);
    }
    
    /**
     * To minimize our window
     * @param event ActionEvent
     */
    
    @FXML
    public void minimize(ActionEvent event) {
        Stage stage = (Stage)border.getScene().getWindow();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    /**
     * To maximize our window
     * @param event ActionEvent
     */
    
    @FXML
    public void maximize(ActionEvent event) {
        Stage stage = (Stage)border.getScene().getWindow();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setFullScreen(true);
    }
    
    /**
     * ham is JFXhamburger to provide the drawer function
     * @param event MouseEvent
     * @throws IOException when resource is not found
     */
    
    @FXML
    public void ham(MouseEvent event) throws IOException {
        
        
        VBox box = FXMLLoader.load(getClass().getResource("DrawerFXML.fxml"));
        drawer.setSidePane(box);
        
        for(Node node : box.getChildren()) {
            if(node.getAccessibleText()!=null) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)-> {
                   switch(node.getAccessibleText()){
                       case "video" :
                       {
                           try         
                           {
                               Parent p2 = FXMLLoader.load(getClass().getResource("VideoTrim.fxml"));
                                    Scene scene = new Scene(p2);
                                    //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                                    stage.setScene(scene);
                                    //stage.initStyle(StageStyle.TRANSPARENT);
                                    //stage.setTitle("VIDEO MENU");
                                    stage.show();
                           } catch(IOException ex) {
                               ex.printStackTrace();
                           } 
                       }
                       break;
                       
                       case "audio" :
                       {
                           error_msg.setText("You are already in audio section.");
                       }
                       break;
                       
                       case "photo" :
                       {
                           try         
                           {
                               Parent p2 = FXMLLoader.load(getClass().getResource("ImageCrop.fxml"));
                                    Scene scene = new Scene(p2);
                                    //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                                    stage.setScene(scene);
                                    //stage.initStyle(StageStyle.TRANSPARENT);
                                    //stage.setTitle("VIDEO MENU");
                                    stage.show();
                           } catch(IOException ex) {
                               ex.printStackTrace();
                           }
                       }
                       break;
                       
                       case "search" :
                       {
                                  String cmd = "Start http://google.com";
        
                                    ProcessBuilder builder2 = new ProcessBuilder("cmd.exe","/c",cmd);
                                    builder2.redirectErrorStream(true);

                                    try{
                                        Process p=builder2.start();

                                    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                                    String line="";
                                    while (true) {
                                        try {
                                        line = r.readLine();
                                        }
                                        catch(IOException ex){
                                           ex.printStackTrace(); 
                                        }
                                        if (line == null) { break; }
                                        System.out.println(line);
                                    }
                                    }
                                    catch(IOException ex){
                                        ex.printStackTrace();
                                    } 
                       }
                       break;
                   } 
                });
            }
        }
        
        if(drawer.isClosed())
            drawer.open();
        else
            drawer.close();
        
    }
    
}
