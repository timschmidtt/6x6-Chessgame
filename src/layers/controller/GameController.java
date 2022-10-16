package layers.controller;

import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import layers.model.GameModel;
import layers.model.Square;
import layers.model.Tuple;
import layers.model.actors.Player;
import layers.model.actors.humanPlayer.HumanPlayer;
import layers.model.pieces.Piece;
import layers.view.View;
import programloader.Program;
import programloader.ProgramManager;
import utils.Observable;
import utils.Observer;

import java.util.HashMap;
import java.util.List;

public class GameController implements Observer {

  private final View view;
  private GameModel model;
  private Piece activePiece;
  private Tuple<Integer, Integer> activePieceCoordinates;
  private final ProgramManager programManager;

  public GameController(View view, GameModel model) {
    this.view = view;
    this.model = model;
    this.programManager = new ProgramManager();
    setOnAction();
    new EventController(this);
  }

  /**
   * This will start a new game with the selected programs from the radio menu and setup
   * everything in model and view to be ready for a new game sequence.
   */
  public synchronized void startNewGame() {
    // Reset everything in the model and in the view
    restartGame();
    // Get the selected programs
    Tuple<String, String> selectedPrograms = this.view.getGameMenu().getSelectedPrograms();
    HashMap<String, Program> programHashmap = this.programManager.getProgramHashMap();
    // Init players from selected programs in radio menu
    Player[] players = new Player[2];
    if (selectedPrograms.getFirst().equals("_Mensch")) {
      // If it's a HumanPlayer we just load the HumanPlayer and not a Jar
      players[0] = new HumanPlayer(false, "Spieler A");
    } else {
      players[0] = this.programManager.loadPlayerJarFile(programHashmap.get(selectedPrograms.getFirst()), false, "Spieler A");
    }
    if (selectedPrograms.getSecond().equals("_Mensch")) {
      // If it's a HumanPlayer we just load the HumanPlayer and not a Jar
      players[1] = new HumanPlayer(true, "Spieler B");
    } else {
      players[1] = this.programManager.loadPlayerJarFile(programHashmap.get(selectedPrograms.getSecond()), true, "Spieler B");
    }
    // Prepare view and model attributes and elements to get started
    this.view.getStartButton().setDisable(true);
    this.view.getStopButton().setDisable(false);
    this.view.setLabelInformation(players[0].getName() + " ist am Zug!");
    this.model.setPlayers(players);
    this.model.setCurrentPlayer(players[0]);
    this.model.getBoard().addObserver(this.view.getBoardPanel());
    this.model.getReferee().addObserver(this);
    // Start the game thread
    this.model.start();
  }

  /**
   * This method will check if the clicked position on the chessboard accesses a piece and
   * set it as the active piece if possible.
   *
   * @param mouseEvent The event which occurred.
   */
  public void respondToPressedMouse(MouseEvent mouseEvent) {
    // Check if the current player is a HumanPlayer
    if (this.model.getCurrentPlayer() instanceof HumanPlayer) {
      // Get the coordinates of the mouseEvent
      Tuple<Integer, Integer> coordinates = this.view.getBoardPanel().getCoordinates(mouseEvent);
      int x = coordinates.getFirst();
      int y = coordinates.getSecond();
      // Get the chosen piece
      Piece piece = this.model.getBoard().getSquare(x, y).getPiece();
      if (piece != null) {
        // Check if the piece can be selected
        if (this.model.getReferee().canPieceGetSelected(x, y, this.model.getBoard())) {
          // Set the selected piece as activePiece in the controller and view
          this.activePieceCoordinates = new Tuple<>(x, y);
          this.activePiece = piece;
          this.view.getBoardPanel().setActivePiece(piece, getActivePieceCoordinates());
        }
      }
    }
  }

  /**
   * This method will display the active piece (if available) onto the cursor.
   *
   * @param mouseEvent The event which occurred.
   */
  public void respondToDraggedMouse(MouseEvent mouseEvent) {
    // Check if the current player is a HumanPlayer
    if (this.model.getCurrentPlayer() instanceof HumanPlayer) {
      if (this.activePiece != null) {
        // Get the coordinates of the mouseEvent
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        Tuple<Double, Double> mouseCoordinates = new Tuple<>(x, y);
        // Get a list of possible moves from the referee
        List<Tuple<Integer, Integer>> activePieceMoves =
                this.model.getReferee().getPossibleMoves(this.activePiece, this.activePieceCoordinates, this.model.getBoard());
        // Redirect the information above to the view
        this.view.getBoardPanel().drawActivePieceOnCursor(mouseCoordinates, activePieceMoves);
      }
    }
  }

  /**
   * This method will check if the by the player wanted move can be executed and if possible
   * set into the {@link layers.model.actors.humanPlayer.HumanMove}.
   *
   * @param mouseEvent The event which occurred.
   */
  public void respondToReleasedMouse(MouseEvent mouseEvent) {
    // Check if the player is a HumanPlayer
    if (this.model.getCurrentPlayer() instanceof HumanPlayer) {
      if (this.activePiece != null) {
        // Get the coordinates of the mouseEvent
        Tuple<Integer, Integer> endCoordinates = this.view.getBoardPanel().getCoordinates(mouseEvent);
        // Get the fromSquare and toSquare for the moveSquare
        Square fromSquare =
            this.model.getBoard().getSquare(this.activePieceCoordinates.getFirst(), this.activePieceCoordinates.getSecond());
        Square toSquare = this.model.getBoard().getSquare(endCoordinates.getFirst(), endCoordinates.getSecond());
        Tuple<Square, Square> moveSquare = new Tuple<>(fromSquare, toSquare);
        // Check if the moveSquare could be executed
        if (this.model.getReferee().checkMove(moveSquare, this.model.getBoard())) {
          // Cast the current player into an HumanPlayer and set the move into the HumanMoveThread
          HumanPlayer humanPlayer = (HumanPlayer) this.model.getCurrentPlayer();
          humanPlayer.getHumanMove().setMove(moveSquare);
          // Reset the active piece in view and controller
          this.activePiece = null;
          this.view.getBoardPanel().setActivePiece(null, new Tuple<>(-1,-1));
        } else {
          // If not reset the current view
          this.view.getBoardPanel().resetView(null);
        }
      }
    }
  }

  /**
   * This update method is for refreshing the label information under the chessboard
   * in the gui.
   *
   * @param observable the observable object.
   * @param object   an argument passed to the {@code notifyObservers}
   *                 method.
   */
  @Override
  public void update(Observable observable, Object object) {
    if (Platform.isFxApplicationThread()) {
      this.view.updateLabelInformation(object);
    } else {
      Platform.runLater(() -> this.view.updateLabelInformation(object));
    }
  }

  /**
   * This will set the actionEvents to the buttons in the view.
   */
  private void setOnAction() {
    this.view.getStartButton().setOnAction(actionEvent -> startNewGame());
    this.view.getStopButton().setOnAction(actionEvent -> stopGame());
    this.view.getOpenButton().setOnAction(actionEvent -> openNewGameGui());
  }

  /**
   * Restarts the game model and view.
   */
  private void restartGame() {
    this.model = new GameModel();
    this.view.getBoardPanel().setBoard(this.model.getBoard());
    this.view.getBoardPanel().resetView(null);
  }

  /**
   * Opens a new window with a new GuiGame that can be played while the old
   * game is still running.
   */
  public void openNewGameGui() {
    GameModel gameModel = new GameModel();
    View view = new View(gameModel, new Stage());
    new GameController(view, gameModel);
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
    this.view.getStopButton().setDisable(true);
    this.view.getStartButton().setDisable(false);
    this.model.interrupt();
  }
}
