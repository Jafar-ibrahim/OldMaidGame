package Enums;

public enum Suit {
    SPADES("♠"),HEARTS("♥"),CLUBS("♣"),DIAMONDS("♦"),JOKER("\uD83C\uDFAD");
    private final String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
