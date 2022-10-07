package layers.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import programloader.ProgramManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameMenu {

    private ProgramManager programManager;
    private final List<Menu> menus;

    public GameMenu() {
        this.programManager = new ProgramManager();
        this.menus = new ArrayList<>();
        this.menus.add(createMenu1());
        this.menus.add(createMenu2());
        this.menus.add(createMenu3());
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

        this.programManager.getPrograms().forEach(program -> {
            RadioMenuItem radioMenuItem = new RadioMenuItem("_" + program.getClassName());
            radioMenuItem.setGraphic(getIcon("/viewIcons/AI.png", 20));
            menu2.getItems().add(radioMenuItem);
        });


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

        this.programManager.getPrograms().forEach(program -> {
            RadioMenuItem radioMenuItem = new RadioMenuItem("_" + program.getClassName());
            radioMenuItem.setGraphic(getIcon("/viewIcons/AI.png", 20));
            menu3.getItems().add(radioMenuItem);
        });

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

    public List<Menu> getMenus() {
        return this.menus;
    }
}
