package xyz.ronella.git.pr.cloner.desktop.controller;

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
    private static final String STATE_DIR = "bin/state";
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

    private static File getStateDir() {
        final var stateDirFile = new File(STATE_DIR);
        if (!stateDirFile.exists()) {
            stateDirFile.mkdirs();
        }
        return stateDirFile;
    }

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
     * The directory.
     * @return The directory.
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * The selected remote.
     * @return
     */
    public String getRemote() {
        return remote;
    }

    /**
     * Capture the directory.
     * @param directory The directory.
     */
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    /**
     * Capture the remote.
     * @param remote The remote.
     */
    public void setRemote(String remote) {
        this.remote = remote;
    }

    public void save() {
        final var filename = getStateFile();
        try (var objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename.getAbsolutePath()))) {
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void restore() {
        final var filename = getStateFile();
        if (filename.exists()) {
            try (var objectInputStream = new ObjectInputStream(new FileInputStream(filename.getAbsolutePath()))) {
                final var state = (PRClonerState) objectInputStream.readObject();
                this.directory = state.directory;
                this.remote = state.remote;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
