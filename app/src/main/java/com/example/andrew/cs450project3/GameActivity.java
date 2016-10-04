package com.example.andrew.cs450project3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class GameActivity extends AppCompatActivity {

    private GameFragment gameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameFragment = (GameFragment) getFragmentManager().findFragmentById(R.id.game_fragment);





    }
    public void resetGame(){
        gameFragment.reset();
    }

    public void startThinking(){
        View thinkView = findViewById(R.id.thinking);
        thinkView.setVisibility(View.VISIBLE);
    }
    public void stopThinking(){
        View thinkView = findViewById(R.id.thinking);
        thinkView.setVisibility(View.GONE);
    }

}
