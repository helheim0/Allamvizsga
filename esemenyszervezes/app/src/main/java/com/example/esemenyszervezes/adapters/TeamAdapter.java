package com.example.esemenyszervezes.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.activities.TeamDetailActivity;
import com.example.esemenyszervezes.pojo.Team;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.MyViewHolder> {
    public static final String TEAM_DETAIL = "TEAM_DETAIL";
    private Context mContext;
    private List<Team> teamList = new ArrayList<>();

    public TeamAdapter(Context mContext, List<Team> teamList){
        this.mContext = mContext;
        this.teamList = teamList;
    }

    public void loadTeams(List<Team> teamList){
        this.teamList = teamList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TeamAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_team_layout, parent, false);
        return new MyViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.MyViewHolder holder, int position) {
       Team team = teamList.get(position);
        holder.title.setText(teamList.get(position).getName());
        holder.description.setText(teamList.get(position).getDescription());
        //holder.image.setImageResource(teamList.get(position).getImage());
        //holder.image.setImageResource(teamList.get(position).getImage());
        //Glide.with(mContext).load(teamList.get(position).getImage()).apply(RequestOptions.centerCropTransform()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        final TextView title;
        final TextView description;
        //final ImageView image;

        public MyViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.teamNameLV);
            //image = (ImageView) itemView.findViewById(R.id.teamImage);
            description = (TextView) itemView.findViewById(R.id.team_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Team team = (Team) v.getTag();
                            if(team!= null){
                                Intent i = new Intent(mContext, TeamDetailActivity.class);
                                i.putExtra(TEAM_DETAIL, new Team(title.toString(), description.toString()));
                                mContext.startActivity(i);
                            }
                        }
            });
        }

    }
}
