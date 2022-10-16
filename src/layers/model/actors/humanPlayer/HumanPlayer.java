package layers.model.actors.humanPlayer;

import layers.model.Square;
import layers.model.Tuple;
import layers.model.actors.Player;

public class HumanPlayer extends Player {

    private HumanMove humanMove;

    public HumanPlayer(Boolean color, String name) {
        super(color, name);
    }

    @Override
    public Tuple<Square, Square> getNextMove(Tuple<Square, Square> move) {
        // If there was a previous move execute it
        if (move != null) {
            getChessBoard().executeMove(move);
        }
        // Start the humanMove
        this.humanMove = new HumanMove();
        humanMove.setDaemon(true);

        try {
            humanMove.start();
            humanMove.join();
            move = humanMove.getMove();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return move;
    }

    public HumanMove getHumanMove() {
        return this.humanMove;
    }
}