package layers.model.actors.humanPlayer;

import layers.controller.GameController;
import layers.model.GameModel;
import layers.model.Square;
import layers.model.Tuple;
import layers.model.actors.Player;
import layers.view.View;

public class HumanPlayer extends Player {

  private final View view;
  private final GameModel model;

  public HumanPlayer(Boolean color, String name, GameController gameController) {
    super(color, name);
    this.view = gameController.getView();
    this.model = gameController.getModel();
  }

  @Override
  public Tuple<Square, Square> getNextMove(Tuple<Square, Square> move) {
    HumanMove humanMove = new HumanMove(view, model);
    humanMove.setDaemon(true);
    humanMove.start();
    return humanMove.getMove();
  }
}
