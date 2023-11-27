/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.poo.proyectosegundoparcial;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import tienda.*;

public class vWallet implements Initializable {

    @FXML
    private Text tDinero;
    @FXML
    private Button bAgregarSaldo;
    @FXML
    private TextField tfSaldo;
    
    private Parent root;
    @FXML
    private Text tWallet;
    @FXML
    private Text tBalance;
    @FXML
    private Text tDolar;
    @FXML
    private ImageView Regresar;
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String PIXE = "file:PIXELADE.TTF";
        String GILL = "file:Gill Sans.otf";
        Font GILL37 = Font.loadFont(GILL, 37);
        Font GILL20 = Font.loadFont(GILL, 20);
        Font GILL36 = Font.loadFont(GILL, 36);
        Font GILL16 = Font.loadFont(GILL, 16);       
        Font GILL15 = Font.loadFont(GILL, 15);
        tWallet.setFont(GILL37);
        tBalance.setFont(GILL20);
        tDolar.setFont(GILL36);
        tDinero.setFont(GILL36);
        bAgregarSaldo.setFont(GILL16);
        tfSaldo.setFont(GILL16);

        
        
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
        
        tfSaldo.setTextFormatter(textFormatter);
        
        tfSaldo.setText("");
    }    
    
    public void home(Usuario user, VInterfazUsuario controllerIUsuario){
        String saldoActual = user.getWallet().toString();
        tDinero.setText(saldoActual);
        Regresar.setOnMouseClicked(event -> cerrarVentana(event));
        bAgregarSaldo.setOnAction(event -> {
            try {
                actualizarSaldo(user, controllerIUsuario);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        
    }
    
    public void actualizarSaldo(Usuario user, VInterfazUsuario controllerIUsuario) throws IOException{
        String fSaldo = tfSaldo.getText();
        
        if(fSaldo.equals("")){
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error");
            alerta.setHeaderText("ATENCION");
            alerta.setContentText("No hay saldo que agregar");
            alerta.showAndWait();        
        }
        
        if(!fSaldo.equals("")){
            Double saldoAgg = Double.parseDouble(fSaldo);
            user.agregarSaldo(saldoAgg);

            String nuevoSaldo = user.getWallet().toString();

            tDinero.setText(nuevoSaldo);

            // actualizamos el saldo en la interfaz principal
            controllerIUsuario.actualizarSaldo(nuevoSaldo);
            tfSaldo.setText("");
        }
    }
    private void cerrarVentana(Event event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
