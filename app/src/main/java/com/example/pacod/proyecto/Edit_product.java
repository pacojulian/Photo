package com.example.pacod.proyecto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pacod.proyecto.DB.OperacionesBaseDatos;
import com.example.pacod.proyecto.DB.Producto;

public class Edit_product extends AppCompatActivity {
    OperacionesBaseDatos datos;

    private TextView txtnombre;
    private EditText ed_precio;
    private ImageView imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageview=(ImageView)findViewById(R.id.edit_image);

        getApplicationContext().deleteDatabase("carrito1.db");
        datos = OperacionesBaseDatos
                .obtenerInstancia(getApplicationContext());




        Intent intent = getIntent();
        final String v_nombre = intent.getStringExtra("Nombre");
        final String v_path = intent.getStringExtra("Path");

        txtnombre=(TextView)findViewById(R.id.edit_producto);
        ed_precio=(EditText)findViewById(R.id.txt_precio);

        txtnombre.setText(v_nombre);
        setSupportActionBar(toolbar);
        imageview.setImageURI(Uri.parse(v_path));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String s_precio= ed_precio.getText().toString();
                final int precio=Integer.parseInt(s_precio);


                try{
                    datos.getDb().beginTransaction();

                    Boolean producto=datos.actualizarProducto(new Producto(null,v_nombre,precio,null));
                    datos.getDb().setTransactionSuccessful();

                    /*DatabaseReference childRef = myRef.child(txt_nombre);
                    childRef.child("Cantidad").setValue(txt_cantidad);*/

                    Toast.makeText(Edit_product.this,
                            "se pudo editar?: "+ producto, Toast.LENGTH_LONG).show();
                }finally {
                    datos.getDb().endTransaction();
                }
            }
        });
    }

}
