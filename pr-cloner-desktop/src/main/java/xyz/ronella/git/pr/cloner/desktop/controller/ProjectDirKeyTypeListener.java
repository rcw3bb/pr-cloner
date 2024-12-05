package xyz.ronella.git.pr.cloner.desktop.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import xyz.ronella.trivial.decorator.CloseableLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Listener for changes in the focus state of the Git project directory TextField.
 * Updates the ComboBox with the list of Git remotes when the TextField loses focus.
 *
 * @author Ron Webb
 * @since 1.2.1
 */
@SuppressWarnings({"PMD.AvoidUsingVolatile", "PMD.NonThreadSafeSingleton"})
public class ProjectDirKeyTypeListener implements ChangeListener<Boolean> {

    private final static Lock LOCK = new ReentrantLock();

    private final PRClonerController controller;
    private static boolean isAttached;
    private static volatile ProjectDirKeyTypeListener listener;

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
    public void changed(final ObservableValue<? extends Boolean> observable, final Boolean oldValue,
                        final Boolean newValue) {
        try {
            if (!newValue) {
                controller.updateCBORemotes();
            }

            controller.txtGitProjectDir.focusedProperty().removeListener(this);
        }
        finally {
            isAttached = false;
        }
    }

    /**
     * Attaches the listener to the controller.
     *
     * @param controller The controller to be used for updating the ComboBox.
     */
    @SuppressWarnings({"PMD.UnusedLocalVariable", "PMD.AvoidDeeplyNestedIfStmts"})
    public static void attachListener(final PRClonerController controller) {
        if (!isAttached) {

            if (listener == null) {
                try (var ___ = new CloseableLock(LOCK)) {
                    if (listener ==null) {
                        listener = new ProjectDirKeyTypeListener(controller);
                    }
                }
            }

            controller.txtGitProjectDir.focusedProperty().addListener(listener);

            isAttached = true;
        }
    }

    /**
     * Detaches the listener from the controller.
     *
     * @param controller The controller to be used for updating the ComboBox.
     */
    public static void detachListener(final PRClonerController controller) {
        if (isAttached) {
            controller.txtGitProjectDir.focusedProperty().removeListener(listener);

            isAttached = false;
        }
    }
}