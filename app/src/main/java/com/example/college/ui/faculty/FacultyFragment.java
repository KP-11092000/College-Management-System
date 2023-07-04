package com.example.college.ui.faculty;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.college.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FacultyFragment extends Fragment {

    private RecyclerView imcaDepartment,mcaDepartment,mcsDepartment,mathDepartment;
    private LinearLayout imcaNoData,mcaNoData,mcsNoData,mathNoData;
    private ArrayList<TeacherData> list1,list2,list3,list4;
    private DatabaseReference reference,dbRef;
    private TeacherAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_faculty, container, false);
        imcaDepartment=view.findViewById(R.id.imcaDepartment);
        mcaDepartment=view.findViewById(R.id.mcaDepartment);
        mcsDepartment=view.findViewById(R.id.mcsDepartment);
        mathDepartment=view.findViewById(R.id.mathDepartment);

        imcaNoData=view.findViewById(R.id.imcaNoData);
        mcaNoData=view.findViewById(R.id.mcaNoData);
        mcsNoData=view.findViewById(R.id.mcsNoData);
        mathNoData=view.findViewById(R.id.mathNoData);

        reference= FirebaseDatabase.getInstance().getReference().child("teacher");
        imcaDepartment();
        mcaDepartment();
        mcsDepartment();
        mathDepartment();

        return view;
    }

    private void imcaDepartment() {
        dbRef=FirebaseDatabase.getInstance().getReference().child("teacher").child("IMCA");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1=new ArrayList<>();
                if(!dataSnapshot.exists()){
                    imcaNoData.setVisibility(View.VISIBLE);
                    imcaDepartment.setVisibility((View.GONE));
                }else
                {
                    imcaNoData.setVisibility(View.GONE);
                    imcaDepartment.setVisibility((View.VISIBLE));
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        TeacherData data=snapshot.getValue(TeacherData.class);
                        list1.add(data);


                    }

                    imcaDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    imcaDepartment.setHasFixedSize(true);
                    adapter=new TeacherAdapter(list1,getContext());
                    imcaDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mcaDepartment() {
        dbRef=reference.child("MCA");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2=new ArrayList<>();
                if(!snapshot.exists()){
                    mcaNoData.setVisibility(View.VISIBLE);
                    mcaDepartment.setVisibility((View.GONE));
                }else
                {
                    mcaNoData.setVisibility(View.GONE);
                    mcaDepartment.setVisibility((View.VISIBLE));
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        TeacherData data=snapshot1.getValue(TeacherData.class);
                        list2.add(data);


                    }
                    mcaDepartment.setHasFixedSize(true);
                    mcaDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list2,getContext());
                    mcaDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mcsDepartment() {
        dbRef=reference.child("MCS");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3=new ArrayList<>();
                if(!snapshot.exists()){
                    mcsNoData.setVisibility(View.VISIBLE);
                    mcsDepartment.setVisibility((View.GONE));
                }else
                {
                    mcsNoData.setVisibility(View.GONE);
                    mcsDepartment.setVisibility((View.VISIBLE));
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        TeacherData data=snapshot1.getValue(TeacherData.class);
                        list3.add(data);


                    }
                    mcsDepartment.setHasFixedSize(true);
                    mcsDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list3,getContext());
                    mcsDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mathDepartment() {
        dbRef=reference.child("MATHEMATICS");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4=new ArrayList<>();
                if(!snapshot.exists()){
                    mathNoData.setVisibility(View.VISIBLE);
                    mathDepartment.setVisibility((View.GONE));
                }else
                {
                    mathNoData.setVisibility(View.GONE);
                    mathDepartment.setVisibility((View.VISIBLE));
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        TeacherData data=snapshot1.getValue(TeacherData.class);
                        list4.add(data);


                    }
                    mathDepartment.setHasFixedSize(true);
                    mathDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list4,getContext());
                    mathDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}