package com.example.christian.christian_pset2;

// necessary imports
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

// create class
public class StoryActivity extends AppCompatActivity {

    // onCreate callback
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_story);

        Intent intent = getIntent();
        String receivedText = intent.getStringExtra("OUT");

        TextView textView = findViewById(R.id.StoryText);
        textView.setText(Html.fromHtml(receivedText));

        textView.setMovementMethod(new ScrollingMovementMethod());
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

    // this event is fired when the button is clicked to go back to the story-pick page
    public void pickStory(View view) {
        Intent intent = new Intent(this, PickActivity.class);
        startActivity(intent);

        // make sure to stop the current activity
        finish();
    }
}
