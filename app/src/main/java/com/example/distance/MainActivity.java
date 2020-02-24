package com.example.distance;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.distance.db_things.Reminder_dbObj;
import com.example.distance.db_things.distanceViewModel;
import com.example.distance.db_things.reminderAdapter;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements reminderAdapter.reminderAdapterListener {

    //device location things
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location myLocation;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    private final double DENEVELAT = 34.070743;
    private final double DENEVELON = -118.450159;


    private distanceViewModel viewModel;
    public static final int NEW_REMINDER_ACTIVITY_REQUEST_CODE = 1;
    public static final int VIEW_REMIDER_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //location things...
        getLocationPermission();
        getDeviceLocation();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final reminderAdapter adapter = new reminderAdapter(this, this, DENEVELAT, DENEVELON);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(distanceViewModel.class);


        viewModel.getlReminders().observe(this, new Observer<List<Reminder_dbObj>>() {
            @Override
            public void onChanged(@Nullable final List<Reminder_dbObj> vals) {
                // Update the cached copy of the words in the adapter.
                adapter.setlData(vals);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, newItemActivity.class);
                startActivityForResult(intent, NEW_REMINDER_ACTIVITY_REQUEST_CODE);
            }
        });
    }


    //
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("ACTGENERALCLOSED");
        if(requestCode == NEW_REMINDER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            double templat = data.getIntExtra(newItemActivity.EXTRA_REPLY_LAT,0);
            double templon = data.getIntExtra(newItemActivity.EXTRA_REPLY_LAT,0);

            Reminder_dbObj reminder = new Reminder_dbObj(data.getStringExtra(newItemActivity.EXTRA_REPLY_LABEL),data.getStringExtra(newItemActivity.EXTRA_REPLY_LOCATION), templat, templon, getDistance(templat, templon));
            System.out.println("new reminder created: "+reminder.getLabel());
            viewModel.insert(reminder);
            System.out.println("reminder added successfully!");
        }
        else if (requestCode == VIEW_REMIDER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int remove = data.getIntExtra(holderForMap.EXTRA_REPLY_REMOVE,-1);
            System.out.println("ACT CLOSED: id = "+remove);
            if (remove != -1) {
                viewModel.deleteReminder(viewModel.getlReminders().getValue().get(remove));
            }
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_valid,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void reminderPressed(int id) {
        System.out.println("SENDING REMINDERID: "+id);
        Intent intent = new Intent(MainActivity.this, holderForMap.class);
        intent.putExtra("reminderID", id);
        startActivityForResult(intent, VIEW_REMIDER_ACTIVITY_REQUEST_CODE);

    }

    private void getDeviceLocation(){
        Log.d("TAG", "getDeviceLocation: getting the devices current location");


        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d("TAG", "onComplete: found location!");
                            myLocation = (Location) task.getResult();
                            //System.out.println("My Location: "+myLocation.getLatitude()+" .. "+myLocation.getLongitude());

                            //moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),DEFAULT_ZOOM);

                        }else{
                            Log.d("TAG", "onComplete: current location is null");
                            Toast.makeText(MainActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e("TAG", "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void getLocationPermission(){
        Log.d("TAG", "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    int getDistance(double lat,double lon) {
        int R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat-DENEVELAT);  // deg2rad below
        double dLon = deg2rad(lon-DENEVELON);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(DENEVELAT)) * Math.cos(deg2rad(DENEVELON)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in km
        int ft = (int)(d*3280.8399);
        return ft;
    }

    double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

}
