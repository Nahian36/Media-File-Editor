/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_project;

import com.jfoenix.controls.JFXDrawer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
 * VideoMergeController controls VideoMerge window
 * @author Atiqur Rahman 180041123
 */
public class VideoMergeController {
    
    //copy
    @FXML
    private Label error_msg;
    @FXML
    private MediaView mediaView;
    @FXML
    private Slider progressBar1;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private BorderPane border;
    
    private String merge_file_name = "merge1",vpath="";
    private int counter=0;
    private String[] str , strname , strext , strpath;
    private List<File> selectedfiles;
    private MediaPlayer mediaPlayer;
    
    /**
     * choosing files to open from local drive
     * @param event ActionEvent
     * @throws FileNotFoundException when file is not detected
     * @throws IOException when resource is not found
     */
    
    @FXML
    public void chooseFiles(ActionEvent event) throws FileNotFoundException, IOException{
        
        if(counter > 0) {
            error_msg.setText("Sorry you can merge only once.");
            return ;
        }
        
        File f5 = new File("F:\\project_x\\video0.mp4");
        if(f5.length() != 0) {
            if(f5.renameTo(new File("G:\\animation1.mp4")) == false)f5.delete();
        }
        
        File f6 = new File("F:\\project_x\\video1.mp4");
        if(f6.length() != 0) {
            if(f6.renameTo(new File("G:\\animation2.mp4")) == false)f6.delete();
        }
        
        File f7 = new File("F:\\project_x\\merge1.mp4");
        if(f7.length() != 0){
            f7.delete();
        }
        
        FileChooser fc = new FileChooser();
        
        fc.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("MKV Files","*.mkv","MP4 Files","*.mp4")
        );
        
        //System.out.println("debug");
        
        selectedfiles = fc.showOpenMultipleDialog(null);
        //System.out.println("debug");
        if(selectedfiles != null){
            
            str = new String[selectedfiles.size()];
            strname = new String[selectedfiles.size()];
            strext = new String[selectedfiles.size()];
            strpath = new String[selectedfiles.size()];
            
            for(int i=0;i<selectedfiles.size();i++) {
                //System.out.println("debug");
                str[i]=selectedfiles.get(i).getAbsolutePath();
                
                char c = '\\';
                strname[i]="";
                //System.out.println(c);
                int j=str[i].length()-1;
                //System.out.println(j);
                while(j>=0){
                    if(str[i].charAt(j) == c) {
                        strpath[i] = str[i].substring(0,j);
                        break;
                    }
                    strname[i]+=str[i].charAt(j);
                    j--;
                }
                j=str[i].length()-1;
                c = '.';
                strext[i] = "";
                while(j>=0) {
                    if(str[i].charAt(j) == c)break;
                    strext[i]+=str[i].charAt(j);
                    j--;
                }
                
                strname[i] = new StringBuilder(strname[i]).reverse().toString();
                strext[i] = new StringBuilder(strext[i]).reverse().toString();
                System.out.println(strname[i]);
                System.out.println(strext[i]);
                System.out.println(strpath[i]);
            }
            
            String strcmp = strext[0];
            for(int i=0;i<selectedfiles.size();i++){
                if(!strcmp.equals(strext[i])){
                    //use label here
                    //System.out.println("All the files must have same extension");
                    error_msg.setText("Please select the files which has same extension");
                    return;
                }
            }
            
        } else {
            //System.out.println("file is not choosen properly");
            error_msg.setText("Please choose the files correctly");
        }
        
    }
    
    /**
     * Merging video files
     * @param event ActionEvent
     * @throws FileNotFoundException when file is not detected
     * @throws IOException when resource is not found
     */
    
    @FXML
    public void merge(ActionEvent event) throws FileNotFoundException, IOException {
        
        if(selectedfiles == null) {
            error_msg.setText("Please choose file first.");
            return ;
        }
        
        if(counter > 0) {
            error_msg.setText("Sorry you can merge only once.");
            return ;
        } else counter++;
        
            File f2 = new File("F:\\project_x\\merge.txt");
            if(!f2.exists()){
                    f2.createNewFile();
            }
            PrintWriter pw = new PrintWriter(f2);
            
            for(int i=0;i<selectedfiles.size();i++) {
                
                //changing file's directory
                File f1 = new File(str[i]);

                f1.renameTo(new File("F:\\project_x\\video"+Integer.toString(i)+"."+strext[i]));

                pw.println("file 'video"+Integer.toString(i)+"."+strext[i]+"'");
            
            }
            
            pw.close();
            
            String directory = "F:\\project_x";
            String fn = merge_file_name+"."+strext[0];
            String cmd = String.format("%s && cd/ && cd %s && ffmpeg -f concat -i merge.txt -c copy %s",directory.substring(0,2),directory.substring(3,directory.length()),fn);
            
            ProcessBuilder builder = new ProcessBuilder("cmd.exe","/c",cmd);
            builder.redirectErrorStream(true);

            try{
                Process p=builder.start();

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
            
            error_msg.setText("Merge Completed");
    }
    
    /**
     * To see the merged video
     * @param event ActionEvent
     */
    
    @FXML
    public void preview(ActionEvent event) {
        File f5 = new File("F:\\project_x\\merge1.mp4");
        
        if(f5.length() == 0)
        {
            System.out.println("amibujhina");
            error_msg.setText("Please merge first.");
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
     * To play the merged video
     * @param event ActionEvent
     */
    
    @FXML
    public void play(ActionEvent event) {
        
        if(counter == 0) {
            error_msg.setText("Please merge first.");
            return ;
        }
        mediaPlayer.play();
    }
    
    /**
     * To pause the merged video
     * @param event ActionEvent
     */
    
    @FXML
    public void pause(ActionEvent event) {
        
        if(counter == 0) {
            error_msg.setText("Please merge first.");
            return ;
        }
        mediaPlayer.pause();
    }
    
    /**
     * To stop the merged video
     * @param event ActionEvent
     */
    
    @FXML
    public void stop(ActionEvent event) {
        
        if(counter == 0) {
            error_msg.setText("Please merge first.");
            return ;
        }
        mediaPlayer.stop();
    }
    
    /**
     * To go to "trim" window from "merge" window
     * @param event ActionEvent
     * @throws IOException when resource is not found
     */
    
    @FXML
    public void trim(ActionEvent event) throws IOException {
        Parent p2 = FXMLLoader.load(getClass().getResource("VideoTrim.fxml"));
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
