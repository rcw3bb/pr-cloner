package xyz.ronella.git.pr.cloner.desktop.util;

import org.slf4j.LoggerFactory;
import xyz.ronella.logging.LoggerPlus;
import xyz.ronella.trivial.handy.PathFinder;

import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * The AppInfo class is the class that provides the application information.
 *
 * @author Ron Webb
 * @since 1.2.1
 */
final public class AppInfo {

    private static final LoggerPlus LOGGER = new LoggerPlus(LoggerFactory.getLogger(AppInfo.class));
    private static final String CONFIG_FILE = "app-info.properties";
    private transient ResourceBundle prop = null;

    /**
     * The INSTANCE is the singleton instance of the AppInfo class.
     */
    public static final AppInfo INSTANCE = new AppInfo();

    private AppInfo() {
        try {
            final var appInfoFile = PathFinder.getBuilder(CONFIG_FILE)
                    .setFallbackToClassloader(true)
                    .build();

            final var appInfoIStream = appInfoFile.getInputStream();

            if (appInfoIStream.isPresent()) {
                this.prop = new PropertyResourceBundle(appInfoIStream.get());
            }

        } catch (IOException exp) {
            LOGGER.error(LOGGER.getStackTraceAsString(exp));
            throw new RuntimeException(exp);
        }
    }

    /**
     * The getAppVersion method returns the application version.
     * @return The application version.
     */
    public String getAppVersion() {
        return prop.getString("app.version");
    }

    /**
     * The getAppName method returns the application name.
     * @return The application name.
     */
    public String getAppName() {
        return prop.getString("app.name");
    }

    /**
     * The getBuildDate method returns the build date.
     * @return The build date.
     */
    public String getBuildDate() {
        return prop.getString("build.date");
    }

}
