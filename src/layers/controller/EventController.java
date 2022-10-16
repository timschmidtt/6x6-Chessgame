package layers.controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import static javafx.scene.input.MouseEvent.MOUSE_DRAGGED;
import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;
import static javafx.scene.input.MouseEvent.MOUSE_RELEASED;

/**
 * DOC: Document me Tim !
 *
 * @author Tim Schmidt (tim.schmidt@cewe.de)
 * @since 12.09.22
 */
public class EventController implements EventHandler<MouseEvent> {
    private final GameController gameController;

    public EventController(GameController gameController) {
        this.gameController = gameController;
        gameController.getView().getBoardPanel().addEventHandler(MOUSE_PRESSED, this);
        gameController.getView().getBoardPanel().addEventHandler(MOUSE_DRAGGED, this);
        gameController.getView().getBoardPanel().addEventHandler(MOUSE_RELEASED, this);
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
        getGameController().respondToPressedMouse(mouseEvent);
    }

    private void mouseDragged(MouseEvent mouseEvent) {
        getGameController().respondToDraggedMouse(mouseEvent);
    }

    private void mouseReleased(MouseEvent mouseEvent) {
        getGameController().respondToReleasedMouse(mouseEvent);
    }

    private GameController getGameController() {
        return this.gameController;
    }
}
