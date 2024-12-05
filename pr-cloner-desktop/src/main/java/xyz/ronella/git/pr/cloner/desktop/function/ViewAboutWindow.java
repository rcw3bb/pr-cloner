package xyz.ronella.git.pr.cloner.desktop.function;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import org.slf4j.LoggerFactory;

import xyz.ronella.git.pr.cloner.desktop.common.Images;
import xyz.ronella.logging.LoggerPlus;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Class responsible for displaying the About window.
 */
public class ViewAboutWindow implements Consumer<Window> {

    private final static LoggerPlus LOGGER_PLUS = new LoggerPlus(LoggerFactory.getLogger(ViewAboutWindow.class));

    /**
     * Constructs a ViewAboutWindow.
     */
    public ViewAboutWindow() {
        super();
    }

    /**
     * Displays the About window when the accept method is called.
     *
     * @param window The parent window from which the About window is displayed.
     */
    @Override
    public void accept(final Window window) {
        try(var mLOG = LOGGER_PLUS.groupLog("accept")) {
            mLOG.debug(() -> "Display about window.");
            final Stage parentStage = (Stage) window;
            final FXMLLoader loader = new FXMLLoader(Thread.currentThread().getContextClassLoader().getResource("about.fxml"));
            final Parent root = loader.load();
            final Stage stage = new Stage();
            stage.getIcons().add(Images.ICON);
            stage.initOwner(parentStage);
            stage.setTitle(Invoker.generate(new ApplicationTitle()));
            final Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }
        catch(IOException ioe) {
            LOGGER_PLUS.getLogger().error(LOGGER_PLUS.getStackTraceAsString(ioe));
        }
    }
}