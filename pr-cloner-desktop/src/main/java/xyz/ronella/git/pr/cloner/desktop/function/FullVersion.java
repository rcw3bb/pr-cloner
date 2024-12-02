package xyz.ronella.git.pr.cloner.desktop.function;

import xyz.ronella.git.pr.cloner.desktop.common.impl.Version;

import java.util.StringJoiner;
import java.util.function.Supplier;

/**
 * Class responsible for generating the full version string of the application.
 *
 * @author Ron Webb
 */
public class FullVersion implements Supplier<String> {

    /**
     * Constructor for the FullVersion class.
     */
    public FullVersion() {}

    /**
     * Generates the full version string by combining major, minor, maintenance, and build versions.
     *
     * @return The full version string.
     */
    @Override
    public String get() {
        var version = new Version();
        var fullVersion = new StringJoiner(".")
                .add(version.getMajor().toString())
                .add(version.getMinor().toString())
                .add(version.getMaintenance())
                .add(version.getBuild())
                .toString();
        return fullVersion;
    }
}