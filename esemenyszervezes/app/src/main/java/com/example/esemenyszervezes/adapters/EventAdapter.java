package com.example.esemenyszervezes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.activities.EventDetailActivity;
import com.example.esemenyszervezes.activities.EventInvitationActivity;
import com.example.esemenyszervezes.pojo.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private Context mContext;
    private List<Event> eventList;
    private static final String TAG = "EventDetailActivity";
    private OnItemClickListener clickListener;

    public EventAdapter(Context mContext, List<Event> eventList){
        this.mContext = mContext;
        this.eventList = eventList;
    }

    public interface OnItemClickListener {
        void onAddClicked(int position);
        void onMoreClicked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_layout, parent, false);
        return new MyViewHolder(view, clickListener);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, date, location;
        ImageView add, more;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener clickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.event_name);
            description = itemView.findViewById(R.id.event_description);
            date = itemView.findViewById(R.id.event_date);
            location = itemView.findViewById(R.id.event_location);
            add = itemView.findViewById(R.id.add_icon);
            more = itemView.findViewById(R.id.more_icon);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Event event = (Event)v.getTag();
                    if(clickListener != null && event!= null){
                        int position = getAdapterPosition();
                        clickListener.onAddClicked(position);
                            Intent i = new Intent(mContext, EventInvitationActivity.class);
                            i.putExtra("name", event.getName());
                            i.putExtra("date", event.getDate());
                            i.putExtra("description", event.getDescription());
                            i.putExtra("location", event.getLocation());
                            v.getContext().startActivity(i);
                    }
                }
            });
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Event event = (Event) v.getTag();
                    if (clickListener != null && event != null) {
                        int position = getAdapterPosition();
                        clickListener.onMoreClicked(position);
                            Intent i = new Intent(mContext, EventDetailActivity.class);
                            i.putExtra("name", event.getName());
                            v.getContext().startActivity(i);
                    }
                }
            });
        }

       /* @Override
        public void onClick(View v) {
            //if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
            final Event event = (Event)v.getTag();
            if(event!= null){

                if(R.id.more_icon == v.getId()){
                Intent i = new Intent(mContext, EventDetailActivity.class);
                i.putExtra("EVENT_DETAIL", event);
                mContext.startActivity(i);
                }

               if(R.id.add_icon == v.getId()){
                    Intent i = new Intent(mContext, EventInvitationActivity.class);
                    i.putExtra("EVENT_DETAIL", event);
                    mContext.startActivity(i);
                }
            }
        }*/
    }
}
