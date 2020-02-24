package com.example.distance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.distance.db_things.Reminder_dbObj;
import com.example.distance.db_things.distanceViewModel;
import com.example.distance.db_things.reminderAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements reminderAdapter.reminderAdapterListener {


    private distanceViewModel viewModel;
    public static final int NEW_REMINDER_ACTIVITY_REQUEST_CODE = 1;
    public static final int VIEW_REMIDER_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final reminderAdapter adapter = new reminderAdapter(this, this);
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

            Reminder_dbObj reminder = new Reminder_dbObj(data.getStringExtra(newItemActivity.EXTRA_REPLY_LABEL),data.getStringExtra(newItemActivity.EXTRA_REPLY_LOCATION), 0);
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
}
