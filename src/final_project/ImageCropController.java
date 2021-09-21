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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * ImageCropController controls ImageCrop window
 * @author Mushfiqul Haque 180041140
 */
public class ImageCropController {
    
    @FXML
    private Label error_msg;
    @FXML
    private ImageView view1;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private BorderPane border;
    
    private File selectedfile;
    private int counter=0,c=0;
    private String str_n="",strext="";
    private double x1,x2,y1,y2;
    
    /**
     * ChooseFiles works for the button "open" in the UI
     * this button helps user to choose file from local drive
     * 
     * @param event ActionEvent
     */
    
    @FXML
    public void chooseFiles(ActionEvent event) {
        
        if(counter > 0) {
            error_msg.setText("Sorry you can crop only once.");
            return ;
        }
        
        
        
        FileChooser fc = new FileChooser();
        
        selectedfile = fc.showOpenDialog(null);
        
        if(selectedfile == null ){
            error_msg.setText("Please choose file correctly.");
            return ;
        }
        
        view1.setImage(new Image(selectedfile.toURI().toString(),true));
        
        str_n = selectedfile.getAbsolutePath();
        
        int j=str_n.length()-1;
        char c1 = '.';
        strext = "";
        while(j>=0) {
            if(str_n.charAt(j) == c1)break;
            strext+=str_n.charAt(j);
            j--;
        }
        strext = new StringBuilder(strext).reverse().toString();
        System.out.println(strext);
        
        File f2 = new File("F:\\project_x\\forcropping"+strext);
        
        if(f2.length() != 0) {
            if(f2.renameTo(new File("G:\\forcropping."+strext))==false)f2.delete();
        }
        
        File f3 = new File("F:\\project_x\\cropped."+strext);
        
        if(f3.length() != 0) {
            
            System.out.println("mh99");
            f3.delete();
        }
        
        File f1 = new File(selectedfile.getAbsolutePath().toString());
        
        f1.renameTo(new File("F:\\project_x\\forcropping."+strext));
        
    }
    
    /**
     * Preview helps user to see his cropped photos
     * 
     * @param event ActionEvent
     */
    
    @FXML
    public void preview(ActionEvent event) {
        
        if(counter != 1) {
            error_msg.setText("Please crop first.");
            return ;
        }
        
        File f3 = new File("F:\\project_x\\cropped."+strext);
        
        view1.setImage(new Image(f3.toURI().toString(),true));
        
    }
    
    /**
     * MouseClicked gets the Cartesian value for cropping depending on the mouse clicks on the screen
     * 
     * @param event MouseEvent
     */
    
    @FXML
    public void mouseClicked(MouseEvent event) {
        c++;
        //f_x.setText(Double.toString(event.getX()));
        //f_y.setText(Double.toString(event.getY()));
        
        if(c%2==0){
            x2=event.getX();
            y2=event.getY();
        } else {
            x1=event.getX();
            y1=event.getY();
        }
        
        System.out.println("x1"+x1+' '+"y1"+y1+' '+"x2"+x2+' '+"y2"+y2);
    }
    /**
     * Crop is for cropping our image
     * 
     * @param event ActionEvent
     */
    @FXML
    public void crop(ActionEvent event) {
        
        if(counter > 0) {
            error_msg.setText("Sorry you can crop only once.");
            return ;
        } else counter++;
        
        error_msg.setText("");
        
       if(c<2 || "".equals(str_n)) {
           
           error_msg.setText("Choose a file and click two points");
           counter=0;
           return ;
       }
       
       //System.out.println(x1+" "+x2);
       double c_w=x2-x1;
       double c_h=y2-y1;
       double c_x=x1;
       double c_y=y1;
       
       if(c_w<0.0 || c_h<0.0 || c_x<0.0 || c_y<0.0){
           
           error_msg.setText("choose 1st on top-left and 2nd bottom-right");
           c=0;
           counter=0;
           return ;
           
       }
       
       String directory = "F:\\project_x";
            //String fn = merge_file_name+"."+strext[0];
            String cmd = String.format("%s && cd/ && cd %s && ffmpeg -i forcropping."+strext+" -vf \"crop=%.2f:%.2f:%.2f:%.2f\" cropped."+strext,directory.substring(0,2),directory.substring(3,directory.length()),c_w,c_h,c_x,c_y);
            
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
            
            error_msg.setText("Successful Cropping");
        
    }
    /**
     * To go from crop to scale we need this "scale" button
     * 
     * @param event ActionEvent
     * @throws IOException when resource is not found
     */
    @FXML
    public void scale(ActionEvent event) throws IOException {
                               Parent p2 = FXMLLoader.load(getClass().getResource("ImageScale.fxml"));
                                    Scene scene = new Scene(p2);
                                    //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                                    stage.setScene(scene);
                                    //stage.initStyle(StageStyle.TRANSPARENT);
                                    //stage.setTitle("VIDEO MENU");
                                    stage.show(); 
    }
    
    /**
     * To go from crop to filter
     * @param event ActionEvent
     * @throws IOException when resource is not found
     */
    
    @FXML
    public void filter(ActionEvent event) throws IOException {
        Parent p2 = FXMLLoader.load(getClass().getResource("ImageFilter.fxml"));
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
    public void close(ActionEvent event) {
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
                           error_msg.setText("You are already in image section.");
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
