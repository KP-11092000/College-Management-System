package com.example.college.ui.about;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;

import com.example.college.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class AboutFragment extends Fragment {


    private ViewPager viewPager;
    private BranchAdapter adapter;
    private ArrayList<BranchModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about, container, false);
        list=new ArrayList<>();
        list.add(new BranchModel(R.drawable.ic_computer,"MCA","MCA in Fergusson College"));
        list.add(new BranchModel(R.drawable.ic_imca,"IMCA","IMCA in Fergusson College"));
        adapter=new BranchAdapter(getContext(),list);
        viewPager=view.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        ImageView imageView=view.findViewById(R.id.college_image);
        try {
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/imca-860a6.appspot.com/o/gallery%2F%5BB%40f0b24a0jpg?alt=media&token=43e8110e-628e-4c28-bbd6-fb903cb0c8b2").into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

}