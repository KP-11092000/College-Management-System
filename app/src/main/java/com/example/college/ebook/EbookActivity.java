package com.example.college.ebook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.college.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class EbookActivity extends AppCompatActivity {
    private RecyclerView ebookRecycler;
    private DatabaseReference reference;
    private ArrayList<EbookData> list,list1;
    private EbookAdapter adapter;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout linearLayout;


    private File filePath=new File(Environment.getExternalStorageDirectory()+"/Demo.xlsx");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        setContentView(R.layout.activity_ebook);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ebooks");

        FloatingActionButton downloadButton =(FloatingActionButton)findViewById(R.id.download_button);
        ebookRecycler=findViewById(R.id.ebookRecycler);
        shimmerFrameLayout=findViewById(R.id.shimmer_view_container );
        linearLayout=findViewById(R.id.shimmer_layout);
        reference= FirebaseDatabase.getInstance().getReference().child("pdf");
        list=new ArrayList<>();
        list1=new ArrayList<>();
        getData();

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list1.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            EbookData data=snapshot.getValue(EbookData.class);

                            list1.add(data);
                        }

                        try {
                            HSSFWorkbook workbook=new HSSFWorkbook();
                            Sheet sheet=workbook.createSheet("Books");

                            Row headerRow;

                            headerRow=sheet.createRow(0);
                            headerRow.createCell(0).setCellValue("Index");
                            headerRow.createCell(1).setCellValue("Book Name");
                            headerRow.createCell(2).setCellValue("Links");


                            int rowIndex=1;
                            for(int i=0;i<list1.size();i++){
                                Row row=sheet.createRow(rowIndex++);
                                EbookData data1=list1.get(i);
                                row.createCell(0).setCellValue(i+1);
                                row.createCell(1).setCellValue(data1.getPdfTitle());
                                row.createCell(2).setCellValue(data1.getPdfUrl());


                            }
                            try {
                                if(!filePath.exists()){
                                    filePath.createNewFile();
                                }
                                FileOutputStream fileout=new FileOutputStream(filePath);
                                workbook.write(fileout);
                                if(fileout!=null) {
                                    fileout.flush();
                                    fileout.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(EbookActivity.this,"Excel Sheet has been generated Check your File Manager  ",Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EbookActivity.this,"Failed to get Data from database", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



    }


    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EbookData data=snapshot.getValue(EbookData.class);

                    list.add(data);
                }

                adapter=new EbookAdapter(EbookActivity.this, list);
                ebookRecycler.setLayoutManager(new LinearLayoutManager(EbookActivity.this));

                ebookRecycler.setAdapter(adapter);
                shimmerFrameLayout.stopShimmer();
                linearLayout.setVisibility(View.GONE);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EbookActivity.this,"Failed to get Data from database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    protected void onResume() {
        shimmerFrameLayout.startShimmer();
        super.onResume();
    }



}