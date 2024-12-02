/**
 * The module-info.java file is used to define the module and its dependencies.
 */
module git.pr.cloner.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.slf4j;
    requires ch.qos.logback.classic;
    requires java.naming;

    requires xyz.ronella.logging.logger.plus;
    requires xyz.ronella.casual.trivial;
    requires java.desktop;

    opens xyz.ronella.git.pr.cloner.desktop.controller to javafx.fxml;
    exports xyz.ronella.git.pr.cloner.desktop;

}