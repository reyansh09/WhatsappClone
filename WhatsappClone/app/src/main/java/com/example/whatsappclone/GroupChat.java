package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappclone.Adaptor.MassageAdapter;
import com.example.whatsappclone.Models.MessageModel;
import com.example.whatsappclone.databinding.ActivityGroupChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.file.attribute.GroupPrincipal;
import java.util.ArrayList;
import java.util.Date;

public class GroupChat extends AppCompatActivity {

    ActivityGroupChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(GroupChat.this,MainActivity.class);
                startActivity(intent);
            }
        });


        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final ArrayList<MessageModel> messageModels=new ArrayList<>();

        final String senderId= FirebaseAuth.getInstance().getUid();
        binding.userName.setText("Group Chat");
        final MassageAdapter adaptor= new MassageAdapter(messageModels,this);

        binding.chatRecyclerView.setAdapter(adaptor);

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        database.getReference().child("Group Chat")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataShot: snapshot.getChildren()){
                                    MessageModel model =dataShot.getValue(MessageModel.class);
                                    messageModels.add(model);
                                }
                                adaptor.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = binding.messageType.getText().toString();
                final MessageModel model= new MessageModel(senderId,message);
                model.setTimeStamp(new Date().getTime());
                binding.messageType.setText("");

                database.getReference().child("Group Chat").push().setValue(model)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(GroupChat.this, "Message send", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}