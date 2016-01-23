package com.example.secrethitler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Game extends AppCompatActivity {

    int chancellor = 0;
    int president = 1;
    private Integer numPlayers;
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
            System.out.println("Party: " + p.playerData[1] + " Role: "+ p.playerData[2]);
        }
    }

    public void assignParty(int numPlayers){
        int numFacists;
        switch(numPlayers){
            case 5: case 6:
                numFacists = 2;
                group.add(new Player("fascist", "Hitler"));
                break;
            case 7: case 8:
                numFacists = 3;
                group.add(new Player("fascist", "Hitler"));
                break;
            case 9: case 10:
                numFacists = 4;
                group.add(new Player("fascist", "Hitler"));
                break;
            default:
                numFacists = 20;
                break;
        }

        for(int i = 1; i < numFacists; i++){
            group.add(new Player("fascist", "not Hitler"));
        }

        for(int i = 0; i < numPlayers - numFacists; i++){
            group.add(new Player("liberal", "not Hitler"));
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
