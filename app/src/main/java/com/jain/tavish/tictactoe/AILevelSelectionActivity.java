package com.jain.tavish.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AILevelSelectionActivity extends AppCompatActivity {

    Button hardAI, easyAI, next;
    boolean easyOrNot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailevel_selection);

        hardAI = findViewById(R.id.btn_hard_player);
        easyAI = findViewById(R.id.btn_easy_AI);
        next = findViewById(R.id.btn_next);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        next.setEnabled(false);
        next.setAlpha((float) 0.4);

        if (hardAI.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) hardAI.getLayoutParams();
            p.setMargins(0, height/4, 0, 0);
            hardAI.requestLayout();
        }

        if (easyAI.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) easyAI.getLayoutParams();
            p.setMargins(0, 2*(height/4), 0, 0);
            easyAI.requestLayout();
        }

        if (next.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) next.getLayoutParams();
            p.setMargins(0, 3*(height/4), 0, 0);
            next.requestLayout();
        }

        hardAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyAI.setAlpha((float) 0.4);
                hardAI.setAlpha((float)1.0);
                easyOrNot = false;
                next.setEnabled(true);
                next.setAlpha((float) 1.0);
            }
        });

        easyAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hardAI.setAlpha((float) 0.4);
                easyAI.setAlpha((float)1.0);
                easyOrNot = true;
                next.setEnabled(true);
                next.setAlpha((float) 1.0);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(easyOrNot){
                    Intent intent = new Intent(AILevelSelectionActivity.this, EasyAIPlayerActivity.class);
                    startActivity(intent);
                }else if(!easyOrNot){
                    Intent intent = new Intent(AILevelSelectionActivity.this, HardAIPlayerActivity.class);
                    startActivity(intent);
                }
            }
        });


    }
}
