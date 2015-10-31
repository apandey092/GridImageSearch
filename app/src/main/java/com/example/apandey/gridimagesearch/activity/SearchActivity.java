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
    private String size;
    private String type;
    private String color;
    private String siteFilter;
    private int start = 0;

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

    }

    private void setUpViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        imageResults = new ArrayList<>();
        aImageResults = new ImageResultsAdapter(this, imageResults);
        gvResults.setAdapter(aImageResults);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
                ImageResult imageResult = imageResults.get(position);
                i.putExtra("result", imageResult);
                startActivity(i);
            }
        });
        gvResults.setOnScrollListener(new EndlessScrollListener(8) {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                loadMore(page);
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
//            i.putExtra("size", imageSize);
//            i.putExtra("type", imageType);
//            i.putExtra("color", imageColor);
//            i.putExtra("sitefilter", siteFilter);
            size = data.getStringExtra("size");
            type = data.getStringExtra("type");
            color = data.getStringExtra("color");
            siteFilter = data.getStringExtra("sitefilter");
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
       loadMore(0);
    }

    private void loadMore(int start){
        String query = etQuery.getText().toString();
//        Toast.makeText(this, "Search for "+query, Toast.LENGTH_SHORT).show();
        String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8&start="+start;
//        https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=fb&rsz=8&imgc=blue&imgsz=icon&imgtype=face&as_sitesearch=facebook.com
        if(size != null && !size.isEmpty()){
            searchUrl += "&imgsz="+size;
        }
        if(type != null && !type.isEmpty()){
            searchUrl += "&imgtype="+type;
        }
        if(siteFilter != null && !siteFilter.isEmpty()){
            searchUrl += "&as_sitesearch="+siteFilter;
        }
        if(color != null && !color.isEmpty()){
            searchUrl += "&imgc="+color;
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                try {
                    JSONArray imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear();
                    aImageResults.addAll(ImageResult.fromJsonArray(imageResultsJson));
//                    aImageResults.notifyDataSetChanged();
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
}
