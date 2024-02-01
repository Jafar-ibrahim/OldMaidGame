import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game extends Thread{

    Deck deck;
    public Game() {
        deck = Deck.getInstance();
    }

    @Override
    public void run() {
        GameState gameState = GameState.getInstance();

        List<Player> players = new ArrayList<>();
        for(int i = 1 ; i < 5 ; i++){
            players.add(new Player("P"+i,gameState));
        }
        gameState.initializeTurnQueue(players);
        deck.initializeCards();
        deck.shuffle();

        dealCards(players,deck);

        for(Player player : players){
            player.start();
        }
    }

    public void dealCards(List<Player> players , Deck deck){
        // could have just given every player 13 cards at once,
        // but I want to imitate the real game as much as possible (1 card each at a time)
        for(int i = 0 ; i < 13 ; i++){
            for(Player player : players){
                player.addToHand(deck.getTopCard());
            }
        }
    }
}
