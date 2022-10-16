package layers.model.actors.humanPlayer;

import layers.model.GameModel;
import layers.model.Square;
import layers.model.Tuple;
import layers.view.View;

/**
 * DOC: Document me Tim !
 *
 * @author Tim Schmidt (tim.schmidt@cewe.de)
 * @since 12.10.22
 */
public class HumanMove extends Thread {

  private Object syncObject = new Object();
  private Tuple<Square, Square> move;

  @Override
  public void run() {
    synchronized (this.syncObject) {
      try {
        if (this.isInterrupted()) {
          return;
        }
        this.syncObject.wait();
      } catch (InterruptedException ignored) {
      }
    }
  }

  public void setMove(Tuple<Square, Square> move) {
    this.move = move;
    synchronized (this.syncObject) {
      this.syncObject.notify();
    }
  }

  public Tuple<Square, Square> getMove() {
    return this.move;
  }
}
