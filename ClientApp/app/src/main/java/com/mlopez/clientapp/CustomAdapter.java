package com.mlopez.clientapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<UserModel> userList;

    public CustomAdapter(Context context, ArrayList<UserModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.main_list_layout, parent, false);
        }
        TextView name;
        name = (TextView) convertView.findViewById(R.id.txt_name);
        name.setText(new StringBuilder()
                .append(userList.get(position).getFirstName())
                .append(" ")
                .append(userList.get(position).getLastName()).toString());
        return convertView;
    }
}
