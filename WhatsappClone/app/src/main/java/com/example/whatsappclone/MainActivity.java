package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.whatsappclone.Adaptor.FragmentAdaptor;
import com.example.whatsappclone.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();


        binding.viewPager.setAdapter(new FragmentAdaptor(getSupportFragmentManager()));

        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.setting){
            Intent intent2= new Intent(this, SettingActivity.class);
            startActivity(intent2);

        } else if (id==R.id.groupChat) {
            //Toast.makeText(this, "starting a group chat", Toast.LENGTH_SHORT).show();
            Intent intent1= new Intent(this, GroupChat.class);
            startActivity(intent1);

        } else if (id==R.id.logout) {

            auth.signOut();
            Intent intent= new Intent(this, LoginActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}