package com.example.pacod.proyecto;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.internal.NavigationMenu;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.squareup.picasso.Picasso;

import com.example.pacod.proyecto.DB.OperacionesBaseDatos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.Manifest;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class MainActivity extends AppCompatActivity {

    OperacionesBaseDatos datos;

    private String APP_DIRECTORY ="Camera/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY+"media";
    private String TEMPORAL_PICTURE_NAME ="Temporal.jpg";

    private final int PHOTO_CODE = 200;
    private final int RESULT_LOAD_IMAGE = 100;
   // private static int SELECT_PICTURE = 200;
   private static int SELECT_PICTURE = 1;
    private ImageView imageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;





    //RecyclerView And Cardview

    ListView lv_languages;
    LanguagesListAdapter list_adapter;
    private ArrayList<String> languages;
    private ArrayList<String> precio;
    private ArrayList<String> Path;



    //String[] languages = new String[] { "SQL"};

    //public static int [] language_images={R.drawable.cart};



    TextView txturl;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carrito, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:
              /*Toast.makeText(MainActivity.this,
                        "Seleccionaste: "+item, Toast.LENGTH_LONG).show();*/

                Intent intent = new Intent(MainActivity.this,cart.class);
                startActivity(intent);
                return true;
            case R.id.historial:
                Intent intent2 = new Intent(MainActivity.this,Historial.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String column1="";
        String column2="";
        String column3="";
        String column4="";
        String resultado="";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.fab_speed_dial);
        imageView =(ImageView)findViewById(R.id.image1);
        txturl=(TextView)findViewById(R.id.txt_url);

        getApplicationContext().deleteDatabase("carrito1.db");
        datos = OperacionesBaseDatos
                .obtenerInstancia(getApplicationContext());


        languages = new ArrayList<String>();
        precio = new ArrayList<String>();
        Path = new ArrayList<String>();

        //String value = "SQL";
        //languages.add(value);


        init();
        lv_languages.setAdapter(list_adapter);




        try{
         datos.getDb().beginTransaction();
           // Boolean chato =datos.eliminarTablaPedido();
            //Log.d("Eliminar",chato.toString());
         Cursor c= datos.obtenerProductos();
         if(c.moveToFirst()){
             do{
                 //assing values
                 column1 = c.getString(0);
                 column2 = c.getString(1);
                 column3 = c.getString(2);
                 column4= c.getString(3);
                 resultado=column1+"#"+column2+"#"+column3+"#"+column4+"#"+resultado;
                 //Do something Here with values


             }while(c.moveToNext());

         }
         c.close();
         //txturl.setText(resultado);
         Datos_Producto(resultado);
            Log.d("Base de datos",resultado);
         datos.getDb().setTransactionSuccessful();
     }
     finally {
         datos.getDb().endTransaction();
     }


       /* Toast.makeText(MainActivity.this,
                "HOLA "+ c, Toast.LENGTH_SHORT).show();*/




        /*
        * TOOLBAR
        * */



       // String prueba="content://com.google.andorid.apps.photos.contentprovider/0/1content%3A%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F7010/ORIGINAL/NONE/1067974336";
        String prueba="Drawable/add.png";
        Bitmap imageBitmap = (Bitmap) BitmapFactory.decodeFile(prueba);
        imageView.setImageBitmap(imageBitmap);





/*
* Friebase
* */
        DatabaseReference ref =FirebaseDatabase.getInstance().getReference();
        DatabaseReference mensajeRef= ref.child("producto");


        mensajeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String value = dataSnapshot.child("Cantidad").getValue(String.class);

                //languages.add(value);
                //txturl.setText(value);
                /*Toast.makeText(MainActivity.this,
                        "El valor es: "+value, Toast.LENGTH_LONG).show();
                //list_adapter.notifyDataSetChanged();*/



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*
        * RecylcerView
        * */


        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                // TODO: Do something with yout menu items, or return false if you don't want to show them
                return true;
            }
        });


        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                //TODO: Start some activity
                /*Toast.makeText(MainActivity.this,
                        "Seleccionaste: "+menuItem, Toast.LENGTH_LONG).show();*/

                switch (menuItem.getItemId()){

                   /* case R.id.menu_foto:
                       /* Toast.makeText(MainActivity.this,
                                "Seleccionaste: "+menuItem, Toast.LENGTH_LONG).show();
                       //openCamera();
                        dispatchTakePictureIntent();
                       /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,PHOTO_CODE);
                        return true;


                    case R.id.menu_gallery:
                        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                        /*Toast.makeText(MainActivity.this,
                                "Seleccionaste: "+menuItem, Toast.LENGTH_LONG).show();
                        return true;*/
                    case R.id.menu_add:
                             /*String prueba= (String) txturl.getText();

                        imageView.setImageURI(Uri.parse(prueba));
                        Toast.makeText(MainActivity.this,
                                "Seleccionaste: "+prueba, Toast.LENGTH_LONG).show();*/
                        Intent intent = new Intent(MainActivity.this,add_product.class);
                        startActivity(intent);

                        /*Toast.makeText(MainActivity.this,
                                "HOLA: ", Toast.LENGTH_LONG).show();*/

                        break;

                }



                return false;
            }
        });


    }



    private void init() {


        getSupportActionBar().setTitle("Lista de Productos");
        list_adapter = new LanguagesListAdapter(this,languages, precio,Path);
        lv_languages = (ListView) findViewById(R.id.lv_languages);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.pacod.proyecto",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE );
            }
        }
    }
    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file://"+image.getAbsolutePath();
     txturl.setText(mCurrentPhotoPath);
        return image;
    }

  /*  private void openCamera() {

        /*

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(newfile));
        startActivityForResult(intent,PHOTO_CODE);*/
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       // startActivityForResult(intent,PHOTO_CODE);







    //}



    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        switch (RC){

            case RESULT_LOAD_IMAGE:


             Uri pat = I.getData();
                String path;
                imageView.setImageURI(pat);
                txturl.setText(pat.toString());
                path=pat.toString();


               /*try {
                    ExifInterface exif = new ExifInterface(path);
                    exif.getAttribute(ExifInterface.TAG_DATETIME);
                } catch (IOException e) {

                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,
                            "error", Toast.LENGTH_LONG).show();
                }


                Toast.makeText(MainActivity.this,
                        "La ruta"+pat, Toast.LENGTH_LONG).show();*/


                break;

            /*case  PHOTO_CODE:

                Bitmap bitmap =(Bitmap)I.getExtras().get("data");
                imageView.setImageBitmap(bitmap);


                break;*/

            case REQUEST_IMAGE_CAPTURE:
               /* Bundle extras = I.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
*/
                Picasso.with(this).load(txturl.getText().toString()).into(imageView);


                break;
        }
    }

    private void Datos_Producto(String datos)
    {
        String Id="";
        String Nombre="";
        String Precio="";
        String path="";
        int z =4;
        String[] result = datos.split("#");
        int tamaño=((result.length)/z);

        for (int x=0; x<tamaño; x++){
            Id=result[0+x*z];
            Nombre=result[1+x*z];
            Precio=result[2+x*z];
            path=result[3+x*z];
            System.out.println(Id);

            languages.add(Nombre);
            precio.add(Precio);
            Path.add(path);
            list_adapter.notifyDataSetChanged();


        }

       // txturl.setText(Id);

    }



}
