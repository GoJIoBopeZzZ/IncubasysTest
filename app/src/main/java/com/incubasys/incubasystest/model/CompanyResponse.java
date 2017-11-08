package com.incubasys.incubasystest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CompanyResponse {
    @SerializedName("data")
    @Expose
    private List<CompanyData> companyData = new ArrayList<>();
    @SerializedName("error")
    @Expose
    private Error error;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public List<CompanyData> getData() {
        return companyData;
    }

    public void setData(List<CompanyData> companyData) {
        this.companyData = companyData;
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
