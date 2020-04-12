package com.example.esemenyszervezes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.pojo.Event;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private Context mContext;
    private List<Event> eventList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

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
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.title.setText(eventList.get(position).getName());
        holder.description.setText(eventList.get(position).getDescription());
        holder.date.setText(eventList.get(position).getDate());
        holder.location.setText(eventList.get(position).getLocation());

       /* Event model = eventList.get(position);

        RequestOptions requestOptions = new RequestOptions();

       Glide.with(mContext)
                .load(eventList.get(position).getImage())
                .apply(RequestOptions.centerCropTransform())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(holder.image);*/
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, description, date, location;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.event_name);
            description = (TextView) itemView.findViewById(R.id.event_description);
            date = (TextView) itemView.findViewById(R.id.event_date);
            location = (TextView) itemView.findViewById(R.id.event_location);
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
