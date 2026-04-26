package net.justonedev.graphs.common.generator;

import net.justonedev.graphs.common.graph.Edge;
import net.justonedev.graphs.common.graph.Graph;
import net.justonedev.graphs.common.graph.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface GraphGenerator {
    Graph generate();

    default Graph generateFullGraph(int vertices) {
        Graph graph = new Graph();
        List<Vertex> vertexList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            vertexList.add(new Vertex());
        }
        for (int i = 0; i < vertexList.size(); i++) {
            for (int j = i + 1; j < vertexList.size(); j++) {
                graph.addEdge(new Edge(
                        vertexList.get(i),
                        vertexList.get(j),
                        true
                ));
            }
        }
        return graph;
    }

    /**
     * Generates a random double value between a minimum and maximum value.
     * @param random the random object to use.
     * @param min the minimum value, inclusive.
     * @param max the maximum value, exclusive.
     * @return a random double value between the given bounds.
     */
    default double pollRandomValue(Random random, double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }

    /**
     * Generates a random integer value between a minimum and maximum value.
     * <p>
     *     Contrary to other randomness methods, the maximum value is inclusive.
     * </p>
     * @param random the random object to use.
     * @param min the minimum value, inclusive.
     * @param max the maximum value, inclusive.
     * @return a random integer value between the given bounds.
     */
    default int pollRandomValue(Random random, int min, int max) {
        return ((int) (random.nextDouble() * (max - min + 1))) + min;
    }
}
