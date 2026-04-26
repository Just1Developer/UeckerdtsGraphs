package net.justonedev.graphs.reingold_tilford;

import net.justonedev.graphs.common.drawing.LinearLineDrawing;
import net.justonedev.graphs.common.graph.Tree;
import net.justonedev.graphs.common.graph.Vertex;
import net.justonedev.graphs.common.ui.drawable.Position;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ReingoldTilford extends LinearLineDrawing {
    private final int xOffset;
    private final int yOffset;
    private final int xMultiplier;
    private final int yStep;
    private final Map<Vertex, Integer> shiftMap;

    // Currently only supports binary trees
    public ReingoldTilford(Tree binaryTree, int xOffset, int yOffset, int xMultiplier, int yStep) {
        super(binaryTree);
        this.shiftMap = new HashMap<>();
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xMultiplier = xMultiplier;
        this.yStep = yStep;
    }

    public void setVertexDrawings() {
        Map<Vertex, Position> positions = new HashMap<>();
        shiftMap.clear();
        Tree tree = (Tree) graph;
        buildContours(tree, tree.getRoot());
        Queue<VertexDrawingData> data = new LinkedList<>();
        data.offer(new VertexDrawingData(tree.getRoot(), 0, 0, yOffset));

        while (!data.isEmpty()) {
            VertexDrawingData dataPoint = data.poll();
            positions.put(dataPoint.root(), new Position(xOffset + xMultiplier * dataPoint.xCoord(), dataPoint.yCoord()));
            for (Vertex child : tree.getChildren(dataPoint.root())) {
                data.offer(new VertexDrawingData(child, this.shiftMap.get(child), dataPoint.xCoord() + this.shiftMap.get(child), dataPoint.yCoord() + yStep));
            }
        }
        super.setVertexDrawings(positions);
    }

    private record VertexDrawingData(Vertex root, int currentShift, int xCoord, int yCoord) {
    }

    private Contours buildContours(Tree tree, Vertex root) {
        var children = tree.getChildren(root);
        if (children.isEmpty()) {
            // Leaf vertex. Create new contour
            return new Contours();
        }
        // Assume binary tree
        Contours left = buildContours(tree, children.getFirst());
        Contours right = buildContours(tree, children.getLast());
        left.mergeRight(right);
        int shift = left.getShift();
        shiftMap.put(children.getFirst(), -shift);
        shiftMap.put(children.getLast(), shift);
        return left;
    }
}
