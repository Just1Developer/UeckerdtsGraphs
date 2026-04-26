package net.justonedev.graphs.common.generator;

public class BinaryTreeGenerator extends TreeGenerator {
    public BinaryTreeGenerator(int maxDepth, double propagationProbability) {
        super(Integer.MAX_VALUE, maxDepth, propagationProbability, 2, 2);
    }
}
