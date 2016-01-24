package com.example.secrethitler;

        import android.os.Parcel;
        import android.os.Parcelable;

        import java.io.Serializable;

/**
 * Created by Logan on 1/22/2016.
 */
public class Player implements Parcelable {
    String[] playerData = new String[5];

    public Player(String party, String role){
        playerData[1] = party;
        playerData[2] = role;
        playerData[3] = "alive";
    }

    public void setName(String name){
        playerData[0] = name;
    }

    public String[] getPlayerData(){
        return playerData;
    }

    public void setVote(boolean voteYes){
        if(voteYes){
            playerData[4] = "ja";
        }else{
            playerData[4] = "nein";
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(this.playerData);
    }

    private Player(Parcel in) {
        this.playerData = in.createStringArray();
    }

    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
