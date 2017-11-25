package com.example.asus.marimakan.calculateTotal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.asus.marimakan.constants.Constants;
import com.example.asus.marimakan.model.Dish;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/13/2017.
 */

public class CalculateTotalActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        long total = calculateTotal(intent.getParcelableArrayListExtra(Constants.MENU_ITEM_LIST));
        intent.putExtra(Constants.TOTAL_AMOUNT, total);
        setResult(RESULT_OK,intent);
        finish();
    }

    private long calculateTotal(ArrayList<Parcelable> menuList) {
        long totalAmount = 0;
        for(int i=0; i<menuList.size(); i++){
            Dish dish = (Dish) menuList.get(i);
            totalAmount = totalAmount + dish.getPrice() * dish.getQty();
        }
        return totalAmount;
    }
}
