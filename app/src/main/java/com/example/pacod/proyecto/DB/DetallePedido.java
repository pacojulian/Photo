package com.example.pacod.proyecto.DB;

/**
 * Created by pacod on 14/10/2017.
 */

public class DetallePedido {

    public String idCabeceraPedido;

    public int secuencia;

    public String idProducto;


    public float precio;

    public DetallePedido(String idCabeceraPedido, int secuencia,
                         String idProducto, float precio) {
        this.idCabeceraPedido = idCabeceraPedido;
        this.secuencia = secuencia;
        this.idProducto = idProducto;
        this.precio = precio;
    }
}