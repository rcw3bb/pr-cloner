package xyz.ronella.git.pr.cloner.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xyz.ronella.git.pr.cloner.desktop.common.Images;

/**
 * The application entry class.
 *
 * @author Ron Webb
 * @since 2019-10-18
 */
public class Main extends Application {

    private static final String MAIN_UI_FILE = "pr-cloner.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(MAIN_UI_FILE));
        Parent root = loader.load();
        primaryStage.setTitle("Pull Request Cloner");
        primaryStage.getIcons().add(Images.ICON);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}