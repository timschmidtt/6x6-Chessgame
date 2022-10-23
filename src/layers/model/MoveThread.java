package layers.model;

import layers.model.actors.Player;

/**
 * This is the basic thread used for executing moves.
 *
 * @author Tim Schmidt (tim.schmidt@student.ibs-ol.de)
 */
public class MoveThread extends Thread {

  private final Player currentPlayer;
  private final Tuple<Square, Square> currentMove;

  public MoveThread(Player currentPlayer, Tuple<Square, Square> currentMove) {
    this.currentMove = currentMove;
    this.currentPlayer = currentPlayer;
  }

  @Override
  public void run() {
    Tuple<Square, Square> lastMove = this.currentPlayer.getNextMove(this.currentMove);
    this.currentPlayer.setLastMove(lastMove);
  }
}
