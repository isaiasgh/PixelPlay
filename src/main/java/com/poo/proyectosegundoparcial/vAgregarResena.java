/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.poo.proyectosegundoparcial;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import tienda.*;

/**
 * FXML Controller class
 *
 * @author isaias
 */
public class vAgregarResena implements Initializable {

    @FXML
    private Text tNombre;
    @FXML
    private ImageView portadaVideojuego;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox contenedorVbox;
    @FXML
    private ImageView Regresar;
    private Parent root;
    private Stage stage;
    private Font fuente = new Font("Gill Sans", 16);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void home(UsuarioDev user, Videojuego vg,VInterfazUsuario controllerIUsuario){
        tNombre.setText(vg.getNombre() + " - " + vg.getCategoria());
        
        String estilo2 = "-fx-background-color: transparent; -fx-text-fill: transparent;";
        Regresar.setOnMouseClicked(event -> {
            try {
                mostrarMisVideojuegos(user,event,controllerIUsuario);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        File imageFile = vg.getPortada();
        Image image = new Image(imageFile.toURI().toString());
        portadaVideojuego.setImage(image);
        
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        ArrayList<Resena> resenas = vg.getResenas();
        actualizarResenas(resenas);
    }
    
    public void actualizarResenas(ArrayList<Resena> resenas){        
        contenedorVbox.getChildren().clear();
        contenedorVbox.setAlignment(Pos.TOP_CENTER);
        
        for(Resena r: resenas){
            TextFlow textFlow = new TextFlow();
            textFlow.setLineSpacing(10);
            textFlow.setMaxWidth(400); 
            
            Usuario user = r.getAutor();
            
            Text comentario = new Text(user.getUsername() + ": " + r.getComentario());
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String formattedDate = dateFormat.format(r.getFecha());

            Text fecha = new Text("\n"+ formattedDate);
            fecha.setFont(fuente);
            fecha.setFill(Color.web("#D6EEFF"));
            
            comentario.setFont(fuente);
            fecha.setFont(fuente);
            comentario.setStyle("-fx-fill: #D6EEFF;");
            fecha.setStyle("-fx-fill: #D6EEFF;");
            
            textFlow.getChildren().addAll(comentario, fecha);
            
            contenedorVbox.getChildren().addAll(textFlow);
        }
    
    }
    
    public void mostrarMisVideojuegos(UsuarioDev user, Event event,VInterfazUsuario controllerIUsuario) throws IOException{
       
        if(user.getVideojuegos().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Aún no has publicado ningún videojuego");
            alert.setContentText("Publica un videojuego para poder acceder a esta ventana");
            alert.showAndWait();
        
        }
        
        if(!user.getVideojuegos().isEmpty()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vMisVideojuegos.fxml"));
            root = loader.load();

            vMisVideojuegos vMisVideojuegos = loader.getController();
            vMisVideojuegos.home(user,controllerIUsuario);

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene newScene = new Scene(root, 800, 600);
            stage.setScene(newScene);
            stage.setResizable(false);
            stage.setTitle("PixelPlay - Mis Videojuegos 🎮");
            stage.show();
        }
    }
    
    
}
