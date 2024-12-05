package xyz.ronella.git.pr.cloner.desktop.controller;

import org.slf4j.LoggerFactory;
import xyz.ronella.logging.LoggerPlus;
import xyz.ronella.trivial.decorator.CloseableLock;

import java.io.*;
import java.nio.file.Paths;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Holds a serializable state of pr cloner.
 *
 * @author Ron Webb
 * @since 1.2.0
 */
@SuppressWarnings({"PMD.AvoidUsingVolatile", "PMD.NonThreadSafeSingleton"})
public final class PRClonerState implements Serializable {

    @Serial
    private static final long serialVersionUID = -5108020863981919108L;
    private final static LoggerPlus LOGGER_PLUS = new LoggerPlus(LoggerFactory.getLogger(PRClonerState.class));
    private static final String STATE_DIR = "state";
    private static final String FILENAME = "PRClonerState.obj";
    private static final Lock LOCK = new ReentrantLock();
    private static volatile PRClonerState state;


    /**
     * The directory chosen.
     */
    private String directory;

    /**
     * The remote selected.
     */
    private String remote;

    /**
     * The only method that can create an instance of PRClonerState.
     * @return An instance of PRClonerState.
     */
    @SuppressWarnings("PMD.UnusedLocalVariable")
    public static PRClonerState getInstance() {
        if (state == null) {
            try (var ___ = new CloseableLock(LOCK)) {
                if (state == null) {
                    state = new PRClonerState();
                }
            }
        }

        return state;
    }

    /**
     * Retrieves the state directory file.
     * Creates the directory if it does not exist.
     *
     * @return The state directory file.
     */
    private static File getStateDir() {
        final var stateDirFile = new File(STATE_DIR);
        if (!stateDirFile.exists()) {
            stateDirFile.mkdirs();
        }
        return stateDirFile;
    }

    /**
     * Retrieves the state file.
     *
     * @return The state file.
     */
    private static File getStateFile() {
        return Paths.get(getStateDir().getAbsolutePath(), FILENAME).toFile();
    }

    private PRClonerState() {}
    /**
     * Retrieves the directory.
     *
     * @return The directory.
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * Retrieves the selected remote.
     *
     * @return The selected remote.
     */
    public String getRemote() {
        return remote;
    }

    /**
     * Sets the directory.
     *
     * @param directory The directory.
     */
    public void setDirectory(final String directory) {
        this.directory = directory;
    }

    /**
     * Sets the remote.
     *
     * @param remote The remote.
     */
    public void setRemote(final String remote) {
        this.remote = remote;
    }

    /**
     * Saves the current state to a file.
     */
    @SuppressWarnings({"PMD.AvoidFileStream", "PMD.AvoidThrowingRawExceptionTypes"})
    public void save() {
        final var filename = getStateFile();
        try(var mLOG = LOGGER_PLUS.groupLog("save")) {
            try (var objOutputStream = new ObjectOutputStream(new FileOutputStream(filename.getAbsolutePath()))) {
                objOutputStream.writeObject(this);
                objOutputStream.flush();
            } catch (IOException ioException) {
                mLOG.error(LOGGER_PLUS.getStackTraceAsString(ioException));
                throw new RuntimeException(ioException);
            }
        }
    }

    /**
     * Restores the state from a file.
     */
    @SuppressWarnings({"PMD.AvoidFileStream", "PMD.AvoidThrowingRawExceptionTypes"})
    public void restore() {
        final var filename = getStateFile();
        try(var mLOG = LOGGER_PLUS.groupLog("restore")) {
            if (filename.exists()) {
                try (var objectInputStream = new ObjectInputStream(new FileInputStream(filename.getAbsolutePath()))) {
                    final var state = (PRClonerState) objectInputStream.readObject();
                    this.directory = state.directory;
                    this.remote = state.remote;
                } catch (IOException | ClassNotFoundException exception) {
                    mLOG.error(LOGGER_PLUS.getStackTraceAsString(exception));
                    throw new RuntimeException(exception);
                }
            }
        }
    }
}