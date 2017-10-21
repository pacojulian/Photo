package com.example.pacod.proyecto;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pacod.proyecto.DB.OperacionesBaseDatos;

import java.util.ArrayList;
import java.util.List;

public class cart extends AppCompatActivity {

    OperacionesBaseDatos datos;
    Toolbar toolbar;
    ListView lv_languages;
    private Spinner spinner;

    LanguagesListAdapter1 list_adapter1;
    private ArrayList<String> languages;
    private ArrayList<String> precio;
    private List<Integer> language_images;


    /*String[] languages = new String[] { "SQL",
            "JAVA",

    };*/

   /* public static int [] language_images={R.drawable.killers,
            R.drawable.killers,
         };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setTitle("Carrito");

        spinner = (Spinner) findViewById(R.id.spinner);

        Intent intent = getIntent();
        String value = intent.getStringExtra("Nombre"); //if it's a string you stored.

        precio = new ArrayList<String>();

        languages = new ArrayList<String>();

        language_images = new ArrayList<Integer>();

        getApplicationContext().deleteDatabase("carrito1.db");
        datos = OperacionesBaseDatos
                .obtenerInstancia(getApplicationContext());


        String column1="";
        List<String> nombre;
        nombre = new ArrayList<String>();

        try{
            datos.getDb().beginTransaction();
            Cursor c= datos.obtenerProductos();
            if(c.moveToFirst()){
                do{
                    //assing values

                    column1 = c.getString(1);

                     nombre.add(column1);

                }while(c.moveToNext());

            }
            c.close();
            //txturl.setText(resultado);

            //Log.d("Base de datos",resultado);
            datos.getDb().setTransactionSuccessful();
        }
        finally {
            datos.getDb().endTransaction();
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, nombre);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});

       

        init();
        lv_languages.setAdapter(list_adapter1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                languages.add("Chato");
                language_images.add(R.drawable.killers);
                precio.add("500");
                list_adapter1.notifyDataSetChanged();
                Toast.makeText(cart.this,
                        "Producto ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadSpinnerData() {


        // Spinner Drop down elements
        //List<String> lables = datos.getAllLabels();

        // Creating adapter for spinner
      /*  ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, nombre);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);*/
    }

    private void init() {

;

        list_adapter1 = new LanguagesListAdapter1(cart.this,languages,language_images,precio);
        lv_languages = (ListView) findViewById(R.id.lv_languages1);

    }


}
