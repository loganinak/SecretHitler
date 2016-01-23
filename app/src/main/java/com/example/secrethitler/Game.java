package com.example.secrethitler;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

            Intent intent = new Intent(this, NamePlayers.class);
            intent.putParcelableArrayListExtra("group", group);
            startActivityForResult(intent, setNamesCode);
    }

    @Override
    public void onPause(){
        super.onPause();
        System.out.println("pausing.....................................................");
    }

    public void onResume(){
        super.onResume();
        System.out.println("resuming.....................................................");
        System.out.println("Players: " + group.size());
        for(Player p : group){
            System.out.println("Party: " + p.playerData[1] + " Role: " + p.playerData[2] + " name: " + p.playerData[0]);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == setNamesCode) {
            if (resultCode == Activity.RESULT_OK) {
                group = data.getParcelableArrayListExtra("group");
                if(group.get(0).getPlayerData()[0] == null){
                    Intent intent = new Intent(this, NamePlayers.class);
                    intent.putParcelableArrayListExtra("group", group);
                    startActivityForResult(intent, setNamesCode);
                }
            }
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
            group.add(new Player("fascist", "not Hitler"));
        }

        for(int i = 0; i < numPlayers - numFascists; i++){
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
