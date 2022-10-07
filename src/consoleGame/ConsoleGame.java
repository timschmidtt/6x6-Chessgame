package consoleGame;

import layers.model.GameModel;
import layers.model.Tuple;
import utils.IO;

public class ConsoleGame extends GameModel {

  public void startGame() {
    initPlayers();
    drawChessBoard();
  }

  /**
   * This method will draw the current board of the game with the current players.
   */
  public void drawChessBoard() {
    System.out.println("         " + getPlayers()[0].getName());
    System.out.println("   1    2   3   4    5   6");
    for (int y = 0; y < 6; y++) {
      System.out.print(y + 1 + " ");
      for (int x = 0; x < 6; x++) {
        if (getBoard().getSquare(x, y).getPiece() != null) {
          String pieceName = getBoard().getSquare(x, y).getPiece().getName();
          // Draw black pieces
          if (!getBoard().getSquare(x, y).getPiece().getColor()) {
            switch (pieceName) {
              case "Pawn":
                System.out.print(" b♙ ");
                break;
              case "King":
                System.out.print(" b♕ ");
                break;
              case "Knight":
                System.out.print(" b♘ ");
                break;
              case "Queen":
                System.out.print(" b♚ ");
                break;
              case "Rook":
                System.out.print(" b♖ ");
                break;
            }
            // Draw white pieces
          } else {
            switch (pieceName) {
              case "Pawn":
                System.out.print(" w♙ ");
                break;
              case "King":
                System.out.print(" w♕ ");
                break;
              case "Knight":
                System.out.print(" w♘ ");
                break;
              case "Queen":
                System.out.print(" w♚ ");
                break;
              case "Rook":
                System.out.print(" w♖ ");
                break;
            }
          }
        } else {
          System.out.print("  ⛋ ");
        }
      }
      System.out.println("");
    }
    System.out.println("         " + getPlayers()[1].getName());
    System.out.println();
  }

  /**
   * This method will make all players and the referee make the saved move. If the King gets beaten the game will end. If that's not the
   * case we swap the current player.
   * <p>
   * This method is just in use in the console game because the console players don't need to communicate directly here. They just make the
   * moves the human player wants them to do.
   */
  public void executeMoves() {
    // If the King is beaten the game ends, else the next player is up.
    if (getReferee().isKingBeaten(getCurrentMove())) {
      getBoard().executeMove(getCurrentMove());
      endGame();
    } else {
      getBoard().executeMove(getCurrentMove());
      getPlayers()[0].getNextMove(getCurrentMove());
      getPlayers()[1].getNextMove(getCurrentMove());
      nextPlayer();
    }
  }

  /**
   * Asks a human player with the use of the {@link IO} class what move he wants to do. If the move could be done, the game will get this
   * move to work with it.
   *
   * @return A boolean value if the move could be done.
   */
  public boolean askForMove() {
    // Ask player what move he wants to do
    System.out.println(getCurrentPlayer().getName() + " ist am Zug!");
    int fromColumn = IO.readInt("von Spalte: ") - 1;
    int fromRow = IO.readInt("von Reihe: ") - 1;
    int toColumn = IO.readInt("nach Spalte: ") - 1;
    int toRow = IO.readInt("nach Reihe: ") - 1;
    // Check if field-size was handled correct
    if (fromRow < 0 || fromRow > 5 || fromColumn < 0 || fromColumn > 5 || toRow < 0 || toRow > 5 || toColumn < 0 || toColumn > 5) {
      return false;
    }
    setCurrentMove(new Tuple<>(getBoard().getSquare(fromColumn, fromRow), getBoard().getSquare(toColumn, toRow)));
    return true;
  }

  private void initPlayers() {
    getPlayers()[0] = new ConsolePlayer(false, "Spieler A");
    getPlayers()[1] = new ConsolePlayer(true, "Spieler B");
    setPlayers(getPlayers());
    setCurrentPlayer(getPlayers()[0]);
    getReferee().setCurrentPlayer(getPlayers()[0]);
  }
}
