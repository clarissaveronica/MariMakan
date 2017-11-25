package com.example.asus.marimakan.checkout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.marimakan.R;
import com.example.asus.marimakan.model.Dish;

import java.util.List;

/**
 * Created by ASUS on 11/13/2017.
 */

public class CheckoutAdapter extends ArrayAdapter<Dish> {

    int resource;
    Context context;

    public CheckoutAdapter(Context context, int resource, List<Dish> menuList){

        super(context, resource, menuList);
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        RelativeLayout itemView;
        final Dish dish = getItem(position);

        if(convertView == null){
            itemView = new RelativeLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(inflater);
            layoutInflater.inflate(resource, itemView, true);
        }
        else {
            itemView = (RelativeLayout) convertView;
        }

        TextView itemNumber = (TextView)itemView.findViewById(R.id.itemNumberCheckoutPage);
        TextView dishName = (TextView)itemView.findViewById(R.id.itemNameCheckoutPage);
        TextView dishQtyAndPrice = (TextView)itemView.findViewById(R.id.itemQtyAndPriceCheckoutPage);
        ImageView imageView = (ImageView)itemView.findViewById(R.id.dishImageCheckoutPage);

        imageView.setImageResource(dish.getImageId());
        dishQtyAndPrice.setText("Qty:"+dish.getQty() + " x Rp" + dish.getPrice() + " = Rp"+dish.getQty()*dish.getPrice());
        dishName.setText(dish.getName());
        itemNumber.setText(String.valueOf(dish.getItemId()));

        return itemView;
    }
}
