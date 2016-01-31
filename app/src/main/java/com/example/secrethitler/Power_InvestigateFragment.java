package com.example.secrethitler;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.secrethitler.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Power_InvestigateFragment extends Fragment {
    FragmentActivity myContext;

    public Power_InvestigateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_power__investigate, container, false);

        ArrayList<Player> players = getArguments().getParcelableArrayList("players");
        android.support.v4.app.FragmentTransaction fragmentTransaction = myContext.getSupportFragmentManager().beginTransaction();
        LinearLayout layout = (LinearLayout)view.findViewById(R.id.linearLayout);
        for(Player p: players){
            Bundle player = new Bundle();
            player.putParcelable("player", p);
            Fragment fragment = new PlayerFragment();
            fragment.setArguments(player);
            fragmentTransaction.add(layout.getId(), fragment, "fragment");
        }
        fragmentTransaction.commit();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

}
