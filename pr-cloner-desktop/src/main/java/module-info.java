module template.javafx.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;

    opens xyz.ronella.git.pr.cloner.desktop.controller to javafx.fxml;
    exports xyz.ronella.git.pr.cloner.desktop;
}