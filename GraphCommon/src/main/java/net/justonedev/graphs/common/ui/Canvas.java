package net.justonedev.graphs.common.ui;

import lombok.Getter;
import net.justonedev.graphs.common.drawing.Drawing;
import net.justonedev.graphs.common.ui.drawable.Drawable;
import net.justonedev.graphs.common.ui.drawable.Line;

import javax.swing.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Canvas extends JPanel {
    public static final int VERTEX_SIZE = 30;
    private static final int LINE_WIDTH = 1;

    private static final int X_OFFSET = 10;
    private static final int Y_OFFSET = 200;
    private static final double MIN_ZOOM_LEVEL = 0.5;
    private static final double MAX_ZOOM_LEVEL = 5;
    private static final double ZOOM_STEP = 0.05;

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color VERTEX_BACKGROUND_COLOR = Color.WHITE;
    private static final Color VERTEX_BORDER_COLOR = Color.BLACK;
    private static final Color VERTEX_CONNECTION_COLOR = Color.darkGray;
    
    private final List<Drawable> drawables;

    private final Map<Line, Float> brightness = new HashMap<>();

    @Getter
    private int cameraOffsetX;
    @Getter
    private int cameraOffsetY;
    @Getter
    private double zoomLevel;

    public Canvas(Window window) {
        super.setSize(window.getSize());
        cameraOffsetX = X_OFFSET;
        cameraOffsetY = Y_OFFSET;
        zoomLevel = 1.0;
        this.drawables = new ArrayList<>();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;

        int VERTEXSize = (int) Math.round(VERTEX_SIZE * zoomLevel);
        int VERTEXBorderWidth = (int) Math.round(VERTEX_SIZE * zoomLevel * 0.07);
        int lineWidth = (int) Math.round(LINE_WIDTH * zoomLevel);
        int VERTEXCenterOffset = VERTEXSize/2;

        graphics.setBackground(BACKGROUND_COLOR);

        for (Drawable drawable : drawables) {
            switch (drawable.getShape()) {
                case CIRCLE -> {
                    graphics.setStroke(new BasicStroke(VERTEXBorderWidth));
                    graphics.setColor(VERTEX_BACKGROUND_COLOR);
                    graphics.fillOval(drawable.getCanvasX(this), drawable.getCanvasY(this), VERTEXSize, VERTEXSize);
                    graphics.setColor(VERTEX_BORDER_COLOR);
                    graphics.drawOval(drawable.getCanvasX(this), drawable.getCanvasY(this), VERTEXSize, VERTEXSize);
                }
                case LINE -> {
                    Line line = (Line) drawable;
                    graphics.setStroke(new BasicStroke(lineWidth));

                    graphics.setColor(VERTEX_CONNECTION_COLOR);
                    graphics.drawLine(line.getCanvasSourceX(this) + VERTEXCenterOffset, line.getCanvasSourceY(this) + VERTEXCenterOffset,
                            line.getCanvasTargetX(this) + VERTEXCenterOffset, line.getCanvasTargetY(this) + VERTEXCenterOffset);
                }
            }
        }
    }

    public void addGraph(Drawing graph) {
        drawables.addAll(graph.getEdgeDrawables());
        drawables.addAll(graph.getVertices());
    }

    //region Mouse Events

    public void zoomOut() {
        double oldZoomLevel = zoomLevel;
        zoomLevel = Math.max(MIN_ZOOM_LEVEL, zoomLevel - ZOOM_STEP);
        double relativeUpdate = oldZoomLevel - zoomLevel;
        updateCameraPosition((int) (relativeUpdate * getWidth() / 2), (int) (relativeUpdate * getHeight() / 2));
    }

    public void zoomIn() {
        double oldZoomLevel = zoomLevel;
        zoomLevel = Math.min(MAX_ZOOM_LEVEL, zoomLevel + ZOOM_STEP);
        double relativeUpdate = oldZoomLevel - zoomLevel;
        updateCameraPosition((int) (relativeUpdate * getWidth() / 2), (int) (relativeUpdate * getHeight() / 2));
    }

    public void updateCameraPosition(int deltaX, int deltaY) {
        updateCameraPosition(deltaX, deltaY, true);
    }
    public void updateCameraPosition(int deltaX, int deltaY, boolean useScale) {
        double scale = useScale ? zoomLevel : 1;
        this.cameraOffsetX += (int) (deltaX / scale);
        this.cameraOffsetY += (int) (deltaY / scale);
    }
}
