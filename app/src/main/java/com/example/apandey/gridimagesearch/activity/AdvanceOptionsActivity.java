package com.example.apandey.gridimagesearch.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.apandey.gridimagesearch.R;

public class AdvanceOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_options);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_advance_options, menu);
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

    public void onSave(View v){
            Intent i = new Intent();
            EditText editImageSz = (EditText)findViewById(R.id.evImgSize);
            EditText editImageType = (EditText)findViewById(R.id.evImageType);
            EditText editImageColor = (EditText)findViewById(R.id.evClrFilter);
            EditText editSiteFilter = (EditText)findViewById(R.id.evSiteFilter);
            String imageSize = editImageSz.getText().toString();
            String imageType = editImageType.getText().toString();
            String imageColor = editImageColor.getText().toString();
            String siteFilter = editSiteFilter.getText().toString();

            i.putExtra("size", imageSize);
            i.putExtra("type", imageType);
            i.putExtra("color", imageColor);
            i.putExtra("sitefilter", siteFilter);
            setResult(RESULT_OK, i);
            finish();

    }
}
