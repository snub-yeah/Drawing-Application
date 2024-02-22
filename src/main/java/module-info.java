module edu.lukewilson.artapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens edu.lukewilson.artapplication to javafx.fxml;
    exports edu.lukewilson.artapplication;
}