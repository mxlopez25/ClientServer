package com.mlopez.clientapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AddressAdapter extends BaseAdapter {
    Context context;
    ArrayList<AddressModel> addressList;



    public AddressAdapter(Context context, ArrayList<AddressModel> addressList) {
        this.context = context;
        this.addressList = addressList;
    }

    @Override
    public int getCount() {
        return addressList.size();
    }

    @Override
    public Object getItem(int position) {
        return addressList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return addressList.get(position).addressId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.address_list_layout, parent, false);
        }
        TextView tvStreet1, tvStreet2, tvCity, tvZipCode, tvPhone;
        tvStreet1 = (TextView) convertView.findViewById(R.id.txt_street1);
        tvCity = (TextView) convertView.findViewById(R.id.txt_city);
        tvPhone = (TextView) convertView.findViewById(R.id.txt_phone);
        tvZipCode = (TextView) convertView.findViewById(R.id.txt_zipcode);

        tvStreet1.setText(new StringBuilder().append(addressList.get(position).getStreet1()).append(", ").append(addressList.get(position).getStreet2()));
        tvCity.setText(addressList.get(position).getCity());
        tvZipCode.setText(addressList.get(position).getZipCode());
        tvPhone.setText(addressList.get(position).getPhone());
        return convertView;
    }
}
