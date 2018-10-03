package com.jain.tavish.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ModeSelectionActivity extends AppCompatActivity {

    Button buttonAI, buttonPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selection);

        buttonAI = findViewById(R.id.btn_play_with_AI);
        buttonPlayer = findViewById(R.id.btn_play_with_player);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        if (buttonAI.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) buttonAI.getLayoutParams();
            p.setMargins(0, height/3, 0, 0);
            buttonAI.requestLayout();
        }

        if (buttonPlayer.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) buttonPlayer.getLayoutParams();
            p.setMargins(0, 2*(height/3), 0, 0);
            buttonPlayer.requestLayout();
        }

    }

    public void playAI(View view) {
        Intent intent = new Intent(ModeSelectionActivity.this, AIPlayerActivity.class);
        startActivity(intent);
    }

    public void playPlayer(View view) {
        Intent intent = new Intent(ModeSelectionActivity.this, TwoPlayerActivity.class);
        startActivity(intent);
    }
}
