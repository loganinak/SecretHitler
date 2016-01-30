package com.example.secrethitler;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Power_TopThreeFragment extends Fragment {


    public Power_TopThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_power__top_three, container, false);

        ArrayList<Integer> cards = getArguments().getIntegerArrayList("cards");

        ((ImageView)view.findViewById(R.id.topThree_policy1)).setImageResource(cards.get(0));
        ((ImageView)view.findViewById(R.id.topThree_policy2)).setImageResource(cards.get(1));
        ((ImageView)view.findViewById(R.id.topThree_policy3)).setImageResource(cards.get(2));

        return view;
    }

}
