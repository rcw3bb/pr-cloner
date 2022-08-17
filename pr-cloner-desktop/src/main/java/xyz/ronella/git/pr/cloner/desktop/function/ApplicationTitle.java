package xyz.ronella.git.pr.cloner.desktop.function;

import xyz.ronella.git.pr.cloner.desktop.common.PRConfig;
import xyz.ronella.trivial.command.Invoker;

import java.util.StringJoiner;
import java.util.function.Supplier;

public class ApplicationTitle implements Supplier<String> {
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