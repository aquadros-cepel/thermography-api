package com.tech.thermography.web.rest.dto;

public class ValidationIssueDTO {

    public String id;
    public String fileId;
    public String rowId;
    public int rowIndex;
    public String field;
    public String message;
    public String severity;
    public boolean resolved;
}