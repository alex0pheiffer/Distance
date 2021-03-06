package com.example.distance;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.distance.db_things.Reminder_dbObj;
import com.example.distance.db_things.distanceViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class holderForMap extends AppCompatActivity implements OnMapReadyCallback {

    public static final String EXTRA_REPLY_REMOVE = "REPLY_REMOVE";

    private distanceViewModel viewModel;
    private GoogleMap mMap;
    private List<Reminder_dbObj> lReminders;

    private final double DENEVELAT = 34.070743;
    private final double DENEVELON = -118.450159;

    private Reminder_dbObj mReminder;
    private TextView titleTextView;
    private TextView locationTextView;
    private FloatingActionButton remove_fab;
    private FloatingActionButton save_fab;
    private static final float DEFAULT_ZOOM = 15f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent mIntent = getIntent();
        final int reminderID = mIntent.getIntExtra("reminderID", 0);

        titleTextView = findViewById(R.id.reminder_map);
        locationTextView = findViewById(R.id.location_map);
        remove_fab = findViewById(R.id.fab_remove);
        save_fab = findViewById(R.id.fab_save);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        lReminders = new ArrayList<Reminder_dbObj>();
        viewModel = new ViewModelProvider(this).get(distanceViewModel.class);
        viewModel.getlReminders().observe(this, new Observer<List<Reminder_dbObj>>() {
            @Override
            public void onChanged(@Nullable final List<Reminder_dbObj> vals) {
                for (Reminder_dbObj n : vals) {
                    lReminders.add(n);
                }
                //set this activity's reminder and the cooresponding views
                System.out.println("ACT CREATED: id = "+reminderID);
                mReminder = lReminders.get(reminderID);
                titleTextView.setText(mReminder.getLabel());
                locationTextView.setText(mReminder.getLocation());
                // Add a marker in Sydney and move the camera
                if (mReminder != null) {
                    System.out.println("LAT: "+mReminder.getLat()+" LON: "+mReminder.getLon());
                    LatLng sydney = new LatLng(mReminder.getLat(), mReminder.getLon());
                    mMap.addMarker(new MarkerOptions().position(sydney).title(mReminder.getLocation()));
                    if (mReminder.getDistance() < 5000) moveCamera(sydney, DEFAULT_ZOOM, mReminder.getLocation());
                    else mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                }
            }
        });


        save_fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                System.out.println("FAB PRESSED: return -1");
                replyIntent.putExtra(EXTRA_REPLY_REMOVE, -1);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
        remove_fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                System.out.println("FAB PRESSED: return "+reminderID);
                replyIntent.putExtra(EXTRA_REPLY_REMOVE, reminderID);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d("TAG", "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        if (mReminder != null) {
            LatLng sydney = new LatLng(mReminder.getLat(), mReminder.getLon());
            mMap.addMarker(new MarkerOptions().position(sydney).title(mReminder.getLocation()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
        else {
            LatLng sydney = new LatLng(DENEVELAT, DENEVELON);
            mMap.addMarker(new MarkerOptions().position(sydney).title("DeNeve"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }
}
