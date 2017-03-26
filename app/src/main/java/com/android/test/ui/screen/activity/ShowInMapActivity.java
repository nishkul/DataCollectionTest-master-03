package com.android.test.ui.screen.activity;

import android.app.Dialog;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.test.R;
import com.android.test.model.Entry;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by root on 26/3/17.
 */

public class ShowInMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    ArrayList<Entry> entry;
    GoogleMap mGoogleMap;
    GoogleApiClient googleApiClient;
    LocationRequest mLocationRequest;
    private FloatingActionButton fab;
    private EditText mSearchEdt;
    private Button mZoomOut;
    private Button mZoomIN;
    private Button mGoBtn;
    private MarkerOptions markerOptions;
    private Marker marker;

    public boolean googleServicesAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int isAvailable = apiAvailability.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (apiAvailability.isUserResolvableError(isAvailable)) {
            Dialog dialog = apiAvailability.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "unable to connect play servies", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entry = (ArrayList<Entry>) getIntent().getSerializableExtra("EnteryListData");
        if (googleServicesAvailable()) {

            setContentView(R.layout.map_layout);
            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

//            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                gpsDisableDialog();
//            }
            initView();

            listener();

            for (int i = 0; i < entry.size(); i++) {
                Log.v("qqqqqqqqq", entry.get(i).getTitle() + " " + entry.get(i).getDescription());
            }

        } else {
            Toast.makeText(this, "unable to find play servies", Toast.LENGTH_SHORT).show();
        }

    }


    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.topPanel);
        linearLayout.setVisibility(View.GONE);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void listener() {
    }

    private void drawMarker(LatLng point) {
        // Creating an instance of MarkerOptions

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Enabling MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);


        // If locations are already saved
        if (entry != null) {

            String lat = "";
            String lng = "";

            // Iterating through all the locations stored
            for (int i = 0; i < entry.size(); i++) {

                // Getting the latitude of the i-th location
                if (entry.get(i).getLat() != null)
                    lat = entry.get(i).getLat();

                if (entry.get(i).getLang() != null)
                    lng = entry.get(i).getLang() + i;
                // Getting the longitude of the i-th location

                // Drawing marker on the map
                //  drawMarker());

                if (lat != null && lng != null) {
                    MarkerOptions markerOptions = new MarkerOptions();

                    // Setting latitude and longitude for the marker
                    markerOptions.position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
                    // Adding marker on the Google Map
                    googleMap.addMarker(markerOptions);
                }

            }

            // Moving CameraPosition to last clicked position
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)), 5);
            googleMap.moveCamera(cameraUpdate);
            // Setting the zoom level in the map on last position  is clicked
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(5f));
        }
    }


}
