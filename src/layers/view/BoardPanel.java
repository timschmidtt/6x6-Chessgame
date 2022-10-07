package layers.view;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import layers.model.Board;
import layers.model.GameModel;
import layers.model.Square;
import layers.model.Tuple;
import layers.model.pieces.Piece;
import utils.Observable;
import utils.Observer;

import java.util.List;

public class BoardPanel extends Canvas implements Observer {

    private final double PANEL_WIDTH;
    private final double PANEL_HEIGHT;
    private Board chessBoard;
    private Piece activePiece;
    private Tuple<Integer, Integer> activePieceCoordinates;

    public BoardPanel(double panelWidth, double panelHeight, GameModel gameModel) {
        super(panelWidth, panelHeight);
        this.chessBoard = gameModel.getBoard();
        this.PANEL_WIDTH = panelWidth;
        this.PANEL_HEIGHT = panelHeight;
        initFirstSequence();
    }

    private void initFirstSequence() {
        this.activePiece = null;
        this.activePieceCoordinates = new Tuple<>(-1, -1);
        drawChessBoard(getGraphicsContext2D(), this.chessBoard);
        drawPieces();
    }

    /**
     * This method will draw the squares of the chessboard with it colors. The chessboard
     * will be dynamically scaled to the size of the canvas defined in the constructor.
     *
     * @param graphicsContext2D The graphic context of the canvas.
     * @param chessBoard        The current chessboard.
     */
    private void drawChessBoard(GraphicsContext graphicsContext2D, Board chessBoard) {
        double rectWidth = PANEL_WIDTH / chessBoard.getColumns();
        double rectHeight = PANEL_HEIGHT / chessBoard.getRows();
        for (int y = 0; y < chessBoard.getRows(); y++) {
            for (int x = 0; x < chessBoard.getColumns(); x++) {
                // Decide weather the rect get filled black or white
                if (chessBoard.getSquare(x, y).getColor()) {
                    graphicsContext2D.setFill(Color.rgb(247, 237, 220));
                } else {
                    graphicsContext2D.setFill(Color.rgb(146, 93, 33));
                }
                // Now draw and fill the rect
                graphicsContext2D.fillRect(x * rectWidth, y * rectWidth, rectWidth, rectHeight);
            }
        }
    }

    /**
     * This method will draw the pieces into the squares of the chessboard.
     * The picture that will be drawn is embedded in the class of the piece.
     */
    private void drawPieces() {
        int columnSize = getBoard().getColumns();
        int rowSize = getBoard().getRows();
        double imageSize = 100 / 1.175;
        double imageMargin = 100 - imageSize;
        for (int y = 0; y < rowSize; y++) {
            for (int x = 0; x < columnSize; x++) {
                if (getBoard().getSquare(x, y).getPiece() != null && x != getActivePieceCoordinates().getFirst()
                        | y != getActivePieceCoordinates().getSecond()) {
                    getGraphicsContext2D().drawImage(getBoard().getSquare(x, y).getPiece().getIcon(), x * 100 + 0.5 * imageMargin, y * 100 + imageMargin, imageSize, imageSize);
                }
            }
        }
    }

    /**
     * This method will draw the current active piece on the current positions of the cursor.
     *
     * @param position The position of the cursor.
     * @param moves    The moves the active piece could execute.
     */
    public void drawActivePieceOnCursor(Tuple<Double, Double> position, List<Tuple<Integer, Integer>> moves) {
        double x = checkCoordinates(position.getFirst());
        double y = checkCoordinates(position.getSecond());
        if (activePiece != null) {
            getGraphicsContext2D().clearRect(0, 0, 600, 600);
            drawChessBoard(getGraphicsContext2D(), getBoard());
            markPossibleMoves(moves);
            markActivePiecePosition();
            drawPieces();
            getGraphicsContext2D().drawImage(getActivePiece().getIcon(), x - 40, y - 40, 100 / 1.175, 100 / 1.175);
        }
    }

    public void drawMovingPiece(Tuple<Integer, Integer> position, Tuple<Double, Double> move, Piece piece) {
        double x = move.getFirst() + position.getFirst();
        double y = move.getSecond() + position.getSecond();
        getGraphicsContext2D().clearRect(0, 0, 600, 600);
        drawChessBoard(getGraphicsContext2D(), getBoard());
        drawPieces();
        getGraphicsContext2D().drawImage(piece.getIcon(), x, y, 100 / 1.175, 100 / 1.175);
    }

    /**
     * This method will mark the position where the current active piece is positioned.
     */
    private void markActivePiecePosition() {
        int column = getActivePieceCoordinates().getFirst();
        int row = getActivePieceCoordinates().getSecond();
        getGraphicsContext2D().setFill(Color.rgb(59, 217, 15));
        getGraphicsContext2D().fillRect(column * 100, row * 100, 100, 100);
        if (chessBoard.getSquare(column, row).getColor()) {
            getGraphicsContext2D().setFill(Color.rgb(247, 237, 220));
        } else {
            getGraphicsContext2D().setFill(Color.rgb(146, 93, 33));
        }
        getGraphicsContext2D().fillRect((column * 100) + 3, (row * 100) + 3, 94, 94);
    }

    /**
     * This method will mark every square the active piece could move to.
     *
     * @param moves A list of moves that could be done.
     */
    public void markPossibleMoves(List<Tuple<Integer, Integer>> moves) {
        moves.forEach(move -> {
            int column = getActivePieceCoordinates().getFirst() + move.getFirst();
            int row = getActivePieceCoordinates().getSecond() + move.getSecond();
            if (inFiled(column) && inFiled(row)) {
                getGraphicsContext2D().setFill(Color.rgb(25, 60, 206));
                getGraphicsContext2D().fillRect(column * 100, row * 100, 100, 100);
                if (chessBoard.getSquare(column, row).getColor()) {
                    getGraphicsContext2D().setFill(Color.rgb(247, 237, 220));
                } else {
                    getGraphicsContext2D().setFill(Color.rgb(146, 93, 33));
                }
                getGraphicsContext2D().fillRect((column * 100) + 3, (row * 100) + 3, 94, 94);
            }
        });
    }

    /**
     * This method will draw the current chessboard. Should be called after a move were executed on the board.
     */
    @SuppressWarnings("unchecked")
    public void resetView(Object object) {
        if (object != null) {
            Tuple<Square, Square> move = (Tuple<Square, Square>) object;
            setActivePiece(move.getFirst().getPiece(), new Tuple<>(move.getSecond().getColumn(), move.getSecond().getRow()));
            drawChessBoard(getGraphicsContext2D(), this.chessBoard);
            drawPieces();
            if (object.getClass().equals(Tuple.class)) {
                AnimationThread animationThread = new AnimationThread(move, this);
                animationThread.setDaemon(true);
                animationThread.start();
            }
        } else {
            drawChessBoard(getGraphicsContext2D(), this.chessBoard);
            setActivePiece(null, new Tuple<>(-1, -1));
            drawPieces();
        }
    }

    @Override
    public void update(Observable observable, Object object) {
        if (Platform.isFxApplicationThread()) {
            resetView(object);
        } else {
            Platform.runLater(() -> resetView(object));
        }
    }

    /**
     * This method will convert the coordinates of the canvas from the view layer
     * into the coordinates of the board from the logic layer.
     *
     * @param mouseEvent The mouseEvent that comes from the EventController.
     * @return The converted coordinates.
     */
    public Tuple<Integer, Integer> getCoordinates(MouseEvent mouseEvent) {
        return new Tuple<>((int) (checkCoordinates(mouseEvent.getX()) / 100), (int) (checkCoordinates(mouseEvent.getY()) / 100));
    }

    /**
     * Checks if the chosen column or row index fits the board size.
     *
     * @param size Column / row index.
     * @return If the index fits the board.
     */
    private boolean inFiled(int size) {
        return size <= 5 && size >= 0;
    }

    /**
     * This method is used for keeping the pieces optical in the chessboard. If the cursor
     * moves outside the chessboard the piece will be still sit in the chessboard without
     * exceeding the border of the chessboard.
     *
     * @param coordinate Coordinates of the cursor.
     * @return The adjusted coordinates.
     */
    private Double checkCoordinates(Double coordinate) {
        double x = coordinate;
        if (x < 22) {
            x = 22;
        }
        if (x > 572) {
            x = 572;
        }
        return x;
    }

    /**
     * This setter sets the current active piece and his current position on the chessboard.
     *
     * @param activePiece            The current active piece.
     * @param activePieceCoordinates It's chessboard coordinates.
     */
    public void setActivePiece(Piece activePiece, Tuple<Integer, Integer> activePieceCoordinates) {
        this.activePieceCoordinates = activePieceCoordinates;
        this.activePiece = activePiece;
    }

    public Board getBoard() {
        return this.chessBoard;
    }

    public void setBoard(Board chessBoard) {
        this.chessBoard = chessBoard;
    }

    public Piece getActivePiece() {
        return this.activePiece;
    }

    public Tuple<Integer, Integer> getActivePieceCoordinates() {
        return this.activePieceCoordinates;
    }
}
