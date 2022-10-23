package layers.model;

import layers.model.pieces.Piece;

/**
 * A square is the basic structure for every rectangle on the board.
 *
 * @author Tim Schmidt (tim.schmidt@student.ibs-ol.de)
 */
public class Square {
    private final int row;
    private final int column;
    private final boolean color;
    private Piece piece;

    public Square(int column, int row, boolean color) {
        this.column = column;
        this.row = row;
        this.color = color;
    }

    /**
     * In some cases we need to clone a square, so that we can still work with this
     * object after some time. This use case often comes up when a square is involved
     * into storing moves. If the squares of the move wouldn't be cloned and the move
     * gets executed we also change the signature of the squares of the move because they
     * are the same objects.
     *
     * @return A cloned square of the current square.
     */
    public Square cloneSquare() {
        Square clone = new Square(this.column, this.row, this.color);
        if (this.piece != null) {
            clone.setPiece(this.piece);
        }
        return clone;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece newPiece) {
        this.piece = newPiece;
    }

    public boolean isPieceSet() {
        return this.piece != null;
    }

    public void removePiece() {
        this.piece = null;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public boolean getColor() {
        return color;
    }
}
