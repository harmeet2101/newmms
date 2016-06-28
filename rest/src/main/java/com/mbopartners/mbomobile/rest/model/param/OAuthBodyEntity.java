package com.mbopartners.mbomobile.rest.model.param;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OAuthBodyEntity implements Serializable {
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;
    @SerializedName("grant_type")
    public String grantType;
    @SerializedName("refresh_token")
    public String refreshToken;
}
