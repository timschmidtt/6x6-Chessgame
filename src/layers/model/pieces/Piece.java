package layers.model.pieces;

import javafx.scene.image.Image;
import layers.model.Board;
import layers.model.Square;
import layers.model.Tuple;

import java.util.List;

/**
 * Abstract class that defines the basic structure of all pieces.
 */
public abstract class Piece {

    private final boolean color;
    private final List<Tuple<Integer, Integer>> moves;
    private final String name;
    private final Image image;

    public Piece(boolean color, String name) {
        this.color = color;
        this.name = name;
        this.moves = createMoves();
        this.image = setImage();
    }

    public List<Tuple<Integer, Integer>> getMoves() {
        return this.moves;
    }

    /**
     * Creates a list with the coordinates of the possible moves of the piece.
     * This method is abstract so that every piece gets different moves from this method.
     *
     * @return A list of Pairs with move coordinates.
     */
    public abstract List<Tuple<Integer, Integer>> createMoves();

    /**
     * Sets the unique icon of every piece. This icon is needed in {@link layers.view.BoardPanel}
     * to draw the pieces onto the board.
     *
     * @return The icon of the piece.
     */
    public abstract Image setImage();

    /**
     * This method checks if a piece is between the start square and the end square of a move.
     *
     * @param move the move of a piece.
     * @return A boolean if a piece is between or not.
     */
    public boolean canMove(Tuple<Integer, Integer> move, Square fromSquare, Board chessboard) {
        int hor = move.getFirst();
        int ver = move.getSecond();
        boolean squareBetweenIsSet;
        int counter = 1;
        // Horizontal moves
        if (Math.abs(hor) > Math.abs(ver)) {
            int column = fromSquare.getColumn() + hor / Math.abs(hor) * counter;
            // Just check for moves that will end inside the board
            if (column <= 5 && column >= 0) {
                // Iterate through all squares between
                for (int i = Math.abs(hor); i > 1; i--) {
                    squareBetweenIsSet = chessboard.getSquare(column, fromSquare.getRow()).isPieceSet();
                    if (squareBetweenIsSet) {
                        return false;
                    }
                    counter++;
                }
            }
            // Horizontal moves
        } else {
            int nextRow = (ver / Math.abs(ver)) * counter;
            if (Math.abs(ver) > Math.abs(hor)) {
                int row = fromSquare.getRow() + nextRow;
                // Just check for moves that will end inside the board
                if (row <= 5 && row >= 0) {
                    // Iterate through all squares between
                    for (int i = Math.abs(ver); i > 1; i--) {
                        squareBetweenIsSet = chessboard.getSquare(fromSquare.getColumn(), row).isPieceSet();
                        if (squareBetweenIsSet) {
                            return false;
                        }
                        counter++;
                    }
                }
                // Cross moves, here is (ver == hor)
            } else {
                int column = fromSquare.getColumn() + hor / Math.abs(hor) * counter;
                int row = fromSquare.getRow() + nextRow;
                // Just check for moves that will end inside the board
                if (column <= 5 && column >= 0 && row <= 5 && row >= 0) {
                    // Iterate through all squares between
                    for (int i = Math.abs(ver); i > 1; i--) {
                        squareBetweenIsSet = chessboard.getSquare(column, row).isPieceSet();
                        if (squareBetweenIsSet) {
                            return false;
                        }
                        counter++;
                    }
                }
            }
        }
        return true;
    }

    public boolean getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }

    public Image getIcon() {
        return this.image;
    }
}
