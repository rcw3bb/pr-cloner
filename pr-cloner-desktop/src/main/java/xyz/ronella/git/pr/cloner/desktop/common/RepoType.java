package xyz.ronella.git.pr.cloner.desktop.common;

import java.util.Locale;

public enum RepoType {

    GITHUB("default-cloner.bat"),
    BITBUCKET("bitbucket-cloner.bat");

    final private String script;

    RepoType(String script) {
        this.script = script;
    }

    public String getScript() {
        return script;
    }

    public static RepoType getEnum(final String repoType) {
        final var ALL_CAPS_REPOTYPE = repoType.toUpperCase(Locale.ROOT);
        return RepoType.valueOf(ALL_CAPS_REPOTYPE);
    }

}
