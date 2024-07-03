package com.kinnarastudio.commons.mekarisign.model;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONObject;

public class DocumentListResponse {
    private final GetDocumentListBody data;

    public DocumentListResponse(JSONObject fromJson) throws ParseException {
        JSONArray dataArray = fromJson.getJSONArray("data");
        data = new GetDocumentListBody(dataArray, fromJson.getJSONObject("pagination"));
    }

    public DocumentListResponse(GetDocumentListBody data)
    {
        this.data = data;
    }

    public GetDocumentListBody getData()
    {
        return data;
    }
}
