package xyz.ronella.git.pr.cloner.desktop.function;

import xyz.ronella.trivial.functional.Sink;

import java.util.function.*;

/**
 * Invokes an implementation of basic functional interfaces.
 *
 * @author Ron Webb
 * @since 2019-11-28
 */
public final class Invoker {

    private Invoker() {}

    /**
     * Executes an implementation BiConsumer.
     *
     * @param logic The BiConsumer implementation.
     * @param arg1 The first parameter for the BiConsumer.
     * @param arg2 The second parameter for the BiConsumer.
     * @param <TYPE_ARG1> The type of the first parameter.
     * @param <TYPE_ARG2> The type of the second parameter.
     */
    public static <TYPE_ARG1, TYPE_ARG2> void execute(
            final BiConsumer<TYPE_ARG1, TYPE_ARG2> logic, final TYPE_ARG1 arg1, final TYPE_ARG2 arg2)
    {
        logic.accept(arg1, arg2);
    }

    /**
     * Executes an implementation BiConsumer but passed in nulls for the arguments.
     *
     * @param logic The BiConsumer implementation.
     * @param <TYPE_ARG1> The type of the first parameter.
     * @param <TYPE_ARG2> The type of the second parameter.
     *
     * @since 2.0.0
     */
    public static <TYPE_ARG1, TYPE_ARG2> void execute(final BiConsumer<TYPE_ARG1, TYPE_ARG2> logic)
    {
        logic.accept(null, null);
    }

    /**
     * Executes an implementation of BiFunction.
     *
     * @param logic The BiFunction implementation.
     * @param arg1 The first parameter for the BiFunction.
     * @param arg2 The second parameter for the BiFunction.
     * @param <TYPE_ARG1> The type of the first parameter.
     * @param <TYPE_ARG2> The type of the second parameter.
     * @param <TYPE_RETURN> The type of the expected return value.
     * @return The output of a BiFunction.
     */
    public static <TYPE_ARG1, TYPE_ARG2, TYPE_RETURN>
    TYPE_RETURN process(final BiFunction<TYPE_ARG1, TYPE_ARG2, TYPE_RETURN> logic, final TYPE_ARG1 arg1,
                        final TYPE_ARG2 arg2) {
        return logic.apply(arg1, arg2);
    }

    /**
     * Executes an implementation Consumer.
     *
     * @param logic The Consumer implementation.
     * @param arg The parameter for the Consumer.
     * @param <TYPE> The type of the parameter.
     */
    public static <TYPE> void execute(final Consumer<TYPE> logic, final TYPE arg) {
        logic.accept(arg);
    }

    /**
     * Executes an implementation Consumer but passed in null to the argument.
     *
     * @param logic The Consumer implementation.
     * @param <TYPE> The type of the parameter.
     *
     * @since 2.0.0
     */
    public static <TYPE> void execute(final Consumer<TYPE> logic) {
        logic.accept(null);
    }

    /**
     * Executes an implementation of Function.
     *
     * @param logic The Function implementation.
     * @param arg The parameter for the Function.
     * @param <TYPE_ARG> The type of the parameter.
     * @param <TYPE_RETURN> The type of the expected return value.
     * @return The output of a Function.
     */
    public static <TYPE_ARG, TYPE_RETURN> TYPE_RETURN process(final Function<TYPE_ARG, TYPE_RETURN> logic,
                                                              final TYPE_ARG arg) {
        return logic.apply(arg);
    }

    /**
     * Executes an implementation of Supplier.
     * @param logic The Supplier implementation.
     * @param <TYPE_RETURN> The type of the expected return value.
     * @return The output of a Supplier.
     */
    public static <TYPE_RETURN> TYPE_RETURN generate(final Supplier<TYPE_RETURN> logic) {
        return logic.get();
    }

    /**
     * Executes an implementation of Sink
     * @param logic The Sink implementation.
     */
    public static void plunge(final Sink logic) {
        logic.plummet();
    }

}
