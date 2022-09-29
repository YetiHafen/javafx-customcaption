package net.yetihafen.javafx.customcaption;

import javafx.scene.Node;
import lombok.Getter;

@Getter
public class AdvancedCaptionConfiguration {
    private Node captionDragRegion;

    public AdvancedCaptionConfiguration() {
    }

    /**
     * Specify the {@link Node} defining the draggable area
     * @param captionDragRegion the {@link Node}
     */
    public AdvancedCaptionConfiguration setCaptionDragRegion(Node captionDragRegion) {
        this.captionDragRegion = captionDragRegion;
        return this;
    }
}
