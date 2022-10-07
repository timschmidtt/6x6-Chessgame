package layers.model;

import layers.model.actors.Player;

/**
 * DOC: Document me Tim !
 *
 * @author Tim Schmidt (tim.schmidt@cewe.de)
 * @since 19.09.22
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
