import Enums.Color;
import Enums.Rank;
import Enums.Suit;

public class RankedCard extends Card {
    private final Rank rank;

    public RankedCard(Color color, Suit suit , Rank rank) {
        super(color, suit);
        this.rank = rank;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return getSuit().getSymbol() +" "+ getRank().getSymbol() +" "+ getSuit().getSymbol();
    }
}
