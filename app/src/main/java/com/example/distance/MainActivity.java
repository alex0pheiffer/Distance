package com.example.distance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

public class MainActivity extends AppCompatActivity {


    private distanceViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final reminderAdapter adapter = new reminderAdapter(this);
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
                Intent intent = new Intent(MainActivity.this, dbCheckerActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
