package com.yjk.sample._1_room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class ActivityPetProfile {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String kind, name, age;
}
