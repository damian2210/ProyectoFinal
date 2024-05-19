package com.example.myapplication.bbdd;

import android.provider.BaseColumns;

public class PreferenciasContract {

    private PreferenciasContract() {}



    public static class Ajustes implements BaseColumns {
        public static final String TABLE_NAME = "Ajustes";
        public static final String COLUMN_NAME_TITLE = "Tamaño";
        public static final String COLUMN_NAME_SUBTITLE = "Seleccionado";
    }

    public static class Imagen implements BaseColumns {
        public static final String TABLE_NAME = "Imagen";
        public static final String COLUMN_NAME_TITLE = "Rol";
        public static final String COLUMN_NAME_SUBTITLE = "Imagen";
    }


    public static final String SQL_CREATE_AJUSTES_ENTRIES =
            "CREATE TABLE " + Ajustes.TABLE_NAME + " (" +
                    Ajustes._ID + " INTEGER PRIMARY KEY," +
                    Ajustes.COLUMN_NAME_TITLE + " INTEGER," +
                    Ajustes.COLUMN_NAME_SUBTITLE + " INTEGER)";

    public static final String SQL_CREATE_IMAGEN_ENTRIES =
            "CREATE TABLE " + Imagen.TABLE_NAME + " (" +
                    Imagen._ID + " INTEGER PRIMARY KEY," +
                    Imagen.COLUMN_NAME_TITLE + " TEXT," +
                    Imagen.COLUMN_NAME_SUBTITLE + " BLOB)";

    public static final String SQL_DELETE_AJUSTES_ENTRIES =
            "DROP TABLE IF EXISTS " + Ajustes.TABLE_NAME;

    public static final String SQL_DELETE_IMAGEN_ENTRIES =
            "DROP TABLE IF EXISTS " + Imagen.TABLE_NAME;
}

