package guiGame;

import javafx.application.Application;
import javafx.stage.Stage;
import layers.controller.GameController;
import layers.model.GameModel;
import layers.view.View;

public class Main extends Application {

  @Override
  public void start(Stage stage) {
    GameModel gameModel = new GameModel();
    View view = new View(gameModel, stage);
    GameController gameController = new GameController(view, gameModel);
    gameController.initGameSequence();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
