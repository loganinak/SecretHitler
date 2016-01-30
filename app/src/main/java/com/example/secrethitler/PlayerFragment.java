package com.example.secrethitler;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment {


    public PlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        Player player = getArguments().getParcelable("player");
        ((TextView)view.findViewById(R.id.playerText_TextView)).append(player.getPlayerData()[0]);
        view.findViewById(R.id.playerFragemt_Button).setTag(player.getPlayerData()[0]);
        return view;
    }

}
