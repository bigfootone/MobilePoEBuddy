package com.example.bigfootone.mobilepoebuddy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by David Stuart on 27/11/2016.
 * S1313657
 */

 class devPostArrayAdapter extends ArrayAdapter<SingleDevPost>
{
    public devPostArrayAdapter (Context context, ArrayList<SingleDevPost> singleDevPosts)
    {
        super(context, R.layout.custom_row, singleDevPosts);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        SingleDevPost event = getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_row, parent, false);
        }

        TextView postTitle = (TextView) convertView.findViewById(R.id.titleText);
        postTitle.setText(event.getPostTitle());
        TextView postDev = (TextView) convertView.findViewById(R.id.devText);
        postDev.setText(event.getPostPoster());

        return convertView;
    }
}
