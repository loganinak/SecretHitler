package com.example.secrethitler;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class PlayerRole extends android.app.Fragment implements View.OnClickListener {

    public PlayerRole() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_role, container, false);
    }

    public void confirm(){
        System.out.println("-------------------------------------------------------------------------------------------------------");
    }

    @Override
    public void onClick(View v) {
    }
}
