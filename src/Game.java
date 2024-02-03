import java.util.*;

public class Game extends Thread{

    Deck deck;
    public Game() {
        deck = Deck.getInstance();
    }

    @Override
    public void run() {
        GameManager gameManager = GameManager.getInstance();

        System.out.println("Please enter the number of players (2-10)");
        int noOfPlayers = readIntegerInput(2,10);

        List<Player> players = new ArrayList<>();
        for(int i = 1 ; i <= noOfPlayers ; i++){
            players.add(new Player("Player "+i, gameManager));
        }
        gameManager.initializeTurnQueue(players);
        deck.initializeCards(noOfPlayers);
        deck.shuffle();

        dealCards(players,deck);
        System.out.println("------------ Game started ------------");
        System.out.println("No. of players : " + gameManager.getNoOfPlayers());
        System.out.println("No. of Cards : " + (deck.calculateNeededCards(noOfPlayers)+1));
        System.out.println("First ,discarding matching cards simultaneously, then start taking turns  : ");
        for(Player player : players){
            player.start();
        }
        try {
            gameManager.waitForGameEnd();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n------------ Game Over ------------");

        System.out.println("***********************************");
        System.out.println("***********************************");
        System.out.println("**    The Old Maid is "+ gameManager.getLoser().getName()+"   **");
        System.out.println("***********************************");
        System.out.println("***********************************");

    }

    public void dealCards(List<Player> players , Deck deck){
        int noOfCards = deck.calculateNeededCards(players.size());
        int cardsPerPlayer = noOfCards / players.size();
        for(Player player : players){
            for(int i = 0 ; i < cardsPerPlayer; i++){
                player.getHand().addToHand(deck.getTopCard());
            }
        }
        if (players.size() * cardsPerPlayer < noOfCards)
            players.get(players.size()-1).getHand().addToHand(deck.getTopCard());
        Random random = new Random();
        players.get(random.nextInt(players.size())).getHand().addToHand(deck.getTopCard());
    }
    public final int readIntegerInput(int min , int max){
        Scanner scanner = new Scanner(System.in);
        int input ;
        while(true) {
            try {
                input = scanner.nextInt();
                if (input >= min && input <= max)
                    break;
                else
                    System.out.println("Please enter a number in the specified range("+min+"-"+max+")");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input format , please enter a number :");
            }finally {
                scanner.nextLine();
            }
        }
        return input;
    }
}
