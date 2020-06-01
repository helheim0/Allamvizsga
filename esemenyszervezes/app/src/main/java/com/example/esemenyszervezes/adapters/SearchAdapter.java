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
import com.example.esemenyszervezes.activities.AcceptInvitationActivity;
import com.example.esemenyszervezes.pojo.Team;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private static final String TAG = "SearchAdapter";
    private List<Team> teams;
    private Context context;
    private SelectedTeam selectedTeam;

    public SearchAdapter(List<Team> teams,  SelectedTeam selectedTeam, Context context) {
        this.teams = teams;
        this.selectedTeam = selectedTeam;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new MyViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Team team = teams.get(position);
        holder.itemView.setTag(team);
        holder.name.setText(teams.get(position).getName());
        holder.code.setText(teams.get(position).getCode());
        holder.description.setText(teams.get(position).getDescription());
        holder.id.setText(Integer.toString(teams.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    private List<Team> data = new ArrayList<>();
    public void swapData(List<Team> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }
    public interface SelectedTeam{

        void selectedTeam(Team teamModel);

    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView name;
        final TextView description;
        final TextView code;
        final TextView id;

        public MyViewHolder(View itemView,  Context context) {
            super(itemView);
            name = itemView.findViewById(R.id.search_name);
            code = itemView.findViewById(R.id.search_code);
            description = itemView.findViewById(R.id.search_desc);
            id = itemView.findViewById(R.id.search_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
          //  selectedTeam.selectedTeam(teams.get(getAdapterPosition()));
           final Team team = (Team) v.getTag();
            if(team != null) {
                Intent intent = new Intent(context, AcceptInvitationActivity.class);
                intent.putExtra("TEAM_DETAIL", team);
                context.startActivity(intent);
                Log.d(TAG, "onClick: adapter click called");
            }
        }
    }
}
