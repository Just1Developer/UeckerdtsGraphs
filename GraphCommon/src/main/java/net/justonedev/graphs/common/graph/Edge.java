package net.justonedev.graphs.common.graph;

import java.util.Objects;
import java.util.Set;

public record Edge(Vertex from, Vertex to, boolean undirected) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        if (edge.undirected() != undirected()) return false;
        if (undirected) {
            return Set.of(from, to).equals(Set.of(edge.from, edge.to));
        }
        return Objects.equals(to, edge.to) && Objects.equals(from, edge.from);
    }

    @Override
    public int hashCode() {
        if (undirected) {
            return Set.of(from, to).hashCode();
        }
        return Objects.hash(from, to);
    }
}
