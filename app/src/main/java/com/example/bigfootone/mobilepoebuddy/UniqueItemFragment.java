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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UniqueItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UniqueItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UniqueItemFragment extends Fragment {

    SingleUniqueItem singleUniqueItemInfo;
    Integer endID;
    private ListView listView;
    private ArrayList<String> currentCategories = new ArrayList<>();
    private ArrayList<String> currentSubCategories = new ArrayList<>();
    private UniqueItemArrayAdapter arrayAdapter;
    private String [][] categoryInfo = new String[2][50];
    private int category = 0;
    private int subCategory = 1;

    UniqueItemExpandableListAdapter expandableAdapter;
    ExpandableListView expandableList;
    HashMap<String, List<String>> childData;
    List<String> headers;
    MenuItem clearAll;

    public UniqueItemFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_unique_item, container, false);
        //listView = (ListView) view.findViewById(R.id.uniqueItemListView);

        UniqueItemDatabaseManager databaseManager = new UniqueItemDatabaseManager(getContext(), "UniqueItemDB.s3db", null, 1);
        endID = databaseManager.getNumberOfRows("ID") + 3;

        singleUniqueItemInfo = new SingleUniqueItem();

        setHasOptionsMenu(true);

        childData = new HashMap<>();

        for(int i = 3; i < endID; i++)
        {
            singleUniqueItemInfo = databaseManager.findItem(Integer.toString(i));
            String itemCategory = singleUniqueItemInfo.getItemCategory();
            String itemSubCategory = singleUniqueItemInfo.getItemSubCategory();

            List<String> list;
            if(childData.containsKey(itemCategory))
            {
                list = childData.get(itemCategory);
                if(!list.contains(itemSubCategory))
                {
                    list.add(itemSubCategory);
                }
            }
            else
            {
                list = new ArrayList<>();
                list.add(itemSubCategory);
                childData.put(itemCategory, list);
                currentCategories.add(itemCategory);
            }
        }

        for (Map.Entry<String, List<String>> entry : childData.entrySet()) {
            String Category = entry.getKey();
            for(String Subcategory : entry.getValue()) {
                Log.e("Testy", "Category (" + Category + ") has (" + Subcategory + ")");
            }
        }

        expandableList = (ExpandableListView) view.findViewById(R.id.UniqueExpandableListView2);
        expandableAdapter = new UniqueItemExpandableListAdapter(getActivity().getApplicationContext(), currentCategories, childData);
        expandableList.setBackgroundColor(Color.rgb(90,90,90));
        expandableList.setAdapter(expandableAdapter);

        //arrayAdapter = new UniqueItemArrayAdapter(getActivity().getApplicationContext(), currentCategories);
        //listView.setAdapter(arrayAdapter);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

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
