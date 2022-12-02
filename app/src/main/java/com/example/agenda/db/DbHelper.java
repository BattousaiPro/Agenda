package com.example.agenda.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
   // Clase Ayudante Donde Se Crean Nuestras Elementos y Tablas ( No Utiliza Mucho Espacio )
public class DbHelper extends SQLiteOpenHelper { // Hereda Desde La Base De Datos

    private static final int DATABASE_VERSION = 2; // Depende De Los Cambios a realizar ( Se Controla La Vercion De La Data Base )
    private static final String DATABASE_NOMBRE = "agenda.db"; // Nombre De La Base De Datos
    public static final String TABLE_CONTACTOS = "t_contactos"; // Registra Diferentes Contactos

    public DbHelper(@Nullable Context context) { // Contructor
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION); // Nombre Base De Datos y La Variable De La Vercion
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { // Se Ejecuta Al Momento De Usar La Base De Datos

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CONTACTOS + "(" + // Concadenacion    <----- ( Tabla )
                "id INTEGER PRIMARY KEY AUTOINCREMENT," + // Campos Creados
                "nombre TEXT NOT NULL," + // Campos Creados
                "telefono TEXT NOT NULL," + // Campos Creados
                "correo_electronico TEXT)"); // Campos Creados
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { // Metodo, Se Ejecuta Segun La Vercion De La Base De Datos

        // Ejecucion Del Metodo
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_CONTACTOS); // Elimina La Tabla De Arriba y Agrega Los Nuevos Cambios ( Se Agregan Los Cambios De La Tabla )
        onCreate(sqLiteDatabase); // Crea Con Las Modificaciones a La Tabla

    }
}
