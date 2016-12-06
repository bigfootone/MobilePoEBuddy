package com.example.bigfootone.mobilepoebuddy;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchedDatabasedActivity extends AppCompatActivity {

    private ArrayList<String> currentUniques = new ArrayList<>();
    private UniqueItemArrayAdapter arrayAdapter;
    private ListView listView;
    private Integer endID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_databased);
        listView = (ListView) findViewById(R.id.searchedListView);
        listView.setBackgroundColor(Color.rgb(90,90,90));

        Intent intent = getIntent();
        String subCategory = (String) intent.getSerializableExtra("subCategory");
        endID = (Integer) intent.getSerializableExtra("NumberOfRows");


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(subCategory);
        actionBar.setDisplayHomeAsUpEnabled(true);

        UniqueItemDatabaseManager databaseManager = new UniqueItemDatabaseManager(getApplicationContext(), "UniqueItemDB.s3db", null, 1);
        SingleUniqueItem singleUniqueItem;

        for(int i = 3; i < endID; i++)
        {
            singleUniqueItem = databaseManager.findItem(Integer.toString(i));
            String itemSubCategory = singleUniqueItem.getItemSubCategory();
            String uniqueName = singleUniqueItem.getItemUniqueName();

            if (!currentUniques.contains(uniqueName) && itemSubCategory.equals(subCategory))
            {
                currentUniques.add(uniqueName);
            }
        }

        arrayAdapter = new UniqueItemArrayAdapter(getApplicationContext(), currentUniques);
        listView.setAdapter(arrayAdapter);

    }

    @Override
    public void onStart()
    {
        super.onStart();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String uniqueName = (String) listView.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(), SingleUniqueItemDisplay.class);
                intent.putExtra("name", uniqueName);
                intent.putExtra("NumberOfRows", endID);
                startActivity(intent);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
        return true;
    }

}
