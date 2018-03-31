package student;

import cz.cvut.atg.zui.astar.AbstractOpenList;
import cz.cvut.atg.zui.astar.PlannerInterface;
import cz.cvut.atg.zui.astar.RoadGraph;
import cz.cvut.atg.zui.astar.Utils;
import eu.superhub.wp5.planner.planningstructure.GraphEdge;
import eu.superhub.wp5.planner.planningstructure.GraphNode;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Planner implements PlannerInterface {
    private OpenList openList = new OpenList();
    private HashMap<Long, Double> openListContains = new HashMap<>();
    private GraphNode destination = null;

    private double maxSpeed = 0.0;

    @Override
    public List<GraphEdge> plan(RoadGraph graph, GraphNode origin, GraphNode destination) {
        maxSpeed = graph.getAllEdges().stream().max(Comparator.comparingDouble(GraphEdge::getAllowedMaxSpeedInKmph)).get().getAllowedMaxSpeedInKmph();
        this.destination = destination;

        Node start = Node.CreateOriginNode(origin);
        openListContains.put(start.getNodeId(), 0.0);
        expandNode(graph, start);

        Node processed = null;
        boolean planFound = false;
        while (openList.hasNext()) {
            processed = openList.poll();

            if (processed.getNodeId() == destination.getId()) {
                planFound = true;
                break;
            }

            expandNode(graph, processed);
        }

        return !planFound ? null : processed.reconstructWayFromNode();
    }

    private double getHeuristic(GraphNode node) {
        return Utils.distanceInKM(node, destination) / maxSpeed;
    }

    private void expandNode(RoadGraph graph, Node nodeToExpand) {
        List<GraphEdge> edges = graph.getNodeOutcomingEdges(nodeToExpand.getNodeId());
        if (edges == null) {
            return;
        }

        GraphNode startingNode = nodeToExpand.getNode();
        for (GraphEdge currEdge : edges) {
            GraphNode endNode = graph.getNodeByNodeId(currEdge.getToNodeId());
            Node newNode = new Node(startingNode, endNode, currEdge, nodeToExpand, getHeuristic(endNode));

            if (openListContains.containsKey(newNode.getNodeId())) {
                if (openListContains.get(newNode.getNodeId()) <= newNode.getPrice()) continue;

                openList.addOrUpdate(newNode); //this method will update existing node OR will call add method in the openList
                openListContains.put(newNode.getNodeId(), newNode.getPrice());
                continue;
            }

            openList.add(newNode);
            openListContains.put(newNode.getNodeId(), newNode.getPrice());
        }
    }

    @Override
    public AbstractOpenList getOpenList() {
        return openList;
    }
}
