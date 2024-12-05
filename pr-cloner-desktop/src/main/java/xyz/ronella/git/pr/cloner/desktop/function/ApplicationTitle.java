package xyz.ronella.git.pr.cloner.desktop.function;

import xyz.ronella.git.pr.cloner.desktop.common.PRConfig;
import xyz.ronella.git.pr.cloner.desktop.util.AppInfo;

import java.util.function.Supplier;

/**
 * Class responsible for generating the application title.
 *
 * @author Ron Webb
 */
public class ApplicationTitle implements Supplier<String> {

    /**
     * Constructor for ApplicationTitle.
     */
    public ApplicationTitle() {
        super();
    }

    /**
     * Generates the application title.
     *
     * @return The application title as a String.
     */
    @Override
    public String get() {
        final var appInfo = AppInfo.INSTANCE;
        return "PR Cloner v" + appInfo.getAppVersion() + " - " + PRConfig.INSTANCE.getRepoType().getName() + " mode";
    }
}