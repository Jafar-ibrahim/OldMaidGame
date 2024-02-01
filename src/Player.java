import Enums.Suit;

import java.util.*;
import java.util.stream.Collectors;

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
        System.out.println("start");

        System.out.println("end");

        checkForWin();
        while (!winner) {
            try {
                gameState.waitForTurn(this);
                sleep(1000);// just to monitor the progress comfortably
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                return;
            }

            if(gameState.getNoOfPlayers() == 1){
                gameState.setGameOver(true);
                return;
            }

            System.out.println(getName() + "'s turn");
            printHand();

            Card chosenCard = gameState.drawCardFromLastPlayer();
            addToHand(chosenCard);
            System.out.println("drew " + chosenCard);
            if(hasMatchFor(chosenCard)){
                discardMatchingPair(chosenCard);
                checkForWin();
            }
            if(isTheJoker(chosenCard)){
                gameState.setLoser(this);
            }
            if(!winner)
                gameState.appendPlayerToQueue(this);
            gameState.nextTurn();
        }
    }

    private void findMatchesAndDiscard(){
        for(Map.Entry<CustomKey, List<Card>> entry : hand.entrySet()){
            if(entry.getValue().size() == 2){
                discardMatchingPair(entry.getKey());
            }
        }
    }
    public void checkForWin(){
        if(discardedAllCards()){
            this.winner = true;
            System.out.println("[Attention]  "+getName()+" discarded all his/her cards and is out of the play");
        }
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
        List<Card> handCardsList = new ArrayList<>();
        for(List<Card> cardList : hand.values()){
            handCardsList.addAll(cardList);
        }
        Card targetCard = handCardsList.get(n);

        hand.remove(new CustomKey(targetCard));
        return targetCard;
    }
    private void discardMatchingPair(CustomKey key){
        List<Card> discarded = hand.get(key);
        hand.remove(key);
        System.out.println(getName() + " discarded a pair of (" + discarded.get(1)+ " , "+discarded.get(2)+")");
    }
    private boolean hasMatchFor(Card targetCard){
        CustomKey key = new CustomKey(targetCard.getColor(),Card.getNumberOrRank(targetCard));
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
        List<Card> sortedCards = new ArrayList<>(hand.keySet())
                                .stream().sorted(Comparator.comparingInt(x -> x.getSuit().ordinal()))
                                .collect(Collectors.toList());
        System.out.println(sortedCards);
    }
}
