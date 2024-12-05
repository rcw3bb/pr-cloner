package xyz.ronella.git.pr.cloner.desktop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the About dialog.
 *
 * @author Ron Webb
 */
@SuppressWarnings("PMD.UnusedPrivateMethod")
public class AboutController implements Initializable {

    @FXML
    private Button btnClose;

    /**
     * Constructs an AboutController.
     */
    public AboutController() {
        super();
    }

    /**
     * Handles the action event for the close button.
     *
     * @param event The action event triggered by the close button.
     */
    @FXML
    @SuppressWarnings("PMD.UnusedFormalParameter")
    private void btnCloseAction(ActionEvent event) {
        final Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller class.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Do nothing
    }
}