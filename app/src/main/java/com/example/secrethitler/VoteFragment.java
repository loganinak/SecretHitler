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
public class VoteFragment extends Fragment {


    public VoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vote, container, false);
        TextView text = (TextView)view.findViewById(R.id.votingInfo_TextView);
        Player president = getArguments().getParcelable("president");
        Player chancellor = getArguments().getParcelable("chancellor");
        text.append(president.getPlayerData()[0] + " as President with " + chancellor.getPlayerData()[0] + " as Chancellor");
        return view;
    }

}
