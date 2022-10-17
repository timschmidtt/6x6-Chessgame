package layers.model.actors.aiPlayer1;

import layers.model.Square;
import layers.model.Tuple;

import java.util.List;

/**
 * DOC: Document me Tim !
 *
 * @author Tim Schmidt (tim.schmidt@cewe.de)
 * @since 17.10.22
 */
public class AiPlayer1Move {

  private Tuple<Square, Square> move;
  private final int AI_LEVEL = 1;
  private boolean isMoving;

  public AiPlayer1Move(Tuple<Square, Square> move) {
    this.move = move;
    calcMove(move);
  }

  private void calcMove(Tuple<Square, Square> move) {
    if (this.isMoving) {
      Tuple<Square, Square> nextMove = maxValue(AI_LEVEL);
    } else {
      Tuple<Square, Square> nextMove = minValue(AI_LEVEL);
    }
  }

  private Tuple<Square, Square> minValue(int ai_level) {
    return null;
  }

  private Tuple<Square, Square> maxValue(int searchingLevel) {

    /*
    Tuple<Square, Square> bestMove = null;
    int highestValue = -99999999;
    List<Tuple<Square, Square>> movesList = getAllNextMoves();
    movesList.forEach(move -> {
      move.simulate();
      Tuple<Square, Square> simulatedMove;
      int simulatedMoveValue;
      if (searchingLevel <= 1 || noNexMoves()) {
        // If there are no next moves we set the current move as the simulated,
        // the same goes for the value
        simulatedMove = move;
        simulatedMoveValue = ratingFunction();
      } else if (isMoving) {
        // Player A turns
        // ... maxValue(searchingLevel -1);
      } else {
        // Player B turns
        // ... minValue(searchingLevel -1);
      }
      move.simulateBack();
      if (searchingLevel > highestValue) {
        highestValue = simulatedMoveValue;
        bestMove = move;
      }

    });
    // return the best move and the highest value

     */
    return null;
  }

  private List<Tuple<Square, Square>> getAllNextMoves() {
    return null;
  }
}
