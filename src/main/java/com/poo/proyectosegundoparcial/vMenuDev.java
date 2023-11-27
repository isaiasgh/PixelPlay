/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.poo.proyectosegundoparcial;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import tienda.*;

/**
 * FXML Controller class
 *
 * @author isaias
 */
public class vMenuDev implements Initializable {

    @FXML
    private Text tSaludo;
    @FXML
    private Button bMisVideojuegos;
    @FXML
    private Button bAggVideojuego;
    
    private Parent root;
    private Stage stage;
    @FXML
    private ImageView Regresar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void home(Usuario user, VInterfazUsuario controllerIUsuario){
        
        String username = user.getUsername();
        tSaludo.setText("¡Bienvenido al PixelPlay DevLab, " + username + "!");
        bAggVideojuego.setOnAction(event -> {
            try {
                this.mostrarAgregarVideojuego((UsuarioDev)user, event, controllerIUsuario);
            } catch (IOException ex) {
                    ex.printStackTrace();
            }
        
        });
        Regresar.setOnMouseClicked(event -> cerrarVentana(event));
        bMisVideojuegos.setOnAction(event -> {
            try {
                mostrarMisVideojuegos((UsuarioDev)user, event,controllerIUsuario);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        
        });

        
    }
    
    public void mostrarAgregarVideojuego(UsuarioDev user, Event event, VInterfazUsuario controllerIUsuario) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vAgregarVideojuego.fxml"));
        root = loader.load();

        vAgregarVideojuego vAgrearVideojuego = loader.getController();
        vAgrearVideojuego.home(user, controllerIUsuario);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene newScene = new Scene(root, 800, 600);
        stage.setScene(newScene);
        stage.setResizable(false);
        stage.setTitle("PixelPlay - Nuevo Videojuego 👾");
        
        stage.show(); // Muestra la nueva ventana sin cerrar la ventana actual

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

    private void cerrarVentana(Event event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    
}