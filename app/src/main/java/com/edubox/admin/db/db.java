package com.edubox.admin.db;

import android.content.Context;

import androidx.room.Room;

public class db {
    private static roomDb roomDb;
    private Context context;

    public db() {

    }

    public db(Context context){
        this.context = context;
    }

    public roomDb db(Context context){
        this.context = context;
        roomDb = getRoomDb(context);
        return roomDb;
    }
    public static roomDb getRoomDb(Context context){

        if (roomDb==null){
            roomDb = Room.databaseBuilder(context, roomDb.class,"eduBoxRoom").build();
        }

        return roomDb;
    }
}
