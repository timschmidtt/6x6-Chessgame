package layers.model;

import com.sun.xml.internal.bind.v2.TODO;
import layers.model.pieces.Piece;

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

    // TODO comment
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
