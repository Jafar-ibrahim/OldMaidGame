package Enums;

public enum Suit {
    SPADES("♠"),HEARTS("♥"),CLUBS("♣"),DIAMONDS("♦"),JOKER(" ");
    private final String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
