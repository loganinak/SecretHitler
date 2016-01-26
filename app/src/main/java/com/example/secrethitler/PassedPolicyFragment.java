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
public class PassedPolicyFragment extends Fragment {


    public PassedPolicyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passed_policy, container, false);
        ((ImageView)view.findViewById(R.id.passedPolicy_ImageView)).setImageResource(getArguments().getInt("card"));
        return view;
    }

}
