package com.example.asus.marimakan.productPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.marimakan.R;
import com.example.asus.marimakan.constants.Constants;
import com.example.asus.marimakan.homePage.MainActivity;
import com.example.asus.marimakan.model.Dish;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/13/2017.
 */

public class ItemDetailsActivity extends AppCompatActivity {

    int quantity;
    int position;
    ArrayList<Dish> dishList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        TextView nameDetailsPage = (TextView) findViewById(R.id.NameDetailsPage);
        TextView priceDetailsPage = (TextView) findViewById(R.id.PriceDetailsPage);
        TextView descriptionDetailsPage = (TextView) findViewById(R.id.DescriptionDetailsPage);
        ImageView imageView = (ImageView) findViewById(R.id.ImageDetailsPage);
        final EditText editText = (EditText) findViewById(R.id.EditQtyDetailsPage);

        Intent intent = getIntent();
        dishList = intent.getParcelableArrayListExtra(Constants.MENU_ITEM_LIST);
        position = intent.getIntExtra(Constants.MENU_ITEM_POS,0);
        String name = intent.getStringExtra(Constants.MENU_NAME);
        int imageId = intent.getIntExtra(Constants.MENU_IMAGE_ID,0);
        long price = intent.getLongExtra(Constants.MENU_PRICE,0);
        quantity = intent.getIntExtra(Constants.MENU_ITEM_QTY,0);
        String description = intent.getStringExtra(Constants.MENU_DESCRIPTION);

        nameDetailsPage.setText(name);
        imageView.setImageResource(imageId);
        editText.setText(String.valueOf(quantity));
        editText.setSelection(editText.getText().length());
        String displayPrice = " x Price : Rp" + price;
        priceDetailsPage.setText(displayPrice);
        descriptionDetailsPage.setText("Description : \n \n " + description);

        // Get Qty from command line and go back to Main page on clicking Back button
        Button button = (Button) findViewById(R.id.BackButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(editText.getText().toString().length() != 0) {
                    dishList.get(position - 1).setQty(Integer.parseInt(editText.getText().toString()));
                }
                else
                    dishList.get(position - 1).setQty(0);
                goBackToMainScreen();
            }
        });
    }

    public void goBackToMainScreen(){

        Intent intent = new Intent(this, MainActivity.class);
        intent.putParcelableArrayListExtra(Constants.MENU_ITEM_LIST, dishList);
        startActivity(intent);
    }

}
