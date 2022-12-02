package com.example.agenda.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.agenda.entidades.Contactos;

import java.util.ArrayList;

public class DbContactos extends DbHelper { // Hereda Del DBHelper Para Manipular La Base De Datos

    // Declaracion
    Context context;

    // Contructor
    public DbContactos(@Nullable Context context) {
        super(context); // Asignacion
        this.context = context; // Se Iguala a La Del Contructor
    }

    public long insertarContacto(String nombre, String telefono, String correo_electronico) { // Resibe Parametros De La Tabla

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Funciones Para Incertar Los Registros
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);  // Parametros Nombre De La Columna y Se Iguala a Su Semejante
            values.put("telefono", telefono); // Parametros Nombre De La Columna y Se Iguala a Su Semejante
            values.put("correo_electronico", correo_electronico); // Parametros Nombre De La Columna y Se Iguala a Su Semejante

            id = db.insert(TABLE_CONTACTOS, null, values); // Solicita Nombre De La Tabla A Insertar y Hereda Del Helper
        } catch (Exception ex) {
            ex.toString(); // En Caso De Problemas No Se Detengan
        }

        return id;
    }

    public ArrayList<Contactos> mostrarContactos() { // Metodo Para Leer Contctos, ( Trae La Informacion De La Tabla )

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " ORDER BY nombre ASC", null);

        if (cursorContactos.moveToFirst()) { // Validacion
            do { // Pasa Al Cursor Al Primer Elemento o Al Primer Resultado De La Cosulta
                contacto = new Contactos(); // Fila Y Columna De Lo Que trae El Select
                contacto.setId(cursorContactos.getInt(0)); // Se Parte en Posicion 1
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setTelefono(cursorContactos.getString(2));
                contacto.setCorreo_electornico(cursorContactos.getString(3));
                listaContactos.add(contacto); // Trae La Informacion De Tipo Contacto Del Registro De La Tabla
            } while (cursorContactos.moveToNext());
        }

        cursorContactos.close();

        return listaContactos; // Termini De Metodo ArrayList De Tabla Contacto
    }

    public Contactos verContacto(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Contactos contacto = null;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorContactos.moveToFirst()) {
            contacto = new Contactos();
            contacto.setId(cursorContactos.getInt(0));
            contacto.setNombre(cursorContactos.getString(1));
            contacto.setTelefono(cursorContactos.getString(2));
            contacto.setCorreo_electornico(cursorContactos.getString(3));
        }

        cursorContactos.close();

        return contacto;
    }

    public boolean editarContacto(int id, String nombre, String telefono, String correo_electronico) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try { // Envios De Parametros Concatenados
            db.execSQL("UPDATE " + TABLE_CONTACTOS + " SET nombre = '" + nombre + "', telefono = '" + telefono + "', correo_electronico = '" + correo_electronico + "' WHERE id='" + id + "' "); // Se Valida Por ID
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally { // Siempre Va a Pasar Por Finally Pasando Por Alto Try o Casth
            db.close(); // Cierra Conexion Se Actualiza  o No
        }

        return correcto;
    }

    public boolean eliminarContacto(int id) { // Se Elimina Por ID

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_CONTACTOS + " WHERE id = '" + id + "'"); // Se Filta Por ID
            correcto = true; // Se HiZo Correctamente
        } catch (Exception ex) {
            ex.toString();
            correcto = false; // En Caso Que Se Presente Un Problema
        } finally {
            db.close();
        }

        return correcto; // Al Terminar Cerrar Base De Datos Y Verifique En El Booleano Si Es Correcto
    }
}