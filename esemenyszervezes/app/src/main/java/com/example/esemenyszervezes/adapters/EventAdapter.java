package com.example.esemenyszervezes.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.activities.EventDetailActivity;
import com.example.esemenyszervezes.pojo.Event;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private Context mContext;
    private List<Event> eventList = new ArrayList<>();
    private static final String TAG = "EventDetailActivity";

    public EventAdapter(Context mContext, List<Event> eventList){
        this.mContext = mContext;
        this.eventList = eventList;
    }
    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_layout,parent, false);
        return new MyViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.title.setText(eventList.get(position).getName());
        holder.description.setText(eventList.get(position).getDescription());
        holder.date.setText(eventList.get(position).getDate());
        holder.location.setText(eventList.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title, description, date, location;


        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.event_name);
            description = (TextView) itemView.findViewById(R.id.event_description);
            date = (TextView) itemView.findViewById(R.id.event_date);
            location = (TextView) itemView.findViewById(R.id.event_location);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final Event event = (Event)v.getTag();
            if(event!= null){
                Intent i = new Intent(mContext, EventDetailActivity.class);
                //  i.putExtra("EVENT_DETAIL", new Event(title.toString(), description.toString(), location.toString(), date.toString()));
                i.putExtra("EVENT_DETAIL",event);
                mContext.startActivity(i);
            }
        }

    }

}
