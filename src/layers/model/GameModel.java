package layers.model;

import layers.model.actors.Player;
import layers.model.actors.Referee;

/**
 * This is the basic structure of every game. It consists of a {@link Referee},
 * a {@link Board}, two {@link Player}s, a boolean if the game is ended and the current move.
 *
 * @author Tim Schmidt (tim.schmidt@student.ibs-ol.de)
 */
public class GameModel extends Thread {

    private final Referee referee;
    private Board board;
    private Player[] players;
    private Player currentPlayer;
    private boolean end;
    private Tuple<Square, Square> currentMove = null;

    public GameModel() {
        this.board = new Board(6, 6);
        this.players = new Player[2];
        this.referee = new Referee();
    }

    /**
     * This is the thread of the game in the model and how the general game
     * flow will be executed.
     */
    @Override
    public void run() {
        while (!this.isInterrupted()) {
            // Create a new thread where a player can make his move
            MoveThread moveThread = new MoveThread(this.currentPlayer, this.currentMove);
            moveThread.setDaemon(true);
            moveThread.start();
            try {
                //noinspection BusyWait
                Thread.sleep(100);
                moveThread.join();
            } catch (InterruptedException e) {
                return;
            }
            // Set the executed move from the current player as the
            // current move and execute it
            setCurrentMove(this.currentPlayer.getLastMove());
            this.getBoard().executeMove(this.currentMove);
            try {
                //noinspection BusyWait
                Thread.sleep(2300);
                moveThread.join();
            } catch (InterruptedException e) {
                return;
            }
            // Check via the referee if the king was beaten
            if (this.referee.isKingBeaten(currentMove)) {
                return;
            } else {
                nextPlayer();
            }
        }
    }

    /**
     * Common method that should be used to start a game.
     * (Just used by ConsoleGame)
     */
    public void startGame() {
    }

    /**
     * This method will switch the current player.
     */
    public void nextPlayer() {
        if (currentPlayer == players[0]) {
            currentPlayer = players[1];
        } else {
            currentPlayer = players[0];
        }
        getReferee().setCurrentPlayer(currentPlayer);
    }

    /**
     * Sets the current player for the game model and the referee.
     *
     * @param currentPlayer The current player of class.
     */
    public void setCurrentPlayer(Player currentPlayer) {
        getReferee().setCurrentPlayer(currentPlayer);
        this.currentPlayer = currentPlayer;
    }

    public Referee getReferee() {
        return referee;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isEnd() {
        return end;
    }

    public void endGame() {
        this.end = true;
    }

    public Tuple<Square, Square> getCurrentMove() {
        return currentMove;
    }

    public void setCurrentMove(Tuple<Square, Square> move) {
        this.currentMove = move;
    }
}
