package com.example.pacod.proyecto;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.pacod.proyecto.DB.OperacionesBaseDatos;
import com.example.pacod.proyecto.DB.Producto;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class add_product extends AppCompatActivity {



    OperacionesBaseDatos datos;
    Uri pat;

    private String APP_DIRECTORY ="Camera/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY+"media";
    private String TEMPORAL_PICTURE_NAME ="Temporal.jpg";

    private final int PHOTO_CODE = 200;
    private final int RESULT_LOAD_IMAGE = 100;
    // private static int SELECT_PICTURE = 200;
    private static int SELECT_PICTURE = 1;
    private ImageView imageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
       //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //DATABASE
        getApplicationContext().deleteDatabase("carrito1.db");
        datos = OperacionesBaseDatos
                .obtenerInstancia(getApplicationContext());


        imageView =(ImageView)findViewById(R.id.add_image);


        //FireBase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("producto");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(add_product.this,
                        "Seleccionaste la imagen ", Toast.LENGTH_LONG).show();
            }
        });

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final PopupMenu popup = new PopupMenu(add_product.this, fab);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.show();//showing popup menu
            }
        });
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {


                switch (item.getItemId()){
                    case R.id.add_camera:
                       /* Toast.makeText(MainActivity.this,
                                "Seleccionaste: "+menuItem, Toast.LENGTH_LONG).show();*/
                        //openCamera();
                        dispatchTakePictureIntent();
                       /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,PHOTO_CODE);*/
                        return true;
                    case R.id.add_gallery:
                        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        //i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                        /*Toast.makeText(MainActivity.this,
                                "Seleccionaste: "+menuItem, Toast.LENGTH_LONG).show();*/
                        return true;


                }
                return false;
            }
        });
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        //fireBase Methods
        final EditText ed1 = (EditText) findViewById(R.id.campo_nombre);
        final EditText ed2 = (EditText) findViewById(R.id.campo_precio);

        final FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_nombre = ed1.getText().toString();
                String txt_precio = ed2.getText().toString();


                if(!txt_nombre.isEmpty()&&!txt_precio.isEmpty())
                {
                    try{
                        datos.getDb().beginTransaction();

                         String producto=datos.insertarProducto(new Producto(null, txt_nombre, Integer.parseInt(txt_precio),pat.toString()));
                        datos.getDb().setTransactionSuccessful();

                    /*DatabaseReference childRef = myRef.child(txt_nombre);
                    childRef.child("Cantidad").setValue(txt_cantidad);*/

                        Toast.makeText(add_product.this,
                                "Los valores Guardados son: "+ pat, Toast.LENGTH_LONG).show();

                    }finally {
                        datos.getDb().endTransaction();
                    }
                }

                else
                {
                    Toast.makeText(add_product.this,
                            "Error algun campo esta vacio ", Toast.LENGTH_SHORT).show();
                }

            }
        });

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
                 pat = FileProvider.getUriForFile(this,
                        "com.example.pacod.proyecto",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, pat);
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
        return image;
    }
    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        switch (RC){

            case RESULT_LOAD_IMAGE:


                pat = I.getData();
                String path;
                imageView.setImageURI(pat);
                //txturl.setText(pat.toString());
                //path=pat.toString();


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


                Bundle extras = I.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);

               // Picasso.with(this).load(txturl.getText().toString()).into(imageView);


                break;
        }
    }





}
