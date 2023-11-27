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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import tienda.*;

/**
 * FXML Controller class
 *
 * @author isaias
 */
public class vCodigos implements Initializable {
    private Stage stage;
    private Parent root;
    @FXML
    private Text tNombreVg;
    @FXML
    private ImageView portadaVg;
    @FXML
    private TextField fCodigo;
    @FXML
    private TextField fDescuento;
    @FXML
    private TableView<Codigo> tablaCodigos;
    @FXML
    private TableColumn<Codigo,String> colCod;
    @FXML
    private TableColumn<Codigo,Double> colDescuento;
    @FXML
    private Button bAgregarCod;
    @FXML
    private Button bEliminarCod;
    @FXML
    private Text tTitulo;
    @FXML
    private ImageView Regresar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //String GILL = "file:Gill Sans.otf";
        //Font PIXEtitulo = Font.loadFont(GILL, 36);
        //tTitulo.setFont(PIXEtitulo);
        
        TextFormatter<Double> textFormatter = new TextFormatter<>(
            new DoubleStringConverter(),
            0.0, // Valor inicial
            change -> {
                if (change.isAdded() && !change.getText().matches("\\d*\\.?\\d*")) {
                    return null;
                }
                return change;
            }
        );
        
        fDescuento.setTextFormatter(textFormatter);
        fDescuento.setText("");
    }    
    
    public void home(UsuarioDev user, Videojuego vg,VInterfazUsuario controllerIUsuario){
        tNombreVg.setText(vg.getNombre());
        
        File imageFile = vg.getPortada();
        Image image = new Image(imageFile.toURI().toString());
        portadaVg.setImage(image);
        
        actualizarTabla(vg);
        
        bAgregarCod.setOnAction(event -> agregarCodigo(user, vg));
        bEliminarCod.setOnAction(event -> eliminarCodigo(user, vg));
        
        tablaCodigos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {  // Verifica un solo clic
                Codigo codigoSeleccionado = tablaCodigos.getSelectionModel().getSelectedItem();
                if (codigoSeleccionado != null) {
                    fCodigo.setText(codigoSeleccionado.getCodigo());
                }
            }
        });
        Regresar.setOnMouseClicked(event -> {
            try {
                mostrarMisVideojuegos(user,event,controllerIUsuario);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    public void agregarCodigo(UsuarioDev user, Videojuego vg){
        String codigo = fCodigo.getText();
        double porcentaje;
        
        boolean datosVacios = fDescuento.getText().equals("") || codigo.equals("");
        
        if(datosVacios){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Falta llenar datos");
            alert.setContentText("Por favor, llena los datos para continuar");
            alert.showAndWait();
        
        }
        
        if(!datosVacios){
            porcentaje = Double.parseDouble(fDescuento.getText());
            
            if (!UsuarioDev.existeCodigo(vg, codigo)){
                user.crearCodigo(vg, codigo, porcentaje);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("¡Felicidades!");
                alert.setHeaderText("Código agregado exitosamente");
                alert.setContentText("Se ha agregado el código");
                alert.showAndWait();               
                actualizarTabla(vg);
                
                fDescuento.setText("");
                fCodigo.setText("");
            
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Código ya existente");
                alert.setContentText("El código ingresado ya existe, intenta de nuevo");
                alert.showAndWait();
            
            }
        
        }
    
    }
    
    public void eliminarCodigo(UsuarioDev user, Videojuego vg){
        String codigo = fCodigo.getText();
        
        boolean datosVacios = codigo.equals("");
        
        if(datosVacios){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("No ha ingresado el código a eliminar");
            alert.setContentText("Introduzca el código que desea eliminar del sistema");
            alert.showAndWait();
        
        }
        
        if(!datosVacios){
            
            if (UsuarioDev.existeCodigo(vg, codigo)){                
                user.eliminarCodigo(vg, codigo);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Códigos");
                alert.setHeaderText("Código: " + codigo);
                alert.setContentText("El código ha sido eliminado");
                alert.showAndWait();
                
                actualizarTabla(vg);
                
                fDescuento.setText("");
                fCodigo.setText("");
            
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Código no existente");
                alert.setContentText("El código ingresado no existe, intenta de nuevo");
                alert.showAndWait();
            
            }
        
        }
    
    }
    
    public void actualizarTabla(Videojuego vg) {
        ArrayList<Codigo> codigos = vg.getCodigos();
        
        tablaCodigos.getItems().clear();
        
        if(!codigos.isEmpty()){
            colCod.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            colDescuento.setCellValueFactory(new PropertyValueFactory<>("dscPorcentaje"));

            ObservableList<Codigo> observableList = FXCollections.observableArrayList(codigos);
            tablaCodigos.setItems(observableList);
        }
    
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
}
