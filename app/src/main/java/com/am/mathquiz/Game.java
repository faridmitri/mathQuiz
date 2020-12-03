package com.am.mathquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.accessibility.AccessibilityViewCommand;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class Game extends AppCompatActivity {

    TextView score;
    TextView life;
    TextView time;
    TextView question;
    EditText answer;
    Button ok;
    Button nextquest;
    Random random = new Random();
    int number1;
    int number2;
    int useranswer;
    int realanswer;
    int userscore = 0;
    int userlife = 3;
    CountDownTimer timer;
    private static final long START_TIMER_IN_MILIS = 30000;
    boolean timer_running;
    long TIME_LEFT_IN_MILIS = START_TIMER_IN_MILIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        score = findViewById(R.id.scorenum);
        life = findViewById(R.id.lifenum);
        time = findViewById(R.id.timenum);
        question = findViewById(R.id.questid);
        ok = findViewById(R.id.okid);
        nextquest = findViewById(R.id.nextid);
        answer = findViewById(R.id.answerid);

        gameContinue();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ok.setEnabled(false);
                nextquest.setEnabled(true);
                useranswer = Integer.valueOf(answer.getText().toString());
                pauseTimer();
                if (useranswer == realanswer) {
                    userscore = userscore + 1;
                    question.setText("Correct Answer");
                    score.setText(String.format("%d", userscore));
                } else {
                    userlife = userlife - 1;
                    question.setText("Wrong Answer, Correct answer is: " + realanswer);
                   life.setText(String.format("%d", userlife));
                }

            }
        });

        nextquest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
                answer.setText("");
               gameContinue();

            }
        });
    }

    public void gameContinue() {
        ok.setEnabled(true);
        nextquest.setEnabled(false);
        startTimer();
        number1 = random.nextInt(100);
        number2 = random.nextInt(100);
        realanswer = number1 + number2;
        question.setText(number1 + "+" + number2);
    }

    public void startTimer() {
        timer = new CountDownTimer(TIME_LEFT_IN_MILIS,1000) {
            @Override
            public void onTick(long l) {
                TIME_LEFT_IN_MILIS = l;
                updateText();
            }

            @Override
            public void onFinish() {
                timer_running = false;
                pauseTimer();
                resetTimer();
                updateText();
                userlife = userlife - 1;
                question.setText("Time is up");
                ok.setEnabled(false);
                nextquest.setEnabled(true);

            }
        }.start();
        timer_running = true;
    }

    public void updateText()
    {
       int second = (int)(TIME_LEFT_IN_MILIS/1000)%60;
       //String timeleft = String.format(Local.getDefault(),"%02d",second);
        String timeleft = (String.format("%02d",second));
        time.setText(timeleft);
        life.setText(String.format("%d", userlife));

    }

    public void pauseTimer()
    {
       timer.cancel();
       timer_running = false;
    }

    public void resetTimer()
    {
      TIME_LEFT_IN_MILIS = START_TIMER_IN_MILIS;
      updateText();
    }
}