package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public void onButtonClick(View view){
        //1.get username and password via EditText view.
        EditText usernameText = (EditText) findViewById(R.id.login_name);
        String str = usernameText.getText().toString();
        //2.add username to SharedPreferences object.
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username",str).apply();
        //3.start second activity
        goToActivity2(str);
    }

    public void clickFunction(View view) {
        EditText myTextField = (EditText) findViewById(R.id.login_name);
        String str = myTextField.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", str).apply();

        goToActivity2(str);
    }

    public void goToActivity2(String s){
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("username",s);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usernameKey = "username";

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);

        if (!sharedPreferences.getString(usernameKey, "").equals("")){
            // "username" key exists in SharedPreferences object which means that a user was logged in before the app close
            // Get the name of that
            String curr = sharedPreferences.getString(usernameKey,"");
            goToActivity2(curr);
        } else {
            // sharedPreferences object has no username key set
            // Start screen 1, that is the main activity
            setContentView(R.layout.activity_main);
        }
    }
}