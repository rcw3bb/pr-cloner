package xyz.ronella.git.pr.cloner.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xyz.ronella.git.pr.cloner.desktop.common.Images;
import xyz.ronella.git.pr.cloner.desktop.function.ApplicationTitle;
import xyz.ronella.git.pr.cloner.desktop.function.Invoker;
import xyz.ronella.trivial.handy.PathFinder;

/**
 * The application entry class.
 *
 * @author Ron Webb
 * @since 2019-10-18
 */
public class Main extends Application {

    static {
        final var userDir = "user.dir";
        final var confDir = System.getProperty(userDir) + "/conf";
        final var logPath = PathFinder.getBuilder("logback.xml")
                .addSysProps(userDir)
                .addPaths(confDir, "..", "../conf")
                .build();
        final var optLogFile = logPath.getFile();

        if (optLogFile.isPresent()) {
            final var logSysProp = "logback.configurationFile";
            final var logFile = optLogFile.get().getAbsolutePath();
            System.out.printf("%s: %s%n", logSysProp, logFile);
            System.setProperty(logSysProp, logFile);
        }
    }

    private static final String MAIN_UI_FILE = "pr-cloner.fxml";

    /**
     * Constructor for the Main class.
     */
    public Main() {}

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Thread.currentThread().getContextClassLoader().getResource(MAIN_UI_FILE));
        Parent root = loader.load();
        primaryStage.getIcons().add(Images.ICON);
        primaryStage.setTitle(Invoker.generate(new ApplicationTitle()));
        primaryStage.setResizable(false);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The application entry point.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}