/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_project;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * VideoTrimController controls VideoTrim window
 * @author Atiqur Rahman 180041123
 */
public class VideoTrimController {
    
    @FXML
    private JFXHamburger hamburger;
    
    @FXML
    private JFXDrawer drawer;
    
    //after
    public String vpath="",vpath_1="";
    private MediaPlayer mediaPlayer;
    private String startTime_s="";
    private String endTime_s="";
    private int startTime,endTime,startTime_1,counter=0;
    
    @FXML
    private Label error_msg;
    @FXML
    private MediaView mediaView;
    @FXML
    private Slider progressBar1;
    @FXML
    private BorderPane border;
    
    /**
     * To open files from local drive
     * @param event ActionEvent
     */
    
    @FXML
    public void chooseFiles(ActionEvent event){
        
        if(counter > 0) {
            error_msg.setText("Sorry you can trim only once.");
            return ;
        }
        
        File f3 = new File("F:\\project_x\\trim1.mp4");
        
        if(f3.length() != 0) {
            if(f3.renameTo(new File("G:\\animation.mp4"))==false)f3.delete();
        }
        
        File f4 = new File("F:\\project_x\\trim1_cut1.mp4");
        
        if(f4.length() != 0) {
            System.out.println("atiqonfire");
            if(f4.renameTo(new File("G:\\animation_cut.mp4"))==false)f4.delete();
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
        f1.renameTo(f2 = new File("F:\\project_x\\trim"+Integer.toString(1)+".mp4"));
        vpath = f2.toURI().toString();
        System.out.println(vpath);
        
        
        
        
        if(vpath != null){
            
            Media media = new Media(vpath);
            mediaPlayer = new MediaPlayer(media);
             mediaView.setMediaPlayer(mediaPlayer);
            
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
     * To see our trimmed video
     * @param event ActionEvent
     */
    
    @FXML
    public void preview(ActionEvent event) {
        
        if(counter == 0) {
            error_msg.setText("Please trim first.");
            return ;
        }
        
        File f5 = new File("F:\\project_x\\trim1_cut1.mp4");
        
        if(f5.length() == 0)
        {
            error_msg.setText("Please trim first.");
            return ;
        }
        
        vpath = f5.toURI().toString();
        
        Media media = new Media(vpath);
            mediaPlayer = new MediaPlayer(media);
             mediaView.setMediaPlayer(mediaPlayer);
            
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
     * To save our trimmed video
     * @param event ActionEvent
     */
    
    @FXML
    public void save(ActionEvent event) {
        
        if(counter == 0) {
            error_msg.setText("Please trim first.");
            return ;
        }
        
        File f6 = new File("F:\\project_x\\trim1.mp4");
        
        if(f6.length() == 0) {
            error_msg.setText("Please Choose a file first.");
            return ;
        }
        
        File f7 = new File("F:\\project_x\\trim1_cut1.mp4");
        
        if(f7.length() == 0) {
            error_msg.setText("Please trim first.");
            return ;
        }
        
        System.out.println(f6.getAbsolutePath());
        System.out.println(f7.getAbsolutePath());
        
        f6.renameTo(new File("G:\\animation.mp4"));
        f7.renameTo(new File("G:\\animation_cut.mp4"));
        
        error_msg.setText("Save Completed");
        
    }
    
    /**
     * To play the video
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
     * To pause the video
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
     * To stop the video
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
     * Choosing the point from where our trimming will start
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
     * Choosing the point from where our trimming will end
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
     * Trim our video
     * @param event ActionEvent
     * @throws IOException when resource is not found
     */
    
    @FXML
    public void trim(ActionEvent event) throws IOException {
        
        if("".equals(vpath_1)) {
            error_msg.setText("Please choose file.");
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
        String cmd2=String.format("%s && cd/ && cd %s && ffmpeg -i trim1.mp4 -ss "+startTime_s+" -t "+endTime_s+" -c copy trim1_cut1.mp4",directory.substring(0,2),directory.substring(3,directory.length()));
        
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
     * To go to "merge" window from "trim" window
     * @param event ActionEvent
     * @throws IOException when resource is not found
     */
    
    @FXML
    public void merge(ActionEvent event) throws IOException {
        Parent p2 = FXMLLoader.load(getClass().getResource("VideoMerge.fxml"));
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
        
        //System.out.println("atiq");
        
        VBox box = FXMLLoader.load(getClass().getResource("DrawerFXML.fxml"));
        drawer.setSidePane(box);
        
        for(Node node : box.getChildren()) {
            if(node.getAccessibleText()!=null) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)-> {
                   switch(node.getAccessibleText()){
                       case "video" :
                       {
                           error_msg.setText("You are already in video section.");
                       }
                       break;
                       
                       case "audio" :
                       {
                           try         
                           {
                               Parent p2 = FXMLLoader.load(getClass().getResource("AudioTrim.fxml"));
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
