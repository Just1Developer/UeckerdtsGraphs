package net.justonedev.graphs;

import net.justonedev.graphs.common.drawing.Drawing;
import net.justonedev.graphs.common.drawing.LinearLineDrawing;
import net.justonedev.graphs.common.generator.GraphGenerator;
import net.justonedev.graphs.common.generator.SimpleGraphGenerator;
import net.justonedev.graphs.common.graph.Graph;
import net.justonedev.graphs.common.graph.Vertex;
import net.justonedev.graphs.common.ui.Window;
import net.justonedev.graphs.common.ui.drawable.Position;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Window window = new Window(30);
        window.drawGraphDrawing(generateSimpleDrawing(10));
    }

    private static Drawing generateSimpleDrawing(@SuppressWarnings("SameParameterValue") int vertices) {
        GraphGenerator graphGenerator = new SimpleGraphGenerator(vertices);
        Graph graph = graphGenerator.generate();
        var drawing = new LinearLineDrawing(graph);
        Map<Vertex, Position> positions = new HashMap<>();
        for (Vertex vertex : graph.getVertices()) {
            // This part is the complicated one in algorithms:
            positions.put(vertex, new Position((int) (250 + Math.random() * 800), (int) (-100 + Math.random() * 600)));
        }
        drawing.setVertexDrawings(positions);
        return drawing;
    }
}
