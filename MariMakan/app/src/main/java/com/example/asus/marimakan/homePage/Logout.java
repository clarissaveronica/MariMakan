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
import com.example.asus.marimakan.database.DBHelper;

public class Logout extends AppCompatActivity {

    EditText pass;
    String password;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        Button button = (Button)findViewById(R.id.logoutButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });

        Button cancel = (Button)findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancel();
            }
        });

        pass = (EditText) findViewById(R.id.pass);
        prefs = getSharedPreferences("login", 0);
    }

    public void onSubmit(){
        password = pass.getText().toString();
        if(password.equals("abc123")) {
            DBHelper dbHelper = new DBHelper(this);
            dbHelper.deleteDatabaseAndTable();
            prefs.edit().putBoolean("isFirstLaunch", true).commit();
            prefs.edit().putBoolean("launched", true).commit();
            Intent intent = new Intent(this, Login.class);
            intent.putExtra("reset", true);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "Wrong Password!", Toast.LENGTH_LONG).show();
        }
    }

    public void onCancel(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
