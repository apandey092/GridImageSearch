package com.example.apandey.gridimagesearch.models;

import android.content.Intent;

import java.io.Serializable;

/**
 * Created by apandey on 11/1/15.
 */
public class FilterQuery implements Serializable {
    public String size;
    public String type;
    public String color;
    public String siteFilter;

    public FilterQuery(String size, String type, String color, String siteFilter) {
        this.size = size;
        this.type = type;
        this.color = color;
        this.siteFilter = siteFilter;
    }

    public FilterQuery(){

    }

    public static FilterQuery fromData(Intent data){
        FilterQuery filterQuery = new FilterQuery();
        filterQuery.size = data.getStringExtra("size");
        filterQuery.type = data.getStringExtra("type");
        filterQuery.color = data.getStringExtra("color");
        filterQuery.siteFilter = data.getStringExtra("sitefilter");
        return filterQuery;
    }
}
