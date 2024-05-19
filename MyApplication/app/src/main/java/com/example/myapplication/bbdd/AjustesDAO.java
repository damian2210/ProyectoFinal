package com.example.myapplication.bbdd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.myapplication.bbdd.entidades.Ajustes;

import java.util.ArrayList;

public class AjustesDAO {

        public void insertarAjustes(SQLiteDatabase bd,ArrayList<Ajustes> ajustes) {
    ContentValues values = new ContentValues();
            for (Ajustes ajuste : ajustes) {
                values.put(PreferenciasContract.Ajustes.COLUMN_NAME_TITLE, ajuste.getTamaño());
                values.put(PreferenciasContract.Ajustes.COLUMN_NAME_SUBTITLE, ajuste.getSeleccionado() ? 1:0);
                bd.insert(PreferenciasContract.Ajustes.TABLE_NAME, null, values);
            }
}

        // Listar todos los ajustes
        public ArrayList<Ajustes> listarAjustes(SQLiteDatabase bd) {
            ArrayList<Ajustes> ajustesList = new ArrayList<>();

            String[] columns = {
                    PreferenciasContract.Ajustes._ID,
                    PreferenciasContract.Ajustes.COLUMN_NAME_TITLE,
                    PreferenciasContract.Ajustes.COLUMN_NAME_SUBTITLE
            };

            Cursor cursor = bd.query(
                    PreferenciasContract.Ajustes.TABLE_NAME,
                    columns,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(PreferenciasContract.Ajustes._ID));
                    int tamano = cursor.getInt(cursor.getColumnIndexOrThrow(PreferenciasContract.Ajustes.COLUMN_NAME_TITLE));
                    boolean seleccionado = cursor.getInt(cursor.getColumnIndexOrThrow(PreferenciasContract.Ajustes.COLUMN_NAME_SUBTITLE)) == 1;
                    ajustesList.add(new Ajustes((int) id, tamano, seleccionado));
                }
                cursor.close();
            }

            return ajustesList;
        }

        // Modificar todos los 'seleccionado' a false (0)
        public int deseleccionar(SQLiteDatabase bd) {
            ContentValues values = new ContentValues();
            values.put(PreferenciasContract.Ajustes.COLUMN_NAME_SUBTITLE, 0);

            String selection = PreferenciasContract.Ajustes.COLUMN_NAME_SUBTITLE + " = ?";
            String[] selectionArgs = { String.valueOf(1) };

            return bd.update(PreferenciasContract.Ajustes.TABLE_NAME, values, selection, selectionArgs);
        }

        // Modificar 'seleccionado' a true (1) para un ajuste específico por tamaño
        public int seleccionarPorTamaño(SQLiteDatabase bd,int tamaño) {
            ContentValues values = new ContentValues();
            values.put(PreferenciasContract.Ajustes.COLUMN_NAME_SUBTITLE, 1);

            String selection = PreferenciasContract.Ajustes.COLUMN_NAME_TITLE + " = ?";
            String[] selectionArgs = { String.valueOf(tamaño) };

            return bd.update(PreferenciasContract.Ajustes.TABLE_NAME, values, selection, selectionArgs);
        }

    public Ajustes obtenerAjusteSeleccionado(SQLiteDatabase bd) {

        Ajustes ajusteSeleccionado = null;

        String[] projection = {
                PreferenciasContract.Ajustes._ID,
                PreferenciasContract.Ajustes.COLUMN_NAME_TITLE,
                PreferenciasContract.Ajustes.COLUMN_NAME_SUBTITLE
        };

        String selection = PreferenciasContract.Ajustes.COLUMN_NAME_SUBTITLE + " = ?";
        String[] selectionArgs = { "1" };

        Cursor cursor = bd.query(
                PreferenciasContract.Ajustes.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            int tamaño = cursor.getInt(cursor.getColumnIndexOrThrow(PreferenciasContract.Ajustes.COLUMN_NAME_TITLE));
            boolean seleccionado = cursor.getInt(cursor.getColumnIndexOrThrow(PreferenciasContract.Ajustes.COLUMN_NAME_SUBTITLE)) == 1;

            ajusteSeleccionado = new Ajustes(tamaño, seleccionado);
        }

        cursor.close();


        return ajusteSeleccionado;
    }

    public void borrarTodosLosAjustes(SQLiteDatabase bd) {


        bd.delete(PreferenciasContract.Ajustes.TABLE_NAME, null, null);

    }
}
