package com.example.college.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.college.MainActivity;

import com.example.college.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import okhttp3.MediaType;

public class RegisterActivity extends AppCompatActivity {
private EditText regName,regEmail,regPassword;
private Button register;
private String name,email,pass;
private FirebaseAuth auth;
private DatabaseReference reference;
private DatabaseReference dbRef;
private ProgressDialog pd;
private TextView openLog;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        pd=new ProgressDialog(this);
        auth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference();;
        regName=findViewById(R.id.regName);
        regEmail=findViewById(R.id.regEmail);
        regPassword=findViewById(R.id.regPass);
        register=findViewById(R.id.register);
        openLog=findViewById(R.id.openLogin);
        openLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void openLogin() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }


    private void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    private void validateData() {
        name=regName.getText().toString();
        email=regEmail.getText().toString();
        pass=regPassword.getText().toString();
        if(name.isEmpty())
        {
            regName.setError("Required Name");
            regName.requestFocus();
        }else if(email.isEmpty())
        {
            regEmail.setError("Required Email");
            regEmail.requestFocus();
        }
        else if(pass.isEmpty())
        {
            regPassword.setError("Required Password");
            regPassword.requestFocus();
        }
        else
        {
            pd.setMessage("Creating account Please Wait ");
            pd.show();
            createUser();
        }

    }

    private void createUser() {
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    uploadData();
                }else
                {
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(RegisterActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadData() {
        dbRef=reference.child("users");
        String key=dbRef.push().getKey();
        HashMap<String,String> user=new HashMap<>();
        user.put("key",key);
        user.put("name",name);
        user.put("email",email);
        user.put("status","no");
        dbRef.child(key).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                    openMain();
                }
                else
                {
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}