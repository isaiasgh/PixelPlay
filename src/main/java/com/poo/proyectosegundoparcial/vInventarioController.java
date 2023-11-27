/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.poo.proyectosegundoparcial;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
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
public class vInventarioController implements Initializable {

    @FXML
    private Text tWallet;
    @FXML
    private Button bComprar;
    @FXML
    private Button bResenas;
    @FXML
    private Button bRegresar;
    @FXML
    private Text tNombreVg;
    @FXML
    private Text tPrecio;
    @FXML
    private Text tCantDescargas;
    @FXML
    private Text tDesarrollador;
    
    private int widthSpane = 224;
    private int heightSpane = 330;
    
    private int widthImg = 200;
    private int heightImg = 290;
   
    private int widthBtn = 128;
    private int heightBtn = 34;
    @FXML
    private VBox contenedorHbox;
    @FXML
    private ScrollPane scrollPane;
    
    private Videojuego videojuego;
    @FXML
    private Text tLoTienes;
    
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private Text tNombreVg1;
    @FXML
    private Button bRegalar;
    
    private vInventarioController thisController = this;


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
        tPrecio.setText("");
        tCantDescargas.setText("");
        tDesarrollador.setText("");
        tLoTienes.setText("");
        
        Sistema sist = consultas.dameSistema();
        ArrayList<Videojuego> Inventario = sist.getInventario();
        
        // Crear una lista o mapa para almacenar los botones y sus identificadores
        Font fuente = new Font("Gill Sans", 13);
        String estilo2 = "-fx-background-color: transparent; -fx-text-fill: transparent;";
        
        int cantidadVg = Inventario.size();
        int j = 0;

        if(cantidadVg != 0){
            int cantFilas = (int) Math.ceil(cantidadVg/3.0);
            int tope = 3;
            
            for(int i = 0; i < cantFilas; i++){
                HBox hbox = new HBox(10);
                
                while(j < cantidadVg & j < tope){
                    Videojuego vg = Inventario.get(j);
                    
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
        
        bComprar.setOnAction(event -> comprarVideojuego(user));
        bRegresar.setOnAction(event -> {
            try {
                regresar(user, event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        bResenas.setOnAction(event -> {
            try {
                mostrarResenas();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        bRegalar.setOnAction(event -> {
            try {
                mostrarRegalar(user);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    public void mostrarRegalar(Usuario user) throws IOException{
        
        if(videojuego == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Selección incorrecta");
            alert.setContentText("No se ha seleccionado ningún videojuego");
            alert.showAndWait();
        
        }
        
        if(user.getlSeguidos().isEmpty() & videojuego != null){
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Atención");
            alerta.setHeaderText("No sigues a nadie");
            alerta.setContentText("Primero debes seguir a alguien para poder regalar un videojuego");
            alerta.showAndWait();
        }
        
        if(!user.getlSeguidos().isEmpty() & videojuego != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vRegalar.fxml"));
            root = loader.load();

            vRegalarController vRegalarController = loader.getController();
            vRegalarController.home(user, videojuego, thisController);

            Stage newStage = new Stage(); // Crea una nueva ventana emergente
            Scene newScene = new Scene(root, 800, 600);
            newStage.setScene(newScene);
            newStage.setResizable(false);
            newStage.setTitle(videojuego.getNombre() + " - Regalar Videojuegos 🎁");

            newStage.show(); // Muestra la nueva ven
      
    }
            
    }
    
    public void mostrarResenas() throws IOException{
        
        if(videojuego == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Selección incorrecta");
            alert.setContentText("No se ha seleccionado ningún videojuego");
            alert.showAndWait();
           
        }
        
        if(videojuego != null & videojuego.getResenas().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("El videojuego seleccionado no tiene reseñas");
            alert.setContentText("Al parecer " + videojuego.getNombre() + " no tiene ninguna reseña\n¡Cómpralo para poder escribir tu propia reseña!");
            alert.showAndWait();        
     
        }
        
        if(videojuego != null & !videojuego.getResenas().isEmpty()){
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vResenasInventario.fxml"));
            root = loader.load();

            vResenasInventarioController vResenasIcontroller = loader.getController();
            vResenasIcontroller.home(videojuego);

            Stage newStage = new Stage(); // Crea una nueva ventana emergente
            Scene newScene = new Scene(root, 800, 600);
            newStage.setScene(newScene);
            newStage.setResizable(false);
            newStage.setTitle(videojuego.getNombre() + " - Reseñas 🗣️");

            newStage.show(); // Muestra la nueva ven
    
        }
    }
    
    public void seleccionarVg(Videojuego vg, Usuario user){
        videojuego = vg; 
        tNombreVg.setText(vg.getNombre() + " - " + vg.getCategoria());
        tDesarrollador.setText("Desarrollado por: " + vg.getDesarrollador().getUsername());
        tPrecio.setText("Precio: $" + vg.getPrecio());
        tCantDescargas.setText("Descargas: " + vg.getCantDescargas());
        
        if (user.tieneVg(vg)){
            tLoTienes.setText("Lo tienes: sí");

        } else {
            tLoTienes.setText("Lo tienes: no");
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
    
    public void comprarVideojuego(Usuario user){
        if(videojuego == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Selección incorrecta");
            alert.setContentText("No se ha seleccionado ningún videojuego");
            alert.showAndWait();
            
        }
        
        // si sí se seleccionó un videojuego
        if(videojuego != null){
                
            // si no lo tiene
            if(!user.tieneVg(videojuego)){
                
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
                        Alert alert = new Alert(AlertType.CONFIRMATION);
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
                                            Alert alertC = new Alert(AlertType.CONFIRMATION);
                                            alertC.setTitle("Confirmación");
                                            alertC.setHeaderText("Código detectado, ¿Desea continuar?");
                                            alertC.setContentText("Nuevo Precio: $" + precioVg + "\nPresione Sí para continuar o Cancelar para cancelar.");

                                            // Personalizar los botones mostrados en el cuadro de diálogo
                                            ButtonType buttonTypeCancel = new ButtonType("Cancelar");
                                            alertC.getButtonTypes().setAll(buttonTypeSi, buttonTypeCancel);

                                            // Mostrar el cuadro de diálogo y esperar a que el usuario responda
                                            alertC.showAndWait().ifPresent(responses -> {
                                                if (responses == buttonTypeSi) {
                                                    user.comprarVg(videojuego, precioVg);
                                                    actualizarSaldo("" + user.getWallet().getSaldo());
                                                    Sistema sist = consultas.dameSistema();
                                                    seleccionarVg(sist.getInventario().get(videojuego.getId()), user);
                                                    
                                                    alert.setTitle("Felicidades");
                                                    alert.setHeaderText("Transacción completada");
                                                    alert.setContentText("Acabas de coprar " + videojuego.getNombre() + " ahora puedes encontrarlo en tu Biblioteca");
                                                    alert.showAndWait(); 

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
                
             // si ya tiene el videojuego   
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Error al comprar");
                alert.setContentText("Ya tienes " + videojuego.getNombre() + " en tu Biblioteca");
                alert.showAndWait();
            
            }
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
        stage.setResizable(false);
        stage.show();    
    
    }
    
    public void actualizarSaldo(String nuevoSaldo){
        tWallet.setText("$" + nuevoSaldo);
    }
    
    public void comprarSinDescuento(Usuario user, boolean b) {        
        
        if (!b) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
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
                    user.comprarVg(videojuego);
                    actualizarSaldo("" + user.getWallet().getSaldo());
                    Sistema sist = consultas.dameSistema();
                    seleccionarVg(sist.getInventario().get(videojuego.getId()), user);
                    
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Felicidades");
                    alert2.setHeaderText("Transacción completada");
                    alert2.setContentText("Acabas de coprar " + videojuego.getNombre() + " ahora puedes encontrarlo en tu Biblioteca");
                    alert2.showAndWait(); 

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
            Alert alertC = new Alert(AlertType.CONFIRMATION);
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
                    user.comprarVg(videojuego);
                    actualizarSaldo("" + user.getWallet().getSaldo());
                    Sistema sist = consultas.dameSistema();
                    seleccionarVg(sist.getInventario().get(videojuego.getId()), user);
                }
            });
        
        }
    }
}