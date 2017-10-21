package com.example.pacod.proyecto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pacod.proyecto.DB.OperacionesBaseDatos;
import com.example.pacod.proyecto.DB.Producto;

import java.util.ArrayList;
import java.util.List;

public class Product extends AppCompatActivity {

    OperacionesBaseDatos datos;
    private TextView txtnombre,txtprecio;
    private TextView eliminar;
    private ImageView imageview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_product);
        setSupportActionBar(toolbar);


            /*
            BASE DE DATOS
            * */
        getApplicationContext().deleteDatabase("carrito1.db");
        datos = OperacionesBaseDatos
                .obtenerInstancia(getApplicationContext());






        txtnombre=(TextView)findViewById(R.id.product_nombre);
        imageview=(ImageView)findViewById(R.id.product_image);
        txtprecio=(TextView)findViewById(R.id.precio_product);
        FloatingActionButton btn_edit = (FloatingActionButton) findViewById(R.id.btn_edit);
        FloatingActionButton btn_delete = (FloatingActionButton) findViewById(R.id.btn_delete);
        Intent intent = getIntent();
        final String v_nombre = intent.getStringExtra("Nombre");
        final String v_precio = intent.getStringExtra("Precio");
        final String v_path = intent.getStringExtra("Path");
        txtnombre.setText(v_nombre);
        txtprecio.setText("$ "+v_precio);

        imageview.setImageURI(Uri.parse(v_path));

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(Product.this,
                        "Editar ", Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent(Product.this,Edit_product.class);
                intent.putExtra("Nombre",v_nombre);
                intent.putExtra("Path",v_path);
                startActivity(intent);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    datos.getDb().beginTransaction();

                    Boolean producto=datos.eliminarProducto(v_nombre);
                    datos.getDb().setTransactionSuccessful();

                    /*DatabaseReference childRef = myRef.child(txt_nombre);
                    childRef.child("Cantidad").setValue(txt_cantidad);*/

                    Toast.makeText(Product.this,
                            "se pudo eliminar?: "+ producto, Toast.LENGTH_LONG).show();
                }finally {
                    datos.getDb().endTransaction();
                }
            }
        });


    }




}
