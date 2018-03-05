package student;

import cz.cvut.atg.zui.astar.AbstractOpenList;
import cz.cvut.atg.zui.astar.PlannerInterface;
import cz.cvut.atg.zui.astar.RoadGraph;
import eu.superhub.wp5.planner.planningstructure.GraphEdge;
import eu.superhub.wp5.planner.planningstructure.GraphNode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class Planner implements PlannerInterface {

    @Override
    public List<GraphEdge> plan(RoadGraph graph, GraphNode origin, GraphNode destination) {
        throw new NotImplementedException();
    }

    @Override
    public AbstractOpenList getOpenList() {
        throw new NotImplementedException();
    }
}