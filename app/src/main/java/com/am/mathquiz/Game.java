package com.am.mathquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.accessibility.AccessibilityViewCommand;

import android.os.Bundle;
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
                nextquest.setEnabled(true);
                useranswer = Integer.valueOf(answer.getText().toString());
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
                answer.setText("");
             gameContinue();
            }
        });
    }

    public void gameContinue() {
        nextquest.setEnabled(false);
        number1 = random.nextInt(100);
        number2 = random.nextInt(100);
        realanswer = number1 + number2;
        question.setText(number1 + "+" + number2);
    }
}