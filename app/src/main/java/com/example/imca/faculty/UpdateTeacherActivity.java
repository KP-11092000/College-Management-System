package com.example.imca.faculty;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.imca.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateTeacherActivity extends AppCompatActivity {
    private ImageView updateTeacherImage;
    private EditText updateTeacherName,updateTeacherEmail,updateTeacherPost;
    private Button updateTeacherBtn,deleteTeacherBtn;
    private String name,email,post,image,category,uniquekey;
    private Bitmap bitmap=null;
    private String downloadUrl;
    private ProgressDialog pd;
    private StorageReference storageReference;
    private DatabaseReference reference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher);
        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        image=getIntent().getStringExtra("image");
        post=getIntent().getStringExtra("post");
        updateTeacherImage=findViewById(R.id.updateTeacherImage);
        updateTeacherName=findViewById(R.id.updateTeacherName);
        updateTeacherPost=findViewById(R.id.updateTeacherPost);
        updateTeacherEmail=findViewById(R.id.updateTeacherEmail);
        updateTeacherBtn=findViewById(R.id.updateTeacherBtn);
        deleteTeacherBtn=findViewById(R.id.deleteTeacherBtn);

        reference= FirebaseDatabase.getInstance().getReference().child("teacher");
        storageReference= FirebaseStorage.getInstance().getReference();
         uniquekey=getIntent().getStringExtra("key");
         category=getIntent().getStringExtra("category");
        try {
            Picasso.get().load(image).into(updateTeacherImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateTeacherEmail.setText(email);
        updateTeacherName.setText(name);
        updateTeacherPost.setText(post);

        updateTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        updateTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=updateTeacherName.getText().toString();
                email=updateTeacherEmail.getText().toString();
                post=updateTeacherPost.getText().toString();
                checkvalidation();
            }
        });
        deleteTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });

    }

    private void deleteData() {
        reference.child(category).child(uniquekey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UpdateTeacherActivity.this, "Teacher Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UpdateTeacherActivity.this,UpdateFaculty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkvalidation() {
        if(name.isEmpty()){
            updateTeacherName.setError("Empty");
            updateTeacherName.requestFocus();
        }else if(post.isEmpty())
        {
            updateTeacherPost.setError("Empty");
            updateTeacherPost.requestFocus();

        }
        else if(email.isEmpty())
        {
            updateTeacherEmail.setError("Empty");
            updateTeacherEmail.requestFocus();
        }else if(bitmap==null)
        {
            updateData(image);
        }else
        {
            uploadImage();
        }
    }

    private void uploadImage() {

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg=baos.toByteArray();
        final StorageReference filePath;
        filePath=storageReference.child("Teacher").child(finalimg+"jpg");
        final UploadTask uploadTask=filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(UpdateTeacherActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful())
                {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl=String.valueOf(uri);
                                    updateData(downloadUrl);
                                }
                            });
                        }
                    });
                }
                else
                {   pd.dismiss();
                    Toast.makeText(UpdateTeacherActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateData(String s) {
        HashMap hp=new HashMap();
        hp.put("name",name);
        hp.put("email",email);
        hp.put("post",post);
        hp.put("image",s);

        reference.child(category).child(uniquekey).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(UpdateTeacherActivity.this, "Teacher Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UpdateTeacherActivity.this,UpdateFaculty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery(){
        Intent pickImage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(pickImage);
    }
    ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult activityResult) {
                    int result=activityResult.getResultCode();
                    Intent data=activityResult.getData();

                    if(result==RESULT_OK)
                    {
                        Uri uri=data.getData();
                        try {
                            bitmap =MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        updateTeacherImage.setImageBitmap(bitmap);
                    }
                }
            }
    );
}