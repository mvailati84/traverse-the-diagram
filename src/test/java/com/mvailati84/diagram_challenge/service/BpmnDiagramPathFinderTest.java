package com.mvailati84.diagram_challenge.service;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BpmnDiagramPathFinderTest {

    private BpmnDiagramPathFinder finder;
    private BpmnModelInstance model;

    @BeforeEach
    void setup(){
        InputStream in = this.getClass().getResourceAsStream("/bpmn20.xml");
        model = Bpmn.readModelFromStream(in);

        finder = new BpmnDiagramPathFinder();
    }

    @Test
    @DisplayName("Given 2 nodes with a valid path, then return it with success")
    void givenTwoNodesWithAValidPath_thenReturnThePath() throws NodeNotFoundException {
        List<String> path = finder.findPathBetween(model, "approveInvoice", "invoiceProcessed");

        assertNotNull(path);
        assertEquals("approveInvoice, invoice_approved, prepareBankTransfer, ServiceTask_1, invoiceProcessed", String.join(", ", path));
    }
    @Test
    @DisplayName("Given 2 nodes with a valid path, then return it with success")
    void givenTwoNodesWithNotAValidPath_thenReturnAnEmptyPath() throws NodeNotFoundException {
        List<String> path = finder.findPathBetween(model, "invoiceProcessed", "approveInvoice");

        assertTrue(path.isEmpty());
    }

    @Test
    void givenANotExistingNode_thenThrowException() {
        assertThrows(NodeNotFoundException.class,
                () -> {
                    finder.findPathBetween(model, "notExistingNode", "notExistingNode");
                });
    }



}