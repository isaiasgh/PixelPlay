/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.poo.proyectosegundoparcial;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
public class vMiBibliotecaController implements Initializable {
    
    private Videojuego videojuego;
    @FXML
    private Button bRegresar;
    @FXML
    private Button bResenas;
    @FXML
    private Text tNombreVg;
    @FXML
    private Text tDesarrollador;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox contenedorHbox;
    @FXML
    private Text tWallet;
    
    private int widthSpane = 224;
    private int heightSpane = 330;
    
    private int widthImg = 200;
    private int heightImg = 290;
   
    private int widthBtn = 128;
    private int heightBtn = 34;
   
    private Parent root;
    private Stage stage;
    private Scene scene;
    
    private vMiBibliotecaController thisController = this; 
    /**
     * Initializes the controller class.
     */
    @Override 
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void home(Usuario user){
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        tWallet.setText("$ " + user.getWallet().getSaldo());
        tNombreVg.setText("Ningún videojuego seleccionado");
        tDesarrollador.setText("");
        
        Sistema sist = consultas.dameSistema();
        ArrayList<Videojuego> Biblioteca = user.getBiblioteca().getVideojuegos();
        
        // Crear una lista o mapa para almacenar los botones y sus identificadores
        Font fuente = new Font("Gil Sans", 13);
        String estilo2 = "-fx-background-color: transparent; -fx-text-fill: transparent;";
        
        int cantidadVg = Biblioteca.size();
        int j = 0;

        if(cantidadVg != 0){
            int cantFilas = (int) Math.ceil(cantidadVg/3.0);
            int tope = 3;
            
            for(int i = 0; i < cantFilas; i++){
                HBox hbox = new HBox(10);
                
                while(j < cantidadVg & j < tope){
                    Videojuego vg = Biblioteca.get(j);
                    
                    StackPane spane = new StackPane();
                    spane.setAlignment(Pos.CENTER);
                    spane.setPrefSize(widthSpane, heightSpane);

                    ImageView visualizadorImg = new ImageView();
                    visualizadorImg.setFitHeight(heightImg);
                    visualizadorImg.setFitWidth(widthImg);

                    File imageFile = vg.getPortada();
                    Image image = new Image(imageFile.toURI().toString());
                    visualizadorImg.setImage(image);

                    Button btnSeleccionar = new Button("Seleccionar");
                    btnSeleccionar.setPrefSize(widthBtn, heightBtn);
                    btnSeleccionar.setStyle(estilo2);
                    btnSeleccionar.setFont(fuente);

                    btnSeleccionar.setCursor(Cursor.HAND);
                    
                    spane.getChildren().addAll(visualizadorImg, btnSeleccionar);

                    hbox.getChildren().addAll(spane);
                    
                    // Manejar el evento cuando el cursor entra del botón
                    visualizadorImg.setOnMouseEntered(event -> estiloHover(btnSeleccionar, visualizadorImg));
                    btnSeleccionar.setOnMouseEntered(event -> estiloHover(btnSeleccionar, visualizadorImg));

                    // Manejar el evento cuando el cursor sale del botón
                    visualizadorImg.setOnMouseExited(event -> estiloNotHover(btnSeleccionar, visualizadorImg));
                    btnSeleccionar.setOnMouseExited(event -> estiloNotHover(btnSeleccionar, visualizadorImg));
                    
                    btnSeleccionar.setOnAction(event -> seleccionarVg(vg, user));
                    
                    j++;
                }
                
                tope += 3;
                contenedorHbox.getChildren().addAll(hbox);    
            }
        }      
        
        bRegresar.setOnAction(event -> {
            try {
                regresar(user, event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        bResenas.setOnAction(event -> {
            try {
                mostrarAggResena(user);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
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
     
    public void seleccionarVg(Videojuego vg, Usuario user){
        videojuego = vg; 
        tNombreVg.setText(vg.getNombre() + " - " + vg.getCategoria());
        tDesarrollador.setText("Desarrollado por: " + vg.getDesarrollador().getUsername());
   
    }
    
    public void regresar(Usuario user, Event event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vInterfazUsuario.fxml"));
        root = loader.load();
            
        VInterfazUsuario interfazControlador = loader.getController();
        interfazControlador.actualizarTop();
        interfazControlador.home(user);
            
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();    
    
    }
    
    public void mostrarAggResena(Usuario user) throws IOException{
        if(videojuego == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Ningún videojuego seleccionado");
            alert.setContentText("Selecciona algún videojuego para poder continuar");
            alert.showAndWait();
        
        }
        
        if(videojuego != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vNuevaResena.fxml"));
            root = loader.load();
            vNuevaResenaController vAggResenaController = loader.getController();
            vAggResenaController.home(user, videojuego);

            Stage newStage = new Stage(); // Crea una nueva ventana emergente
            Scene newScene = new Scene(root, 800, 600);
            newStage.setScene(newScene);
            newStage.setResizable(false);
            newStage.setTitle("PixelPlay - Nueva Reseña 🗣️");

            newStage.show(); // Muestra la nueva ventana sin cerrar la ventana actual

        
        
        }
    
    
    }
}
