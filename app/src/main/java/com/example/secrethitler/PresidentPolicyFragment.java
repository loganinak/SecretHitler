package com.example.secrethitler;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PresidentPolicyFragment extends Fragment {


    public PresidentPolicyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_president_policy, container, false);

        ArrayList<Integer> cards = getArguments().getIntegerArrayList("cards");

        ((ImageButton)view.findViewById(R.id.president_policy1)).setImageResource(cards.get(0));
        (view.findViewById(R.id.president_policy1)).setTag(cards.get(0));

        ((ImageButton)view.findViewById(R.id.president_policy2)).setImageResource(cards.get(1));
        (view.findViewById(R.id.president_policy2)).setTag(cards.get(1));

        ((ImageButton)view.findViewById(R.id.president_policy3)).setImageResource(cards.get(2));
        (view.findViewById(R.id.president_policy3)).setTag(cards.get(2));


        return view;
    }

}
