package OldMaidGame.CardsManagment;

import OldMaidGame.Cards.Card;
import OldMaidGame.InputOutputManager;
import OldMaidGame.Player;

import java.util.*;

public class Hand {
    private final Map<CustomHandMapKey, List<Card>> handMap;
    private final InputOutputManager inputOutputManager;

    public Hand() {
        handMap = new HashMap<>();
        inputOutputManager = InputOutputManager.getInstance();
    }
    public void addToHand(Card card){
        CustomHandMapKey key = new CustomHandMapKey(card);
        List<Card> matchingCards = handMap.computeIfAbsent(key, k -> new ArrayList<>());
        matchingCards.add(card);
    }
    public void findMatchesAndDiscard(Player player){
        Set<Map.Entry<CustomHandMapKey, List<Card>>> entries = new HashSet<>(handMap.entrySet());
        for(Map.Entry<CustomHandMapKey, List<Card>> entry : entries){
            if(entry.getValue().size() == 2){
                discardMatchingPair(player,entry.getKey());
            }
        }
    }
    public Card giveNthCardFromHand(int n){
        HandIterator it  = new HandIterator(handMap);
        while (n-- >= 1) it.next();
        Card targetCard = it.next();
        handMap.remove(new CustomHandMapKey(targetCard));
        return targetCard;
    }
    public void discardMatchingPair(Player player, CustomHandMapKey key){
        List<Card> discarded = handMap.get(key);
        handMap.remove(key);
        inputOutputManager.printDiscardedCards(player.getName(),discarded);
    }
    public boolean hasMatchFor(CustomHandMapKey key){
        return handMap.containsKey(key) && handMap.get(key).size() == 2 ;
    }
    public int getHandSize(){
        return handMap.size();
    }

    public Map<CustomHandMapKey, List<Card>> getHandMap() {
        return handMap;
    }

    public boolean discardedAllCards(){
        return handMap.isEmpty();
    }
}
