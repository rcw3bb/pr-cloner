package xyz.ronella.git.pr.cloner.desktop.common.impl;

import org.slf4j.LoggerFactory;
import xyz.ronella.git.pr.cloner.desktop.common.IVersion;
import xyz.ronella.logging.LoggerPlus;

import java.io.IOException;
import java.util.Optional;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Implementation of the IVersion interface to retrieve version information from a properties file.
 *
 * @author Ron Webb
 */
public class Version implements IVersion {

    private final static LoggerPlus LOGGER_PLUS = new LoggerPlus(LoggerFactory.getLogger(Version.class));

    private ResourceBundle prop;

    /**
     * Constructs a Version object and loads the version properties from the 'version.properties' file.
     */
    public Version() {
        try(var versionProp = ClassLoader.getSystemResourceAsStream("version.properties")) {
            if (Optional.ofNullable(versionProp).isPresent()) {
                this.prop = new PropertyResourceBundle(versionProp);
            }
        } catch (IOException exp) {
            LOGGER_PLUS.getLogger().error(LOGGER_PLUS.getStackTraceAsString(exp));
        }
    }

    /**
     * Retrieves the major version number.
     *
     * @return the major version number as an Integer.
     */
    @Override
    public Integer getMajor() {
        return Integer.valueOf(prop.getString("MAJOR"));
    }

    /**
     * Retrieves the minor version number.
     *
     * @return the minor version number as an Integer.
     */
    @Override
    public Integer getMinor() {
        return Integer.valueOf(prop.getString("MINOR"));
    }

    /**
     * Retrieves the maintenance version string.
     *
     * @return the maintenance version as a String.
     */
    @Override
    public String getMaintenance() {
        return prop.getString("MAINTENANCE");
    }

    /**
     * Retrieves the build version string.
     *
     * @return the build version as a String.
     */
    @Override
    public String getBuild() {
        return prop.getString("BUILD");
    }

}