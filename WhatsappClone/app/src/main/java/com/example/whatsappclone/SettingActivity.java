package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappclone.Models.Users;
import com.example.whatsappclone.databinding.ActivitySettingBinding;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import okhttp3.internal.cache.DiskLruCache;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;

    FirebaseAuth auth;
    FirebaseDatabase database;

    FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();


        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.etstatus.getText().toString().equals("") && !binding.etUsername.getText().toString().equals("")){
                    String status= binding.etstatus.getText().toString();
                    String username= binding.etUsername.getText().toString();

                    HashMap<String,Object> obj=new HashMap<>();
                    obj.put("userName",username);
                    obj.put("status",status);

                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).updateChildren(obj);
                    Toast.makeText(SettingActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                }


            }
        });

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Users users= snapshot.getValue(Users.class);
                                Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.avatar).into(binding.profilePic);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,25);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData()!=null){
            Uri sFile= data.getData();
            binding.profilePic.setImageURI(sFile);

            final StorageReference reference= storage.getReference().child("ProfilePic")
                    .child(FirebaseAuth.getInstance().getUid());

            reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("ProfilePic").setValue(uri.toString());
                        }
                    });
                }
            });
        }
    }
}