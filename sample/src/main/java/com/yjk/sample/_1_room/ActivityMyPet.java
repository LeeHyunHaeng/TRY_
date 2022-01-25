package com.yjk.sample._1_room;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.yjk.sample.databinding.ActivityMypetBinding;

import java.util.List;

public class ActivityMyPet extends AppCompatActivity {

    private ActivityMypetBinding binding;
    private ActivityPetDatabase db;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        binding = ActivityMypetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = Room.databaseBuilder(this,ActivityPetDatabase.class,"petprofile").allowMainThreadQueries().build();

        fetchProfile();
    }

    public void fetchProfile() {
        List<ActivityPetProfile> petProfiles = db.activityPetProfileDao().getAll();

        String profileText = "사용자 목록";
        for(ActivityPetProfile profile : petProfiles){
            profileText += "\n" + profile.kind + "," + profile.name + "," + profile.age;
        }

        binding.petList.setText(profileText);
    }

    public void addProfile(View view){
        ActivityPetProfile profile = new ActivityPetProfile();
        profile.kind = binding.kind.getText().toString();
        profile.name = binding.name.getText().toString();
        profile.age = binding.age.getText().toString();

        db.activityPetProfileDao().insert(profile);
        fetchProfile();
    }
}
