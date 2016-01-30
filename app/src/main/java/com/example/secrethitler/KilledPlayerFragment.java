package com.example.secrethitler;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class KilledPlayerFragment extends Fragment {


    public KilledPlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_killed_player, container, false);
        Player player = getArguments().getParcelable("player");
        ((TextView)view.findViewById(R.id.playerKilled_TextView)).append(player.getPlayerData()[0] + "!");
        return view;
    }

}
