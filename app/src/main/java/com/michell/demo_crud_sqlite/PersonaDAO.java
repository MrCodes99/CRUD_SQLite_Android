package com.michell.demo_crud_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MICHELL on 27/06/2017.
 */

public class PersonaDAO {
    private final String TABLE = "Persona";
    private final String COL_ID = "id";
    private final String COL_NOMBRE = "nombre";
    private final String COL_APELLIDO = "apellido";
    private final String COL_EDAD = "edad";
    private final String COL_DOCUMENTO = "documento";
    private final String COL_TELEFONO = "telefono";
    private final String COL_DISPONIBLE = "disponible";
    private Context mContext;

    public PersonaDAO(Context context) {
        mContext = context;
    }

    public Persona getFirstPerson() {
        Persona persona = null;
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
            Cursor cursor = dataBaseHelper.openDataBase().query(TABLE, null, null, null, null, null, null, "1");
            if (cursor.moveToFirst())
                persona = transformarCursorAPersona(cursor);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return persona;
    }

    public Persona getPersonById(int id) {
        Persona persona = null;
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
            Cursor cursor = dataBaseHelper.openDataBase().query(TABLE, null, COL_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor.moveToFirst())
                persona = transformarCursorAPersona(cursor);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return persona;
    }

    public List<Persona> listPersonas() {
        List<Persona> lstPersona = new ArrayList<>();
        Persona persona = null;
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(mContext);
            Cursor cursor = dataBaseHelper.openDataBase().query(TABLE, null, null, null, null, null, null);
            if (cursor.moveToFirst())
                do {
                    lstPersona.add(transformarCursorAPersona(cursor));
                } while (cursor.moveToNext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return lstPersona;
    }

    public long insert(Persona persona) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOMBRE, persona.getNombre());
        contentValues.put(COL_APELLIDO, persona.getApellido());
        contentValues.put(COL_EDAD, persona.getEdad());
        contentValues.put(COL_DOCUMENTO, persona.getDocumento());
        contentValues.put(COL_TELEFONO, persona.getTelefono());
        contentValues.put(COL_DISPONIBLE, persona.isDisponible() ? 1 : 0);

        return new DataBaseHelper(mContext)
                .openDataBase()
                .insert(TABLE, null, contentValues);
    }

    public void update(Persona persona) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOMBRE, persona.getNombre());
        contentValues.put(COL_APELLIDO, persona.getApellido());
        contentValues.put(COL_EDAD, persona.getEdad());
        contentValues.put(COL_DOCUMENTO, persona.getDocumento());
        contentValues.put(COL_TELEFONO, persona.getTelefono());
        contentValues.put(COL_DISPONIBLE, persona.isDisponible() ? 1 : 0);

        new DataBaseHelper(mContext)
                .openDataBase()
                .update(TABLE, contentValues, COL_ID + " = ?",new String[]{String.valueOf(persona.getId())});
    }

    public void delete(Persona persona) {
        new DataBaseHelper(mContext)
                .openDataBase()
                .delete(TABLE, COL_ID + " = ?",new String[]{String.valueOf(persona.getId())});
    }

    private Persona transformarCursorAPersona(Cursor cursor) {
        Persona persona = new Persona();
        persona.setId(cursor.isNull(cursor.getColumnIndex(COL_ID)) ? "" : cursor.getString(cursor.getColumnIndex(COL_ID)));
        persona.setNombre(cursor.isNull(cursor.getColumnIndex(COL_NOMBRE)) ? "" : cursor.getString(cursor.getColumnIndex(COL_NOMBRE)));
        persona.setApellido(cursor.isNull(cursor.getColumnIndex(COL_APELLIDO)) ? "" : cursor.getString(cursor.getColumnIndex(COL_APELLIDO)));
        persona.setEdad(cursor.isNull(cursor.getColumnIndex(COL_EDAD)) ? 0 : cursor.getInt(cursor.getColumnIndex(COL_EDAD)));
        persona.setTelefono(cursor.isNull(cursor.getColumnIndex(COL_TELEFONO)) ? "" : cursor.getString(cursor.getColumnIndex(COL_TELEFONO)));
        persona.setDocumento(cursor.isNull(cursor.getColumnIndex(COL_DOCUMENTO)) ? "" : cursor.getString(cursor.getColumnIndex(COL_DOCUMENTO)));
        persona.setDisponible(cursor.isNull(cursor.getColumnIndex(COL_DISPONIBLE)) ? false : cursor.getInt(cursor.getColumnIndex(COL_DISPONIBLE)) > 0);
        return persona;
    }
}

