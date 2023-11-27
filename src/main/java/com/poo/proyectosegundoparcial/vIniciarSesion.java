/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.poo.proyectosegundoparcial;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import tienda.*;
/**
 * FXML Controller class
 *
 * @author isaias
 */
public class vIniciarSesion implements Initializable {


    @FXML
    private Button bIniciarSesion;
    @FXML
    private TextField fUsuario;
    @FXML
    private PasswordField fPassword;
    @FXML
    private Text fRestablecerP;
    @FXML
    private Text fCrearCuenta;
    @FXML
    private ImageView miLogo;
    
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private Text titPixelPlay;
    @FXML
    private Text tBienvenido;
    @FXML
    private Text tTexto;
    private Boolean bandera;
    /**
     * Initializes the controller class.
     */
    
    

    public vIniciarSesion() {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String PIXE = "file:PIXELADE.TTF";
        String GILL = "file:Gill Sans.otf";
        Font PIXEtitulo = Font.loadFont(PIXE, 65);
        Font GILL14 = Font.loadFont(GILL, 14);
        Font GILL15 = Font.loadFont(GILL, 15);
        Font GILL34 = Font.loadFont(GILL, 34);
        Font GILL16 = Font.loadFont(GILL, 16);
        titPixelPlay.setFont(PIXEtitulo);
        bIniciarSesion.setFont(GILL14);
        fUsuario.setFont(GILL15);
        fPassword.setFont(GILL15);
        fRestablecerP.setFont(GILL14);
        fCrearCuenta.setFont(GILL14);
        tBienvenido.setFont(GILL34);
        tTexto.setFont(GILL16);
        
    }    
    
    @FXML
    private void iniciarSesion(MouseEvent event) throws IOException {
        String credencial = fUsuario.getText();
        String password = fPassword.getText();
        
        Sistema sist = consultas.dameSistema();
        
        Usuario user = sist.iniciarSesion(credencial, password);
        System.out.println(credencial);
        System.out.println(password);
        if(user == null){
            bandera=sist.existeUser(credencial);
            
            if(credencial.equals("") ){
                Alert alertConfirmacion = new Alert(Alert.AlertType.WARNING);
                alertConfirmacion.setTitle("Campo Vacío");
                alertConfirmacion.setHeaderText("Lo sentimos :(");
                alertConfirmacion.setContentText("El campo de USUARIO se encuentra vacío, por favor, asegurate de llenarlo :D");
                alertConfirmacion.showAndWait();
            }  if(password.equals("") & !(credencial.equals(""))){
                    Alert alertConfirmacion = new Alert(Alert.AlertType.WARNING);
                    alertConfirmacion.setTitle("Campo Vacío");
                    alertConfirmacion.setHeaderText("Lo sentimos :( ");
                    alertConfirmacion.setContentText("El campo de CONTRASEÑA se encuentra vacío, por favor, asegurate de llenarlo :D");
                    alertConfirmacion.showAndWait();
                        }if(bandera == true & !password.equals("") & !(credencial.equals(""))){
                            Alert alertConfirmacion = new Alert(Alert.AlertType.WARNING);
                            alertConfirmacion.setTitle("Credenciales Inválidas");
                            alertConfirmacion.setHeaderText("Lo sentimos :(");
                            alertConfirmacion.setContentText("La CONTRASEÑA ingresada no es la correcta, intenta de nuevo");
                            alertConfirmacion.showAndWait();
                                }if(!bandera  & !password.equals("") & !(credencial.equals(""))){
                                Alert alertConfirmacion = new Alert(Alert.AlertType.WARNING);
                                alertConfirmacion.setTitle("Credenciales Inválidas");
                                alertConfirmacion.setHeaderText("Lo sentimos :(");
                                alertConfirmacion.setContentText("El USUARIO que ingresaste no existe, intenta de nuevo");
                                alertConfirmacion.showAndWait();
                            }

                        }
        
        if(user != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vInterfazUsuario.fxml"));
            root = loader.load();
            
            VInterfazUsuario VInterfazUsuariocontroller = loader.getController();
            VInterfazUsuariocontroller.home(user);
            
            
            
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 1280, 720); 
            stage.setScene(scene);
            
            // Centrar la ventana en la pantalla
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primaryScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primaryScreenBounds.getHeight() - stage.getHeight()) / 2);
            stage.show();
            
        }
    }
    
    @FXML
    private void restablecerP(MouseEvent event) throws IOException{
        App.setRoot("vCambiarPassword");
    }

    @FXML
    private void vCrearCuenta() throws IOException {
        App.setRoot("vCrearCuenta");

    }
    
    // solucionar error: Cuando inicias sesion y luego cierras sesion e intenetas crear cuenta o reestablecer password no funciona
    
    public void home(){
        fRestablecerP.setOnMousePressed(event -> {
            try {
                mostrarCambiarPassword(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        fCrearCuenta.setOnMouseClicked(event -> {
            try {
                mostrarCrearCuenta(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
    }
    
    
    private void mostrarCrearCuenta(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vCrearCuenta.fxml"));
        root = loader.load();
            
        vCrearCuenta vCrearCuentaController = loader.getController();
        vCrearCuentaController.home();
            
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();        
        
    }
    
    
    private void mostrarCambiarPassword(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vCambiarPassword.fxml"));
        root = loader.load();
            
        vCambiarPassword vCambiarPasswordController = loader.getController();
        vCambiarPasswordController.home();
            
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();        
        
    }
}
