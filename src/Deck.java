import Enums.Color;
import Enums.Rank;
import Enums.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;
    private static Deck instance;

    public Deck() {
        cards = new ArrayList<>();
    }
    public static Deck getInstance(){
        if(instance == null)
            instance = new Deck();
        return instance;
    }
    public void initializeCards(){

        for(int i = 2 ; i <= 9 ; i++){
            cards.add(new NumeralCard(Color.BLACK, Suit.SPADES,i));
            cards.add(new NumeralCard(Color.BLACK, Suit.CLUBS,i));
            cards.add(new NumeralCard(Color.RED, Suit.DIAMONDS,i));
            cards.add(new NumeralCard(Color.RED, Suit.HEARTS,i));
        }

        for(Rank rank : Rank.values()){
            if(rank != Rank.Joker){
                cards.add(new RankedCard(Color.BLACK,Suit.SPADES,rank));
                cards.add(new RankedCard(Color.BLACK,Suit.CLUBS,rank));
                cards.add(new RankedCard(Color.RED,Suit.DIAMONDS,rank));
                cards.add(new RankedCard(Color.RED,Suit.HEARTS,rank));

            }
        }

        cards.add(new RankedCard(Color.NONE,Suit.JOKER,Rank.Joker));
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public Card getTopCard(){
        return cards.remove(cards.size()-1);
    }
}
