package OldMaidGame;

import OldMaidGame.Cards.Card;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class GameManager {
    private final int noOfPlayers;
    private final Deque<Player> playerTurnQueue ;
    private Player loser; // store the loser for announcement
    private final CountDownLatch concurrentDiscardCounter;
    private static GameManager instance;

    private GameManager() {
        playerTurnQueue = new LinkedList<>();
        InputOutputManager inputOutputManager = InputOutputManager.getInstance();
        noOfPlayers = inputOutputManager.readNoOfPlayers();
        concurrentDiscardCounter = new CountDownLatch(noOfPlayers);
    }
    public static GameManager getInstance() {
        if(instance == null)
            instance = new GameManager();
        return instance;
    }
    public boolean gameOver(){
        return getCurrentNoOfPlayers() == 1;
    }
    public void notifyFinishedDiscarding(){
        concurrentDiscardCounter.countDown();
    }
    public void waitForOthersToDiscard(){
        try {
            concurrentDiscardCounter.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public Player getLoser() {
        return loser;
    }
    public synchronized void appendPlayerToQueue(Player player) {
        playerTurnQueue.offer(player);
    }
    public synchronized void removePlayerFromQueue(Player player) {
        playerTurnQueue.remove(player);
    }
    public synchronized void initializeTurnQueue(List<Player> players) {
        playerTurnQueue.addAll(players);
    }

    public synchronized void nextTurn() {
        playerTurnQueue.remove();
        notifyAll();
    }
    public Card drawCardFromLastPlayer(){
        Player lastPlayer = getLastPlayerInQueue();
        Random random = new Random();
        int n = random.nextInt(lastPlayer.getHand().getHandSize());
        return lastPlayer.getHand().giveNthCardFromHand(n);
    }
    public synchronized void waitForTurn(Player player) throws InterruptedException {
        while (!playerTurnQueue.isEmpty() && !playerTurnQueue.peek().equals(player)) {
            wait();
        }
    }
    public void setLoser(Player loser) {
        this.loser = loser;
    }
    public int getNoOfPlayers(){
        return noOfPlayers;
    }
    public int getCurrentNoOfPlayers(){
        return playerTurnQueue.size();
    }
    public Player getLastPlayerInQueue(){
        return playerTurnQueue.getLast();
    }
    public void advancePlayerForward(Player player){
        removePlayerFromQueue(player);
        playerTurnQueue.addFirst(player);
    }

    // If a player wins because another player took his last card ,
    // he'll still be waiting for his turn ,so his turn must be consumed
    // instantly so that no other player will mistake him for an active player and try
    // to draw a card from him. ( to avoid another player acting before the winning player exits)
    public synchronized void consumeWinnerEmptyTurn(Player currentPlayer, Player winner){
        removePlayerFromQueue(currentPlayer);
        advancePlayerForward(winner);
        notifyAll();
    }
}