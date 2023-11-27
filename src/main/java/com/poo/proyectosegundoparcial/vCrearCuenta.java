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
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tienda.*;
/**
 * FXML Controller class
 *
 * @author isaias
 */
public class vCrearCuenta implements Initializable {

    @FXML
    private TextField fcNombreApellido;
    @FXML
    private TextField fcEmail;
    @FXML
    private TextField fcUsername;
    @FXML
    private CheckBox cbDesarrollador;
    @FXML
    private PasswordField fcPassword;
    @FXML
    private CheckBox cbAceptoT;
    @FXML
    private Button bcrearCuenta;
    @FXML
    private Text tIniciarSesion;
    @FXML
    private ImageView imagenCrearC;
    
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
        tIniciarSesion.setOnMouseClicked(event -> {
            try {
                mostrarIniciarSesion(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
    }
    
    
    @FXML
    private void crearCuenta(MouseEvent event) {
        String nombre = fcNombreApellido.getText();
        String email = fcEmail.getText();
        String password = fcPassword.getText();
        String username = fcUsername.getText();
        boolean esDesarrollador = cbDesarrollador.isSelected();
        boolean aceptoTerminos = cbAceptoT.isSelected();
        boolean camposVacios = !email.contains("@") || nombre.equals("") || email.equals("") || password.equals("") || username.equals("");
        
        Sistema sist = consultas.dameSistema();
        
        if(camposVacios){
            // alerta campos vacios
            Alert alertCamposVacios = new Alert(Alert.AlertType.WARNING);
            alertCamposVacios.setTitle("Error");
            alertCamposVacios.setHeaderText("ATENCION");
            alertCamposVacios.setContentText("Por favor, llena todos los campos\nVerifica que el e-mail sea correcto");
            alertCamposVacios.showAndWait();
        
        }
        
        if(!camposVacios & !aceptoTerminos){
            Alert alertTyC = new Alert(Alert.AlertType.WARNING);
            alertTyC.setTitle("Error");
            alertTyC.setHeaderText("ATENCION");
            alertTyC.setContentText("Por favor, acepta los términos y condiciones");
            alertTyC.showAndWait();
       
        }
        
        if(!camposVacios & aceptoTerminos & sist.existeUsuario(username)){
            Alert alertTyC = new Alert(Alert.AlertType.WARNING);
            alertTyC.setTitle("Error");
            alertTyC.setHeaderText("ATENCION");
            alertTyC.setContentText("Usuario en uso, intenta otro");
            alertTyC.showAndWait();
        }
        
                
        if(!camposVacios & aceptoTerminos & sist.existeMail(email)){
            Alert alertTyC = new Alert(Alert.AlertType.WARNING);
            alertTyC.setTitle("Error");
            alertTyC.setHeaderText("ATENCION");
            alertTyC.setContentText("E-mail en uso, intenta otro");
            alertTyC.showAndWait();
        }
                
        if(!camposVacios & aceptoTerminos & !sist.existeUsuario(username) & !sist.existeMail(email)){
            sist.crearCuenta(nombre, username, email, password, esDesarrollador);
            consultas.escribeSistema(sist);
            
            Alert alertConfirmacion = new Alert(Alert.AlertType.INFORMATION);
            alertConfirmacion.setTitle("Cuenta creada");
            alertConfirmacion.setHeaderText("¡Felicidades!");
            alertConfirmacion.setContentText("Acabas de crear tu cuenta de PixelPlay, ¡Inicia sesión para empezar a divertirte!");
            alertConfirmacion.showAndWait();
            
            try{
                App.setRoot("vIniciarSesion");
            } catch(IOException e){
            }
        }
        
    }

    @FXML
    private void ventanaIniciarSesion(MouseEvent event) {
        try{
            App.setRoot("vIniciarSesion");
        } catch(IOException e){
        }
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

}
