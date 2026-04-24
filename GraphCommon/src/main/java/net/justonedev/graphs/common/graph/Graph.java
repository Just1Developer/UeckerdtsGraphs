package net.justonedev.graphs.common.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A simple undirected graph.
 * Allows multi-edges and loops.
 */
public class Graph {
    protected final Map<Vertex, List<Edge>> edges;

    public Graph() {
        edges = new HashMap<>();
    }

    public boolean contains(Vertex vertex) {
        return edges.containsKey(vertex);
    }

    public List<Edge> getEdges(Vertex vertex) {
        return edges.getOrDefault(vertex, new ArrayList<>());
    }

    public List<Vertex> getVertices() {
        return new ArrayList<>(edges.keySet());
    }

    public Set<Edge> getAllDistinctEdges() {
        return getVertices().stream().map(this::getEdges).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    public void insert(Vertex vertex) {
        if (!contains(vertex)) edges.put(vertex, new ArrayList<>());
    }

    public void addEdge(Edge edge) {
        insert(edge.from());
        insert(edge.to());
        edges.get(edge.from()).add(edge);
        edges.get(edge.to()).add(edge);
    }

    public boolean isSimple() {
        return !hasLoop() && !hasMultiedge();
    }

    public boolean hasLoop() {
        // todo
        return true;
    }

    public boolean hasMultiedge() {
        // todo
        return true;
    }
}
