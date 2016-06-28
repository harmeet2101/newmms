package com.mbopartners.mbomobile.rest.rest.method.volley_impl;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Pair;

import com.android.volley.Response;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ua.com.mobidev.android.mdrest.web.rest.request.AbstractRestRequest;
import ua.com.mobidev.android.mdrest.web.rest.response.UniversalRestResponse;

public class MultipartGsonRequest_2 extends GsonRequest {
    public static final String KEY_PICTURE = "file";

    private final File sFile;
    private HttpEntity mHttpEntity;

    public MultipartGsonRequest_2(AbstractRestRequest universalRestRequest, Response.Listener<UniversalRestResponse> responseListener, Response.ErrorListener errorListener) {
        super(universalRestRequest, responseListener, errorListener);
        this.sFile = (File) universalRestRequest.getRequestBody();
        this.mHttpEntity = buildMultipartEntity(sFile);
    }

    @Override
    public String getBodyContentType() {
        String contentType = mHttpEntity.getContentType().getValue();
        return contentType;
    }

    @Override
    public byte[] getBody() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mHttpEntity.writeTo(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    private HttpEntity buildMultipartEntity(File imageFile) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        String mimeType = imageFile.getName().substring(imageFile.getName().lastIndexOf("."));
        if (mimeType.contains("jpg")){
            mimeType = "image/jpeg";
        }else if (mimeType.contains("png")){
            mimeType = "image/png";
        }
        String urlEncodedFileName = "";
        try {
            urlEncodedFileName = URLEncoder.encode(imageFile.getName(), "UTF-8");
        }catch (UnsupportedEncodingException uee){
            uee.printStackTrace();
        }
        builder.addBinaryBody("file",imageFile,ContentType.create(mimeType),urlEncodedFileName);
        return builder.build();
    }
}
