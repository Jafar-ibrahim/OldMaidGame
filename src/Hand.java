import java.util.*;

public class Hand {
    private final Map<CustomKey, List<Card>> hand;

    public Hand() {
        hand = new HashMap<>();
    }
    public void addToHand(Card card){
        CustomKey key = new CustomKey(card);
        List<Card> matchingCards = hand.computeIfAbsent(key, k -> new ArrayList<>());
        matchingCards.add(card);
    }
    public void findMatchesAndDiscard(Player player){
        Set<Map.Entry<CustomKey, List<Card>>> entries = new HashSet<>(hand.entrySet());
        for(Map.Entry<CustomKey, List<Card>> entry : entries){
            if(entry.getValue().size() == 2){
                discardMatchingPair(player,entry.getKey());
            }
        }
    }
    public Card giveNthCardFromHand(int n){
        HandIterator it  = new HandIterator(hand);
        while (n-- >= 1) it.next();
        Card targetCard = it.next();
        hand.remove(new CustomKey(targetCard));
        return targetCard;
    }
    public void discardMatchingPair(Player player, CustomKey key){
        List<Card> discarded = hand.get(key);
        hand.remove(key);
        System.out.println(player.getName() + " discarded a pair of (" + discarded.get(0)+ " , "+discarded.get(1)+")");
    }
    public boolean hasMatchFor(CustomKey key){
        return hand.containsKey(key) && hand.get(key).size() == 2 ;
    }
    public int getHandSize(){
        return hand.size();
    }

    public boolean discardedAllCards(){
        return hand.isEmpty();
    }
    public void printHand(){
        HandIterator it = new HandIterator(hand);
        System.out.print("{ ");
        while (it.hasNext()){
            System.out.print(it.next());
            if(it.hasNext()) System.out.print(" , ");
        }
        System.out.println(" }");
    }
}
