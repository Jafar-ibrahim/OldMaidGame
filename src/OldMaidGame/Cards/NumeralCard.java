package OldMaidGame.Cards;

import OldMaidGame.Enums.Color;
import OldMaidGame.Enums.Suit;

public class NumeralCard extends Card {

    private final int number;
    public NumeralCard(Color color, Suit suit , int number) {
        super(color, suit);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return getSuit().getSymbol() +" "+ getNumber() +" "+ getSuit().getSymbol();
    }
}
