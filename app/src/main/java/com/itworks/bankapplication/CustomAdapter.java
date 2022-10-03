package com.itworks.bankapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Operation> arrayList;
    private Activity activity;



    public CustomAdapter(MainActivity mainActivity, Activity activity, Context context, ArrayList<Operation> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.activity = activity;

    }



    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mycustomlistview, null);
            TextView name = (TextView) convertView.findViewById(R.id.id_name);
            TextView amount = (TextView) convertView.findViewById(R.id.id_amount);
            TextView date = (TextView) convertView.findViewById(R.id.id_date);
            TextView isIncome =(TextView)convertView.findViewById(R.id.id_isIncome);
            TextView desc = (TextView) convertView.findViewById(R.id.descInList);

            Operation operation = arrayList.get(position);
            name.setText(operation.getName());
            amount.setText(String.valueOf(operation.getAmount()));
            date.setText(operation.getDate());
            isIncome.setText(operation.stringIsIncome());
            desc.setText(operation.getDescription());

        }

        return convertView;
    }






    public Object getItem(int position){
        return arrayList.get(position);
    }


    public int getCount(){
        return this.arrayList.size();
    }


    public void deleteItem(int pos) {
        arrayList.remove(pos);
        notifyDataSetChanged();
    }

}
