package com.example.secrethitler;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Game extends AppCompatActivity {

    private Integer numPlayers;
    int setNamesCode = 2;
    //0 = name, 1 = party, 2 = role, 3 = dead/alive, 4 = vote
    ArrayList<Player> group;
    //index 0 = fascistPoliciesPassed 1 = liberalPoliciesPassed
    int[] gameData = new int[]{0, 0, 0};
    Player chancellor;
    Player president;
    Player previousChancellor;
    Player previousPresident;
    //use R.drawable.PNGNAME to get id of fascist and liberal
    ArrayList<Integer> cards = new ArrayList<Integer>();
    ArrayList<Integer> drawn = new ArrayList<Integer>();
    boolean chancellorTurn = false;
    Player choice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        numPlayers = getIntent().getExtras().getInt("Players");
        System.out.println("You have started the game with " + numPlayers + " players");

        group = new ArrayList<Player>();

        assignParty(numPlayers);

        System.out.println("Players: " + group.size());
        for (Player p : group) {
            System.out.println("Party: " + p.playerData[1] + " Role: " + p.playerData[2]);
        }

        //create policy deck
        resetCards();

        replaceCurrentFragment(new InputNameFragment(), "fragment_container");
    }

    private void assignParty(int numPlayers) {
        int numFascists;
        switch (numPlayers) {
            case 5:
            case 6:
                numFascists = 2;
                group.add(new Player("fascist", "Hitler"));
                break;
            case 7:
            case 8:
                numFascists = 3;
                group.add(new Player("fascist", "Hitler"));
                break;
            case 9:
            case 10:
                numFascists = 4;
                group.add(new Player("fascist", "Hitler"));
                break;
            default:
                numFascists = 20;
                break;
        }

        for (int i = 1; i < numFascists; i++) {
            group.add(new Player("fascist", "fascist"));
        }

        for (int i = 0; i < numPlayers - numFascists; i++) {
            group.add(new Player("liberal", "liberal"));
        }

        Collections.shuffle(group);
    }

    public void confirmName(View view) {
        EditText playerName = (EditText) findViewById(R.id.inputName_EditText);
        Player temp = group.remove(0);
        temp.setName(playerName.getText().toString());

        PlayerRoleFragment playerRoleFragment = new PlayerRoleFragment();

        Bundle role = new Bundle();
        role.putString("role", temp.getPlayerData()[2]);
        playerRoleFragment.setArguments(role);

        replaceCurrentFragment(playerRoleFragment, "fragment_container");

        group.add(temp);
    }

    public void confirmRole(View view) {
        if (group.get(0).getPlayerData()[0] == null) {
            replaceCurrentFragment(new InputNameFragment(), "fragment_container");
        } else {
            startNextPlayerTurn();
        }
    }

    public void replaceCurrentFragment(Fragment fragment, String name) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void startNextPlayerTurn() {
        president = group.get(0);
        Fragment confirmPlayerFragment = new ConfirmPlayerFragment();
        Bundle playerData = new Bundle();
        playerData.putParcelable("playerData", president);
        confirmPlayerFragment.setArguments(playerData);
        replaceCurrentFragment(confirmPlayerFragment, "fragment_container");
    }

    public void playerTurn(View view) {
        if (!chancellorTurn) {
            Bundle players = new Bundle();
            ArrayList eligiblePlayers = new ArrayList();
            for (Player p : group) {
                if (p != previousChancellor && p != previousPresident && p != president && p.getPlayerData()[3] == "alive") {
                    eligiblePlayers.add(p);
                }
            }
            players.putParcelableArrayList("Players", eligiblePlayers);
            Fragment fragment = new ChooseChancellorFragment();
            fragment.setArguments(players);
            replaceCurrentFragment(fragment, "fragment_container");
        } else {
            Bundle chancellorCards = new Bundle();
            chancellorCards.putIntegerArrayList("cards", drawn);
            Fragment chancellorPolicyFragment = new ChancellorPolicyFragment();
            chancellorPolicyFragment.setArguments(chancellorCards);
            replaceCurrentFragment(chancellorPolicyFragment, "fragment_container");
        }
    }

    public void endPlayerTurn() {
        group.add(group.remove(0));
        chancellorTurn = false;
        drawn.clear();
        president = null;
        chancellor = null;
        choice = null;
        resetVotes();
        startNextPlayerTurn();
    }

    public void nextPlayerTurn(View view) {
        endPlayerTurn();
    }

    public void getVotes(View view) {
        //set the chancellor here based on click from fragment_choose_chancellor.xml
        //for now though
        chancellor = choice;
        Bundle candidates = new Bundle();
        candidates.putParcelable("president", president);
        candidates.putParcelable("chancellor", chancellor);
        Fragment voteFragment = new VoteFragment();
        voteFragment.setArguments(candidates);
        replaceCurrentFragment(voteFragment, "fragment_container");
    }

    public void vote(View view) {
        if (group.get(0).getPlayerData()[4] == null) {
            switch (view.getId()) {
                case R.id.nein_Button:
                    group.get(0).setVote(0);
                    break;
                default:
                    group.get(0).setVote(1);
                    break;
            }
            group.add(group.remove(0));
        }
        if (group.get(0).getPlayerData()[4] == null) {
            Bundle candidates = new Bundle();
            candidates.putParcelable("president", president);
            candidates.putParcelable("chancellor", chancellor);
            Fragment voteFragment = new VoteFragment();
            voteFragment.setArguments(candidates);
            replaceCurrentFragment(voteFragment, "fragment_container");
        } else {
            int numJa = 0;
            int numNein = 0;
            for (Player p : group) {
                if (p.getPlayerData()[4].compareTo("ja") == 0) {
                    numJa++;
                } else {
                    numNein++;
                }
            }
            System.out.println(numJa);
            System.out.println(numNein);
            if (numJa > numNein) {
                Player previousChancellor = chancellor;
                Player previousPresident = president;
                chancellorTurn = true;
                Bundle player = new Bundle();
                player.putParcelable("player", group.get(0));
                Fragment voteResultFragment = new VoteResultsFragment();
                voteResultFragment.setArguments(player);
                replaceCurrentFragment(voteResultFragment, "fragment_container");
            } else {
                endPlayerTurn();
            }
        }
    }

    private void resetVotes() {
        for (Player p : group) {
            p.setVote(3);
        }
    }

    public void presidentPolicies(View view) {
        if (cards.size() < 3) {
            resetCards();
        }
        Bundle drawnPolicy = new Bundle();
        drawn.add(cards.remove(0));
        drawn.add(cards.remove(0));
        drawn.add(cards.remove(0));
        drawnPolicy.putIntegerArrayList("cards", drawn);
        Fragment presidentPolicyFragment = new PresidentPolicyFragment();
        presidentPolicyFragment.setArguments(drawnPolicy);
        replaceCurrentFragment(presidentPolicyFragment, "fragmentContainer");
    }

    public void resetCards() {
        cards.clear();
        for (int i = 0; i < 11; i++) {
            cards.add(R.drawable.policy_fascist);
        }
        for (int i = 0; i < 6; i++) {
            cards.add(R.drawable.policy_liberal);
        }
        Collections.shuffle(cards);
    }

    public void discardPolicy(View view) {
        int idClicked;
        ImageButton button = (ImageButton) view.findViewById(view.getId());
        if (button.getTag().equals(R.drawable.policy_fascist)) {
            idClicked = R.drawable.policy_fascist;
        } else {
            idClicked = R.drawable.policy_liberal;
        }
        for (int i = 0; i < 3; i++) {
            if (drawn.get(i) == idClicked) {
                drawn.remove(i);
                i = 4;
            }
        }
        if (drawn.size() == 2) {
            Fragment confirmPlayerFragment = new ConfirmPlayerFragment();
            Bundle playerData = new Bundle();
            playerData.putParcelable("playerData", chancellor);
            confirmPlayerFragment.setArguments(playerData);
            replaceCurrentFragment(confirmPlayerFragment, "fragment_container");
        } else {
            if (drawn.get(0) == R.drawable.policy_fascist) {
                gameData[0]++;
            } else {
                gameData[1]++;
            }
            System.out.println("Fascist policies passed: " + gameData[0]);
            System.out.println("liberal policies passed: " + gameData[1]);
            Bundle idPassed = new Bundle();
            idPassed.putInt("card", drawn.get(0));
            Fragment passedPolicyFragment = new PassedPolicyFragment();
            passedPolicyFragment.setArguments(idPassed);
            replaceCurrentFragment(passedPolicyFragment, "fragment_container");
        }
    }

    public void setChoice(View view) {
        TextView text = (TextView) view.findViewById(R.id.playerText_TextView);
        for (Player p : group) {
            if (p.getPlayerData()[0].compareTo((String) view.getTag()) == 0) {
                choice = p;
            }
        }
    }
    
    public void presPowers(View view) {

    }

}
