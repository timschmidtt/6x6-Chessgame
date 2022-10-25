package layers.model.actors.aiPlayer1;

import layers.model.Board;
import layers.model.Square;
import layers.model.Tuple;
import layers.model.actors.Player;
import layers.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    private int debug = 0;

    public ArtificialIntelligenceMove(Tuple<Square, Square> move, int searchingLevel, Board board, Player player) {
        this.board = board;
        this.move = move;
        this.searchingLevel = searchingLevel;
        this.isMoving = player.getColor();
    }

    /**
     * This will execute a given move and then calculate the best move and execute it.
     */
    public Tuple<Square, Square> getBestMove() {
        if (this.move != null) {
            this.board.executeMove(this.move);
        }
        return calcMove().getFirst();
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

    // TODO comment
    private Tuple<Tuple<Square, Square>, Integer> maxValue(int searchingLevel, boolean activePlayer) {
        Tuple<Square, Square> bestMove = null;
        int highestValue = -99999999;
        // Request all possible moves to the current game situation
        List<Tuple<Square, Square>> movesList = getAllNextMoves(activePlayer);
        Tuple<Tuple<Square, Square>, Integer> result = null;
        // Just check for null
        for (int i = 0; i < movesList.size(); i++) {
            System.out.println(debug);
            // simulate a move
            simulateMove(movesList.get(i));
            Tuple<Square, Square> simulatedMove;
            int simulatedMoveValue;
            // Decide if to go deeper or to stay on the current level
            if (searchingLevel <= 1) {
                // If there are no next moves we set the current move as the simulated,
                // the same goes for the value
                simulatedMove = movesList.get(i);
                simulatedMoveValue = ratingFunction(simulatedMove, activePlayer);
            } else if (activePlayer) {
                debug++;
                // Player A turns
                result = maxValue(searchingLevel - 1, false);
                simulatedMoveValue = result.getSecond();
                simulatedMove = result.getFirst();
            } else {
                debug++;
                // Player B turns
                result = minValue(searchingLevel - 1, true);
                simulatedMoveValue = result.getSecond();
                simulatedMove = result.getFirst();
            }
            // Restore the simulated move
            undoMove(movesList.get(i));
            // If the current simulated move vale is higher as the current
            // highest move vale we reassign the highest value and the best move
            if (simulatedMoveValue > highestValue) {
                if (isOwnMove(simulatedMove, activePlayer)) {
                    highestValue = simulatedMoveValue;
                    bestMove = simulatedMove;
                }
            }
            result = new Tuple<>(bestMove, highestValue);
        }
        // return the best move and the highest value
        return result;
    }

    // TODO comment
    private Tuple<Tuple<Square, Square>, Integer> minValue(int searchingLevel, boolean activePlayer) {
        Tuple<Square, Square> bestMove = null;
        int lowestValue = 99999999;
        // Request all possible moves to the current game situation
        List<Tuple<Square, Square>> movesList = getAllNextMoves(activePlayer);
        Tuple<Tuple<Square, Square>, Integer> result = null;
        for (int i = 0; i < movesList.size(); i++) {
            System.out.println(debug);
            // simulate a move
            simulateMove(movesList.get(i));
            Tuple<Square, Square> simulatedMove;
            int simulatedMoveValue;
            // Decide if to go deeper or to stay on the current level
            if (this.searchingLevel <= 1) {
                // If there are no next moves we set the current move as the simulated,
                // the same goes for the value
                simulatedMove = movesList.get(i);
                simulatedMoveValue = ratingFunction(simulatedMove, activePlayer);
            } else if (activePlayer) {
                debug++;
                // Player A turns
                result = maxValue(searchingLevel - 1, false);
                simulatedMoveValue = result.getSecond();
                simulatedMove = result.getFirst();
            } else {
                debug++;
                // Player B turns
                result = minValue(searchingLevel - 1, true);
                simulatedMoveValue = result.getSecond();
                simulatedMove = result.getFirst();
            }
            // Restore the simulated move
            undoMove(movesList.get(i));
            // If the current simulated move vale is higher as the current
            // highest move vale we reassign the highest value and the best move
            if (simulatedMoveValue < lowestValue) {
                if (isOwnMove(simulatedMove, activePlayer)) {
                    lowestValue = simulatedMoveValue;
                    bestMove = simulatedMove;
                }
            }
            if (bestMove.getFirst().getPiece() == null) {
                System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
            }
            result = new Tuple<>(bestMove, lowestValue);
        }
        return result;
    }

    private boolean isOwnMove(Tuple<Square, Square> simulatedMove, boolean activePlayer) {
        boolean pieceColor = simulatedMove.getFirst().getPiece().getColor();
        return activePlayer == pieceColor;
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
    private int ratingFunction(Tuple<Square, Square> move, boolean activePlayer) {
        // This will rate how good the current board is for the player
        Square toSquare = move.getSecond();
        int result = 0;
        // We get the beaten piece that lead us to this situation. As more important
        // the piece was as higher the points will be for it.
        if (toSquare.isPieceSet()) {
            switch (toSquare.getPiece().getName()) {
                case "King":
                    result += 999999;
                    break;
                case "Queen":
                    result += 2000;
                    break;
                case "Knight":
                    result += 1400;
                    break;
                case "Rook":
                    result += 1200;
                    break;
                case "Pawn":
                    result += 400;
                    break;
            }
        }
        /*
        // Add value of how many pieces could get beaten
        Tuple<Integer, List<Tuple<Square, Square>>> beatableActions = checkBeatableActions(activePlayer);
        result += beatableActions.getFirst();
        // Rate how good is it to opfer a piece
        result += sacrificePiece(activePlayer, beatableActions.getSecond());

         */
        return result;
    }

    /**
     * This will check how many own pieces could get beaten right now by the
     * enemy and by the player himself. There is a special amount of points
     * for every piece that falls into this category.
     *
     * @param activePlayer The current active player.
     *
     * @return A value to rate the game situation.
     */
    private Tuple<Integer, List<Tuple<Square, Square>>> checkBeatableActions(boolean activePlayer) {
        AtomicInteger result = new AtomicInteger();
        List<Tuple<Square, Square>> ownMoves = getAllNextMoves(activePlayer);
        List<Tuple<Square, Square>> opponentMoves = getAllNextMoves(!activePlayer);
        // Current player can beat pieces
        ownMoves.forEach(ownMove -> {
            if (ownMove.getSecond().isPieceSet()) {
                Piece piece = ownMove.getSecond().getPiece();
                switch (piece.getName()) {
                    case "King":
                        result.addAndGet(99999);
                        break;
                    case "Queen":
                        result.addAndGet(200);
                        break;
                    case "Knight":
                        result.addAndGet(140);
                        break;
                    case "Rook":
                        result.addAndGet(120);
                        break;
                    case "Pawn":
                        result.addAndGet(40);
                        break;
                }
            }
        });
        // Opponent can beat pieces
        List<Tuple<Square, Square>> movesThatCanBeat = new ArrayList<>();
        opponentMoves.forEach(opponentMove -> {
            if (opponentMove.getSecond().isPieceSet()) {
                movesThatCanBeat.add(opponentMove);
                Piece piece = opponentMove.getSecond().getPiece();
                switch (piece.getName()) {
                    case "King":
                        result.addAndGet(-99999);
                        break;
                    case "Queen":
                        result.addAndGet(-200);
                        break;
                    case "Knight":
                        result.addAndGet(-140);
                        break;
                    case "Rook":
                        result.addAndGet(-120);
                        break;
                    case "Pawn":
                        result.addAndGet(-40);
                        break;
                }
            }
        });
        return new Tuple<>(result.get(), movesThatCanBeat);
    }

    private int sacrificePiece(boolean activePlayer, List<Tuple<Square, Square>> beatableActions) {
        AtomicInteger result = new AtomicInteger();
        List<Tuple<Square, Square>> ownMoves = getAllNextMoves(activePlayer);
        beatableActions.forEach(opponentMove -> {
            result.addAndGet(checkSacrificeWorth(opponentMove, ownMoves, activePlayer));
        });
        return result.get();
    }

    private int checkSacrificeWorth(Tuple<Square, Square> opponentMove, List<Tuple<Square, Square>> ownMoves, boolean activePlayer) {
        AtomicInteger result = new AtomicInteger();
        ownMoves.forEach(ownMove ->{
            simulateMove(ownMove);
            if (isInvalid(opponentMove, activePlayer)) {
                switch (ownMove.getFirst().getPiece().getName()) {
                    case "King":
                        result.addAndGet(-9999);
                        break;
                    case "Queen":
                        result.addAndGet(+100);
                        break;
                    case "Knight":
                        result.addAndGet(+140);
                        break;
                    case "Rook":
                        result.addAndGet(+160);
                        break;
                    case "Pawn":
                        result.addAndGet(+200);
                        break;
                }
            }
            undoMove(ownMove);
        });
        return result.get();
    }

    private boolean isInvalid(Tuple<Square, Square> opponentMove, boolean activePlayer) {
        Tuple<Integer, Integer> coordinateMove = new Tuple<>(
                opponentMove.getFirst().getColumn() - opponentMove.getSecond().getColumn(),
                opponentMove.getFirst().getRow() - opponentMove.getSecond().getRow()
        );
        return !isPossible(coordinateMove, opponentMove.getFirst(), opponentMove.getFirst().getPiece(), activePlayer);
    }

    /**
     * Call this method to retrieve all possible executable moves of the current
     * player for all his current being alive pieces.
     *
     * @return All possible moves.
     */
    private List<Tuple<Square, Square>> getAllNextMoves(boolean player) {
        List<Tuple<Square, Square>> moveList = new ArrayList<>();
        // Iterate over all squares
        for (int column = 0; column < this.board.getColumns(); column++) {
            for (int row = 0; row < this.board.getRows(); row++) {
                Square square = board.getSquare(column, row);
                // Check if there is a piece of the player
                if (square.isPieceSet()) {
                    if (square.getPiece().getColor() == player) {
                        Piece piece = square.getPiece();
                        piece.getMoves().forEach(move -> {
                            // Add all possible moves to the list
                            if (isPossible(move, square, piece, player)) {
                                Tuple<Square, Square> moveSquare = getMoveSquare(move, square);
                                moveList.add(moveSquare);
                            }
                        });
                    }
                }
            }
        }
        return moveList;
    }

    /**
     * Checks if a given move of a given piece could be executed
     * on the current board.
     *
     * @param move       The given move to proof.
     * @param fromSquare The square where the piece is standing.
     * @param piece      The piece that will do the move.
     * @return If the move can be executed.
     */
    private boolean isPossible(Tuple<Integer, Integer> move, Square fromSquare, Piece piece, boolean activePlayer) {
        // Check if the move could be executed
        if (piece.canMove(move, fromSquare, this.board)) {
            return canTakeSquare(new Tuple<>(fromSquare.getColumn(), fromSquare.getRow()), move, this.board, activePlayer);
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
    private boolean canTakeSquare(Tuple<Integer, Integer> fromSquare, Tuple<Integer, Integer> move, Board board, boolean activePlayer) {
        int column = fromSquare.getFirst() + move.getFirst();
        int row = fromSquare.getSecond() + move.getSecond();
        if (column <= 5 && column >= 0 && row <= 5 && row >= 0) {
            Square toSquare = board.getSquare(column, row);
            if (toSquare.getPiece() != null) {
                return toSquare.getPiece().getColor() != activePlayer;
            }
            return true;
        }
        return false;
    }

    /**
     * Returns a {@link Tuple} with two {@link Square}s (fromSquare, toSquare) of the given move
     * and the given fromSquare.
     *
     * @param move       The given move.
     * @param fromSquare The given fromSquare.
     * @return A {@link Tuple} of two {@link Square}s.
     */
    private Tuple<Square, Square> getMoveSquare(Tuple<Integer, Integer> move, Square fromSquare) {
        fromSquare = fromSquare.cloneSquare();
        Square toSquare = this.board.getSquare(fromSquare.getColumn() + move.getFirst(), fromSquare.getRow() + move.getSecond());
        toSquare = toSquare.cloneSquare();
        return new Tuple<>(fromSquare, toSquare);
    }
}
