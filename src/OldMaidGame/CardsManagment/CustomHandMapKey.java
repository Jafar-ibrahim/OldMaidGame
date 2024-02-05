package OldMaidGame.CardsManagment;

import OldMaidGame.Cards.Card;
import OldMaidGame.Cards.NumeralCard;
import OldMaidGame.Cards.RankedCard;
import OldMaidGame.Enums.Color;

import java.util.Objects;

public class CustomHandMapKey {
    private final Color color;
    private final String numberOrRank;

    public CustomHandMapKey(Card card){
        this.color = card.getColor();
        this.numberOrRank = getNumberOrRank(card);
    }
    public static String getNumberOrRank(Card card){
        if(card instanceof NumeralCard)
            return Integer.toString(((NumeralCard) card).getNumber());
        else
            return ((RankedCard) card).getRank().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomHandMapKey customHandMapKey = (CustomHandMapKey) o;
        return color == customHandMapKey.color && numberOrRank.equals(customHandMapKey.numberOrRank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, numberOrRank);
    }
}
