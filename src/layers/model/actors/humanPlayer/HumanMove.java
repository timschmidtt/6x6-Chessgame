package layers.model.actors.humanPlayer;

import layers.model.Square;
import layers.model.Tuple;
import layers.model.pieces.Piece;

/**
 * The HumanMove thread is used to start a new move thread for a human player.
 *
 * @author Tim Schmidt (tim.schmidt@student.ibs-ol.de)
 */
public class HumanMove extends Thread {

  private final Object syncObject = new Object();
  private Tuple<Square, Square> move;

  /**
   * The HumanMove thread will start and paused immediately. It will be
   * paused till the human player makes a legal move. After it will be closed.
   */
  @Override
  public void run() {
    synchronized (this.syncObject) {
      try {
        if (this.isInterrupted()) {
          return;
        }
        // Wait until the HumanPlayer did a legal move
        this.syncObject.wait();
      } catch (InterruptedException ignored) {
      }
    }
  }

  /**
   * This method is used to set the move made by the HumanPlayer from outside the
   * thread ({@link layers.controller.GameController}). This will also notify the syncObject.
   *
   * @param move The move made by a HumanPlayer.
   */
  public void setMove(Tuple<Square, Square> move) {
    // Get duplicates of the two squares that are involved in the move. We need to clone
    // duplicates of the squares in the last move. Otherwise, after the first move is executed,
    // the square information are changed and the saved move could not be reused
    Square fromSquareClone = move.getFirst().cloneSquare();
    Square toSquareClone = move.getSecond().cloneSquare();
    this.move = new Tuple<>(fromSquareClone, toSquareClone);
    // Notify the thread to continue
    synchronized (this.syncObject) {
      this.syncObject.notify();
    }
  }

  public Tuple<Square, Square> getMove() {
    return this.move;
  }
}
