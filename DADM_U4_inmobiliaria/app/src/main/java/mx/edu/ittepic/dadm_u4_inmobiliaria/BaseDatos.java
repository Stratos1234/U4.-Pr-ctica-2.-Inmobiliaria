package mx.edu.ittepic.dadm_u4_inmobiliaria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class BaseDatos extends SQLiteOpenHelper { //super clase, interfaz de dbms pero se hace manual

    public BaseDatos( Context context,  String name,
                      SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //crear
        //Se ejeucuta cunado la aplicacion(Ejemplo ejercicio 1)se ejecuta en el CEL
        //Sirve para construir en el SQLite que esta  en el CEL las tablas que la APP requiere para funcionar
        db.execSQL("CREATE TABLE PROPIETARIO(ID INTEGER PRIMARY KEY NOT NULL ,NOMBRE VARCHAR(200),DOMICILIO VARCHAR(500),TELEFONO VARCHAR(50))"); //realizada todo excepto select, funcuina insert,create_table,delete,update
        db.execSQL("CREATE TABLE INMUEBLE (ID INTEGER PRIMARY KEY NOT NULL, DOMICILIO VARCHAR (200), PRECIOVENTA FLOAT, PRECIORENTA FLOAT, FECHATRANSACCION DATE,IDP INTEGER, FOREIGN KEY (IDP) REFERENCES PROPIETARIO(ID))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//actualizar,cuando se modifica la estructra de la tabla
        //las versiones, control de  version se empieza en 1
        //se ejecuta cuando el oncreate crea las tablas, toda la alteracion es el oncreate(actualizacion)
        //update actualizacion menor y upgrate es una actualizacion mas grande

    }
}





