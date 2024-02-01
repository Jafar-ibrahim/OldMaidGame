import Enums.Color;
import Enums.Suit;

import java.util.Objects;

public abstract class Card {
    private Color color;
    private Suit suit;

    public Card(Color color, Suit suit) {
        this.color = color;
        this.suit = suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return color == card.color && getNumberOrRank(this).equals(getNumberOrRank(card));
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, suit);
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
}
