package layers.model.actors.aiPlayer1;

import layers.model.Board;
import layers.model.Square;
import layers.model.Tuple;
import layers.model.actors.Player;
import layers.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    private boolean isMoving;

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

        //return maxValue(this.searchingLevel);
        if (this.isMoving) {
            return maxValue(this.searchingLevel);
        } else {
            return minValue(this.searchingLevel);
        }
    }

    // TODO comment
    private Tuple<Tuple<Square, Square>, Integer> maxValue(int searchingLevel) {
        Tuple<Square, Square> bestMove = null;
        int highestValue = -999999999;
        // Request all possible moves to the current game situation
        List<Tuple<Square, Square>> movesList = getAllNextMoves();
        Tuple<Tuple<Square, Square>, Integer> result;
        // Just check for null
        for (int i = 0; i < movesList.size(); i++) {
            // simulate a move
            simulateMove(movesList.get(i));
            Tuple<Square, Square> simulatedMove;
            int simulatedMoveValue;
            System.out.println(i + ",,," + movesList.size());
            // Decide if to go deeper or to stay on the current level
            if (searchingLevel <= 1) {
                // If there are no next moves we set the current move as the simulated,
                // the same goes for the value
                simulatedMove = movesList.get(i);
                simulatedMoveValue = ratingFunction(simulatedMove);
            } else if (this.isMoving) {
                // Player A turns
                result = maxValue(searchingLevel - 1);
                simulatedMoveValue = result.getSecond();
                simulatedMove = result.getFirst();
            } else {
                // Player B turns
                result = minValue(searchingLevel - 1);
                simulatedMoveValue = result.getSecond();
                simulatedMove = result.getFirst();
            }
            // Restore the simulated move
            undoMove(movesList.get(i));
            // If the current simulated move vale is higher as the current
            // highest move vale we reassign the highest value and the best move
            System.out.println(simulatedMoveValue + " max");
            if (simulatedMoveValue > highestValue) {
                highestValue = simulatedMoveValue;
                bestMove = simulatedMove;
            } else if (simulatedMoveValue == highestValue) {
                Random random = new Random();
                int randomIndex = random.nextInt(2) + 1;
                if (randomIndex == 1) {
                    bestMove = simulatedMove;
                }
            }
        }
        // return the best move and the highest value
        return new Tuple<>(bestMove, highestValue);
    }

    // TODO comment
    private Tuple<Tuple<Square, Square>, Integer> minValue(int searchingLevel) {
        Tuple<Square, Square> bestMove = null;
        int lowestValue = 999999999;
        // Request all possible moves to the current game situation
        List<Tuple<Square, Square>> movesList = getAllNextMoves();
        Tuple<Tuple<Square, Square>, Integer> result;
        for (int i = 0; i < movesList.size() - 1; i++) {
            // simulate a move
            simulateMove(movesList.get(i));
            Tuple<Square, Square> simulatedMove;
            int simulatedMoveValue;
            System.out.println(i + ",,," + movesList.size());
            // Decide if to go deeper or to stay on the current level
            if (searchingLevel <= 1) {
                // If there are no next moves we set the current move as the simulated,
                // the same goes for the value
                simulatedMove = movesList.get(i);
                simulatedMoveValue = ratingFunction(simulatedMove);
            } else if (this.isMoving) {
                // Player A turns
                result = maxValue(searchingLevel - 1);
                simulatedMoveValue = result.getSecond();
                simulatedMove = result.getFirst();
            } else {
                // Player B turns
                result = minValue(searchingLevel - 1);
                simulatedMoveValue = result.getSecond();
                simulatedMove = result.getFirst();
            }
            // Restore the simulated move
            undoMove(movesList.get(i));
            // If the current simulated move vale is higher as the current
            // highest move vale we reassign the highest value and the best move
            System.out.println(simulatedMoveValue + " min");
            if (simulatedMoveValue < lowestValue) {
                lowestValue = simulatedMoveValue;
                bestMove = simulatedMove;
            } else if (simulatedMoveValue == lowestValue) {
                Random random = new Random();
                int randomIndex = random.nextInt(2) + 1;
                if (randomIndex == 1) {
                    bestMove = simulatedMove;
                }
            }
        }
        return new Tuple<>(bestMove, lowestValue);
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
        // Swap the player
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
        this.isMoving = !this.isMoving;
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
        Square toSquare = move.getSecond();
        int result = 0;
        // We get the beaten piece that lead us to this situation. As more important
        // the piece was as higher the points will be for it.
        result = countPieceScore();
        // Add value of how many pieces could get beaten
        //Tuple<Integer, List<Tuple<Square, Square>>> beatableActions = checkBeatableActions();
        //result += beatableActions.getFirst();
        // Rate how good is it to opfer a piece
        //result += sacrificePiece(activePlayer, beatableActions.getSecond());
        return result;
    }

    public int countPieceScore() {
        int result = 0;
        for (int row = 0; row < this.board.getRows(); row++) {
            for (int column = 0; column < this.board.getColumns(); column++) {
                Square square = this.board.getSquare(column, row);
                if (square.isPieceSet()) {
                    Piece piece = square.getPiece();
                    if (piece.getColor()) {
                        switch (piece.getName()) {
                            case "King":
                                result += 99999;
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
                    } else {
                        switch (piece.getName()) {
                            case "King":
                                result -= 99999;
                                break;
                            case "Queen":
                                result -= 2000;
                                break;
                            case "Knight":
                                result -= 1400;
                                break;
                            case "Rook":
                                result -= 1200;
                                break;
                            case "Pawn":
                                result -= 400;
                                break;
                        }
                    }

                }
            }
        }
        return result;
    }

    /**
     * This will check how many own pieces could get beaten right now by the
     * enemy and by the player himself. There is a special amount of points
     * for every piece that falls into this category.
     *
     * @return A value to rate the game situation.
     */
    private Tuple<Integer, List<Tuple<Square, Square>>> checkBeatableActions() {
        AtomicInteger result = new AtomicInteger();
        List<Tuple<Square, Square>> ownMoves = getAllNextMoves();
        List<Tuple<Square, Square>> opponentMoves = getAllNextMoves();
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
        /*
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

         */
        return new Tuple<>(result.get(), null);
    }

    private int sacrificePiece(List<Tuple<Square, Square>> beatableActions) {
        AtomicInteger result = new AtomicInteger();
        List<Tuple<Square, Square>> ownMoves = getAllNextMoves();
        beatableActions.forEach(opponentMove -> {
            result.addAndGet(checkSacrificeWorth(opponentMove, ownMoves));
        });
        return result.get();
    }

    private int checkSacrificeWorth(Tuple<Square, Square> opponentMove, List<Tuple<Square, Square>> ownMoves) {
        AtomicInteger result = new AtomicInteger();
        ownMoves.forEach(ownMove -> {
            simulateMove(ownMove);
            if (isInvalid(opponentMove)) {
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

    private boolean isInvalid(Tuple<Square, Square> opponentMove) {
        Tuple<Integer, Integer> coordinateMove = new Tuple<>(
                opponentMove.getFirst().getColumn() - opponentMove.getSecond().getColumn(),
                opponentMove.getFirst().getRow() - opponentMove.getSecond().getRow()
        );
        return !isPossible(coordinateMove, opponentMove.getFirst(), opponentMove.getFirst().getPiece());
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
        for (int row = 0; row < this.board.getColumns(); row++) {
            for (int column = 0; column < this.board.getRows(); column++) {
                Square square = this.board.getSquare(column, row);
                // Check if there is a piece of the activePlayer
                if (square.isPieceSet()) {
                    if (square.getPiece().getColor() == this.isMoving) {
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
                return toSquare.getPiece().getColor() != this.isMoving;
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
