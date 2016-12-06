package com.example.bigfootone.mobilepoebuddy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DevPostsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DevPostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DevPostsFragment extends Fragment {

    private ListView listView;
    private ArrayList<SingleDevPost> allPosts;
    MenuItem refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dev_posts, container, false);
        listView = (ListView) view.findViewById(R.id.list_view);
        listView.setBackgroundColor(Color.rgb(90,90,90));
        parseXML();

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        devPostArrayAdapter arrayAdapter = new devPostArrayAdapter(getActivity().getApplicationContext(), allPosts);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                //Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                SingleDevPost thisPost = (SingleDevPost) listView.getAdapter().getItem(position);
                Intent intent = new Intent(getContext(), devPostActivity.class);
                intent.putExtra("postClicked", thisPost);
                startActivity(intent);
            }
        });
    }

    public void parseXML()
    {
        ArrayList<SingleDevPost> allDevPosts = new ArrayList<SingleDevPost>();
        String RSSURL = "http://www.gggtracker.com/rss.php";
        AsyncXMLParser asyncXMLParser = new AsyncXMLParser(getContext(), RSSURL);

        try
        {
            allDevPosts = asyncXMLParser.execute("").get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }

        allPosts = allDevPosts;
        allPosts.remove(0);
        for (SingleDevPost singlePost: allPosts)
        {
            singlePost.splitTitle();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        refresh = menu.add("Refresh");
        refresh.setShowAsAction(1);
        refresh.setIcon(android.R.drawable.ic_popup_sync);

        refresh.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                parseXML();
                return true;
            }
        });
    }



}
