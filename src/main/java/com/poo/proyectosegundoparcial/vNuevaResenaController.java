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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import tienda.*;

/**
 * FXML Controller class
 *
 * @author isaias
 */
public class vNuevaResenaController implements Initializable {

    @FXML
    private ImageView portadaVideojuego;
    @FXML
    private TextArea fResena;
    @FXML
    private Text tNombre;
    @FXML
    private Text tDesarrollador;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button bAggResena;
    
    private Font fuente = new Font("Gil Sans", 16);
    @FXML
    private VBox contenedorVbox;
    @FXML
    private ImageView Regresar;
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fResena.setPrefWidth(347);
        }    
    
    public void home(Usuario user, Videojuego videojuego) {
        bAggResena.setOnAction(event -> crearResena(user, videojuego));
        Regresar.setOnMouseClicked(event -> cerrarVentana(event));
        fResena.setPrefWidth(347);
        tNombre.setText(videojuego.getNombre() + " - " + videojuego.getCategoria());
        tDesarrollador.setText(videojuego.getDesarrollador().getUsername());
        
        String estilo2 = "-fx-background-color: transparent; -fx-text-fill: transparent;";
        
        File imageFile = videojuego.getPortada();
        Image image = new Image(imageFile.toURI().toString());
        portadaVideojuego.setImage(image);
        
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        ArrayList<Resena> resenas = dameResenas(user, videojuego);
        
        if (resenas.size() == 0) {
            Text mensaje = new Text("Por el momento no tienes reseñas,\nagrega una y podrás verla aquí");
            mensaje.setTextAlignment(TextAlignment.CENTER);
            mensaje.setStyle("-fx-fill: #D6EEFF;");
            mensaje.setFont(fuente);
            contenedorVbox.setAlignment(Pos.CENTER);
            contenedorVbox.getChildren().setAll(mensaje);
        }
        
        if(!resenas.isEmpty()){
            // ahora sí mostramos las reseñas
            actualizarResenas(resenas);       
        }
    }
    
    public void crearResena(Usuario user, Videojuego videojuego){
        String comentario = fResena.getText();
        
        if  (comentario.equals("")) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error");
            alerta.setHeaderText("ATENCION");
            alerta.setContentText("No has escrito ninguna reseña, por favor intentalo de nuevo.");
            alerta.showAndWait();        
        
        }
        
        if (!comentario.equals("")) {
            user.crearResenas(videojuego, comentario);
            // funcion para actualizar las resenas
            ArrayList<Resena> resenas = dameResenas(user, videojuego);    
            actualizarResenas(resenas);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reseña creada");
            alert.setHeaderText("Se ha agregado una nueva reseña");
            alert.setContentText("Tu reseña ha sido creada, ahora todos podrán saber lo que opinas de " + videojuego.getNombre());
            alert.showAndWait(); 
            
            fResena.setText("");
        }
    
    }
    
    public ArrayList<Resena> dameResenas(Usuario user, Videojuego videojuego){
        Font fuente = new Font("Gil Sans", 16);
        ArrayList<Resena> resenas = new ArrayList<Resena>();
        if (!user.getResenas().isEmpty()){
            for(Resena r: user.getResenas()){
                
                // anadimos las resenas que son sobre el videojuego
                if(r.getVideojuego().getId() == videojuego.getId()){
                    resenas.add(r);
                
                }
            
            }
            
            // si no ha hecho ninguna resena sobre ese juego: 
            if (resenas.isEmpty()){
                Text mensaje = new Text("Por el momento no tienes reseñas,\nagrega una y podrás verla aquí");
                mensaje.setTextAlignment(TextAlignment.CENTER);
                mensaje.setStyle("-fx-fill: #D6EEFF;");
                mensaje.setFont(fuente);
                contenedorVbox.setAlignment(Pos.CENTER);
                contenedorVbox.getChildren().setAll(mensaje);
            
            }
        
        }
        
        return resenas;   
    }
    
    public void actualizarResenas(ArrayList<Resena> resenas){
        Usuario user = resenas.get(0).getAutor();
        
        contenedorVbox.getChildren().clear();
        
        for(Resena r: resenas){
            TextFlow textFlow = new TextFlow();
            textFlow.setLineSpacing(10);
            textFlow.setMaxWidth(400); 
            
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