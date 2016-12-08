package com.example.bigfootone.mobilepoebuddy;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


/**
 * Created by Bigfootone on 05/12/2016.
 */

public class MapActivity extends Fragment implements OnMapReadyCallback, LocationListener{

    Marker mapMarker;
    private GoogleMap googleMap;
    private MapView mapView;
    private static final int REQUEST_LOCATION = 5;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private GoogleApiClient apiClient;
    private Marker homeMarker;
    private SharedPreferences preferences;
    private SaveHomeLocation homeLocation;
    private LatLng homeLatlng;
    private LatLng GGGHub = new LatLng(-36.848461, 174.763336);
    private Polyline line;

    //some of the features did not work, left in as emulator wouldnt run maps and couldnt check to see what would work without android device
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_layout, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        apiClient = new GoogleApiClient.Builder(getContext()).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks()
                {
                    @Override
                    public void onConnected(@Nullable Bundle bundle)
                    {
                        //set the refresh rate for checking location, mix and max
                        locationRequest = new LocationRequest();
                        locationRequest.setInterval(1000);
                        locationRequest.setFastestInterval(100);
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    }

                    @Override
                    public void onConnectionSuspended(int i)
                    {

                    }
                })
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
}

    @Override
    public void onMapReady(final GoogleMap gMap)
    {
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            gMap.setMyLocationEnabled(true);
        }
        else
        {
            //if we dont have permission, request it
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("Please allow access to your location to use this feature");
            alert.setTitle("Permission needed");
            Log.e("Mapping", "you made it");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                }
            });
            alert.create();
            alert.show();
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        homeLocation = new SaveHomeLocation(preferences);

        //once the map has been clicked, ask if the user wants to change their home location
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng)
            {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage("Do you wish to change your home location?");
                alert.setTitle("Change Home Location");
                Log.e("Mapping", "you made it");
                //if the user clicks yes, remove the old marker.
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(homeMarker != null)
                        {
                            homeMarker.remove();
                        }
                        //add the latitude and longitude of the clicked location to the preferences
                        saveData(latLng, gMap);
                        //calculate distance between set location and the clicked point
                        float distance[] = new float[1];
                        Location.distanceBetween(GGGHub.latitude, GGGHub.longitude, homeLatlng.latitude, homeLatlng.longitude, distance);
                        float moreDistance = distance[0] / 1000;
                        Toast.makeText(getContext(), "Your location is " + Float.toString(moreDistance) + " kimometres away from GGG", Toast.LENGTH_LONG).show();

                    }
                });
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
                alert.create();
                alert.show();

            }
        });

        //once the map has loaded up, grab the home location from the preferences and set it as a marker on the map.
        String latHome = preferences.getString("lat", "00.00");
        String longHome = preferences.getString("long", "00.00");
        Log.e("location", latHome);
        double latHomeReal = Double.valueOf(latHome);
        double longHomeReal = Double.valueOf(longHome);
        LatLng latLng = new LatLng(latHomeReal, longHomeReal);
        homeMarker = gMap.addMarker(new MarkerOptions().position(latLng));
        homeLatlng = latLng;
        drawLine(gMap);
    }

    @Override
    public void onLocationChanged(Location location)
    {
        //if the user moves loation, update their location
        lastLocation = location;
        if(mapMarker != null)
        {
            mapMarker.remove();
        }
        double latitude = lastLocation.getLatitude();
        double longitude = lastLocation.getLongitude();
        mapMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
        mapMarker.setTitle("You are Here");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

    @Override
    public void onProviderDisabled(String provider)
    {

    }

    @Override
    public void onProviderEnabled(String provider)
    {

    }

    //draw a line from the home location and the PoE offices in New Zealand
    public void drawLine(GoogleMap map)
    {
        if(line != null)
        {
            line.remove();
        }
        line = map.addPolyline(new PolylineOptions().add(homeLatlng, GGGHub).width(5).color(Color.BLACK));
    }

    //save the home location to the user preferences
    public void saveData(LatLng latLng, GoogleMap gMap)
    {
        final String latitude = String.valueOf(latLng.latitude);
        final String longitude = String.valueOf(latLng.longitude);
        homeMarker = gMap.addMarker(new MarkerOptions().position(latLng));
        homeLocation.savePreferences("lat", latitude);
        homeLocation.savePreferences("long", longitude);
        homeLatlng = new LatLng(latLng.latitude, latLng.longitude);
        drawLine(gMap);
    }

}
