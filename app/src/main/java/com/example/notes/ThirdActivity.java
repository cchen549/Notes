package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThirdActivity extends AppCompatActivity {

    int noteid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //1. Get EditText view.
        EditText noteEditText = (EditText) findViewById(R.id.editText);

        //2. Get Intent.
        Intent intent = getIntent();

        //3. Get the value of integer "noteid" from intent.
        //4  Initialize class variable "noteid" with the value from intent.
        noteid = intent.getIntExtra("noteid", noteid);

        if (noteid != -1) {
            // Display content of note by retrieving from "notes" ArrayList in SecondActivity.
            Note note = MainActivity2.notes.get(noteid);
            String noteContent = note.getContent();
            // Use editText.setText() to display the contents of this note on screen.
            noteEditText.setText(noteContent);
        }
    }

    public void saveMethod(View view) {
        //1. Get editText view and the content that user entered.
        EditText editText = (EditText) findViewById(R.id.editText);
        String content = editText.getText().toString();

        // 2. Initialize SQLiteDatabase instance.
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

        // 3. Initialize DBHelper class.
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        // 4. Set username by fetching it from SharePreferences
        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(usernameKey, "");

        // 5. Save information to database
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) { //Add note.
            title = "NOTE_" + (MainActivity2.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else { //Update note.
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(title, date, content, username);
        }

        // 6. Go to second activity
        goToSecondActivity();
    }

    public void goToSecondActivity() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}