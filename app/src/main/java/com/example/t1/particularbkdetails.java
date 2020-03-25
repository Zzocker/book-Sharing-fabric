package com.example.t1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.t1.RetrofitApis.ApiInterface;
import com.example.t1.RetrofitApis.RetrofitClient;
import com.example.t1.RetrofitCoverimageModels.Sendcoverimgurlformat;
import com.example.t1.RetrofitRegisPage.Getregisformat;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iceteck.silicompressorr.SiliCompressor;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

import es.dmoral.toasty.Toasty;
import is.arontibo.library.ElasticDownloadView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class particularbkdetails extends AppCompatActivity {
    private Toolbar mtoolbar;
    private StorageReference mImageStorage;
    private  static final int GALLERY_PIC=1;

    private ImageView bkcoverimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particularbkdetails);
        mtoolbar=(Toolbar)findViewById(R.id.particularbktoolbar);
        Intent intent=getIntent();
        String bkisbn=intent.getStringArrayExtra("ID_EXTRA")[0];
        mImageStorage= FirebaseStorage.getInstance().getReference();
        bkcoverimg=findViewById(R.id.bkcoverimage);
        String bkname=intent.getStringArrayExtra("ID_EXTRA")[1];
        String bkauthor=intent.getStringArrayExtra("ID_EXTRA")[2];
        String bkimgurl=intent.getStringArrayExtra("ID_EXTRA")[3];
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(bkname);
        if(!bkimgurl.isEmpty())
        {
            Picasso.with(particularbkdetails.this).load(bkimgurl).into(bkcoverimg);
        }

    }

    public void changecoverclick(View view) {



        Intent galleryIntent=new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        CropImage.activity()
                .setAspectRatio(3,4)
                .start(this);
        //CropImage.ActivityResult result = CropImage.getActivityResult(data);
        //startActivityForResult(Intent.createChooser(galleryIntent,"SELECT IMAGE"),GALLERY_PIC);



    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                String filepath4=resultUri.getPath();
                //Toast.makeText(this, filepath4, Toast.LENGTH_SHORT).show();
                String newcompressedimagepath = SiliCompressor.with(particularbkdetails.this).compress(filepath4, new File("/data/user/0/com.example.t1/cache/"));
                Intent intent=getIntent();
                String bkisbn=intent.getStringArrayExtra("ID_EXTRA")[0];
                final StorageReference filepath=mImageStorage.child("BookCovers").child(bkisbn+".jpg");
                filepath.putFile(Uri.fromFile(new File(newcompressedimagepath))).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //Got download url now put it in blockchain
                                    final String download_url=uri.toString();
                                    SharedPreferences sharedPreferences=getSharedPreferences("Secrets",MODE_PRIVATE);
                                    String currenttoken=sharedPreferences.getString("token","");
                                    //backend starts

                                    ApiInterface apiInterface= RetrofitClient.getClient().create(ApiInterface.class);
                                    Intent intent=getIntent();
                                    String bkisbn=intent.getStringArrayExtra("ID_EXTRA")[0];
                                    Sendcoverimgurlformat senddet=new Sendcoverimgurlformat(bkisbn,download_url);
                                    Call<Getregisformat> c=apiInterface.changecover(senddet,currenttoken);
                                    c.enqueue(new Callback<Getregisformat>() {
                                        @Override
                                        public void onResponse(Call<Getregisformat> call, Response<Getregisformat> response) {
                                            if(response.isSuccessful())
                                            {
                                                Toasty.success(particularbkdetails.this,"Successfully Uploaded Cover Image",Toasty.LENGTH_SHORT).show();

                                                Picasso.with(particularbkdetails.this).load(download_url).into(bkcoverimg);
                                            }
                                            else
                                            {
                                                Toasty.error(particularbkdetails.this,response.body().getMsg(),Toasty.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Getregisformat> call, Throwable t) {
                                            Toasty.error(particularbkdetails.this,t.getMessage(),Toasty.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            });
                        }
                    }
                });


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        /*if(requestCode==GALLERY_PIC&&resultCode==RESULT_OK)
        {


            Uri imageUri= data.getData();

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            Toast.makeText(this, "Entering", Toast.LENGTH_SHORT).show();
            Uri resultUri = result.getUri();

            Intent intent=getIntent();
            String bkisbn=intent.getStringArrayExtra("ID_EXTRA")[0];
            final StorageReference filepath=mImageStorage.child("BookCovers").child(bkisbn+".jpg");
            filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //Got download url now put it in blockchain
                                final String download_url=uri.toString();
                                SharedPreferences sharedPreferences=getSharedPreferences("Secrets",MODE_PRIVATE);
                                String currenttoken=sharedPreferences.getString("token","");
                                //backend starts
                                ApiInterface apiInterface= RetrofitClient.getClient().create(ApiInterface.class);
                                Intent intent=getIntent();
                                String bkisbn=intent.getStringArrayExtra("ID_EXTRA")[0];
                                Sendcoverimgurlformat senddet=new Sendcoverimgurlformat(bkisbn,download_url);
                                Call<Getregisformat> c=apiInterface.changecover(senddet,currenttoken);
                                c.enqueue(new Callback<Getregisformat>() {
                                    @Override
                                    public void onResponse(Call<Getregisformat> call, Response<Getregisformat> response) {
                                        if(response.isSuccessful())
                                        {
                                            Toasty.success(particularbkdetails.this,"Successfully Uploaded Cover Image",Toasty.LENGTH_SHORT).show();

                                            Picasso.with(particularbkdetails.this).load(download_url).into(bkcoverimg);
                                        }
                                        else
                                        {
                                            Toasty.error(particularbkdetails.this,response.body().getMsg(),Toasty.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Getregisformat> call, Throwable t) {
                                        Toasty.error(particularbkdetails.this,t.getMessage(),Toasty.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }
                }
            });
            if (resultCode == RESULT_OK) {


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }






        }*/

    }
}
