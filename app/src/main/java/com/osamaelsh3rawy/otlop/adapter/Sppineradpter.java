package com.osamaelsh3rawy.otlop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.osamaelsh3rawy.otlop.R;
import com.osamaelsh3rawy.otlop.data.model.listOfAreaNot.ListOfAreaData;

import java.util.ArrayList;
import java.util.List;

public class Sppineradpter extends BaseAdapter {
    List<ListOfAreaData> data = new ArrayList<>();
    public int selectedId = 0;
    private LayoutInflater inflater;
    private Context context;

    public Sppineradpter(Context context) {
        this.context = context;
        inflater = (LayoutInflater.from(context));
    }

    public void setData(List<ListOfAreaData> list, String hint) {
        this.data = new ArrayList<>();
        this.data.add(new ListOfAreaData(0, hint));
        this.data.addAll(list);
    }

    @Override
    public int getCount() {
        return data.size();
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
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.item_custom_spinner_layout, null);
        TextView spinnerTV = view.findViewById(R.id.tvSpinnerLayout);

        spinnerTV.setText(data.get(position).getName());
        selectedId = data.get(position).getId();
        return view;
    }
}
