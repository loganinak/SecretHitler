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
public class ConfirmPlayerFragment extends Fragment {


    public ConfirmPlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_confirm_player, container, false);

        Player player = getArguments().getParcelable("playerData");
        TextView text = (TextView)view.findViewById(R.id.confirmPlayer_TextView);
        text.append(player.getPlayerData()[0] + '?');

        return view;
    }

}
