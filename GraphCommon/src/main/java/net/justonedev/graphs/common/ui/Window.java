package net.justonedev.graphs.common.ui;

import lombok.Setter;
import net.justonedev.graphs.common.drawing.Drawing;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.concurrent.TimeUnit;

public class Window {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    private final JFrame frame;
    private final Canvas canvas;
    private volatile boolean run;
    @Setter
    private double framesPerSecond;

    public Window(double fps) {
        this.framesPerSecond = fps;
        frame = new JFrame("Neural Network Renderer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        canvas = new Canvas(this);
        frame.add(canvas);
        registerEvents();
        frame.setVisible(true);
        frame.requestFocus();
        beginRender();
    }

    public void drawGraphDrawing(Drawing graph) {
        canvas.addGraph(graph);
    }

    public Dimension getSize() {
        return frame.getSize();
    }

    public void beginRender() {
        run = true;
        final long nanosPerFrame = Math.round(1e9 / framesPerSecond);
        Thread.startVirtualThread(() -> {
            while (run) {
                long time = System.nanoTime();
                this.canvas.repaint();
                long delta = System.nanoTime() - time;
                long remainder = nanosPerFrame - delta;
                if (remainder > 0) {
                    try {
                        TimeUnit.NANOSECONDS.sleep(remainder);
                    } catch (InterruptedException ignored) {}
                }
            }
        });
    }

    //region MouseEvents

    private void registerEvents() {
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                isDragging = false;
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    int deltaX = e.getX() - draggingBeginX;
                    int deltaY = e.getY() - draggingBeginY;
                    draggingBeginX = e.getX();
                    draggingBeginY = e.getY();
                    canvas.updateCameraPosition(deltaX, deltaY);
                } else {
                    draggingBeginX = e.getX();
                    draggingBeginY = e.getY();
                    isDragging = true;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isDragging = false;
            }
        });

        canvas.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() > 0) onMouseScrollUp();
                else if (e.getWheelRotation() < 0) onMouseScrollDown();
            }
        });
    }

    private volatile boolean isDragging = false;
    private volatile int draggingBeginX;
    private volatile int draggingBeginY;

    public void onMouseScrollUp() {
        canvas.zoomOut();
    }

    public void onMouseScrollDown() {
        canvas.zoomIn();
    }
}
