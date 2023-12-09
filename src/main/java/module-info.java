module com.example.ap_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.testng;
    requires junit;

    opens com.example.ap_project to javafx.fxml;
    exports com.example.ap_project;
}