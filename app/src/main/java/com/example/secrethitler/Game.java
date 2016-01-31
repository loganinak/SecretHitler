package com.example.secrethitler;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.Collections;

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
    int turnID = 0;
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
        if (turnID == 0) {
            Bundle players = new Bundle();
            ArrayList eligiblePlayers = new ArrayList();
            for (Player p : group) {
                if (!p.equals(previousChancellor) && !p.equals(previousPresident) && p != president) {
                    eligiblePlayers.add(p);
                }
            }
            players.putParcelableArrayList("Players", eligiblePlayers);
            Fragment fragment = new ChooseChancellorFragment();
            fragment.setArguments(players);
            replaceCurrentFragment(fragment, "fragment_container");
        } else if(turnID == 1){
            Bundle chancellorCards = new Bundle();
            chancellorCards.putIntegerArrayList("cards", drawn);
            Fragment chancellorPolicyFragment = new ChancellorPolicyFragment();
            chancellorPolicyFragment.setArguments(chancellorCards);
            replaceCurrentFragment(chancellorPolicyFragment, "fragment_container");
        } else if(turnID == 2){
            if (cards.size() < 3) {
                resetCards();
            }
            Bundle topCards = new Bundle();
            topCards.putIntegerArrayList("cards", cards);
            Fragment fragment = new Power_TopThreeFragment();
            fragment.setArguments(topCards);
            replaceCurrentFragment(fragment, "fragmentContainer");
        }
    }

    public void endPlayerTurn() {
        group.add(group.remove(0));
        turnID = 0;
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
                if(gameData[0] >=3 && chancellor.getPlayerData()[2].compareTo("Hitler") == 0){
                    gameOver("fascists");
                } else {
                    previousChancellor = chancellor;
                    previousPresident = president;
                    turnID = 1;
                    Bundle player = new Bundle();
                    player.putParcelable("player", group.get(0));
                    Fragment voteResultFragment = new VoteResultsFragment();
                    voteResultFragment.setArguments(player);
                    replaceCurrentFragment(voteResultFragment, "fragment_container");
                }
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
        for (int i = 0; i < 11 - gameData[0]; i++) {
            cards.add(R.drawable.policy_fascist);
        }
        for (int i = 0; i < 6 - gameData[1]; i++) {
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

            if(gameData[0] >= 6){
                gameOver("fascists");
            } else if(gameData[1] >= 5){
                gameOver("liberals");
            } else {
                Bundle idPassed = new Bundle();
                idPassed.putInt("card", drawn.get(0));
                Fragment passedPolicyFragment = new PassedPolicyFragment();
                passedPolicyFragment.setArguments(idPassed);
                replaceCurrentFragment(passedPolicyFragment, "fragment_container");
            }
        }
    }

    public void setChoice(View view) {
        for (Player p : group) {
            if (p.getPlayerData()[0].equals(view.getTag())) {
                choice = p;
            }
        }
    }

    public void presPowers(View view) {
        if(view.getTag().equals(R.drawable.policy_fascist)) {
            switch (numPlayers) {
                case 5:
                case 6:
                    switch (gameData[0]){
                        case 3:
                            power_topThree();
                            break;
                        case 4:
                        case 5:
                            power_kill();
                            break;
                        default:
                            endPlayerTurn();
                            break;
                }
                    break;
                case 7:
                case 8:
                    switch (gameData[0]){
                        case 2:
                            power_investigate();
                            break;
                        case 3:
                            power_nextPres();
                            break;
                        case 4:
                        case 5:
                            power_kill();
                            break;
                        default:
                            endPlayerTurn();
                            break;
                    }
                    break;
                case 9:
                case 10:
                    switch (gameData[0]){
                        case 1:
                        case 2:
                            power_investigate();
                            break;
                        case 3:
                            power_nextPres();
                            break;
                        case 4:
                        case 5:
                            power_kill();
                            break;
                        default:
                            endPlayerTurn();
                            break;
                    }
                    break;
            }
        } else{
            endPlayerTurn();
        }
    }

    private void power_investigate() {
        Fragment fragment = new Power_InvestigateFragment();
        ArrayList eligible = new ArrayList();
        for(Player p: group){
            if(!p.equals(president)){
                eligible.add(p);
            }
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("players", eligible);
        fragment.setArguments(bundle);
        replaceCurrentFragment(fragment, "fragment_container");
    }

    public void investigatePlayerParty(View view){
        Bundle bundle = new Bundle();
        bundle.putParcelable("player", choice);
        Fragment fragment = new PlayerPartyFragment();
        fragment.setArguments(bundle);
        replaceCurrentFragment(fragment, "fragment_container");
    }

    private void power_nextPres() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("players", group);
        Fragment fragment = new Power_ChooseNextPres();
        fragment.setArguments(bundle);
        replaceCurrentFragment(fragment, "fragment_container");
    }

    public void startWithChosenPres(View view){
        //set next president
        president = choice;

        //end player turn
        turnID = 0;
        drawn.clear();
        chancellor = null;
        choice = null;
        resetVotes();

        //start presidents turn
        Fragment confirmPlayerFragment = new ConfirmPlayerFragment();
        Bundle playerData = new Bundle();
        playerData.putParcelable("playerData", president);
        confirmPlayerFragment.setArguments(playerData);
        replaceCurrentFragment(confirmPlayerFragment, "fragment_container");
    }

    private void power_kill() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("players", group);
        Fragment fragment = new Power_KillPlayerFragment();
        fragment.setArguments(bundle);
        replaceCurrentFragment(fragment, "fragment_container");
    }

    private void power_topThree() {
        turnID = 2;
        Fragment confirmPlayerFragment = new ConfirmPlayerFragment();
        Bundle playerData = new Bundle();
        playerData.putParcelable("playerData", president);
        confirmPlayerFragment.setArguments(playerData);
        replaceCurrentFragment(confirmPlayerFragment, "fragment_container");
    }

    public void removePlayer(View view){
        if(choice.getPlayerData()[2].compareTo("Hitler") == 0){
            gameOver("liberals");
        } else {
            group.remove(choice);
            Bundle player = new Bundle();
            player.putParcelable("player", choice);
            Fragment fragment = new KilledPlayerFragment();
            fragment.setArguments(player);
            replaceCurrentFragment(fragment, "fragmentContainer");
        }
    }

    public void gameOver(String winner){
        Bundle bundle = new Bundle();
        bundle.putString("winners", winner);
        Fragment fragment = new GameOverFragment();
        fragment.setArguments(bundle);
        replaceCurrentFragment(fragment, "fragment_container");
    }
}
