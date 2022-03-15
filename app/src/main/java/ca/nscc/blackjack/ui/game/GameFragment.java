package ca.nscc.blackjack.ui.game;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import ca.nscc.blackjack.databinding.FragmentGameBinding;


public class GameFragment extends Fragment {

    private GameViewModel dashboardViewModel;
    private FragmentGameBinding binding;

    //gameplay vars
    Card[] deck ;
    ArrayList<Card> playerHand;
    ArrayList<Card> houseHand;

    //graphic vars
    Button buttonStart;
    Button buttonHit;
    Button buttonStay;

    ImageView pCard1;
    ImageView pCard2;
    ImageView pCard3;
    ImageView pCard4;
    ImageView pCard5;

    ImageView hCard1;
    ImageView hCard2;
    ImageView hCard3;
    ImageView hCard4;
    ImageView hCard5;

    TextView txtYourHandValue;
    TextView txtHouseHandValue;
    TextView txtScore;
    TextView txtPlayerName;



    //other vars
    int hitCount;
    int deckPosition;
    int score;
    int intPlayerHandValue;
    int intHouseHandValue;

    int imageResource;
    String imageName;
    String packageName;
    SharedPreferences pref;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(GameViewModel.class);

        binding = FragmentGameBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //card array
        deck = new Card[]{new Card(2, "two_of_hearts"),new Card(3, "three_of_hearts"),
                new Card(4, "four_of_hearts"), new Card(5, "five_of_hearts"),
                new Card(6, "six_of_hearts"), new Card(7, "seven_of_hearts"),
                new Card(8, "eight_of_hearts"), new Card(9, "nine_of_hearts"),
                new Card(10, "ten_of_hearts"), new Card(10, "jack_of_hearts"),
                new Card(10, "queen_of_hearts"), new Card(10, "king_of_hearts"), new Card(11, "ace_of_hearts"),
                new Card(2, "two_of_diamonds"),new Card(3, "three_of_diamonds"),
                new Card(4, "four_of_diamonds"), new Card(5, "five_of_diamonds"),
                new Card(6, "six_of_diamonds"), new Card(7, "seven_of_diamonds"),
                new Card(8, "eight_of_diamonds"), new Card(9, "nine_of_diamonds"),
                new Card(10, "ten_of_diamonds"), new Card(10, "jack_of_diamonds"),
                new Card(10, "queen_of_diamonds"), new Card(10, "king_of_diamonds"), new Card(11, "ace_of_diamonds"),
                new Card(2, "two_of_clubs"),new Card(3, "three_of_clubs"),
                new Card(4, "four_of_clubs"), new Card(5, "five_of_clubs"),
                new Card(6, "six_of_clubs"), new Card(7, "seven_of_clubs"),
                new Card(8, "eight_of_clubs"), new Card(9, "nine_of_clubs"),
                new Card(10, "ten_of_clubs"), new Card(10, "jack_of_clubs"),
                new Card(10, "queen_of_clubs"), new Card(10, "king_of_clubs"), new Card(11, "ace_of_clubs"),
                new Card(2, "two_of_spades"),new Card(3, "three_of_spades"),
                new Card(4, "four_of_spades"), new Card(5, "five_of_spades"),
                new Card(6, "six_of_spades"), new Card(7, "seven_of_spades"),
                new Card(8, "eight_of_spades"), new Card(9, "nine_of_spades"),
                new Card(10, "ten_of_spades"), new Card(10, "jack_of_spades"),
                new Card(10, "queen_of_spades"), new Card(10, "king_of_spades"), new Card(11, "ace_of_spades"),};


        buttonStart = binding.buttonStart;
        buttonHit = binding.buttonHit;
        buttonStay = binding.buttonStay;

        pCard1 = binding.pCard1;
        pCard2 = binding.pCard2;
        pCard3 = binding.pCard3;
        pCard4 = binding.pCard4;
        pCard5 = binding.pCard5;

        hCard1 = binding.hCard1;
        hCard2 = binding.hCard2;
        hCard3 = binding.hCard3;
        hCard4 = binding.hCard4;
        hCard5 = binding.hCard5;

        txtYourHandValue = binding.txtYourHandValue;
        txtHouseHandValue = binding.txtHouseHandValue;
        txtScore = binding.txtScore;
        txtPlayerName = binding.txtPlayerName;

        hitCount = 0;
        score = 0;
        txtScore.setText("Score: " + score);

        pref = getActivity().getSharedPreferences("user_data", MODE_PRIVATE);
        String nameString = pref.getString("playerName", "Guest");

        txtPlayerName.setText("Player: "+ nameString);





        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //on click, shuffle the array
                Collections.shuffle(Arrays.asList(deck));

                //clear hands, reset deck position
                playerHand = new ArrayList<Card>();
                deckPosition = 0;
                hitCount = 0;

                //get resource ID for card names and set images, add card to player hand
                imageName = deck[deckPosition].getName();
                imageResource = getResources().getIdentifier(imageName, "drawable", getContext().getPackageName() );
                pCard1.setImageResource(imageResource);
                playerHand.add(deck[deckPosition]);
                deckPosition ++;

                imageName = deck[deckPosition].getName();
                imageResource = getResources().getIdentifier(imageName, "drawable", getContext().getPackageName() );
                pCard2.setImageResource(imageResource);
                playerHand.add(deck[deckPosition]);
                deckPosition ++;

                //calculate score
                intPlayerHandValue = calculateScore(playerHand);
                txtYourHandValue.setText("Your Hand Value: " + intPlayerHandValue);

                buttonStart.setVisibility(View.INVISIBLE);
                buttonHit.setVisibility(View.VISIBLE);
                buttonStay.setVisibility(View.VISIBLE);

                //pCard1.setImageResource(deck[0].getName());
                //Toast.makeText(getContext(),  imageResource, Toast.LENGTH_LONG).show();
            }
        });

        buttonHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        buttonHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitCount ++;

                imageName = deck[deckPosition].getName();
                imageResource = getResources().getIdentifier(imageName, "drawable", getContext().getPackageName() );
                playerHand.add(deck[deckPosition]);
                deckPosition ++;
                intPlayerHandValue = calculateScore(playerHand);
                txtYourHandValue.setText("Your Hand Value: " + intPlayerHandValue);

                if (hitCount == 1){
                    pCard3.setImageResource(imageResource);

                }

                if (hitCount == 2){
                    pCard4.setImageResource(imageResource);
                }

                if (hitCount == 3){
                    pCard5.setImageResource(imageResource);

                    //used to trigger the buttonStay code in if 5 cards are drawn and player still has not busted
                    if(intPlayerHandValue <= 21){
                        buttonStay.callOnClick();
                    }

                }


                if (intPlayerHandValue > 21){
                    busted();
                }


            }
        });

        buttonStay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                houseHand = new ArrayList<Card>();
                intHouseHandValue = 0;


                while(houseHand.size() < 6 && intHouseHandValue < intPlayerHandValue){

                    imageName = deck[deckPosition].getName();
                    imageResource = getResources().getIdentifier(imageName, "drawable", getContext().getPackageName() );
                    houseHand.add(deck[deckPosition]);
                    deckPosition ++;
                    intHouseHandValue = calculateScore(houseHand);
                    txtHouseHandValue.setText("House Hand Value: " + intHouseHandValue);

                    if (houseHand.size() == 1){
                        hCard1.setImageResource(imageResource);
                    }

                    if (houseHand.size() == 2){
                        hCard2.setImageResource(imageResource);
                    }

                    if (houseHand.size() == 3){
                        hCard3.setImageResource(imageResource);
                    }

                    if (houseHand.size() == 4){
                        hCard4.setImageResource(imageResource);
                    }

                    if (houseHand.size() == 5){
                        hCard5.setImageResource(imageResource);
                    }

                }

                if (intHouseHandValue > 21 || intHouseHandValue < intPlayerHandValue){
                    win();
                }else{
                    busted();
                }

            }
        });





        return root;
    }

    public int calculateScore(ArrayList<Card> hand){

        int aceCount = 0;
        int total = 0;
        for (int i = 0; i < hand.size(); i++){
            if (hand.get(i).getName() == "ace_of_hearts" || hand.get(i).getName() == "ace_of_diamonds"
                    || hand.get(i).getName() == "ace_of_clubs" || hand.get(i).getName() == "ace_of_spades"){
                aceCount ++;
            }
        }

        for (int i = 0; i < hand.size(); i++){
            int value = hand.get(i).getValue();
            total = total + value;
        }

        if (total > 21){
            if (aceCount != 0){
                for (int i = 0; i < aceCount; i ++){
                    if (total > 21){
                        total = total - 10;
                    }
                }
            }
        }


        return total;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void busted(){
        Toast.makeText(getContext(),  "House wins. You lose 50 points.", Toast.LENGTH_LONG).show();
        score = score - 50;
        txtScore.setText("Score: " + score);
        buttonHit.setVisibility(View.INVISIBLE);
        buttonStay.setVisibility(View.INVISIBLE);


        //resets game after a few seconds
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resetGame();
            }
        }, 2000);

    }

    public void win(){
        Toast.makeText(getContext(),  "Player wins. You gain 50 points.", Toast.LENGTH_LONG).show();
        score = score + 50;
        txtScore.setText("Score: " + score);
        buttonHit.setVisibility(View.INVISIBLE);
        buttonStay.setVisibility(View.INVISIBLE);


        //resets game after a few seconds
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resetGame();
            }
        }, 3000);
    }


    public void resetGame(){

        imageName = "card_back";
        imageResource = getResources().getIdentifier(imageName, "drawable", getContext().getPackageName() );
        pCard1.setImageResource(imageResource);
        pCard2.setImageResource(imageResource);
        pCard3.setImageResource(imageResource);
        pCard4.setImageResource(imageResource);
        pCard5.setImageResource(imageResource);

        hCard1.setImageResource(imageResource);
        hCard2.setImageResource(imageResource);
        hCard3.setImageResource(imageResource);
        hCard4.setImageResource(imageResource);
        hCard5.setImageResource(imageResource);

        txtYourHandValue.setText("Your hand Value: 0");
        txtHouseHandValue.setText("House Hand Value: 0");

        buttonStart.setVisibility(View.VISIBLE);

    }
}