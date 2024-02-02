import Enums.Suit;

import java.util.*;

public class Player extends Thread{

    private String name;
    private Map<CustomKey, List<Card>> hand;
    private boolean winner;
    private final GameState gameState;


    public Player(String name,GameState gameState) {
        super(name);
        this.gameState = gameState;
        this.hand = new HashMap<>();
    }

    @Override
    public void run() {
        findMatchesAndDiscard();

        synchronized (gameState) {
            while (!winner) {
                try {
                    gameState.waitForTurn(this);
                    //sleep(1000);// just to monitor the progress comfortably
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                if (winner){
                    gameState.nextTurn();
                    break;
                }

                System.out.println("-------------------------------------");
                System.out.println(getName() + "'s turn");
                printHand();

                if (gameState.getNoOfPlayers() == 1) {
                    gameState.notifyGameOver(this);
                    break;
                }
                Card chosenCard = gameState.drawCardFromLastPlayer();
                CustomKey chosenCardKey = new CustomKey(chosenCard);
                addToHand(chosenCard);
                System.out.println("Drew " + chosenCard + " from " + gameState.getLastPlayerInQueue().getName());
                // in case this player took the last player's last card
                gameState.getLastPlayerInQueue().checkForWin();

                if (hasMatchFor(chosenCardKey)) {
                    discardMatchingPair(chosenCardKey);
                    checkForWin();
                }
                if (isTheJoker(chosenCard)) {
                    gameState.setLoser(this);
                }
                if (!winner)
                    gameState.appendPlayerToQueue(this);
                gameState.nextTurn();
            }
        }
    }

    private void findMatchesAndDiscard(){
        Set<Map.Entry<CustomKey, List<Card>>> entries = new HashSet<>(hand.entrySet());
        for(Map.Entry<CustomKey, List<Card>> entry : entries){
            if(entry.getValue().size() == 2){
                discardMatchingPair(entry.getKey());
            }
        }
    }
    public boolean checkForWin(){
        if(discardedAllCards()){
            this.winner = true;
            System.out.println("[Attention]  "+getName()+" discarded all his/her cards and is out of the play");
            /*if (gameState.playerHasRemainingTurns(this)){
                gameState.removePlayerFromQueue(this);
            }*/
            return true;
        }
        return false;
    }
    public Map<CustomKey, List<Card>> getHand() {
        return hand;
    }
    public void addToHand(Card card){
        CustomKey key = new CustomKey(card);
        List<Card> matchingCards = hand.computeIfAbsent(key, k -> new ArrayList<>());
        matchingCards.add(card);
    }
    public Card giveNthCardFromHand(int n){
        HandIterator it  = new HandIterator(hand);
        while (n-- >= 1) it.next();
        Card targetCard = it.next();
        hand.remove(new CustomKey(targetCard));
        return targetCard;
    }
    private void discardMatchingPair(CustomKey key){
        List<Card> discarded = hand.get(key);
        hand.remove(key);
        System.out.println(getName() + " discarded a pair of (" + discarded.get(0)+ " , "+discarded.get(1)+")");
    }
    private boolean hasMatchFor(CustomKey key){
        return hand.containsKey(key) && hand.get(key).size() == 2 ;
    }
    public int getHandSize(){
        return hand.size();
    }

    private boolean discardedAllCards(){
        return hand.isEmpty();
    }
    private boolean isTheJoker(Card targetCard){
        return targetCard.getSuit() == Suit.JOKER;
    }
    private void printHand(){
        HandIterator it = new HandIterator(hand);
        System.out.print(getName()+"'s cards : ");
        while (it.hasNext())
            System.out.print(it.next()+", ");
        System.out.println();
    }
}
