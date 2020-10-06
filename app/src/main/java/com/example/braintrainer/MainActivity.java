package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    GridLayout optionsGridLayout;
    Button startButton;
    TextView questionTextView;
    Button option1Button;
    Button option2Button;
    Button option3Button;
    Button option4Button;
    TextView resultTextView;
    TextView timerTextView;
    CountDownTimer timer;
    TextView scoreTextView;
    int correctAnswerIndex;
    int options[] = new int[4];
    int score = 0;
    int total = 0;

    void createAPuzzle(){

        Random random = new Random();
        char operators[] = { '*', '+', '-'};

        int number1, number2, operator;
        number1 = random.nextInt(50);
        number2 = random.nextInt(50);
        operator = random.nextInt(3);

        String number1InString = Integer.toString(number1);
        String number2InString = Integer.toString(number2);
        char operationToBePerformed = operators[operator];

        int correctAnswer = getANumber(number1, number2, operationToBePerformed);

        String correctAnswerToString = Integer.toString(correctAnswer);


        questionTextView.setText(number1InString+operationToBePerformed+number2InString);




        correctAnswerIndex = random.nextInt(4);
        options[correctAnswerIndex] = correctAnswer;
        for( int i = 0; i < 3; i++ ){
            int disperancy = random.nextInt(5);
            char operatordisperancy = operators[random.nextInt(3)];
            int viableoption = getANumber(correctAnswer, disperancy, operatordisperancy);
            if( viableoption == correctAnswer ){
                i--;
                continue;
            }
            if( options[i] != 0 && (i+1) != correctAnswerIndex ){
                options[i+1] = viableoption;
            }else{
                options[i] = viableoption;
            }
        }
        for( int i = 0; i < 3; i++ ){
            if( i != correctAnswerIndex ){
                for( int j = i+1; j < 4; j++ ){
                    if( options[i] == options[j] && j!= correctAnswerIndex ){
                        options[j] += 3;
                    }
                }
            }
        }

        option1Button.setText(Integer.toString(options[0]));
        option2Button.setText(Integer.toString(options[1]));
        option3Button.setText(Integer.toString(options[2]));
        option4Button.setText(Integer.toString(options[3]));
        timer.cancel();
        timerTextView.setText("30s");
        timer.start();


    }

    int getANumber( int number1, int number2, char operator ){
        int correctAnswer = 0;
        switch( operator ){
            case '+' :
                correctAnswer = number1 + number2;
                break;
            case '-' :
                correctAnswer = number1 - number2;
                break;
            case '*' :
                correctAnswer = number1 * number2;
                break;
        }
        return correctAnswer;
    }

    public void startGame(View view){
        startButton.setVisibility(View.INVISIBLE);
        timerTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        questionTextView.setVisibility(View.VISIBLE);
        optionsGridLayout.setVisibility(View.VISIBLE);
        resultTextView.setVisibility(View.VISIBLE);
        createAPuzzle();
    }

    public void checkAnswer(View view) {

        if (view.getTag().toString().equals(Integer.toString(correctAnswerIndex))) {
            score++;
            resultTextView.setText("Correct!");
        }else{
            resultTextView.setText("Incorrect!");
        }
        total++;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(total));
        createAPuzzle();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        optionsGridLayout = (GridLayout)(findViewById(R.id.optionsGridLayout));
        scoreTextView = (TextView)findViewById(R.id.scoreTextView);
        startButton = (Button)(findViewById(R.id.startButton));
        questionTextView = (TextView)(findViewById(R.id.questionTextView));
        option1Button = (Button)(findViewById(R.id.option1Button));
        option2Button = (Button)(findViewById(R.id.option2Button));
        option3Button = (Button)(findViewById(R.id.option3Button));
        option4Button = (Button)(findViewById(R.id.option4Button));
        resultTextView = (TextView) (findViewById(R.id.resultTextView));
        timerTextView = (TextView) (findViewById(R.id.timerTextView));
        timer = new CountDownTimer(30000, 1000) {

            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                timerTextView.setText("30s");
                resultTextView.setText("Time is Over!");

            }
        };
        createAPuzzle();
        timer.start();

    }
}