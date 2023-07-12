package com.mvailati84.diagram_challenge.service;

import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BpmnDiagramPathFinder {

    public List<String> findPathBetween(BpmnModelInstance modelInstance, String startNodeId, String endNodeId) throws NodeNotFoundException {
        /* check if both nodes exist */
        FlowNode startNode = findNodeById(modelInstance, startNodeId);
        findNodeById(modelInstance, endNodeId);

        /* prepare the path and add the first node */
        List<String> path = new ArrayList<>();
        path.add(startNodeId);

        return nextStep(path, startNode, endNodeId);
    }

    private List<String> nextStep(List<String> path, FlowNode nextNode, String endNodeId) {

        List<FlowNode> outgoing = getAllOutgoing(nextNode);

        for (FlowNode node : outgoing) {
            if (loopDetected(path, node)) {
                continue;
            }

            if (endNodeReached(path, endNodeId)) {
                return path;
            }

            /* do another step in the graph */
            path.add(node.getId());
            nextStep(path, node, endNodeId);
        }

        if (endNodeReached(path, endNodeId)) {
            return path;
        }

        /* if I'm here, no further step available, remove last item and return for trying a new path */
        path.remove(path.size() - 1);
        return path;
    }

    private boolean endNodeReached(List<String> path, String node) {
        return node.equals(path.get(path.size() - 1));
    }

    private boolean loopDetected(List<String> path, FlowNode node) {
        return path.contains(node.getId());
    }

    private List<FlowNode> getAllOutgoing(FlowNode node) {
        return node.getOutgoing()
                .stream()
                .map(SequenceFlow::getTarget)
                .toList();
    }

    private static FlowNode findNodeById(BpmnModelInstance modelInstance, String nodeId) throws NodeNotFoundException {
        FlowNode node = modelInstance.getModelElementById(nodeId);
        if (node == null) {
            throw new NodeNotFoundException(nodeId);
        }

        return node;
    }
}
