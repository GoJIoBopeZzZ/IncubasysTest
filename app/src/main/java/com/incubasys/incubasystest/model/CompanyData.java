package com.incubasys.incubasystest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("whenCreated")
    @Expose
    private Long whenCreated;
    @SerializedName("whenUpdated")
    @Expose
    private Long whenUpdated;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("description")
    @Expose
    private Object description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Long whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Long getWhenUpdated() {
        return whenUpdated;
    }

    public void setWhenUpdated(Long whenUpdated) {
        this.whenUpdated = whenUpdated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }
}
