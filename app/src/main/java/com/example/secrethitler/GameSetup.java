package com.example.secrethitler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class GameSetup extends AppCompatActivity {

    private static final Integer[] playerCount_array = new Integer[]{5, 6, 7, 8, 9, 10};
    private Spinner playerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);

        playerCount = (Spinner)findViewById(R.id.playerCount);
        ArrayAdapter<Integer> playerCount_Adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, playerCount_array);
        playerCount.setAdapter(playerCount_Adapter);

    }


    public void launchGame(View view) {
        Intent intent = new Intent(this, Game.class);
        intent.putExtra("Players", (Integer) playerCount.getSelectedItem());
        GameSetup.this.startActivity(intent);
        finish();
    }
}