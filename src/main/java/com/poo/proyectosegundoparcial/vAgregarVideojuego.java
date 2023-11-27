/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.poo.proyectosegundoparcial;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import tienda.*;

/**
 * FXML Controller class
 *
 * @author isaias
 */
public class vAgregarVideojuego implements Initializable {

    @FXML
    private Button bElegirPortada;
    @FXML
    private TextField fNombre;
    @FXML
    private TextField fPrecio;
    @FXML
    private TextField fCod;
    @FXML
    private TextField fPorcentaje;
    @FXML
    private ComboBox<Categoria> cbCat;
    @FXML
    private Button bAgregar;
    
    private Sistema sist = consultas.dameSistema();
    
    private File portada;
    @FXML
    private ImageView portadaVideojuego;
    @FXML
    private ImageView Regresar;

    private Parent root;
    private Stage stage;
    private Scene scene;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        
        
        // Configurar el TextFormatter para aceptar solo valores numéricos
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
        
        TextFormatter<Double> porcentajeTextFormatter = new TextFormatter<>(
            new DoubleStringConverter(),
            0.0, // Valor inicial
            change -> {
                if (change.isAdded() && !change.getText().matches("\\d*\\.?\\d*")) {
                    return null;
                }
                return change;
            }
        );
        
        fPorcentaje.setOnKeyPressed(event -> obligarText());
        fCod.setOnKeyTyped(event -> obligarText());
        
        bElegirPortada.setOnAction(event -> {
            try {
                seleccionarPortada();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        fPrecio.setTextFormatter(textFormatter);
        fPorcentaje.setTextFormatter(porcentajeTextFormatter);
        fPrecio.setText("");
        fPorcentaje.setText("");
        
        cbCat.getItems().addAll(Categoria.values());

    }
    
    public void seleccionarPortada() throws IOException {
    FileChooser fileChooser = new FileChooser();

    // Crea un filtro para permitir solo archivos de imagen
    ExtensionFilter imageFilter = new ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg", "*.gif");
    fileChooser.getExtensionFilters().add(imageFilter);

    File selectedFile = fileChooser.showOpenDialog(new Stage());

    if (selectedFile != null) {
        // Copiar la imagen seleccionada a una carpeta específica dentro de tu proyecto
        File targetFolder = new File("portadasVideojuegos");
        if (!targetFolder.exists()) {
            targetFolder.mkdirs(); // Crea la carpeta si no existe
        }

        File targetFile = new File(targetFolder, selectedFile.getName());
        Files.copy(selectedFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        portada = targetFile; // Almacena la ruta de la imagen copiada en la variable portada
        Image image = new Image(targetFile.toURI().toString());
        portadaVideojuego.setImage(image);
    }

    if (selectedFile == null) {
        portada = null;
    }
}


    public void obligarText(){
        if(!(fPorcentaje.getText().equals("")) || !(fCod.getText().equals(""))){
            fCod.setPromptText("código de descuento*");
            fPorcentaje.setPromptText("% de descuento*");
        } else{
            fCod.setPromptText("código descuento (opcional)");
            fPorcentaje.setPromptText("% de descuento (opcional)");
        }
    }
    
    public void home(UsuarioDev user, VInterfazUsuario controllerIUsuario){        
        bAgregar.setOnAction(event -> agregarVideojuego(user, controllerIUsuario));
        
        Regresar.setOnMouseClicked(event -> {
                try {
                    cerrarVentana(user, controllerIUsuario, event);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
                
    }
    
    public void agregarVideojuego(UsuarioDev user,  VInterfazUsuario controllerIUsuario){
        Categoria categoria = cbCat.getValue();
        String nombre = fNombre.getText();
        double precio = 0;
        double porcentaje = 0;
        String codigo = fCod.getText();
        
        boolean fVacios = (categoria == null) || nombre.equals("") || fPrecio.getText().equals("") || (portada == null);
        
        Videojuego vg = new Videojuego();        
        
        if(fVacios){
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error");
            alerta.setHeaderText("ATENCION");
            alerta.setContentText("Debes llenar todos los datos antes de continuar");
            alerta.showAndWait();        
        }
        
        if (!fVacios){
            boolean hayPorcentaje = !fPorcentaje.getText().equals("");
            boolean hayCodigo = !(fCod.getText().equals(""));
            
            // si hay porcentaje y hay codigo:
            if (hayPorcentaje & hayCodigo){
                precio = Double.parseDouble(fPrecio.getText());
                porcentaje = Double.parseDouble(fPorcentaje.getText());
                
                // si el precio o el porcentaje es negativo, o el porcentaje es mayor a 100:
                if (precio < 0 || porcentaje < 0 || porcentaje >= 100) {
                    // Alerta
                    Alert alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setTitle("Error");
                    alerta.setHeaderText("ATENCION");
                    alerta.setContentText("El precio no puede ser menor a 0\nEl porcentaje no puede ser ni menor a cero ni mayor o igual 100");
                    alerta.showAndWait();
                
                    // si no es así: 
                } else{
                    user.publicarVj(nombre, precio, categoria, codigo, porcentaje, portada);
                    
                    Alert alertConfirmacion = new Alert(Alert.AlertType.INFORMATION);
                    alertConfirmacion.setTitle("Videojuego publicado");
                    alertConfirmacion.setHeaderText("¡Felicidades!");
                    alertConfirmacion.setContentText("Acabas de publicar un nuevo videojuego en PixelPlay");
                    alertConfirmacion.showAndWait();

                    // seteamos todo en vacio
                    fNombre.setText("");
                    fCod.setText("");
                    fPorcentaje.setText("");
                    fPrecio.setText("");
                    cbCat.getSelectionModel().select(null);
                    
                    // reestablecemos el promptext del codigo/porcentaje
                    fCod.setPromptText("coódigo descuento (opcional)");
                    fPorcentaje.setPromptText("% de descuento (opcional)");
                    
                    portada = null;
                    
                    controllerIUsuario.actualizarTop();
                
                }
                
            }
            
            // si no hay porcentaje y no hay código:
            if (!hayPorcentaje & !hayCodigo) {
                precio = Double.parseDouble(fPrecio.getText());
                user.publicarVj(nombre, precio, categoria, portada);
                
                Alert alertConfirmacion = new Alert(Alert.AlertType.INFORMATION);
                alertConfirmacion.setTitle("Videojuego publicado");
                alertConfirmacion.setHeaderText("¡Felicidades!");
                alertConfirmacion.setContentText("Acabas de publicar un nuevo videojuego en PixelPlay");
                alertConfirmacion.showAndWait();

                fNombre.setText("");
                fCod.setText("");
                fPorcentaje.setText("");
                fPrecio.setText("");
                cbCat.getSelectionModel().select(null);
                
                // reestablecemos el promptext del codigo/porcentaje
                fCod.setPromptText("coódigo descuento (opcional)");
                fPorcentaje.setPromptText("% de descuento (opcional)");
                
                portada = null;
                
                controllerIUsuario.actualizarTop();
            
            }
        }

    }

    private void cerrarVentana(UsuarioDev user,  VInterfazUsuario controllerIUsuario,Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vMenuDev.fxml"));
        root = loader.load();
        vMenuDev vMenuDevController = loader.getController();
        vMenuDevController.home(user, controllerIUsuario);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene newScene = new Scene(root, 800, 600);
        stage.setScene(newScene);
        stage.setResizable(false);
        stage.setTitle("PixelPlay - Menú de Desarrollador 🐍");

        stage.show(); // Muestra la nueva ventana sin cerrar la ventana actual

    }

}
