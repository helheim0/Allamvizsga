package com.example.esemenyszervezes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.activities.TeamAdminActivity;
import com.example.esemenyszervezes.pojo.OnItemClickListener;
import com.example.esemenyszervezes.pojo.Team;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.MyViewHolder> {
    public static final String TEAM_DETAIL = "TEAM_DETAIL";
    private static final String TAG = "TeamAdapter";
    private Context mContext;
    private List<Team> teamList;
    private OnItemClickListener clickListener;

    public TeamAdapter(List<Team> teamList, Context mContext) {
        this.mContext = mContext;
        this.teamList = teamList;
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
        holder.itemView.setTag(team);
        holder.title.setText(teamList.get(position).getName());
        holder.description.setText(teamList.get(position).getDescription());
        holder.code.setText(teamList.get(position).getCode());
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
        final TextView code;

        public MyViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            title = itemView.findViewById(R.id.teamNameLV);
            description = itemView.findViewById(R.id.team_description);
            code = itemView.findViewById(R.id.edit_team);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
                final Team team = (Team) v.getTag();
                if (team != null) {
                    Intent intent = new Intent(mContext, TeamAdminActivity.class);
                    intent.putExtra("TEAM_DETAIL", team);
                    mContext.startActivity(intent);
                }
            }
        }
    }

