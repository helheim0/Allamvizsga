package com.example.esemenyszervezes.adapters;
import com.example.esemenyszervezes.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.esemenyszervezes.pojo.User;

import java.util.ArrayList;
import java.util.List;


public class MemberAdapter extends ArrayAdapter {
    private Context context;
    private List<User> memberList;

    public MemberAdapter(Context context, List<User> memberList) {
        super(context, R.layout.member_model, memberList);
        this.context = context;
        this.memberList = memberList;
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
        return memberList.size();
    }

    @Override
    public Object getItem(int position) {
        return memberList.get(position);
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

            holder.name = (TextView) convertView.findViewById(R.id.member_name);
            holder.username = (TextView) convertView.findViewById(R.id.member_username);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.name.setText(memberList.get(position).getFullName());
        holder.username.setText(memberList.get(position).getUsername());

        return convertView;
    }

    private class ViewHolder {
        protected TextView name, username;
    }
}
