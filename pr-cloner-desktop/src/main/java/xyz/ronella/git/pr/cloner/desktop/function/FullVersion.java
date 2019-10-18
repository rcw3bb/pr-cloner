package xyz.ronella.git.pr.cloner.desktop.function;

import xyz.ronella.git.pr.cloner.desktop.common.impl.Version;

import java.util.StringJoiner;
import java.util.function.Supplier;

public class FullVersion implements Supplier<String> {
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