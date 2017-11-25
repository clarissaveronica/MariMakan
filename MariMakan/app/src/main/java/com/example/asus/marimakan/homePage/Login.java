package com.example.asus.marimakan.homePage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.marimakan.R;

public class Login extends AppCompatActivity {

    EditText table;
    String num;
    SharedPreferences prefs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = (Button)findViewById(R.id.confirmButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });

        table = (EditText) findViewById(R.id.tableNumber);
        prefs = getSharedPreferences("login", 0);
    }

    public void onSubmit(){
        num = table.getText().toString();
        if(num.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Input Your Table Number!", Toast.LENGTH_LONG).show();
        }else{
            prefs.edit().putBoolean("isFirstLaunch", false).commit();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Num", num);
            startActivity(intent);
        }
    }
}