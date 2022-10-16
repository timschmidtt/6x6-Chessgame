package layers.model.actors.humanPlayer;

import layers.controller.GameController;
import layers.model.GameModel;
import layers.model.Square;
import layers.model.Tuple;
import layers.model.actors.Player;
import layers.view.View;

public class HumanPlayer extends Player {

  private HumanMove humanMove;

  public HumanPlayer(Boolean color, String name) {
    super(color, name);
  }

  @Override
  public Tuple<Square, Square> getNextMove(Tuple<Square, Square> move) {
    this.humanMove = new HumanMove();
    humanMove.setDaemon(true);

    if (move != null) {
      getChessBoard().executeMove(move);
    }

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
