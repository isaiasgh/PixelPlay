module com.poo.proyectosegundoparcial {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    
    opens com.poo.proyectosegundoparcial to javafx.fxml;
    opens tienda to javafx.base;
    exports com.poo.proyectosegundoparcial;
}
