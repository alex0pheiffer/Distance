package com.example.distance;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.distance.db_things.Reminder_dbObj;
import com.example.distance.db_things.distanceViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class holderForMap extends AppCompatActivity {

    public static final String EXTRA_REPLY_REMOVE = "REPLY_REMOVE";

    private distanceViewModel viewModel;
    private List<reminder> lReminders;

    private reminder mReminder;
    private TextView titleTextView;
    private TextView locationTextView;
    private Button removeButton;
    private FloatingActionButton fab_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder_for_map);

        Intent mIntent = getIntent();
        final int reminderID = mIntent.getIntExtra("reminderID", 0);

        titleTextView = findViewById(R.id.reminder_map);
        locationTextView = findViewById(R.id.location_map);
        removeButton = findViewById(R.id.removeButton);
        fab_button = findViewById(R.id.fab_map);

        lReminders = new ArrayList<reminder>();
        viewModel = new ViewModelProvider(this).get(distanceViewModel.class);
        viewModel.getlReminders().observe(this, new Observer<List<Reminder_dbObj>>() {
            @Override
            public void onChanged(@Nullable final List<Reminder_dbObj> vals) {
                for (Reminder_dbObj n : vals) {
                    lReminders.add(new reminder(n.getId(),n.getLabel(), n.getLocation()));
                }
                //set this activity's reminder and the cooresponding views
                System.out.println("ACT CREATED: id = "+reminderID);
                mReminder = lReminders.get(reminderID);
                titleTextView.setText(mReminder.getLabel());
                locationTextView.setText(mReminder.getLocation());
            }
        });


        fab_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                System.out.println("FAB PRESSED: return -1");
                replyIntent.putExtra(EXTRA_REPLY_REMOVE, -1);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                System.out.println("FAB PRESSED: return "+reminderID);
                replyIntent.putExtra(EXTRA_REPLY_REMOVE, reminderID);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });


    }
}
