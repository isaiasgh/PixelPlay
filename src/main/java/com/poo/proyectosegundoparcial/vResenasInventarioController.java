/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.poo.proyectosegundoparcial;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
public class vResenasInventarioController implements Initializable {

    @FXML
    private Text tNombre;
    @FXML
    private ImageView portadaVideojuego;
    @FXML
    private Text tDesarrollador;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox contenedorVbox;
    
    private Font fuente = new Font("Gill Sans", 16);
    @FXML
    private ImageView Regresar;

    private Stage stage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void home (Videojuego videojuego) {
        tNombre.setText(videojuego.getNombre() + " - " + videojuego.getCategoria());
        tDesarrollador.setText(videojuego.getDesarrollador().getUsername());
        
        String estilo2 = "-fx-background-color: transparent; -fx-text-fill: transparent;";
        Regresar.setOnMouseClicked(event -> cerrarVentana(event));
        File imageFile = videojuego.getPortada();
        Image image = new Image(imageFile.toURI().toString());
        portadaVideojuego.setImage(image);
        
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        ArrayList<Resena> resenas = videojuego.getResenas();
        actualizarResenas(resenas);
        
    }
 
    public void actualizarResenas(ArrayList<Resena> resenas){        
        contenedorVbox.getChildren().clear();
        
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
    private void cerrarVentana(Event event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}