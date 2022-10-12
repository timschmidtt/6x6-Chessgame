package layers.model.actors.humanPlayer;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import layers.controller.GameController;
import layers.model.GameModel;
import layers.model.Square;
import layers.model.Tuple;
import layers.model.pieces.Piece;
import layers.view.BoardPanel;
import layers.view.View;

import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;

/**
 * DOC: Document me Tim !
 *
 * @author Tim Schmidt (tim.schmidt@cewe.de)
 * @since 12.10.22
 */
public class HumanMove extends Thread implements EventHandler<MouseEvent> {

  private final Object syncObject;
  private Piece activePiece;
  private Tuple<Square, Square> move;
  private final BoardPanel chessBoard;
  private final GameModel model;
  private final View view;
  private Tuple<Integer, Integer> activePieceCoordinates;

  public HumanMove(View view, GameModel model) {
    this.syncObject = new Object();
    this.chessBoard = view.getBoardPanel();
    this.model = model;
    this.view = view;
  }

  @Override
  public void run() {
    this.chessBoard.setOnMousePressed(this);
    this.chessBoard.setOnMouseDragged(this);
    this.chessBoard.setOnMouseReleased(this);

    // wait until move is done
    synchronized (this.syncObject) {
      try {
        // Check if game thread is interrupted
        if (this.isInterrupted()) {
          return;
        }
        this.syncObject.wait();
      } catch (InterruptedException ignored) {
      }
    }

    this.chessBoard.setOnMousePressed(this);
    this.chessBoard.setOnMouseDragged(this);
    this.chessBoard.setOnMouseReleased(this);
  }

  @Override
  public void handle(MouseEvent mouseEvent) {
    if (MOUSE_PRESSED.equals(mouseEvent.getEventType())) {
      mousePressed(mouseEvent);
    } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEvent.getEventType())) {
      mouseDragged(mouseEvent);
    } else if (MouseEvent.MOUSE_RELEASED.equals(mouseEvent.getEventType())) {
      mouseReleased(mouseEvent);
    }
  }

  private void mousePressed(MouseEvent mouseEvent) {
    Tuple<Integer, Integer> coordinates = this.view.getBoardPanel().getCoordinates(mouseEvent);
    int x = coordinates.getFirst();
    int y = coordinates.getSecond();
    Piece piece = this.model.getBoard().getSquare(x, y).getPiece();
    if (piece != null) {
      if (this.model.getReferee().canPieceGetSelected(x, y, this.model.getBoard())) {
        this.activePieceCoordinates = new Tuple<>(x, y);
        this.activePiece = piece;
        this.view.getBoardPanel().setActivePiece(piece, this.activePieceCoordinates);
      }
    }
  }

  private void mouseDragged(MouseEvent mouseEvent) {
    Tuple<Integer, Integer> coordinates = this.view.getBoardPanel().getCoordinates(mouseEvent);
    int x = coordinates.getFirst();
    int y = coordinates.getSecond();
    Piece piece = this.model.getBoard().getSquare(x, y).getPiece();
    if (piece != null) {
      if (this.model.getReferee().canPieceGetSelected(x, y, this.model.getBoard())) {
        this.activePieceCoordinates = new Tuple<>(x, y);
        this.activePiece = piece;
        this.view.getBoardPanel().setActivePiece(piece, this.activePieceCoordinates);
      }
    }
  }

  private void mouseReleased(MouseEvent mouseEvent) {
    Tuple<Integer, Integer> endCoordinates = this.view.getBoardPanel().getCoordinates(mouseEvent);
    if (this.activePiece != null) {
      Square fromSquare = this.model.getBoard().getSquare(this.activePieceCoordinates.getFirst(), this.activePieceCoordinates.getSecond());
      Square toSquare = this.model.getBoard().getSquare(endCoordinates.getFirst(), endCoordinates.getSecond());
      Tuple<Square, Square> moveSquare = new Tuple<>(fromSquare, toSquare);
      if (this.model.getReferee().checkMove(moveSquare, this.model.getBoard())) {
        this.move = moveSquare;
      }
    }

    synchronized (this.syncObject) {
      // Reset current action
      /*
      this.panel.resetCurrentAction();
      this.panel.initRedraw();

       */

      // Unlock
      this.syncObject.notify();
    }

  }

  public Tuple<Square, Square> getMove() {
    return this.move;
  }
}
