package com.mvailati84.diagram_challenge;

import com.mvailati84.diagram_challenge.service.BpmnDiagramPathFinder;
import com.mvailati84.diagram_challenge.service.BpmnFetcher;
import com.mvailati84.diagram_challenge.service.NodeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MainRunner implements CommandLineRunner {

    public static final String BPMN_MODEL_URL = "https://n35ro2ic4d.execute-api.eu-central-1.amazonaws.com/prod/engine-rest/process-definition/key/invoice/xml";
    final BpmnFetcher fetcher;
    final BpmnDiagramPathFinder pathFinder;

    public MainRunner(BpmnFetcher fetcher, BpmnDiagramPathFinder pathFinder) {
        this.fetcher = fetcher;
        this.pathFinder = pathFinder;
    }

    @Override
    @SuppressWarnings("squid:S106")
    public void run(String... args) throws Exception {
        log.info("Start diagram path analyzer with args [{}]", String.join(",", args));

        existsInCaseOfInvalidArguments(args);

        BpmnModelInstance modelInstance = fetcher.fetchRemotely(BPMN_MODEL_URL);
        log.info("Bpmn model retrieved with success");

        String startNodeId = args[0];
        String endNodeId = args[1];

        try {
            List<String> path = pathFinder.findPathBetween(modelInstance, startNodeId, endNodeId);

            if (path.isEmpty()) {
                log.info("No path found between nodes");
                System.exit(-1);
            }

            System.out.println("The path from " + startNodeId + " to " + endNodeId + " is: [" + String.join(", ", path) + "]");
        } catch (NodeNotFoundException e) {
            log.error("Error retrieving path between nodes", e);
            System.exit(-1);
        }
    }

    private void existsInCaseOfInvalidArguments(String[] args) {
        if (args.length != 2) {
            log.error("Invalid number of arguments. Please specify a start node id and an end node id");
            System.exit(-1);
        }
    }
}
