package com.example.secrethitler;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class NamePlayers extends AppCompatActivity {

    ArrayList<Player> group;
    private final String LOG_TAG = NamePlayers.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nameplayers);
    }

    public void endConfirm(View view) {
        Intent data = new Intent();
        EditText name = (EditText) findViewById(R.id.inputName);

        group = getIntent().getParcelableArrayListExtra("group");

        group.get(0).setName(name.getText().toString());
        group.add(group.remove(0));

        data.putParcelableArrayListExtra("group", group);
        setResult(Activity.RESULT_OK, data);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.player_role, new PlayerRole());
    }


    @Override
    public void onBackPressed() {
    }
}
