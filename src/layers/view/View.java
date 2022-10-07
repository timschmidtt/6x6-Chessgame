package layers.view;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import layers.controller.GameController;
import layers.model.GameModel;

import java.util.Objects;


public class View extends Application {

    private final GameModel gameModel;
    private GameController gameController;
    private BoardPanel chessBoardPanel;

    // Declaration of the buttons
    private Button openButton;
    private Button saveButton;
    private Button printButton;
    private Button startButton;
    private Button stopButton;
    private Button exitButton;


    public View(GameModel gameModel, Stage stage) {
        this.gameModel = gameModel;
        start(stage);
    }

    /**
     * This is the start method of the Java Application class. Here the gui of the Application will be created.
     *
     * @param stage the stage give by the main class.
     */
    public void start(Stage stage) {
        GameMenu gameMenu = new GameMenu();
        Menu menu1 = gameMenu.getMenus().get(0);
        Menu menu2 = gameMenu.getMenus().get(1);
        Menu menu3 = gameMenu.getMenus().get(2);

        // Create the menuBar and add its menus
        MenuBar menubar = new MenuBar();
        menubar.getMenus().add(menu1);
        menubar.getMenus().add(menu2);
        menubar.getMenus().add(menu3);
        // Create the buttons for the toolBar
        this.openButton = new Button();
        this.saveButton = new Button();
        this.startButton = new Button();
        this.stopButton = new Button();
        // Set the images of the buttons
        this.openButton.setGraphic(getIcon("/viewIcons/open_folder.png"));
        this.saveButton.setGraphic(getIcon("/viewIcons/save_button.png"));
        this.startButton.setGraphic(getIcon("/viewIcons/play_button.png"));
        this.stopButton.setGraphic(getIcon("/viewIcons/stop_button.png"));
        // Create the toolbar and add its buttons
        ToolBar toolBar = new ToolBar();
        toolBar.getItems().add(this.openButton);
        toolBar.getItems().add(this.saveButton);
        toolBar.getItems().add(new Separator());
        toolBar.getItems().add(this.startButton);
        toolBar.getItems().add(this.stopButton);
        // Create a boardPanel as place to draw the chess game and place it in a pane
        this.chessBoardPanel = new BoardPanel(600, 600, getGameModel());
        Pane chessboard = new Pane(chessBoardPanel);
        chessboard.setMaxSize(600, 600);
        chessboard.setMinSize(600, 600);
        chessboard.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));

        // Create two labels, one for each player
        Label labelPlayer1 = new Label("Spieler A");
        setLabelStyle(labelPlayer1);
        Label labelPlayer2 = new Label("Spieler B");
        setLabelStyle(labelPlayer2);

        // Put the labels and the chessboard pane into a vBox
        VBox innerScene = new VBox(labelPlayer1, chessboard, labelPlayer2);
        innerScene.setAlignment(Pos.CENTER);
        // And put this section into a scrollPane
        ScrollPane scrollPane = new ScrollPane(innerScene);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        // Separator for a better overview
        Separator separator = new Separator(Orientation.HORIZONTAL);

        // Label at the bottom for information
        Label labelInformation = new Label("Willkommen zum 6x6 Schach");
        setLabelStyle(labelInformation);

        // Fill the main vBox with all the content to display
        VBox vBoxScene = new VBox(menubar, toolBar, scrollPane, separator, labelInformation);
        vBoxScene.setAlignment(Pos.CENTER);

        // Configuration of the stage
        stage.setTitle("6x6-Chess-Referee!");
        Scene scene = new Scene(vBoxScene, 1280, 800);
        stage.setScene(scene);
        stage.show();
    }

    public void setOnAction() {
        this.openButton.setOnAction(actionEvent -> this.gameController.openNewGameGui());
        this.saveButton.setOnAction(actionEvent -> this.gameController.saveCurrentGame());
        // this.startButton.setOnAction(actionEvent -> this.gameController.startNewDummyGame());
        this.stopButton.setOnAction(actionEvent -> this.gameController.stopGame());
    }

    /**
     * Method for getting an Imageview from an url.
     *
     * @param url  the path of the image resource
     * @return An imageview.
     */
    private ImageView getIcon(String url) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(url)));
        ImageView imageView = new ImageView(image);
        int ICON_SIZE = 25;
        imageView.setFitHeight(ICON_SIZE);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Method for setting the standard style of all labels.
     *
     * @param label the label which style will be set.
     */
    private void setLabelStyle(Label label) {
        label.setMinSize(40, 40);
        label.setFont(new Font("Serif", 12));
        label.setStyle("-fx-font-weight: bold; -fx-text-alignment: center; -fx-label-padding: 12, 0, 12, 0");
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public BoardPanel getBoardPanel() {
        return chessBoardPanel;
    }

    public Button getStartButton() {
        return this.startButton;
    }

    public Button getStopButton() {
        return this.stopButton;
    }
}