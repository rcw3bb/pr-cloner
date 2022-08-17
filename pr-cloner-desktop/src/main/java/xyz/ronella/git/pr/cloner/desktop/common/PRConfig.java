package xyz.ronella.git.pr.cloner.desktop.common;

import org.slf4j.LoggerFactory;
import xyz.ronella.logging.LoggerPlus;

import java.io.IOException;
import java.util.Optional;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

final public class PRConfig {

    public final static PRConfig INSTANCE = new PRConfig();

    private ResourceBundle prop;

    private PRConfig() {
        final LoggerPlus LOGGER_PLUS = new LoggerPlus(LoggerFactory.getLogger(PRConfig.class));
        try(var mLOG = LOGGER_PLUS.groupLog("PRConfig")) {
            try {
                final var propFile = "pr-cloner.properties";
                mLOG.error(()-> "Reading: " + propFile);
                var versionProp = ClassLoader.getSystemResourceAsStream(propFile);
                if (Optional.ofNullable(versionProp).isPresent()) {
                    this.prop = new PropertyResourceBundle(versionProp);
                }
            } catch (IOException exp) {
                mLOG.error(()-> LOGGER_PLUS.getStackTraceAsString(exp));
            }
        }
    }

    public RepoType getRepoType() {
        final var repoType = prop.getString("repoType");
        return RepoType.getEnum(repoType);
    }

}
