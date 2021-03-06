package com.example.pacod.proyecto.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.BaseColumns;

/**
 * Created by pacod on 14/10/2017.
 */

public class BaseDatosPedidos extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "carrito2.db";

    private static final int VERSION_ACTUAL = 5;

    private final Context contexto;

    interface Tablas {
        String CABECERA_PEDIDO = "cabecera_pedido";
        String DETALLE_PEDIDO = "detalle_pedido";
        String PRODUCTO = "producto";

    }

    interface Referencias {

        String ID_CABECERA_PEDIDO = String.format("REFERENCES %s(%s) ON DELETE CASCADE",
                Tablas.CABECERA_PEDIDO, ContratoPedidos.CabecerasPedido.generarIdCabeceraPedido());

        String ID_PRODUCTO = String.format("REFERENCES %s(%s)",
                Tablas.PRODUCTO, ContratoPedidos.Productos.generarIdProducto());




    }

    public BaseDatosPedidos(Context contexto) {
        super(contexto, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.contexto = contexto;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s DATETIME NOT NULL,%s INTEGER NOT NULL," +
                        "%s INTEGER NOT NULL)",
                Tablas.CABECERA_PEDIDO, BaseColumns._ID,
                ContratoPedidos.CabecerasPedido.FECHA,ContratoPedidos.CabecerasPedido.TOTAL,ContratoPedidos.CabecerasPedido.ELEMENTOS));

        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL,%s REAL NOT NULL ," +
                        "%s REAL NOT NULL,%s  REAL NOT NULL,%s DATETIME NOT NULL )",
                Tablas.DETALLE_PEDIDO, BaseColumns._ID,
                ContratoPedidos.DetallesPedido.NOMBRE_PRODUCTO,
                ContratoPedidos.DetallesPedido.PRECIO, ContratoPedidos.DetallesPedido.CANT,
                ContratoPedidos.DetallesPedido.VALORES,ContratoPedidos.DetallesPedido.FECHA));

        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL UNIQUE,%s REAL NOT NULL,%s TEXT NOT NULL)",
                Tablas.PRODUCTO, BaseColumns._ID,
                 ContratoPedidos.Productos.NOMBRE, ContratoPedidos.Productos.PRECIO,ContratoPedidos.Productos.PATH));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Tablas.CABECERA_PEDIDO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.DETALLE_PEDIDO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.PRODUCTO);


        onCreate(db);
    }


}