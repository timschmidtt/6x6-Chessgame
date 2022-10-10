package layers.controller;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import layers.model.GameModel;
import layers.model.Square;
import layers.model.Tuple;
import layers.model.actors.Player;
import layers.model.actors.player.HumanPlayer;
import layers.model.pieces.Piece;
import layers.view.View;
import programloader.Program;
import programloader.ProgramManager;
import java.util.HashMap;
import java.util.List;

public class GameController {

  private final View view;
  private GameModel model;
  private Piece activePiece;
  private Tuple<Integer, Integer> activePieceCoordinates;
  private ProgramManager programManager;

  public GameController(View view, GameModel model) {
    this.view = view;
    this.model = model;
    this.view.setGameController(this);
    this.view.setOnAction();
    new EventController(this);
    this.programManager = new ProgramManager();
  }

  public void initNewHumanGame() {
    Player[] players = new Player[2];
    players[0] = new HumanPlayer(false, "Spieler A");
    players[1] = new HumanPlayer(true, "Spieler B");
    getModel().setPlayers(players);
    getModel().setCurrentPlayer(players[0]);
  }

  public synchronized void startNewGame() {
    restartGame();
    Tuple<String, String> selectedPrograms = this.view.getGameMenu().getSelectedPrograms();
    HashMap<String, Program> programHashmap = this.programManager.getProgramHashMap();

    // Init players selected in radio menu
    Player[] players = new Player[2];
    if (selectedPrograms.getFirst().equals("_Mensch")) {
      players[0] = new HumanPlayer(false, "Spieler A");
    } else {
      players[0] = this.programManager.loadPlayerJarFile(programHashmap.get(selectedPrograms.getFirst()), false, "Spieler A");
    }
    if (selectedPrograms.getSecond().equals("_Mensch")) {
      players[1] = new HumanPlayer(true, "Spieler B");
    } else {
      players[1] = this.programManager.loadPlayerJarFile(programHashmap.get(selectedPrograms.getSecond()), true, "Spieler B");
    }

    this.view.getStartButton().setDisable(true);
    this.view.getStopButton().setDisable(false);
    this.model.setPlayers(players);
    this.model.setCurrentPlayer(players[0]);
    this.model.getBoard().addObserver(this.view.getBoardPanel());
    this.model.start();
  }

  public void respondToReleasedMouse(MouseEvent mouseEvent) {
    Tuple<Integer, Integer> endCoordinates = this.view.getBoardPanel().getCoordinates(mouseEvent);
    if (this.activePiece != null) {
      Square fromSquare = this.model.getBoard().getSquare(this.activePieceCoordinates.getFirst(), this.activePieceCoordinates.getSecond());
      Square toSquare = this.model.getBoard().getSquare(endCoordinates.getFirst(), endCoordinates.getSecond());
      Tuple<Square, Square> moveSquare = new Tuple<>(fromSquare, toSquare);
      if (this.model.getReferee().checkMove(moveSquare, getModel().getBoard())) {
        if (this.model.getReferee().isKingBeaten(moveSquare)) {
          this.model.endGame();
        }
        this.model.getBoard().executeMove(moveSquare);
        this.model.nextPlayer();
        if (this.model.isEnd()) {
          restartGame();
        }
      }
      this.view.getBoardPanel().resetView(null);
    }
  }

  public void respondToPressedMouse(MouseEvent mouseEvent) {
    Tuple<Integer, Integer> coordinates = this.view.getBoardPanel().getCoordinates(mouseEvent);
    int x = coordinates.getFirst();
    int y = coordinates.getSecond();
    Piece piece = this.model.getBoard().getSquare(x, y).getPiece();
    if (piece != null) {
      if (this.model.getReferee().canPieceGetSelected(x, y, this.model.getBoard())) {
        this.activePieceCoordinates = new Tuple<>(x, y);
        this.activePiece = piece;
        this.view.getBoardPanel().setActivePiece(piece, getActivePieceCoordinates());
      }
    }
  }

  public void respondToDraggedMouse(MouseEvent mouseEvent) {
    double x = mouseEvent.getX();
    double y = mouseEvent.getY();
    if (this.activePiece != null) {
      Tuple<Double, Double> mouseCoordinates = new Tuple<>(x, y);
      List<Tuple<Integer, Integer>> activePieceMoves =
          this.model.getReferee().getPossibleMoves(this.activePiece, this.activePieceCoordinates, this.model.getBoard());
      this.view.getBoardPanel().drawActivePieceOnCursor(mouseCoordinates, activePieceMoves);
    }
  }

  private void restartGame() {
    this.model = new GameModel();
    this.view.getBoardPanel().setBoard(this.model.getBoard());
    initNewHumanGame();
  }

  public void openNewGameGui() {
    GameModel gameModel = new GameModel();
    View view = new View(gameModel, new Stage());
    GameController gameController = new GameController(view, gameModel);
    gameController.initNewHumanGame();
  }

  public View getView() {
    return view;
  }

  public GameModel getModel() {
    return model;
  }

  public void setModel(GameModel model) {
    this.model = model;
  }

  public Tuple<Integer, Integer> getActivePieceCoordinates() {
    return this.activePieceCoordinates;
  }

  public void saveCurrentGame() {
    // TODO: 24.09.22 Implement
  }

  public void stopGame() {
    // TODO: 24.09.22 Implement
    this.view.getStopButton().setDisable(true);
    this.view.getStartButton().setDisable(false);
    this.model.interrupt();
  }
}
