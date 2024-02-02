import java.util.*;

public class GameState {
    private static GameState instance;
    private boolean gameOver;
    private Player loser; // Store the loser for announcement
    public final Deque<Player> playerTurnQueue ; // Queue for player turns


    private GameState() {
        playerTurnQueue = new LinkedList<>();
    }

    public static GameState getInstance() {
        if(instance == null)
            instance = new GameState();
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
        playerTurnQueue.offer(player); // Add player to the end of the queue
    }

    public synchronized void removePlayerFromQueue(Player player) {
        playerTurnQueue.remove(player);
        notifyAll();
    }
    public synchronized void initializeTurnQueue(List<Player> players) {
        playerTurnQueue.addAll(players); // Add all players to the queue initially
    }

    public synchronized Player getCurrentPlayer() {
        return playerTurnQueue.peek(); // Get the player at the front of the queue without removing them
    }

    public synchronized void nextTurn() {
        playerTurnQueue.remove();
        notifyAll();
    }
    public synchronized Card drawCardFromLastPlayer(){
        Player lastPlayer = getLastPlayerInQueue();
        System.out.println("debug : " + lastPlayer.getName());
        Random random = new Random();
        int n = random.nextInt(lastPlayer.getHandSize());
        return lastPlayer.giveNthCardFromHand(n);
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

}
