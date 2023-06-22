package com.mvailati84.diagram_challenge.service;

public class NodeNotFoundException extends Throwable {
    public NodeNotFoundException(String startNodeId) {
        super("Not able to find a node with id " + startNodeId);
    }
}
