package com.mvailati84.diagram_challenge;

import com.mvailati84.diagram_challenge.service.ApplicationShutdown;
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
    final ApplicationShutdown applicationShutdown;

    public MainRunner(BpmnFetcher fetcher, BpmnDiagramPathFinder pathFinder, ApplicationShutdown applicationShoutdown) {
        this.fetcher = fetcher;
        this.pathFinder = pathFinder;
        this.applicationShutdown = applicationShoutdown;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Start diagram path analyzer ...");

        existsInCaseOfInvalidArguments(args);

        BpmnModelInstance modelInstance = fetcher.fetchRemotely(BPMN_MODEL_URL);
        log.info("Bpmn model retrieved with success");

        String startNodeId = args[1];
        String endNodeId = args[2];

        try {
            List<String> path = pathFinder.findPathBetween(modelInstance, startNodeId, endNodeId);

            if (path.isEmpty()) {
                log.info("No path found between nodes");
                applicationShutdown.shutdown(-1);
            }

            System.out.println("The path from " + startNodeId + " to " + endNodeId + " is: [" + String.join(", ", path) + "]");
        } catch (NodeNotFoundException e) {
            log.error("Error retrieving path between nodes", e);
            applicationShutdown.shutdown(-1);
        }
    }

    private void existsInCaseOfInvalidArguments(String[] args) {
        if (args.length != 3) {
            log.error("Invalid number of arguments. Please specify a start node id and an end node id");
            applicationShutdown.shutdown(-1);
        }
    }
}
