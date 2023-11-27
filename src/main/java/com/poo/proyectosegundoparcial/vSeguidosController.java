/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.poo.proyectosegundoparcial;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import tienda.*;

/**
 * FXML Controller class
 *
 * @author isaias
 */
public class vSeguidosController implements Initializable {

    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private TableColumn<Usuario,String> colUsername;
    @FXML
    private TableColumn<Usuario,String> colNombre;
    @FXML
    private TableColumn<Usuario,String> colCorreo;
    @FXML
    private TableColumn<Usuario,String> colDesarrollador;
    @FXML
    private Button bDejarSeguir;
    @FXML
    private TextField fDejarSeguir;
    @FXML
    private Text tSigues;
    @FXML
    private ImageView Regresar;
    private Stage stage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String PIXE = "file:PIXELADE.TTF";
        String GILL = "file:Gill Sans.otf";
        Font GILL30 = Font.loadFont(GILL, 30);
        Font GILL16 = Font.loadFont(GILL, 16);   
        Font GILL15 = Font.loadFont(GILL, 15); 
        tSigues.setFont(GILL30);
        fDejarSeguir.setFont(GILL16);
        fDejarSeguir.setFont(GILL16);
        bDejarSeguir.setFont(GILL16);
        colUsername.setStyle("-fx-font-family: '" + GILL15.getName() + "'; -fx-font-size: 16px;");
        colNombre.setStyle("-fx-font-family: '" + GILL15.getName() + "'; -fx-font-size: 16px;");
        colCorreo.setStyle("-fx-font-family: '" + GILL15.getName() + "'; -fx-font-size: 16px;");
        colDesarrollador.setStyle("-fx-font-family: '" + GILL15.getName() + "'; -fx-font-size: 16px;");
            
    }    
    
    public void home(Usuario user){
        ArrayList<Usuario> uSeguidos = user.getlSeguidos();
        Regresar.setOnMouseClicked(event -> cerrarVentana(event));
        // Configura las columnas
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("mail"));
               
        // Configura la columna colDesarrollador
        colDesarrollador.setCellValueFactory(data -> {
            Usuario usuario = data.getValue();
            String esDesarrollador = (usuario instanceof UsuarioDev) ? "Sí" : "No";
            return new SimpleStringProperty(esDesarrollador);
        });

        // Llena la tabla con la lista de usuarios
        tablaUsuarios.getItems().clear();
        tablaUsuarios.getItems().addAll(uSeguidos);
        bDejarSeguir.setOnAction(event -> dejarSeguir(user));
        
        tablaUsuarios.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {  // Verifica un solo clic
                Usuario usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
                if (usuarioSeleccionado != null) {
                    fDejarSeguir.setText(usuarioSeleccionado.getUsername());
                }
            }
        });
    
    }
    
    public void dejarSeguir (Usuario user){
        String username = fDejarSeguir.getText();
        
        if(username.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Selección incorrecta");
            alert.setContentText("No se ha seleccionado ningún usuario para dejar de seguir");
            alert.showAndWait();
        
        }
        
        if(username.equals(user.getUsername())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Selección incorrecta");
            alert.setContentText("No te puedes seleccionar a ti mismo");
            alert.showAndWait();
        
        }
        
        if(!username.equals("") & !username.equals(user.getUsername())){
            Sistema sist = consultas.dameSistema();
            if(sist.existeUsuario(username)){
                user.dejarSeguir(username);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Listo");
                alert.setHeaderText("Acabas de dejar de seguir " + username);
                alert.setContentText("Ya no podrás enviarle regalos");
                alert.showAndWait();  
                
                fDejarSeguir.setText("");
                home(user);          
            }
            
            if(!sist.existeUsuario(username)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Selección incorrecta");
                alert.setContentText("El usuario ingresado no existe");
                alert.showAndWait();        

            }
        }
    
    }
    private void cerrarVentana(Event event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
