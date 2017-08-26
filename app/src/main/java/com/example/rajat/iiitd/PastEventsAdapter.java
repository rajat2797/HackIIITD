package com.example.rajat.iiitd;

/**
 * Created by rajat on 26/8/17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PastEventsAdapter extends RecyclerView.Adapter<PastEventsAdapter.MyEventsView> {

    List<EventsClass> events;
    Context ctx;

    public PastEventsAdapter(Context c, List<EventsClass> a){
        ctx = c;
        events = a;
    }

    @Override
    public MyEventsView onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        View v = layoutInflater.inflate(R.layout.past_events, parent, false);
        return new MyEventsView(v);

    }

    @Override
    public void onBindViewHolder(MyEventsView holder, int position) {
        holder.name.setText(events.get(position).name);
        holder.status.setText(events.get(position).status);
        holder.date.setText(events.get(position).date);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class MyEventsView extends RecyclerView.ViewHolder {
        TextView name;
        TextView status;
        TextView date;
        public MyEventsView(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.eventName);
            status = (TextView) itemView.findViewById(R.id.eventStatus);
            date = (TextView) itemView.findViewById(R.id.eventDate);
        }
    }
}
