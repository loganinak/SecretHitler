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
public class VoteResultsFragment extends Fragment {


    public VoteResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vote_results, container, false);
        TextView wonText = (TextView)view.findViewById(R.id.passtoPresident_TextView);
        Player player = getArguments().getParcelable("player");
        wonText.append(player.getPlayerData()[0]);
        return view;
    }

}
