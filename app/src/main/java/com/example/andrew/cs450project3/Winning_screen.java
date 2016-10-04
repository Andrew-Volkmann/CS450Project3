package com.example.andrew.cs450project3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Winning_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning_screen);
        Intent mIntent = getIntent();
        int tries = mIntent.getIntExtra("intCount", 0);
        TextView count = (TextView) findViewById(R.id.final_count);
        count.setText(tries+ "");
        Button button = (Button) findViewById(R.id.playagian);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newIntent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(newIntent);

            }
        });

    }

}
