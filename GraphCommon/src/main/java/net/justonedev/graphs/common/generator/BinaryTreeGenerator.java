package net.justonedev.graphs.common.generator;

public class BinaryTreeGenerator extends TreeGenerator {
    public BinaryTreeGenerator(int maxDepth, double propagationProbability) {
        this(maxDepth, propagationProbability, System.nanoTime());
    }
    public BinaryTreeGenerator(int maxDepth, double propagationProbability, long seed) {
        super(Integer.MAX_VALUE, maxDepth, propagationProbability, 2, 2);
        this.setSeed(seed);
    }
}
