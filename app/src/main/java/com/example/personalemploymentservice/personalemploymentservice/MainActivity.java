package com.example.personalemploymentservice.personalemploymentservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addTextNoteButton = null;
    private FloatingActionButton addVoiceNoteButton = null;
    private final String TEXT_NOTE = "Text note";
    private final String CHECK_LIST = "Check list";
    private final String VOICE_NOTE = "Voice note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addTextNoteButton = findViewById(R.id.add_text_note);
        addTextNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTextNoteButton.setClickable(false);
                Intent intent = new Intent(MainActivity.this, TextNoteCreating.class);
                startActivity(intent);
            }
        });
        addVoiceNoteButton = findViewById(R.id.add_voice_note);
        addVoiceNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVoiceNoteButton.setClickable(false);
                Intent intent = new Intent(MainActivity.this, VoiceNoteCreating.class);
                startActivity(intent);
            }
        });
    }

    private void clearLayout(LinearLayout parentLayout) {
        parentLayout.removeAllViewsInLayout();
    }

    private void addCardFromFiles(LinearLayout parentLayout) {
        File[] files = new File(getFilesDir().getAbsolutePath()).listFiles();
        String fileNameWithExtension;
        String fileName;
        String fileExtension;
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    LinearLayout addedLayout = null;
                    fileNameWithExtension = files[i].getName();
                    fileName = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf("."));
                    fileExtension = fileNameWithExtension.substring(fileNameWithExtension.lastIndexOf("."));
                    switch (fileExtension) {
                        case ".txt":
                            addedLayout = cardGenerate(fileName, "", TEXT_NOTE);
                            break;
                        case ".ckl":
                            addedLayout = cardGenerate(fileName, "", CHECK_LIST);
                            break;
                        case ".mp3":
                            addedLayout = cardGenerate(fileName, "", VOICE_NOTE);
                            break;
                    }
                    if (parentLayout != null) {
                        if (addedLayout != null) {
                            parentLayout.addView(addedLayout);
                            Toast.makeText(this, "Note was found!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, "Note was found but no added!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "Root layout has not found!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private LinearLayout cardGenerate(String titleText, String contentText, String noteType) {
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setVisibility(View.VISIBLE);
        container.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        container.setWeightSum(1);
        switch (noteType) {
            case TEXT_NOTE:
                TextView title = new TextView(this);
                title.setText(titleText);
                TextView contentPart = new TextView(this);
                contentPart.setText(contentText);
                container.addView(title);
                container.addView(contentPart);
                break;
            case CHECK_LIST:
                break;
            case VOICE_NOTE:
                break;
        }
        return container;
    }

    @Override
    protected void onResume() {
        super.onResume();
        addTextNoteButton.setClickable(true);
        addVoiceNoteButton.setClickable(true);
        LinearLayout parentContainer = findViewById(R.id.notes_list);
        ScrollView scrollView = new ScrollView(this);
        LinearLayout notesList = new LinearLayout(this);
        scrollView.addView(notesList);
        parentContainer.addView(scrollView);
        addCardFromFiles(parentContainer);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LinearLayout parentContainer = findViewById(R.id.notes_list);
        clearLayout(parentContainer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
            return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }
}
