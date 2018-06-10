package com.example.rahulgeorge.beaconclientapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class customListAdapter extends BaseAdapter {
    ArrayList<Beacon> devices;
    LayoutInflater inflater;
    Context context;
    TextView name,company;
    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
            view=inflater.inflate(R.layout.custom_employee_list,null);
        name=view.findViewById(R.id.name);
        company=view.findViewById(R.id.location);
        name.setText(devices.get(i).getBeaconName());
        if(devices.get(i).getActive())
            company.setText("Waiting for the device violation");
        else
            company.setText(devices.get(i).getBlufiName());

        return view;
    }

    public customListAdapter(HashMap<String,Beacon> devices,HashMap<String ,String> deviceList, Context context)
    {
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.devices=new ArrayList<>();
        Beacon temp;
        for (String key :devices.keySet()) {
            temp=devices.get(key);
            temp.setBeaconName(deviceList.get(temp.getBeaconId()));
            temp.setBlufiName(deviceList.get(temp.getBlufiId()));
            this.devices.add(temp);
        }

    }
}
