package com.example.college.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.college.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private ImageSlider imageSlider;
    private ImageView map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        imageSlider=view.findViewById(R.id.slider);
        ArrayList<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/imca-860a6.appspot.com/o/gallery%2F%5BB%40b70304bjpg?alt=media&token=8ce99eab-d5c1-4223-8624-7f7d7c58b1cc", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/imca-860a6.appspot.com/o/gallery%2F%5BB%40ce1beebjpg?alt=media&token=fc2b000f-dcea-4c0c-8eeb-a6b0474e52a5", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/imca-860a6.appspot.com/o/gallery%2F%5BB%40126b8a1jpg?alt=media&token=e919b7f6-7b5c-4c6e-acc0-87246e8f399b", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);
        imageSlider.startSliding(1000);
        map=view.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });
        return view;
    }

    private void openMap() {
        Uri uri= Uri.parse("geo:0,0?q=Fergusson College Pune");
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }
}