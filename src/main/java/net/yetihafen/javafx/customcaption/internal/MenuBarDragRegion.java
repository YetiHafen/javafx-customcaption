package net.yetihafen.javafx.customcaption.internal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import net.yetihafen.javafx.customcaption.DragRegion;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarDragRegion extends DragRegion implements ShowInitializable {

    public MenuBarDragRegion(MenuBar base) {
        super(base);
    }

    @Override
    public void showInit() {
        // exclude all elements in MenuBar from DragRegion
        HBox box = (HBox) ((MenuBar) getBase()).getChildrenUnmodifiable().get(0);
        for(Node node : box.getChildrenUnmodifiable()) {
            super.addExcludeBounds(node);
        }
    }
}
