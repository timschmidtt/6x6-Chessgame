package layers.model.actors;

import layers.model.Board;
import layers.model.Square;
import layers.model.Tuple;

/**
 * Abstract class that defines the basic structure of all players.
 *
 * @author Tim Schmidt (tim.schmidt@student.ibs-ol.de)
 */
public abstract class Player {

    private Board chessBoard;
    private final Boolean color;
    private final String name;
    private Tuple<Square, Square> lastMove;

    public Player(Boolean color, String name) {
        this.chessBoard = new Board(6,6);
        this.color = color;
        this.name = name;
    }

    public abstract Tuple<Square, Square> getNextMove(Tuple<Square, Square> move);

    public Boolean getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public Board getChessBoard() {
        return this.chessBoard;
    }

    public void setChessBoard(Board chessBoard) {
        this.chessBoard = chessBoard;
    }

    public void setLastMove(Tuple<Square, Square> lastMove) {
        this.lastMove = lastMove;
    }

    public Tuple<Square, Square> getLastMove() {
        return this.lastMove;
    }
}
