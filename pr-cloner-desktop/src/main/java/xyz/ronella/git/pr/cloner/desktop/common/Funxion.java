package xyz.ronella.git.pr.cloner.desktop.common;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Funxion {

    private Funxion() {}

    public static class ExecutorBuilder<TYPE_DATA> {

        protected Consumer<TYPE_DATA> logic;

        public ExecutorBuilder(Consumer<TYPE_DATA> logic) {
            this.logic = logic;
        }

        public void execute(TYPE_DATA data) {
            logic.accept(data);
        }

        public void execute() {
            logic.accept(null);
        }
    }

    public static class ProcessorBuilder<TYPE_INPUT, TYPE_OUTPUT> {

        protected Function<TYPE_INPUT, TYPE_OUTPUT> logic;

        public ProcessorBuilder(Function<TYPE_INPUT, TYPE_OUTPUT> logic) {
            this.logic = logic;
        }

        public TYPE_OUTPUT process(TYPE_INPUT data) {
            return logic.apply(data);
        }
    }

    public static class GeneratorBuilder<TYPE_DATA> {

        protected Supplier<TYPE_DATA> logic;

        public GeneratorBuilder(Supplier<TYPE_DATA> logic) {
            this.logic = logic;
        }

        public TYPE_DATA generate() {
            return logic.get();
        }
    }

    public static <TYPE_DATA> ExecutorBuilder<TYPE_DATA> buildExecutor(Consumer<TYPE_DATA> logic) {
        return new ExecutorBuilder<>(logic);
    }

    public static <TYPE_INPUT, TYPE_OUTPUT> ProcessorBuilder<TYPE_INPUT, TYPE_OUTPUT>
    buildProcessor(Function<TYPE_INPUT, TYPE_OUTPUT> logic) {
        return new ProcessorBuilder<>(logic);
    }

    public static <TYPE_DATA> GeneratorBuilder<TYPE_DATA> buildGenerator(Supplier<TYPE_DATA> logic) {
        return new GeneratorBuilder<>(logic);
    }

}
