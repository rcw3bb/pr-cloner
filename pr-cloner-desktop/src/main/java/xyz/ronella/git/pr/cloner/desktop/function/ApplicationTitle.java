package xyz.ronella.git.pr.cloner.desktop.function;

import xyz.ronella.git.pr.cloner.desktop.common.PRConfig;

import java.util.StringJoiner;
import java.util.function.Supplier;

/**
 * Class responsible for generating the application title.
 *
 * @author Ron Webb
 */
public class ApplicationTitle implements Supplier<String> {

    /**
     * Constructor for the ApplicationTitle class.
     */
    public ApplicationTitle() {}

    /**
     * Generates the application title.
     *
     * @return The application title as a String.
     */
    @Override
    public String get() {
        return new StringJoiner("")
                .add("PR Cloner v")
                .add(Invoker.generate(new FullVersion()))
                .add(" - ").add(PRConfig.INSTANCE.getRepoType().getName())
                .add(" mode")
                .toString();
    }
}