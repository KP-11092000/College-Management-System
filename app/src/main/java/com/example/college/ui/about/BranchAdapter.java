package com.example.college.ui.about;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.college.R;

import java.util.ArrayList;
import java.util.List;

public class BranchAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<BranchModel> list;

    public BranchAdapter(Context context, ArrayList<BranchModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view=LayoutInflater.from(context).inflate(R.layout.branch_item_layout,container,false);
        ImageView brIcon;
        TextView brtitle,brDesc;
        brIcon=view.findViewById(R.id.br_icon);
        brtitle=view.findViewById(R.id.br_title);
        brDesc=view.findViewById(R.id.br_desc);

        brIcon.setImageResource(list.get(position).getImg());
        brtitle.setText((list.get(position).getTitle()));
        brDesc.setText(list.get(position).getDescription());
        container.addView(view,0);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
      container.removeView((View) object);
    }
}
