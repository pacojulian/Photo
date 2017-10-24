package com.example.pacod.proyecto.DB;

import java.util.UUID;

/**
 * Created by pacod on 14/10/2017.
 */

public class ContratoPedidos {

    interface ColumnasCabeceraPedido {
        String ID = "id";
        String FECHA = "fecha";
        String TOTAL="total";
        String ELEMENTOS="elemento";

    }

    interface ColumnasDetallePedido {
        String ID = "id";
        String NOMBRE_PRODUCTO = "nombre_producto";
        String PRECIO = "precio";
        String CANT="cant";
        String VALORES="valores";
        String FECHA = "fecha";
    }

    interface ColumnasProducto {
        String ID = "id";
        String NOMBRE = "nombre";
        String PRECIO = "precio";
        String PATH="path";

    }



    public static class CabecerasPedido implements ColumnasCabeceraPedido {
        public static String generarIdCabeceraPedido() {
            return "CP-" + UUID.randomUUID().toString();
        }
    }

    public static class DetallesPedido implements ColumnasDetallePedido {
        // Métodos auxiliares
    }

    public static class Productos implements ColumnasProducto{
        public static String generarIdProducto() {
            return "PRO-" + UUID.randomUUID().toString();
        }
    }






    private ContratoPedidos() {
    }

}