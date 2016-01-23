package com.example.secrethitler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Game extends AppCompatActivity {

    private Integer players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        players =  getIntent().getExtras().getInt("Players");
        System.out.println("You have started the game with " + players + " players");
    }
}
