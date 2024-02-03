package OldMaidGame.Enums;

public enum Rank {
    JACK("J"),QUEEN("Q"),KING("K"),ACE("A"),Joker("Joker");
    private final String symbol;

    Rank(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
