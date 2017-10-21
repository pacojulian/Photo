package com.example.pacod.proyecto;

/**
 * Created by pacod on 12/10/2017.
 */
import android.content.Context;
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
import java.util.List;


public class LanguagesListAdapter1 extends BaseAdapter{
    ArrayList<String> result;
    ArrayList<String> result1;
    Context context;
    List<Integer> imageId;
    private static LayoutInflater inflater=null;

    public LanguagesListAdapter1(cart mainActivity, ArrayList<String> prgmNameList, List<Integer> prgmImages, ArrayList<String> list_precio) {
// TODO Auto-generated constructor stub
        result=prgmNameList;
        result1=list_precio;
        context=mainActivity;
        imageId=prgmImages;
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

        TextView tv_language1;
        TextView v_precio;
        ImageView im_language;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
        Holder holder=new Holder();
        View view;
        view = inflater.inflate(R.layout.layout_language_list_item1, null);

        holder.tv_language1=(TextView) view.findViewById(R.id.tv_language1);
        holder.v_precio=(TextView) view.findViewById(R.id.tv_language2);
        holder.im_language=(ImageView) view.findViewById(R.id.im_language1);

        holder.tv_language1.setText(result.get(position));
        holder.v_precio.setText("$ "+result1.get(position));

        Picasso.with(context).load(imageId.get(position)).into(holder.im_language);


        double total= getTotal(result1);
        TextView txt_total=(TextView) view.findViewById(R.id.cart_total);
       //txt_total.setText("$ 9,000.00");

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + result.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    public double getTotal(ArrayList<String> list){

        double total=0.0;
        for(int i=0;i<list.size();i++){
            total=total+Double.parseDouble(list.get(i));
        }
        return total;
    }

}