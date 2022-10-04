package net.yetihafen.javafx.customcaption;


import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;

import java.util.ArrayList;

public class DragRegion {

    private final ArrayList<Node> excludedBounds = new ArrayList<>();
    private final Node base;

    public DragRegion(Node base) {
        this.base = base;
    }

    /**
     * check if any given point is in the specified area
     * (excluded areas are considered)
     * @param p the {@link Point2D} (screen coordinates)
     * @return true if the point is inside the specified area
     */
    public boolean contains(Point2D p) {
        return this.contains(p.getX(), p.getY());
    }

    /**
     * check if any given point is in the specified area
     * (excluded areas are considered)
     * @param x the x coordinate (in screen coordinates)
     * @param y the y coordinate (in screen coordinates)
     * @return true if the point is inside the specified area
     */
    public boolean contains(double x, double y) {
        // check if point is in excluded region
        if(!excludedBounds.isEmpty()) {
            for(Node node : excludedBounds) {
                // return false if point is in excluded region
                if(nodeToScreenBounds(node).contains(x, y)) return false;
            }
        }
        Bounds baseBounds = nodeToScreenBounds(base);
        // return if point is contained in the specified region
        return baseBounds.getMaxX() > x && baseBounds.getMinX() < x &&
                baseBounds.getMaxY() > y && baseBounds.getMinY() < y;
    }

    private Bounds nodeToScreenBounds(Node node) {
        return node.localToScreen(node.getBoundsInLocal());
    }


    /**
     * adds a node to exclude its area
     * @param node the node to exclude
     */
    public DragRegion addExcludeBounds(Node node) {
        excludedBounds.add(node);
        return this;
    }
}
