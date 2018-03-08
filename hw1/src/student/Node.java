package student;

import eu.superhub.wp5.planner.planningstructure.GraphEdge;
import eu.superhub.wp5.planner.planningstructure.GraphNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Node implements Comparable<Node> {
    private Node previous;

    private GraphEdge edge;
    private GraphNode startNode;
    private GraphNode endNode;

    private double realPrice;
    private double heuristicPrice;

    private Node(GraphNode endNode) {
        this.endNode = endNode;
    }

    protected Node(GraphNode startNode, GraphNode endNode, GraphEdge edge, Node previous, double heuristicPrice) {
        this.previous = previous;
        this.startNode = startNode;
        this.endNode = endNode;
        this.edge = edge;

        realPrice = previous.getRealPrice() + ((edge.getLengthInMetres() / 1000.0) / edge.getAllowedMaxSpeedInKmph());
        this.heuristicPrice = heuristicPrice;
    }

    public Node getPrevious() {
        return previous;
    }

    public GraphNode getStartNode() {
        return startNode;
    }

    public GraphNode getEndNode() {
        return endNode;
    }

    public double getHeuristicPrice() {
        return heuristicPrice;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public void setEdge(GraphEdge edge) {
        this.edge = edge;
    }

    public void setStartNode(GraphNode startNode) {
        this.startNode = startNode;
    }

    public void setEndNode(GraphNode endNode) {
        this.endNode = endNode;
    }

    public void setRealPrice(double realPrice) {
        this.realPrice = realPrice;
    }

    public void setHeuristicPrice(double heuristicPrice) {
        this.heuristicPrice = heuristicPrice;
    }

    protected GraphNode getNode(){
        return endNode;
    }

    protected long getNodeId() {
        return endNode.getId();
    }

    protected double getRealPrice() {
        return realPrice;
    }

    protected GraphEdge getEdge(){
        return edge;
    }

    protected double getPrice() {
        return realPrice + heuristicPrice;
    }

    protected List<GraphEdge> reconstructWayFromNode() {
        Stack<GraphEdge> s = new Stack<>();
        List<GraphEdge> result = new ArrayList<>(s.size());
        Node curr = this;

        while (curr != null) {
            s.push(curr.getEdge());
            curr = curr.previous;
        }
        s.pop();

        while (!s.empty()) {
            result.add(s.pop());
        }
        return result;
    }

    @Override
    public int compareTo(Node o) {
        return Double.compare(getPrice(), o.getPrice());
    }

    protected static Node CreateOriginNode(GraphNode endNode) {
        return new Node(endNode);
    }
}
