package student;

import cz.cvut.atg.zui.astar.AbstractOpenList;

import java.util.Optional;
import java.util.PriorityQueue;

public class OpenList extends AbstractOpenList<Node> {
    private PriorityQueue<Node> queue = new PriorityQueue<>();

    protected Node poll() {
        return queue.poll();
    }

    private Optional<Node> getNodeWithId(long id) {
        return queue.stream().filter(x -> x.getNodeId() == id).findFirst();
    }

    protected void addOrUpdate(Node node) {
        Optional<Node> opt = getNodeWithId(node.getNodeId());

        if (!opt.isPresent()) {
            add(node);
            return;
        }

        Node a = opt.get();
        a.setEdge(node.getEdge());
        a.setHeuristicPrice(node.getHeuristicPrice());
        a.setPrevious(node.getPrevious());
        a.setRealPrice(node.getRealPrice());
        a.setStartNode(node.getStartNode());
        a.setEndNode(node.getEndNode());

        refreshPriority();
    }

    private void refreshPriority() {
        Node a = queue.poll();
        queue.add(a);
    }

    protected boolean hasNext() {
        return !queue.isEmpty();
    }

    protected boolean removeId(long nodeId) {
        return queue.removeIf(x -> x.getNodeId() == nodeId);
    }

    @Override
    protected boolean addItem(Node item) {
        return queue.add(item);
    }


}
