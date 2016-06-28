package com.mbopartners.mbomobile.rest.model.response.oauth;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OAuthApiException  implements Serializable {
    @SerializedName("error")
    private String error;
    @SerializedName("error_description")
    private String error_description;
}