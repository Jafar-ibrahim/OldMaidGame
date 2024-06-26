package OldMaidGame;

import OldMaidGame.Cards.Card;
import OldMaidGame.CardsManagment.CustomHandMapKey;
import OldMaidGame.CardsManagment.Hand;

public class Player extends Thread{

    private final Hand hand;
    private boolean winner;
    private final GameManager gameManager;
    private final InputOutputManager inputOutputManager;

    public Player(String name, GameManager gameManager) {
        super(name);
        this.gameManager = gameManager;
        this.hand = new Hand();
        inputOutputManager = InputOutputManager.getInstance();
    }

    @Override
    public void run() {
        // initial discard is done simultaneously
        hand.findMatchesAndDiscard(this);
        gameManager.notifyFinishedDiscarding();
        gameManager.waitForOthersToDiscard();

        while (!winner) {
            try {
                gameManager.waitForTurn(this);
                //sleep(1000);// just to monitor the progress comfortably
            } catch (InterruptedException e) {
                gameManager.removePlayerFromQueue(this);
                System.out.println(getName() + " was interrupted");
                Thread.currentThread().interrupt();
                break;
            }

            if (winner) {
                gameManager.nextTurn();
                break;
            }

            inputOutputManager.printPlayerTurnInfo(this);

            if (gameManager.gameOver()) {
                gameManager.setLoser(this);
                break;
            }

            Card chosenCard = gameManager.drawCardFromLastPlayer();
            CustomHandMapKey chosenCardKey = new CustomHandMapKey(chosenCard);
            hand.addToHand(chosenCard);

            inputOutputManager.printDrawnCard(chosenCard, gameManager.getLastPlayerInQueue());

            if (hand.hasMatchFor(chosenCardKey)) {
                hand.discardMatchingPair(this, chosenCardKey);
                checkForWin();
            }

            // store the last player before the current thread becomes the last one after
            // appending itself again
            Player lastPlayer = gameManager.getLastPlayerInQueue();
            if (!winner)
                gameManager.appendPlayerToQueue(this);

            // in case this player took the last player's last card
            if (lastPlayer.checkForWin())
                gameManager.consumeWinnerEmptyTurn(this, lastPlayer);
            else
                gameManager.nextTurn();

        }
    }
    public boolean checkForWin(){
        if(hand.discardedAllCards()){
            this.winner = true;
            inputOutputManager.announcePlayerWin(getName());
            return true;
        }
        return false;
    }

    public Hand getHand() {
        return hand;
    }
}