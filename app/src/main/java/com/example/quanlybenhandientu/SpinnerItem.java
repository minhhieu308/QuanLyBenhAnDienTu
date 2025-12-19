package com.example.quanlybenhandientu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinnerItem extends BaseAdapter {
    Context context;
    String[] luaChon;

    public SpinnerItem(Context context, String[] luaChon) {
        this.context = context;
        this.luaChon = luaChon;
    }

    @Override
    public int getCount() {
        return luaChon.length;
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
        view = LayoutInflater.from(context).inflate(R.layout.spinner_item_layout,viewGroup,false);
        TextView textView = view.findViewById(R.id.tv);
        textView.setText(luaChon[i]);
        return view;
    }
}
