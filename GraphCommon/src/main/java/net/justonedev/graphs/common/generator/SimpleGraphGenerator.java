package net.justonedev.graphs.common.generator;

import net.justonedev.graphs.common.graph.Edge;
import net.justonedev.graphs.common.graph.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SimpleGraphGenerator implements GraphGenerator {
    private static final double DEFAULT_FILL_PERCENTAGE = 65.0;

    private final int minVertices;
    private final int maxVertices;
    private final double minFillPercentage;
    private final double maxFillPercentages;
    protected Random random;

    public SimpleGraphGenerator(int vertices) {
        this(vertices, DEFAULT_FILL_PERCENTAGE);
    }

    public SimpleGraphGenerator(int vertices, double fillPercentage) {
        this(vertices, fillPercentage, fillPercentage);
    }

    public SimpleGraphGenerator(int vertices, double minFillPercentage, double maxFillPercentage) {
        this(vertices, vertices, minFillPercentage, maxFillPercentage);
    }

    public SimpleGraphGenerator(int minVertices, int maxVertices, double minFillPercentage, double maxFillPercentage) {
        this(minVertices, maxVertices, minFillPercentage, maxFillPercentage, System.nanoTime());
    }

    public SimpleGraphGenerator(int minVertices, int maxVertices, double minFillPercentage, double maxFillPercentage, long seed) {
        this.minVertices = minVertices;
        this.maxVertices = maxVertices;
        this.minFillPercentage = Math.max(0, Math.min(100, minFillPercentage));
        this.maxFillPercentages = Math.max(0, Math.min(100, maxFillPercentage));
        setSeed(seed);
    }

    public void setSeed(long seed) {
        this.random = new Random(seed);
    }

    @Override
    public Graph generate() {
        // Easier to generate the full Graph and remove edges from there
        int vertices = pollVertexCount();
        double fillPercentage = pollFillPercentage();
        int edgesToRemove = (int) Math.round((vertices * vertices - vertices) * (1 - fillPercentage / 100));
        Graph graph = generateFullGraph(vertices);
        var edges = new ArrayList<>(graph.getAllDistinctEdges());
        Collections.shuffle(edges, random);
        for (Edge edge : edges.subList(0, edgesToRemove)) {
            graph.removeEdge(edge, true);
        }
        return graph;
    }

    protected int pollVertexCount() {
        return pollRandomValue(random, minVertices, maxVertices);
    }

    protected double pollFillPercentage() {
        return pollRandomValue(random, minFillPercentage, maxFillPercentages);
    }
}
