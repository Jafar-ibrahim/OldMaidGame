import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InputOutputManager {

    private static InputOutputManager instance;

    public static InputOutputManager getInstance(){
        if(instance == null)
            instance = new InputOutputManager();
        return instance;
    }

    public void printGameStart(int noOfPlayers , int noOfCards){
        System.out.println("------------ Game started ------------");
        System.out.println("No. of players : " + noOfPlayers);
        System.out.println("No. of Cards : " + noOfCards);
        System.out.println("First ,discarding matching cards simultaneously, then start taking turns  : ");
    }
    public int readNoOfPlayers(){
        System.out.println("Please enter the number of players (2-10)");
        return readIntegerInput(2,10);
    }
    public void announceLoser(Player loser){
        System.out.println("\n------------ Game Over ------------");

        System.out.println("***********************************");
        System.out.println("***********************************");
        System.out.println("**    The Old Maid is "+ loser.getName()+"   **");
        System.out.println("***********************************");
        System.out.println("***********************************");
    }

    public void printPlayerTurnInfo(Player player){
        System.out.println("=====================================");
        System.out.println("-> "+player.getName() + "'s turn <-");
        System.out.print(player.getName()+"'s cards : ");
        printHand(player.getHand().getHandMap());
    }
    public void printDrawnCard(Card card , Player targetPlayer){
        System.out.println("Drew " + card + " from " + targetPlayer.getName());
    }
    public void announcePlayerWin(String playerName){
        System.out.println("[Attention]  "+playerName+" discarded all his/her cards and is out of the play");
    }
    public void printDiscardedCards(String playerName,List<Card> discarded){
        System.out.println(playerName + " discarded a pair of (" + discarded.get(0)+ " , "+discarded.get(1)+")");
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

    public void printHand(Map<CustomKey, List<Card>> hand){
        HandIterator it = new HandIterator(hand);
        System.out.print("{ ");
        while (it.hasNext()){
            System.out.print(it.next());
            if(it.hasNext()) System.out.print(" , ");
        }
        System.out.println(" }");
    }

}
