/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant_menu_3;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Youssef Al-lehyani
 */
public class menu_Restaurant extends Application {
    
   @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root;
       root = FXMLLoader.load(getClass().getResource("home.fxml"));

       
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
