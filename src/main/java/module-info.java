module SocialNetwork.main {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    //provides java.sql.Driver with org.postgresql.Driver;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens socialnetworkgui to javafx.fxml;
    exports socialnetworkgui.controller to javafx.fxml;
    opens socialnetworkgui.controller to javafx.fxml;
    exports socialnetworkgui;

    opens socialnetwork to javafx.fxml;
    opens socialnetwork.domain to javafx.base;
    exports socialnetwork;
    exports socialnetwork.domain to javafx.base;
}