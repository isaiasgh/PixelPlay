/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.poo.proyectosegundoparcial;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tienda.*;

/**
 * FXML Controller class
 *
 * @author isaias
 */
public class vRegalarController implements Initializable {

    @FXML
    private ImageView portadaVideojuego;
    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private TableColumn<Usuario,String> colUsername;
    @FXML
    private TableColumn<Usuario,String> colNombre;
    @FXML
    private TableColumn<Usuario,String> colCorreo;
    @FXML
    private TextField fUsuario;
    @FXML
    private Button bRegalar;
    private Usuario destinatario;
    private Videojuego videojuego;
    
    private vInventarioController controladorInventario;
    @FXML
    private ImageView Regresar;
    private Stage stage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void home(Usuario user, Videojuego videojuego, vInventarioController thisController){
        controladorInventario = thisController;
        this.videojuego = videojuego;
        ArrayList<Usuario> uSeguidos = user.getlSeguidos();
        Regresar.setOnMouseClicked(event -> cerrarVentana(event));
        // Configura las columnas
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("mail"));

        // Llena la tabla con la lista de usuarios
        tablaUsuarios.getItems().clear();
        tablaUsuarios.getItems().addAll(uSeguidos);
        
        File imageFile = videojuego.getPortada();
        Image image = new Image(imageFile.toURI().toString());
        portadaVideojuego.setImage(image);
        
        tablaUsuarios.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {  // Verifica un solo clic
                Usuario usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
                if (usuarioSeleccionado != null) {
                    destinatario = usuarioSeleccionado;
                    fUsuario.setText(usuarioSeleccionado.getUsername());
                }
            }
        });
    
        bRegalar.setOnAction(event -> regalar(videojuego, user));
    }
    
    public void regalar(Videojuego videojuego, Usuario user){
        if(destinatario == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Selección incorrecta");
            alert.setContentText("No se ha seleccionado ningún usuario");
            alert.showAndWait();
        
        }
    
        if(destinatario != null & destinatario.tieneVg(videojuego)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error al seleccionar el usuario");
            alert.setContentText("Al parecer " + destinatario.getUsername() + " ya tiene " + videojuego.getNombre() + " en su Biblioteca");
            alert.showAndWait();
        
        }
        
        if(destinatario != null & !destinatario.tieneVg(videojuego)){
            // si el videojuego no tiene codigos
                if(videojuego.getCodigos().isEmpty()){
                                       
                    // si se tiene el dinero suficiente
                    if(videojuego.getPrecio() <= user.getWallet().getSaldo()){
                        
                        comprarSinDescuento(user, false);
                        
                    // si no tiene el dinero suficiente
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error");
                        alert.setHeaderText("Saldo insuficiente");
                        alert.setContentText("No tienes suficiente dinero en tu Wallet:(\n¡Agrega más dinero e intenta de nuevo!");
                        alert.showAndWait();
                    }
                

                // si el videojuego sí tiene código
                } else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmación");
                        alert.setHeaderText("¿Tiene algún código de " + videojuego.getNombre() + " que desee usar?");
                        alert.setContentText("Presione sí para ingresar el código y no para continuar sin código");

                        // Personalizar los botones mostrados en el cuadro de diálogo
                        ButtonType buttonTypeSi = new ButtonType("Sí");
                        ButtonType buttonTypeNo = new ButtonType("No");
                        alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);

                        // Mostrar el cuadro de diálogo y esperar a que el usuario responda
                        alert.showAndWait().ifPresent(response -> {
                            if (response == buttonTypeSi) {
                                
                                TextInputDialog dialog = new TextInputDialog();
                                dialog.setTitle("Códigos PixelPlay");
                                dialog.setHeaderText("Ingrese el código para obtener su descuento.\nSi no lo tiene presione 'Cancelar'");
                                dialog.setContentText("Código:");

                                // Mostrar el cuadro de diálogo y esperar a que el usuario ingrese texto
                                Optional<String> result = dialog.showAndWait();

                                // Procesar el resultado si el usuario ingresó texto
                                result.ifPresent(codigo -> {
                                    double descuento = user.damePorcentaje(codigo, videojuego) / 100;
                                    double precioVg = videojuego.getPrecio() - (videojuego.getPrecio() * descuento);
                                            
                                    if (descuento != 0) {
                                        if(precioVg <= user.getWallet().getSaldo()){
                                            Alert alertC = new Alert(Alert.AlertType.CONFIRMATION);
                                            alertC.setTitle("Confirmación");
                                            alertC.setHeaderText("Código detectado, ¿Desea continuar?");
                                            alertC.setContentText("Nuevo Precio: $" + precioVg + "\nPresione Sí para continuar o Cancelar para cancelar.");

                                            // Personalizar los botones mostrados en el cuadro de diálogo
                                            ButtonType buttonTypeCancel = new ButtonType("Cancelar");
                                            alertC.getButtonTypes().setAll(buttonTypeSi, buttonTypeCancel);

                                            // Mostrar el cuadro de diálogo y esperar a que el usuario responda
                                            alertC.showAndWait().ifPresent(responses -> {
                                                if (responses == buttonTypeSi) {
                                                    user.regalarVg(videojuego, destinatario, precioVg);
                                                    
                                                    alert.setTitle("Felicidades");
                                                    alert.setHeaderText("Transacción completada");
                                                    alert.setContentText("Acabas de regalar " + videojuego.getNombre() + " a " + destinatario.getUsername());
                                                    alert.showAndWait(); 
                                                    
                                                    controladorInventario.actualizarSaldo("" + user.getWallet().getSaldo());
                                                    controladorInventario.seleccionarVg(videojuego, user);
                                                }
                                            });    
                                        
                                            
                                        } else {
                                            Alert alertW = new Alert(Alert.AlertType.WARNING);
                                            alertW.setTitle("Error");
                                            alertW.setHeaderText("Saldo insuficiente");
                                            alertW.setContentText("No tienes suficiente dinero en tu Wallet:(\n¡Agrega más dinero e intenta de nuevo!");
                                            alertW.showAndWait();
                                        
                                        }
                                        
                                    
                                    } else {
                                        Alert alertW = new Alert(Alert.AlertType.WARNING);
                                        alertW.setTitle("Error");
                                        alertW.setHeaderText("El código ingresado no existe");
                                        alertW.setContentText("Se cancelará la compra. Intentalo más tarde.");
                                        alertW.showAndWait();
                                    }
                                    
                                });
  
                            } else if (response == buttonTypeNo) {
                                
                                // si se tiene el dinero suficiente
                                if(videojuego.getPrecio() <= user.getWallet().getSaldo()){
                                    comprarSinDescuento(user, true);
                                    
                                // si no tiene el dinero suficiente
                                } else {
                                    Alert alertW = new Alert(Alert.AlertType.WARNING);
                                    alertW.setTitle("Error");
                                    alertW.setHeaderText("Saldo insuficiente");
                                    alertW.setContentText("No tienes suficiente dinero en tu Wallet:(\n¡Agrega más dinero e intenta de nuevo!");
                                    alertW.showAndWait();
                                    
                                }     
                            }
                        });                        
                }
            
        }
    
    }
    
    public void comprarSinDescuento(Usuario user, boolean b) {        
        
        if (!b) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Desea continuar?");
            alert.setContentText("Presione Sí para continuar o Cancelar para cancelar.");

            // Personalizar los botones mostrados en el cuadro de diálogo
            ButtonType buttonTypeSi = new ButtonType("Sí");
            ButtonType buttonTypeCancel = new ButtonType("Cancelar");
            alert.getButtonTypes().setAll(buttonTypeSi, buttonTypeCancel);

            // Mostrar el cuadro de diálogo y esperar a que el usuario responda
            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeSi) {
                    user.regalarVg(videojuego, destinatario);
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Felicidades");
                    alert2.setHeaderText("Transacción completada");
                    alert2.setContentText("Acabas de regalar " + videojuego.getNombre() + " a " + destinatario.getUsername());
                    alert2.showAndWait(); 
                    
                    controladorInventario.actualizarSaldo("" + user.getWallet().getSaldo());
                    controladorInventario.seleccionarVg(videojuego, user);


                } else if (response == buttonTypeCancel) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Compra canceladad");
                    alert2.setHeaderText("Transacción cancelada");
                    alert2.setContentText("Acabas de cancelar tu compra");
                    alert2.showAndWait(); 
                }
            });    
        } 
        
        // si el videojuego tenia codigo
        if (b) {
            Alert alertC = new Alert(Alert.AlertType.CONFIRMATION);
            alertC.setTitle("Confirmación");
            alertC.setHeaderText("¿Desea continuar con su compra (sin ningún descuento)?");
            alertC.setContentText("Presione Sí para continuar o Cancelar para cancelar.");

            // Personalizar los botones mostrados en el cuadro de diálogo
            ButtonType buttonTypeCSi = new ButtonType("Sí");
            ButtonType buttonTypeCancel = new ButtonType("Cancelar");
            alertC.getButtonTypes().setAll(buttonTypeCSi, buttonTypeCancel);

            // Mostrar el cuadro de diálogo y esperar a que el usuario responda
            alertC.showAndWait().ifPresent(responses -> {
                if (responses == buttonTypeCSi) {
                    user.regalarVg(videojuego, destinatario);
                    controladorInventario.actualizarSaldo("" + user.getWallet().getSaldo());
                    controladorInventario.seleccionarVg(videojuego, user);
                }
            });
        
        }
    }
    
    private void cerrarVentana(Event event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
