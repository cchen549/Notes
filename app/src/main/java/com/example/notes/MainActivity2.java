package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    TextView welcome;
    public static ArrayList<Note> notes = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // 1.Display welcome message. Fetch username from SharedPreferences.
        welcome = (TextView) findViewById(R.id.welcome);
        // Fetch username from SharedPreferences.
        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(usernameKey, "");
        welcome.setText("Welcome " + username + "!");

        // 2. Get SQLiteDatabase instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

        // 3. Initialize the notes class variable using readNotes method implemented in DBHelper class. Use the username you
        //    got from SharedPreferences as a parameter to readNotes method.
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        notes = dbHelper.readNotes(username);

        // 4. Create an ArrayList<String> object by iterating over notes object
        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        // 5. User ListView view to display notes on screen
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        // 6. Add onItemClickListener for ListView item, a note in our case
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Initialize intent to take user to the NoteActivity
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                // Add the position of item that was clicked on as "noteid"
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menumain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.logout:
                //Erase username from shared preferences
                SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                String usernameKey = "username";
                sharedPreferences.edit().remove(usernameKey).apply();
                goToActivity1();
                return true;
            case R.id.addnote:
                //Go to activity 3.
                goToNoteActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToActivity1(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToNoteActivity() {
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }
}