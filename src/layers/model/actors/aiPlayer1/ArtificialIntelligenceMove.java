package layers.model.actors.aiPlayer1;

import layers.model.Board;
import layers.model.Square;
import layers.model.Tuple;
import layers.model.actors.Player;
import layers.model.pieces.Piece;
import java.util.ArrayList;
import java.util.List;

/**
 * This move class is used to identify the best possible move. Therefore, we search
 * through an undefined depth of consecutive possible moves.
 *
 * @author Tim Schmidt (tim.schmidt@student.ibs-ol.de)
 */
public class ArtificialIntelligenceMove {

    private final Board board;
    private final Tuple<Square, Square> move;
    private final int searchingLevel;
    private final boolean isMoving;
    private Piece beatenPiece;

    public ArtificialIntelligenceMove(Tuple<Square, Square> move, int searchingLevel, Board board, Player player) {
        this.board = board;
        this.move = move;
        this.searchingLevel = searchingLevel;
        this.isMoving = player.getColor();
        executeMoveAction();
    }

    /**
     * This will execute a given move and then calculate the best move and execute it.
     */
    public void executeMoveAction() {
        if (this.move != null) {
            this.board.executeMove(this.move);
        }
        Tuple<Square, Square> bestMove = calcMove().getFirst();
        this.board.executeMove(bestMove);
    }

    /**
     * Decides if to use the maxValue or the minValue function.
     *
     * @return The min or max value and the best move.
     */
    private Tuple<Tuple<Square, Square>, Integer> calcMove() {
        if (this.isMoving) {
            return maxValue(this.searchingLevel, true);
        } else {
            return minValue(this.searchingLevel, false);
        }
    }

    /**
     *
     *
     * @param searchingLevel
     * @param isMoving
     * @return
     */
    private Tuple<Tuple<Square, Square>, Integer> maxValue(int searchingLevel, boolean isMoving) {
        Tuple<Square, Square> bestMove = null;
        int highestValue = -99999999;
        // Request all possible moves to the current game situation
        List<Tuple<Square, Square>> movesList = getAllNextMoves();
        Tuple<Tuple<Square, Square>, Integer> result = null;
        // Just check for null
        if (movesList != null) {
            for (int i = 0; i < movesList.size(); i++) {
                // simulate a move
                simulateMove(movesList.get(i));
                Tuple<Square, Square> simulatedMove = null;
                int simulatedMoveValue = 0;
                // Decide if to go deeper or to stay on the current level
                if (searchingLevel <= 1 || i + 1 < movesList.size()) {
                    // If there are no next moves we set the current move as the simulated,
                    // the same goes for the value
                    simulatedMove = movesList.get(i);
                    simulatedMoveValue = ratingFunction(simulatedMove);
                } else if (isMoving) {
                    // Player A turns
                    result = maxValue(searchingLevel - 1, false);
                } else {
                    // Player B turns
                    result = minValue(searchingLevel - 1, true);
                }
                // Restore the simulated move
                undoMove(movesList.get(i));
                // If the current simulated move vale is higher as the current
                // highest move vale we reassign the highest value and the best move
                if (simulatedMoveValue > highestValue) {
                    highestValue = simulatedMoveValue;
                    bestMove = simulatedMove;
                }
                result = new Tuple<>(bestMove, highestValue);
            }
        }
        // return the best move and the highest value
        return result;
    }

    // TODO comment
    private Tuple<Tuple<Square, Square>, Integer> minValue(int searchingLevel, boolean isMoving) {
        Tuple<Square, Square> bestMove = null;
        int lowestValue = 99999999;
        // Request all possible moves to the current game situation
        List<Tuple<Square, Square>> movesList = getAllNextMoves();
        Tuple<Tuple<Square, Square>, Integer> result = null;
        if (movesList != null) {
            for (int i = 0; i < movesList.size(); i++) {
                // simulate a move
                simulateMove(movesList.get(i));
                Tuple<Square, Square> simulatedMove = null;
                int simulatedMoveValue = 0;
                // Decide if to go deeper or to stay on the current level
                if (this.searchingLevel <= 1 || i + 1 < movesList.size()) {
                    // If there are no next moves we set the current move as the simulated,
                    // the same goes for the value
                    simulatedMove = movesList.get(i);
                    simulatedMoveValue = ratingFunction(simulatedMove);
                } else if (isMoving) {
                    // Player A turns
                    result = maxValue(searchingLevel - 1, false);
                } else {
                    // Player B turns
                    result = minValue(searchingLevel - 1, true);
                }
                // Restore the simulated move
                undoMove(movesList.get(i));
                // If the current simulated move vale is higher as the current
                // highest move vale we reassign the highest value and the best move
                if (simulatedMoveValue < lowestValue) {
                    lowestValue = simulatedMoveValue;
                    bestMove = simulatedMove;
                }
                result = new Tuple<>(bestMove, lowestValue);
            }
        }
        return result;
    }

    /**
     * This will execute a move on the own board but will not notify any observers.
     *
     * @param move The move to be simulated.
     */
    private void simulateMove(Tuple<Square, Square> move) {
        // First we need to get the moving piece
        Piece piece = move.getFirst().getPiece();
        // Then we place the moving piece at the destination square and
        // delete it at the origin square
        this.board.getSquare(move.getSecond().getColumn(), move.getSecond().getRow()).setPiece(piece);
        this.board.getSquare(move.getFirst().getColumn(), move.getFirst().getRow()).setPiece(null);
    }

    /**
     * This will undo a former executed move and set the
     * board back to it former state.
     *
     * @param move The move to be undone.
     */
    private void undoMove(Tuple<Square, Square> move) {
        // First get the pieces that were involved into the move
        Piece piece = move.getFirst().getPiece();
        Piece beatenPiece = move.getSecond().getPiece();
        // Then we set the pieces to their origin square
        this.board.getSquare(move.getFirst().getColumn(), move.getFirst().getRow()).setPiece(piece);
        this.board.getSquare(move.getSecond().getColumn(), move.getSecond().getRow()).setPiece(beatenPiece);
    }

    /**
     * This method is like the brain of the AI. It will rate how good or bad the current
     * state of the board is for the given player. As higher the return value of this method
     * is, as better the current state of the game is.
     *
     * @param move The last simulated move.
     * @return A value how good the game state is.
     */
    private int ratingFunction(Tuple<Square, Square> move) {
        // This will rate how good the current board is for the player
        Square fromSquare = move.getFirst();
        Square toSquare = move.getSecond();
        int result = 0;

        if (toSquare.isPieceSet()) {
            switch (toSquare.getPiece().getName()) {
                case "King":
                    result += 9999999;
                    break;
                case "Queen":
                    result += 100;
                    break;
                case "Knight":
                    result += 70;
                    break;
                case "Rook":
                    result += 60;
                    break;
                case "Pawn":
                    result += 10;
                    break;
            }

        }
        return result;
    }

    /**
     * Call this method to retrieve all possible executable moves of the current
     * player for all his current being alive pieces.
     *
     * @return All possible moves.
     */
    private List<Tuple<Square, Square>> getAllNextMoves() {
        List<Tuple<Square, Square>> moveList = new ArrayList<>();
        // Iterate over all squares
        for (int column = 0; column < this.board.getColumns(); column++) {
            for (int row = 0; row < this.board.getRows(); row++) {
                Square square = board.getSquare(column, row);
                // Check if there is a piece of the player
                if (square.isPieceSet()) {
                    if (square.getPiece().getColor() == isMoving) {
                        Piece piece = square.getPiece();
                        piece.getMoves().forEach(move -> {
                            // Add all possible moves to the list
                            if (isPossible(move, square, piece)) {
                                Tuple<Square, Square> moveSquare = getMoveSquare(move, square);
                                moveList.add(moveSquare);
                            }
                        });
                        return moveList;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Checks if a given move of a given piece could be executed
     * on the current board.
     *
     * @param move The given move to proof.
     * @param fromSquare The square where the piece is standing.
     * @param piece The piece that will do the move.
     *
     * @return If the move can be executed.
     */
    private boolean isPossible(Tuple<Integer, Integer> move, Square fromSquare, Piece piece) {
        // Check if the move could be executed
        if (piece.canMove(move, fromSquare, this.board)) {
            return canTakeSquare(new Tuple<>(fromSquare.getColumn(), fromSquare.getRow()), move, this.board);
        }
        return false;
    }

    /**
     * Proofs if a piece can execute a move on the current {@link Board}.
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
                return toSquare.getPiece().getColor() != isMoving;
            }
            return true;
        }
        return false;
    }

    /**
     * Returns a {@link Tuple} with two {@link Square}s (fromSquare, toSquare) of the given move
     * and the given fromSquare.
     *
     * @param move The given move.
     * @param fromSquare The given fromSquare.
     *
     * @return A {@link Tuple} of two {@link Square}s.
     */
    private Tuple<Square, Square> getMoveSquare(Tuple<Integer, Integer> move, Square fromSquare) {
        fromSquare = fromSquare.cloneSquare();
        Square toSquare = this.board.getSquare(fromSquare.getColumn() + move.getFirst(), fromSquare.getRow() + move.getSecond());
        toSquare = toSquare.cloneSquare();
        return new Tuple<>(fromSquare, toSquare);
    }
}
