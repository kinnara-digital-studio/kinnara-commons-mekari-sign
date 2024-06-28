package com.kinnarastudio.commons.mekarisign.model;

public class GetDocumentListsResponse {
    private final SignResponseAttributes[] signResponseAttributes;
    private final int currentPage;
    private final int previous;
    private final int next;
    private final int perPage;
    private final int totalPage;
    private final int documentCount;

    public GetDocumentListsResponse (SignResponseAttributes[] signResponseAttributes, int currentPage, int previous, int next, int perPage, int totalPage, int documentCount)
    {
        this.signResponseAttributes = signResponseAttributes;
        this.currentPage = currentPage;
        this.previous = previous;
        this.next = next;
        this.perPage = perPage;
        this.totalPage = totalPage;
        this.documentCount = documentCount;
    }
}
