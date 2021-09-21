/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_project;

import com.jfoenix.controls.JFXDrawer;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Function;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * ImageFilterController controls ImageFilter window
 * @author Mushfiqul Haque 180041140
 */
public class ImageFilterController {
    
    @FXML
    private Label error_msg;
    
    @FXML
    private ImageView view1;
    
    @FXML
    private JFXDrawer drawer;
    
    @FXML
    private BorderPane border;
    
    private int counter=0;
    private File selectedfile;
    
    /**
     * chooseFiles is for choosing files using "open" button
     * @param event ActionEvent
     */
    
    @FXML
    public void chooseFiles(ActionEvent event) {
        
        if(counter > 0) {
            error_msg.setText("Sorry you can't filter anymore.");
            return ;
        }
        
        FileChooser fc = new FileChooser();
        
        selectedfile = fc.showOpenDialog(null);
        
        if(selectedfile == null) {
            error_msg.setText("Please choose file correctly.");
            return ;
        }
        
        view1.setImage(new Image(selectedfile.toURI().toString(),true)); 
    }
    
    /**
     * To provide invert value to "Filter" 
     * @param event ActionEvent
     */
    
    @FXML
    public void invert(ActionEvent event) {
        if(counter > 0) {
            error_msg.setText("Sorry you can't filter anymore.");
            return ;
        }
        
                Filter filter = new Filter("Invert", c-> c.invert());
        view1.setImage(filter.apply(new Image(selectedfile.toURI().toString())));
    }
    
    /**
     * To provide Gray scale value to "Filter"
     * @param event ActionEvent
     */
    
    @FXML
    public void gscale(ActionEvent event) {
        if(counter > 0) {
            error_msg.setText("Sorry you can't filter anymore.");
            return ;
        }
        Filter filter = new Filter("Grayscale",c->c.grayscale());
        view1.setImage(filter.apply(new Image(selectedfile.toURI().toString())));
    }
    
    /**
     * To provide blue value to "Filter" 
     * @param event ActionEvent
     */
    
    @FXML
    public void blue(ActionEvent event) {
        if(counter > 0) {
            error_msg.setText("Sorry you can't filter anymore.");
            return ;
        }
        Filter filter = new Filter("Blue",c->Color.color(c.getRed(),c.getGreen(),1.0));
        view1.setImage(filter.apply(new Image(selectedfile.toURI().toString())));
    }
    
    /**
     * b_w is for black and white filtering
     * @param event ActionEvent
     */
    
    @FXML
    public void b_w(ActionEvent event) {
        if(counter > 0) {
            error_msg.setText("Sorry you can't filter anymore.");
            return ;
        }
        Filter filter = new Filter("Black and White",c-> valueOf(c)<1.5 ? Color.BLACK : Color.WHITE);
        view1.setImage(filter.apply(new Image(selectedfile.toURI().toString())));

    }
    
    /**
     * To convert colors into black and white only
     * @param c Color
     * @return double
     */
    
    private double valueOf(Color c) {
        return c.getRed() + c.getGreen() + c.getBlue();
    }
    
    /**
     * To save the filtered image 
     * @param event ActionEvent
     */
    
    @FXML
    public void save(ActionEvent event) {

        if(counter > 0) {
            error_msg.setText("Sorry you can't save anymore.");
            return ;
        } else counter++;
        
        File f1 = new File("F:\\project_x\\filtered_img.png");
        
        if(f1.length() != 0) {
            error_msg.setText("Your previous file is deleted.");
            f1.delete();
        }
        
        File outputFile = new File("F:/project_x/filtered_img.png");
        BufferedImage bImage = SwingFXUtils.fromFXImage(view1.getImage(), null);
        try {
          ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        
        error_msg.setText("Save Completed.");
        
    }
    /**
     * Filter is a class which actually filter our image with the given value
     * pixel by pixel
     * @author Atiqur Rahman 180041123
     */
        private static class Filter implements Function<Image, Image> {

        private String name;
        private final Function<Color, Color> colorMap;
        
        Filter(String name, Function<Color,Color>colorMap){
            this.name=name;
            this.colorMap = colorMap;
        }
        
        @Override
        public Image apply(Image source) {
            
            int w = (int) source.getWidth();
            int h = (int) source.getHeight();
            
            WritableImage image = new WritableImage(w,h);
            
            for(int y=0;y<h;y++)
                for(int x=0;x<w;x++) {
                    
                    Color c1 = source.getPixelReader().getColor(x,y);
                    Color c2 = colorMap.apply(c1);
                    
                    image.getPixelWriter().setColor(x,y,c2);
                }
            
            return image;
        }
        
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
     * To go from "filter" window to "crop" window
     * @param event ActionEvent
     * @throws IOException when resource is not found
     */
    
    @FXML
    public void crop(ActionEvent event) throws IOException {
        Parent p2 = FXMLLoader.load(getClass().getResource("ImageCrop.fxml"));
        Scene scene = new Scene(p2);
        //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        //stage.initStyle(StageStyle.TRANSPARENT);
        //stage.setTitle("VIDEO MENU");
        stage.show(); 
    }
    
    /**
     * To go from "filter" window to "scale" window
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
