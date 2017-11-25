package com.example.asus.marimakan.homePage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.marimakan.R;
import com.example.asus.marimakan.model.Dish;

import java.util.List;

/**
 * Created by ASUS on 11/13/2017.
 */

public class MenuAdapter extends ArrayAdapter<Dish> {

    int resource;
    Context context;

    public MenuAdapter(Context context, int resource, List<Dish> menuList){

        super(context, resource, menuList);
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LinearLayout dishView;
        final Dish dish = getItem(position);

        String name = dish.getName();
        long price = dish.getPrice();
        int qty = dish.getQty();
        String qtyPrice = "Qty:" + qty + " x Rp" + price;
        int itemId = dish.getItemId();
        int imageId = dish.getImageId();

        if(convertView == null){
            dishView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(inflater);
            layoutInflater.inflate(resource, dishView, true);
        }else {
            dishView = (LinearLayout) convertView;
        }

        TextView countView = (TextView)dishView.findViewById(R.id.itemNumber);
        TextView dishName = (TextView)dishView.findViewById(R.id.itemName);
        TextView dishQtyAndPrice = (TextView)dishView.findViewById(R.id.itemQtyAndPrice);

        ImageView imageView = (ImageView)dishView.findViewById(R.id.dishImage);
        imageView.setImageResource(imageId);

        View view = dishView.findViewById(R.id.detailsButtonHomePage);
        Button qtyButton = (Button)dishView.findViewById(R.id.detailsButtonHomePage);
        qtyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).onClickQtyButton(position);
            }
        });

        dishQtyAndPrice.setText(qtyPrice);
        dishName.setText(name);
        countView.setText(String.valueOf(itemId));

        return dishView;
    }
}

