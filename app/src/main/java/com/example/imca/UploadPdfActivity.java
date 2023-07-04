package com.example.imca;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;

public class UploadPdfActivity extends AppCompatActivity {
    private CardView addPdf;
    private TextView pdfTextView;
    private EditText pdfTitle;
    String downloadUrl="";
    private ProgressDialog pd;
  private Uri pdfData;
  private Bitmap bitmap;
    private Button uploadPdfbtn;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private final int REQ=1;
    private String pdfName,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);
        addPdf = findViewById(R.id.addPdf);
        pdfTitle = findViewById(R.id.pdfTitle);
        uploadPdfbtn = findViewById(R.id.uploadPdfbtn);
        pdfTextView=findViewById(R.id.pdfTextView);
         addPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }

        });
         uploadPdfbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 title=pdfTitle.getText().toString();
                 if(title.isEmpty()){
                     pdfTitle.setError("Empty");
                     pdfTitle.requestFocus();

                 }else if(pdfData==null){
                     Toast.makeText(UploadPdfActivity.this,"Please Upload PDF",Toast.LENGTH_SHORT).show();
                 }
                 else
                 {
                     uploadPdf();
                 }
             }
         });

    }

    private void uploadPdf() {
        pd.setTitle("Please Wait...");
        pd.setMessage("Uploading pdf..");
        pd.show();
        StorageReference reference= storageReference.child("pdf/"+pdfName+"."+System.currentTimeMillis()+".pdf");
        reference.putFile(pdfData)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri uri=uriTask.getResult();
                        uploadData(String.valueOf(uri));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(UploadPdfActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadData(String downloadUrl) {
        String uniqueKey=databaseReference.child("pdf").push().getKey();
        HashMap data =new HashMap();
        data.put("pdfTitle",title);
        data.put("pdfUrl",downloadUrl);
        databaseReference.child("pdf").child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UploadPdfActivity.this,"PDF uploaded successfully",Toast.LENGTH_SHORT).show();
                pdfTitle.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadPdfActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery(){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");

        activityResultLauncher.launch(Intent.createChooser(intent,"Select PDF file"));
    }
    ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @SuppressLint("Range")
                @Override
                public void onActivityResult(ActivityResult activityResult) {
                    int result=activityResult.getResultCode();
                    Intent data=activityResult.getData();

                    if(result==RESULT_OK)
                    {
                        pdfData=data.getData();
                       if(pdfData.toString().startsWith("content://")){
                           Cursor cursor=null;
                           try {
                               cursor = UploadPdfActivity.this.getContentResolver().query(pdfData, null, null, null, null);
                               if (cursor != null && cursor.moveToFirst()) {
                                   pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                               }
                           }catch (Exception e){
                               e.printStackTrace();
                           }

                       }else if(pdfData.toString().startsWith("file://")){
                           pdfName=new File(pdfData.toString()).getName();
                       }
                           pdfTextView.setText(pdfName);
                    }
                }
            }
    );
}