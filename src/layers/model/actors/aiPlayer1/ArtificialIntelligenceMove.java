package layers.model.actors.aiPlayer1;

import layers.model.Board;
import layers.model.Square;
import layers.model.Tuple;
import layers.model.actors.Player;
import layers.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * DOC: Document me Tim !
 *
 * @author Tim Schmidt (tim.schmidt@cewe.de)
 * @since 17.10.22
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

    public void executeMoveAction() {
        if (this.move != null) {
            this.board.executeMove(this.move);
        }
        Tuple<Square, Square> bestMove = calcMove().getFirst();
        this.board.executeMove(bestMove);
    }

    // TODO comment
    private Tuple<Tuple<Square, Square>, Integer> calcMove() {
        if (this.isMoving) {
            return maxValue(this.searchingLevel);
        } else {
            return minValue(this.searchingLevel);
        }
    }

    // TODO comment
    private Tuple<Tuple<Square, Square>, Integer> maxValue(int searchingLevel) {

        // TODO Spieler wechsel

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
                    result = maxValue(searchingLevel - 1);
                } else {
                    // Player B turns
                    result = minValue(searchingLevel - 1);
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
    private Tuple<Tuple<Square, Square>, Integer> minValue(int searchingLevel) {
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
                    result = maxValue(searchingLevel - 1);
                } else {
                    // Player B turns
                    result = minValue(searchingLevel - 1);
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

    // TODO comment
    private void simulateMove(Tuple<Square, Square> move) {
        // First we need to get the moving piece
        Piece piece = move.getFirst().getPiece();
        // Then we place the moving piece at the destination square and
        // delete it at the origin square
        this.board.getSquare(move.getSecond().getColumn(), move.getSecond().getRow()).setPiece(piece);
        this.board.getSquare(move.getFirst().getColumn(), move.getFirst().getRow()).setPiece(null);
    }

    // TODO comment
    private void undoMove(Tuple<Square, Square> move) {
        // First get the pieces that were involved into the move
        Piece piece = move.getFirst().getPiece();
        Piece beatenPiece = move.getSecond().getPiece();
        // Then we set the pieces to their origin square
        this.board.getSquare(move.getFirst().getColumn(), move.getFirst().getRow()).setPiece(piece);
        this.board.getSquare(move.getSecond().getColumn(), move.getSecond().getRow()).setPiece(beatenPiece);
    }

    // TODO comment
    private int ratingFunction(Tuple<Square, Square> move) {
        // This will rate how good the current board is for the player
        Square fromSquare = move.getFirst();
        Square toSquare = move.getSecond();
        int result = 0;

        if (toSquare.isPieceSet()) {
            switch (toSquare.getPiece().getName()) {
                case "King":
                    result += 200;
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

    // TODO comment
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
                return toSquare.getPiece().getColor() != isMoving;
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
