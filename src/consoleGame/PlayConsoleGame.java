package consoleGame;

import layers.model.Board;
import layers.model.Square;
import layers.model.Tuple;

public class PlayConsoleGame {

    public static void main(String[] args) {
        ConsoleGame consoleGame = new ConsoleGame();
        consoleGame.startGame();
        while (!consoleGame.isEnd()) {
            boolean move = true;
            while (move) {
                if (consoleGame.askForMove()) {
                    Tuple<Square, Square> currentMove = consoleGame.getCurrentMove();
                    Board chessBoard = consoleGame.getBoard();
                    if (consoleGame.getReferee().checkMove(currentMove, chessBoard)) {
                        consoleGame.executeMoves();
                        move = false;
                    } else {
                        System.out.println("Ungültiger Zug, dieser Zug verstößt gegen die Spielregeln, versuche es erneut!");
                    }
                } else {
                    System.out.println("Ungültiger Zug, die Spielfedgröße wurde nicht eingehalten, versuche es erneut!");
                }
            }
            consoleGame.drawChessBoard();
        }
        System.out.println(consoleGame.getCurrentPlayer().getName() + " hat gewonnen!");
    }
}
