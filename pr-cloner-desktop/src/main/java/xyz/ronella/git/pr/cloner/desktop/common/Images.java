package xyz.ronella.git.pr.cloner.desktop.common;

import javafx.scene.image.Image;

public class Images {

    private static final String ICON_FILE = "icon.png";

    private Images() {
    }

    public static Image ICON = new Image(Images.class.getClassLoader().getResourceAsStream(ICON_FILE));
}
