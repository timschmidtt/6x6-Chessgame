package layers.model.pieces;

import javafx.scene.image.Image;
import layers.model.Board;
import layers.model.Square;
import layers.model.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pawn extends Piece {

  public Pawn(boolean color) {
    super(color, "Pawn");
  }

  @Override
  public List<Tuple<Integer, Integer>> createMoves() {
    List<Tuple<Integer, Integer>> movesList = new ArrayList<>();
    if (!this.getColor()) {
      // Moves for black pawns
      movesList.add(new Tuple<>(0, 1));
      movesList.add(new Tuple<>(1, 1));
      movesList.add(new Tuple<>(-1, 1));
    } else {
      // Moves for white pawns
      movesList.add(new Tuple<>(0, -1));
      movesList.add(new Tuple<>(1, -1));
      movesList.add(new Tuple<>(-1, -1));
    }
    return movesList;
  }

  /**
   * Pawns have a unique technic of moving. Their moves need to be divided by weather they just move or beat an enemy piece.
   *
   * @param move the move of the pawn.
   * @return A boolean if the pawn move is legal.
   */
  @Override
  public boolean canMove(Tuple<Integer, Integer> move, Square fromSquare, Board chessboard) {
    int hor = move.getFirst();
    int ver = move.getSecond();
    int column = fromSquare.getColumn() + hor;
    int row = fromSquare.getRow() + ver;
    if (column <= 5 && column >= 0 && row <= 5 && row >= 0) {
      Square toSquare = chessboard.getSquare(column, row);
      if (Math.abs(ver) != Math.abs(hor)) {
        return !toSquare.isPieceSet();
      }
      return toSquare.isPieceSet();
    }
    return false;
  }

  @Override
  public Image setImage() {
    Image image;
    if (!getColor()) {
      image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pieceIcons/BlackPawn.png")));
    } else {
      image =  new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pieceIcons/WhitePawn.png")));
    }
    return image;
  }
}
