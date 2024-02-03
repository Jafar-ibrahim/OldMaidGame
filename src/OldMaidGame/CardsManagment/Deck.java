package OldMaidGame.CardsManagment;

import OldMaidGame.Cards.Card;
import OldMaidGame.Cards.NumeralCard;
import OldMaidGame.Cards.RankedCard;
import OldMaidGame.Enums.Color;
import OldMaidGame.Enums.Rank;
import OldMaidGame.Enums.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards;
    private static Deck instance;

    public Deck() {
        cards = new ArrayList<>();
    }
    public static Deck getInstance(){
        if(instance == null)
            instance = new Deck();
        return instance;
    }
    public void initializeCards(int noOfCards){

        for(int i = 2 ; i <= 10 ; i++){
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
        int excessCards = 52 - calculateNeededCards(noOfCards);
        while(excessCards-- > 0){
            cards.remove(cards.size()-1);
        }
        cards.add(new RankedCard(Color.NONE,Suit.JOKER,Rank.Joker));

    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public Card getTopCard(){
        return cards.remove(cards.size()-1);
    }
    public int calculateNeededCards(int noOfPlayers){
        int excessCards = 52 % noOfPlayers;
        if(excessCards % 2 != 0)
            excessCards--;

        return  52 - excessCards;
    }
}
