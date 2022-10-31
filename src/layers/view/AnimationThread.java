package layers.view;

import layers.model.Square;
import layers.model.Tuple;
import layers.model.pieces.Piece;

/**
 * The animation thread will animate an executed move
 * on the chessboard.
 *
 * @author Tim Schmidt (tim.schmidt@student.ibs-ol.de)
 */
public class AnimationThread extends Thread {

    private final Tuple<Square, Square> move;
    private final BoardPanel boardPanel;
    private final Piece piece;

    public AnimationThread(Tuple<Square, Square> move, BoardPanel boardPanel) {
        this.move = move;
        this.boardPanel = boardPanel;
        this.piece = move.getFirst().getPiece();
    }

    /**
     * Here the thread get started. The move that will be animated will be divided
     * into the 100 single parts. Each of them will be displayed separated on the board
     * in a short term, so it seems like the piece slides over it.
     */
    @Override
    public void run() {
        Tuple<Integer, Integer> boardPanelMove = getBoardPanelMove(this.move);
        Tuple<Integer, Integer> startPosition = new Tuple<>(this.move.getFirst().getColumn() * 100, this.move.getFirst().getRow() * 100);
        int index = 1;
        while (index != 101) {
            try {
                Thread.sleep(1100 / 100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Tuple<Double, Double> animationMove = new Tuple<>(
                    (double) (index * boardPanelMove.getFirst() / 100) + 7.5,
                    (double) (index * boardPanelMove.getSecond() / 100) + 15);
            boardPanel.drawMovingPiece(startPosition, animationMove, this.piece);
            index++;
        }
    }

    /**
     * This method calculates the done move by subtracting
     * the columns and rows from the fromSquare and toSquare.
     *
     * @param move The done move (fromSquare and toSquare).
     * @return The done move (coordinates).
     */
    private Tuple<Integer, Integer> getBoardPanelMove(Tuple<Square, Square> move) {
        int column = move.getSecond().getRow() - move.getFirst().getRow();
        int row = move.getSecond().getColumn() - move.getFirst().getColumn();
        return new Tuple<>(row * 100, column * 100);
    }
}
