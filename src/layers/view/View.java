package layers.view;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

    private String[] programNames;

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
        this.openButton.setGraphic(getIcon("/viewIcons/open_folder.png", 25));
        this.saveButton.setGraphic(getIcon("/viewIcons/save_button.png", 25));
        this.startButton.setGraphic(getIcon("/viewIcons/play_button.png", 25));
        this.stopButton.setGraphic(getIcon("/viewIcons/stop_button.png", 25));
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

    /**
     * Creates the first menu for the menuBar.
     *
     * @return a menu for the game.
     */
    private Menu createMenu1() {
        Menu menu1 = new Menu("_Spiel");
        MenuItem menuItem1 = new MenuItem("_Öffnen");
        MenuItem menuItem2 = new MenuItem("_Drucken");
        MenuItem menuItem3 = new MenuItem("_Starten");
        MenuItem menuItem4 = new MenuItem("_Stoppen");
        MenuItem menuItem5 = new MenuItem("_Beenden");
        // Set icons
        menuItem1.setGraphic(getIcon("/viewIcons/open_folder.png", 20));
        menuItem2.setGraphic(getIcon("/viewIcons/print_button.png", 20));
        menuItem3.setGraphic(getIcon("/viewIcons/play_button.png", 20));
        menuItem4.setGraphic(getIcon("/viewIcons/stop_button.png", 20));
        menuItem5.setGraphic(getIcon("/viewIcons/close.png", 20));
        // Set Accelerators
        menuItem1.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        menuItem2.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
        menuItem3.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
        menuItem4.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        menuItem5.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        // Add items to menu
        menu1.getItems().add(menuItem1);
        menu1.getItems().add(new SeparatorMenuItem());
        menu1.getItems().add(menuItem2);
        menu1.getItems().add(menuItem3);
        menu1.getItems().add(new SeparatorMenuItem());
        menu1.getItems().add(menuItem4);
        menu1.getItems().add(menuItem5);
        return menu1;
    }

    /**
     * Creates the second menu for the menuBar.
     *
     * @return a menu for player A.
     */
    private Menu createMenu2() {
        Menu menu2 = new Menu("Spieler_A");
        RadioMenuItem radioItem1 = new RadioMenuItem("_Mensch");
        RadioMenuItem radioItem2 = new RadioMenuItem("_Computer");
        // Set icons
        radioItem1.setGraphic(getIcon("/viewIcons/human.png", 20));
        radioItem2.setGraphic(getIcon("/viewIcons/AI.png", 20));
        // Add to menu
        menu2.getItems().add(radioItem1);
        menu2.getItems().add(radioItem2);
        // Make sure that the radio buttons cant be selected at the same time
        ToggleGroup toggleGroup1 = new ToggleGroup();
        radioItem1.setToggleGroup(toggleGroup1);
        radioItem2.setToggleGroup(toggleGroup1);
        return menu2;
    }

    /**
     * Creates the third menu for the menuBar.
     *
     * @return a menu for player B.
     */
    private Menu createMenu3() {
        Menu menu3 = new Menu("Spieler_B");
        RadioMenuItem radioItem3 = new RadioMenuItem("_Mensch");
        RadioMenuItem radioItem4 = new RadioMenuItem("_Computer");
        // Set icons
        radioItem3.setGraphic(getIcon("/viewIcons/human.png", 20));
        radioItem4.setGraphic(getIcon("/viewIcons/AI.png", 20));
        // Add to menu
        menu3.getItems().add(radioItem3);
        menu3.getItems().add(radioItem4);
        // Make sure that the radio buttons cant be selected at the same time
        ToggleGroup toggleGroup2 = new ToggleGroup();
        radioItem3.setToggleGroup(toggleGroup2);
        radioItem4.setToggleGroup(toggleGroup2);
        return menu3;
    }

    public void setOnAction() {
        this.openButton.setOnAction(actionEvent -> this.gameController.openNewGameGui());
        this.saveButton.setOnAction(actionEvent -> this.gameController.saveCurrentGame());
        this.startButton.setOnAction(actionEvent -> this.gameController.startNewDummyGame());
        this.stopButton.setOnAction(actionEvent -> this.gameController.stopGame());
    }

    /**
     * Method for getting an Imageview from an url.
     *
     * @param url  the path of the image resource
     * @param size the size the imageview need to have
     * @return An imageview.
     */
    private ImageView getIcon(String url, int size) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(url)));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(size);
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