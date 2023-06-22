package com.mvailati84.diagram_challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BpmnModelResponse (String id, String bpmn20Xml){}
