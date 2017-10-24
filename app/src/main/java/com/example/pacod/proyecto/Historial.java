package com.example.pacod.proyecto;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.pacod.proyecto.DB.OperacionesBaseDatos;

import java.util.ArrayList;

public class Historial extends AppCompatActivity {

    OperacionesBaseDatos datos;

    private ArrayList<String> fecha;
    private ArrayList<String> elemento;
    ListView lv_historial;
    historialAdapter list_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Historial de Compra");
        getApplicationContext().deleteDatabase("carrito1.db");
        datos = OperacionesBaseDatos
                .obtenerInstancia(getApplicationContext());


        fecha = new ArrayList<String>();
        //fecha.add("17/09/2017");
         elemento= new ArrayList<String>();
        //elemento.add("4 elementos");

        obtener_historial();
        init();
        lv_historial.setAdapter(list_adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void obtener_historial(){
        String column1="";
        String column2="";
        String column3="";
        String column4="";
        String resultado="";
        try{
            datos.getDb().beginTransaction();
            Cursor c= datos.obtenerHistorial();
            if(c.moveToFirst()){
                do{
                    //assing values
                    column1 = c.getString(0);
                    column2 = c.getString(1);
                    column3 = c.getString(2);
                    column4 = c.getString(3);
                    resultado=column1+"_"+column2+"_"+column3+"_"+column4+"_"+resultado;
                    //Do something Here with values


                }while(c.moveToNext());

            }
            c.close();
            //txturl.setText(resultado);
            Datos_Pedido(resultado);
            Log.d("Base de datos",resultado);
            datos.getDb().setTransactionSuccessful();
        }
        finally {
            datos.getDb().endTransaction();
        }

    }
    private void init() {



        list_adapter = new historialAdapter(this,fecha,elemento);
        lv_historial = (ListView) findViewById(R.id.lv_historial);
    }

    private void Datos_Pedido(String datos)
    {

        String Id="";
        String s_fecha="";
        String s_elementos="";
        String total="";
        int z =4;
        String[] result = datos.split("_");
        int tamaño=((result.length)/z);
        Log.d("Prueba string",String.valueOf(tamaño));
        for (int x=0; x<tamaño; x++){
            Id=result[0+x*z];
            s_fecha=result[1+x*z];
            total=result[2+x*z];
            s_elementos=result[3+x*z];
            //System.out.println(Id);

            Log.d("TOTAL",s_elementos);
            //fecha.add("17/09/2017");
            //elemento.add("4 elementos");
            fecha.add(s_fecha);
            elemento.add(s_elementos);
            //list_adapter.notifyDataSetChanged();


        }

        // txturl.setText(Id);

    }

}
