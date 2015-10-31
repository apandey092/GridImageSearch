package com.example.apandey.gridimagesearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apandey.gridimagesearch.R;
import com.example.apandey.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by apandey on 10/29/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {
    public ImageResultsAdapter(Context context,  List<ImageResult> images) {
        super(context, android.R.layout.simple_list_item_1, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageResult imageResult = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }
        ImageView imageView = (ImageView)convertView.findViewById(R.id.ivImage);
        TextView txtView = (TextView)convertView.findViewById(R.id.tvTitle);

        imageView.setImageResource(0);
        txtView.setText(imageResult.title);
        Picasso.with(getContext()).load(imageResult.thumbUrl).into(imageView);
        return convertView;
    }
}
