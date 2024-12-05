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
import javafx.scene.input.KeyEvent;
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
@SuppressWarnings({"PMD.UnusedPrivateMethod","PMD.ExcessiveImports", "PMD.UnusedFormalParameter", "PMD.TooManyMethods"})
public class PRClonerController implements Initializable {

    private final static LoggerPlus LOGGER_PLUS = new LoggerPlus(LoggerFactory.getLogger(PRClonerController.class));

    /**
     * The text field for the GIT project directory.
     */
    @FXML
    /* default */ TextField txtGitProjectDir;

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
     * Constructs a PRClonerController.
     */
    public PRClonerController() {
        super();
    }

    @FXML
    private void mnuCloseAction(final ActionEvent event) {
        closeApp();
    }

    @FXML
    private void mnuOpenAction(final ActionEvent event) {
        selectDirectoryAction();
        event.consume();
    }

    private void selectDirectoryAction() {
        ProjectDirKeyTypeListener.detachListener(this);
        final var txtProperty = txtGitProjectDir.textProperty();
        final var listener = new ProjectDirActionListener(this);
        try {
            txtProperty.addListener(listener);
            selectDirectory();
        }
        finally {
            txtProperty.removeListener(listener);
        }
    }

    @FXML
    private void mnuAboutAction(ActionEvent event) {
        Invoker.execute(new ViewAboutWindow(),mainMenuBar.getScene().getWindow());
    }

    private void selectDirectory() {
        final DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Select a git project folder");

        final File file = dirChooser.showDialog(mainMenuBar.getScene().getWindow());
        Optional.ofNullable(file).ifPresent( ___file -> {
            txtGitProjectDir.textProperty().setValue(___file.getAbsolutePath());
        });
    }

    private void closeApp() {
        final Stage stage = (Stage) mainMenuBar.getScene().getWindow();
        stage.close();
    }

    private void showError(final String errorText) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
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
    @SuppressWarnings("PMD.DoNotUseThreads")
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

    private void saveState() {
        final var state = PRClonerState.getInstance();
        state.setDirectory(txtGitProjectDir.getText());
        state.setRemote(cboRemotes.getValue());
        state.save();
    }

    private String getScriptDir() {
        return PathFinder.getBuilder("scripts").addPaths("../").build().getFile().orElse(new File("."))
                .getAbsolutePath();
    }

    private void doCloning() {
        try(var mLOG = LOGGER_PLUS.groupLog("doCloning")) {
            final var script = String.format("%s/%s", getScriptDir(),
                    PRConfig.INSTANCE.getRepoType().getScript());
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
    private void txtGitProjectDirMouseClicked(final MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount()==2) {
            selectDirectoryAction();
        }
        event.consume();
    }

    private void showInvalidGitDir() {
        cboRemotes.getItems().clear();
        cboRemotes.disableProperty().set(true);
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Not a git project directory");
        alert.showAndWait();
    }

    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    private List<String> getRemotes() {
        final String projectDir = txtGitProjectDir.getText();
        final var remotes = new ArrayList<String>();

        try (var mLOG = LOGGER_PLUS.groupLog("runCommand")) {
            final Path gitDir = Paths.get(projectDir, ".git");
            if (gitDir.toFile().exists()) {
                final var remoteBatch = PathFinder.getBuilder("remotes.bat")
                        .addPaths(getScriptDir()).build().getFile();

                remoteBatch.ifPresentOrElse(___remoteBatch -> {

                    CommandProcessor.process(CommandProcessor.ProcessOutputHandler
                            .captureStreams((output, error) -> {
                                try (BufferedReader reader = new BufferedReader(
                                        new InputStreamReader(output, StandardCharsets.UTF_8))
                                ) {
                                    remotes.addAll(reader.lines().sorted(Comparator.naturalOrder()).toList());
                                } catch (IOException ioe) {
                                    mLOG.error(LOGGER_PLUS.getStackTraceAsString(ioe));
                                    throw new RuntimeException(ioe);
                                }
                            }),

                            CommandArray.getBuilder().addArgs(List.of(___remoteBatch.getAbsolutePath(), projectDir))
                                    .build());

                    }, () -> mLOG.error("Remote batch file not found."));

            } else {
                showInvalidGitDir();
            }
        }

        return remotes;
    }

    /**
     * Updates the ComboBox with the list of Git remotes.
     */
    public void updateCBORemotes() {
        if (!txtGitProjectDir.getText().isEmpty()) {
            final List<String> remotes = getRemotes();
            final ObservableList<String> listItems = cboRemotes.getItems();
            listItems.clear();
            if (remotes.isEmpty()) {
                cboRemotes.editableProperty().set(true);
            } else {
                listItems.addAll(remotes.stream().toList());
                cboRemotes.disableProperty().set(false);
            }
        }
    }

    @FXML
    private void txtGitProjectDirAction(final ActionEvent event) {
        ProjectDirKeyTypeListener.detachListener(this);
        updateCBORemotes();
        event.consume();
    }

    @FXML
    private void txtGitProjectDirKeyTyped(final KeyEvent event) {
        ProjectDirKeyTypeListener.attachListener(this);
        event.consume();
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

        restoreState();
    }

}
