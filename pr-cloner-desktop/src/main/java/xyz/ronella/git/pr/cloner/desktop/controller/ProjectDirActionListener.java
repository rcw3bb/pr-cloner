package xyz.ronella.git.pr.cloner.desktop.controller;

import javafx.beans.value.ChangeListener;

import java.util.Optional;

/**
 * Listener for changes in the Git project directory TextField.
 * Updates the ComboBox with the list of Git remotes.
 *
 * @author Ron Webb
 * @since 1.2.1
 */
public class ProjectDirActionListener implements ChangeListener<String> {

    private final PRClonerController controller;

    /**
     * Constructs a ProjectDirListener with the specified TextField and ComboBox.
     *
     * @param controller The TextField for the Git project directory.
     */
    public ProjectDirActionListener(final PRClonerController controller) {
        this.controller = controller;
    }

    /**
     * Called when the value of the observed TextField changes.
     * Updates the ComboBox with the list of Git remotes.
     *
     * @param observable The observable value.
     * @param oldValue The old value.
     * @param newValue The new value.
     */
    @Override
    public void changed(final javafx.beans.value.ObservableValue<? extends String> observable, final String oldValue,
                        final String newValue) {

        Optional.ofNullable(newValue).ifPresent(___ -> controller.updateCBORemotes());

    }
}