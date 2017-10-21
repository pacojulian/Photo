package com.example.pacod.proyecto.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pacod on 14/10/2017.
 */

public final class OperacionesBaseDatos {

    private static BaseDatosPedidos baseDatos;

    private static OperacionesBaseDatos instancia = new OperacionesBaseDatos();

    private OperacionesBaseDatos() {
    }

    public static OperacionesBaseDatos obtenerInstancia(Context contexto) {
        if (baseDatos == null) {
            baseDatos = new BaseDatosPedidos(contexto);
        }
        return instancia;
    }



    public Cursor obtenerCabecerasPedidos() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        builder.setTables(CABECERA_PEDIDO_JOIN_CLIENTE_Y_FORMA_PAGO);

        return builder.query(db, proyCabeceraPedido, null, null, null, null, null);
    }

    private static final String CABECERA_PEDIDO_JOIN_CLIENTE_Y_FORMA_PAGO = "cabecera_pedido " +
            "INNER JOIN cliente " +
            "ON cabecera_pedido.id_cliente = cliente.id " +
            "INNER JOIN forma_pago " +
            "ON cabecera_pedido.id_forma_pago = forma_pago.id";


    private final String[] proyCabeceraPedido = new String[]{
            BaseDatosPedidos.Tablas.CABECERA_PEDIDO + "." + ContratoPedidos.CabecerasPedido.ID,
            ContratoPedidos.CabecerasPedido.FECHA,
            };




    public Cursor obtenerCabeceraPorId(String id) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String selection = String.format("%s=?", ContratoPedidos.CabecerasPedido.ID);
        String[] selectionArgs = {id};

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(CABECERA_PEDIDO_JOIN_CLIENTE_Y_FORMA_PAGO);

        String[] proyeccion = {
                BaseDatosPedidos.Tablas.CABECERA_PEDIDO + "." + ContratoPedidos.CabecerasPedido.ID,
                ContratoPedidos.CabecerasPedido.FECHA};

        return builder.query(db, proyeccion, selection, selectionArgs, null, null, null);
    }




    public String insertarCabeceraPedido(CabeceraPedido pedido) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        // Generar Pk
        String idCabeceraPedido = ContratoPedidos.CabecerasPedido.generarIdCabeceraPedido();

        ContentValues valores = new ContentValues();
        valores.put(ContratoPedidos.CabecerasPedido.generarIdCabeceraPedido(), idCabeceraPedido);
        valores.put(ContratoPedidos.CabecerasPedido.FECHA, pedido.fecha);

        // Insertar cabecera
        db.insertOrThrow(BaseDatosPedidos.Tablas.CABECERA_PEDIDO, null, valores);

        return idCabeceraPedido;
    }



    public boolean actualizarCabeceraPedido(CabeceraPedido pedidoNuevo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoPedidos.CabecerasPedido.FECHA, pedidoNuevo.fecha);


        String whereClause = String.format("%s=?", ContratoPedidos.CabecerasPedido.generarIdCabeceraPedido());
        String[] whereArgs = {pedidoNuevo.idCabeceraPedido};

        int resultado = db.update(BaseDatosPedidos.Tablas.CABECERA_PEDIDO, valores, whereClause, whereArgs);

        return resultado > 0;
    }


    public boolean eliminarCabeceraPedido(String idCabeceraPedido) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = ContratoPedidos.CabecerasPedido.generarIdCabeceraPedido() + "=?";
        String[] whereArgs = {idCabeceraPedido};

        int resultado = db.delete(BaseDatosPedidos.Tablas.CABECERA_PEDIDO, whereClause, whereArgs);

        return resultado > 0;
    }

    public Cursor obtenerDetallesPorIdPedido(String idCabeceraPedido) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s WHERE %s=?",
                BaseDatosPedidos.Tablas.DETALLE_PEDIDO, ContratoPedidos.CabecerasPedido.generarIdCabeceraPedido());

        String[] selectionArgs = {idCabeceraPedido};

        return db.rawQuery(sql, selectionArgs);

    }

    public String insertarDetallePedido(DetallePedido detalle) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoPedidos.DetallesPedido.ID, detalle.idCabeceraPedido);
        valores.put(ContratoPedidos.DetallesPedido.SECUENCIA, detalle.secuencia);
        valores.put(ContratoPedidos.DetallesPedido.ID_PRODUCTO, detalle.idProducto);
        valores.put(ContratoPedidos.DetallesPedido.PRECIO, detalle.precio);

        db.insertOrThrow(BaseDatosPedidos.Tablas.DETALLE_PEDIDO, null, valores);

        return String.format("%s#%d", detalle.idCabeceraPedido, detalle.secuencia);

    }



    public boolean actualizarDetallePedido(DetallePedido detalle) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoPedidos.DetallesPedido.SECUENCIA, detalle.secuencia);
        valores.put(ContratoPedidos.DetallesPedido.PRECIO, detalle.precio);

        String selection = String.format("%s=? AND %s=?",
                ContratoPedidos.DetallesPedido.ID_PRODUCTO, ContratoPedidos.DetallesPedido.SECUENCIA);
        final String[] whereArgs = {detalle.idCabeceraPedido, String.valueOf(detalle.secuencia)};

        int resultado = db.update(BaseDatosPedidos.Tablas.DETALLE_PEDIDO, valores, selection, whereArgs);

        return resultado > 0;
    }

    public boolean eliminarDetallePedido(String idCabeceraPedido, int secuencia) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String selection = String.format("%s=? AND %s=?",
                ContratoPedidos.DetallesPedido.ID_PRODUCTO, ContratoPedidos.DetallesPedido.SECUENCIA);
        String[] whereArgs = {idCabeceraPedido, String.valueOf(secuencia)};

        int resultado = db.delete(BaseDatosPedidos.Tablas.DETALLE_PEDIDO, selection, whereArgs);

        return resultado > 0;
    }




    public Cursor obtenerProductos() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s", BaseDatosPedidos.Tablas.PRODUCTO);

        return db.rawQuery(sql, null);
    }

    public String insertarProducto(Producto producto) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        // Generar Pk
       // String idProducto = ContratoPedidos.Productos.generarIdProducto();
        //valores.put(ContratoPedidos.Productos.ID, idProducto);
        valores.put(ContratoPedidos.Productos.NOMBRE, producto.nombre);
        valores.put(ContratoPedidos.Productos.PRECIO, producto.precio);
        valores.put(ContratoPedidos.Productos.PATH, producto.path);

        db.insertOrThrow(BaseDatosPedidos.Tablas.PRODUCTO, null, valores);

        return producto.nombre+"_"+producto.precio;

    }

    public boolean actualizarProducto(Producto producto) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(ContratoPedidos.Productos.NOMBRE, producto.nombre);
        valores.put(ContratoPedidos.Productos.PRECIO, producto.precio);


        String whereClause = String.format("%s=?", ContratoPedidos.Productos.NOMBRE);
        String[] whereArgs = {producto.nombre};

        int resultado = db.update(BaseDatosPedidos.Tablas.PRODUCTO, valores, whereClause, whereArgs);

        return resultado > 0;
    }
    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + BaseDatosPedidos.Tablas.PRODUCTO;

        SQLiteDatabase db = baseDatos.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.d("Constanza",cursor.getString(1));
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public boolean eliminarProducto(String Producto) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s=?", ContratoPedidos.Productos.NOMBRE);
        String[] whereArgs = {Producto};

        int resultado = db.delete(BaseDatosPedidos.Tablas.PRODUCTO, whereClause, whereArgs);

        return resultado > 0;
    }


    public SQLiteDatabase getDb() {
        return baseDatos.getWritableDatabase();
    }
}