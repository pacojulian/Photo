package com.example.pacod.proyecto;

/**
 * Created by pacod on 09/10/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class LanguagesListAdapter extends BaseAdapter{
    ArrayList<String> result;
    ArrayList<String> result2;
    ArrayList<String> result3;
    Context context;
    ArrayList<String>  imageId;
    private static LayoutInflater inflater=null;

    public LanguagesListAdapter(MainActivity mainActivity, ArrayList<String> prgmNameList,ArrayList<String>  prgmImages,ArrayList<String>  string_path) {
// TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        result2=prgmImages;
        result3=string_path;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
// TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
// TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
// TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv_language;
        TextView tv_precio;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
        Holder holder=new Holder();
        final View view;
        view = inflater.inflate(R.layout.layout_language_list_item, null);

        holder.tv_language=(TextView) view.findViewById(R.id.tv_language);
        holder.tv_language.setText("PRODUCTO: "+result.get(position));

        holder.tv_precio=(TextView) view.findViewById(R.id.tv_precio);
        holder.tv_precio.setText("PRECIO: "+result2.get(position));


        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                String nombre= result.get(position).toString();
                String Precio= result2.get(position).toString();
                String path= result3.get(position).toString();
                Toast.makeText(context, "You Clicked " +result.get(position), Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(context, Product.class);
                //intent.putextra("your_extra","your_class_value");
                intent.putExtra("Nombre",nombre);
                intent.putExtra("Precio",Precio);
                intent.putExtra("Path",path);
                context.startActivity(intent);
            }
        });
        return view;
    }

}