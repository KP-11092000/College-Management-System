package com.example.imca.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.imca.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateFaculty extends AppCompatActivity {
    FloatingActionButton fab;
    private RecyclerView imcaDepartment,mcaDepartment,mcsDepartment,mathDepartment;
    private LinearLayout imcaNoData,mcaNoData,mcsNoData,mathNoData;
    private ArrayList<TeacherData> list1,list2,list3,list4;
    private DatabaseReference reference,dbRef;
    private TeacherAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_faculty);
        imcaDepartment=findViewById(R.id.imcaDepartment);
        mcaDepartment=findViewById(R.id.mcaDepartment);
        mcsDepartment=findViewById(R.id.mcsDepartment);
        mathDepartment=findViewById(R.id.mathDepartment);

        imcaNoData=findViewById(R.id.imcaNoData);
        mcaNoData=findViewById(R.id.mcaNoData);
        mcsNoData=findViewById(R.id.mcsNoData);
        mathNoData=findViewById(R.id.mathNoData);

        reference= FirebaseDatabase.getInstance().getReference().child("teacher");
        imcaDepartment();
        mcaDepartment();
        mcsDepartment();
        mathDepartment();
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateFaculty.this,AddTeacher.class));
            }
        });
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

                    imcaDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    imcaDepartment.setHasFixedSize(true);
                    adapter=new TeacherAdapter(list1,UpdateFaculty.this,"IMCA"  );
                    imcaDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateFaculty.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
                    mcaDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter=new TeacherAdapter(list2,UpdateFaculty.this,"MCA");
                    mcaDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    mcsDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter=new TeacherAdapter(list3,UpdateFaculty.this,"MCS");
                    mcsDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    mathDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter=new TeacherAdapter(list4,UpdateFaculty.this,"MATHEMATICS");
                    mathDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}