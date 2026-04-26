package net.justonedev.graphs.common.generator;

import net.justonedev.graphs.common.graph.Edge;
import net.justonedev.graphs.common.graph.Graph;
import net.justonedev.graphs.common.graph.Tree;
import net.justonedev.graphs.common.graph.Vertex;

public class TreeGenerator extends SimpleGraphGenerator {
    private static final int DEFAULT_MIN_CHILDREN = 0;
    private static final int DEFAULT_MAX_CHILDREN = 3;

    private final int maxDepth;
    private final double propagationProbability;
    private final int minChildren;
    private final int maxChildren;

    public TreeGenerator(int maxDepth, double propagationProbability) {
        this(Integer.MAX_VALUE, maxDepth, propagationProbability);
    }

    public TreeGenerator(int vertices, int maxDepth, double propagationProbability) {
        this(vertices, maxDepth, propagationProbability, DEFAULT_MIN_CHILDREN, DEFAULT_MAX_CHILDREN);
    }

    public TreeGenerator(int vertices, int maxDepth, double propagationProbability, int minChildren, int maxChildren) {
        super(vertices);
        this.maxDepth = maxDepth;
        this.propagationProbability = propagationProbability;
        this.minChildren = minChildren;
        this.maxChildren = maxChildren;
    }

    @Override
    public Graph generate() {
        int maxVertices = pollVertexCount();
        Vertex root = new Vertex();
        Tree tree = new Tree(root);
        propagate(tree, root, maxVertices, 0);
        return tree;
    }

    private void propagate(Tree tree, Vertex parent, int maxVertices, int depth) {
        if (depth >= maxDepth) return;
        if (random.nextDouble() > propagationProbability) return;
        if (tree.getSize() >= maxVertices) return;
        int children = pollChildCount();
        for (int i = 0; i < children; i++) {
            Vertex child = new Vertex();
            tree.addEdge(new Edge(parent, child, true));
            propagate(tree, child, maxVertices, depth + 1);
        }
    }

    protected int pollChildCount() {
        return pollRandomValue(random, minChildren, maxChildren);
    }
}
