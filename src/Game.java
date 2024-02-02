import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Thread{

    Deck deck;
    public Game() {
        deck = Deck.getInstance();
    }

    @Override
    public void run() {
        GameManager gameManager = GameManager.getInstance();

        List<Player> players = new ArrayList<>();
        for(int i = 1 ; i < 5 ; i++){
            players.add(new Player("P"+i, gameManager));
        }
        gameManager.initializeTurnQueue(players);
        deck.initializeCards();
        deck.shuffle();

        dealCards(players,deck);
        System.out.println("------------ Game started ------------");
        System.out.println("No. of players : " + gameManager.getNoOfPlayers());
        System.out.println("First ,discarding matching cards simultaneously, then start taking turns  : ");
        for(Player player : players){
            player.start();
        }
        try {
            gameManager.waitForGameEnd();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("------------ Game Over ------------");
        System.out.println("The Old Maid is "+ gameManager.getLoser().getName());
    }

    public void dealCards(List<Player> players , Deck deck){
        // could have just given every player 13 cards at once,
        // but I want to imitate the real game as much as possible (1 card each at a time)
        for(int i = 0 ; i < 13 ; i++){
            for(Player player : players){
                player.getHand().addToHand(deck.getTopCard());
            }
        }
        Random random = new Random();
        players.get(random.nextInt(players.size())).getHand().addToHand(deck.getTopCard());
    }
}
