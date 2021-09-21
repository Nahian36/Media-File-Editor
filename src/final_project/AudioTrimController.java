/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_project;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
 *AudioTrimController class controls AudioTrim window
 * @author Nahian Ibn Asad 180041136
 */
public class AudioTrimController {
    @FXML
    private JFXHamburger hamburger;
    
    @FXML
    private JFXDrawer drawer;
    
    @FXML
    private BorderPane border;
    
    //after
    public String vpath="",vpath_1="";
    private MediaPlayer mediaPlayer;
    private String startTime_s="";
    private String endTime_s="";
    private int startTime=0,endTime=0,startTime_1,counter=0;
    
    @FXML
    private Label error_msg;
    @FXML
    private MediaView mediaView;
    @FXML
    private Slider progressBar1;
    
    /**
     * chooseFiles is for choosing files using "open" button
     * @param event ActionEvent
     */
    
    @FXML
    public void chooseFiles(ActionEvent event){
        
        if(counter > 0) {
            error_msg.setText("Sorry you can trim only once.");
            return ;
        }
        
        File f3 = new File("F:\\project_x\\audio_trim1.mp3");
        
        if(f3.length() != 0) {
            if(f3.renameTo(new File("G:\\music.mp3"))==false)f3.delete();
        }
        
        File f4 = new File("F:\\project_x\\audio_trim1_cut1.mp3");
        
        if(f4.length() != 0) {
            System.out.println("atiqonfire");
            if(f4.renameTo(new File("G:\\music_cut.mp3"))==false)f4.delete();
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
        f1.renameTo(f2 = new File("F:\\project_x\\audio_trim"+Integer.toString(1)+".mp3"));
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
     * To hear the trimmed audio
     * @param event ActionEvent
     */
    
    @FXML
    public void preview(ActionEvent event) {
        
        if(counter == 0) {
            error_msg.setText("Please trim first.");
            return ;
        }
        
        File f5 = new File("F:\\project_x\\audio_trim1_cut1.mp3");
        
        if(f5.length() == 0)
        {
            error_msg.setText("Please trim first.");
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
     * To save the trimmed audio
     * @param event ActionEvent
     */
    
    @FXML
    public void save(ActionEvent event) {
        
        if(counter == 0) {
            error_msg.setText("Please trim first.");
            return ;
        }
        
        File f6 = new File("F:\\project_x\\audio_trim1.mp3");
        
        if(f6.length() == 0) {
            error_msg.setText("Please Choose a file first.");
            return ;
        }
        
        File f7 = new File("F:\\project_x\\audio_trim1_cut1.mp3");
        
        if(f7.length() == 0) {
            error_msg.setText("Please trim first.");
            return ;
        }
        
        System.out.println(f6.getAbsolutePath());
        System.out.println(f7.getAbsolutePath());
        
        f6.renameTo(new File("G:\\music.mp3"));
        f7.renameTo(new File("G:\\music_cut.mp3"));
        
        error_msg.setText("Save Completed");
        
    }
    
    /**
     * To play the audio
     * @param event ActionEvent
     */
    
    @FXML
    public void play(ActionEvent event){
        
        if("".equals(vpath_1)) {
            error_msg.setText("Please choose file.");
            return ;
        }
        mediaPlayer.play();
    }
    
    /**
     * To pause the audio
     * @param event ActionEvent
     */
    
    @FXML
    public void pause(ActionEvent event){
        
        if("".equals(vpath_1)) {
            error_msg.setText("Please choose file.");
            return ;
        }
        mediaPlayer.pause();
    }
    
    /**
     * To stop the audio
     * @param event ActionEvent
     */
    
    @FXML
    public void stop(ActionEvent event){
        
        if("".equals(vpath_1)) {
            error_msg.setText("Please choose file.");
            return ;
        }
        mediaPlayer.stop();
    }
    
    /**
     * "start" selects the point from where we will start to trim
     * @param event ActionEvent
     */
    
    @FXML
    public void start(ActionEvent event) {
        
        if("".equals(vpath_1)) {
            error_msg.setText("Please choose file.");
            return ;
        }
        
        if(counter > 0) {
            error_msg.setText("Sorry you can trim only once.");
            return ;
        } 
        
        startTime = (int) (Duration.seconds(progressBar1.getValue())).toSeconds();
        startTime_1 = startTime;
        
        int hours,minutes,seconds;

        hours = startTime/3600;
        startTime -= (hours*3600);

        minutes = startTime/60;
        startTime -= (minutes*60);

        seconds = startTime;
        
        startTime_s = Integer.toString(hours)+":"+Integer.toString(minutes)+":"+Integer.toString(seconds);
        
    }
    
    /**
     * "end" selects the point where we will end our trim
     * @param event ActionEvent
     */
    
    @FXML
    public void end(ActionEvent event) {
        
        if("".equals(vpath_1)) {
            error_msg.setText("Please choose file.");
            return ;
        }
        
        if(counter > 0) {
            error_msg.setText("Sorry you can trim only once.");
        }
        
        endTime = (int) (Duration.seconds(progressBar1.getValue())).toSeconds();
        endTime = endTime-startTime_1;
        System.out.println(endTime);
        
        int hours,minutes,seconds;

        hours = endTime/3600;
        endTime -= (hours*3600);

        minutes = endTime/60;
        endTime -= (minutes*60);

        seconds = endTime;
        
        
        endTime_s = Integer.toString(hours)+":"+Integer.toString(minutes)+":"+Integer.toString(seconds);
    }
    
    /**
     * To trim our audio files
     * @param event ActionEvent
     * @throws IOException when resource is not found
     */
    
    @FXML
    public void trim(ActionEvent event) throws IOException {
        
        if("".equals(vpath_1)) {
            error_msg.setText("Please choose file.");
            return ;
        }
        
        if(startTime>endTime || (startTime==0&&endTime==0)) {
            error_msg.setText("Choose start & end point again.");
            return ;
        }
        
        if(counter > 0) {
            error_msg.setText("Sorry You can trim only once.");
            return ;
        } else counter++;
        
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
        String cmd2=String.format("%s && cd/ && cd %s && ffmpeg -i audio_trim1.mp3 -ss "+startTime_s+" -t "+endTime_s+" -c copy audio_trim1_cut1.mp3",directory.substring(0,2),directory.substring(3,directory.length()));
        
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
        
        mediaPlayer.stop();
        error_msg.setText("Trim Completed");
        
    }    
    
    /**
     * To go from "trim" window to "merge" window
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
     * To go from "trim" window to "enhance" window
     * @param event ActionEvent
     * @throws IOException when resource is not found
     */
    
    @FXML
    public void enhance(ActionEvent event) throws IOException {
        Parent p2 = FXMLLoader.load(getClass().getResource("AudioEnhance.fxml"));
        Scene scene = new Scene(p2);
        //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        //stage.initStyle(StageStyle.TRANSPARENT);
        //stage.setTitle("VIDEO MENU");
        stage.show();  
    }
    
    //before
    
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
