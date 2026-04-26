package net.justonedev.graphs.common.drawing;

import net.justonedev.graphs.common.graph.Edge;
import net.justonedev.graphs.common.graph.Graph;
import net.justonedev.graphs.common.graph.Vertex;
import net.justonedev.graphs.common.ui.drawable.Circle;
import net.justonedev.graphs.common.ui.drawable.Line;
import net.justonedev.graphs.common.ui.drawable.Position;

import java.util.Map;

public class LinearLineDrawing extends BaseDrawing {
    public LinearLineDrawing(Graph graph) {
        super(graph);
    }

    @Override
    public void setVertexDrawings(Map<Vertex, Position> positions) {
        for (var vertex : positions.values()) {
            super.vertices.add(new Circle(vertex.x(), vertex.y()));
        }
        setLineDrawings(positions);
    }

    @Override
    protected void setLineDrawings(Map<Vertex, Position> positions) {
        for (Edge edge : graph.getAllDistinctEdges()) {
            Position fromPosition = positions.get(edge.from());
            Position toPosition = positions.get(edge.to());
            this.edges.add(new Line(
                    fromPosition,
                    toPosition
            ));
        }
    }
}
