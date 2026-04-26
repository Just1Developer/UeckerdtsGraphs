package net.justonedev.graphs.common.drawing;

import net.justonedev.graphs.common.graph.Graph;
import net.justonedev.graphs.common.graph.Vertex;
import net.justonedev.graphs.common.ui.drawable.Drawable;
import net.justonedev.graphs.common.ui.drawable.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseDrawing implements Drawing {
    protected Set<Drawable> vertices;
    protected Set<Drawable> edges;
    protected Graph graph;

    public BaseDrawing(Graph graph) {
        this.graph = graph;
        this.vertices = new HashSet<>();
        this.edges = new HashSet<>();
    }

    protected abstract void setVertexDrawings(Map<Vertex, Position> positions);
    protected abstract void setLineDrawings(Map<Vertex, Position> positions);

    @Override
    public List<Drawable> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public List<Drawable> getEdgeDrawables() {
        return new ArrayList<>(edges);
    }
}
