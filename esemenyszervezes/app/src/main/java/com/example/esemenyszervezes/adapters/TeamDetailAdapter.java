package com.example.esemenyszervezes.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.activities.TeamDetailActivity;
import com.example.esemenyszervezes.pojo.OnItemClickListener;
import com.example.esemenyszervezes.pojo.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamDetailAdapter extends RecyclerView.Adapter<TeamDetailAdapter.MyViewHolder> {
    public static final String TEAM_DETAIL = "TEAM_DETAIL";
    private static final String TAG = "TeamDetailAdapter";
    private Context mContext;
    private List<Team> teamList;
    private OnItemClickListener clickListener;

    public TeamDetailAdapter(Context mContext, List<Team> teamList) {
        this.mContext = mContext;
        this.teamList = teamList;
    }

    @NonNull
    @Override
    public TeamDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_team_layout, parent, false);
        return new MyViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamDetailAdapter.MyViewHolder holder, int position) {
        Team team = teamList.get(position);
        holder.itemView.setTag(team);
        holder.title.setText(teamList.get(position).getName());
        holder.description.setText(teamList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView title;
        final TextView description;

        public MyViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            title = itemView.findViewById(R.id.detail_teamNameLV);
            description = itemView.findViewById(R.id.detail_team_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final Team team = (Team) v.getTag();
            if(team != null) {
                Intent intent = new Intent(mContext, TeamDetailActivity.class);
                intent.putExtra("TEAM_DETAIL", team);
                mContext.startActivity(intent);
                Log.d(TAG, "onClick: adapter click called");
            }
        }
    }
}
