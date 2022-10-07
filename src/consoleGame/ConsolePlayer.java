package consoleGame;

import layers.model.Board;
import layers.model.Square;
import layers.model.Tuple;
import layers.model.actors.Player;

public class ConsolePlayer extends Player {

    public ConsolePlayer(Boolean color, String name) {
        super(color, name);
    }

    /**
     * This method lets a console player make a move on his own chessboard.
     * The move should be approved by a referee first.
     *
     * @param move the move to do.
     */
    @Override
    public Tuple<Square, Square> getNextMove(Tuple<Square, Square> move) {
        Board board = getChessBoard();
        Square fromSquare = move.getFirst();
        Square toSquare = move.getSecond();
        // Make the moves on the local var board.
        board.getSquare(toSquare).setPiece(board.getSquare(fromSquare).getPiece());
        board.getSquare(fromSquare).removePiece();
        // Save the move on the right board.
        setChessBoard(board);
        return null;
    }
}
