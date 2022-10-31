package layers.model.actors.aiPlayer1;

import layers.model.Square;
import layers.model.Tuple;
import layers.model.actors.Player;

/**
 * DOC: Document me Tim !
 *
 * @author Tim Schmidt (tim.schmidt@cewe.de)
 * @since 17.10.22
 */
public class AiPlayer2 extends Player {

  private final int AI_LEVEL = 2;

  public AiPlayer2(Boolean color, String name) {
    super(color, name);
  }

  @Override
  public Tuple<Square, Square> getNextMove(Tuple<Square, Square> move) {
    if (move != null) {
      getChessBoard().executeMove(move);
    }
    ArtificialIntelligenceMove artificialIntelligenceMove = new ArtificialIntelligenceMove(move, AI_LEVEL, getChessBoard(), this);
    Tuple<Square, Square> nextMove = artificialIntelligenceMove.getBestMove();
    getChessBoard().executeMove(nextMove);
    return nextMove;
  }
}
