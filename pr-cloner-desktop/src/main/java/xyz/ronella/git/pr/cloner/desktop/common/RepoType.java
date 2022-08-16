package xyz.ronella.git.pr.cloner.desktop.common;

import java.util.Locale;

public enum RepoType {

    GITHUB("GitHub", "default-cloner.bat"),
    BITBUCKET("Bitbucket", "bitbucket-cloner.bat");

    final private String script;
    final private String name;

    RepoType(String name, String script) {
        this.name = name;
        this.script = script;
    }

    public String getScript() {
        return script;
    }

    public String getName() {
        return name;
    }

    public static RepoType getEnum(final String repoType) {
        final var ALL_CAPS_REPOTYPE = repoType.toUpperCase(Locale.ROOT);
        return RepoType.valueOf(ALL_CAPS_REPOTYPE);
    }

}
