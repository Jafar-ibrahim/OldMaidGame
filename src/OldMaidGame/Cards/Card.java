package OldMaidGame.Cards;

import OldMaidGame.Enums.Color;
import OldMaidGame.Enums.Suit;

public abstract class Card {
    private Color color;
    private Suit suit;

    public Card(Color color, Suit suit) {
        this.color = color;
        this.suit = suit;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public static String getNumberOrRank(Card card){
        if(card instanceof NumeralCard)
            return Integer.toString(((NumeralCard) card).getNumber());
        else
            return ((RankedCard) card).getRank().toString();
    }
    public static boolean isTheJoker(Card targetCard){
        return targetCard.getSuit() == Suit.JOKER;
    }
}
