package com.example.bigfootone.mobilepoebuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by David Stuart on 01/12/2016.
 * S1313657
 */

public class UniqueItemArrayAdapter extends ArrayAdapter<String>
{

    public UniqueItemArrayAdapter(Context context, ArrayList<String> singleUniqueItem)
    {
        super(context, R.layout.custom_single_row, singleUniqueItem);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        String event = getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_single_row, parent, false);
        }

        TextView category = (TextView) convertView.findViewById(R.id.uniqueSingleRow);
        category.setText(event);
        return convertView;
    }

}
