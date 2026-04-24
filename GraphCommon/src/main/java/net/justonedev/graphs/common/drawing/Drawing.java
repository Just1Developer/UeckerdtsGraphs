package net.justonedev.graphs.common.drawing;

import net.justonedev.graphs.common.ui.drawable.Circle;
import net.justonedev.graphs.common.ui.drawable.Drawable;

import java.util.List;

public interface Drawing {
    List<Drawable> getEdgeDrawables();
    List<Circle> getVertices();
}
