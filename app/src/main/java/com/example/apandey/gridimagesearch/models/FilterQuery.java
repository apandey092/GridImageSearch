package com.example.apandey.gridimagesearch.models;

import android.content.Intent;

/**
 * Created by apandey on 11/1/15.
 */
public class FilterQuery {
    public String size;
    public String type;
    public String color;
    public String siteFilter;

    public static FilterQuery fromData(Intent data){
        FilterQuery filterQuery = new FilterQuery();
        filterQuery.size = data.getStringExtra("size");
        filterQuery.type = data.getStringExtra("type");
        filterQuery.color = data.getStringExtra("color");
        filterQuery.siteFilter = data.getStringExtra("sitefilter");
        return filterQuery;
    }
}
