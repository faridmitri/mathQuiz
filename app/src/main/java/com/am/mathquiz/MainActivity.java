package com.am.mathquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button addition;
    Button substruction;
    Button multiplication;
    Button division;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addition = findViewById(R.id.addbutton);
        substruction = findViewById(R.id.subbutton);
        multiplication = findViewById(R.id.mulbutton);
        division = findViewById(R.id.divbutton);

        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Game.class);
                intent.putExtra("calcul","plus");
                startActivity(intent);
            }
        });

        substruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Game.class);
                intent.putExtra("calcul","minus");
                startActivity(intent);
            }
        });

        multiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Game.class);
                intent.putExtra("calcul","mult");
                startActivity(intent);
            }
        });

        division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Game.class);
                intent.putExtra("calcul","div");
                startActivity(intent);
            }
        });
    }
}