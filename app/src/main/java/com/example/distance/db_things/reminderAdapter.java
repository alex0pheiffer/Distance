package com.example.distance.db_things;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.distance.R;
import com.example.distance.reminder;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class reminderAdapter extends RecyclerView.Adapter<reminderAdapter.dataViewHolder>{

    private reminderAdapterListener listener;

        class dataViewHolder extends RecyclerView.ViewHolder {
            private final TextView dataItemView;

            private dataViewHolder(View itemView) {
                super(itemView);
                dataItemView = itemView.findViewById(R.id.textView);
            }
        }

        private final LayoutInflater mInflater;

        private List<Reminder_dbObj> lData; //Cached copy of words
        private List<reminder> lReminders;

        public reminderAdapter(reminderAdapterListener listener, Context context) {
            mInflater = LayoutInflater.from(context);
            this.listener = listener;
        }

        @Override
        public dataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.recycleview_item, parent, false);
            return new dataViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(dataViewHolder holder, final int position) {
            if (lData != null) {
                final reminder current = (reminder)(lReminders.get(position));
                holder.dataItemView.setText(current.getLabel() + " [" + current.getDistance()+" ft ]");
                holder.dataItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //if the item is pressed, open a map view to show where this location is and how far we are
                        listener.reminderPressed(position);
                        System.out.println(""+current.getLabel()+" "+current.getId()+" is pressed.");
                    }
                });
            }
            else {
                // covers the case of data not being ready yet
                holder.dataItemView.setText("Nothing to do...");
            }
        }

        public void setlData(List<Reminder_dbObj> vals) {
            lData = vals;
            lReminders = new ArrayList<reminder>();
            for (Reminder_dbObj n : vals) {
                lReminders.add(new reminder(n.getId(), n.getLabel(), n.getLocation()));
            }
            notifyDataSetChanged();
        }
        // getItemCount() is called many times, and when it is first called,
        // mWords has not been updated (means intially, it's null, and we can't return null
        @Override
        public int getItemCount() {
            if (lData != null) {
                return lData.size();
            }
            else {
                return 0;
            }
        }

        public interface reminderAdapterListener {
            void reminderPressed(int id);
        }
    }
