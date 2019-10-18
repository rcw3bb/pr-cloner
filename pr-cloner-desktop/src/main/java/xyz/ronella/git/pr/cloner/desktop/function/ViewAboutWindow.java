package xyz.ronella.git.pr.cloner.desktop.function;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import xyz.ronella.git.pr.cloner.desktop.common.Funxion;
import xyz.ronella.git.pr.cloner.desktop.common.Images;
import xyz.ronella.git.pr.cloner.desktop.common.LoggerPlus;

import java.io.IOException;
import java.util.function.Consumer;

public class ViewAboutWindow implements Consumer<Window> {

    private final static LoggerPlus LOGGER_PLUS = new LoggerPlus(LogManager.getLogger(ViewAboutWindow.class));

    @Override
    public void accept(Window window) {
        try(var mLOG = LOGGER_PLUS.logByMethodCall("accept")) {
            mLOG.debug(()-> "Display about window.");
            Stage parentStage = (Stage) window;
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("about.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(Images.ICON);
            stage.initOwner(parentStage);
            stage.setTitle(Funxion.buildGenerator(new ApplicationTitle()).generate());
            Scene scene = new Scene(root);
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