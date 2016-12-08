package com.example.bigfootone.mobilepoebuddy;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


/**
 * Created by David Stuart
 * S1313657
 */
public class UniqueItemFragment extends Fragment {

    SingleUniqueItem singleUniqueItemInfo;
    Integer endID;
    private ArrayList<String> currentCategories = new ArrayList<>();
    UniqueItemExpandableListAdapter expandableAdapter;
    ExpandableListView expandableList;
    HashMap<String, List<String>> childData;
    MenuItem clearAll;

    public UniqueItemFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_unique_item, container, false);
        setHasOptionsMenu(true);

        UniqueItemDatabaseManager databaseManager = new UniqueItemDatabaseManager(getContext(), "UniqueItemDB.s3db", null, 1);
        endID = databaseManager.getNumberOfRows("ID") + 3;

        singleUniqueItemInfo = new SingleUniqueItem();

        //create a new hasmap for the expandable list
        childData = new HashMap<>();

        for(int i = 3; i < endID; i++)
        {
            singleUniqueItemInfo = databaseManager.findItem(Integer.toString(i));
            String itemCategory = singleUniqueItemInfo.getItemCategory();
            String itemSubCategory = singleUniqueItemInfo.getItemSubCategory();

            //for every item in the database, check if the current category is added as a key to the hashmap
            List<String> list;
            if(childData.containsKey(itemCategory))
            {
                list = childData.get(itemCategory);
                //if the subcategory does not exist for that key, add it
                if(!list.contains(itemSubCategory))
                {
                    list.add(itemSubCategory);
                }
            }
            //if the category does not exist, add the category as a key and the subcategory as a value
            else
            {
                list = new ArrayList<>();
                list.add(itemSubCategory);
                childData.put(itemCategory, list);
                currentCategories.add(itemCategory);
            }
        }

        //logging categories to subcategories
        for (Map.Entry<String, List<String>> entry : childData.entrySet()) {
            String Category = entry.getKey();
            for(String Subcategory : entry.getValue()) {
                Log.e("Test", "Category (" + Category + ") has (" + Subcategory + ")");
            }
        }

        expandableList = (ExpandableListView) view.findViewById(R.id.UniqueExpandableListView2);
        expandableAdapter = new UniqueItemExpandableListAdapter(getActivity().getApplicationContext(), currentCategories, childData);
        expandableList.setBackgroundColor(Color.rgb(90,90,90));
        expandableList.setAdapter(expandableAdapter);

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        //if a value in the hashmap is clicked, open an activiy and pass in that subcategory
        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                String currentSubCategory = (String) expandableAdapter.getChild(groupPosition, childPosition);
                Intent intent = new Intent(getContext(), SearchedDatabasedActivity.class);
                intent.putExtra("subCategory", currentSubCategory);
                intent.putExtra("NumberOfRows", endID);
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        clearAll = menu.add("Clear Favourites");

        clearAll.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                clearFavourites();
                Toast.makeText(getContext(), "Favourites cleared", Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    public void clearFavourites()
    {
        UniqueItemDatabaseManager databaseManager = new UniqueItemDatabaseManager(getContext(), "UniqueItemDB.s3db", null, 1);
        endID = databaseManager.getNumberOfRows("ID") + 3;

        for(int i = 3; i < endID; i++)
        {
            databaseManager.clearFavourites(i);
        }
    }



}
