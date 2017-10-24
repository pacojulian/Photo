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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pacod.proyecto.DB.CabeceraPedido;
import com.example.pacod.proyecto.DB.DetallePedido;
import com.example.pacod.proyecto.DB.OperacionesBaseDatos;
import com.example.pacod.proyecto.DB.Producto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

public class cart extends AppCompatActivity {

    OperacionesBaseDatos datos;
    Toolbar toolbar;
    ListView lv_languages;
    private Spinner spinner;

    LanguagesListAdapter1 list_adapter1;
    private ArrayList<String> languages;
    private ArrayList<String> precio;
    private ArrayList<Product> producto;
    private List<Integer> language_images;


    private  int total;
    private  int elemento;


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
        total=0;
        elemento=0;
        spinner = (Spinner) findViewById(R.id.spinner);

        Intent intent = getIntent();
        String value = intent.getStringExtra("Nombre"); //if it's a string you stored.

        precio = new ArrayList<String>();


        languages = new ArrayList<String>();

        language_images = new ArrayList<Integer>();
        producto= new ArrayList<Product>();

        getApplicationContext().deleteDatabase("carrito1.db");
        datos = OperacionesBaseDatos
                .obtenerInstancia(getApplicationContext());


        String column1="";
        String column2="";
        String column3="";
        String column4="";
        String resultado="";
        List<String> nombre;
        List<String> p_spinner;
        nombre = new ArrayList<String>();
        p_spinner= new ArrayList<String>();

        try{
            datos.getDb().beginTransaction();
            Cursor c= datos.obtenerProductos();
            if(c.moveToFirst()){
                do{
                    //assing values
                    column1 = c.getString(1);
                    column2 = c.getString(2);

                    resultado=column1+"-"+column2;
                     nombre.add(resultado);
                    p_spinner.add(column2);
                   // producto.add(new Producto(null,column1,column2,null));

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
       /* Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();*/
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

                total=0;
                elemento=0;
                lv_languages.setAdapter(null);
                Toast.makeText(cart.this,
                        "Compra Realizada ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(cart.this,MainActivity.class);
                startActivity(intent);


            }
        });


        /*
        Calculamos el total
        * */

        final TextView tot=(TextView)findViewById(R.id.cart_total);
        final TextView elem=(TextView)findViewById(R.id.cart_elem);
        final EditText ed1 = (EditText) findViewById(R.id.appCompatEditText);
        tot.setText("$ 0");
        elem.setText("0 "+"Elementos");
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.add_producto);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = spinner.getSelectedItem().toString();

                int cantidad2=precioSpinner(text);
                String name=nombreSpinner(text);


                if(ed1.getText().toString().isEmpty())
                {

                Toast.makeText(cart.this,
                        "Error: debes de seleccionar una Cantidad ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int cantidad = Integer.parseInt(ed1.getText().toString());
                    total=total+(cantidad2*cantidad);
                    tot.setText("$ "+total);
                    elemento= elemento+1;
                    elem.setText(elemento+" Elementos");

                    //String fechaActual = Calendar.getInstance().toString();
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
                    String fechaActual = format1.format(cal.getTime());
                    Log.d("fecha",fechaActual);


                    languages.add(name);
                    precio.add(ed1.getText().toString()+" de "+String.valueOf(cantidad2));
                    list_adapter1.notifyDataSetChanged();
                    try{
                        datos.getDb().beginTransaction();

                        String producto=datos.insertarDetallePedido(new DetallePedido(null, name,cantidad2,cantidad,elemento,fechaActual));
                        String pedido= datos.insertarCabeceraPedido(new CabeceraPedido(null,fechaActual,total,elemento));
                        datos.getDb().setTransactionSuccessful();

                    /*DatabaseReference childRef = myRef.child(txt_nombre);
                    childRef.child("Cantidad").setValue(txt_cantidad);*/

                        Toast.makeText(cart.this,
                                "Los valores Guardados son: "+ pedido, Toast.LENGTH_LONG).show();

                    }finally {
                        datos.getDb().endTransaction();
                    }
                }


               /* Toast.makeText(cart.this,
                        "los valores son "+total, Toast.LENGTH_SHORT).show();*/
            }
        });




    }

    public int precioSpinner(String spinner){
        String nombre="";
        int  val=0;

        StringTokenizer st = new StringTokenizer(spinner,"-");
        while (st.hasMoreTokens()) {
            nombre =st.nextToken();
            val=Integer.parseInt(st.nextToken());


        }
        return val;
    }
    public String nombreSpinner(String spinner){
        String nombre="";
        int  val=0;

        StringTokenizer st = new StringTokenizer(spinner,"-");
        while (st.hasMoreTokens()) {
            nombre =st.nextToken();
            val=Integer.parseInt(st.nextToken());


        }
        return nombre;
    }

    private void init() {

;

        list_adapter1 = new LanguagesListAdapter1(cart.this,languages,precio);
        lv_languages = (ListView) findViewById(R.id.lv_languages1);

    }


}
