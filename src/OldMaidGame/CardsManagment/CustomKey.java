package OldMaidGame.CardsManagment;

import OldMaidGame.Cards.Card;
import OldMaidGame.Enums.Color;

import java.util.Objects;

public class CustomKey {
    private final Color color;
    private final String numberOrRank;

    public CustomKey(Card card){
        this.color = card.getColor();
        this.numberOrRank = Card.getNumberOrRank(card);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomKey customKey = (CustomKey) o;
        return color == customKey.color && numberOrRank.equals(customKey.numberOrRank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, numberOrRank);
    }
}
