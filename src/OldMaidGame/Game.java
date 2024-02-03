package OldMaidGame;

import OldMaidGame.CardsManagment.Deck;

import java.util.*;
public class Game extends Thread{

    private final Deck deck;
    private final InputOutputManager inputOutputManager;
    private final GameManager gameManager;

    public Game() {
        deck = Deck.getInstance();
        inputOutputManager = InputOutputManager.getInstance();
        gameManager = GameManager.getInstance();
    }

    @Override
    public void run() {
        int noOfPlayers = gameManager.getNoOfPlayers();
        List<Player> players = new ArrayList<>();
        for(int i = 1 ; i <= noOfPlayers ; i++){
            players.add(new Player("Player "+i, gameManager));
        }
        gameManager.initializeTurnQueue(players);
        deck.initializeCards(noOfPlayers);
        deck.shuffle();

        dealCards(players,deck);
        inputOutputManager.printGameStart(noOfPlayers,deck.calculateNeededCards(noOfPlayers)+1);
        for(Player player : players){
            player.start();
        }
        try {
            gameManager.waitForGameEnd();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        inputOutputManager.announceLoser(gameManager.getLoser());

    }

    public void dealCards(List<Player> players , Deck deck){
        int noOfCards = deck.calculateNeededCards(players.size());
        int cardsPerPlayer = noOfCards / players.size();
        for(Player player : players){
            for(int i = 0 ; i < cardsPerPlayer; i++){
                player.getHand().addToHand(deck.getTopCard());
            }
        }
        // to deal the one card left when one player has to have 1 more card than others
        if (players.size() * cardsPerPlayer < noOfCards)
            players.get(players.size()-1).getHand().addToHand(deck.getTopCard());
        // cards number with joker is always odd , so one more card will be dealt to
        // a random player
        Random random = new Random();
        players.get(random.nextInt(players.size())).getHand().addToHand(deck.getTopCard());
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

}
