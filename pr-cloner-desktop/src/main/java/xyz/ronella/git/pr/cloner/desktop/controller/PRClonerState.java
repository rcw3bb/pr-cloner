package xyz.ronella.git.pr.cloner.desktop.controller;

import org.slf4j.LoggerFactory;
import xyz.ronella.logging.LoggerPlus;

import java.io.*;
import java.nio.file.Paths;

/**
 * Holds a serializable state of pr cloner.
 *
 * @author Ron Webb
 * @since 1.2.0
 */
public final class PRClonerState implements Serializable {

    @Serial
    private static final long serialVersionUID = -5108020863981919108L;
    private final static LoggerPlus LOGGER_PLUS = new LoggerPlus(LoggerFactory.getLogger(PRClonerState.class));
    private static final String STATE_DIR = "state";
    private static final String FILENAME = "PRClonerState.obj";
    private static PRClonerState STATE;

    /**
     * The only method that can create an instance of PRClonerState.
     * @return An instance of PRClonerState.
     */
    public static PRClonerState getInstance() {
        if (STATE==null) {
            STATE = new PRClonerState();
        }

        return STATE;
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
     * The directory chosen.
     */
    private String directory;

    /**
     * The remote selected.
     */
    private String remote;

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
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    /**
     * Sets the remote.
     *
     * @param remote The remote.
     */
    public void setRemote(String remote) {
        this.remote = remote;
    }

    /**
     * Saves the current state to a file.
     */
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