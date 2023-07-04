package com.example.college;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.college.Library.AddData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayDeque;

public class LibraryActivity extends AppCompatActivity {

    String color,name;
    Button selectedSeat=null;
    private EditText nameEditText;
    private Button sumbitButton;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Library Seat Reservation");
        sumbitButton = findViewById(R.id.submitButton);
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        gridLayout.setColumnCount(28); // Set the column count to 20 for example
        nameEditText = findViewById(R.id.nameEditText);
        int cellWidth = getResources().getDimensionPixelSize(R.dimen.seat_cell_width);
        int cellHeight = getResources().getDimensionPixelSize(R.dimen.seat_cell_height);
        for (int i = 0; i < 105; i++) {
            Button seatView = new Button(this);
            seatView.setText("seat" + (i + 1));
            seatView.setId(i + 1);
            seatView.setWidth(cellWidth);
            seatView.setHeight(cellHeight);
            seatView.setBackgroundColor(Color.WHITE);
            dbRef= FirebaseDatabase.getInstance().getReference().child("teacher").child(category);
            final String uniqueKey=dbRef.push().getKey();
            AddData temp = new AddData(i + 1, "WHITE");


        }
    }
}
