package com.example.pacod.proyecto.DB;

/**
 * Created by pacod on 14/10/2017.
 */

public class DetallePedido {

    public String idCabeceraPedido,nombre,fecha;


    public String idProducto;



    public  int cant,element, precio;
    public DetallePedido(String idCabeceraPedido,String nombre,
                         int precio,int cant,int element,String fecha)
    {
        this.idCabeceraPedido = idCabeceraPedido;
        this.nombre= nombre;
        this.precio = precio;
        this.cant = cant;
        this.element=element;
        this.fecha=fecha;

    }


}
