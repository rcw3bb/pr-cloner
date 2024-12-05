package xyz.ronella.git.pr.cloner.desktop.common;

import javafx.scene.image.Image;

import java.util.Objects;

/**
 * A utility class for loading images used in the application.
 *
 * @author Ron Webb
 */
public final class Images {

    // The file name of the icon image.
    private static final String ICON_FILE = "icon.png";

    /**
     * The icon image loaded from the resources.
     * This image is loaded using the context class loader of the current thread.
     */
    public static final Image ICON = new Image(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(ICON_FILE)));

    // Private constructor to prevent instantiation.
    private Images() {}

}