public class Player extends Thread{

    private final Hand hand;
    private boolean winner;
    private final GameManager gameManager;


    public Player(String name, GameManager gameManager) {
        super(name);
        this.gameManager = gameManager;
        this.hand = new Hand();
    }

    @Override
    public void run() {
        hand.findMatchesAndDiscard(this);
        synchronized (gameManager) {
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
                if (winner){
                    gameManager.nextTurn();
                    break;
                }

                System.out.println("-------------------------------------");
                System.out.println(getName() + "'s turn");
                System.out.print(getName()+"'s cards : ");
                hand.printHand();

                if (gameManager.checkIfPlayerLost(this)) {
                    break;
                }
                Card chosenCard = gameManager.drawCardFromLastPlayer();

                CustomKey chosenCardKey = new CustomKey(chosenCard);
                hand.addToHand(chosenCard);
                System.out.println("Drew " + chosenCard + " from " + gameManager.getLastPlayerInQueue().getName());


                if (hand.hasMatchFor(chosenCardKey)) {
                    hand.discardMatchingPair(this,chosenCardKey);
                    checkForWin();
                }
                if (Card.isTheJoker(chosenCard)) {
                    gameManager.setLoser(this);
                }

                // store the last player before the current thread becomes the last one after
                // appending itself again
                Player lastPlayer = gameManager.getLastPlayerInQueue();
                if (!winner)
                    gameManager.appendPlayerToQueue(this);

                // in case this player took the last player's last card
                if(lastPlayer.checkForWin())
                    gameManager.consumeWinnerEmptyTurn(this,lastPlayer);
                else
                    gameManager.nextTurn();
            }
        }
    }
    public boolean checkForWin(){
        if(hand.discardedAllCards()){
            this.winner = true;
            System.out.println("[Attention]  "+getName()+" discarded all his/her cards and is out of the play");
            return true;
        }
        return false;
    }

    public Hand getHand() {
        return hand;
    }
}