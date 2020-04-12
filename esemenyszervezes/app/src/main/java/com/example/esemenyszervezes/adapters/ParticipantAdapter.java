package com.example.esemenyszervezes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.esemenyszervezes.R;
import com.example.esemenyszervezes.pojo.User;

import java.util.List;

public class ParticipantAdapter extends ArrayAdapter {
    private Context context;
    private List<User> participantList;

    public ParticipantAdapter(Context context, List<User> participantList) {
        super(context, R.layout.member_model, participantList);
        this.context = context;
        this.participantList = participantList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return participantList.size();
    }

    @Override
    public Object getItem(int position) {
        return participantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.member_model, null, true);

            holder.name = (TextView) convertView.findViewById(R.id.participant_name);
            holder.username = (TextView) convertView.findViewById(R.id.participant_username);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.name.setText(participantList.get(position).getFullName());
        holder.username.setText(participantList.get(position).getUsername());

        return convertView;
    }

    private class ViewHolder {
        protected TextView name, username;
    }
}
