package xyz.ronella.git.pr.cloner.desktop.common;

import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class LoggerPlus {
    private Logger logger;

    public LoggerPlus(Logger logger) {
        this.logger = logger;
    }

    public static class MethodLogger implements AutoCloseable {

        private LoggerPlus logPlus;
        private String methodName;
        private BiFunction<String, String, String> messageBlock = (String ___methodName, String ___message) ->
                new StringJoiner(" ").add(___methodName).add(___message).toString();

        public MethodLogger(String methodName, LoggerPlus logPlus, boolean withHeader) {
            this.methodName = methodName;
            this.logPlus = logPlus;
            if (withHeader) {
                if (this.logPlus.logger.isDebugEnabled()) {
                    this.logPlus.logger.debug(messageBlock.apply(this.methodName, "[BEGIN]"));
                }
            }
        }

        @Override
        public void close() {
            logPlus.debug(() -> messageBlock.apply(methodName, "[END]"));
        }

        public void debug(final String message) {
            debug(()-> message);
        }

        public void debug(Supplier<String> message) {
            logPlus.debug(() -> messageBlock.apply(methodName, message.get()));
        }

        public void info(final String message) {
            info(()-> message);
        }

        public void info(Supplier<String> message) {
            logPlus.info(() -> messageBlock.apply(methodName, message.get()));
        }

        public void error(final String message) {
            error(()-> message);
        }

        public void error(Supplier<String> message) {
            logPlus.error(() -> messageBlock.apply(methodName, message.get()));
        }

        public void warn(final String message) {
            warn(()-> message);
        }

        public void warn(Supplier<String> message) {
            logPlus.warn(() -> messageBlock.apply(methodName, message.get()));
        }

        public void trace(final String message) {
            trace(()-> message);
        }

        public void trace(Supplier<String> message) {
            logPlus.trace(() -> messageBlock.apply(methodName, message.get()));
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public void debug(final String message) {
        debug(()-> message);
    }

    public void debug(Supplier<String> message) {
        if (logger.isDebugEnabled()) {
            Optional.ofNullable(message).ifPresent(___message -> logger.debug(___message.get()));
        }
    }

    public void info(final String message) {
        info(()-> message);
    }

    public void info(Supplier<String> message) {
        if (logger.isInfoEnabled()) {
            Optional.ofNullable(message).ifPresent(___message -> logger.info(___message.get()));
        }
    }

    public void error(final String message) {
        error(()-> message);
    }

    public void error(Supplier<String> message) {
        if (logger.isErrorEnabled()) {
            Optional.ofNullable(message).ifPresent(___message -> logger.error(___message.get()));
        }
    }

    public void warn(final String message) {
        warn(()-> message);
    }

    public void warn(Supplier<String> message) {
        if (logger.isWarnEnabled()) {
            Optional.ofNullable(message).ifPresent(___message -> logger.warn(___message.get()));
        }
    }

    public void trace(final String message) {
        trace(()-> message);
    }

    public void trace(Supplier<String> message) {
        if (logger.isTraceEnabled()) {
            Optional.ofNullable(message).ifPresent(___message -> logger.trace(___message.get()));
        }
    }

    public MethodLogger logByMethodCall(String methodName, boolean withHeader) {
        return new MethodLogger(methodName, this, withHeader);
    }

    public MethodLogger logByMethodCall(String methodName) {
        return logByMethodCall(methodName, true);
    }

    public String getStackTraceAsString(Exception exp) {
        try(var sWriter = new StringWriter(); var pWriter = new PrintWriter(sWriter)) {
            exp.printStackTrace(pWriter);
            return sWriter.toString();
        }
        catch(IOException ioe) {}
        return null;
    }
}
