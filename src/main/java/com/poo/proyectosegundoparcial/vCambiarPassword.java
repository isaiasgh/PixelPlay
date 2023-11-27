/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.poo.proyectosegundoparcial;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tienda.*;

/**
 * FXML Controller class
 *
 * @author isaias
 */
public class vCambiarPassword implements Initializable {

    @FXML
    private TextField fEmail;
    @FXML
    private TextField fUsername;
    @FXML
    private PasswordField fPassword;
    @FXML
    private Button bRegresar;
    @FXML
    private Button bContinuar;
    
    private Parent root;
    private Stage stage;
    private Scene scene;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void home(){
        bRegresar.setOnMousePressed(event -> {
            try {
                mostrarIniciarSesion(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    
    
    }
    
    private void mostrarIniciarSesion(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vIniciarSesion.fxml"));
        root = loader.load();
            
        vIniciarSesion vIniciarSesionController = loader.getController();
        vIniciarSesionController.home();
            
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();   
    
    }
    
    @FXML
    private void ventanaIniciarSesion(MouseEvent event) throws IOException{
        App.setRoot("vIniciarSesion");
    }

    @FXML
    private void validarInfo(MouseEvent event) {
        String email = fEmail.getText();
        String username = fUsername.getText();
        String password = fPassword.getText();
        
        Sistema sist = consultas.dameSistema();
        
        boolean seCambio = sist.restablecerPassword(email, username, password);
        
        if(!seCambio){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Información incorrecta");
            alert.setContentText("El usuaio no existe o la información ingresada es incorrecta.\nVerifica e intenta de nuevo.");
            alert.showAndWait();
        }
        
        if(seCambio){
            consultas.escribeSistema(sist);
            
            Alert alertConfirmacion = new Alert(Alert.AlertType.INFORMATION);
            alertConfirmacion.setTitle("Contraseña cambiada");
            alertConfirmacion.setHeaderText("La contraseña ha sido cambiada");
            alertConfirmacion.setContentText("Para iniciar sesión ingresa la nueva contraseña :D");
            alertConfirmacion.showAndWait();
            
            fEmail.setText("");
            fUsername.setText("");
            fPassword.setText("");
            
            try{
                App.setRoot("vIniciarSesion");
            } catch(IOException e){
            }
            
        }
    }
    
}