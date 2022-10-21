package layers.model.actors.aiPlayer1;

import layers.model.Board;
import layers.model.Square;
import layers.model.Tuple;
import layers.model.actors.Player;
import layers.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * DOC: Document me Tim !
 *
 * @author Tim Schmidt (tim.schmidt@cewe.de)
 * @since 17.10.22
 */
public class ArtificialIntelligenceMove {

  private Tuple<Square, Square> move;
  private boolean isMoving;

  private Board board;
  private Player player;

  public ArtificialIntelligenceMove(Tuple<Square, Square> move, int searchingLevel, Board board, Player player) {
    this.move = move;
    this.board = board;
    calcMove(move, searchingLevel);
  }

  // TODO comment
  private void calcMove(Tuple<Square, Square> move, int searchingLevel) {
    if (this.isMoving) {
      Tuple<Square, Square> nextMove = maxValue(searchingLevel);
    } else {
      Tuple<Square, Square> nextMove = minValue(searchingLevel);
    }
  }

  // TODO comment
  private Tuple<Square, Square> maxValue(int searchingLevel) {

    AtomicReference<Tuple<Square, Square>> bestMove = null;
    AtomicInteger highestValue = new AtomicInteger(-99999999);
    List<Tuple<Square, Square>> movesList = getAllNextMoves();

    movesList.forEach(move -> {
      // Simulate the current move
      simulateMove(move);

      Tuple<Square, Square> simulatedMove;
      int simulatedMoveValue;

      if (searchingLevel <= 1 || noNextMoves()) {
        // If there are no next moves we set the current move as the simulated,
        // the same goes for the value
        simulatedMove = move;
        simulatedMoveValue = ratingFunction();
      } else if (isMoving) {
        // Player A turns
        maxValue(searchingLevel - 1);
      } else {
        // Player B turns
        minValue(searchingLevel - 1);
      }

      // Restore
      undoMove(move);

      if (searchingLevel > highestValue.get()) {
        highestValue.set(simulatedMoveValue);
        bestMove.set(move);
      }
    });
    // return the best move and the highest value

    return null;
  }

  // TODO comment
  private Tuple<Square, Square> minValue(int ai_level) {
    return null;
  }

  // TODO comment
  private void simulateMove(Tuple<Square, Square> move) {
    // Here we need to simulate a move on the own board
  }

  // TODO comment
  private void undoMove(Tuple<Square, Square> move) {
    // Here we need to undo a move on the own board
  }

  // TODO comment
  private int ratingFunction() {
    // This will rate how good the current board is for the player
    return 0;
  }

  // TODO comment
  private List<Tuple<Square, Square>> getAllNextMoves() {
    List<Tuple<Square, Square>> moveList = new ArrayList<>();
    // Iterate over all squares
    for (int column = 0; column < this.board.getColumns(); column++) {
      for (int row = 0; row < this.board.getRows(); row++) {
        Square square = board.getSquare(column, row);
        // Check if there is a piece of the player
        if (square.isPieceSet()) {
          if (square.getPiece().getColor() == this.player.getColor()) {
            Piece piece = square.getPiece();
            piece.getMoves().forEach(move -> {
              // Add all possible moves to the list
              if (isPossible(move, square, piece)) {
                Tuple<Square, Square> moveSquare = getMoveSquare(move, square);
                moveList.add(moveSquare);
              }
            });
          }
        }
      }
    }
    return null;
  }

  // TODO comment
  private boolean isPossible(Tuple<Integer, Integer> move, Square fromSquare, Piece piece) {
    // Check if the move could be executed
    if (piece.canMove(move, fromSquare, this.board)) {
      return canTakeSquare(new Tuple<>(fromSquare.getColumn(), fromSquare.getRow()), move, this.board);
    }
    return false;
  }

  /**
   * Proofs if a piece can execute a move on the current chessboard.
   *
   * @param fromSquare The start square of the piece.
   * @param move       The move that will be proofed.
   * @param board      The board where the move should be executed.
   * @return If the move could be executed.
   */
  private boolean canTakeSquare(Tuple<Integer, Integer> fromSquare, Tuple<Integer, Integer> move, Board board) {
    int column = fromSquare.getFirst() + move.getFirst();
    int row = fromSquare.getSecond() + move.getSecond();
    if (column <= 5 && column >= 0 && row <= 5 && row >= 0) {
      Square toSquare = board.getSquare(column, row);
      if (toSquare.getPiece() != null) {
        return toSquare.getPiece().getColor() != this.player.getColor();
      }
      return true;
    }
    return false;
  }

  // TODO comment
  private Tuple<Square, Square> getMoveSquare(Tuple<Integer, Integer> move, Square fromSquare) {
    fromSquare = fromSquare.cloneSquare();
    Square toSquare = this.board.getSquare(fromSquare.getColumn() + move.getFirst(), fromSquare.getRow() + move.getSecond());
    toSquare = toSquare.cloneSquare();
    return new Tuple<>(fromSquare, toSquare);
  }
}
