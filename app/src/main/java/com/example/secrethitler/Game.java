package com.example.secrethitler;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class Game extends AppCompatActivity {

    private Integer numPlayers;
    int setNamesCode = 2;
    ArrayList<Player> group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        numPlayers =  getIntent().getExtras().getInt("Players");
        System.out.println("You have started the game with " + numPlayers + " players");

        group = new ArrayList<Player>();

        assignParty(numPlayers);

        System.out.println("Players: " + group.size());
        for(Player p : group){
            System.out.println("Party: " + p.playerData[1] + " Role: " + p.playerData[2]);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        InputNameFragment inputNameFragment = new InputNameFragment();
        fragmentTransaction.add(R.id.fragment_container, inputNameFragment, "inputName_Fragment");
        fragmentTransaction.commit();
    }

    public void confirmName(View view) {
        System.out.println("---------------------------------------------------------------------");

        EditText playerName = (EditText)findViewById(R.id.inputName_EditText);
        Player temp = group.remove(0);
        temp.setName(playerName.getText().toString());

        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlayerRoleFragment playerRoleFragment = new PlayerRoleFragment();

        Bundle role = new Bundle();
        role.putString("role", temp.getPlayerData()[2]);
        playerRoleFragment.setArguments(role);

        fragmentTransaction.replace(R.id.fragment_container, playerRoleFragment, "playerRole_Fragment");
        fragmentTransaction.commit();

        group.add(temp);
    }

    public void confirmRole(View view){
        if(group.get(0).getPlayerData()[0] == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            InputNameFragment inputNameFragment = new InputNameFragment();
            fragmentTransaction.replace(R.id.fragment_container, inputNameFragment, "inputName_Fragment");
            fragmentTransaction.commit();
        }
    }
    public void assignParty(int numPlayers){
        int numFascists;
        switch(numPlayers){
            case 5: case 6:
                numFascists = 2;
                group.add(new Player("fascist", "Hitler"));
                break;
            case 7: case 8:
                numFascists = 3;
                group.add(new Player("fascist", "Hitler"));
                break;
            case 9: case 10:
                numFascists = 4;
                group.add(new Player("fascist", "Hitler"));
                break;
            default:
                numFascists = 20;
                break;
        }

        for(int i = 1; i < numFascists; i++){
            group.add(new Player("fascist", "fascist"));
        }

        for(int i = 0; i < numPlayers - numFascists; i++){
            group.add(new Player("liberal", "liberal"));
        }

        group = shuffle(group);
    }

    public ArrayList shuffle(ArrayList list){
        ArrayList temp = new ArrayList();
        while(!list.isEmpty()){
            temp.add(list.remove((int) (Math.random() * list.size())));
        }
        return temp;
    }
}
