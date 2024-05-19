package com.example.myapplication.bbdd;

import static com.example.myapplication.bbdd.PreferenciasContract.SQL_CREATE_AJUSTES_ENTRIES;
import static com.example.myapplication.bbdd.PreferenciasContract.SQL_CREATE_IMAGEN_ENTRIES;
import static com.example.myapplication.bbdd.PreferenciasContract.SQL_DELETE_AJUSTES_ENTRIES;
import static com.example.myapplication.bbdd.PreferenciasContract.SQL_DELETE_IMAGEN_ENTRIES;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PreferenciasHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Preferencias.db";

    public PreferenciasHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_AJUSTES_ENTRIES);
        db.execSQL(SQL_CREATE_IMAGEN_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_AJUSTES_ENTRIES);
        db.execSQL(SQL_DELETE_IMAGEN_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
