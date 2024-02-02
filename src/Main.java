import Enums.Color;
import Enums.Suit;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Game game = new Game();
        game.start();

        /*Set<CustomKey> cards = new HashSet<>();
        Card card = new NumeralCard(Color.BLACK, Suit.SPADES,1);
        Card card2 = new NumeralCard(Color.BLACK, Suit.CLUBS,1);
        cards.add(new CustomKey(card));
        System.out.println(cards.add(new CustomKey(card2)));

        Player p1 = new Player("p1",GameState.getInstance());

        for(int i = 2 ; i <= 9 ; i++){
            p1.addToHand(new NumeralCard(Color.BLACK, Suit.SPADES,i));
            p1.addToHand(new NumeralCard(Color.BLACK, Suit.CLUBS,i));
            p1.addToHand(new NumeralCard(Color.RED, Suit.DIAMONDS,i));
            p1.addToHand(new NumeralCard(Color.RED, Suit.HEARTS,i));
        }
        int n = 2;
        HandIterator it = new HandIterator(p1.getHand());
        while (it.hasNext()){
            System.out.print(it.next()+",");
        }
        it = new HandIterator(p1.getHand());
        System.out.println();
        while (n-- >= 1) it.next();
        Card targetCard = it.next();
        System.out.println(targetCard);*/
    }
}