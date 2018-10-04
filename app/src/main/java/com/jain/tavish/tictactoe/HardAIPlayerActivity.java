package com.jain.tavish.tictactoe;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class HardAIPlayerActivity extends AppCompatActivity {

    GridLayout gridLayout;
    ImageView playBoard[][] = new ImageView[3][3];
    char matrix[][] = new char[3][3];
    TextView turnTeller;
    Button button;
    int gameOver = 0;//0 if going on and 1 if over
    char player = 'X', opponent = 'O';
    int turn = 0;//1 for X and 2 for O
    AlertDialog.Builder builder;
    int stepsCount = 0;
    int XwinOrNot = 0;
    int OwinOrNot = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_player);

        button = findViewById(R.id.btn_new_game_comp);
        gridLayout = findViewById(R.id.grid_layout_comp);
        builder = new AlertDialog.Builder(this);
        turnTeller = findViewById(R.id.tv_turn_teller_comp);
        turnTeller.setText("X's turn");

        turn = 1;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame(view);
            }
        });

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = '_';
                playBoard[i][j] = (ImageView) gridLayout.getChildAt(3*i+j);
                final int finalI = i;
                final int finalJ = j;
                playBoard[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        playMove(playBoard[finalI][finalJ]);
                    }
                });
            }
        }
    }

    public void playMove(View view){
        if (isMovesLeft(matrix)) {
            int index = gridLayout.indexOfChild(view);
            int i = index / 3;
            int j = index % 3;

            if(turn == 1 && gameOver == 0 && matrix[i][j] == '_'){
                matrix[i][j] = 'X';
                turnTeller.setText("O's turn");
                Glide.with(this)
                        .load(R.drawable.ic_cross)
                        .into(playBoard[i][j]);
                turn = 2;


                stepsCount++;
                if(isMovesLeft(matrix)) {
                    int computerTurn = findBestMove(matrix);
                    // Dividing by matrix rows count to get the row
                    int row = computerTurn / 3;
                    int col = computerTurn % 3;
                    turnTeller.setText("X's turn");
                    Glide.with(this)
                            .load(R.drawable.ic_circle)
                            .into(playBoard[row][col]);
                    stepsCount++;
                    matrix[row][col] = 'O';
                    turn = 1;
                }
            }else if(turn == 2 && gameOver == 0 && matrix[i][j] == '_'){
                matrix[i][j] = 'O';
                stepsCount++;
                turnTeller.setText("X's turn");
                Glide.with(this)
                        .load(R.drawable.ic_circle)
                        .into(playBoard[i][j]);
                turn = 1;
                if(isMovesLeft(matrix)) {
                    int computerTurn = findBestMove(matrix);
                    int row = computerTurn / 3;
                    int col = computerTurn % 3;
                    stepsCount++;
                    turnTeller.setText("O's turn");
                    Glide.with(this)
                            .load(R.drawable.ic_cross)
                            .into(playBoard[row][col]);
                    matrix[row][col] = 'X';
                    turn = 2;
                }
            }

        }

        checkWin();

        if (gameOver == 1) {
            if (XwinOrNot == 1) {
                builder.setTitle("X wins !!");
            } else if (OwinOrNot == 1) {
                builder.setTitle("O wins !!");
            } else if(XwinOrNot == 0 && OwinOrNot == 0 && (stepsCount == 9 || stepsCount == 10)){
                builder.setTitle("Match Drawn !!");
            }
            builder.setPositiveButton("NEW GAME",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    newGame(new View(getApplicationContext()));
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else if (gameOver == 0){
            if(XwinOrNot == 0 && OwinOrNot == 0 && (stepsCount == 9 || stepsCount == 10)){
                builder.setTitle("Match Drawn !!");
            }
        }

    }

    public void newGame(View view) {

        XwinOrNot = 0;
        OwinOrNot = 0;
        stepsCount = 0;
        gameOver = 0;
        turn=1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = '_';
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playBoard[i][j].setEnabled(true);
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Glide.with(this).load(0).into(playBoard[i][j]);
            }
        }
    }

    public void checkWin(){

        int array[] = {-1,-1,-1,-1,-1,-1,-1,-1,-1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(matrix[i][j] == 'X') {
                    array[3 * i + j] = 1;
                }else if(matrix[i][j] == 'O'){
                    array[3 * i + j] = 2;
                }
            }
        }
        //0 if lose 1 if win
        XwinOrNot = checkAllCases(array, 1);
        OwinOrNot = checkAllCases(array, 2);

        if(XwinOrNot == 1 || OwinOrNot == 1 || stepsCount == 9){
            gameOver = 1;
        }

    }

    int checkAllCases(int array[], int player){
        if(array[0] == player && array[1] == player && array[2] == player){
            return 1;
        }else  if(array[3] == player && array[4] == player && array[5] == player){
            return 1;
        }else  if(array[6] == player && array[7] == player && array[8] == player){
            return 1;
        }else  if(array[0] == player && array[3] == player && array[6] == player){
            return 1;
        }else  if(array[1] == player && array[4] == player && array[7] == player){
            return 1;
        }else  if(array[2] == player && array[5] == player && array[8] == player){
            return 1;
        }else  if(array[0] == player && array[4] == player && array[8] == player){
            return 1;
        }else  if(array[2] == player && array[4] == player && array[6] == player){
            return 1;
        }
        return 0;
    }

    boolean isMovesLeft(char board[][])
    {
        for (int i = 0; i<3; i++)
            for (int j = 0; j<3; j++)
                if (board[i][j]=='_')
                    return true;
        return false;
    }

    int evaluate(char b[][])
    {
        // Checking for Rows for X or O victory.
        for (int row = 0; row<3; row++)
        {
            if (b[row][0]==b[row][1] &&
                    b[row][1]==b[row][2])
            {
                if (b[row][0]==player)
                    return +10;
                else if (b[row][0]==opponent)
                    return -10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col<3; col++)
        {
            if (b[0][col]==b[1][col] &&
                    b[1][col]==b[2][col])
            {
                if (b[0][col]==player)
                    return +10;

                else if (b[0][col]==opponent)
                    return -10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (b[0][0]==b[1][1] && b[1][1]==b[2][2])
        {
            if (b[0][0]==player)
                return +10;
            else if (b[0][0]==opponent)
                return -10;
        }

        if (b[0][2]==b[1][1] && b[1][1]==b[2][0])
        {
            if (b[0][2]==player)
                return +10;
            else if (b[0][2]==opponent)
                return -10;
        }

        // Else if none of them have won then return 0
        return 0;
    }

    // This is the minimax function. It considers all
    // the possible ways the game can go and returns
    // the value of the board
    int minimax(char board[][], int depth, boolean isMax)
    {
        int score = evaluate(board);

        // If Maximizer has won the game return his/her
        // evaluated score
        if (score == 10)
            return score;

        // If Minimizer has won the game return his/her
        // evaluated score
        if (score == -10)
            return score;

        // If there are no more moves and no winner then
        // it is a tie
        if (!isMovesLeft(board))
            return 0;

        // If this maximizer's move
        if (isMax)
        {
            int best = -1000;

            // Traverse all cells
            for (int i = 0; i<3; i++)
            {
                for (int j = 0; j<3; j++)
                {
                    // Check if cell is empty
                    if (board[i][j]=='_')
                    {
                        // Make the move
                        board[i][j] = player;

                        // Call minimax recursively and choose
                        // the maximum value
                        // TODO
                        best = max( best,
                                minimax(board, depth+1, !isMax) );

                        // Undo the move
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }

        // If this minimizer's move
        else
        {
            int best = 1000;

            // Traverse all cells
            for (int i = 0; i<3; i++)
            {
                for (int j = 0; j<3; j++)
                {
                    // Check if cell is empty
                    if (board[i][j]=='_')
                    {
                        // Make the move
                        board[i][j] = opponent;

                        // Call minimax recursively and choose
                        // the minimum value
                        //TODO
                        best = min(best,
                                minimax(board, depth+1, !isMax));

                        // Undo the move
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }
    }

    // This will return the best possible move for the opponent
    int findBestMove(char board[][]){

        int bestVal = 1000;
        int row = -1;
        int col = -1;

        // Traverse all cells, evalutae minimax function for
        // all empty cells. And return the cell with optimal
        // value.
        for (int i = 0; i<3; i++)
        {
            for (int j = 0; j<3; j++)
            {
                // Check if cell is empty
                if (board[i][j]=='_')
                {
                    // Make the move
                    board[i][j] = opponent;

                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(board, 0, true);

                    // Undo the move
                    board[i][j] = '_';

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal < bestVal)
                    {
                        row = i;
                        col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }


        return 3*row + col;

    }


}