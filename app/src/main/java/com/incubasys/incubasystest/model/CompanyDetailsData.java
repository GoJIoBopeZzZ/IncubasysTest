package com.incubasys.incubasystest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyDetailsData {
    @SerializedName("data")
    @Expose
    private CompanyData companyData;
    @SerializedName("error")
    @Expose
    private Error error;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public CompanyData getData() {
        return companyData;
    }

    public void setData(CompanyData companyData) {
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
