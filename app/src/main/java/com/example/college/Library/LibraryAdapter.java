package com.example.college.Library;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import androidx.gridlayout.widget.GridLayout;

import java.util.List;

public class LibraryAdapter extends BaseAdapter {
    private Context context;
    private List<Reservation> items;

    public LibraryAdapter(Context context, List<Reservation> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button textView;

        if (convertView == null) {
            textView = new Button(context);
            textView.setLayoutParams(new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, 1f)));
        } else {
            textView = (Button) convertView;
        }

        textView.setText(items.get(position).toString());

        return textView;
    }
}
