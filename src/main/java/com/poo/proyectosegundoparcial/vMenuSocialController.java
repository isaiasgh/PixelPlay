/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.poo.proyectosegundoparcial;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import tienda.*;

/**
 * FXML Controller class
 *
 * @author isaias
 */
public class vMenuSocialController implements Initializable {

    @FXML
    private Text tNombreVg1;
    @FXML
    private Text tWallet;
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
    private Button bSeguir;
    @FXML
    private Button bMisSeguidos;
    @FXML
    private Button bSeguidos;
    @FXML
    private Button bRegresar;
    @FXML
    private TextField fUsuarioSeguir;
    
    private Parent root;
    private Stage stage;
    private Scene scene;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String GILL = "file:Gill Sans.otf";
        Font GILL15 = Font.loadFont(GILL, 15);
        colUsername.setStyle("-fx-font-family: '" + GILL15.getName() + "'; -fx-font-size: 16px;");
        colNombre.setStyle("-fx-font-family: '" + GILL15.getName() + "'; -fx-font-size: 16px;");
        colCorreo.setStyle("-fx-font-family: '" + GILL15.getName() + "'; -fx-font-size: 16px;");
        colDesarrollador.setStyle("-fx-font-family: '" + GILL15.getName() + "'; -fx-font-size: 16px;");
        tablaUsuarios.setStyle("-fx-font-family: '" + GILL15.getName() + "'; -fx-font-size: 16px;");
    }    
    
    public void home(Usuario user){
        tWallet.setText("$ " + user.getWallet().getSaldo());
        Sistema sist = consultas.dameSistema();
        
        ArrayList<Usuario> lUsuarios = sist.getlUsuarios();
        
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
        tablaUsuarios.getItems().addAll(lUsuarios);
        
        bSeguir.setOnAction(event -> seguirUser(user));
        
        bSeguidos.setOnAction(event -> {
            try {
                mostrarVSeguidos(user);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        bMisSeguidos.setOnAction(event -> {
            try {
                mostrarVSeguidores(user);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        bRegresar.setOnAction(event -> {
            try {
                regresar(user, event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        tablaUsuarios.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {  // Verifica un solo clic
                Usuario usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
                if (usuarioSeleccionado != null) {
                    fUsuarioSeguir.setText(usuarioSeleccionado.getUsername());
                }
            }
        });
    
    }
   
    public void actualizarSaldo(String nuevoSaldo){
        tWallet.setText("$" + nuevoSaldo);
    }
    
    public void seguirUser(Usuario user){
        String username = fUsuarioSeguir.getText();
        
        if(username.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Selección incorrecta");
            alert.setContentText("No se ha seleccionado ningún usuario a seguir");
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
                boolean yalosigue = false;
                
                for(Usuario u: user.getlSeguidos()){
                    if(u.getUsername().equals(username)){
                        yalosigue = true;
                    
                    }
                }
                
                if(yalosigue){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("Ya sigues a " + username);
                    alert.showAndWait();  

                }
                
                if(!yalosigue){
                    user.seguirUser(username);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("¡Felicidades!");
                    alert.setHeaderText("Acabas de seguir a " + username);
                    alert.setContentText("¡Ahora puedes hacerle regalos desde el Inventario!");
                    alert.showAndWait();  
                }
                
                
                fUsuarioSeguir.setText("");
                
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
    
    public void mostrarVSeguidores(Usuario user) throws IOException{
        
        if(user.getlSeguidores().isEmpty()){       
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Aún no te sigue nadie");
            alert.setContentText("Cuando tengas tu primer seguidor podrás acceder a esta ventana");
            alert.showAndWait();
        }
        
        if(!user.getlSeguidores().isEmpty()){
                
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vSeguidores.fxml"));
            root = loader.load();

            vSeguidoresController seguidoresController = loader.getController();
            seguidoresController.home(user);

            Stage newStage = new Stage(); // Crea una nueva ventana emergente
            Scene newScene = new Scene(root, 800, 600);
            newStage.setScene(newScene);
            newStage.setResizable(false);
            newStage.setTitle("PixelPlay - Seguidores 🤝");

            newStage.show(); // Muestra la nueva ventana sin cerrar la ventana actual
        
        }
    }
    
    public void mostrarVSeguidos(Usuario user) throws IOException{
        
        if(user.getlSeguidos().isEmpty()){
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Aún no sigues a nadie");
            alert.setContentText("Sigue a alguien para poder acceder a esta ventana");
            alert.showAndWait();
            
        }
        
        if(!user.getlSeguidos().isEmpty()){
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vSeguidos.fxml"));
            root = loader.load();

            vSeguidosController seguidosController = loader.getController();
            seguidosController.home(user);

            Stage newStage = new Stage(); // Crea una nueva ventana emergente
            Scene newScene = new Scene(root, 800, 600);
            newStage.setScene(newScene);
            newStage.setResizable(false);
            newStage.setTitle("PixelPlay - Seguidos 🤝");

            newStage.show(); // Muestra la nueva ventana sin cerrar la ventana actual

        }
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

}