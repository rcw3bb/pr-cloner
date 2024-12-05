package xyz.ronella.git.pr.cloner.desktop.common;

import org.slf4j.LoggerFactory;
import xyz.ronella.logging.LoggerPlus;
import xyz.ronella.trivial.handy.PathFinder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Singleton class for managing PR configuration.
 *
 * @author Ron Webb
 */
final public class PRConfig {

    /**
     * The single instance of PRConfig.
     */
    public final static PRConfig INSTANCE = new PRConfig();

    private final ResourceBundle prop;

    /**
     * Private constructor to initialize the configuration.
     * Loads properties from the 'pr-cloner.properties' file located in either '../conf' or 'conf' directories.
     */
    @SuppressWarnings({"PMD.AvoidFileStream", "PMD.AvoidThrowingRawExceptionTypes"})
    private PRConfig() {
        final LoggerPlus logger = new LoggerPlus(LoggerFactory.getLogger(PRConfig.class));
        try(var mLOG = logger.groupLog("PRConfig")) {
            try {
                final var confName = "pr-cloner.properties";
                final var locations = List.of("../conf", "conf");
                final var confFound = PathFinder.getBuilder(confName).addPaths(locations).build().getFile();

                final var propFile = confFound.get();

                mLOG.debug(()-> "Reading: " + propFile);

                try (var versionProp = new FileInputStream(propFile)) {
                    this.prop = new PropertyResourceBundle(versionProp);
                }
            } catch (IOException exp) {
                mLOG.error(()-> logger.getStackTraceAsString(exp));
                throw new RuntimeException(exp);
            }
        }
    }

    /**
     * Retrieves the repository type from the configuration.
     *
     * @return The repository type as a RepoType enum.
     */
    public RepoType getRepoType() {
        final var repoType = prop.getString("repoType");
        return RepoType.getEnum(repoType);
    }

}