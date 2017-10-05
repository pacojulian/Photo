package com.example.pacod.proyecto;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.internal.NavigationMenu;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.squareup.picasso.Picasso;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.jar.Manifest;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class MainActivity extends AppCompatActivity {
    private String APP_DIRECTORY ="Camera/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY+"media";
    private String TEMPORAL_PICTURE_NAME ="Temporal.jpg";

    private final int PHOTO_CODE = 200;
    private final int RESULT_LOAD_IMAGE = 100;
   // private static int SELECT_PICTURE = 200;
   private static int SELECT_PICTURE = 1;
    private ImageView imageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    TextView txturl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.fab_speed_dial);
        imageView =(ImageView)findViewById(R.id.image1);
        txturl=(TextView)findViewById(R.id.txt_url);

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

                    case R.id.menu_foto:
                       /* Toast.makeText(MainActivity.this,
                                "Seleccionaste: "+menuItem, Toast.LENGTH_LONG).show();*/
                       //openCamera();
                        dispatchTakePictureIntent();
                       /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,PHOTO_CODE);*/
                        return true;


                    case R.id.menu_gallery:
                        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                        /*Toast.makeText(MainActivity.this,
                                "Seleccionaste: "+menuItem, Toast.LENGTH_LONG).show();*/
                        return true;

                }



                return false;
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


               /* try {
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



}
