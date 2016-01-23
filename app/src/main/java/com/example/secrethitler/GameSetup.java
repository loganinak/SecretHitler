package com.example.secrethitler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class GameSetup extends AppCompatActivity {

    private static final Integer[] playerCount_array = new Integer[]{5, 6, 7, 8, 9, 10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);

        Spinner playerCount = (Spinner)findViewById(R.id.playerCount);
        ArrayAdapter<Integer> playerCount_Adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, playerCount_array);
        playerCount.setAdapter(playerCount_Adapter);
    }
}
