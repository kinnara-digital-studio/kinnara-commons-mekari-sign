package com.kinnarastudio.commons.mekarisign.model;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kinnarastudio.commons.Try;
import com.kinnarastudio.commons.jsonstream.JSONStream;

public class DocumentListsPagination {
    private final int currentPage;
    private final int previous;
    private final int next;
    private final int perPage;
    private final int totalPage;
    private final int documentCount;

    public DocumentListsPagination (int currentPage, int previous, int next, int perPage, int totalPage, int documentCount)
    {
        this.currentPage = currentPage;
        this.previous = previous;
        this.next = next;
        this.perPage = perPage;
        this.totalPage = totalPage;
        this.documentCount = documentCount;
    }

    public DocumentListsPagination(JSONObject jsonBody) throws ParseException
    {
        currentPage = jsonBody.getInt("current_page");
        previous = jsonBody.optInt("previous");
        next = jsonBody.optInt("next");
        perPage = jsonBody.getInt("per_page");
        totalPage = jsonBody.getInt("total_pages");
        documentCount = jsonBody.getInt("count");
    }

    public int getCurrentPage()
    {
        return currentPage;
    }

    public int getPrevious()
    {
        return previous;
    }

    public int getNext()
    {
        return next;
    }

    public int getPerPage()
    {
        return perPage;
    }

    public int getTotalPage()
    {
        return totalPage;
    }
    public int getDocumentCount()
    {
        return documentCount;
    }
}
