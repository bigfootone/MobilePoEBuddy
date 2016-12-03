package com.example.bigfootone.mobilepoebuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Bigfootone on 01/12/2016.
 */

public class UniqueItemExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> headers;
    private HashMap<String, List<String>> childData;

    public UniqueItemExpandableListAdapter(Context context, List<String> allHeaders, HashMap<String, List<String>> childData)
    {
        this.context = context;
        this.headers = allHeaders;
        this.childData = childData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return this.childData.get(this.headers.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int position, int childPosition)
    {
        return childPosition;
    }

    @Override
    public View getChildView(int position, final int childPosition, boolean isLastItem, View view, ViewGroup viewGroup)
    {
        final String childText = (String) getChild(position, childPosition);

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_single_child_row, null);
        }

        TextView childList = (TextView) view.findViewById(R.id.uniqueSingleChildRow);
        childList.setText(childText);
        return view;
    }

    @Override
    public int getChildrenCount(int position)
    {
        return this.childData.get(this.headers.get(position)).size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return this.headers.get(groupPosition);
    }

    @Override
    public int getGroupCount()
    {
        return this.headers.size();
    }

    @Override
    public long getGroupId(int position)
    {
        return position;
    }

    @Override
    public View getGroupView(int position, boolean isExpanded, View view, ViewGroup parent)
    {
        String headerTitle = (String) getGroup(position);
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_single_row, null);
        }

        TextView header = (TextView) view.findViewById(R.id.uniqueSingleRow);
        header.setText(headerTitle);
        return view;
    }


    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public boolean isChildSelectable(int position, int childPositiong)
    {
        return true;
    }
}
