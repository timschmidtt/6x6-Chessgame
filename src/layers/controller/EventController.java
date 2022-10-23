package layers.controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import static javafx.scene.input.MouseEvent.MOUSE_DRAGGED;
import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;
import static javafx.scene.input.MouseEvent.MOUSE_RELEASED;

/**
 * The EventController will control all thrown events by an humanPlayer and
 * assign them into the GameController to the right method.
 *
 * @author Tim Schmidt (tim.schmidt@student.ibs-ol.de)
 */
public class EventController implements EventHandler<MouseEvent> {
    private final GameController gameController;

    /**
     * In the constructor all events that need to be watched get registered.
     *
     * @param gameController The GameController.
     */
    public EventController(GameController gameController) {
        this.gameController = gameController;
        gameController.getView().getBoardPanel().addEventHandler(MOUSE_PRESSED, this);
        gameController.getView().getBoardPanel().addEventHandler(MOUSE_DRAGGED, this);
        gameController.getView().getBoardPanel().addEventHandler(MOUSE_RELEASED, this);
    }

    /**
     * This method will assign the mouseEvent to the right method.
     *
     * @param mouseEvent the event which occurred.
     */
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
        this.gameController.respondToPressedMouse(mouseEvent);
    }

    private void mouseDragged(MouseEvent mouseEvent) {
        this.gameController.respondToDraggedMouse(mouseEvent);
    }

    private void mouseReleased(MouseEvent mouseEvent) {
        this.gameController.respondToReleasedMouse(mouseEvent);
    }
}
