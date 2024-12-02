package xyz.ronella.git.pr.cloner.desktop.common;

/**
 * Interface representing the version information.
 *
 * @author Ron Webb
 */
public interface IVersion {

    /**
     * Gets the major version number.
     *
     * @return the major version number as an Integer.
     */
    Integer getMajor();

    /**
     * Gets the minor version number.
     *
     * @return the minor version number as an Integer.
     */
    Integer getMinor();

    /**
     * Gets the maintenance version information.
     *
     * @return the maintenance version as a String.
     */
    String getMaintenance();

    /**
     * Gets the build version information.
     *
     * @return the build version as a String.
     */
    String getBuild();
}