package com.example.christian.christian_pset2;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class PickActivity extends AppCompatActivity {

    public Story story;
    public String fileName;
    public String[] fileNames = {"madlib0_simple.txt", "madlib1_tarzan.txt", "madlib2_university.txt", "madlib3_clothes.txt", "madlib4_dance.txt"};

    RadioButton radioSimple, radioTarzan, radioUniversity, radioClothes, radioDance, radioRandom;
    RadioGroup radioButtons;
    EditText wordAdder;
    KeyListener listener;
    Button useButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick);
        radioSimple = findViewById(R.id.radioSimple);
        radioTarzan = findViewById(R.id.radioTarzan);
        radioUniversity = findViewById(R.id.radioUniversity);
        radioClothes = findViewById(R.id.radioClothes);
        radioDance = findViewById(R.id.radioDance);
        radioRandom = findViewById(R.id.radioRandom);
        wordAdder = findViewById(R.id.wordAdder);
        useButton = findViewById(R.id.actionButton);
        progressBar = findViewById(R.id.progressBar);

        listener = wordAdder.getKeyListener();
        wordAdder.setHint("Pick story first");
        wordAdder.setKeyListener(null);

    }

    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void selectStory(View view) {

        if (radioSimple.isChecked()) {
            fileName = fileNames[0];
        }
        else if (radioTarzan.isChecked()) {
            fileName = fileNames[1];
        }
        else if (radioUniversity.isChecked()) {
            fileName = fileNames[2];
        }
        else if (radioClothes.isChecked()) {
            fileName = fileNames[3];
        }
        else if (radioDance.isChecked()) {
            fileName = fileNames[4];
        }
        else if (radioRandom.isChecked()) {
            Random random = new Random();
            int index = random.nextInt(fileNames.length);
            fileName = fileNames[index];
        }

        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(fileName);
        }
        catch (IOException e){
            Log.e("message: ",e.getMessage());
        }
        story = new Story(inputStream);

        wordAdder.setHint(story.getNextPlaceholder());
        wordAdder.setKeyListener(listener);
        int used = (story.getPlaceholderCount() - story.getPlaceholderRemainingCount()) + 1;
        useButton.setText("USE! " + used + "/" + story.getPlaceholderCount());
        progressBar.setMax(story.getPlaceholderCount());
        progressBar.setProgress(0);

    }

    public void createStory(View view) {

        radioButtons = findViewById(R.id.radioButtons);

        if (radioButtons.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "Please select story first!", Toast.LENGTH_SHORT).show();
        } else {
            String filledInWord = "<b>" + wordAdder.getText().toString() + "</b>";

            if (filledInWord.length() <= 7) {
                Toast.makeText(getApplicationContext(), "Please enter a " + story.getNextPlaceholder() + "!", Toast.LENGTH_SHORT).show();
            } else {
                story.fillInPlaceholder(filledInWord);

                int used = (story.getPlaceholderCount() - story.getPlaceholderRemainingCount()) + 1;
                if (used == story.getPlaceholderCount()) {
                    useButton.setText("Create story!");

                } else if (used > story.getPlaceholderCount()) {
                    String storyString = story.toString();

                    Intent intent = new Intent(this, StoryActivity.class);
                    intent.putExtra("OUT", storyString);

                    startActivity(intent);
                    finish();
                } else {
                    useButton.setText("USE! " + used + "/" + story.getPlaceholderCount());

                }
                wordAdder.setText("");
                wordAdder.setHint(story.getNextPlaceholder());
                progressBar.setProgress(used - 1);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("buttontext", useButton.getText().toString());
        outState.putSerializable("story", story);
        outState.putInt("progress", progressBar.getProgress());
        outState.putInt("progressmax", progressBar.getMax());
        outState.putString("placeholderhint", wordAdder.getHint().toString());
        outState.putString("placeholdertext", wordAdder.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        wordAdder.setKeyListener(listener);
        useButton.setText(inState.getString("buttontext"));
        story = (Story) inState.getSerializable("story");
        progressBar.setProgress(inState.getInt("progress"));
        progressBar.setMax(inState.getInt("progressmax"));
        wordAdder.setText(inState.getString("placeholdertext"));
        wordAdder.setHint(inState.getString("placeholderhint"));

    }
}
