package layers.model.pieces;

import javafx.scene.image.Image;
import layers.model.Board;
import layers.model.Square;
import layers.model.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class King extends Piece {

    public King(boolean color) {
        super(color, "King");
    }

    @Override
    public List<Tuple<Integer, Integer>> createMoves() {
        List<Tuple<Integer, Integer>> movesList = new ArrayList<>();
        movesList.add(new Tuple<>(1,0));
        movesList.add(new Tuple<>( -1,0));
        movesList.add(new Tuple<>(0,1));
        movesList.add(new Tuple<>( 0, -1));
        movesList.add(new Tuple<>(1,1));
        movesList.add(new Tuple<>( -1,-1));
        movesList.add(new Tuple<>(1, -1));
        movesList.add(new Tuple<>(-1, 1));
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
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pieceIcons/BlackKing.png")));
        } else {
            image =  new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pieceIcons/WhiteKing.png")));
        }
        return image;
    }
}
