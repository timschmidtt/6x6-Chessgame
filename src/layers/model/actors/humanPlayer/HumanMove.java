package layers.model.actors.humanPlayer;

import layers.model.Square;
import layers.model.Tuple;
import layers.model.pieces.Piece;

/**
 * DOC: Document me Tim !
 *
 * @author Tim Schmidt (tim.schmidt@cewe.de)
 * @since 12.10.22
 */
public class HumanMove extends Thread {

  private final Object syncObject = new Object();
  private Tuple<Square, Square> move;

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
    // Get duplicates of the two squares that are involved in the move. We need to save
    // duplicates of the squares as last move because otherwise after the first move is executed
    // the square information are changed and the saved move could not be reused
    int firstColumn = move.getFirst().getColumn();
    int firstRow = move.getFirst().getRow();
    Piece firstPiece = move.getFirst().getPiece();
    boolean firstColor = move.getFirst().getColor();

    int secondColumn = move.getSecond().getColumn();
    int secondRow = move.getSecond().getRow();
    Piece secondPiece = move.getSecond().getPiece();
    boolean secondColor = move.getSecond().getColor();

    Square first = new Square(firstColumn, firstRow, firstColor);
    first.setPiece(firstPiece);
    Square second = new Square(secondColumn, secondRow, secondColor);
    if (secondPiece != null) {
      second.setPiece(secondPiece);
    }
    this.move = new Tuple<>(first, second);

    synchronized (this.syncObject) {
      this.syncObject.notify();
    }
  }

  public Tuple<Square, Square> getMove() {
    return this.move;
  }
}
