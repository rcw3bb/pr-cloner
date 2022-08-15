package xyz.ronella.git.pr.cloner.desktop.common;

import org.apache.logging.log4j.LogManager;
import xyz.ronella.git.pr.cloner.desktop.common.impl.Version;

import java.io.IOException;
import java.util.Optional;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

final public class PRConfig {

    public final static PRConfig INSTANCE = new PRConfig();

    private ResourceBundle prop;

    private final static LoggerPlus LOGGER_PLUS = new LoggerPlus(LogManager.getLogger(PRConfig.class));

    private PRConfig() {
        try {
            var versionProp = ClassLoader.getSystemResourceAsStream("pr-cloner.properties");
            if (Optional.ofNullable(versionProp).isPresent()) {
                this.prop = new PropertyResourceBundle(versionProp);
            }
        } catch (IOException exp) {
            LOGGER_PLUS.getLogger().error(LOGGER_PLUS.getStackTraceAsString(exp));
        }
    }

    public RepoType getRepoType() {
        final var repoType = prop.getString("repoType");
        return RepoType.getEnum(repoType);
    }

}
