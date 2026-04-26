package net.justonedev.graphs.common.ui.drawable;

import net.justonedev.graphs.common.ui.Canvas;
import net.justonedev.graphs.common.ui.Shape;

public class Line extends Drawable {
    private final int toX;
    private final int toY;

    public Line(Circle fromVertex, Circle toVertex) {
        this(fromVertex.getX(), fromVertex.getY(), toVertex.getX(), toVertex.getY());
    }

    public Line(Position from, Position to) {
        this(from.x(), from.y(), to.x(), to.y());
    }

    public Line(int fromX, int fromY, int toX, int toY) {
        super(fromX, fromY);
        this.toX = toX;
        this.toY = toY;
    }

    public int getCanvasSourceX(Canvas canvas) {
        return super.getCanvasX(canvas);
    }

    public int getCanvasSourceY(Canvas canvas) {
        return super.getCanvasY(canvas);
    }

    public int getCanvasTargetX(Canvas canvas) {
        return super.translateCoordinate(this.toX, canvas.getCameraOffsetX(), canvas);
    }

    public int getCanvasTargetY(Canvas canvas) {
        return super.translateCoordinate(this.toY, canvas.getCameraOffsetY(), canvas);
    }

    @Override
    public Shape getShape() {
        return Shape.LINE;
    }
}
