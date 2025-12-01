package com.bajaj.test.dto;

public class SubmissionRequest {
    private String finalQuery;

    // Empty Constructor
    public SubmissionRequest() {}

    // Constructor with arguments
    public SubmissionRequest(String finalQuery) {
        this.finalQuery = finalQuery;
    }

    // Getters and Setters
    public String getFinalQuery() { return finalQuery; }
    public void setFinalQuery(String finalQuery) { this.finalQuery = finalQuery; }
}