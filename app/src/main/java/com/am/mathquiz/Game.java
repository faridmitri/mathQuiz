package com.am.mathquiz;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;



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
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        score = findViewById(R.id.scorenum);
        life = findViewById(R.id.lifenum);
        time = findViewById(R.id.timenum);
        question = findViewById(R.id.questid);
        ok = findViewById(R.id.okid);
        nextquest = findViewById(R.id.nextid);
        answer = findViewById(R.id.answerid);
        answer.setRawInputType(Configuration.KEYBOARD_12KEY);
        answer.requestFocus();
        if(answer.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        gameContinue();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ok.setEnabled(false);
                nextquest.setEnabled(true);

                String text = answer.getText().toString();
                try {
                    useranswer = Integer.valueOf(text);
                } catch (NumberFormatException e) {
                    useranswer = 0;
                }
                // useranswer = Integer.valueOf(answer.getText().toString());
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

               if (userlife == 0)
               {
                   Toast.makeText(getApplicationContext(),"Game Over",Toast.LENGTH_LONG).show();
                   Intent intent = new Intent(Game.this,Result.class);
                   intent.putExtra("scor",userscore);
                   startActivity(intent);
                   finish();
                   if (mInterstitialAd.isLoaded()) {
                       mInterstitialAd.show();
                   } else {
                       Log.d("TAG", "The interstitial wasn't loaded yet.");
                   }
               }
                  else
               {
                  gameContinue();
               }
            }
        });
    }

    public void gameContinue() {
        ok.setEnabled(true);
        nextquest.setEnabled(false);
        startTimer();

        String calcul;
        Intent intent = getIntent();
        calcul = intent.getStringExtra("calcul");
         switch (calcul) {
             case "plus":
                 number1 = random.nextInt(100);
                 number2 = random.nextInt(100);
                 realanswer = number1 + number2;
                 question.setText(number1 + "+" + number2);
                 break;
             case "minus":
                 number1 = random.nextInt(100);
                 number2 = random.nextInt(100);
                 if (number1 > number2) {
                     realanswer = number1 - number2;
                     question.setText(number1 + "-" + number2);
                 } else {
                     realanswer = number2 - number1;
                     question.setText(number2 + "-" + number1);
                 }
                 break;
             case "mult":
                 number1 = random.nextInt(20);
                 number2 = random.nextInt(20);
                 realanswer = number1 * number2;
                 question.setText(number1 + "*" + number2);
                 break;
             case "div":
                 number1 = random.nextInt(400)+1;
                 number2 = random.nextInt(400)+1;
                 if (number1 > number2) {
                     if (number1 % number2 == 0) {
                         realanswer = number1 / number2;
                         question.setText(number1 + "/" + number2);
                     } else {gameContinue();}

                 } else {
                     if (number2 % number1 == 0) {
                         realanswer = number2 / number1;
                         question.setText(number2 + "/" + number1);
                     } else {gameContinue();}

                 }
                 break;
         }


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