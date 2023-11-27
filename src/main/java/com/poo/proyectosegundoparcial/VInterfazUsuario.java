/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.poo.proyectosegundoparcial;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import tienda.*;

/**
 * FXML Controller class
 *
 * @author isaias
 */
public class VInterfazUsuario implements Initializable {
    
    @FXML
    private Button bSocial;
    @FXML
    private Button bWallet;
    @FXML
    private Text tWallet;
    @FXML
    private Text tVideogamesTop;
    @FXML
    private Text tDescargasTop;        
    @FXML
    private Button bMiBilbioteca;
    @FXML
    private Button bCerrarSesion;
    @FXML
    private Button bMenuDev;
    @FXML
    private Button bInventario;
    @FXML
    private Text tBienvenida;
    
    private Parent root;
    private Stage stage;
    private Scene scene;

    private VInterfazUsuario thisController = this;
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.actualizarTop();
    }    
    
    public void home(Usuario user){
        tWallet.setText("$ " + user.getWallet().getSaldo());
        
        tBienvenida.setText("Bienvenido " + user.getUsername());
        
        if ((user instanceof UsuarioDev)){
            bMenuDev.setOnAction(event -> {
                try {
                    mostrarMenuDev((UsuarioDev) user);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        } else{
            bMenuDev.setText("");
            bMenuDev.setStyle("-fx-border-width: 0; -fx-background-color: trasnparent; -fx-border-color: transparent;");
            bMenuDev.setCursor(Cursor.DEFAULT);
        }
        
        this.actualizarTop();
        
        bWallet.setOnAction(event -> {
            try {
                mostrarWallet(user);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        bInventario.setOnAction(event -> {
            try {
                mostrarInventario(user, event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        bMiBilbioteca.setOnAction(event -> {
            try {
                mostrarBiblioteca(user, event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        bSocial.setOnAction(event -> {
            try {
                mostrarMenuSocial(user, event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    public void mostrarMenuSocial(Usuario user, Event event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vMenuSocial.fxml"));
        root = loader.load();
            
        vMenuSocialController MenuSocialController = loader.getController();
        MenuSocialController.home(user);
            
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1280, 720);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show(); 
    
    }
    
    public void mostrarBiblioteca(Usuario user, Event event) throws IOException{
        if(user.getBiblioteca().getVideojuegos().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Biblioteca vacía");
            alert.setHeaderText("No tienes ningún videojuego en tu Biblioteca");
            alert.setContentText("Compra tu primer videojuego para que puedas verlo aquí :D");
            alert.showAndWait();
        
        }
        
        if(!user.getBiblioteca().getVideojuegos().isEmpty()){
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vMiBiblioteca.fxml"));
            root = loader.load();

            vMiBibliotecaController bibliotecaControlador = loader.getController();
            bibliotecaControlador.home(user);

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 1280, 720);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();  
            
        }
    
    }
    
    public void mostrarInventario(Usuario user, Event event) throws IOException{
        Sistema sist = consultas.dameSistema();
        
        if(sist.getInventario().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Lo sentimos");
            alert.setHeaderText("Aún no hay vidoejuegos publicados por ningun usuario");
            alert.setContentText("Hasta el momento no contamos con videojuegos, estamos trabajando en ello");
            alert.showAndWait();
        
        }
        
        if(!sist.getInventario().isEmpty()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vInventario.fxml"));
            root = loader.load();

            vInventarioController inventarioControlador = loader.getController();
            inventarioControlador.home(user);

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 1280, 720);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();    
            
        }
    }
   
    public void mostrarWallet(Usuario user) throws IOException{
        // le mandamos la referencia de este controlador
             
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vWallet.fxml"));
        root = loader.load();

        vWallet vWalletController = loader.getController();
        vWalletController.home(user, thisController);

        Stage newStage = new Stage(); // Crea una nueva ventana emergente
        Scene newScene = new Scene(root, 551, 295);
        newStage.setScene(newScene);
        newStage.setResizable(false);
        newStage.setTitle("PixelPlay - Mi Wallet 💸");

        newStage.show(); // Muestra la nueva ventana sin cerrar la ventana actual
    }
 
    public void mostrarMenuDev(UsuarioDev user) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vMenuDev.fxml"));
        root = loader.load();
        vMenuDev vMenuDevController = loader.getController();
        vMenuDevController.home(user, thisController);

        Stage newStage = new Stage(); // Crea una nueva ventana emergente
        Scene newScene = new Scene(root, 800, 600);
        newStage.setScene(newScene);
        newStage.setResizable(false);
        newStage.setTitle("PixelPlay - Menú de Desarrollador 🐍");

        newStage.show(); // Muestra la nueva ventana sin cerrar la ventana actual

    }
    
    public void actualizarSaldo(String nuevoSaldo){
        tWallet.setText("$" + nuevoSaldo);
    }
    
    public void actualizarTop(){
        String top = "";
        String cantDescargas = "";
        
        
        Sistema sist = consultas.dameSistema();
        
        ArrayList<Videojuego> topVideojuegos = new ArrayList<>();
        ArrayList<Videojuego> inventario = sist.getInventario();

        if(inventario != null){
            if (inventario.size() >= 10) {
                // Ordenar el inventario por la cantidad de descargas de manera descendente
                inventario.sort((vg1, vg2) -> Integer.compare(vg2.getCantDescargas(), vg1.getCantDescargas()));

                // Agregar los primeros 10 videojuegos del inventario al topVideojuegos
                for (int i = 0; i < 10; i++) {
                    topVideojuegos.add(inventario.get(i));
                }

                int k = 1;

                for(Videojuego videojuego :topVideojuegos){
                    top = top + k + ". " + videojuego.getNombre() + "\n";
                    cantDescargas = cantDescargas + videojuego.getCantDescargas() + "\n";
                    k++;
                }

            }
        }
        
        tDescargasTop.setText(cantDescargas);
        tVideogamesTop.setText(top);
    }
    
    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
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