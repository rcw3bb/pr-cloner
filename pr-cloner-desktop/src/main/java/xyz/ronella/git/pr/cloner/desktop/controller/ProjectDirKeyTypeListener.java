package xyz.ronella.git.pr.cloner.desktop.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Listener for changes in the focus state of the Git project directory TextField.
 * Updates the ComboBox with the list of Git remotes when the TextField loses focus.
 *
 * @author Ron Webb
 * @since 1.2.1
 */
public class ProjectDirKeyTypeListener implements ChangeListener<Boolean> {

    private final PRClonerController controller;
    private static boolean IS_ATTACHED;
    private static ProjectDirKeyTypeListener LISTENER;

    /**
     * Constructs a ProjectDirKeyTypeListener with the specified controller.
     *
     * @param controller The controller to be used for updating the ComboBox.
     */
    public ProjectDirKeyTypeListener(final PRClonerController controller) {
        this.controller = controller;
    }

    /**
     * Called when the focus state of the observed TextField changes.
     * Updates the ComboBox with the list of Git remotes when the TextField loses focus.
     *
     * @param observable The observable value.
     * @param oldValue The old value.
     * @param newValue The new value.
     */
    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        try {
            if (!newValue) {
                controller.updateCBORemotes();
            }

            controller.txtGitProjectDir.focusedProperty().removeListener(this);
        }
        finally {
            IS_ATTACHED = false;
        }
    }

    /**
     * Attaches the listener to the controller.
     *
     * @param controller The controller to be used for updating the ComboBox.
     */
    public static void attachListener(PRClonerController controller) {
        if (!IS_ATTACHED) {

            if (LISTENER==null) {
                LISTENER = new ProjectDirKeyTypeListener(controller);
            }

            controller.txtGitProjectDir.focusedProperty().addListener(LISTENER);

            IS_ATTACHED = true;
        }
    }

    /**
     * Detaches the listener from the controller.
     *
     * @param controller The controller to be used for updating the ComboBox.
     */
    public static void detachListener(PRClonerController controller) {
        if (IS_ATTACHED) {
            controller.txtGitProjectDir.focusedProperty().removeListener(LISTENER);

            IS_ATTACHED = false;
        }
    }
}