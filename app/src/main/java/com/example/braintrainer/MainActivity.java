package com.example.braintrainer;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    long millisInFuture = 30000;
    long countDownInterval = 1000;
    int questionCounter=0;
    int correctAnswerCount =0;
    int answer;
    boolean timeOut = false;

    CountDownTimer countDownTimer;
    TextView counterTextView,scoreTextView, questionTextView,firstTextView, secondTextView, thirdTextView, fourthTextView;
    Button reTryButton, goButton;
    android.support.v7.widget.GridLayout gridLayout;
    ConstraintLayout gameLayout;

    public void goToGame(View view){
        view.setVisibility(View.GONE);
        gameLayout.setVisibility(View.VISIBLE);
        generateRandomQuestion();
        counter();
    }

    public void counter(){
         countDownTimer = new CountDownTimer(millisInFuture+100,countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                counterTextView.setText( (millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
               reTryButton.setVisibility(View.VISIBLE);
               timeOut =true;
            }
        }.start();
    }

    public void generateRandomQuestion() {
        questionCounter++;
        Random random = new Random();
        int firstNum = random.nextInt(90) + 10;
        int secondNum = random.nextInt(90) + 10;
        questionTextView.setText(firstNum + "+" + secondNum);
        answer = firstNum + secondNum;
        int randomSelection = random.nextInt(4);

        for (int i = 0; i < 4; i++) {
            int j = random.nextInt(190) + 10;
            if (i == randomSelection) {
                setGridTexts(randomSelection, (firstNum + secondNum));
            } else {
                while (j == answer) { //ignore repetitive answers
                    j = random.nextInt(190) + 10;
                }
                setGridTexts(i, j);
            }
        }
    }

    public void setGridTexts(int tag,int value){
        if(tag == 0) {
            firstTextView.setText(value+"");

        }
        else if( tag == 1){
            secondTextView.setText(value+"");

        }
        else if (tag == 2){
            thirdTextView.setText(value+"");

        }
        else if (tag == 3){
            fourthTextView.setText(value+"");
        }
    }

    public void select(final View view)  {
        final TextView textView = (TextView) view;
        if(timeOut == false) {
            if (Integer.parseInt(textView.getText().toString()) == answer) {
                correctAnswerCount++;
                textView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                textView.postDelayed(new Runnable() { //delay for showing green color for correct answer
                    @Override
                    public void run() {
                        textView.setBackgroundResource(R.drawable.back);
                        scoreTextView.setText(correctAnswerCount+"/"+questionCounter);
                        generateRandomQuestion();
                    }
                },250);
            }
            else {
                scoreTextView.setText(correctAnswerCount + "/" + questionCounter);
                generateRandomQuestion();
            }
        }
    }

    public void reTry(View view) {
        counterTextView.setText(millisInFuture+"s");
        scoreTextView.setText("0/0");
        correctAnswerCount = 0;
        questionCounter =0;
        timeOut = false;
        generateRandomQuestion();
        counter();
        reTryButton.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterTextView = findViewById(R.id.counterTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        reTryButton = findViewById(R.id.reTryButton);
        questionTextView = findViewById(R.id.questionTextView);
        gridLayout = findViewById(R.id.gridLayout);
        firstTextView= findViewById(R.id.firstTextView);
        secondTextView = findViewById(R.id.secondTextView);
        thirdTextView = findViewById(R.id.thirdTextView);
        fourthTextView = findViewById(R.id.fourthTextView);
        goButton = findViewById(R.id.goButton);
        gameLayout = findViewById(R.id.gameLayout);

        gameLayout.setVisibility(View.GONE);
        goButton.setVisibility(View.VISIBLE);
    }
}
