package com.mvailati84.diagram_challenge.service;

import com.mvailati84.diagram_challenge.model.BpmnModelResponse;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Service
public class BpmnFetcher {

    final RestTemplate restTemplate;

    public BpmnFetcher(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BpmnModelInstance fetchRemotely(String url) {
        ResponseEntity<BpmnModelResponse> response = restTemplate.getForEntity(url, BpmnModelResponse.class);

        String modelXml = response.getBody().bpmn20Xml();

        return Bpmn.readModelFromStream(new ByteArrayInputStream(modelXml.getBytes(StandardCharsets.UTF_8)));
    }
}
