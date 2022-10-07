package layers.model.pieces;

import javafx.scene.image.Image;
import layers.model.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Queen extends Piece {

    public Queen(boolean color) {
        super(color, "Queen");
    }

    @Override
    public List<Tuple<Integer, Integer>> createMoves() {
        List<Tuple<Integer, Integer>> movesList = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            // Vertical moves
            movesList.add(new Tuple<>(i,0));
            movesList.add(new Tuple<>( -i,0));
            // Horizontal moves
            movesList.add(new Tuple<>(0,i));
            movesList.add(new Tuple<>(0, -i));
            // Cross moves
            movesList.add(new Tuple<>(i,i));
            movesList.add(new Tuple<>(-i,-i));
            movesList.add(new Tuple<>(i, -i));
            movesList.add(new Tuple<>(-i, i));
        }
        return movesList;
    }

    @Override
    public Image setImage() {
        Image image;
        if (!getColor()) {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pieceIcons/BlackQueen.png")));
        } else {
            image =  new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pieceIcons/WhiteQueen.png")));
        }
        return image;
    }
}
