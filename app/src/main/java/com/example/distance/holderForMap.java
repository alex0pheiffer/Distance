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

import java.util.List;

public class holderForMap extends AppCompatActivity {

    public static final String EXTRA_REPLY_REMOVE = "REPLY_REMOVE";

    private distanceViewModel viewModel;

    private reminder mReminder;
    private TextView locationTextView;
    private Button removeButton;
    private Button fab_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder_for_map);

        locationTextView = findViewById(R.id.location_map);
        removeButton = findViewById(R.id.removeButton);
        fab_button = findViewById(R.id.fab_map);

        viewModel = new ViewModelProvider(this).get(distanceViewModel.class);
        viewModel.getlReminders().observe(this, new Observer<List<Reminder_dbObj>>() {
            @Override
            public void onChanged(@Nullable final List<Reminder_dbObj> vals) {
                //do nothing? this shouldnt change while we're viewing the list here
            }
        });
        fab_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_REPLY_REMOVE, false);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_REPLY_REMOVE, true);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });


    }
}
