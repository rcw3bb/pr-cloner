package xyz.ronella.git.pr.cloner.desktop.common;

import java.util.Locale;

/**
 * Enum representing different types of repositories.
 *
 * @author Ron Webb
 */
public enum RepoType {

    /**
     * GitHub repository type.
     */
    GITHUB("GitHub", "default-cloner.bat"),

    /**
     * Bitbucket repository type.
     */
    BITBUCKET("Bitbucket", "bitbucket-cloner.bat");

    final private String script;
    final private String name;

    /**
     * Constructor for RepoType enum.
     *
     * @param name   The name of the repository type.
     * @param script The script associated with the repository type.
     */
    RepoType(final String name, final String script) {
        this.name = name;
        this.script = script;
    }

    /**
     * Gets the script associated with the repository type.
     *
     * @return The script associated with the repository type.
     */
    public String getScript() {
        return script;
    }

    /**
     * Gets the name of the repository type.
     *
     * @return The name of the repository type.
     */
    public String getName() {
        return name;
    }

    /**
     * Converts a string to the corresponding RepoType enum.
     *
     * @param repoType The string representation of the repository type.
     * @return The corresponding RepoType enum.
     * @throws IllegalArgumentException if the specified repoType does not match any enum value.
     */
    public static RepoType getEnum(final String repoType) {
        final var allCapsRepoType = repoType.toUpperCase(Locale.ROOT);
        return RepoType.valueOf(allCapsRepoType);
    }

}