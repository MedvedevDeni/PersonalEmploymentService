package com.example.personalemploymentservice.personalemploymentservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class TextNoteCreating extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creating_text_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_text_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Intent intent;
        switch (itemId) {
            case R.id.action_settings:
                intent = new Intent(this, Settings.class);
                startActivity(intent);
                return super.onOptionsItemSelected(item);
            case R.id.action_save_text_note:
                EditText editTemp = findViewById(R.id.title);
                String title = editTemp.getText().toString();
                editTemp = findViewById(R.id.content);
                if (title.equals("")) {
                    Toast.makeText(this, "Title can't be empty", Toast.LENGTH_LONG).show();
                } else {
                    title += ".txt";
                    try {
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput(title, MODE_PRIVATE)));
                        writer.write(editTemp.getText().toString());
                        writer.flush();
                        writer.close();
                        Toast.makeText(this, "File saved at: " + getFilesDir().getAbsolutePath(), Toast.LENGTH_LONG).show();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                return super.onOptionsItemSelected(item);

        }

        return super.onOptionsItemSelected(item);
    }
}
