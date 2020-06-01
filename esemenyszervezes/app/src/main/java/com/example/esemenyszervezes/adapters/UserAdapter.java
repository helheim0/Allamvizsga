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

public class UserAdapter extends ArrayAdapter {
    private Context context;
    private List<User> userList;

    private LayoutInflater layoutInflater;

    public UserAdapter(Context context, List<User> userList) {
        super(context, 0, userList);
        this.context = context;
        this.userList = userList;
    }

    @Override
    public User getItem(int position){
        return userList.get(position);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.user_item, null, true);
            holder.name = convertView.findViewById(R.id.item_name);
            holder.username =  convertView.findViewById(R.id.item_username);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.name.setText(userList.get(position).getFullName());
        holder.username.setText(userList.get(position).getUsername());

        return convertView;
    }

    private class ViewHolder {
        protected TextView name, username;

    }
}
