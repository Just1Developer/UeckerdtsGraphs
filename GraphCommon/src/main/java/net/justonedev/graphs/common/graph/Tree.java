package net.justonedev.graphs.common.graph;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class Tree extends Graph {
    @Getter
    protected Vertex root;

    public Tree(Vertex root) {
        super.insert(root);
        this.root = root;
    }

    @Override
    public void removeEdge(Edge edge, boolean removeUnconnectedVertices) {
        super.removeEdge(edge, removeUnconnectedVertices);
        // In case root was removed, add it back
        insert(root);
    }

    /**
     * Gets all vertices sorted by depth, not necessarily sorted within a layer.
     * @return a list of all vertices, beginning with the root.
     */
    @Override
    public List<Vertex> getVertices() {
        Queue<Vertex> remaining = new LinkedList<>();
        remaining.add(root);
        Set<Vertex> seen = new HashSet<>(edges.size());
        List<Vertex> vertices = new ArrayList<>(edges.size());
        while(!remaining.isEmpty()) {
            Vertex vertex = remaining.poll();
            vertices.add(vertex);
            seen.add(vertex);
            remaining.addAll(edges.get(vertex).stream().map(Edge::to).filter(v -> !seen.contains(v)).toList());
        }
        return vertices;
    }

    public List<Vertex> getChildren(Vertex parent) {
        return edges.get(parent).stream().map(Edge::to).filter(vertex -> !Objects.equals(vertex, parent)).toList();
    }
}
