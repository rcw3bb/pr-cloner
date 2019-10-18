package xyz.ronella.git.pr.cloner.desktop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.ronella.git.pr.cloner.desktop.common.Images;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SampleController implements Initializable {

    final static Logger LOG = LogManager.getLogger(SampleController.class);

    @FXML
    private TextField field1;

    @FXML
    private TextField field2;

    @FXML
    private Button btnButton;

    @FXML
    private MenuBar mainMenuBar;

    @FXML
    private MenuItem mnuClose;

    @FXML
    private MenuItem mnuOpen;

    @FXML
    private MenuItem mnuAbout;

    @FXML
    private void mnuCloseAction(ActionEvent event) {
        Stage stage = (Stage) mainMenuBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void mnuOpenAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("XML Files", "*.xml")
        );

        File file = fileChooser.showOpenDialog(mainMenuBar.getScene().getWindow());
        Optional.ofNullable(file).ifPresent( ___file -> {
            field1.textProperty().setValue(___file.getAbsolutePath());
        });
    }

    @FXML
    private void mnuAboutAction(ActionEvent event) {
        try {
            Stage parentStage = (Stage) mainMenuBar.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("about.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(Images.ICON);
            stage.initOwner(parentStage);
            stage.setTitle("Hello World");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @FXML
    private void btnButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Field1 and Field2 values");
        alert.setContentText(field1.getText() + " " + field2.getText());
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.debug("Initialize");
    }

}
