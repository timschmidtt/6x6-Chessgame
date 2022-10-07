package layers.model;

import layers.model.actors.Player;
import layers.model.actors.Referee;
import layers.model.pieces.Piece;

import java.util.Objects;

/**
 * This is the basic structure of every game. It consists of a {@link Referee}, a {@link Board}, two {@link Player}s, a boolean if the game
 * is ended and the current move.
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

    @Override
    public void run() {
        while (true) {
            if (this.isInterrupted()) {
                break;
            }
            // Create a new thread where dummy player can make his move
            MoveThread moveThread = new MoveThread(this.currentPlayer, this.currentMove);
            moveThread.setDaemon(true);
            moveThread.start();
            try {
                //noinspection BusyWait
                Thread.sleep(1100);
                moveThread.join();
            } catch (InterruptedException e) {
                return;
            }
            setCurrentMove(this.currentPlayer.getLastMove());
            // Check if the last move was legal
            if (this.referee.checkMove(this.currentMove, this.board)) {
                this.getBoard().executeMove(getCurrentMove());
            } else {
                // If the move made by a player wasn't correct we will just end the game
                break;
            }
            // Check if the King was beaten
            checkGameEnd();
            if (!isEnd()) {
                nextPlayer();
            } else {
                endGame();
                return;
            }
        }
    }

    private void checkGameEnd() {
        Piece lastBeatenPiece = getCurrentPlayer().getLastMove().getSecond().getPiece();
        if (lastBeatenPiece != null) {
            if (Objects.equals(lastBeatenPiece.getName(), "King")) {
                endGame();
            }
        }
    }

    /**
     * Common method that should be used to start a game.
     */
    public void startGame() {
    }

    /**
     * This method will just switch the current player.
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
