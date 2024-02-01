import Enums.Color;
import Enums.Suit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Game game = new Game();
        //game.start();

        Set<CustomKey> cards = new HashSet<>();
        Card card = new NumeralCard(Color.BLACK, Suit.SPADES,1);
        Card card2 = new NumeralCard(Color.BLACK, Suit.CLUBS,1);
        cards.add(new CustomKey(card));
        System.out.println(cards.add(new CustomKey(card2)));
    }
}