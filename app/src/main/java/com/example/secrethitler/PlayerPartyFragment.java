package com.example.secrethitler;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerPartyFragment extends Fragment {


    public PlayerPartyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_party, container, false);

        Player player = getArguments().getParcelable("player");

        if(player.getPlayerData()[1].compareTo("fascist") == 0){
            ((ImageView)view.findViewById(R.id.playerParty_ImageView)).setImageResource(R.drawable.party_fascist);
        } else {
            ((ImageView)view.findViewById(R.id.playerParty_ImageView)).setImageResource(R.drawable.party_liberal);
        }

        return view;
    }

}
