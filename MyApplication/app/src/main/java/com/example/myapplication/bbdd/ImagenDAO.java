package com.example.myapplication.bbdd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.bbdd.entidades.Imagen;

public class ImagenDAO {

    public void insertarImagen(SQLiteDatabase bd,String rol, byte[] imagenBytes) {

        if (!imagenExiste(bd, rol)) {
            ContentValues values = new ContentValues();
            values.put(PreferenciasContract.Imagen.COLUMN_NAME_TITLE, rol);
            values.put(PreferenciasContract.Imagen.COLUMN_NAME_SUBTITLE, imagenBytes);
            bd.insert(PreferenciasContract.Imagen.TABLE_NAME, null, values);
        }
    }
    public boolean imagenExiste(SQLiteDatabase bd, String rol) {
        Cursor cursor = bd.rawQuery("SELECT COUNT(*) FROM Imagen WHERE rol = ?", new String[]{rol});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    // Obtener una imagen por rol
    public byte[] obtenerImagenPorRol(SQLiteDatabase bd,String rol) {

        byte[] imagenBytes = null;
        String[] projection = {
                PreferenciasContract.Imagen.COLUMN_NAME_SUBTITLE
        };
        String selection = PreferenciasContract.Imagen.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { rol };
        Cursor cursor = bd.query(
                PreferenciasContract.Imagen.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            imagenBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(PreferenciasContract.Imagen.COLUMN_NAME_SUBTITLE));
        }
        cursor.close();
        return imagenBytes;
    }
}
