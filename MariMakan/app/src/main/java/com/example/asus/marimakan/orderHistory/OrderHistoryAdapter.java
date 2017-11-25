package com.example.asus.marimakan.orderHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.marimakan.R;
import com.example.asus.marimakan.model.Orders;

import java.util.List;

/**
 * Created by ASUS on 11/13/2017.
 */

public class OrderHistoryAdapter extends ArrayAdapter<Orders> {

    Context context;
    int resource;

    public OrderHistoryAdapter(Context context, int resource, List<Orders> ordersList){

        super(context, resource, ordersList);
        this.context = context;
        this.resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        RelativeLayout ordersView;
        final Orders orders = getItem(position);

        if(convertView == null){
            ordersView = new RelativeLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(inflater);
            layoutInflater.inflate(resource, ordersView, true);
        }
        else {
            ordersView = (RelativeLayout) convertView;
        }

        TextView displayOrderItems = (TextView)ordersView.findViewById(R.id.ItemIdsHistoryPage);
        TextView displayOrderDate = (TextView)ordersView.findViewById(R.id.DateOfOrderHistoryPage);
        TextView displayOrderAmount = (TextView)ordersView.findViewById(R.id.TotalOrderAmountHistoryPage);
        TextView displayOrderItemNames = (TextView)ordersView.findViewById(R.id.ItemNamesHistoryPage);

        displayOrderDate.setText("Date : "+ orders.getDate());
        displayOrderItems.setText("Item Ids : "+ orders.getItemIds());
        displayOrderAmount.setText("Amount : Rp"+ String.valueOf(orders.getTotalOrderAmount()));
        displayOrderItemNames.setText("Items : " + orders.getNames());

        return ordersView;
    }
}