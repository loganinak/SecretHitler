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
public class PlayerRoleFragment extends Fragment {


    public PlayerRoleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_role, container, false);
        ImageView roleImage = (ImageView)view.findViewById(R.id.image_role);

        String role = getArguments().getString("role");
        switch(role){
            case "Hitler":
                roleImage.setImageResource(R.drawable.role_hitler);
                break;
            case "fascist":
                roleImage.setImageResource(R.drawable.role_fascist);
                break;
            case "liberal":
                roleImage.setImageResource(R.drawable.role_liberal);
                break;
            default:
                roleImage.setImageResource(R.drawable.policy_back);
                break;
        }


        return view;
    }

}
