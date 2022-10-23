package layers.model;

import layers.model.pieces.King;
import layers.model.pieces.Knight;
import layers.model.pieces.Pawn;
import layers.model.pieces.Piece;
import layers.model.pieces.Queen;
import layers.model.pieces.Rook;
import utils.Observable;

/**
 * This is the basic structure of a board used for the game.
 *
 * @author Tim Schmidt (tim.schmidt@student.ibs-ol.de)
 */
public class Board extends Observable {

    private final Square[][] board;
    private final int rows;
    private final int columns;

    public Board(int rows, int columns) {
        this.columns = columns;
        this.rows = rows;
        this.board = createChessBoard();
        initPieces();
    }

    /**
     * This method will fill the empty chessboard with {@link Square} new objects
     * and assign them their colors.
     *
     * @return A filled chessboard.
     */
    private Square[][] createChessBoard() {
        Square[][] chessBoard = new Square[getColumns()][getRows()];
        boolean color;
        for (int y = 0; y < getRows(); y++) {
            color = (y % 2 == 0);
            for (int x = 0; x < getColumns(); x++) {
                chessBoard[x][y] = new Square(x, y, color);
                color = !color;
            }
        }
        return chessBoard;
    }

    /**
     * This method creates and places all pieces on the chessboard.
     */
    private void initPieces() {
        // Set pawns
        for (int x = 0; x < 6; x++) {
            this.board[x][1].setPiece(new Pawn(false));  // Black Pawns
            this.board[x][4].setPiece(new Pawn(true));   // White Pawns
        }

        // Set Rooks
        this.board[0][0].setPiece(new Rook(false));      // Black Rooks
        this.board[5][0].setPiece(new Rook(false));
        this.board[0][5].setPiece(new Rook(true));       // White Rooks
        this.board[5][5].setPiece(new Rook(true));

        // Set Knights
        this.board[1][0].setPiece(new Knight(false));  // Black Knights
        this.board[4][0].setPiece(new Knight(false));
        this.board[1][5].setPiece(new Knight(true));   // White Knights
        this.board[4][5].setPiece(new Knight(true));

        // Set Queens
        this.board[2][0].setPiece(new Queen(false));    // Black Queen
        this.board[3][5].setPiece(new Queen(true));     // White Queen

        // Set Kings
        this.board[3][0].setPiece(new King(false));      // Black King
        this.board[2][5].setPiece(new King(true));       // White King
    }

    /**
     * This method will execute the given move and notify the observers that the chessboard has been changed.
     *
     * @param move A given move with two squares (fromSquare, toSquare).
     */
    public void executeMove(Tuple<Square, Square> move) {
        Piece piece = move.getFirst().getPiece();
        this.board[move.getFirst().getColumn()][move.getFirst().getRow()].setPiece(null);
        this.board[move.getSecond().getColumn()][move.getSecond().getRow()].setPiece(piece);
        notifyObservers(this, move);
    }

    public Square getSquare(int column, int row) {
        return this.board[column][row];
    }

    public Square getSquare(Square square) {
        return this.board[square.getColumn()][square.getRow()];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
