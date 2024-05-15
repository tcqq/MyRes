package com.android.myres.common.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class SpinnerAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private String[] item;

    public SpinnerAdapter(Context context, String[] item) {
        this.context = context;
        this.item = item;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            results.values = item;
            results.count = item.length;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return filter;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        TextView text = view.findViewById(android.R.id.text1);
        text.setText(item[position].trim());
        return view;
    }

    @Override
    public Object getItem(int position) {
        return item[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return item.length;
    }

    public int getPosition(String value) {
        for (int i = 0; i < item.length; i++) {
            if (item[i].equalsIgnoreCase(value)) {
                return i;
            }
        }
        return -1;
    }
}