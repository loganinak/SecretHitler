package com.example.secrethitler;

/**
 * Created by Logan on 1/22/2016.
 */
public class Player {
    String[] playerData = new String[3];

    public Player(String party, String role){
        playerData[1] = party;
        playerData[2] = role;
    }

    public void setName(String name){
        playerData[0] = name;
    }

    public String[] getPlayerData(){
        return playerData;
    }
}
