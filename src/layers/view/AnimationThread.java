package layers.view;

import layers.model.Square;
import layers.model.Tuple;
import layers.model.pieces.Piece;

public class AnimationThread extends Thread {

    private final Tuple<Square, Square> move;
    private final BoardPanel boardPanel;
    private final Piece piece;

    public AnimationThread(Tuple<Square, Square> move, BoardPanel boardPanel) {
        this.move = move;
        this.boardPanel = boardPanel;
        this.piece = move.getFirst().getPiece();
    }

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
            Tuple<Double, Double> animationMove = new Tuple<>((double) (index * boardPanelMove.getFirst() / 100) + 7.5, (double) (index * boardPanelMove.getSecond() / 100) + 15);
            boardPanel.drawMovingPiece(startPosition, animationMove, this.piece);
            index++;
        }
    }

    private Tuple<Integer, Integer> getBoardPanelMove(Tuple<Square, Square> move) {
        int column = move.getSecond().getRow() - move.getFirst().getRow();
        int row = move.getSecond().getColumn() - move.getFirst().getColumn();
        return new Tuple<>(row * 100, column * 100);
    }
}
