package com.example.pacod.proyecto.DB;

/**
 * Created by pacod on 14/10/2017.
 */

public class CabeceraPedido {

    public String idCabeceraPedido;

    public String fecha;

    public String idCliente;

    public String idFormaPago;

    public CabeceraPedido(String idCabeceraPedido, String fecha
                          ) {
        this.idCabeceraPedido = idCabeceraPedido;
        this.fecha = fecha;

    }
}