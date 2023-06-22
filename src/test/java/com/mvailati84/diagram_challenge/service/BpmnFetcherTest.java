package com.mvailati84.diagram_challenge.service;

import com.mvailati84.diagram_challenge.MainRunner;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BpmnFetcherTest {

    public static final String BPMN_MODEL_URL = "https://n35ro2ic4d.execute-api.eu-central-1.amazonaws.com/prod/engine-rest/process-definition/key/invoice/xml";
    @Autowired
    private BpmnFetcher fetcher;

    @MockBean
    MainRunner runner;

    @Test
    @DisplayName("Given a valid url then the model is retrived with success")
    void givenAValidUrl_thenModelRetrievied(){
        BpmnModelInstance model = fetcher.fetchRemotely(BPMN_MODEL_URL);
        assertNotNull(model);

        FlowNode startNode = model.getModelElementById("StartEvent_1");
        assertEquals("StartEvent_1", startNode.getId());
    }

}