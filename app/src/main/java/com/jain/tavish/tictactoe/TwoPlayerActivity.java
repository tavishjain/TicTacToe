package com.jain.tavish.tictactoe;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TwoPlayerActivity extends AppCompatActivity {

    public ImageView imageView1 , imageView2 , imageView3 , imageView4 , imageView5 , imageView6 , imageView7 , imageView8 , imageView9;
    public Button newGameButton;
    public boolean cross;
    public TextView turn_teller;
    public int[] cross_number , circle_number;
    public int steps_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);

        newGameButton = findViewById(R.id.btn_new_game);

        turn_teller = findViewById(R.id.tv_turn_teller);

        imageView1 = findViewById(R.id.id_0_0);
        imageView2 = findViewById(R.id.id_0_1);
        imageView3 = findViewById(R.id.id_0_2);
        imageView4 = findViewById(R.id.id_1_0);
        imageView5 = findViewById(R.id.id_1_1);
        imageView6 = findViewById(R.id.id_1_2);
        imageView7 = findViewById(R.id.id_2_0);
        imageView8 = findViewById(R.id.id_2_1);
        imageView9 = findViewById(R.id.id_2_2);

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_game();
            }
        });

        new_game();

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ImageView) view).setImageResource(getImageResourceForView(1));
                imageView1.setEnabled(false);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ImageView) view).setImageResource(getImageResourceForView(2));
                imageView2.setEnabled(false);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ImageView) view).setImageResource(getImageResourceForView(3));
                imageView3.setEnabled(false);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ImageView) view).setImageResource(getImageResourceForView(4));
                imageView4.setEnabled(false);
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ImageView) view).setImageResource(getImageResourceForView(5));
                imageView5.setEnabled(false);
            }
        });

        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ImageView) view).setImageResource(getImageResourceForView(6));
                imageView6.setEnabled(false);
            }
        });

        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ImageView) view).setImageResource(getImageResourceForView(7));
                imageView7.setEnabled(false);
            }
        });

        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ImageView) view).setImageResource(getImageResourceForView(8));
                imageView8.setEnabled(false);
            }
        });

        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ImageView) view).setImageResource(getImageResourceForView(9));
                imageView9.setEnabled(false);
            }
        });

    }

    public int getImageResourceForView(int iv_id){
        int id = R.drawable.ic_cross;

        steps_count++;

        if(cross == false){ //Code for O
            cross = true;
            id = R.drawable.ic_cross;
            circle_number[iv_id - 1] = 0;
            turn_teller.setText("O's turn");
            checkForWin(circle_number , 'X');
        }else if(cross == true){ //Code for X
            cross = false;
            cross_number[iv_id - 1] = 0;
            id = R.drawable.ic_circle;
            checkForWin(cross_number , 'O');
            turn_teller.setText("X's turn");
        }

        return id;
    }
//player number of O is 0 and for X is 1

    public void checkForWin(int[] array , char player){
        if(array[0] == 0 && array[1] == 0 && array[2] == 0){
            playerWon(player);
        }else  if(array[3] == 0 && array[4] == 0 && array[5] == 0){
            playerWon(player);
        }else  if(array[6] == 0 && array[7] == 0 && array[8] == 0){
            playerWon(player);
        }else  if(array[0] == 0 && array[3] == 0 && array[6] == 0){
            playerWon(player);
        }else  if(array[1] == 0 && array[4] == 0 && array[7] == 0){
            playerWon(player);
        }else  if(array[2] == 0 && array[5] == 0 && array[8] == 0){
            playerWon(player);
        }else  if(array[0] == 0 && array[4] == 0 && array[8] == 0){
            playerWon(player);
        }else  if(array[2] == 0 && array[4] == 0 && array[6] == 0){
            playerWon(player);
        }else if(steps_count == 9){
            playerWon('a');
        }
    }

    public void playerWon(char winner){

        imageView1.setEnabled(false);
        imageView2.setEnabled(false);
        imageView3.setEnabled(false);
        imageView4.setEnabled(false);
        imageView5.setEnabled(false);
        imageView6.setEnabled(false);
        imageView7.setEnabled(false);
        imageView8.setEnabled(false);
        imageView9.setEnabled(false);

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        if(winner == 'X'){
            builder.setTitle("X Wins !!!!!");
        }else if(winner == 'O'){
            builder.setTitle("O Wins !!!!!");
        }else if(winner == 'a'){
            builder.setTitle("Match Drawn !!");
        }
        builder.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                new_game();
            }
        }).show();
    }

    public void new_game(){

        steps_count = 0 ;

        imageView1.setEnabled(true);
        imageView2.setEnabled(true);
        imageView3.setEnabled(true);
        imageView4.setEnabled(true);
        imageView5.setEnabled(true);
        imageView6.setEnabled(true);
        imageView7.setEnabled(true);
        imageView8.setEnabled(true);
        imageView9.setEnabled(true);

        cross_number = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};
        circle_number = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};

        imageView1.setImageResource(0);
        imageView2.setImageResource(0);
        imageView3.setImageResource(0);
        imageView4.setImageResource(0);
        imageView5.setImageResource(0);
        imageView6.setImageResource(0);
        imageView7.setImageResource(0);
        imageView8.setImageResource(0);
        imageView9.setImageResource(0);
    }
}
