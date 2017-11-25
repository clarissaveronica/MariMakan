package com.example.asus.marimakan.orderHistory;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.marimakan.R;
import com.example.asus.marimakan.constants.Constants;
import com.example.asus.marimakan.database.DBHelper;
import com.example.asus.marimakan.homePage.MainActivity;
import com.example.asus.marimakan.model.Dish;
import com.example.asus.marimakan.model.Orders;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/13/2017.
 */

public class OrderHistoryActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ArrayList<Orders> ordersList;
    OrderHistoryAdapter orderHistoryAdapter;
    ListView orderHistoryListView;
    ArrayList<Dish> menuItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        Intent intent = getIntent();
        menuItemList = intent.getParcelableArrayListExtra(Constants.MENU_ITEM_LIST);

        dbHelper = new DBHelper(this);
        ordersList = dbHelper.getAllOrders();
        FragmentManager fragmentManager = getFragmentManager();
        OrderHistoryFragment orderHistoryFragment = (OrderHistoryFragment) fragmentManager.findFragmentById(R.id.OrderHistoryFragment);
        orderHistoryAdapter = new OrderHistoryAdapter(this, R.layout.order_history_fragment, ordersList);
        orderHistoryFragment.setListAdapter(orderHistoryAdapter);
        orderHistoryListView = orderHistoryFragment.getListView();
        orderHistoryAdapter.notifyDataSetChanged();

        Button backButton = (Button)findViewById(R.id.BackButtonHistoryPage);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBackButton();
            }
        });

        Button payBill = (Button)findViewById(R.id.PayBill);
        payBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickpayBillButton();
            }
        });

        TextView displayTotal = (TextView) findViewById(R.id.Total);
        displayTotal.setText("Total Amount = Rp" + calculateTotal());
    }

    public void onClickBackButton(){

        Intent intent = new Intent(this, MainActivity.class);
        intent.putParcelableArrayListExtra(Constants.MENU_ITEM_LIST, menuItemList);
        startActivity(intent);
    }

    public void onClickpayBillButton(){
        long num = dbHelper.numberOfRows();
        if (num != 0) {
            Intent intent = new Intent(this, MainActivity.class);
            Toast.makeText(getApplicationContext(), "Your Bill Will Be Delivered To You Shortly! Thank You and See You Later!", Toast.LENGTH_LONG).show();
            dbHelper.deleteDatabaseAndTable();
            startActivity(intent);
        }else Toast.makeText(getApplicationContext(), "Error : No Order Has Been Made Yet!", Toast.LENGTH_LONG).show();
    }

    private long calculateTotal() {
        long totalAmount = 0;
        for(int i=0; i<ordersList.size(); i++){
            Orders order = ordersList.get(i);;
            totalAmount += order.getTotalOrderAmount();
        }
        return totalAmount;
    }
}