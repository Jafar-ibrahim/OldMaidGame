import java.util.*;

public class GameManager {
    public final Deque<Player> playerTurnQueue ;
    private boolean gameOver;
    private Player loser; // store the loser for announcement
    private static GameManager instance;


    private GameManager() {
        playerTurnQueue = new LinkedList<>();
    }

    public static GameManager getInstance() {
        if(instance == null)
            instance = new GameManager();
        return instance;
    }

    public synchronized void notifyGameOver(Player loser) {
        this.gameOver = true;
        this.loser = loser;
        notifyAll(); // Notify waiting GameManager thread
    }

    public synchronized void waitForGameEnd() throws InterruptedException {
        while (!gameOver) {
            wait();
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
        notifyAll();
    }
    public synchronized void initializeTurnQueue(List<Player> players) {
        playerTurnQueue.addAll(players);
    }

    public synchronized Player getCurrentPlayer() {
        return playerTurnQueue.peek();
    }

    public synchronized void nextTurn() {
        playerTurnQueue.remove();
        notifyAll();
    }
    public synchronized Card drawCardFromLastPlayer(){
        Player lastPlayer = getLastPlayerInQueue();
        if(lastPlayer.getHand().discardedAllCards()) return null;
        Random random = new Random();
        int n = random.nextInt(lastPlayer.getHand().getHandSize());
        return lastPlayer.getHand().giveNthCardFromHand(n);
    }
    public synchronized boolean isMyTurn(Player player) {
        return getCurrentPlayer() == player;
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
        return playerTurnQueue.size();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Player getLastPlayerInQueue(){
        return playerTurnQueue.getLast();
    }
    public boolean playerHasRemainingTurns(Player player){
        return playerTurnQueue.contains(player);
    }
    public void advancePlayerForward(Player player){
        playerTurnQueue.addFirst(player);
    }

    public boolean checkIfPlayerLost(Player player){
        if (getNoOfPlayers() == 1) {
            notifyGameOver(player);
            return true;
        }
        return false;
    }
    // If a player wins because another player took his last card ,
    // he'll still be waiting for his turn ,so his turn must be consumed
    // instantly so that no other player will mistake him for an active player and try
    // to draw a card from him. ( to avoid another player acting before the winning player exits)
    public void consumeWinnerEmptyTurn(Player currentPlayer, Player winner){
        removePlayerFromQueue(currentPlayer);
        removePlayerFromQueue(winner);
        advancePlayerForward(winner);
        notifyAll();
    }
}
