package com.example.asus.marimakan.homePage;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.marimakan.R;
import com.example.asus.marimakan.calculateTotal.CalculateTotalActivity;
import com.example.asus.marimakan.checkout.CheckoutActivity;
import com.example.asus.marimakan.constants.Constants;
import com.example.asus.marimakan.model.Dish;
import com.example.asus.marimakan.orderHistory.OrderHistoryActivity;
import com.example.asus.marimakan.productPage.ItemDetailsActivity;
import com.example.asus.marimakan.productPage.ItemDetailsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Dish> menuList = new ArrayList<Dish>();
    private MenuAdapter menuAdapter;
    int itemId = 1;
    ListView menuListView;
    long totalAmount;
    SharedPreferences prefs;
    private static final int MENU_LOGOUT = Menu.FIRST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.splashScreenTheme);
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        if(intent.getParcelableArrayListExtra(Constants.MENU_ITEM_LIST) !=null){
            menuList = intent.getParcelableArrayListExtra(Constants.MENU_ITEM_LIST);

        }else{
            fetchData();
            ItemDetailsFragment detailsFragment = (ItemDetailsFragment) getFragmentManager().findFragmentById(R.id.ItemDetailsFragment);
            if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT && detailsFragment!=null) {
                detailsFragment.updateContent(menuList.get(0),0);
            }
        }

        FragmentManager fragmentManager = getFragmentManager();
        MenuListFragment menuListFragment = (MenuListFragment) fragmentManager.findFragmentById(R.id.MenuListFragment);
        menuAdapter = new MenuAdapter(this, R.layout.menu_list_view, menuList);
        menuListFragment.setListAdapter(menuAdapter);
        menuListView = menuListFragment.getListView();
        menuAdapter.notifyDataSetChanged();

        calculateTotal();

        Button checkoutButton = (Button) findViewById(R.id.ConfirmButton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickCheckoutButton();
            }
        });

        Button historyButton = (Button)findViewById(R.id.historyButton);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHistoryButton();
            }
        });

        prefs = getSharedPreferences("login", 0);

        if(prefs.getBoolean("isFirstLaunch", true)){
                Intent intent2 = new Intent(this, Login.class);
                startActivity(intent2);
        }else {
                if (prefs.getBoolean("launched", true)) {
                    String tableNum = getIntent().getStringExtra("Num");
                    prefs.edit().putString("table", tableNum).commit();
                    prefs.edit().putBoolean("launched", false).commit();
                }
                TextView table = (TextView) findViewById(R.id.TableNum);
                table.setText("Table " + prefs.getString("table", null));
            }
    }

    public void calculateTotal(){
        Intent intent = new Intent(this, CalculateTotalActivity.class);
        intent.putParcelableArrayListExtra(Constants.MENU_ITEM_LIST, menuList);
        // Starting activity to calculate total amount and return
        startActivityForResult(intent,1);
        menuAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(0, MENU_LOGOUT, 0, "Log Out");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_LOGOUT:
                Intent logout = new Intent (this, Logout.class);
                startActivity(logout);
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            long total = data.getLongExtra(Constants.TOTAL_AMOUNT,0);
            TextView displayTotalAmount = (TextView)findViewById(R.id.TotalAmount);
            String displayQtyAndPrice = "Total Amount = Rp" + total;
            displayTotalAmount.setText(displayQtyAndPrice);
            totalAmount = total;
        }
    }
    public void onClickCheckoutButton(){

        boolean proceed = false;
        ArrayList<Dish> itemsInCart = new ArrayList<Dish>();
        for(int i=0; i<menuList.size(); i++){
            if(menuList.get(i).getQty() > 0){
                itemsInCart.add(menuList.get(i));
                proceed = true;
            }
        }
        if(proceed == true){
            Intent intent = new Intent(this, CheckoutActivity.class);
            intent.putParcelableArrayListExtra(Constants.CART_ITEM_LIST ,itemsInCart);
            intent.putExtra(Constants.TOTAL_AMOUNT, totalAmount);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Please add quantity by clicking!", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickHistoryButton(){

        Intent intent = new Intent(this, OrderHistoryActivity.class);
        intent.putParcelableArrayListExtra(Constants.MENU_ITEM_LIST, menuList);
        startActivity(intent);
    }

    public void onClickQtyButton(int position){
        ItemDetailsFragment detailsFragment = (ItemDetailsFragment) getFragmentManager().findFragmentById(R.id.ItemDetailsFragment);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT || detailsFragment==null) {
            Intent intent = new Intent(this, ItemDetailsActivity.class);
            intent.putExtra(Constants.MENU_NAME, menuAdapter.getItem(position).getName());
            intent.putExtra(Constants.MENU_DESCRIPTION, menuAdapter.getItem(position).getDescription());
            intent.putExtra(Constants.MENU_PRICE, menuAdapter.getItem(position).getPrice());
            intent.putExtra(Constants.MENU_IMAGE_ID, menuAdapter.getItem(position).getImageId());
            intent.putExtra(Constants.MENU_ITEM_QTY, menuAdapter.getItem(position).getQty());
            intent.putExtra(Constants.MENU_ITEM_POS, menuAdapter.getItem(position).getItemId());
            intent.putParcelableArrayListExtra(Constants.MENU_ITEM_LIST, menuList);
            startActivity(intent);
        }else{
            detailsFragment.updateContent(menuAdapter.getItem(position),position);
        }
    }

    public void updateQuantity(String newQuantity,int position){
        int qty = 0 ;
        if(newQuantity.length()>0){
            qty = Integer.parseInt(newQuantity);
        }
        menuAdapter.getItem(position).setQty(qty);
        calculateTotal();
        menuAdapter.notifyDataSetChanged();

    }

    public void fetchData(){
        Dish menu1 = new Dish("Dosa", 1L,"Rice, Potato, Ghee \n \n Taste:Medium Spicy", itemId++, R.drawable.dosa, 0);
        menuList.add(menuList.size(), menu1);
        Dish menu2 = new Dish("Burger",10L,"Bun, Potato Patty, Cheese \n \nTaste:Medium Spicy", itemId++, R.drawable.burger, 0);
        menuList.add(menuList.size(), menu2);
        Dish menu3 = new Dish("Pohe",5L,"Pohe, Onion, Groundnut \n \nTaste:Medium Spicy", itemId++, R.drawable.pohe, 0);
        menuList.add(menuList.size(), menu3);
        Dish menu4 = new Dish("Samosa",2L,"Potato, Green Peas, Maida \n \nTaste:Medium Spicy", itemId++, R.drawable.samosa, 0);
        menuList.add(menuList.size(), menu4);
        Dish menu5 = new Dish("Biriyani",6L,"Rice, Spices, Vegetables \n \nTaste:Very Spicy", itemId++, R.drawable.biriyani, 0);
        menuList.add(menuList.size(), menu5);
        Dish menu6 = new Dish("Jalebi",1L,"Maida, Sugar, Kesar \n \nTaste:Sweet", itemId++, R.drawable.jalebi, 0);
        menuList.add(menuList.size(), menu6);
        Dish menu7 = new Dish("Jaamun",2L,"Maida, Sugar, Ghee \n \nTaste:Sweet", itemId++, R.drawable.jaamun, 0);
        menuList.add(menuList.size(), menu7);
        Dish menu8 = new Dish("Bhakarwadi",2L,"Maida, Sugar, Red Chilli, Sesame Seeds \n \nTaste:Medium Spicy", itemId++, R.drawable.bhakarwadi, 0);
        menuList.add(menuList.size(), menu8);
        Dish menu9 = new Dish("Paneer",11L,"Paneer, Indian spices, Tomato \n \nTaste:Medium Spicy", itemId++, R.drawable.paneer, 0);
        menuList.add(menuList.size(), menu4);
    }
}