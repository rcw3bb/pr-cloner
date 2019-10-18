package xyz.ronella.git.pr.cloner.desktop.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import xyz.ronella.git.pr.cloner.desktop.common.Funxion;
import xyz.ronella.git.pr.cloner.desktop.function.ViewAboutWindow;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * The controller attached to pr-cloner.fxml.
 *
 * @author Ron Webb
 * @since 2019-10-18
 */
public class PrClonerController implements Initializable {

    @FXML
    private TextField txtGitProjectDir;

    @FXML
    private TextField txtPullRequest;

    @FXML
    private MenuBar mainMenuBar;

    @FXML
    private ComboBox<String> cboRemotes;

    @FXML
    private void mnuCloseAction(ActionEvent event) {
        closeApp();
    }

    @FXML
    private void mnuOpenAction(ActionEvent event) {
        selectDirectory();
    }

    @FXML
    private void mnuAboutAction(ActionEvent event) {
        Funxion.buildExecutor(new ViewAboutWindow()).execute(mainMenuBar.getScene().getWindow());
    }

    private void selectDirectory() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Select a git project folder");

        File file = dirChooser.showDialog(mainMenuBar.getScene().getWindow());
        Optional.ofNullable(file).ifPresent( ___file -> {
            txtGitProjectDir.textProperty().setValue(___file.getAbsolutePath());
        });
    }

    private void closeApp() {
        Stage stage = (Stage) mainMenuBar.getScene().getWindow();
        stage.close();
    }

    private void showError(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(errorText);
        alert.showAndWait();
    }

    private void showSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Cloning successful");
        alert.showAndWait();
    }

    private void showNoSuccess() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Cloning unsuccessful");
        alert.showAndWait();
    }

    private void showInvalidGitDir() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Not a git project directory");
        alert.showAndWait();
    }

    private void disableComponents() {
        txtGitProjectDir.disableProperty().set(true);
        cboRemotes.disableProperty().set(true);
        txtPullRequest.disableProperty().set(true);
    }

    private void enableComponents() {
        txtGitProjectDir.disableProperty().set(false);
        cboRemotes.disableProperty().set(false);
        txtPullRequest.disableProperty().set(false);
    }

    @FXML
    private void btnCloseAction(ActionEvent event) {
        closeApp();
    }

    @FXML
    private void btnCloneAction(ActionEvent event) {
        String errorText = null;

        if (txtGitProjectDir.getText().isEmpty()) {
            errorText = "No GIT project directory identified";
        }

        if (cboRemotes.getValue()==null) {
            errorText = (errorText!=null ? errorText + " and " : "") + "Remote is not specified";
        }

        if (txtPullRequest.getText().isEmpty()) {
            errorText = (errorText!=null ? errorText + " and " : "") + "Pull request is not specified";
        }

        if (errorText == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Cloning " + txtPullRequest.getText() + " from " + cboRemotes.getValue() + " to " + txtGitProjectDir.getText());
            alert.setContentText("Are you sure?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                doCloning();
            }
        }
        else {
            showError(errorText);
        }
    }

    private List<String> getRemotes() {
        String projectDir = txtGitProjectDir.getText();
        List<String> remotes = Collections.emptyList();

        Path gitDir = Paths.get(projectDir,".git");
        if (gitDir.toFile().exists()) {

            ProcessBuilder pb = new ProcessBuilder("scripts/remotes.bat", projectDir);
            try {
                Process process = pb.start().onExit().get();
                InputStream is = process.getInputStream();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.defaultCharset()))) {
                    return br.lines().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            showInvalidGitDir();
        }
        return remotes;
    }

    private void doCloning() {
        disableComponents();
        File command = new File("scripts/cloner.bat");
        ProcessBuilder pb = new ProcessBuilder(command.getAbsolutePath(), txtGitProjectDir.getText(), cboRemotes.getValue(), txtPullRequest.getText());
        try {
            Process process = pb.start();
            CompletableFuture<Process> future= process.onExit();
            int exitValue = future.get().exitValue();
            if (exitValue==0) {
                showSuccess();
            }
            else {
                showNoSuccess();
            }
            enableComponents();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void txtGitProjectDirMouseClicked(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount()==2) {
            selectDirectory();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtPullRequest.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtPullRequest.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txtGitProjectDir.textProperty().addListener((observable, oldValue, newValue) -> {
            List<String> remotes = getRemotes();
            ObservableList<String> listItems = cboRemotes.getItems();
            listItems.clear();
            if (null != newValue && remotes.size() > 0) {
                listItems.addAll(remotes.stream().collect(Collectors.toList()));
                cboRemotes.disableProperty().set(false);
            }
            else {
                cboRemotes.editableProperty().set(true);
            }
        });
    }

}
