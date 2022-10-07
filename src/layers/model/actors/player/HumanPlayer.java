package layers.model.actors.player;

import layers.model.Square;
import layers.model.Tuple;
import layers.model.actors.Player;

public class HumanPlayer extends Player {

  public HumanPlayer(Boolean color, String name) {
    super(color, name);
  }

  @Override
  public Tuple<Square, Square> getNextMove(Tuple<Square, Square> move) {
    return null;
  }
}
