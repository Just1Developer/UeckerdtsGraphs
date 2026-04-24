package net.justonedev.graphs.common.parser;

public abstract class Parser {

    public static Parser construct(SyntaxType type) {
        return switch (type) {
            case MERMAID -> new MermaidParser();
        };
    }
}
