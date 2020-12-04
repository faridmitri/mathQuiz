package com.am.mathquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Result extends AppCompatActivity {

    Button replay;
    Button exit;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        replay = findViewById(R.id.playagain);
        exit = findViewById(R.id.exit);
        result = findViewById(R.id.result);
        int scor = 0;
        Intent intent = getIntent();
        scor = intent.getIntExtra("scor",0);
        String userscor = String.valueOf(scor);
        result.setText("Your Score Is: "+ userscor);

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Result.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
            }
        });

    }
}