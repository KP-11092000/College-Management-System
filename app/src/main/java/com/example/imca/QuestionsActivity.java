package com.example.imca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

public class QuestionsActivity extends AppCompatActivity {
    private RecyclerView quesView;
    private Button quesBtn;
    private List<QuestionModel> quesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar toolbar=findViewById(R.id.q_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Questions");
        quesView=findViewById(R.id.ques_recycler);
        quesBtn=findViewById(R.id.quesBtn);
        quesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        quesView.setLayoutManager(layoutManager);
        loadQuestion();
    }

    private void loadQuestion() {
        quesList.add(new QuestionModel("1","Q1","A","B","C","D",2));
        quesList.add(new QuestionModel("2","Q2","A","B","C","D",2));
        quesList.add(new QuestionModel("3","Q3","A","B","C","D",2));
    }
}