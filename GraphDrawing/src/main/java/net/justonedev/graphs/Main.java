package net.justonedev.graphs;

import net.justonedev.graphs.common.drawing.Drawing;
import net.justonedev.graphs.common.drawing.LinearLineDrawing;
import net.justonedev.graphs.common.generator.BinaryTreeGenerator;
import net.justonedev.graphs.common.generator.GraphGenerator;
import net.justonedev.graphs.common.generator.SimpleGraphGenerator;
import net.justonedev.graphs.common.generator.TreeGenerator;
import net.justonedev.graphs.common.graph.Graph;
import net.justonedev.graphs.common.graph.Tree;
import net.justonedev.graphs.common.graph.Vertex;
import net.justonedev.graphs.common.ui.Window;
import net.justonedev.graphs.common.ui.drawable.Position;
import net.justonedev.graphs.reingold_tilford.ReingoldTilford;

import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final int TREE_DEPTH = 5;
    private static final double TREE_PROPAGATION_PROBABILITY = 0.85;

    public static void main(String[] args) {
        Window window = new Window(30);
        GraphGenerator generator = new SimpleGraphGenerator(10);
        TreeGenerator treeGenerator = new BinaryTreeGenerator(TREE_DEPTH, TREE_PROPAGATION_PROBABILITY, 3);
        //window.drawGraphDrawing(generateSimpleDrawing(generator));

        // Reingold-Tilford:
        Tree binaryTree = treeGenerator.generate();
        ReingoldTilford reingoldTilford = new ReingoldTilford(binaryTree, 700, -200, 50, 80);
        reingoldTilford.setVertexDrawings();
        window.drawGraphDrawing(reingoldTilford);
    }

    private static Drawing generateSimpleDrawing(GraphGenerator graphGenerator) {
        Graph graph = graphGenerator.generate();
        var drawing = new LinearLineDrawing(graph);
        Map<Vertex, Position> positions = new HashMap<>();
        int y = -100;
        for (Vertex vertex : graph.getVertices()) {
            // This part is the complicated one in algorithms:
            positions.put(vertex, new Position((int) (250 + Math.random() * 800), y));
            y += 50;
        }
        drawing.setVertexDrawings(positions);
        return drawing;
    }
}
