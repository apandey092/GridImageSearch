package com.example.apandey.gridimagesearch.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.example.apandey.gridimagesearch.R;
import com.example.apandey.gridimagesearch.adapters.ImageResultsAdapter;
import com.example.apandey.gridimagesearch.models.FilterQuery;
import com.example.apandey.gridimagesearch.models.ImageResult;
import com.example.apandey.gridimagesearch.utils.EndlessScrollListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    public static int REQUEST_CODE = 10;
    private int start = 0;
    private String searchKey;
    private String GOOGLE_IMAGE_ENDPOINT = "https://ajax.googleapis.com/ajax/services/search/images";
    private FilterQuery filterQuery;

    EditText etQuery;
    //    Button btSearch;
    GridView gvResults;
    ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setUpViews();
        filterQuery = new FilterQuery();
        imageResults = new ArrayList<>();
        aImageResults = new ImageResultsAdapter(this, imageResults);
        gvResults.setAdapter(aImageResults);

    }

    private void setUpViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
                ImageResult imageResult = imageResults.get(position);
                i.putExtra("result", imageResult);
                startActivity(i);
            }
        });
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                loadMore(searchKey, page-1);
                return true;
            }
        });
    }

    public void onComposeAction(MenuItem mi) {
        Intent i = new Intent(this, AdvanceOptionsActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            filterQuery = FilterQuery.fromData(data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onImageSearch(View v) {
        String query = etQuery.getText().toString();
        searchKey = query;
        loadMore(searchKey, 0);
    }

    private void loadMore(String query , int start){
        String searchUrl = GOOGLE_IMAGE_ENDPOINT + "?v=1.0&q=" + query + "&rsz=8&start="+start;
        searchUrl = getSearchUrlWithFilters(searchUrl);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                try {
                    JSONArray imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    aImageResults.addAll(ImageResult.fromJsonArray(imageResultsJson));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("INFO", imageResults.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
    }

    private String getSearchUrlWithFilters(String searchUrl) {
        if(filterQuery.size != null && !filterQuery.size.isEmpty()){
            searchUrl += "&imgsz="+filterQuery.size;
        }
        if(filterQuery.type != null && !filterQuery.type.isEmpty()){
            searchUrl += "&imgtype="+filterQuery.type;
        }
        if(filterQuery.siteFilter != null && !filterQuery.siteFilter.isEmpty()){
            searchUrl += "&as_sitesearch="+filterQuery.siteFilter;
        }
        if(filterQuery.color != null && !filterQuery.color.isEmpty()){
            searchUrl += "&imgc="+filterQuery.color;
        }
        return searchUrl;
    }
}
