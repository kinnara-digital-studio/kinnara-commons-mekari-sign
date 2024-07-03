package com.kinnarastudio.commons.mekarisign.model;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kinnarastudio.commons.Try;
import com.kinnarastudio.commons.jsonstream.JSONStream;

public class GetDocumentListsResponse {
    private final ResponseData[] respData;
    private final DocumentListsPagination docListPagination;

    public GetDocumentListsResponse (ResponseData[] respData, DocumentListsPagination docListPagination)
    {
        this.respData = respData;
        this.docListPagination = docListPagination;
    }

    public GetDocumentListsResponse(JSONObject jsonBody) throws ParseException
    {
        respData = JSONStream.of(jsonBody.getJSONArray("data"), JSONArray::getJSONObject)
                .map(Try.onFunction(ResponseData::new))
                .toArray(ResponseData[]::new);
                
        docListPagination = new DocumentListsPagination(jsonBody.getJSONObject("pagination"));
    }

    public ResponseData[] getRespData()
    {
        return respData;
    }

    public DocumentListsPagination getDocListPagination()
    {
        return docListPagination;
    }
}
