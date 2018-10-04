package com.jain.tavish.tictactoe;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class EasyAIPlayerActivity extends AppCompatActivity {
    int turn = 1;
    int win = 0;
    int gamov = 0;
    int flagEndGame=0;
    int flag;
    String displayTurn;
    GridLayout grid;
    ImageView playBoard[][] = new ImageView[3][3];
    int boardMatrix[][] = new int[3][3];
    double probMatrix[][] = new double[3][3];
    TextView playerTurn;
    int moveNumber=1;
    int counter = 0;
    int player1Win = 0, player2Win = 0, draw = 0;
    int flipValue=0;
    Button button;
    public int[] cross_number , circle_number;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_player);

        button = findViewById(R.id.btn_new_game_comp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame(view);
            }
        });

        cross_number = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};
        circle_number = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};

        playerTurn = (TextView) findViewById(R.id.tv_turn_teller_comp);
        builder = new AlertDialog.Builder(this);

        grid = (GridLayout) findViewById(R.id.grid_layout_comp);
        displayTurn = "X's turn";
        playerTurn.setText(displayTurn);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playBoard[i][j] = (ImageView) grid.getChildAt(3 * i + j);
                final int finalI = i;
                final int finalJ = j;
                playBoard[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        playmove(playBoard[finalI][finalJ]);
                    }
                });
                boardMatrix[i][j]=0;
            }
        }

        if(flipValue==1){
            computerPlay();
            turn=2;
        }
    }

    public void playmove(View view) {
        int index = grid.indexOfChild(view);
        int i = index / 3;
        int j = index % 3;

        view.setEnabled(false);
        flag = 0;
        if (turn == 1 && gamov == 0 && cross_number[3*i+j] != 0 && circle_number[3*i+j] != 0) {

            if(flipValue==0){
                cross_number[3*i+j] = 0;
                displayTurn = "O's turn";
                playerTurn.setText(displayTurn);
                Glide.with(this)
                        .load(R.drawable.ic_cross)
                        .into(playBoard[i][j]);
                boardMatrix[i][j]=1;
                turn = 2;
                moveNumber++;
                computerPlay();
                turn = 1;
                //  cross_number[3*i+j] = 0;
                displayTurn="X's turn";
                moveNumber++;
            }

        } else if (turn == 2 && gamov == 0 && cross_number[3*i+j] != 0 && circle_number[3*i+j] != 0) {

            if(flipValue==1){
                circle_number[3*i+j] = 0;
                displayTurn = "X's turn";
                playerTurn.setText(displayTurn);
                Glide.with(this)
                        .load(R.drawable.ic_circle)
                        .into(playBoard[i][j]);
                boardMatrix[i][j]=1;
                turn = 1;
                moveNumber++;
                computerPlay();
                //  circle_number[3*i+j] = 0;
                displayTurn = "O's turn";
                turn = 2;
                moveNumber++;

            }
        }

        checkWin();

        if (gamov == 1) {
            if (win == 1) {
                builder.setTitle("X wins !!");
                if(flagEndGame==0){
                    player1Win++;
                    counter++;
                }


            } else if (win == 2) {
                builder.setTitle("O wins !!");
                if(flagEndGame==0){
                    player2Win++;
                    counter++;
                }

            }
            flagEndGame=1;
            builder.setPositiveButton("NEW GAME",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    newGame(new View(getApplicationContext()));
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
        if (gamov == 0) {
            for (i = 0; i < 3; i++) {
                for (j = 0; j < 3; j++) {
                    if (cross_number[3*i+j] != 0 && circle_number[3*i+j] != 0) {
                        flag = 1;
                        break;

                    }
                }
            }
            if (flag == 0) {
                builder.setTitle("Match Drawn !!");
                if(flagEndGame==0){
                    counter++;
                    draw++;
                }
                flagEndGame=1;
                builder.setPositiveButton("NEW GAME",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        newGame(new View(getApplicationContext()));
                    }

                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    public void randomPlay(){
        int random = (int)(Math.random()*9);
        int i=random/3;
        int j=random%3;Glide.with(this)
                .load(R.drawable.ic_cross)
                .into(playBoard[i][j]);
        boardMatrix[i][j]=1;
    }

    public void computerPlay(){
        int currentTurn = turn;
        int currentMove = moveNumber;
        int i=0,j=0;
        int moveChoice=0;
        int flag=0;
        int flagGameNotOver=0;

        if(turn==1){
            turn=2;
        }
        else{
            turn=1;
        }
        for(int c=0;c<9;c++){
            i=c/3;
            j=c%3;
            probMatrix[i][j]=0;
        }

        for(int c=0; c<9;c++) {
            i = c / 3;
            j = c % 3;
            if (boardMatrix[i][j] == 0) {
                flagGameNotOver=1;
                boardMatrix[i][j] = 1;
                if (flipValue == 1)
                    Glide.with(this)
                            .load(R.drawable.ic_cross)
                            .into(playBoard[i][j]);
                else
                    Glide.with(this)
                            .load(R.drawable.ic_circle)
                            .into(playBoard[i][j]);

                if (checkWinComp() == 2 && flipValue == 0) {
                    flag=1;
                    Glide.with(this).load(0).into(playBoard[i][j]);
                    boardMatrix[i][j] = 0;
                    break;
                } else if (checkWinComp() == 2 && flipValue == 1) {
                    flag=1;
                    Glide.with(this).load(0).into(playBoard[i][j]);
                    boardMatrix[i][j] = 0;
                    break;
                }
                if (checkWinComp() == 1 && flipValue == 1) {
                    Glide.with(this).load(0).into(playBoard[i][j]);
                    boardMatrix[i][j] = 0;
                    continue;
                } else if (checkWinComp() == 1 && flipValue == 0) {
                    Glide.with(this).load(0).into(playBoard[i][j]);
                    boardMatrix[i][j] = 0;
                    continue;
                } else {
                    probMatrix[i][j]=0.5;

                }
                Glide.with(this).load(0).into(playBoard[i][j]);
                boardMatrix[i][j] = 0;
            }
        }
        if(flagGameNotOver==0){
            return;
        }
        double maxProb=0;
        if(flag==0){
            for(int p=0;p<3;p++){
                for(int q=0;q<3;q++){
                    if(maxProb<probMatrix[p][q]){
                        maxProb=probMatrix[p][q];
                    }
                }
            }
            for(int p=0;p<3;p++){
                for(int q=0;q<3;q++){
                    if(maxProb==probMatrix[p][q] && boardMatrix[p][q]==0){
                        moveChoice=3*p+q;
                        break;
                    }
                }
            }
        }
        else{
            moveChoice=3*i+j;
        }
        turn = currentTurn;
        moveNumber = currentMove;
        int xCoord=moveChoice/3;
        int yCoord=moveChoice%3;
        boardMatrix[xCoord][yCoord]=1;
        if(flipValue==0){
            Glide.with(this)
                    .load(R.drawable.ic_circle)
                    .into(playBoard[xCoord][yCoord]);
            circle_number[3*xCoord+yCoord] = 0;
            displayTurn = "X's turn";
            playerTurn.setText(displayTurn);
        }
        else{
            Glide.with(this)
                    .load(R.drawable.ic_cross)
                    .into(playBoard[xCoord][yCoord]);
            cross_number[3*xCoord+yCoord] = 0;
            displayTurn = "O's turn";
            playerTurn.setText(displayTurn);
        }
    }

    public void newGame(View view) {

        win = 0;
        gamov = 0;
        turn=1;

        cross_number = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};
        circle_number = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playBoard[i][j].setEnabled(true);
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Glide.with(this).load(0).into(playBoard[i][j]);
                boardMatrix[i][j]=0;
            }
        }

        if(flipValue==0){
            if(flagEndGame==1){
                flipValue=1;
                displayTurn = "X's turn";
                playerTurn.setText(displayTurn);
            }
            else{
                displayTurn = "X's turn";
                playerTurn.setText(displayTurn);
            }


        }
        else if(flipValue==1){
            if(flagEndGame==1){
                flipValue=0;
                displayTurn = "X's turn";
                playerTurn.setText(displayTurn);
            }
            else{
                displayTurn = "X's turn";
                playerTurn.setText(displayTurn);
            }

        }
        flagEndGame=0;
        if(flipValue==1){
            randomPlay();
            turn=2;
        }
    }

    public void checkWin() {

        for (int i = 0; i < 3; i++) {

            if ((cross_number[3*i+0] == 0 && cross_number[3*i+1] == 0 && cross_number[3*i+2] == 0) || (circle_number[3*i+0] == 0 && circle_number[3*i+1] == 0 && circle_number[3*i+2] == 0)) {
                if (cross_number[3*i+0] == 0) {
                    gamov = 1;
                    if(flipValue==0)
                        win = 1;
                    else if(flipValue==1)
                        win=2;


                } else if (circle_number[3*i+0] == 0) {
                    gamov = 1;
                    if(flipValue==0)
                        win = 2;
                    else if(flipValue==1)
                        win=1;

                }

            }
            if ((cross_number[3*0+i] == 0 && cross_number[3*1+i] == 0 && cross_number[3*2+i] == 0) || (circle_number[3*0+i] == 0 && circle_number[3*1+i] == 0 && circle_number[3*2+i] == 0)) {
                if (cross_number[3*0+i] == 0) {
                    gamov = 1;
                    if(flipValue==0)
                        win = 1;
                    else if(flipValue==1)
                        win=2;


                } else if (circle_number[3*0+i] == 0) {
                    gamov = 1;
                    if(flipValue==0)
                        win = 2;
                    else if(flipValue==1)
                        win=1;

                }
            }
        }
        if ((cross_number[3*0+0] == 0 && cross_number[3*1+1] == 0 && cross_number[3*2+2] == 0) || (circle_number[3*0+0] == 0 && circle_number[3*1+1] == 0 && circle_number[3*2+2] == 0)) {
            if (cross_number[3*0+0] == 0) {
                gamov = 1;
                if(flipValue==0)
                    win = 1;
                else if(flipValue==1)
                    win=2;

            } else if (circle_number[3*0+0] == 0) {
                gamov = 1;
                if(flipValue==0)
                    win = 2;
                else if(flipValue==1)
                    win=1;

            }
        }

        if ((cross_number[3*0+2] == 0 && cross_number[3*1+1] == 0 && cross_number[3*2+0] == 0) || (circle_number[3*0+2] == 0 && circle_number[3*1+1] == 0 && circle_number[3*2+0] == 0)) {
            if (cross_number[3*0+2] == 0) {
                gamov = 1;
                if(flipValue==0)
                    win = 1;
                else if(flipValue==1)
                    win=2;


            } else if (circle_number[3*0+2] == 0) {
                gamov = 1;
                if(flipValue==0)
                    win = 2;
                else if(flipValue==1)
                    win=1;

            }
        }
    }

    public int checkWinComp() {
        for (int i = 0; i < 3; i++) {
            if ((cross_number[3*i+0] == 0 && cross_number[3*i+1] == 0 && cross_number[3*i+2] == 0) || (circle_number[3*i+0] == 0 && circle_number[3*i+1] == 0 && circle_number[3*i+2] == 0)) {
                if (cross_number[3*i+0] == 0) {

                    if(flipValue==0)
                        return 1;
                    else if(flipValue==1)
                        return 2;

                } else if (circle_number[3*i+0] == 0) {

                    if(flipValue==0)
                        return 2;
                    else if(flipValue==1)
                        return 1;
                }
            }
            if ((cross_number[3*0+i] == 0 && cross_number[3*1+i] == 0 && cross_number[3*2+i] == 0) || (circle_number[3*0+i] == 0 && circle_number[3*1+i] == 0 && circle_number[3*2+i] == 0)) {
                if (cross_number[3*0+i] == 0) {

                    if(flipValue==0)
                        return 1;
                    else if(flipValue==1)
                        return 2;

                } else if (circle_number[3*0+i] == 0) {

                    if(flipValue==0)
                        return 2;
                    else if(flipValue==1)
                        return 1;

                }
            }
        }
        if ((cross_number[3*0+0] == 0 && cross_number[3*1+1] == 0 && cross_number[3*2+2] == 0) || (circle_number[3*0+0] == 0 && circle_number[3*1+1] == 0 && circle_number[3*2+2] == 0)) {
            if (cross_number[3*0+0] == 0) {

                if(flipValue==0)
                    return 1;
                else if(flipValue==1)
                    return 2;


            } else if (circle_number[3*0+0] == 0) {

                if(flipValue==0)
                    return 2;
                else if(flipValue==1)
                    return 1;

            }
        }

        if ((cross_number[3*0+2] == 0 && cross_number[3*1+1] == 0 && cross_number[3*2+0] == 0) || (circle_number[3*0+2] == 0 && circle_number[3*1+1] == 0 && circle_number[3*2+0] == 0)) {
            if (cross_number[3*0+2] == 0) {

                if(flipValue==0)
                    return 1;
                else if(flipValue==1)
                    return 2;

            } else if (circle_number[3*0+2] == 0) {

                if(flipValue==0)
                    return 2;
                else if(flipValue==1)
                    return 1;

            }
        }
        return 0;
    }
}