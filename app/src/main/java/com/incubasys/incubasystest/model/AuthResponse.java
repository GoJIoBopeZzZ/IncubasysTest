package com.incubasys.incubasystest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AuthResponse {
    @SerializedName("data")
    @Expose
    private Data data;

    @SerializedName("error")
    @Expose
    private Error error;

    @SerializedName("success")
    @Expose
    private Boolean success;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
