package com.jain.tavish.tictactoe;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton mail, share, aboutMe;
    public static final String PLAY_STORE_APP_URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mail = findViewById(R.id.mail_image_button);
        share = findViewById(R.id.share_image_button);
        aboutMe = findViewById(R.id.about_me_image_button);

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:jaintavish@gmail.com"));
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this, "No Application found to handle this Action", Toast.LENGTH_SHORT).show();
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Just checkout this awesome application : " + PLAY_STORE_APP_URL);
                intent.setType("text/plain");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this, "No Application found to handle this Action", Toast.LENGTH_SHORT).show();
                }
            }
        });

        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.linkedin.com/in/tavishjain/"));
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this, "No Application found to handle this Action", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void playButton(View view) {
        Intent intent = new Intent(MainActivity.this, ModeSelectionActivity.class);
        startActivity(intent);
    }
}