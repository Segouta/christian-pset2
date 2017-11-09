package com.example.christian.christian_pset2;

// necessary imports
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

// create class
public class PickActivity extends AppCompatActivity {

    // global variables
    public Story story;
    public String fileName;
    public String[] fileNames = {"madlib0_simple.txt", "madlib1_tarzan.txt", "madlib2_university.txt", "madlib3_clothes.txt", "madlib4_dance.txt"};

    // initialize objects
    RadioButton radioSimple, radioTarzan, radioUniversity, radioClothes, radioDance, radioRandom;
    RadioGroup radioButtons;
    EditText wordAdder;
    KeyListener listener;
    Button useButton;
    ProgressBar progressBar;

    // onCreate callback
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick);

        // find all ID's of the objects
        radioSimple = findViewById(R.id.radioSimple);
        radioTarzan = findViewById(R.id.radioTarzan);
        radioUniversity = findViewById(R.id.radioUniversity);
        radioClothes = findViewById(R.id.radioClothes);
        radioDance = findViewById(R.id.radioDance);
        radioRandom = findViewById(R.id.radioRandom);
        wordAdder = findViewById(R.id.wordAdder);
        useButton = findViewById(R.id.actionButton);
        progressBar = findViewById(R.id.progressBar);
        radioButtons = findViewById(R.id.radioButtons);

        // create hint in editText and make unclickable
        listener = wordAdder.getKeyListener();
        wordAdder.setHint("Pick story first");
        wordAdder.setKeyListener(null);

    }

    // onResume callback, used to make the nav bar and status bar disappear
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

    // this event listens for radio button selections
    public void selectStory(View view) {

        // check what radio button is selected and save correct filename
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
            // special case, select random filename
            Random random = new Random();
            int index = random.nextInt(fileNames.length);
            fileName = fileNames[index];
        }

        // open the file chosen in the above section
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(fileName);
        }
        catch (IOException e){
            Log.e("message: ",e.getMessage());
        }

        // assign the story
        story = new Story(inputStream);

        // set the hint to the correct first placeholder
        wordAdder.setHint(story.getNextPlaceholder());

        // make the editText clickable again
        wordAdder.setKeyListener(listener);

        // calculate the amount of used placeholders and set button text accordingly
        int used = (story.getPlaceholderCount() - story.getPlaceholderRemainingCount()) + 1;
        useButton.setText("USE! " + used + "/" + story.getPlaceholderCount());

        // setup of progressBar
        progressBar.setMax(story.getPlaceholderCount());
        progressBar.setProgress(0);

    }

    // this event fires when one clicks the use-button
    public void createStory(View view) {

        // if all radiobuttons are still empty, give toast message
        if (radioButtons.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "Please select story first!", Toast.LENGTH_SHORT).show();
        } else {
            // create string that stores the text typed in the editText
            String filledInWord = wordAdder.getText().toString();

            // if there was no text in the editText, give toast message
            if (filledInWord.length() == 0) {
                Toast.makeText(getApplicationContext(), "Please enter a " + story.getNextPlaceholder() + "!", Toast.LENGTH_SHORT).show();
            } else {
                // store the text in the placeholder
                story.fillInPlaceholder(filledInWord);

                // calculate amount of used placeholders
                int used = (story.getPlaceholderCount() - story.getPlaceholderRemainingCount()) + 1;

                // when last placeholder is reached, change button text
                if (used == story.getPlaceholderCount()) {
                    useButton.setText("Create story!");
                } else if (used > story.getPlaceholderCount()) {
                    // when were done filling the placeholders in, save the string in an intent
                    String storyString = story.toString();
                    Intent intent = new Intent(this, StoryActivity.class);
                    intent.putExtra("OUT", storyString);

                    // start the StoryActivity and finish this activity
                    startActivity(intent);
                    finish();
                } else {
                    // if not in the last or after the last placeholder, just update the button text
                    useButton.setText("USE! " + used + "/" + story.getPlaceholderCount());
                }

                // remove the word from the editText and show next placeholder as a hint
                wordAdder.setText("");
                wordAdder.setHint(story.getNextPlaceholder());

                // update progressBar
                progressBar.setProgress(used - 1);
            }
        }
    }

    // save everything when rotating screen
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

    // set everything after screen is rotated
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
