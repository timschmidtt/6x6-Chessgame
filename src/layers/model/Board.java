package layers.model;

import layers.model.pieces.King;
import layers.model.pieces.Knight;
import layers.model.pieces.Pawn;
import layers.model.pieces.Piece;
import layers.model.pieces.Queen;
import layers.model.pieces.Rook;
import utils.Observable;

public class Board extends Observable {

    private final Square[][] chessBoard;
    private final int rows;
    private final int columns;

    public Board(int rows, int columns) {
        this.columns = columns;
        this.rows = rows;
        this.chessBoard = createChessBoard();
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
            this.chessBoard[x][1].setPiece(new Pawn(false));  // Black Pawns
            this.chessBoard[x][4].setPiece(new Pawn(true));   // White Pawns
        }

        // Set Rooks
        this.chessBoard[0][0].setPiece(new Rook(false));      // Black Rooks
        this.chessBoard[5][0].setPiece(new Rook(false));
        this.chessBoard[0][5].setPiece(new Rook(true));       // White Rooks
        this.chessBoard[5][5].setPiece(new Rook(true));

        // Set Knights
        this.chessBoard[1][0].setPiece(new Knight(false));  // Black Knights
        this.chessBoard[4][0].setPiece(new Knight(false));
        this.chessBoard[1][5].setPiece(new Knight(true));   // White Knights
        this.chessBoard[4][5].setPiece(new Knight(true));

        // Set Queens
        this.chessBoard[2][0].setPiece(new Queen(false));    // Black Queen
        this.chessBoard[3][5].setPiece(new Queen(true));     // White Queen

        // Set Kings
        this.chessBoard[3][0].setPiece(new King(false));      // Black King
        this.chessBoard[2][5].setPiece(new King(true));       // White King
    }

    /**
     * This method will execute the given move and notify the observers that the chessboard has been changed.
     *
     * @param move A given move with two squares (fromSquare, toSquare).
     */
    public void executeMove(Tuple<Square, Square> move) {
        Piece piece = move.getFirst().getPiece();
        this.chessBoard[move.getFirst().getColumn()][move.getFirst().getRow()].setPiece(null);
        this.chessBoard[move.getSecond().getColumn()][move.getSecond().getRow()].setPiece(piece);
        notifyObservers(this, move);
    }

    public Square getSquare(int column, int row) {
        return this.chessBoard[column][row];
    }

    public Square getSquare(Square square) {
        return this.chessBoard[square.getColumn()][square.getRow()];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
