package net.justonedev.graphs.common.ui.drawable;

import net.justonedev.graphs.common.ui.Shape;

public class Circle extends Drawable {
    public Circle(int x, int y) {
        super(x, y);
    }

    @Override
    public Shape getShape() {
        return Shape.CIRCLE;
    }
}
