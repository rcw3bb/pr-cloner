package xyz.ronella.git.pr.cloner.desktop.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Menu;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;
import xyz.ronella.git.pr.cloner.desktop.common.Colors;
import xyz.ronella.git.pr.cloner.desktop.common.PRConfig;
import xyz.ronella.git.pr.cloner.desktop.function.Invoker;
import xyz.ronella.git.pr.cloner.desktop.function.ViewAboutWindow;
import xyz.ronella.logging.LoggerPlus;
import xyz.ronella.trivial.handy.CommandProcessor;
import xyz.ronella.trivial.handy.CommandProcessorException;
import xyz.ronella.trivial.handy.PathFinder;
import xyz.ronella.trivial.handy.impl.CommandArray;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * The controller attached to pr-cloner.fxml.
 *
 * @author Ron Webb
 * @since 2019-10-18
 */
@SuppressWarnings("PMD.UnusedPrivateMethod")
public class PrClonerController implements Initializable {

    private final static LoggerPlus LOGGER_PLUS = new LoggerPlus(LoggerFactory.getLogger(PrClonerController.class));
    @FXML
    private TextField txtGitProjectDir;

    @FXML
    private TextField txtPullRequest;

    @FXML
    private MenuBar mainMenuBar;

    @FXML
    private ComboBox<String> cboRemotes;

    @FXML
    private Button btnClone;

    @FXML
    private Button btnClose;

    @FXML
    private Menu mnuFile;

    /**
     * Constructor for the PrClonerController class.
     */
    public PrClonerController() {}

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
        Invoker.execute(new ViewAboutWindow(),mainMenuBar.getScene().getWindow());
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

    private String calcTextBackground(final String color) {
        return String.format("-fx-control-inner-background: %s", color);
    }
    private void showSuccess() {
        try(var mLOG = LOGGER_PLUS.groupLog("showSuccess")) {
            txtPullRequest.setStyle(calcTextBackground(Colors.LIGHT_GREEN));
            mLOG.debug("Cloning successful.");
        }
    }
    private void showNoSuccess() {
        try(var mLOG = LOGGER_PLUS.groupLog("showNoSuccess")) {
            txtPullRequest.setStyle(calcTextBackground(Colors.LIGHT_RED));
            mLOG.debug("Cloning failed.");
        }
    }
    private void showInvalidGitDir() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Not a git project directory");
        alert.showAndWait();
    }

    private void disableComponents() {
        mnuFile.disableProperty().set(true);
        txtGitProjectDir.disableProperty().set(true);
        cboRemotes.disableProperty().set(true);
        txtPullRequest.disableProperty().set(true);
        btnClone.disableProperty().set(true);
        btnClose.disableProperty().set(true);
    }

    private void enableComponents() {
        mnuFile.disableProperty().set(false);
        txtGitProjectDir.disableProperty().set(false);
        cboRemotes.disableProperty().set(false);
        txtPullRequest.disableProperty().set(false);
        btnClone.disableProperty().set(false);
        btnClose.disableProperty().set(false);
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
            final var thread = new Thread(this::doCloning);
            disableComponents();
            txtPullRequest.setStyle(calcTextBackground(Colors.LIGHT_AMBER));
            thread.start();
        }
        else {
            showError(errorText);
        }
    }

    private List<String> getRemotes() {
        final String projectDir = txtGitProjectDir.getText();
        final var remotes = new ArrayList<String>();
        try(var mLOG = LOGGER_PLUS.groupLog("runCommand")) {
            Path gitDir = Paths.get(projectDir, ".git");
            if (gitDir.toFile().exists()) {
                final var remoteBatch = PathFinder.getBuilder("remotes.bat").addPaths("scripts").build().getFile();

                remoteBatch.ifPresentOrElse(___remoteBatch -> {

                    CommandProcessor.process(CommandProcessor.ProcessOutputHandler.captureStreams((output, error) -> {
                        try (BufferedReader br = new BufferedReader(new InputStreamReader(output, StandardCharsets.UTF_8))) {
                            remotes.addAll(br.lines().sorted(Comparator.naturalOrder()).toList());
                        } catch (IOException ioe) {
                            mLOG.error(LOGGER_PLUS.getStackTraceAsString(ioe));
                            throw new RuntimeException(ioe);
                        }
                    }), CommandArray.getBuilder().addArgs(List.of("./scripts/remotes.bat", projectDir)).build());

                }, () -> mLOG.error("Remote batch file not found."));

            } else {
                showInvalidGitDir();
            }
        }
        return remotes;
    }

    private void saveState() {
        final var state = PRClonerState.getInstance();
        state.setDirectory(txtGitProjectDir.getText());
        state.setRemote(cboRemotes.getValue());
        state.save();
    }

    private void doCloning() {
        try(var mLOG = LOGGER_PLUS.groupLog("doCloning")) {
            final var script = String.format("scripts/%s", PRConfig.INSTANCE.getRepoType().getScript());
            final var command = new File(script);

            final var commandArray = CommandArray.getBuilder()
                    .setCommand(command.getAbsolutePath())
                    .addArgs(List.of(txtGitProjectDir.getText(), cboRemotes.getValue(),txtPullRequest.getText()))
                    .build();

            mLOG.debug(()-> String.join(" ", commandArray.getCommand()));

            try {
                CommandProcessor.process((___process) -> {
                    ___process.onExit().thenAccept(____process -> {
                        if (____process.exitValue() == 0) {
                            Platform.runLater(()-> {
                                showSuccess();
                                saveState();
                                enableComponents();
                            });
                        } else {
                            Platform.runLater(()-> {
                                showNoSuccess();
                                enableComponents();
                            });
                        }
                    });
                    }, commandArray);

            } catch (CommandProcessorException e) {
                mLOG.error(LOGGER_PLUS.getStackTraceAsString(e));
            }
        }
    }
    @FXML
    private void txtGitProjectDirMouseClicked(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount()==2) {
            selectDirectory();
        }
    }

    private void restoreState() {
        final var state = PRClonerState.getInstance();
        state.restore();
        final var dir = Optional.ofNullable(state.getDirectory());
        dir.ifPresent(___dir -> {
            txtGitProjectDir.setText(state.getDirectory());
            cboRemotes.setValue(state.getRemote());
            Platform.runLater(() -> txtPullRequest.requestFocus());
        });
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
                listItems.addAll(remotes.stream().toList());
                cboRemotes.disableProperty().set(false);
            }
            else {
                cboRemotes.editableProperty().set(true);
            }
        });

        restoreState();
    }

}
