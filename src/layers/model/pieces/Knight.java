package layers.model.pieces;

import javafx.scene.image.Image;
import layers.model.Board;
import layers.model.Square;
import layers.model.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Knight extends Piece{

    public Knight(boolean color) {
        super(color, "Knight");
    }

    @Override
    public List<Tuple<Integer, Integer>> createMoves() {
        List<Tuple<Integer, Integer>> movesList = new ArrayList<>();
        movesList.add(new Tuple<>(2,1));
        movesList.add(new Tuple<>(2,-1));
        movesList.add(new Tuple<>(-2,1));
        movesList.add(new Tuple<>(-2,-1));
        movesList.add(new Tuple<>( 1,2));
        movesList.add(new Tuple<>(-1,2));
        movesList.add(new Tuple<>(1,-2));
        movesList.add(new Tuple<>(-1,-2));
        return movesList;
    }

    @Override
    public boolean canMove(Tuple<Integer, Integer> move, Square fromSquare, Board chessboard) {
        return true;
    }

    @Override
    public Image setImage() {
        Image image;
        if (!getColor()) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pieceIcons/BlackKnight.png")));
        } else {
            image =  new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pieceIcons/WhiteKnight.png")));
        }
        return image;
    }
}
