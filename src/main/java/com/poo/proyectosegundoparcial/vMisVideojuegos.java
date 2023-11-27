/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.poo.proyectosegundoparcial;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import tienda.*;

/**
 * FXML Controller class
 *
 * @author isaias
 */
public class vMisVideojuegos implements Initializable {

    private Sistema sist = consultas.dameSistema();
    
    @FXML
    private VBox contImagesView;
    
    private int widthSpane = 224;
    private int heightSpane = 330;
    
    private int widthImg = 200;
    private int heightImg = 290;
   
    private int widthBtn = 128;
    private int heightBtn = 34;
    
    @FXML
    private Button bResena;
    @FXML
    private Button bCodigos;
    
    private Videojuego videojuego;              
    @FXML
    private Text tVideojuegoSeleccionado;
    
    private Parent root;
    private Stage stage;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ImageView Regresar;
    
    private vMisVideojuegos thisController = this;
    @FXML
    private Text tCodigos;
    @FXML
    private Text tResenas;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void home(UsuarioDev user,VInterfazUsuario controllerIUsuario){
        String estilo2 = "-fx-background-color: transparent; -fx-text-fill: transparent;";
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // Crear una lista o mapa para almacenar los botones y sus identificadores
        Font fuente = new Font("Gill Sans", 13);

   
        if(!user.getVideojuegos().isEmpty()){
            int i = 0;
            
            for(Videojuego vg: user.getVideojuegos()){
                i++;
                StackPane spane = new StackPane();
                spane.setAlignment(Pos.CENTER);
                spane.setPrefSize(widthSpane, heightSpane);
                
                ImageView visualizadorImg = new ImageView();
                visualizadorImg.setFitHeight(heightImg);
                visualizadorImg.setFitWidth(widthImg);
                
                File imageFile = vg.getPortada();
                Image image = new Image(imageFile.toURI().toString());
                visualizadorImg.setImage(image);
                
                Button btnAnadirCod = new Button("Seleccionar");
                btnAnadirCod.setPrefSize(widthBtn, heightBtn);
                btnAnadirCod.setStyle(estilo2);
                btnAnadirCod.setFont(fuente);
                                
                spane.getChildren().addAll(visualizadorImg, btnAnadirCod);
                
                contImagesView.getChildren().addAll(spane);
                
                // Manejar el evento cuando el cursor entra del botón
                btnAnadirCod.setOnMouseEntered(event -> estiloHover(btnAnadirCod, visualizadorImg));
                visualizadorImg.setOnMouseEntered(event -> estiloHover(btnAnadirCod, visualizadorImg));
                
                // Manejar el evento cuando el cursor sale del botón
                visualizadorImg.setOnMouseExited(event -> estiloNotHover(btnAnadirCod, visualizadorImg));
                btnAnadirCod.setOnMouseExited(event -> estiloNotHover(btnAnadirCod, visualizadorImg));
                
                btnAnadirCod.setOnAction(event -> seleccionarVg(vg));
                
            }
            
            
            Regresar.setOnMouseClicked(event -> {
                try {
                    cerrarVentana(user, controllerIUsuario, event);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            
            bResena.setOnAction(event -> mostrarAggResena(user, event,controllerIUsuario));
            bCodigos.setOnAction(event -> mostrarAggCodigo(user, event,controllerIUsuario));

        }
  
    }
    
    public void estiloHover(Button btnAnadirCod, ImageView img){
        btnAnadirCod.setStyle("-fx-background-color: #C880B7; -fx-background-radius: 10; -fx-text-fill: #392B58; cursor: hand;");
        img.setOpacity(0.5);
        btnAnadirCod.setFont(new Font("Gil Sans", 13));
    
    }
    
    public void estiloNotHover(Button btnAnadirCod, ImageView img){
        btnAnadirCod.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent;");
        img.setOpacity(1.0);
    }
    
    public void seleccionarVg(Videojuego vg){
        videojuego = vg; 
        
        
        Sistema sist = consultas.dameSistema();
        for(Videojuego v: sist.getInventario()){
            if(v.getId() == videojuego.getId()){
                videojuego = v;
                
            }
        
        }
        
        tVideojuegoSeleccionado.setText("Selección: " + videojuego.getNombre());
        tCodigos.setText("Codigos: "+videojuego.getCodigos().size());
        tResenas.setText("Reseñas: "+videojuego.getResenas().size());            
    }
    
    public void mostrarAggResena(UsuarioDev user, Event event,VInterfazUsuario controllerIUsuario){
        
        Sistema sist = consultas.dameSistema();
        for(Videojuego v: sist.getInventario()){
            if(v.getId() == videojuego.getId()){
                videojuego = v;
                
            }
        
        }
        
        if(videojuego == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Selección incorrecta");
            alert.setContentText("No se ha seleccionado ningún videojuego");
            alert.showAndWait();
            
        } 
        
        if (videojuego != null & videojuego.getResenas().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("El videojuego seleccionado no tiene reseñas");
            alert.setContentText("Al parecer " + videojuego.getNombre() + " no tiene ninguna reseña.");
            alert.showAndWait();        
     
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vAgregarResena.fxml"));
            try {
                root = loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            vAgregarResena vAgregarResena = loader.getController();
            vAgregarResena.home(user, videojuego,controllerIUsuario);
        
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene newScene = new Scene(root, 800, 600);
            stage.setScene(newScene);
            stage.setResizable(false);
            stage.setTitle("PixelPlay - Reseñas");

            stage.show();
            
        }
    
    }
    
    public void mostrarAggCodigo(UsuarioDev user, Event event,VInterfazUsuario controllerIUsuario){
        if(videojuego == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Selección incorrecta");
            alert.setContentText("No se ha seleccionado ningún videojuego");
            alert.showAndWait();
            
        } else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vCodigos.fxml"));
            try {
                root = loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            vCodigos vCodigos = loader.getController();
            vCodigos.home(user, videojuego,controllerIUsuario);
        
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene newScene = new Scene(root, 800, 600);
            stage.setScene(newScene);
            stage.setResizable(false);
            stage.setTitle("PixelPlay - Códigos 🎫");

            stage.show();
            
        }
    
    }
    
    private void cerrarVentana(UsuarioDev user,  VInterfazUsuario controllerIUsuario,Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vMenuDev.fxml"));
        root = loader.load();
        vMenuDev vMenuDevController = loader.getController();
        vMenuDevController.home(user, controllerIUsuario);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene newScene = new Scene(root, 800, 600);
        stage.setScene(newScene);
        stage.setResizable(false);
        stage.setTitle("PixelPlay - Menú de Desarrollador 🐍");

        stage.show(); // Muestra la nueva ventana sin cerrar la ventana actual

    }
    
}