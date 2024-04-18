module com.example.auction_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.auction_app to javafx.fxml;
    exports com.example.auction_app;
}