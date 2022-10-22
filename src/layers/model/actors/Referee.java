package layers.model.actors;

import layers.model.Board;
import layers.model.Square;
import layers.model.Tuple;
import layers.model.pieces.Piece;
import utils.Observable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Referee extends Observable {

    private Player currentPlayer;

    /**
     * At first the move get checked for the common chess rules by {@link #checkCommonChessRules(Piece, Piece)}.
     * After, it will be checked if the chosen move is compatible with chosen piece.
     *
     * @return A boolean if the move is legal
     */
    public Boolean checkMove(Tuple<Square, Square> squareMove, Board chessboard) {
        Square fromSquare = squareMove.getFirst();
        Square toSquare = squareMove.getSecond();
        // Check the common chess rules
        if (!checkCommonChessRules(fromSquare.getPiece(), toSquare.getPiece())) {
            return false;
        }
        // If the piece is able to do the move we return true, otherwise false.
        Tuple<Integer, Integer> squareDelta = calcSquareDelta(fromSquare, toSquare);
        AtomicBoolean isMoveLegal = new AtomicBoolean(false);
        Piece piece = fromSquare.getPiece();
        piece.getMoves().forEach(move -> {
            if (move.equals(squareDelta) && piece.canMove(squareDelta, fromSquare, chessboard)) {
                isMoveLegal.set(true);
            }
        });
        return isMoveLegal.get();
    }

    /**
     * Here the referee will check if the King gets beaten.
     *
     * @param move The move to check.
     * @return A boolean if the king gets beaten.
     */
    public boolean isKingBeaten(Tuple<Square, Square> move) {
        if (move.getSecond().getPiece() != null) {
            notifyObservers(this, move);
            return move.getSecond().getPiece().getName().equals("King");
        }
        return false;
    }

    /**
     * Assure if the player moves his own pieces, just beats enemy pieces
     * and chooses a square with a piece on it.
     *
     * @return A boolean if the common chess rules are followed.
     */
    private boolean checkCommonChessRules(Piece fromSquarePiece, Piece toSquarePiece) {
        boolean currentPlayerColor = this.currentPlayer.getColor();
        if (toSquarePiece != null) {
            if (0 == Boolean.compare(toSquarePiece.getColor(), currentPlayerColor)) {
                return false;
            }
        }
        if (fromSquarePiece == null) {
            return false;
        } else return fromSquarePiece.getColor() == currentPlayerColor;
    }

    /**
     * This method calculates the delta between two squares.
     *
     * @param fromSquare the square from where the move starts.
     * @param toSquare   the square where the move ends.
     * @return The delta of the two squares.
     */
    private Tuple<Integer, Integer> calcSquareDelta(Square fromSquare, Square toSquare) {
        return new Tuple<>(toSquare.getColumn() - fromSquare.getColumn(), toSquare.getRow() - fromSquare.getRow());
    }

    /**
     * Checks if the given piece can be selected by the current player.
     *
     * @param x          The row of the wanted piece.
     * @param y          The column of the wanted piece.
     * @param chessboard The current chessboard.
     * @return If the current player is allowed to select the wanted piece.
     */
    public boolean canPieceGetSelected(int x, int y, Board chessboard) {
        return chessboard.getSquare(x, y).getPiece().getColor() == this.currentPlayer.getColor();
    }

    /**
     * Creates a list of possible moves of the given piece.
     *
     * @param activePiece The current active piece.
     * @param fromSquare  The start square of the active piece.
     * @param board       The current chessboard.
     * @return A list of possible moves of the given piece.
     */
    public List<Tuple<Integer, Integer>> getPossibleMoves(Piece activePiece, Tuple<Integer, Integer> fromSquare, Board board) {
        List<Tuple<Integer, Integer>> moves = new ArrayList<>();
        activePiece.getMoves().forEach(move -> {
            if (activePiece.canMove(move, board.getSquare(fromSquare.getFirst(), fromSquare.getSecond()), board)) {
                if (canTakeSquare(fromSquare, move, board)) {
                    moves.add(move);
                }
            }
        });
        return moves;
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
                return toSquare.getPiece().getColor() != this.currentPlayer.getColor();
            }
            return true;
        }
        return false;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        notifyObservers(this, this.currentPlayer);
    }
}
