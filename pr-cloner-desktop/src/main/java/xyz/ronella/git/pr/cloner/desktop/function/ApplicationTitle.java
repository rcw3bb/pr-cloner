package xyz.ronella.git.pr.cloner.desktop.function;

import xyz.ronella.git.pr.cloner.desktop.common.Funxion;

import java.util.StringJoiner;
import java.util.function.Supplier;

public class ApplicationTitle implements Supplier<String> {
    @Override
    public String get() {
        return new StringJoiner("")
                .add("PR Cloner v")
                .add(Funxion.buildGenerator(new FullVersion()).generate())
                .toString();
    }
}