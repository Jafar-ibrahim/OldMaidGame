import java.util.*;

public class HandIterator implements Iterator<Card> {

    private final Iterator<Map.Entry<CustomKey, List<Card>>> mapIterator;
    private Iterator<Card> currentListIterator = null;

    public HandIterator(Map<CustomKey, List<Card>> hand) {
        this.mapIterator = hand.entrySet().iterator();
    }

    @Override
    public boolean hasNext() {
        while (currentListIterator == null || !currentListIterator.hasNext()) {
            if (!mapIterator.hasNext()) {
                return false; // No more cards in the map
            }
            // Move to the next list
            currentListIterator = mapIterator.next().getValue().iterator();
        }
        return true; // There's a card to return
    }

    @Override
    public Card next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return currentListIterator.next();
    }
}
