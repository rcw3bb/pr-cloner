package xyz.ronella.git.pr.cloner.desktop.common;

import org.slf4j.LoggerFactory;
import xyz.ronella.logging.LoggerPlus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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
                final var confName = "pr-cloner.properties";
                final var locations = List.of("../conf", "conf");

                final var confFound = locations.stream()
                        .map(___location -> new File(String.format("%s/%s", ___location, confName)))
                        .filter(File::exists).findFirst();

                final var propFile = confFound.get();

                mLOG.debug(()-> "Reading: " + propFile);

                final InputStream versionProp = new FileInputStream(propFile);
                this.prop = new PropertyResourceBundle(versionProp);
            } catch (IOException exp) {
                mLOG.error(()-> LOGGER_PLUS.getStackTraceAsString(exp));
                throw new RuntimeException(exp);
            }
        }
    }

    public RepoType getRepoType() {
        final var repoType = prop.getString("repoType");
        return RepoType.getEnum(repoType);
    }

}
