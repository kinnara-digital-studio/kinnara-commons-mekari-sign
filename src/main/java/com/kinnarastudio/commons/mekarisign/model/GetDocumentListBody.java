package com.kinnarastudio.commons.mekarisign.model;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kinnarastudio.commons.Try;
import com.kinnarastudio.commons.jsonstream.JSONStream;

public class GetDocumentListBody {
    private final ResponseData[] respData;
    private final DocumentListsPagination docListPagination;

    public GetDocumentListBody (ResponseData[] respData, DocumentListsPagination docListPagination)
    {
        this.respData = respData;
        this.docListPagination = docListPagination;
    }

    public GetDocumentListBody(JSONArray jsonArray, JSONObject jsonPagination) throws ParseException
    {
        respData = new ResponseData[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject data = jsonArray.getJSONObject(i);
            ResponseData resp = new ResponseData(data);
            respData[i] = resp;
        }

        docListPagination = new DocumentListsPagination(jsonPagination);
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
