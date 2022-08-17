module git.pr.cloner.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.slf4j;
    requires org.apache.logging.log4j;

    requires xyz.ronella.logging.logger.plus;
    requires xyz.ronella.casual.trivial;

    opens xyz.ronella.git.pr.cloner.desktop.controller to javafx.fxml;
    exports xyz.ronella.git.pr.cloner.desktop;

}