package com.example.pacod.proyecto.DB;

/**
 * Created by pacod on 14/10/2017.
 */

public class CabeceraPedido {

    public String idCabeceraPedido;

    public String fecha;

    public String idCliente;

    public String idFormaPago;

    float total;
    int element;

    public CabeceraPedido(String idCabeceraPedido, String fecha,float total, int element
                          ) {
        this.idCabeceraPedido = idCabeceraPedido;
        this.fecha = fecha;
        this.total= total;
        this.element=element;

    }
}