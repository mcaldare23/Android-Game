package com.pandopia.pandadestroyer;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MotionEvent;
import java.util.Random;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity  {

    ImageView[][] cell = new ImageView[9][9];
    TextView display;
    TextView display2;
    Integer[][] cellCode = new Integer[9][9];
    Random rand = new Random();
    int initialX, initialY;
    public int level = 1;
    public int goal = 10;
    public int score = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = (TextView) findViewById(R.id.display);
        display2 = (TextView) findViewById(R.id.score);

        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                String ViewID = "cell_" + (i+1) + (j+1);
                int resID = getResources().getIdentifier(ViewID, "id", getPackageName());
                cell[i][j] = ((ImageView) findViewById(resID));
            }
        }
        initialize();
        draw();

    }



    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();
        int temp;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                initialX = getCellX((int)event.getX());
                initialY = getCellY((int)event.getY());
                break;

            case MotionEvent.ACTION_UP:
                int finalX = getCellX((int)event.getX());
                int finalY = getCellY((int)event.getY());

                if (initialX < finalX && initialY == finalY) {
                    temp = cellCode[initialY][initialX];
                    cellCode[initialY][initialX] = cellCode[initialY][initialX + 1];
                    cellCode[initialY][initialX + 1] = temp;
                    if(validMove()) {
                        delete();
                        update();
                        delete();
                        update();
                        draw();

                    }
                    else{
                        temp = cellCode[initialY][initialX];
                        cellCode[initialY][initialX] = cellCode[initialY][initialX + 1];
                        cellCode[initialY][initialX + 1] = temp;
                        Toast.makeText(getApplicationContext(),"Move not valid",Toast.LENGTH_SHORT).show();
                    }
                }
                else if (initialX > finalX && initialY == finalY) {
                    temp = cellCode[initialY][initialX];
                    cellCode[initialY][initialX] = cellCode[initialY][initialX - 1];
                    cellCode[initialY][initialX - 1] = temp;
                    if(validMove()) {
                        delete();
                        update();
                        delete();
                        update();
                        draw();

                    }
                    else{
                        temp = cellCode[initialY][initialX];
                        cellCode[initialY][initialX] = cellCode[initialY][initialX - 1];
                        cellCode[initialY][initialX - 1] = temp;
                        Toast.makeText(getApplicationContext(),"Move not valid",Toast.LENGTH_SHORT).show();
                    }
                }
                else if (initialY < finalY && initialX == finalX) {
                    temp = cellCode[initialY][initialX];
                    cellCode[initialY][initialX] = cellCode[initialY + 1][initialX];
                    cellCode[initialY + 1][initialX] = temp;
                    if(validMove()) {
                        delete();
                        update();
                        delete();
                        update();
                        draw();

                    }
                    else{
                        temp = cellCode[initialY][initialX];
                        cellCode[initialY][initialX] = cellCode[initialY + 1][initialX];
                        cellCode[initialY + 1][initialX] = temp;
                        Toast.makeText(getApplicationContext(),"Move not valid",Toast.LENGTH_SHORT).show();
                    }
                }
                else if (initialY > finalY && initialX == finalX) {
                    temp = cellCode[initialY][initialX];
                    cellCode[initialY][initialX] = cellCode[initialY - 1][initialX];
                    cellCode[initialY - 1][initialX] = temp;
                    if(validMove()) {
                        delete();
                        update();
                        delete();
                        update();
                        draw();

                    }
                    else{
                        temp = cellCode[initialY][initialX];
                        cellCode[initialY][initialX] = cellCode[initialY - 1][initialX];
                        cellCode[initialY - 1][initialX] = temp;
                        Toast.makeText(getApplicationContext(),"Move not valid",Toast.LENGTH_SHORT).show();
                    }
                }



                else{
                    Toast.makeText(getApplicationContext(),"Move not valid",Toast.LENGTH_SHORT).show();
                }

                break;
            case MotionEvent.ACTION_CANCEL:
                display.setText("Action was CANCEL");
                break;
            case MotionEvent.ACTION_OUTSIDE:
                display.setText("Movement occurred outside bounds of current screen element");
                break;
        }
        return super.onTouchEvent(event);
    }

    public void initialize(){

        display.setText("Level " + level + "\t\t\t\tGoal: " + goal);


        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                cellCode[i][j] = rand.nextInt(6) + 1;
                if(i > 1 && cellCode[i][j] == cellCode[i-1][j] && cellCode[i][j] < 6){
                    cellCode[i][j]++;
                }
                if(i > 1 && cellCode[i][j] == cellCode[i-1][j] && cellCode[i][j] == 1){
                    cellCode[i][j]++;
                }
                if(i > 1 && cellCode[i][j] == cellCode[i-1][j] && cellCode[i][j] == 6){
                    cellCode[i][j]--;
                }
                if(j > 1 && cellCode[i][j] == cellCode[i][j-1] && cellCode[i][j] < 6){
                    cellCode[i][j]++;
                }
                if(j > 1 && cellCode[i][j] == cellCode[i][j-1] && cellCode[i][j] == 1){
                    cellCode[i][j]++;
                }
                if(j > 1 && cellCode[i][j] == cellCode[i][j-1] && cellCode[i][j] == 6){
                    cellCode[i][j]--;
                }
            }
        }
    }

    public void delete(){
        int arr[] = new int[9];
        for(int m = 0; m < 9; m++) {
            for (int n = 0; n < 9; n++) {
                arr[n] = cellCode[m][n];
            }


            int temp[] = new int[arr.length];
            int count = 2;

            for (int i = 1; i < arr.length - 1 ; i++) {
                if (arr[i] == arr[i + 1] && arr[i] == arr[i - 1]) {
                    count++;
                }
                if(arr[i] != arr[i+1] && count >=3){
                    for(int j = i - count + 1; j <= i ; j++){
                        temp[j] =  arr[j] * -1;
                    }
                    count = 2;
                }
                else if(arr[i] == arr[i+1] && count >=3 && i == arr.length - 2){
                    for(int j = i - count + 1; j <= i ; j++){
                        temp[j] =  arr[j] * -1;
                    }
                    count = 2;
                }
                else {
                    temp[i] = arr[i];
                }


            }

            if(arr[0] == Math.abs(temp[1])){
                temp[0] = temp[1];
            }
            else{
                temp[0] = arr[0];
            }
            if(arr[arr.length-1] == Math.abs(temp[arr.length-2])){
                temp[arr.length-1] = temp[arr.length-2];
            }
            else{
                temp[arr.length-1] = arr[arr.length-1];
            }

            for (int i = 0; i < arr.length; i++) {
                if(temp[i] < 0){
                    temp[i] = 0;
                }
            }




            for (int n = 0; n < 9; n++) {
                cellCode[m][n] = temp[n];
            }

        }
            for(int m = 0; m < 9; m++) {
                for (int n = 0; n < 9; n++) {
                    arr[n] = cellCode[n][m];
                }


                int temp[] = new int[arr.length];
                int count = 2;
                for (int i = 1; i < arr.length - 1 ; i++) {
                    if (arr[i] == arr[i+1] && arr[i] == arr[i-1]){
                        count++;
                    }
                    if(arr[i] != arr[i+1] && count >=3){
                        for(int j = i - count + 1; j <= i ; j++){
                            temp[j] =  Math.abs(arr[j]) * -1;
                        }
                        count = 2;
                    }
                    else if(arr[i] == arr[i+1] && count >=3 && i == arr.length - 2){
                        for(int j = i - count + 1; j <= i ; j++){
                            temp[j] =  arr[j] * -1;
                        }
                        count = 2;
                    }
                    else {
                        temp[i] = arr[i];
                    }


                }

                if(arr[0] == Math.abs(temp[1])){
                    temp[0] = temp[1];
                }
                else{
                    temp[0] = arr[0];
                }
                if(arr[arr.length-1] == Math.abs(temp[arr.length-2])){
                    temp[arr.length-1] = temp[arr.length-2];
                }
                else{
                    temp[arr.length-1] = arr[arr.length-1];
                }


                for (int n = 0; n < 9; n++) {
                    cellCode[n][m] = temp[n];
                }

        }

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(cellCode[i][j] < 0){
                    cellCode[i][j] = 0;
                }
            }
        }





    }


    public void update(){

     int count = 0;
     while(count < 9) {
         for (int j = 0; j < 9; j++) {
             for (int i = 8; i >= 0; i--) {
                 if (i == 0) {
                     break;
                 } else if (cellCode[i][j] == 0) {
                     cellCode[i][j] = cellCode[i - 1][j];
                     cellCode[i - 1][j] = 0;
                 }
             }
         }
         count++;
     }

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                  if(cellCode[i][j] == 0){
                        score++;
                        cellCode[i][j] = rand.nextInt(6) + 1;;

                }
            }
        }



        if(score < goal) {
            display2.setText("Score:\t\t" + score);

        }
        else{
            display2.setText("New Level!");
            score = 0;
            level++;
            goal = goal*2;
            initialize();
        }
    }

    public void draw(){

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(cellCode[i][j] == 1){
                    cell[i][j].setImageResource(R.drawable.frog);
                }
                else if(cellCode[i][j] == 2){
                    cell[i][j].setImageResource(R.drawable.tigre);
                }
                else if(cellCode[i][j] == 3){
                    cell[i][j].setImageResource(R.drawable.mucca);
                }
                else if(cellCode[i][j] == 4){
                    cell[i][j].setImageResource(R.drawable.dog);
                }
                else if(cellCode[i][j] == 5){
                    cell[i][j].setImageResource(R.drawable.orso);
                }
                else if(cellCode[i][j] == 6){
                    cell[i][j].setImageResource(R.drawable.maiale);
                }
                else if(cellCode[i][j] == 0){
                    cell[i][j].setImageResource(R.drawable.blood);
                }
                else if(cellCode[i][j] == 10){
                    cell[i][j].setImageResource(R.drawable.panda);
                }
                else{
                    cell[i][j].setImageResource(R.drawable.dot);
                }
            }
        }
    }

    int getCellX(int x) {

        int cellX = 0;

        if (x > 58 && x < 122) {
            cellX = 0;
        } else if (x >= 122 && x < 204) {
            cellX = 1;
        } else if (x >= 204 && x < 273) {
            cellX = 2;
        } else if (x >= 273 && x < 344) {
            cellX = 3;
        } else if (x >= 344 && x < 420) {
            cellX = 4;
        } else if (x >= 420 && x < 494) {
            cellX = 5;
        } else if (x >= 494 && x < 574) {
            cellX = 6;
        } else if (x >= 574 && x < 638) {
            cellX = 7;
        } else if (x >= 638 && x < 713) {
            cellX = 8;
        }
        else{
            Toast.makeText(getApplicationContext(),"???",Toast.LENGTH_SHORT).show();
        }
        return cellX;
    }

    int getCellY(int y) {

        int cellY = 0;

        if (y > 487 && y < 555){
            cellY = 0;
        }
        else if (y >= 555 && y < 625){
            cellY = 1;
        }
        else if (y >= 625 && y < 704){
            cellY = 2;
        }
        else if (y >= 704 && y < 783){
            cellY = 3;
        }
        else if (y >= 783 && y < 850){
            cellY = 4;
        }
        else if (y >= 850 && y < 930){
            cellY = 5;
        }
        else if (y >= 930 && y < 1000){
            cellY = 6;
        }
        else if (y >= 1000 && y < 1070){
            cellY = 7;
        }
        else if (y >= 1070 && y < 1140){
            cellY = 8;
        }
        else{
            Toast.makeText(getApplicationContext(),"???",Toast.LENGTH_SHORT).show();
        }
        return cellY;
    }


    boolean validMove() {
        boolean moveOk = false;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                if (cellCode[i][j].equals(cellCode[i + 1][j]) && cellCode[i][j].equals(cellCode[i + 2][j])){
                   moveOk = true;
                   break;
                }
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (cellCode[i][j].equals(cellCode[i][j+1]) && cellCode[i][j].equals(cellCode[i][j+2])){
                    moveOk = true;
                    break;
                }
            }
        }

        if(moveOk) {return true;}
        else {return false;}
    }



}

