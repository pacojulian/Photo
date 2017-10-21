package com.example.pacod.proyecto.DB;

/**
 * Created by pacod on 14/10/2017.
 */

public class Producto {

    public String idProducto;

    public String nombre,path;

    public float precio;

    public Producto(String idProducto, String nombre, float precio,String path) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.path= path;

    }

    public String getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPath() {
        return path;
    }

    public float getPrecio() {
        return precio;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}